package com.s.semanticmachinetest.view.activity.home;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.s.semanticmachinetest.R;
import com.s.semanticmachinetest.view.activity.employee.AddEmployeeActivity;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;


public class HomeFragment extends Fragment {
    private int mYear, mMonth, mDay, mHour, mMinute;

    @BindView(R.id.mTextViewName)
    TextView mTextViewName;
    @BindView(R.id.mEditDateOfBirth)
    EditText mEditDateOfBirth;
    @BindView(R.id.mLayoutBirth)
    LinearLayout mLayoutBirth;
    @BindView(R.id.mEditAge)
    EditText mEditAge;
    @BindView(R.id.mLayoutAge)
    LinearLayout mLayoutAge;
    @BindView(R.id.mTextViewBirth)
    TextView mTextViewBirth;
    @BindView(R.id.mLayoutDepartment)
    LinearLayout mLayoutDepartment;
    @BindView(R.id.mLayoutDesignation)
    LinearLayout mLayoutDesignation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        ((HomeActivity) getActivity()).setToolbarTitle(getString(R.string.home));

        super.onViewCreated(view, savedInstanceState);
    }

    @OnTouch({R.id.mEditDateOfBirth})
    public boolean setOnDateTouchEdiTextView(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP)
            switch (v.getId()) {
                case R.id.mEditDateOfBirth:
                    mEditDateOfBirth.requestFocus();
                    mEditDateOfBirth.setCursorVisible(true);
                    pickDate();
                    break;
                case R.id.mEditAge:
                    mEditAge.requestFocus();
                    mEditAge.setCursorVisible(true);
                    break;
            }

        return false;
    }

    public void pickDate() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String fromDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        mEditDateOfBirth.setText(fromDate);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}