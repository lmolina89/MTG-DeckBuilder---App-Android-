package com.example.tfglorenzo_mtgdeckbuilder.fragments.activityLogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfglorenzo_mtgdeckbuilder.R;
import com.example.tfglorenzo_mtgdeckbuilder.UserActivity;
import com.example.tfglorenzo_mtgdeckbuilder.api.ConexionRetrofitDeckbuilder;
import com.example.tfglorenzo_mtgdeckbuilder.api.DockerLampApi;
import com.example.tfglorenzo_mtgdeckbuilder.databinding.FragmentLoginBinding;
import com.example.tfglorenzo_mtgdeckbuilder.interfaces.InterfaceLogin;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.data.LoginData;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response.ResponseAuth;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response.ResponseErrorBody;
import com.example.tfglorenzo_mtgdeckbuilder.models.userData.UsersList;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentLogin extends Fragment {
    private EditText txtEmail, txtPass;
    private TextView txtError, txtSignup;
    private Button btnSubmit;
    private InterfaceLogin listenerLogin;
    private FragmentLoginBinding fragmentLoginBinding;
    private ConexionRetrofitDeckbuilder conexionRetrofitDeckbuilder;
    private DockerLampApi dockerLampApi;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private UsersList userList = new UsersList();

    public FragmentLogin() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getActivity().getSharedPreferences(getString(R.string.userPreferences), Context.MODE_PRIVATE);
        conexionRetrofitDeckbuilder = new ConexionRetrofitDeckbuilder();
        dockerLampApi = conexionRetrofitDeckbuilder.getDockerLampApi();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false);
        txtEmail = fragmentLoginBinding.editEmail;
        txtPass = fragmentLoginBinding.editPass;
        btnSubmit = fragmentLoginBinding.btnSubmit;
        txtError = fragmentLoginBinding.txtErrorlog;
        txtSignup = fragmentLoginBinding.txtSignUp;

        txtPass.setText("lmolina");
        txtEmail.setText("lmolinamoreno@hotmail.com");

        if (txtEmail.getText().toString().equals("") && txtPass.getText().toString().equals("")) {
            txtEmail.setText(preferences.getString(getString(R.string.preferences_email), null));
            txtPass.setText(preferences.getString(getString(R.string.preferences_pass), null));
        }

        btnSubmit.setOnClickListener(view1 -> {
            String email = txtEmail.getText().toString();
            String pass = txtPass.getText().toString();
            loginAuth(email, pass);
        });

        txtSignup.setOnClickListener(view1 -> {
            listenerLogin.startSignUp();
        });

        return fragmentLoginBinding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof InterfaceLogin) {
            listenerLogin = (InterfaceLogin) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "Deberias implementar la interfaz en el login, muchacho");
        }
    }

    public void loginAuth(String email, String pass) {
        LoginData loginData = new LoginData(email, pass);
        Call<ResponseAuth> call = dockerLampApi.userAuth(loginData);
        call.enqueue(new Callback<ResponseAuth>() {
            @Override
            public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                String apikey;
                //si el codigo de respuesta es 201 inicia el login
                if (response.isSuccessful() && response.body() != null) {
                    ResponseAuth auth = response.body();
                    if (auth.getResult().equals("ok")) {
                        String userNick = auth.getUser_nick();
                        txtError.setText("");
                        Toast.makeText(getActivity(), "Bienvenido " + userNick, Toast.LENGTH_SHORT).show();
                        apikey = auth.getToken();//si es un usuario normal inicia sesion
                        Intent intent = new Intent(getActivity(), UserActivity.class);
                        intent.putExtra("userEmail", email);
                        editor = preferences.edit();                        //guardo los datos del usuario cuando se logea correctamente
                        editor.putString(getString(R.string.preferences_apikey), apikey);
                        editor.putString(getString(R.string.preferences_email), email);
                        editor.putString(getString(R.string.preferences_pass), pass);
                        editor.putString(getString(R.string.preferences_nick), userNick);
                        editor.putBoolean(getString(R.string.preferences_is_loged), true);
                        editor.commit();
                        startActivity(intent);
                    }
                } else {
                    //si el codigo de respuesta es distinto se procesa el error para mostrar el mensaje
                    if (response.errorBody() != null) {
                        ResponseErrorBody responseBody = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorBody.class);
                        txtError.setText(responseBody.getDetails());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseAuth> call, Throwable t) {
                Toast.makeText(getContext(), "No se ha podido hacer la autenticacion", Toast.LENGTH_SHORT).show();
                System.out.println(t.getLocalizedMessage());
                t.getCause();
            }
        });
    }
}