package com.s.semanticmachinetest.view.activity.employee;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.s.semanticmachinetest.R;
import com.s.semanticmachinetest.db.DataBase;
import com.s.semanticmachinetest.view.Employees;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class AddEmployeeActivity extends AppCompatActivity {
    @BindView(R.id.radioFather)
    RadioButton radioFather;
    @BindView(R.id.radioGroup1)
    RadioGroup radioGroup1;
    private int mYear, mMonth, mDay, mHour, mMinute;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mEditName)
    TextInputEditText mEditName;
    @BindView(R.id.mEditDOB)
    TextInputEditText mEditDOB;
    @BindView(R.id.mEditAge)
    TextInputEditText mEditAge;
    @BindView(R.id.mEditParentName)
    TextInputEditText mEditParentName;
    @BindView(R.id.radioSpouse)
    RadioButton radioSpouse;
    @BindView(R.id.radioSon)
    RadioButton radioSon;
    @BindView(R.id.radioDoughter)
    RadioButton radioDoughter;
    @BindView(R.id.radioMother)
    RadioButton radioMother;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.mButtonNext)
    Button mButtonNext;
    private int inputType = 0;
    DataBase dataBase;
    Employees employees;
    private int updateCallBack;
    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dataBase = new DataBase(this);
        myCalendar = Calendar.getInstance();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioSpouse:
                        inputType = 1;
                        break;
                    case R.id.radioSon:
                        inputType = 2;
                        break;
                    case R.id.radioDoughter:
                        inputType = 3;
                        break;
                }
            }
        });
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioFather:
                        inputType = 4;
                        break;
                    case R.id.radioMother:
                        inputType = 5;
                        break;
                }
            }
        });
        setToolbarTitle(getString(R.string.add_emp));

        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                updateCallBack = 1;
                employees = (Employees) bundle.getSerializable(getString(R.string.key_value));
                mEditAge.setText(employees.getAge());
                mEditDOB.setText(employees.getDob());
                mEditName.setText(employees.getName());
                mEditParentName.setText(employees.getParent_name());

                if (employees.getRelation().equals("1")) {
                    radioSpouse.setChecked(true);
                    radioSon.setChecked(false);
                    radioDoughter.setChecked(false);
                    radioMother.setChecked(false);
                    radioFather.setChecked(false);

                }
                if (employees.getRelation().equals("2")) {
                    radioSpouse.setChecked(false);
                    radioSon.setChecked(true);
                    radioDoughter.setChecked(false);
                    radioMother.setChecked(false);
                    radioFather.setChecked(false);
                }
                if (employees.getRelation().equals("3")) {
                    radioSpouse.setChecked(false);
                    radioSon.setChecked(false);
                    radioDoughter.setChecked(true);
                    radioMother.setChecked(false);
                    radioFather.setChecked(false);
                }
                if (employees.getRelation().equals("4")) {
                    radioSpouse.setChecked(false);
                    radioSon.setChecked(false);
                    radioDoughter.setChecked(false);
                    radioMother.setChecked(false);
                    radioFather.setChecked(true);
                }
                if (employees.getRelation().equals("5")) {
                    radioSpouse.setChecked(false);
                    radioSon.setChecked(false);
                    radioDoughter.setChecked(false);
                    radioMother.setChecked(true);
                }
                setToolbarTitle(getString(R.string.update_employee));
                mButtonNext.setText(getString(R.string.update));
            }
        }
    }

    @OnClick(R.id.mButtonNext)
    public void onViewClicked() {
        String name = mEditName.getText().toString().trim();
        String dateOfBirth = mEditDOB.getText().toString().trim();
        String age = mEditAge.getText().toString().trim();
        String parentName = mEditParentName.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, getString(R.string.enter_name), Toast.LENGTH_SHORT).show();
        } else if (dateOfBirth.isEmpty()) {
            Toast.makeText(this, getString(R.string.enter_dob), Toast.LENGTH_SHORT).show();
        } else if (age.isEmpty()) {
            Toast.makeText(this, getString(R.string.enter_age), Toast.LENGTH_SHORT).show();
        } else if (parentName.isEmpty()) {
            Toast.makeText(this, getString(R.string.enter_parent_name), Toast.LENGTH_SHORT).show();
        } else if (inputType == 0) {
            Toast.makeText(this, getString(R.string.select_parent_relation), Toast.LENGTH_SHORT).show();
        } else {

            Employees emp = new Employees();
            emp.setParent_name(parentName);
            emp.setRelation(String.valueOf(inputType));
            emp.setDob(dateOfBirth);
            emp.setAge(age);
            emp.setName(name);

            if (updateCallBack == 1) {
                emp.setId(employees.getId());
                dataBase.updateEmployee(emp);
                Toast.makeText(this, getString(R.string.employee_updated_successfully), Toast.LENGTH_SHORT).show();

            } else {
                dataBase.addEmployee(emp);
                Toast.makeText(this, getString(R.string.employee_added_successfully), Toast.LENGTH_SHORT).show();
            }
            onBackPressed();
        }
    }

    public void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnTouch({R.id.mEditDOB})
    public boolean setOnDateTouchEdiTextView(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP)
            switch (v.getId()) {
                case R.id.mEditDOB:
                    pickDate();
                    break;
            }

        return false;
    }


    public void pickDate() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(AddEmployeeActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String fromDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        mEditDOB.setText(fromDate);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

}