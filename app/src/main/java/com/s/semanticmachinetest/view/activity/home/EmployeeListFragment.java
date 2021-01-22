package com.s.semanticmachinetest.view.activity.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.s.semanticmachinetest.R;
import com.s.semanticmachinetest.db.DataBase;
import com.s.semanticmachinetest.view.Employees;
import com.s.semanticmachinetest.view.OnTab;
import com.s.semanticmachinetest.view.activity.employee.AddEmployeeActivity;
import com.s.semanticmachinetest.view.activity.employee.EmployeeListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EmployeeListFragment extends Fragment implements OnTab, SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.mTextViewDataNotFount)
    TextView mTextViewDataNotFount;
    private DataBase dataBase;
    private Toolbar toolbar;
    private Toolbar toolbar_animation;
    private SearchView searchView;
    ArrayList<Employees> employees;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee_list, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar_animation = getActivity().findViewById(R.id.toolbar_animation);
        setHasOptionsMenu(true);
        dataBase = new DataBase(getContext());
        ((HomeActivity) getActivity()).setToolbarTitle(getString(R.string.employee));
        swipeRefreshLayout.setOnRefreshListener(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getEmployeeList();

    }

    private void getEmployeeList() {
         employees = dataBase.getEmployeeList();
        bindEmployeeList(employees);
    }

    private void bindEmployeeList(ArrayList<Employees> employees) {
        if (employees.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            mTextViewDataNotFount.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            mTextViewDataNotFount.setVisibility(View.GONE);
        }
        EmployeeListAdapter adapter = new EmployeeListAdapter(getContext(), employees, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.employee_screen_menu, menu);


        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final MenuItem add = menu.findItem(R.id.action_add);
        if (searchItem != null) {
            toolbar_animation.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    collapse(getContext(), toolbar, toolbar_animation);
                }
            });

            searchView = (SearchView) searchItem.getActionView();
            searchView.setFitsSystemWindows(true);
            searchView.setOnQueryTextListener(this);
            searchView.setQueryHint(getString(R.string.search));

            EditText txtSearch = ((EditText) searchView.findViewById(R.id.search_src_text));
            txtSearch.setHint(getString(R.string.search));
            txtSearch.setHintTextColor(Color.DKGRAY);
            txtSearch.setBackgroundColor(getResources().getColor(R.color.white));
            txtSearch.setTextColor(getResources().getColor(R.color.color_333333));


            View v = searchView.findViewById(R.id.search_plate);
            v.setBackgroundColor(getResources().getColor(R.color.white));

            // traverse the view to the widget containing the hint text
            LinearLayout ll = (LinearLayout) searchView.getChildAt(0);
            LinearLayout ll2 = (LinearLayout) ll.getChildAt(2);
            LinearLayout ll3 = (LinearLayout) ll2.getChildAt(1);
            SearchView.SearchAutoComplete autoComplete = (SearchView.SearchAutoComplete) ll3.getChildAt(0);

            View searchplate = (View) searchView.findViewById(R.id.search_edit_frame);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                searchplate.setBackgroundColor(getResources().getColor(R.color.white));
            }


            searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem menuItem) {
                    add.setVisible(false);
                    toolbar.setBackground(new ColorDrawable(getContext().getResources().getColor(R.color.white)));
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                    add.setVisible(true);
                    toolbar.setBackground(new ColorDrawable(getContext().getResources().getColor(R.color.colorPrimaryDark)));
                    return true;
                }
            });
            // set the hint text color
            autoComplete.setHintTextColor(getResources().getColor(R.color.color_333333));
            // set the text color
            autoComplete.setTextColor(getResources().getColor(R.color.color_333333));

            ImageView searchMagIcon = (ImageView) searchView.findViewById(R.id.search_button);
            searchMagIcon.setImageResource(R.drawable.ic_baseline_search_24);

            ImageView closeIcon = searchView.findViewById(R.id.search_close_btn);
            closeIcon.setImageResource(R.drawable.ic_close_black_24dp);
            setImageViewTinColor(getContext(), closeIcon, R.color.color_333333);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        }
        super.onCreateOptionsMenu(menu, inflater);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                startActivity(new Intent(getContext(), AddEmployeeActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDelete(Object object) {
        Employees employees = (Employees) object;

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getString(R.string.alert_message, employees.getName()));
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Employees employees1 = new Employees();
                employees1.setId(employees.getId());
                dataBase.deleteEmployee(employees1);
                dialogInterface.dismiss();
                getEmployeeList();
            }
        }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create();
        builder.show();


    }

    @Override
    public void onEdit(Object object) {
        Employees employees = (Employees) object;
        Intent intent = new Intent(getContext(), AddEmployeeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.key_value), employees);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        getEmployeeList();
    }

    public static void collapse(Context context, Toolbar toolbar, Toolbar toolbarAnimation) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circleReveal(context, toolbar, toolbarAnimation, false);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void circleReveal(Context context, Toolbar toolbar, final Toolbar toolbarAnimation, final boolean isShow) {

        int width = toolbar.getWidth();
        width -= (context.getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) / 2);

        int cx = width;
        int cy = toolbar.getHeight() / 2;

        Animator anim;
        if (isShow)
            anim = ViewAnimationUtils.createCircularReveal(toolbar, cx, cy, 0, (float) width);
        else
            anim = ViewAnimationUtils.createCircularReveal(toolbar, cx, cy, (float) width, 0);

        anim.setDuration((long) 220);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isShow) {
                    toolbarAnimation.setVisibility(View.GONE);
                    super.onAnimationEnd(animation);
                }
            }
        });

//        // make the view visible and start the animation
        if (isShow) {
            toolbar.setVisibility(View.VISIBLE);
        }

        // start the animation
        anim.start();
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (employees!=null) {
            if (newText.equals("")) {
                bindEmployeeList(employees);
            } else {
                ArrayList<Employees> tempList = new ArrayList<>();
                for (int i = 0; i < employees.size(); i++) {
                    if (employees.get(i).getName().toLowerCase().contains((newText).toLowerCase())) {
                        tempList.add(employees.get(i));
                    }
                }
                bindEmployeeList(tempList);
            }
        }
        return false;
    }

    public static void setImageViewTinColor(Context context, ImageView imageView, int color) {
        imageView.setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.MULTIPLY);
    }
}