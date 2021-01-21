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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class regActivity extends AppCompatActivity {

    // Wait
    EditText userId, passEt, emailLEt;
    Button regBtn;

    FirebaseAuth auth;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        // Initialization
        userId = findViewById(R.id.userid2);
        passEt = findViewById(R.id.Passwordid2);
        emailLEt = findViewById(R.id.emailtxt);
        regBtn = findViewById(R.id.btnlog);

        auth = FirebaseAuth.getInstance();
        // Adding Event
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_text = userId.getText().toString();
                String email_text =emailLEt.getText().toString();
                String pass_txt = passEt.getText().toString();

                if(TextUtils.isEmpty(username_text) || TextUtils.isEmpty(email_text) || TextUtils.isEmpty(pass_txt)){
                    Toast.makeText(regActivity.this, "please fill all folder", Toast.LENGTH_SHORT).show();
                }else{
                    regNow(username_text, email_text, pass_txt);
                }
            }
        });

    }
    private void regNow(final String username, String email, String password){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userId = firebaseUser.getUid();
                            myRef = FirebaseDatabase.getInstance().getReference("MyUser")
                                    .child(userId);

                            // HashMap
                            HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put("id", userId);
                            hashMap.put("username", username);
                            hashMap.put("imageURL", "default");

                            // Opening the main
                            myRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent i = new Intent(regActivity.this, MainActivity.class);
                                        startActivity(i);
                                        finishAffinity();
                                    }
                                }
                            });

                        }else{
                            Toast.makeText(regActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}