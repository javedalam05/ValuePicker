package com.picker;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.value.picker.R;



public class ValueAdapter extends RecyclerView.Adapter<ValueAdapter.ViewHolder> {


    //    private String[] mData = new String[0];
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;


    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    String selectedValue = "5k";


    String[] list;

    // data is passed into the constructor
    public ValueAdapter(Context context, String[] list) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
//        this.counter = counter;
    }


    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.value_grid_item, parent, false);
        return new ViewHolder(view);
    }


    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // String animal = mData[position];

        String mValue = list[position];
        holder.myTextView.setText(mValue);


        if(mValue.equalsIgnoreCase(ValuePickerDialog.disableValue)){
            holder.rl_click.setVisibility(View.INVISIBLE);
            holder.rl_click.setEnabled(false);
        }else{
            holder.rl_click.setVisibility(View.VISIBLE);
            holder.rl_click.setEnabled(true);
        }

        holder.rl_click.setTag(position);

        if (mValue.equalsIgnoreCase(selectedValue)) {
            holder.rl_click.setBackgroundResource(R.drawable.circle_grid_blue);
            holder.myTextView.setTextColor(Color.WHITE);
        } else {
            holder.rl_click.setBackgroundResource(R.drawable.circle_shape_light_gray);
            holder.myTextView.setTextColor(Color.parseColor("#9c9797"));
        }
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return list.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
//            implements View.OnClickListener {
        TextView myTextView;
        RelativeLayout rl_click;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = (TextView) itemView.findViewById(R.id.tv_p1);
            rl_click = (RelativeLayout) itemView.findViewById(R.id.rl_click);
//            itemView.setOnClickListener(this);

            rl_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) {
                        int position = (int) v.getTag();
                        mClickListener.onItemClick(v, position, list[position]);
                    }
                }
            });
        }

//        @Override
//        public void onClick(View view) {
////            value = "" + (counter + (getAdapterPosition() + 1));
//
//            if (mClickListener != null) {
//                mClickListener.onItemClick(view, getAdapterPosition(), list[getAdapterPosition()]);
//            }
//
//        }
    }

//    // convenience method for getting data at click position
//    String getItem(int id) {
//        return id];
//    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events

}