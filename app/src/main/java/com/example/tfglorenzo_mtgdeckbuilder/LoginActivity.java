package com.example.tfglorenzo_mtgdeckbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tfglorenzo_mtgdeckbuilder.fragments.activityLogin.FragmentLogin;
import com.example.tfglorenzo_mtgdeckbuilder.fragments.activityLogin.FragmentSignUp;
import com.example.tfglorenzo_mtgdeckbuilder.interfaces.InterfaceLogin;

public class LoginActivity extends AppCompatActivity implements InterfaceLogin {
    private Button btnAdminBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences preferences;
        preferences = getSharedPreferences(getString(R.string.userPreferences), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(getString(R.string.preferences_pass), "");
        editor.putBoolean(getString(R.string.preferences_is_loged), false);
        editor.putString(getString(R.string.preferences_apikey), "");
        editor.commit();

        btnAdminBack = findViewById(R.id.btn_admin_volver);
        btnAdminBack.setOnClickListener(view -> {
            disableAdminBackButton();
            backToLoginFromAdmin();
        });

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainerLogin, new FragmentLogin(), "Login fragment")
                .commit();
    }

    @Override
    public void startSignUp() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerLogin, FragmentSignUp.class, null)
                .commit();
    }

    @Override
    public void backToLogin() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerLogin, FragmentLogin.class, null)
                .commit();
    }

    @Override
    public void backToLoginFromAdmin() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerLogin, FragmentLogin.class, null)
                .commit();
    }


    @Override
    public void activateAdminBackButton() {
        btnAdminBack.setClickable(true);
        btnAdminBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void disableAdminBackButton() {
        btnAdminBack.setClickable(false);
        btnAdminBack.setVisibility(View.INVISIBLE);
    }
}