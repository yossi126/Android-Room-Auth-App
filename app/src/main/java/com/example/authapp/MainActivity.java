package com.example.authapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.authapp.databinding.ActivityMainBinding;
import com.example.authapp.entity.Users;
import com.example.authapp.util.DataViewModel;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DataViewModel dataViewModel;
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataViewModel = new ViewModelProvider(MainActivity.this).get(DataViewModel.class);
        sharedPreferences = getSharedPreferences("UserPref",MODE_PRIVATE);


        binding.registerBtnTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.enterEmailEt.getText().toString().trim();
                String password = binding.enterPassEt.getText().toString().trim();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(MainActivity.this,"fill in all bitch",Toast.LENGTH_SHORT).show();
                    //Snackbar.make(MainActivity.this, R.string.text_label, Snackbar.LENGTH_SHORT).show();
                }else{
                    dataViewModel.userLoginAuth(email,password).observe(MainActivity.this, new Observer<Users>() {
                        @Override
                        public void onChanged(Users user) {
                            if(user == null){
                                Toast.makeText(MainActivity.this,"no user found",Toast.LENGTH_SHORT).show();
                            }else{
                                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                                intent.putExtra(USER_NAME,user.getUserName());
                                intent.putExtra(USER_ID,user.getUserId());
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("UserName",user.getUserName());
                                editor.putInt("UserId",user.getUserId());
                                editor.commit();
                                startActivity(intent);
                            }
                        }
                    });

                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        binding.enterEmailEt.setText("");
        binding.enterPassEt.setText("");
        sharedPreferences = getApplicationContext().getSharedPreferences("UserPref",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "onDestroy: main activity");
    }
}