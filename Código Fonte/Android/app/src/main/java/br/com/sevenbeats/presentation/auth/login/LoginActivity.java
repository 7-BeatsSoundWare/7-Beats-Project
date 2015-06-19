package br.com.sevenbeats.presentation.auth.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import br.com.sevenbeats.R;
import br.com.sevenbeats.core.user.MockUser;
import br.com.sevenbeats.presentation.auth.register.RegisterActivity;
import br.com.sevenbeats.presentation.main.MainActivity;
import br.com.sevenbeats.utils.annotation.Reflection;
import br.com.sevenbeats.utils.validate.FormValidateUtils;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @InjectView(R.id.login_username) EditText userName;
    @InjectView(R.id.login_password) EditText password;
    @InjectView(R.id.login_loading) ProgressBar loading;
    @InjectView(R.id.login_container) View loginContainer;
    @InjectView(R.id.login_toolbar) Toolbar mToolbar;

    public LoginController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null) {
            actionBar.setTitle("Welcome to SevenBeats");
        }

        mController = new LoginController(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.login_log_in_btn) public void login(View view){
        MockUser user = new MockUser();
        user.setUsername(userName.getText().toString());
        user.setPassword(password.getText().toString());
        loading.setVisibility(View.VISIBLE);
        loginContainer.setVisibility(View.INVISIBLE);
        mController.onRequest(LoginConstants.METHOD_ON_LOGIN, user);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.login_register_btn) public void register(View view){
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @SuppressWarnings("unused")
    @Reflection("onAuth") public void onAuth(Boolean autenticado){
        if(autenticado) {
            this.finish();
            startActivity(new Intent(this, MainActivity.class));
        }else{
            loading.setVisibility(View.VISIBLE);
            loginContainer.setVisibility(View.INVISIBLE);
        }
    }


    public boolean isValidLenght(String name){
        return name.length() >= 6 && name.length() <= 45;
    }

    public boolean isValidUsernameLenght(String name){
        return name.length() >= 6 && name.length() <= 25;
    }

    public boolean isValidNameFormat(String name){
        return  (!FormValidateUtils.containsSpecial(name) && !FormValidateUtils.startsWithNumber(name));
    }

    public boolean isValidPassword(String password){
        return (password.length() >= 6 && password.length() < 45);
    }

    public boolean matchPassword(String password1, String password2){
        return FormValidateUtils.matchStrings(password1,password2);
    }

}
