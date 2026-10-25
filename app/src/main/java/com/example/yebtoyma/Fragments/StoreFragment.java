package com.example.yebtoyma.Fragments;

import static com.example.yebtoyma.Dashboard.bottomNav;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.yebtoyma.Adapter.FoodAdapter;
import com.example.yebtoyma.Adapter.Order_food_adapter;
import com.example.yebtoyma.Fragments.User.UserFragment;
import com.example.yebtoyma.Model.FoodItemModel;
import com.example.yebtoyma.Model.Order_food;
import com.example.yebtoyma.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class StoreFragment extends Fragment {
    FirebaseFirestore db;
    FirebaseAuth auth;
    public static TextView total;
    public static int total_price=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_store, container, false);
        FirebaseUser currentUser = (FirebaseUser) FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser!= null) {
            db = FirebaseFirestore.getInstance();
            RecyclerView recyclerView = view.findViewById(R.id.store_recyleview);
            Button buy=view.findViewById(R.id.order_food_button);
            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LottieAnimationView viewaniamtion=view.findViewById(R.id.empty_store);
            TextView EmptyCartText =view.findViewById(R.id.EmptyCartText);
            total=view.findViewById(R.id.order_total);
            viewaniamtion.setVisibility(View.GONE);
            EmptyCartText.setVisibility(View.GONE);
            buy.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            total.setVisibility(View.VISIBLE);

            List<Order_food> foodList = new ArrayList<>();
            Order_food_adapter foodAdapter = new Order_food_adapter(foodList, view.getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            total.setText("");
            total_price=0;
            db.collection("CurrentUser").document(Objects.requireNonNull(currentUser).getUid())
                    .collection("AddToCart").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult().getDocuments()) {

                                    String documentid=document.getId();
                                    Order_food model = document.toObject(Order_food.class);
                                    assert model != null;
                                    model.setProductUid(documentid);
                                    foodList.add(model);
                                    total_price=total_price+model.getProductPrice();
                                    total.setText("Buyurtma narxi: "+total_price+" so'm");
                                    if (foodList.size()==0){
                                        viewaniamtion.setVisibility(View.VISIBLE);
                                        EmptyCartText.setVisibility(View.VISIBLE);
                                        buy.setVisibility(View.GONE);
                                        recyclerView.setVisibility(View.GONE);
                                        total.setVisibility(View.GONE);
                                    }else{
                                        viewaniamtion.setVisibility(View.GONE);
                                        EmptyCartText.setVisibility(View.GONE);
                                        buy.setVisibility(View.VISIBLE);
                                        recyclerView.setVisibility(View.VISIBLE);
                                        total.setVisibility(View.VISIBLE);
                                    }
                                    foodAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Toast.makeText(getContext(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            recyclerView.setAdapter(foodAdapter);
            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Ma'lumotlar qo'shildi !!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;
    }

}