package com.rivetlogic.mobile.liferaytodos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.rivetlogic.mobile.liferaytodoslibrary.TodosScreenlet;

public class TodosListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TextView emptyView;
    //other managers optional:
    // 1column, grid, dynamic: LinearLayoutManager, GridLayoutManager, StaggeredGridLayoutManager
    private LinearLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner dropdown = (Spinner) findViewById(R.id.time_filter_spinner);
        String[] items = new String[]{"Due Today", "Due Tomorrow", "Future", "Past Due"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            TodosScreenlet todosScreenlet = (TodosScreenlet) findViewById(R.id.liferay_todos);

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    todosScreenlet.loadDueTodayTasks();
                } else if (position == 1) {
                    todosScreenlet.loadDueTomorrowTasks();
                } else if (position == 2) {
                    todosScreenlet.loadFutureTasks();
                } else if (position == 3) {
                    todosScreenlet.loadPastDueTasks();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // no action taken
            }
        });

        emptyView = (TextView)findViewById(R.id.empty_view);
        emptyView.setVisibility(View.GONE);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

}
