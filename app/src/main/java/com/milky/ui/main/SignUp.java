package com.milky.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.milky.R;

public class SignUp extends AppCompatActivity {
private Button _signUp,_signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initResources();
        _signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this, MainActivity.class);
                startActivity(i);
            }
        });
        _signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
    private void initResources()
    {
        _signIn = (Button) findViewById(R.id.signin);
        _signUp = (Button) findViewById(R.id.signup);
    }

}
