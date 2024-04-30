package com.example.hyperkicks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.hyperkicks.databinding.ActivityHyperForgotPasswdBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class hyper_forgot_passwd extends AppCompatActivity {
    ActivityHyperForgotPasswdBinding binding;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHyperForgotPasswdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // In your Fragment
                // In your Fragment
                startActivity(new Intent(hyper_forgot_passwd.this,hyper_login.class));

            }
        });

        binding.rsetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.email.getText().toString();
                if (TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(hyper_forgot_passwd.this, "Enter your registered Email Address", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(hyper_forgot_passwd.this, "Check your Email", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(hyper_forgot_passwd.this, "Unable to send email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}