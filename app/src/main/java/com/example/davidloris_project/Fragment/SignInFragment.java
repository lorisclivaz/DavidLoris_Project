package com.example.davidloris_project.Fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.davidloris_project.Model.User;
import com.example.davidloris_project.R;
import com.example.davidloris_project.ViewModel.UserVM;

public class SignInFragment extends Fragment {


    private UserVM userVm;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;

    public SignInFragment() {

    }

    //We create the view
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_signin, container, false);


        userVm = ViewModelProviders.of(this).get(UserVM.class);

        //All the informations about the signin
        editTextUsername = view.findViewById(R.id.usernameField);
        editTextPassword = view.findViewById(R.id.passwordField);
        editTextConfirmPassword = view.findViewById(R.id.passwordConfirmField);

        //We instancied the signin button
        Button signin = view.findViewById(R.id.buttonSignin);
        signin.setOnClickListener(signInClick);

        //We instancied the backlogin button
        Button backLogin = view.findViewById(R.id.buttonLogin);
        backLogin.setOnClickListener(backLoginClick);

        return view;
    }

    View.OnClickListener signInClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //Get all the informations from EditText
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();
            String confirmPassword = editTextConfirmPassword.getText().toString();
            AsyncTask<String, Void, User> userControle = userVm.getUserByusername(username);


            /* Control if the fields are not empty */
            if (username.trim().isEmpty() || password.trim().isEmpty() || confirmPassword.trim().isEmpty()) {
                Toast.makeText(getActivity(), "All fields must be completed", Toast.LENGTH_SHORT).show();
                return;
            }

            /* control if the user doesn't already exist */


            /* control if the passwords match */
            if (!password.equals(confirmPassword)) {
                Toast.makeText(getActivity(), "Passwords doesn't match", Toast.LENGTH_SHORT).show();
                return;
            }

            User newUser = new User(username, password);
            userVm.insert(newUser);

            Toast.makeText(getActivity(), "User registered", Toast.LENGTH_SHORT).show();

            FragmentManager f = getFragmentManager();
            FragmentTransaction t = f.beginTransaction();
            Fragment frag = new LoginFragment();
            t.replace(R.id.login_container,frag);
            t.commit();

        }
    };

    View.OnClickListener backLoginClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.login_container, new LoginFragment()).commit();
        }
    };
}
