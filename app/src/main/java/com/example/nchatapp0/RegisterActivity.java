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
import com.google.firebase.auth.FirebaseUser; //Represents a user's profile information in your Firebase project's user database
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText userET, passET, emailET;
    Button registerBtn;
    Toast toast;

    //Firebase
    FirebaseAuth auth;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userET = findViewById(R.id.editTextTextPersonName);
        passET = findViewById(R.id.editTextTextPassword);
        emailET = findViewById(R.id.emailEditText);
        registerBtn = findViewById(R.id.button);

        auth = FirebaseAuth.getInstance();

        //on pressing SignUp
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 final String usernameText = userET.getText().toString();
                final String emailText = emailET.getText().toString();
                final String passText = passET.getText().toString();

                //We can Use regex here to check data before regestring

                if (TextUtils.isEmpty(usernameText) || TextUtils.isEmpty(emailText) || TextUtils.isEmpty(passText)) {
                    Toast.makeText(RegisterActivity.this, "please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {

                    Query usernameQuery = FirebaseDatabase.getInstance().getReference().child("MyUsers").orderByChild("username").equalTo(usernameText);
                    //username gets ordered alphabetically
                    usernameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getChildrenCount() > 0) {
                                toast = Toast.makeText(RegisterActivity.this, "Username Already Exists", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                            else {
                                toast = Toast.makeText(RegisterActivity.this, "Registering", Toast.LENGTH_SHORT);
                                toast.show();
//                                RegisterNow(usernameText, emailText, passText);
                                auth.createUserWithEmailAndPassword(emailText,passText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            FirebaseUser firebaseUser = auth.getCurrentUser();
                                            String userid = firebaseUser.getUid();
                                            //creating a new column of myUser in the database
                                            myRef = FirebaseDatabase.getInstance().getReference("MyUsers").child(userid);
                                            //HashMaps
                                            HashMap<String,String> hashMap = new HashMap<>();
                                            hashMap.put("id",userid);
                                            hashMap.put("username",usernameText);
                                            hashMap.put("imageURL","default"); //to allow user to store profile pic in database


                                            //opening main activity
                                            myRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if(task.isSuccessful()){
                                                        Intent i = new Intent(RegisterActivity.this,MainActivity.class);
                                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); // to remove this activity from the stack
                                                        startActivity(i);
                                                        finish();
                                                    }

                                                    //put a toast later here in else
                                                    else {
                                                        Toast.makeText(RegisterActivity.this,"SedLyf",Toast.LENGTH_SHORT).show();}
                                                }
                                            });
                                        }
                                        else {
                                            Toast.makeText(RegisterActivity.this,"Invalid",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                            }



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            }
        });
    }

    //
//    private void RegisterNow(final String username , String email ,String password){
//        //Tries to create a new user account with the given email address and password. If successful, it also signs the user in into the app.
//       auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//           @Override
//           public void onComplete(@NonNull Task<AuthResult> task) {
//            if(task.isSuccessful()){
//                FirebaseUser firebaseUser = auth.getCurrentUser();
//                String userid = firebaseUser.getUid();
//                //creating a new column of myUser in the database
//                myRef = FirebaseDatabase.getInstance().getReference("MyUsers").child(userid);
//                //HashMaps
//                HashMap<String,String> hashMap = new HashMap<>();
//                hashMap.put("id",userid);
//                hashMap.put("username",username);
//                hashMap.put("imageURL","default"); //to allow user to store profile pic in database
//
//                //opening main activity
//                myRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//
//                        if(task.isSuccessful()){
//                            Intent i = new Intent(RegisterActivity.this,MainActivity.class);
//                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); // to remove this activity from the stack
//                            startActivity(i);
//                            finish();
//                        }
//
//                        //put a toast later here in else
//                        else {
//                        Toast.makeText(RegisterActivity.this,"SedLyf",Toast.LENGTH_SHORT).show();}
//                    }
//                });
//            }
//            else {
//                Toast.makeText(RegisterActivity.this,"Invalid",Toast.LENGTH_SHORT).show();
//            }
//           }
//       });
//    }
//}
}