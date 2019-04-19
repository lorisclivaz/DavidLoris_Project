package com.example.davidloris_project.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.davidloris_project.Activity.HomeActivity;
import com.example.davidloris_project.BaseApp;
import com.example.davidloris_project.R;
import com.example.davidloris_project.Repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment {


    private UserRepository repository;
    private EditText editTextLogin;
    private EditText editTextPassword;

    public static String MY_PREFS_NAME;
    static String USER_ID_CLOUD;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        repository = ((BaseApp) getActivity().getApplication()).getClientRepository();

        //Get the informations about the login
        editTextLogin = v.findViewById(R.id.usernameField_login);
        editTextPassword = v.findViewById(R.id.passwordField_login);

        //Instancied the login button
        Button loginButton = v.findViewById(R.id.buttonLogin_login);
        loginButton.setOnClickListener(loginClick);

        //Instancied the signin button
        Button signinButton = v.findViewById(R.id.buttonSignin_login);
        signinButton.setOnClickListener(signInClick);

        return v;
    }

    View.OnClickListener loginClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            controlLogin();
        }
    };

    View.OnClickListener signInClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.login_container, new SignInFragment()).commit();
        }
    };


    //We controle the login
    public void controlLogin() {

        //All the information that we need
        String username = editTextLogin.getText().toString();
        String password = editTextPassword.getText().toString();
        MY_PREFS_NAME = editTextLogin.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            editTextPassword.setError("Invalid password");
            editTextPassword.setText("");
            focusView = editTextPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            editTextLogin.setError("Field is required");
            focusView = editTextLogin;
            cancel = true;
        } else if (!isEmailValid(username)) {
            editTextLogin.setError("Invalid email");
            focusView = editTextLogin;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            repository.signIn(username, password, task -> {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                    editTextLogin.setText("");
                    editTextPassword.setText("");
                    USER_ID_CLOUD = FirebaseAuth.getInstance().getUid();
                } else {
                    editTextLogin.setError("Invalid username or password");
                    editTextLogin.requestFocus();
                    editTextPassword.setText("");
                }
            });
        }
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}
