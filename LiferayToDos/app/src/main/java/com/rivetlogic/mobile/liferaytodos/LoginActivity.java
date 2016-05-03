package com.rivetlogic.mobile.liferaytodos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.User;
import com.rivetlogic.mobile.liferaytodos.constants.TodosConstants;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    private TextView loginError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar listToolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(listToolbar);
        LoginScreenlet loginScreenlet = (LoginScreenlet) findViewById(R.id.login_todos);
        loginScreenlet.setListener(this);
        loginError = (TextView) findViewById(R.id.login_error_display);
        loginError.setVisibility(View.GONE);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        LiferayServerContext.setServer(prefs.getString(TodosConstants.LIFERAY_SERVER, TodosConstants.LOCAL_LIFERAY_SERVER_ADDRESS));
        LiferayServerContext.setCompanyId(new Long(prefs.getString(TodosConstants.COMPANY_ID, TodosConstants.LOCAL_LIFERAY_COMPANY_ID)));
    }

    @Override
    public void onLoginSuccess(User user) {
        loginError.setVisibility(View.GONE);
        startActivity(new Intent(this, TodosListActivity.class));
    }

    @Override
    public void onLoginFailure(Exception e) {
        loginError.setVisibility(View.VISIBLE);
        loginError.setText(e.getMessage());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
