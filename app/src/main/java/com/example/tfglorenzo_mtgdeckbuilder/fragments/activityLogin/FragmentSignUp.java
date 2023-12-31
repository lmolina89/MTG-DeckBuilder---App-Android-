package com.example.tfglorenzo_mtgdeckbuilder.fragments.activityLogin;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfglorenzo_mtgdeckbuilder.R;
import com.example.tfglorenzo_mtgdeckbuilder.api.ConexionRetrofitDeckbuilder;
import com.example.tfglorenzo_mtgdeckbuilder.api.DockerLampApi;
import com.example.tfglorenzo_mtgdeckbuilder.interfaces.InterfaceLogin;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.data.RegisterData;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response.ResponseErrorBody;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response.ResponseRegister;
import com.google.gson.Gson;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSignUp extends Fragment {
    private Button btnBack, btnCreate;
    private EditText txtNick, txtEmail, txtPass;
    private TextView txtErrorSign;
    private InterfaceLogin listenerLogin;
    private ConexionRetrofitDeckbuilder conexionRetrofitDeckbuilder;


    public FragmentSignUp() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conexionRetrofitDeckbuilder = new ConexionRetrofitDeckbuilder();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        btnBack = view.findViewById(R.id.btn_back);
        btnCreate = view.findViewById(R.id.btn_create);
        txtNick = view.findViewById(R.id.edit_nick);
        txtEmail = view.findViewById(R.id.edit_email);
        txtPass = view.findViewById(R.id.edit_pass);
        txtErrorSign = view.findViewById(R.id.txt_errorSu);

        btnBack.setOnClickListener(view1 -> {
            listenerLogin.backToLogin();
        });


        btnCreate.setOnClickListener(view1 -> {
            userRegister();
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InterfaceLogin) {
            listenerLogin = (InterfaceLogin) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "Deberias implementar la interfaz de login, muchacho");
        }
    }

    public void userRegister() {
        RegisterData registerData = new RegisterData(
                txtEmail.toString(),
                txtPass.toString(),
                txtNick.toString()
        );

        DockerLampApi dockerLampApi = conexionRetrofitDeckbuilder.getDockerLampApi();
        String nick = txtNick.getText().toString();
        String email = txtEmail.getText().toString();
        String pass = txtPass.getText().toString();
        if (nick.equals("")) {
            txtErrorSign.setText(getString(R.string.error_nick));
            return;
        } else if (pass.matches("")) {
            txtErrorSign.setText(getString(R.string.error_pass));
            return;
        } else {
            txtErrorSign.setText("");
            registerData.setEmail(email);
            registerData.setPasswd(pass);
            registerData.setNick(nick);
        }
        Call<ResponseRegister> call = dockerLampApi.userRegister(registerData);

        call.enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                ResponseRegister responseRegister = response.body();
                if (response.errorBody() != null) {
                    ResponseErrorBody responseBody = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorBody.class);
                    txtErrorSign.setText(responseBody.getDetails());
                    return;
                }
                if (responseRegister.getResult().equals("ok") && responseRegister.getInsertId() != 0) {
                    listenerLogin.backToLogin();
                    Toast.makeText(getContext(), "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
                }
                if (responseRegister.getInsertId() == 0) {
                    txtErrorSign.setText(R.string.email_exists);
                }
                if (responseRegister.getResult().equals("error")) {
                    txtErrorSign.setText(R.string.register_error);
                }
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                Toast.makeText(getContext(), "Error al crear usuario", Toast.LENGTH_SHORT).show();
                System.out.println(t.getLocalizedMessage());
                t.getCause();
            }
        });
    }

    public void getVerifyData() {

    }
}