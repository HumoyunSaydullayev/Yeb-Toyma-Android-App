package com.example.yebtoyma.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yebtoyma.Fragments.MenuFragment;
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


public class MenusAdapter extends RecyclerView.Adapter<MenusAdapter.PopularFoodViewHolder> {

    Context context;
    List<MenuButtons> menuList;
    FirebaseFirestore db;
    public MenusAdapter(Context context, List<MenuButtons> popularFoodList) {
        this.context = context;
        this.menuList = popularFoodList;
    }

    @NonNull
    @Override
    public PopularFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_menus, parent, false);
        return new PopularFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularFoodViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.name.setText(menuList.get(position).getNames());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (menuList.get(position).getNames()) {
                    case "Burger":
                        Burger();
                        break;
                    case "Clab-sendwinch":
                        Sandvich();
                        break;
                    case "Hot Dog":
                        Hot_Dog();
                        break;
                    case "Ichimliklar":
                        Ichimliklar();
                        break;
                    case "Lavash":
                        Lavash();
                        break;
                }
            }
        });


    }

    private void Lavash(){
        List<FoodItemModel> foodList;
        FoodAdapter foodAdapter;
        db =FirebaseFirestore.getInstance();
        @SuppressLint("InflateParams") View view_menu=LayoutInflater.from(context).inflate(R.layout.fragment_menu,null);
        RecyclerView recyclerView = MenuFragment.recyclerView;
        GridLayoutManager manage_lavash=new GridLayoutManager(view_menu.getContext(),2);
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
        foodAdapter = new FoodAdapter(foodList, view_menu.getContext());
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
                            Toast.makeText(context, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        recyclerView.setAdapter(foodAdapter);
    }
    private void Burger(){
        List<FoodItemModel> foodList;
        FoodAdapter foodAdapter;
        db =FirebaseFirestore.getInstance();
        @SuppressLint("InflateParams") View view_menu=LayoutInflater.from(context).inflate(R.layout.fragment_menu,null);
        RecyclerView recyclerView = MenuFragment.recyclerView;
        GridLayoutManager manager_burger=new GridLayoutManager(view_menu.getContext(),2);
        manager_burger.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
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
        recyclerView.setLayoutManager(manager_burger);
        foodAdapter = new FoodAdapter(foodList, view_menu.getContext());
        db.collection("Food")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                FoodItemModel model=document.toObject(FoodItemModel.class);
                                if (model.getFood_type().equals("Burger")){
                                    foodList.add(model);
                                    foodAdapter.notifyDataSetChanged();
                                }

                            }
                        } else {
                            Toast.makeText(context, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        recyclerView.setAdapter(foodAdapter);
    }
    private void Sandvich() {
        List<FoodItemModel> foodList;
        FoodAdapter foodAdapter;
        db = FirebaseFirestore.getInstance();
        @SuppressLint("InflateParams") View view_menu = LayoutInflater.from(context).inflate(R.layout.fragment_menu, null);
        RecyclerView recyclerView = MenuFragment.recyclerView;
        GridLayoutManager manager_sandwich = new GridLayoutManager(view_menu.getContext(), 2);
        manager_sandwich.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if ((position + 1) % 11 * 2 == 0) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        foodList = new ArrayList<>();
        recyclerView.setLayoutManager(manager_sandwich);
        foodAdapter = new FoodAdapter(foodList, view_menu.getContext());
        db.collection("Food")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                FoodItemModel model = document.toObject(FoodItemModel.class);
                                if (model.getFood_type().equals("Sendvich")) {
                                    foodList.add(model);
                                    foodAdapter.notifyDataSetChanged();
                                }

                            }
                        } else {
                            Toast.makeText(context, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        recyclerView.setAdapter(foodAdapter);
    }
    private void Hot_Dog(){
        List<FoodItemModel> foodList;
        FoodAdapter foodAdapter;
        db = FirebaseFirestore.getInstance();
        @SuppressLint("InflateParams") View view_menu = LayoutInflater.from(context).inflate(R.layout.fragment_menu, null);
        RecyclerView recyclerView = MenuFragment.recyclerView;
        GridLayoutManager manager_hot_dog = new GridLayoutManager(view_menu.getContext(), 2);
        manager_hot_dog.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if ((position + 1) % 11 * 2 == 0) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        foodList = new ArrayList<>();
        recyclerView.setLayoutManager(manager_hot_dog);
        foodAdapter = new FoodAdapter(foodList, view_menu.getContext());
        db.collection("Food")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                FoodItemModel model = document.toObject(FoodItemModel.class);
                                if (model.getFood_type().equals("Hot_dog")) {
                                    foodList.add(model);
                                    foodAdapter.notifyDataSetChanged();
                                }
                            }
                        } else {
                            Toast.makeText(context, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        recyclerView.setAdapter(foodAdapter);
    }
    private void Ichimliklar(){
        List<FoodItemModel> foodList;
        FoodAdapter foodAdapter;
        db = FirebaseFirestore.getInstance();
        @SuppressLint("InflateParams") View view_menu = LayoutInflater.from(context).inflate(R.layout.fragment_menu, null);
        RecyclerView recyclerView = MenuFragment.recyclerView;
        GridLayoutManager manager_hot_dog = new GridLayoutManager(view_menu.getContext(), 2);
        manager_hot_dog.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if ((position + 1) % 11 * 2 == 0) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        foodList = new ArrayList<>();
        recyclerView.setLayoutManager(manager_hot_dog);
        foodAdapter = new FoodAdapter(foodList, view_menu.getContext());
        db.collection("Food")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                FoodItemModel model = document.toObject(FoodItemModel.class);
                                if (model.getFood_type().equals("Drink")) {
                                    foodList.add(model);
                                    foodAdapter.notifyDataSetChanged();
                                }
                            }
                        } else {
                            Toast.makeText(context, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        recyclerView.setAdapter(foodAdapter);
    }
    @Override
    public int getItemCount() {
        return menuList.size();
    }


    public static final class PopularFoodViewHolder extends RecyclerView.ViewHolder{
        TextView  name;

        public PopularFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.menus_name);

        }
    }

}
