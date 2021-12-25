package com.kazimasum.qrdemofirebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class login extends AppCompatActivity {
    Button btn_login;
    private EditText editemail,editpassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        editemail = (EditText) findViewById(R.id.editText_Email);
        editpassword = (EditText) findViewById(R.id.editText_Password);
        btn_login=findViewById(R.id.login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateLogin();
            }
        });

    }

    private void validateLogin() {
        String password =editpassword.getText().toString().trim();
        String email =editemail.getText().toString().trim();

        if (email.isEmpty()){
            editemail.setError("email is requied!");
            editemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editemail.setError("Please provide valid email!");
            editemail.requestFocus();
            return;
        }
        if (password.isEmpty()|| password.length() < 6){
            editpassword.setError("password is requied!");
            editpassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    sendUsertoMain();
                    Toast.makeText(login.this,"Success",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(login.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        checkCurrentUser(currentUser);
    }
    public void checkCurrentUser(FirebaseUser user) {
        // [START check_current_user]
        if(user != null){
            sendUsertoMain();
//            sendUsertoapi();
        }

        // [END check_current_user]
    }

    private void sendUsertoMain() {
//           startActivity(new Intent(this,center.class));
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
