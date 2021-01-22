package com.s.semanticmachinetest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.s.semanticmachinetest.view.Employees;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db_machine_test_app";
    public static final String TABLE_EMPLOYEE = "tb_employee";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String AGE = "age";
    public static final String DOB = "dob";
    public static final String PARENT_NAME = "parent_name";
    public static final String RELATION = "relation";


    public DataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTable = "CREATE TABLE " + TABLE_EMPLOYEE + "(" + ID + " INTEGER PRIMARY KEY, " + NAME + " TEXT, "
                + AGE + " TEXT," + DOB + " TEXT," + PARENT_NAME + " TEXT," + RELATION + " TEXT" + ")";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        onCreate(sqLiteDatabase);
    }


    public boolean addEmployee(Employees employees) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, employees.getName());
        values.put(AGE, employees.getAge());
        values.put(DOB, employees.getDob());
        values.put(PARENT_NAME, employees.getParent_name());
        values.put(RELATION, employees.getRelation());
        db.insert(TABLE_EMPLOYEE, null, values);
        db.close();
        return false;
    }


    public ArrayList<Employees> getEmployeeList() {
        ArrayList<Employees> employees = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEE + " ORDER BY " + ID + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                Employees employees1 = new Employees();
                employees1.setId(Integer.parseInt(cursor.getString(0)));
                employees1.setName(cursor.getString(1));
                employees1.setAge(cursor.getString(2));
                employees1.setDob(cursor.getString(3));
                employees1.setParent_name(cursor.getString(4));
                employees1.setRelation(cursor.getString(5));
                employees.add(employees1);
            } while (cursor.moveToNext());
        }
        db.close();
        return employees;
    }


    public int updateEmployee(Employees employees) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, employees.getName());
        values.put(AGE, employees.getAge());
        values.put(DOB, employees.getDob());
        values.put(PARENT_NAME, employees.getParent_name());
        values.put(RELATION, employees.getRelation());
        return db.update(TABLE_EMPLOYEE, values, ID + " = ?",
                new String[]{String.valueOf(employees.getId())});

    }

    public void deleteEmployee(Employees employees) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEE, ID + " = ?", new String[]{String.valueOf(employees.getId())});
        db.close();
    }
}
