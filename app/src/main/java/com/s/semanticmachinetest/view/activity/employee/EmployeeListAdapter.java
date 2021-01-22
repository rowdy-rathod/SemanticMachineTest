package com.s.semanticmachinetest.view.activity.employee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.s.semanticmachinetest.R;
import com.s.semanticmachinetest.view.Employees;
import com.s.semanticmachinetest.view.OnTab;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.Holder> {
    Context mContext;
    ArrayList<Employees> employees;
    OnTab onTab;


    public EmployeeListAdapter(Context mContext, ArrayList<Employees> employees, OnTab onTab) {
        this.mContext = mContext;
        this.employees = employees;
        this.onTab = onTab;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_item_view, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Employees emp = employees.get(position);
        holder.mTextViewAge.setText(emp.getAge());
        holder.mTextViewBirth.setText(emp.getDob());
        holder.mTextViewName.setText(emp.getName());
        holder.mTextViewParentName.setText(emp.getParent_name());
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }


    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.mTextViewName)
        TextView mTextViewName;
        @BindView(R.id.imgEdit)
        ImageView imgEdit;
        @BindView(R.id.imgDelete)
        ImageView imgDelete;
        @BindView(R.id.layoutNameContainer)
        LinearLayout layoutNameContainer;
        @BindView(R.id.mTextViewBirth)
        TextView mTextViewBirth;
        @BindView(R.id.mTextViewAge)
        TextView mTextViewAge;
        @BindView(R.id.mTextViewParentName)
        TextView mTextViewParentName;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.imgEdit, R.id.imgDelete})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.imgEdit:
                    onTab.onEdit(employees.get(getAdapterPosition()));
                    break;
                case R.id.imgDelete:
                    onTab.onDelete(employees.get(getAdapterPosition()));
                    break;
            }
        }
    }
}
