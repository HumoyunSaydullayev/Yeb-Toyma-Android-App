package com.example.yebtoyma.Fragments.User;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.yebtoyma.Model.User;
import com.example.yebtoyma.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class UserFragment extends Fragment {
    FirebaseAuth auth;
    EditText new_name;
    EditText new_phone_number;
    EditText verify_password;
    CountryCodePicker countryCodePicker;
    Button sign;
    Button sendcode;
    String verifitionId;
    View view;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseUser currentUser = (FirebaseUser) FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null){
            List<User> users = null;
            view= inflater.inflate(R.layout.fragment_user,container,false);
            TextView logout=view.findViewById(R.id.logout_app);
            TextView Name=view.findViewById(R.id.User_name_phone);

            FirebaseFirestore db=FirebaseFirestore.getInstance();
            db.collection("User")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    User model=document.toObject(User.class);
                                    if (model.getUser_id().equals(currentUser.getUid())){
                                        Name.setText(model.getUser_name()+"\n"+model.getUser_phone());
                                    }
                                }
                            } else {
                                Toast.makeText(getContext(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    replaceRegister(inflater,container);
                    requireActivity().recreate();
                }
            });
        }else {
            view = inflater.inflate(R.layout.user_register, container, false);
            auth=FirebaseAuth.getInstance();
            new_name=view.findViewById(R.id.new_user_name);
            new_phone_number=view.findViewById(R.id.new_user_phone_number);
            verify_password=view.findViewById(R.id.new_user_verification);
            sign=view.findViewById(R.id.new_register);
            sendcode=view.findViewById(R.id.verification_code);
            countryCodePicker=view.findViewById(R.id.ccp);
            countryCodePicker.registerCarrierNumberEditText(new_phone_number);

            //TextInput
            TextInputLayout user_text=view.findViewById(R.id.user_text);
            LinearLayout phone_text=view.findViewById(R.id.phone_text);
            TextInputLayout veriwy_text=view.findViewById(R.id.veriwy_text);
            sendcode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone=new_phone_number.getText().toString();
                    if (!new_name.getText().toString().equals("")){
                        if (TextUtils.isEmpty(phone) ){
                            Toast.makeText(getContext(), "Mobil raqam maydoni bo'sh", Toast.LENGTH_SHORT).show();
                        }else{
                            sendverificationcode(phone);
                            user_text.setVisibility(View.GONE);
                            sendcode.setVisibility(View.GONE);
                            phone_text.setVisibility(View.GONE);
                            veriwy_text.setVisibility(View.VISIBLE);
                            sign.setVisibility(View.VISIBLE);
                        }
                    }else{
                        Toast.makeText(getContext(), "Ism maydoni bo'sh", Toast.LENGTH_SHORT).show();
                    }


                }
            });
            sign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone=verify_password.getText().toString();
                    if (TextUtils.isEmpty(phone)){
                        Toast.makeText(getContext(), "Tasdiqlash kodi maydoni bo'sh", Toast.LENGTH_SHORT).show();
                    }else{
                        verify_code(phone);
                        replaceUser(inflater,container);
                        requireActivity().recreate();
                    }
                }
            });
        }
        return view;
    }
    public void replaceRegister(LayoutInflater inflater, ViewGroup container){
        view = inflater.inflate(R.layout.user_register, container, false);
    }
    public void replaceUser(LayoutInflater inflater, ViewGroup container){
        view = inflater.inflate(R.layout.fragment_user, container, false);
    }
    public void sendverificationcode(String phone) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(countryCodePicker.getFullNumberWithPlus().replace(" ",""))       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(requireActivity())                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
            final String code=credential.getSmsCode();
            if (code != null){
                verify_code(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(s,token);
            verifitionId=s;
        }
        
    };
    private void verify_code(String code){
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verifitionId,code);
        signbycredentals(credential);

    }

    private void signbycredentals(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getContext(), "Succes", Toast.LENGTH_SHORT).show();
                            FirebaseFirestore firestore;
                            auth=FirebaseAuth.getInstance();
                            firestore=FirebaseFirestore.getInstance();
                            final HashMap<String,Object> cartMap=new HashMap<>();
                            cartMap.put("User_id", auth.getUid());
                            cartMap.put("User_name",new_name.getText().toString());
                            cartMap.put("User_phone",countryCodePicker.getFullNumberWithPlus().replace(" ",""));
                            firestore.collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Toast.makeText(getContext(), "Add to User", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
    }
}