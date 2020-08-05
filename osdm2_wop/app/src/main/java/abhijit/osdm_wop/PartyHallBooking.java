package abhijit.osdm_wop;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class PartyHallBooking extends AppCompatActivity implements View.OnClickListener {
    Button btnDatePickerfrom,btnDatePickerto, btnTimePickerfrom,btnTimePickerto,submit_button;
    EditText txtDateFrom,txtDateTo, txtTimeFrom,txtTimeTo,detail_txt;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_hall_booking);

        btnDatePickerfrom=(Button)findViewById(R.id.btn_date);
        btnDatePickerto=(Button)findViewById(R.id.btn_date_to);
        btnTimePickerfrom=(Button)findViewById(R.id.btn_time);
        btnTimePickerto=(Button)findViewById(R.id.btn_time_to);
        submit_button=(Button)findViewById(R.id.btn_submit);

        txtDateFrom=(EditText)findViewById(R.id.in_date);
        txtDateTo=(EditText)findViewById(R.id.in_date_to);
        txtTimeFrom=(EditText)findViewById(R.id.in_time);
        txtTimeTo=(EditText)findViewById(R.id.in_time_to);
        detail_txt=(EditText)findViewById(R.id.details);



        btnDatePickerfrom.setOnClickListener(this);
        btnDatePickerto.setOnClickListener(this);
        btnTimePickerfrom.setOnClickListener(this);
        btnTimePickerto.setOnClickListener(this);
        submit_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePickerfrom) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDateFrom.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if (v == btnDatePickerto) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDateTo.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePickerfrom) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTimeFrom.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if (v == btnTimePickerto) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTimeTo.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        if(v == submit_button)
        {
            String from_date=txtDateFrom.getText().toString();
            String from_time=txtTimeFrom.getText().toString();
            String to_date=txtDateTo.getText().toString();
            String to_time=txtTimeTo.getText().toString();
            String purpose=detail_txt.getText().toString();
            System.out.println("Submit button pressed"+from_date+" "+from_time+" "+to_date+" "+to_time+" "+purpose);
        }
    }
}
