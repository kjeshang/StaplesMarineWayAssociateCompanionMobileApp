package com.example.staplesmarinewayassociatecompanionapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Product_RecyclerViewAdapter extends RecyclerView.Adapter<Product_RecyclerViewAdapter.ProductHolder> {

    Context context;
    ArrayList<Product> product_list;
    private final Product_RecyclerViewInterface product_recyclerViewInterface;

    public Product_RecyclerViewAdapter(Context context, ArrayList<Product> product_list, Product_RecyclerViewInterface product_recyclerViewInterface) {
        this.context = context;
        this.product_list = product_list;
        this.product_recyclerViewInterface = product_recyclerViewInterface;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new Product_RecyclerViewAdapter.ProductHolder(view, product_recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        holder.txt_product.setText(product_list.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return product_list.size();

    }

    public static class ProductHolder extends RecyclerView.ViewHolder {

        TextView txt_product;

        public ProductHolder(@NonNull View itemView, Product_RecyclerViewInterface product_recyclerViewInterface) {
            super(itemView);
            txt_product = itemView.findViewById(R.id.txt_product);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(product_recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION) {
                            product_recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
