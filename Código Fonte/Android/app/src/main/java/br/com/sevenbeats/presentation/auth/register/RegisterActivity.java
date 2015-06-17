package br.com.sevenbeats.presentation.auth.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.sevenbeats.R;
import br.com.sevenbeats.core.user.MockUser;
import br.com.sevenbeats.presentation.main.MainActivity;
import br.com.sevenbeats.utils.validate.FormValidateUtils;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {


    @InjectView(R.id.register_name) EditText name;
    @InjectView(R.id.register_toolbar) Toolbar mToolbar;
    @InjectView(R.id.register_username) EditText username;
    @InjectView(R.id.register_password) EditText password;
    @InjectView(R.id.register_retype_password) EditText retypePassword;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);
        }
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.register_submit) public void onRegisterClick(View view){
        if(isValidRegister()){
            new MockUser();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public boolean isValidRegister(){
        if(!isValidLenght(name.getText().toString())){
            Toast.makeText(this, "Nome deve ser maior que 5", Toast.LENGTH_SHORT).show();
        }else if(!isValidNameFormat(name.getText().toString())){
            Toast.makeText(this, "Nome invalido", Toast.LENGTH_SHORT).show();
        }else if(!isValidUsernameLenght(username.getText().toString())){
            Toast.makeText(this, "Username deve ser maior que 5", Toast.LENGTH_SHORT).show();
        }else if (!isValidNameFormat(username.getText().toString())) {
            Toast.makeText(this, "Username invalido", Toast.LENGTH_SHORT).show();
        }else if(!isValidPassword(password.getText().toString())){
            Toast.makeText(this, "Password invalido", Toast.LENGTH_SHORT).show();
        }else if(!isValidPassword(retypePassword.getText().toString())){
            Toast.makeText(this, "Password invalido", Toast.LENGTH_SHORT).show();
        }else if(!matchPassword(password.getText().toString(), retypePassword.getText().toString())){
            Toast.makeText(this, "As senhas devem combinar", Toast.LENGTH_SHORT).show();
        }else{
            return true;
        }

        return false;
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
        return (password.length() >= 6 && password.length() < 100);
    }

    public boolean matchPassword(String password1, String password2){
        return FormValidateUtils.matchStrings(password1,password2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return true;
    }
}
