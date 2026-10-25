package com.example.yebtoyma.Adapter;

import static com.example.yebtoyma.Dashboard.bottomNav;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yebtoyma.Dashboard;
import com.example.yebtoyma.Model.FoodItemModel;
import com.example.yebtoyma.Model.Menu;
import com.example.yebtoyma.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    int sum = 0;
    List<FoodItemModel> list;
    Context context;
    private List<Menu> itemsInCartList;
    FirebaseFirestore firestore;
    FirebaseAuth auth;


    public FoodAdapter(List<FoodItemModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_recycler_row, parent, false);
        return new FoodViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(holder.view).load(list.get(position).getFood_image()).into(holder.food_image);
        holder.food_name.setText(list.get(position).getFood_name());
        holder.food_price.setText(list.get(position).getFood_price()+" so'm");
        holder.view.setOnClickListener(view -> {

        });

        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sum++;
                Menu menu=new Menu(list.get(position).getFood_image(),list.get(position).getFood_name(),list.get(position).getFood_type(),list.get(position).getFood_price());
                if(itemsInCartList == null) {
                    itemsInCartList = new ArrayList<>();
                }
                itemsInCartList.add(menu);
                holder.addMoreLayout.setVisibility(View.VISIBLE);
                holder.addToCartButton.setVisibility(View.GONE);

            }
        });
        holder.imageAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sum++;
                holder.count.setText(String.valueOf(sum));
            }
        });
        holder.imageMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sum--;
                if (sum==0){
                    holder.addMoreLayout.setVisibility(View.GONE);
                    holder.addToCartButton.setVisibility(View.VISIBLE);
                }else{
                    holder.count.setText(String.valueOf(sum));
                }
            }
        });
        holder.AddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore=FirebaseFirestore.getInstance();
                auth=FirebaseAuth.getInstance();
                final HashMap<String,Object> cartMap=new HashMap<>();
                cartMap.put("ProductImage",list.get(position).getFood_image());
                cartMap.put("ProductName",list.get(position).getFood_name());
                cartMap.put("ProductType",list.get(position).getFood_type());
                cartMap.put("ProductPrice",list.get(position).getFood_price()*sum);
                cartMap.put("ProductAmount",sum);
                if (auth.getCurrentUser() != null){
                    firestore.collection("CurrentUser").document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                            .collection("AddToCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Toast.makeText(context, "Add to Cart", Toast.LENGTH_SHORT).show();
                                    bottomNav.setCount(3, "1");
                                    sum=1;
                                    holder.count.setText(String.valueOf(sum));
                                    holder.addMoreLayout.setVisibility(View.GONE);
                                    holder.addToCartButton.setVisibility(View.VISIBLE);
                                }
                            });
                }else{
                    Toast.makeText(context, "Avvalo ro'yxatdan o'ting", Toast.LENGTH_SHORT).show();
                    sum=1;
                    holder.count.setText(String.valueOf(sum));
                    holder.addMoreLayout.setVisibility(View.GONE);
                    holder.addToCartButton.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView addToCartButton;
        ImageView imageMinus;
        ImageView imageAddOne;
        ImageView food_image;
        ImageView AddCart;
        TextView food_name, food_price;
        View view;
        TextView count;
        LinearLayout addMoreLayout;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            food_image = itemView.findViewById(R.id.thumbImage);
            food_name = itemView.findViewById(R.id.menuName);
            food_price = itemView.findViewById(R.id.menuPrice);
            addToCartButton = view.findViewById(R.id.addToCartButton);
            imageMinus = view.findViewById(R.id.imageMinus);
            AddCart = view.findViewById(R.id.add_cart);
            count = view.findViewById(R.id.tvCount);
            imageAddOne = view.findViewById(R.id.imageAddOne);
            addMoreLayout  = view.findViewById(R.id.addMoreLayout);

        }
    }
}
