package com.example.authapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.authapp.databinding.ActivityRegisterBinding;
import com.example.authapp.entity.Users;
import com.example.authapp.util.DataViewModel;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    private DataViewModel dataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_register);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataViewModel = new ViewModelProvider(RegisterActivity.this).get(DataViewModel.class);

        binding.registerNewUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editTextEnterName.getText().toString().trim();
                String email = binding.editTextEnterEmail.getText().toString().trim();
                String password = binding.editTextEnterPassword.getText().toString().trim();

                if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
                    Toast.makeText(RegisterActivity.this,"fill in all bitch",Toast.LENGTH_SHORT).show();
                }else{
                    Users user = new Users(name,email,password);
                    dataViewModel.insertNewUser(user);
                    Toast.makeText(RegisterActivity.this,"success",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}