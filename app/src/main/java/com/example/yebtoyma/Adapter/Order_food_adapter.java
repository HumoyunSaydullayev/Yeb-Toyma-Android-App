package com.example.yebtoyma.Adapter;

import static com.example.yebtoyma.Dashboard.bottomNav;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yebtoyma.Fragments.StoreFragment;
import com.example.yebtoyma.Model.Order_food;
import com.example.yebtoyma.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Objects;

public class Order_food_adapter extends RecyclerView.Adapter<Order_food_adapter.PopularFoodViewHolder>{

    Context context;
    List<Order_food> list;
    public Order_food_adapter( List<Order_food> popularFoodList,Context context) {
        this.context = context;
        this.list = popularFoodList;
    }
    @NonNull
    @Override
    public Order_food_adapter.PopularFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_store, parent, false);
        return new PopularFoodViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Order_food_adapter.PopularFoodViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(holder.food_image).load(list.get(position).getProductImage()).into(holder.food_image);
        holder.food_name.setText(list.get(position).getProductName());
        holder.food_price.setText(list.get(position).getProductPrice()+" so'm");
        holder.food_amount.setText(String.valueOf(list.get(position).getProductAmount()));
        holder.food_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db=FirebaseFirestore.getInstance();
                FirebaseUser currentUser = (FirebaseUser) FirebaseAuth.getInstance().getCurrentUser();
                db.collection("CurrentUser").document(Objects.requireNonNull(currentUser).getUid())
                        .collection("AddToCart")
                        .document(list.get(position).getProductUid())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    list.remove(list.remove(position));
                                    notifyDataSetChanged();

                                }
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static final class PopularFoodViewHolder extends RecyclerView.ViewHolder{
        TextView food_name;
        TextView food_price;
        TextView food_amount;
        ImageView food_image;
        ImageView food_delete;
        public PopularFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            food_name = itemView.findViewById(R.id.Order_food_name);
            food_price = itemView.findViewById(R.id.order_food_price);
            food_amount = itemView.findViewById(R.id.order_food_number);
            food_image=itemView.findViewById(R.id.order_food_image);
            food_delete=itemView.findViewById(R.id.item_store);
        }
    }
}
