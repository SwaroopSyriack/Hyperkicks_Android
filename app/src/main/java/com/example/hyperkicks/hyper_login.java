package com.example.hyperkicks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.hyperkicks.databinding.ActivityHyperLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class hyper_login extends AppCompatActivity {
    ActivityHyperLoginBinding binding;

    Dialog dialog;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHyperLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.uemail.getText().toString();
                String passwd = binding.upswd.getText().toString();

                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    if (!passwd.isEmpty()){
                        mAuth.signInWithEmailAndPassword(email,passwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(hyper_login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(hyper_login.this,MainActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(hyper_login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                Log.d("Error",e.getMessage());

                            }
                        });
                    }
                    else{
                        Toast.makeText(hyper_login.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(email.isEmpty()){
                    Toast.makeText(hyper_login.this, "Email Cannot be Empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(hyper_login.this, "Incorrect email", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress();
                startActivity(new Intent(hyper_login.this,hyper_signup.class));
            }
        });

        binding.forgotpaswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(hyper_login.this,hyper_forgot_passwd.class));


            }
        });

    }
    private void progress() {
        dialog = new Dialog(hyper_login.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_progress_bar);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l)
            {
            }

            @Override
            public void onFinish()
            {
                dialog.dismiss();
            }
        }.start();
    }
}