package com.rivetlogic.mobile.liferaytodos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.liferay.mobile.screens.context.SessionContext;
import com.rivetlogic.mobile.liferaytodoslibrary.TodosScreenlet;

public class TodosListActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private RecyclerView mRecyclerView;
    private TextView emptyView;
    private NavigationView navigationView;
    //other managers optional:
    // 1column, grid, dynamic: LinearLayoutManager, GridLayoutManager, StaggeredGridLayoutManager
    private LinearLayoutManager mLayoutManager;
    private AppCompatActivity currentActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        currentActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos_list);

        Toolbar listToolbar = (Toolbar) findViewById(R.id.list_toolbar);
        setSupportActionBar(listToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setupToolbarDropdown();

        navigationView = (NavigationView)findViewById(R.id.navigation);
        setupNavigationView(listToolbar);

        emptyView = (TextView)findViewById(R.id.empty_view);
        emptyView.setVisibility(View.GONE);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void setupToolbarDropdown() {
        Spinner dropdown = (Spinner) findViewById(R.id.time_filter_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.spinner_filter_items_days,
                R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
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
    }

    @Override
    /**
     * eliminate the back action, to go back to the login must be done using the logout option
     */
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    private void setupNavigationView(Toolbar toolbar) {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()){
                    case R.id.navigation_list:
                        //just go back
                        return true;
                    //TODO:
//                    case R.id.navigation_create:
//                        Toast.makeText(getApplicationContext(),"Create Selected",Toast.LENGTH_SHORT).show();
//                        return true;
                    case R.id.navigation_logout:
                        Toast.makeText(getApplicationContext(),"Logging out",Toast.LENGTH_SHORT).show();
                        SessionContext.clearSession();
                        startActivity(new Intent(currentActivity, LoginActivity.class));
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Not a Valid Menu Option",Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.openDrawer, R.string.closeDrawer) ;
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

}
