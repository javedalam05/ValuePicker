package com.picker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.value.picker.R;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class FirstFragment extends Fragment {


    public interface ClickLisner {
        void itemClick(int fragmentIndex, String countIndex);
    }

    public void setClickLisner(ClickLisner clickLisner) {
        this.clickLisner = clickLisner;
    }

    ClickLisner clickLisner;
//    ValuePickerDialog.PICKER_ENUM dialogType;

    String selectedValue = "0";

    String[] list;


    int numberOfColumns = 4;
    int itemCountPerPage = 20;
    int numberOfPages = 0;


    public static final FirstFragment newInstance(String message, int item,
                                                  String indexValue, String[] list) {
        //        ,ValuePickerDialog.PICKER_ENUM dialogType

        FirstFragment f = new FirstFragment();
        Bundle bdl = new Bundle(item);
        f.item = item;
        f.list = list;
        f.selectedValue = indexValue;
        bdl.putString(EXTRA_MESSAGE, message);
        f.setArguments(bdl);
        return f;
    }


    private int item;
    public RecyclerView.Adapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.number_grid_frag, container, false);


        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rvNumbers);

//        if (dialogType == ValuePickerDialog.PICKER_ENUM.NUMBER_PICK) {
//
////            int myValue = Integer.parseInt(selectedValue);
//
//            int numberOfColumns = 5;
//            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
//            if (item == 1) {
//                adapter = new GridAdapter(getActivity(), 0);
////                if (myValue < 50) {
////                    ((GridAdapter) adapter).setSelection(myValue);
////                }
//
//            } else {
//                adapter = new GridAdapter(getActivity(), 50);
////                if (myValue >= 50) {
////
////                    myValue = myValue / 2;
////                    ((GridAdapter) adapter).setSelection(myValue);
////                }
//            }
//            ((GridAdapter) adapter).setSelectedValue(selectedValue);
//
//            ((GridAdapter) adapter).setClickListener(new ItemClickListener() {
//                @Override
//                public void onItemClick(View view, int position, String value) {
//
//                    if (clickLisner != null) {
//                        clickLisner.itemClick(item, value);
//                    }
//
//                    ((GridAdapter) adapter).setSelectedValue(value);
//                    ((GridAdapter) adapter).notifyDataSetChanged();
//                }
//            });
//
//
//        } else

//            if (dialogType == ValuePickerDialog.PICKER_ENUM.VALUE_PICK) {

//        int numberOfColumns = 4;


        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));


//        if (item == 1) {
//            String[] list = getResources().getStringArray(R.array.value_1);
//            adapter = new ValueAdapter(getActivity(), list);
////                ((ValueAdapter) adapter).setSelection(0);
//
//
//        } else {
//            String[] list = getResources().getStringArray(R.array.value_2);
        adapter = new ValueAdapter(getActivity(), list);
//        }

        ((ValueAdapter) adapter).setSelectedValue(selectedValue);

        ((ValueAdapter) adapter).setClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String value) {

                if(!value.equalsIgnoreCase(ValuePickerDialog.disableValue)){
                    if (clickLisner != null) {
                        clickLisner.itemClick(item, value);
                    }

                    ((ValueAdapter) adapter).setSelectedValue(value);
                    adapter.notifyDataSetChanged();
                }


            }
        });


//        }


        recyclerView.setAdapter(adapter);


        return v;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }


    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }
}