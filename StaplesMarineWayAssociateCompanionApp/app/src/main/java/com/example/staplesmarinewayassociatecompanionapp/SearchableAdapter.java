package com.example.staplesmarinewayassociatecompanionapp;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SearchableAdapter extends ArrayAdapter<String> {

    private List<String> originalData;
    private List<String> filteredData;
    private Filter filter;

    public SearchableAdapter(Context context, int resource, List<String> data) {
        super(context, resource, data);
        this.originalData = data;
        this.filteredData = data;
        this.filter = new CustomFilter();
    }

    @Override
    public int getCount() {
        return filteredData.size();
    }

    @Override
    public String getItem(int position) {
        return filteredData.get(position);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<String> filteredList = new ArrayList<>();

            if (constraint != null && constraint.length() > 0) {
                for (String item : originalData) {
                    if (item.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(item);
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;
            } else {
                results.count = originalData.size();
                results.values = originalData;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            filteredData = (List<String>) results.values;
            notifyDataSetChanged();
        }
    }
}
