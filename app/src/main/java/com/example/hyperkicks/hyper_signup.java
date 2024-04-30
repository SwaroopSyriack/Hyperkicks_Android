package com.example.hyperkicks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.hyperkicks.databinding.ActivityHyperSignupBinding;
import com.example.hyperkicks.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class hyper_signup extends AppCompatActivity {
    ActivityHyperSignupBinding binding;
    private FirebaseAuth auth;

    Dialog dialog;
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHyperSignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.username.getText().toString();
                String fname = binding.fname.getText().toString();
                String lname = binding.lname.getText().toString();
                String email = binding.email.getText().toString();
                String passwd = binding.pswd.getText().toString();
                if (validate(username, fname, lname, email, passwd)){
                    sign_up_to_hyperkick(username,fname,lname,email,passwd);
                }
            }
        });
        binding.loginredirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(hyper_signup.this,hyper_login.class));
            }
        });
    }
    private void sign_up_to_hyperkick(String username,String fname, String lname, String email, String passwd) {

        progress();
        auth.createUserWithEmailAndPassword(email,passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            final userModel add_user = new userModel(username,fname,lname,email);
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    firestore.collection("users").document(email).set(add_user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(hyper_signup.this, "Sign up Sucessfull", Toast.LENGTH_SHORT).show();
                                binding.username.setText("");
                                binding.fname.setText("");
                                binding.lname.setText("");
                                binding.email.setText("");
                                binding.pswd.setText("");
                                startActivity(new Intent(hyper_signup.this,hyper_login.class));
                            }
                        }
                    });
                }
                else{
                    Exception exception = task.getException();
                    if (exception instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(hyper_signup.this, "Email already Exits..", Toast.LENGTH_SHORT).show();
                    }
                    else if (exception instanceof FirebaseAuthWeakPasswordException){
                        Toast.makeText(hyper_signup.this, "Weak Password", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if (exception != null)
                        {
                            Log.d("Error Message",exception.getMessage());
                        }
                    }
                }
            }
        });
    }
    public boolean validate(String username,String fname,String lname,String email,String passwd){

        if (TextUtils.isEmpty(username)){
            binding.username.setError("Username name cannot be empty");
        }
        else if (TextUtils.isEmpty(fname)){
            binding.fname.setError("frist name cannot be empty");
        }
        else if (TextUtils.isEmpty(lname)) {
            binding.lname.setError("Last name cannot be empty");
        }
        else if (TextUtils.isEmpty(email)) {
            binding.email.setError("Last name cannot be empty");
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.email.setError("Invalid Email");
        }
        else if (TextUtils.isEmpty(passwd)) {
            binding.pswd.setError("Last name cannot be empty");
        }

        else if (binding.pswd.length() < 8 ){
            binding.pswd.setError("Invalid password");
        }
        else{
            return true;
        }
        return false;
    }

    private void progress() {
        dialog = new Dialog(hyper_signup.this);
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