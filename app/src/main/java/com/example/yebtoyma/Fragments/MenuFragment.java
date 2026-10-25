package com.example.yebtoyma.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yebtoyma.Adapter.FoodAdapter;
import com.example.yebtoyma.Adapter.MenusAdapter;
import com.example.yebtoyma.Dashboard;
import com.example.yebtoyma.Model.FoodItemModel;
import com.example.yebtoyma.Model.MenuButtons;
import com.example.yebtoyma.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    public static RecyclerView recyclerView = null;
    FirebaseFirestore db;
    List<FoodItemModel> foodList;
    FoodAdapter foodAdapter;
    List<FoodItemModel> list = new ArrayList<>();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_menu,container,false);

        if (isConnected(view.getContext())) {
            showInternetDialog(view.getContext());
        }
        RecyclerView buttons=view.findViewById(R.id.popular_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        buttons.setLayoutManager(layoutManager);
        List<MenuButtons> buttonList = new ArrayList<>();

        buttonList.add(new MenuButtons("Lavash"));
        buttonList.add(new MenuButtons("Clab-sendwinch"));
        buttonList.add(new MenuButtons("Burger"));
        buttonList.add(new MenuButtons("Hot Dog"));
        buttonList.add(new MenuButtons("Ichimliklar"));
        MenusAdapter adapter=new MenusAdapter(getContext(),buttonList);
        buttons.setAdapter(adapter);

        recyclerView = view.findViewById(R.id.store_recyleview);
        List<FoodItemModel> foodList;
        FoodAdapter foodAdapter;
        db =FirebaseFirestore.getInstance();
        GridLayoutManager manage_lavash=new GridLayoutManager(view.getContext(),2);
        manage_lavash.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if ((position+1)%11*2==0){
                    return 2;
                }else{
                    return 1;
                }
            }
        });
        foodList=new ArrayList<>();
        recyclerView.setLayoutManager(manage_lavash);
        foodAdapter = new FoodAdapter(foodList, view.getContext());
        db.collection("Food")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                FoodItemModel model=document.toObject(FoodItemModel.class);
                                if (model.getFood_type().equals("Lavash")){
                                    foodList.add(model);
                                    foodAdapter.notifyDataSetChanged();
                                }

                            }
                        } else {
                            Toast.makeText(view.getContext(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        recyclerView.setAdapter(foodAdapter);
        return view;
    }
    private void showInternetDialog(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.no_internet_dialog,null);
        view.findViewById(R.id.try_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnected(context)) {
                    showInternetDialog(context);
                } else {
                    startActivity(new Intent(context, Dashboard.class));
                }
            }
        });

        dialog.setContentView(view);
        dialog.show();

    }

    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiConn == null || !wifiConn.isConnected()) && (mobileConn == null || !mobileConn.isConnected());
    }
}