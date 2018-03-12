package com.picker;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

/**
 * Created by jalam on 22-01-2018.
 */

public class ValuePicker {


    public static void openValuePicker(
            Activity activity,  //activity instance
            String title,       // title of the dialog
            int noOfColumn,     //number of columns in a value grid
            int itemPerPage,    // number of item in a page
            String[] list,      //list of values
            String selectedNumber,  // default selected value when value picker opened
            ValuePickerDialog.GetCounter getCounter //lisner for value get
    ) {

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putStringArray("valueArray", list);
        ValuePickerDialog dialogFragmentWindow = new ValuePickerDialog();
        if (TextUtils.isEmpty(selectedNumber)) {
            selectedNumber = "A";
        }
        dialogFragmentWindow.setIndexValue(selectedNumber);
        dialogFragmentWindow.setArguments(args);
        dialogFragmentWindow.setNumberOfColumns(noOfColumn);
        dialogFragmentWindow.setItemCountPerPage(itemPerPage);

        dialogFragmentWindow.setGetCounter(getCounter);
        dialogFragmentWindow.show(((AppCompatActivity) activity).getSupportFragmentManager(), "");
    }


}
