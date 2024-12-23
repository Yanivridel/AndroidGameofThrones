package com.example.loginsignup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CustomeAdapter extends RecyclerView.Adapter<CustomeAdapter.MyViewHolder> {

    private List<DataModel> dataSet;  // Changed from ArrayList to List
    private List<DataModel> dataSetFull;  // To keep a copy of the full dataset
    private OnItemClickListener onItemClickListener;

    public CustomeAdapter(List<DataModel> dataSet) {
        this.dataSet = dataSet;
        this.dataSetFull = new ArrayList<>(dataSet);  // Keep a copy of the original data
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewFamily;
        TextView textViewDescription;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textView);
            textViewFamily = itemView.findViewById(R.id.textViewFamily);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataModel currentItem = dataSet.get(position);
        holder.textViewName.setText(currentItem.getName());
        holder.textViewFamily.setText(currentItem.getFamily());
        holder.textViewDescription.setText(currentItem.getDescription());
        holder.imageView.setImageResource(currentItem.getImage());

        // If the OnItemClickListener is set, assign it to the itemView
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(currentItem));
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    // Filter method to update the list upon search text change
    public void filterList(String query) {
        if (query.isEmpty()) {
            dataSet = new ArrayList<>(dataSetFull);  // Reset to full list
        } else {
            List<DataModel> filteredList = new ArrayList<>();
            for (DataModel item : dataSetFull) {
                if (item.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(item);
                }
            }
            dataSet = filteredList;  // Set filtered list
        }
        notifyDataSetChanged();  // Refresh the RecyclerView
    }

    // Set the item click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // Interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(DataModel item);
    }
}
