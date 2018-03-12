package com.picker;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.value.picker.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValuePickerDialog extends DialogFragment implements FirstFragment.ClickLisner, View.OnClickListener {


    List<FirstFragment> fragments;
    PagerAdapter ama;

    RelativeLayout btn_close;

    GetCounter getCounter;

    public void setIndexValue(String indexValue) {
        this.indexValue = indexValue;
    }

    String indexValue = "5k";
    final static String disableValue = "$NA$";

    String title = "";

    int selectedPage = 0;

    ViewPager vpPager;

    ImageButton btn_left, btn_right;


    int numberOfColumns = 4;


    int itemCountPerPage = 8;

    int numberOfPages = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_done:

                if (getCounter != null) {
                    getCounter.counterValue(ValuePickerDialog.this, indexValue);
                }

                break;

            case R.id.btn_left:
                prevousPage();
                break;

            case R.id.btn_right:
                nextPage();
                break;
        }

    }


    public interface GetCounter {
        void counterValue(DialogFragment dialog, String index);

        void dismissMethod();

        void onBack();
    }

    public void setGetCounter(GetCounter getCounter) {
        this.getCounter = getCounter;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.value_picker_dialog, container);

        title = getArguments().getString("title");
        String[] list = getArguments().getStringArray("valueArray");

        btn_close = (RelativeLayout) view.findViewById(R.id.btn_close);
        TextView textView = (TextView) view.findViewById(R.id.txt_title);
        textView.setText(title);


        vpPager = (ViewPager) view.findViewById(R.id.viewpager);
        btn_left = (ImageButton) view.findViewById(R.id.btn_left);
        btn_right = (ImageButton) view.findViewById(R.id.btn_right);


        numberOfPages = (int) Math.ceil((double) list.length / itemCountPerPage);


        int listLength = list.length;
        int totalItem = itemCountPerPage * numberOfPages;
        int remainingItem = totalItem - listLength;
        ArrayList<String> valueList = new ArrayList<String>(Arrays.asList(list));
        for (int i = 0;i<remainingItem;i++){
            valueList.add(disableValue);
        }

        String[] mlist = new String[valueList.size()];
        mlist = valueList.toArray(mlist);


        String[][] valuesArray = splitBytes(mlist, itemCountPerPage);


        for (int i = 0; i < valuesArray.length; i++) {
            for (int j = 0; j < valuesArray[i].length; j++) {
                if(indexValue.contains(valuesArray[i][j])){
                    selectedPage = i;
                    break;
                }
            }
        }


        fragments = getFragments(valuesArray);
        ama = new PagerAdapter(getChildFragmentManager(), fragments);
        vpPager.setAdapter(ama);


        if ((selectedPage == 0) && ((numberOfPages - 1) == 0)) {
            btn_left.setVisibility(View.GONE);
            btn_right.setVisibility(View.GONE);
        }
        vpPager.setCurrentItem(selectedPage);

        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedPage = position;
                forDisableButton();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        view.findViewById(R.id.btn_done).setOnClickListener(this);
        view.findViewById(R.id.btn_left).setOnClickListener(this);
        view.findViewById(R.id.btn_right).setOnClickListener(this);


        getDialog().setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getCounter.onBack();
                    dismiss();
                }
                return true;
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });





        forDisableButton();


        ViewTreeObserver vto = vpPager.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                vpPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                // int width = vpPager.getMeasuredWidth();
                int height = vpPager.getMeasuredHeight();


                ViewGroup.LayoutParams btnLeftParams = btn_left.getLayoutParams();
                btnLeftParams.height = height;
                btn_left.setLayoutParams(btnLeftParams);

                ViewGroup.LayoutParams btnRightParams = btn_right.getLayoutParams();
                btnRightParams.height = height;
                btn_right.setLayoutParams(btnRightParams);


            }
        });


        return view;
    }


    @Override
    public void itemClick(int item, String countIndex) {
        for (FirstFragment f : fragments) {
            if (f.adapter instanceof ValueAdapter) {
//                ((ValueAdapter) f.adapter).setSelection(-1);
                ((ValueAdapter) f.adapter).setSelectedValue("-1");
                f.adapter.notifyDataSetChanged();
            }


        }

        //UtilityFunctions.handleToast(getActivity(), "called-->" + countIndex);
        indexValue = countIndex;
    }


//    private List<FirstFragment> getFragments() {
//
////        List fList = new ArrayList();
////        {
////            //FirstFragment f = FirstFragment.newInstance("Fragment 1", 1, indexValue, dialogType);
////            FirstFragment f = FirstFragment.newInstance("Fragment 1", 1, indexValue);
////            f.setClickLisner(this);
////            fList.add(f);
////        }
////
////        {
////            FirstFragment f = FirstFragment.newInstance("Fragment 2", 2, indexValue);
//////            FirstFragment f = FirstFragment.newInstance("Fragment 2", 2, indexValue, dialogType);
////            f.setClickLisner(this);
////            fList.add(f);
////        }
//
//
//
//
//
//        //fList.add(FirstFragment.newInstance("Fragment 2", 2));
////        fList.add(FragmentAcoesMusculares.newInstance("Fragment 3", 3));
//        return fList;
//    }


    public String[][] splitBytes(final String[] data, final int chunkSize) {
        final int length = data.length;
        final String[][] dest = new String[(length + chunkSize - 1) / chunkSize][];
        int destIndex = 0;
        int stopIndex = 0;

        for (int startIndex = 0; startIndex + chunkSize <= length; startIndex += chunkSize) {
            stopIndex += chunkSize;
            dest[destIndex++] = Arrays.copyOfRange(data, startIndex, stopIndex);
        }

        if (stopIndex < length) {
            dest[destIndex] = Arrays.copyOfRange(data, stopIndex, length);
        }


//        if(){
//
//        }


        return dest;
    }


    private List<FirstFragment> getFragments(String[][] valuesArray) {

        List fList = new ArrayList();


        for (int i = 0; i < numberOfPages; i++) {
            FirstFragment f = FirstFragment.newInstance("Fragment " + (i + 1), (i + 1), indexValue, valuesArray[i]);
            f.setClickLisner(this);
//            f.setStartNumber(startNumber);
            f.setNumberOfColumns(numberOfColumns);
//            if (i == numberOfPages - 1) {
//                f.setItemCount(lastPageItem);
//            } else {
//                f.setItemCount(itemCountPerPage);
//            }

//            f.setItemCountPerPage(itemCountPerPage);
            fList.add(f);
        }

        return fList;
    }


    @Override
    public void dismiss() {
        super.dismiss();
//        dismiss();
        if (getCounter != null) {
            getCounter.dismissMethod();
        }
    }


    void nextPage() {
        if (selectedPage < numberOfPages) {
            selectedPage++;
            btn_left.setEnabled(true);
            btn_left.setAlpha(1.0f);
        }

        forDisableButton();
        vpPager.setCurrentItem(selectedPage);

    }


    void forDisableButton() {
        if (selectedPage >= (numberOfPages - 1)) {
            selectedPage = (numberOfPages - 1);
            btn_right.setEnabled(false);
            btn_right.setAlpha(0.3f);

            btn_left.setEnabled(true);
            btn_left.setAlpha(1.0f);
        }

        if (selectedPage <= 0) {
            selectedPage = 0;
            btn_left.setEnabled(false);
            btn_left.setAlpha(0.3f);


        }


        if (selectedPage < (numberOfPages - 1)) {
            btn_right.setEnabled(true);
            btn_right.setAlpha(1.0f);
        }

        if (selectedPage > 0) {
            btn_left.setEnabled(true);
            btn_left.setAlpha(1.0f);
        }

    }

    void prevousPage() {
        if (selectedPage > 0) {
            selectedPage--;
            btn_right.setEnabled(true);
            btn_right.setAlpha(1.0f);
        }

        forDisableButton();
        vpPager.setCurrentItem(selectedPage);

    }



    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }


    public void setItemCountPerPage(int itemCountPerPage) {
        this.itemCountPerPage = itemCountPerPage;
    }


}