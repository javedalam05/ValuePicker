# ValuePicker

Create a simple Value Picker according to your theme using this Custom ValuePicker with your String Array (String[]).

![alt text](https://github.com/javedalam05/ValuePicker/blob/master/device-2018-01-24-220556.png)
![alt text](https://github.com/javedalam05/ValuePicker/blob/master/device-2018-01-24-220759.png)
![alt text](https://github.com/javedalam05/ValuePicker/blob/master/device-2018-01-24-220857.png)


###	How to use ValuePicker:

Simply Call following method according to your requirement

```
String[] list = getResources().getStringArray(R.array.values);       
//array for picker 
//(Simply Create a string value array and pass it with specified parameter)

ValuePicker.openValuePicker(activity,
                dialogTitle,         // dialog title
                3,                   //number of columns in a row
                15,                  // number of items per page
                list,                // values array
                selectedNumber,      //selected value for making selection
                
                new ValuePickerDialog.GetCounter() {
                    @Override
                    public void counterValue(DialogFragment dialog, String index) {
                        selectedNumber = index;
                        tv_num_value.setText(selectedNumber);
                        dialog.dismiss();

                    }

                    @Override
                    public void dismissMethod() {       //called when dialog dismissed
                    }

                    @Override
                    public void onBack() {              //called when device back button pressed
                    }
                });
 ```

If you want to change color theme of picker, simply change color code of colorPrimary in color.xml resource file, as mention below:

```
<color name="colorPrimary">#3F51B5</color>
```
