package com.example.nchatapp0;

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

    EditText userETLogin, passETLogin;
    Button loginBtn,registerBtn;

    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        //checking to see if user exists or not and saves the current user
        if(firebaseUser!=null){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userETLogin = findViewById(R.id.editTextTextPersonName2);
        passETLogin = findViewById(R.id.editTextTextPassword2);
        loginBtn = findViewById(R.id.button2);
        registerBtn = findViewById(R.id.button3);

        auth = FirebaseAuth.getInstance();






        //Register Button
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });


        //onclicking login
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //whitespace triming required!
                String emailText = userETLogin.getText().toString();
                String passText = passETLogin.getText().toString();

                //checking if it is empty
                if(TextUtils.isEmpty(emailText) || TextUtils.isEmpty(passText)){
                    Toast.makeText(LoginActivity.this,"please enter the username or password",Toast.LENGTH_SHORT).show();
                }
                else{
                    //authenticating the user
                    auth.signInWithEmailAndPassword(emailText,passText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); // to remove this activity from the stack
                                startActivity(i);
                                finish();
                            }
                            else{
                                Toast.makeText(LoginActivity.this,"Login Failed!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}