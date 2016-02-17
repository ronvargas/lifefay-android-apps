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
import android.widget.EditText;

import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.User;
import com.rivetlogic.mobile.liferaytodos.constants.TodosConstants;

public class LoginActivity extends AppCompatActivity implements LoginListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        LoginScreenlet loginScreenlet = (LoginScreenlet) findViewById(R.id.login_todos);
        loginScreenlet.setListener(this);

        // TODO: temp to speedup testing
        EditText loginUsername = (EditText) findViewById(R.id.liferay_login);
        EditText loginPass = (EditText) findViewById(R.id.liferay_password);
        loginUsername.setText("test@liferay.com");
        loginPass.setText("test");
        // TODO: end of testing section !!!!

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        LiferayServerContext.setServer(prefs.getString(TodosConstants.LIFERAY_SERVER, TodosConstants.LOCAL_LIFERAY_SERVER_ADDRESS));
        LiferayServerContext.setCompanyId(new Long(prefs.getString(TodosConstants.COMPANY_ID, TodosConstants.LOCAL_LIFERAY_COMPANY_ID)));

    }

    @Override
    public void onLoginSuccess(User user) {
        startActivity(new Intent(this, TodosListActivity.class));
    }

    @Override
    public void onLoginFailure(Exception e) {

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
