package com.example.davidloris_project;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.davidloris_project.Local.MyDatabase;
import com.example.davidloris_project.Local.UserDAO;
import com.example.davidloris_project.Model.User;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fm = getSupportFragmentManager();
    private Fragment displayedFragment = null;
    private LoginFragment loginFragment;

    public  static MyDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDatabase = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, "tabUser").allowMainThreadQueries().build();

        if(savedInstanceState == null) {
            displayedFragment = new LoginFragment();
            fm.beginTransaction().replace(R.id.login_container, displayedFragment).commit();
        }
    }

    public void Login(View view){


        if(displayedFragment.getClass().getSimpleName().equals("SignInFragment")  ){
            displayedFragment = new LoginFragment();
            fm.beginTransaction().replace(R.id.login_container, displayedFragment).commit();
        }
        else if (loginFragment.getLogin().equals("Loris") && loginFragment.getPassword().equals("Clivaz")){
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }

    }
    public void signIn(View view)
    {
        displayedFragment = new SignInFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.login_container, displayedFragment).commit();
    }
}
