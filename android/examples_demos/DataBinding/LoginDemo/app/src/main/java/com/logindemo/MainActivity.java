package com.logindemo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.logindemo.Handler.ClickHandler;
import com.logindemo.Model.User;
import com.logindemo.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements ClickHandler {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        binding.setUser(new User());
        binding.setHandler(this);
    }

    @Override
    public void onClickLogin(View view) {
        User user=binding.getUser();
        if(validate(user))
        {
            Toast.makeText(MainActivity.this,"Hello " + user.userName.get() + " your password is : " + user.password.get(),Toast.LENGTH_SHORT).show();
        }
    }
    private boolean validate(User user)
    {
        if(user.userName.get() == null || user.userName.get().trim().length()<=0)
        {
            Toast.makeText(MainActivity.this,"Please enter username.",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(user.password.get() == null || user.password.get().trim().length()<=0)
        {
            Toast.makeText(MainActivity.this,"Please enter password.",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
