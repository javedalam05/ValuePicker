package com.value.picker;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.picker.ValuePicker;
import com.picker.ValuePickerDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.tv_num_value)
    TextView tv_num_value;

    String dialogTitle = "Value Picker";
    AppCompatActivity activity;
    String selectedNumber;
    String[] list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        activity = this;
//        list = getResources().getStringArray(R.array.alphabets);  //array for picker
        list = getResources().getStringArray(R.array.values);       //array for picker
        selectedNumber = list[0]; //default selection
    }

    @OnClick(R.id.btn_value_pick)
    public void btnClick() {


        ValuePicker.openValuePicker(activity,
                dialogTitle,         // dialog title
                3,                  //number of columns in a row
                15,                 // number of items per page
                list,               // values array
                selectedNumber,     //selected value for making selection
                new ValuePickerDialog.GetCounter() {
                    @Override
                    public void counterValue(DialogFragment dialog, String index) {
                        selectedNumber = index;
                        tv_num_value.setText(selectedNumber);
                        dialog.dismiss();

                    }

                    @Override
                    public void dismissMethod() {
                    }

                    @Override
                    public void onBack() {

                    }
                });

    }
}
