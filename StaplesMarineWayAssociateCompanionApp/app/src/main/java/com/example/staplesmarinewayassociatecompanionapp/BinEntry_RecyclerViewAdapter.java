package com.example.staplesmarinewayassociatecompanionapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BinEntry_RecyclerViewAdapter extends RecyclerView.Adapter<BinEntry_RecyclerViewAdapter.BinEntryHolder> {

    Context context;
    ArrayList<BinEntry> binEntry_list;
    private final Product_RecyclerViewInterface product_recyclerViewInterface;
    DatabaseHelper databaseHelper;

    public BinEntry_RecyclerViewAdapter(Context context, ArrayList<BinEntry> binEntry_list, Product_RecyclerViewInterface product_recyclerViewInterface) {
        this.context = context;
        this.binEntry_list = binEntry_list;
        this.product_recyclerViewInterface = product_recyclerViewInterface;
    }

    @NonNull
    @Override
    public BinEntryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new BinEntry_RecyclerViewAdapter.BinEntryHolder(view, product_recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull BinEntryHolder holder, int position) {
        holder.txt_product.setText(binEntry_list.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return binEntry_list.size();
    }

    public static class BinEntryHolder extends RecyclerView.ViewHolder {

        TextView txt_product;

        public BinEntryHolder(@NonNull View itemView, Product_RecyclerViewInterface product_recyclerViewInterface) {
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
