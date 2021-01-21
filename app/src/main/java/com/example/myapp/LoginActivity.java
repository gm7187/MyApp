package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText userLogId, passwrdId;
    Button loginBtn ,regBtn;

    //Firebase
    FirebaseAuth auth;
    FirebaseUser firebaseUsers;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUsers =FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        userLogId = findViewById(R.id.userid2);
        passwrdId = findViewById(R.id.Passwordid2);
        loginBtn = findViewById(R.id.loginbutton);
        regBtn = findViewById(R.id.button2);

        //firebase Auth

        auth = FirebaseAuth.getInstance();





        // Reg Btn
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, regActivity.class);
                startActivity(i);
            }
        });

        //Login BTn

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_text = userLogId.getText().toString();
                String pass_txt = passwrdId.getText().toString();


         //Checking if it is empty

                if(TextUtils.isEmpty(email_text) || TextUtils.isEmpty(pass_txt)){
                    Toast.makeText( LoginActivity.this, "please fill all folder", Toast.LENGTH_SHORT).show();
                }else{
                    auth.signInWithEmailAndPassword(email_text, pass_txt)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(LoginActivity.this, "login Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }



            }
        });
    }
}