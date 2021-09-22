package com.osos.bottomsheetrecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<ItemObject> mListItems;
    private IClickListner iClickListner;

    public ItemAdapter(List<ItemObject> mListItems, IClickListner iClickListner) {
        this.mListItems = mListItems;
        this.iClickListner = iClickListner;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder holder, int position) {
        ItemObject itemObject = mListItems.get(position);
        if (itemObject == null){
            return;
        }
        holder.textView.setText(itemObject.getName());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickListner.clickItem(itemObject);
            }
        });

        Picasso.get().load(itemObject.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (mListItems!=null){
            return mListItems.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private LinearLayout linearLayout;
        private ImageView imageView;

        public ItemViewHolder(@NonNull View itemView){
            super(itemView);

            linearLayout = itemView.findViewById(R.id.linear);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
