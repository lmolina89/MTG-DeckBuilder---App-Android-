package com.example.tfglorenzo_mtgdeckbuilder.fragments.activityLogin;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfglorenzo_mtgdeckbuilder.R;
import com.example.tfglorenzo_mtgdeckbuilder.api.DockerLampApi;
import com.example.tfglorenzo_mtgdeckbuilder.interfaces.InterfaceLogin;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.data.RegisterData;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response.ResponseRegister;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentSignUp extends Fragment {
    private Button btnBack, btnCreate;
    private EditText txtNick, txtEmail, txtPass;
    private TextView txtErrorSign;
    private InterfaceLogin listenerLogin;
    private final String URL = "http://10.0.2.2:80/api-users/endp/";

    public FragmentSignUp() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                txtNick.toString(),
                "",
                "{}");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DockerLampApi dockerLampApi = retrofit.create(DockerLampApi.class);
        String nick = txtNick.getText().toString();
        String email = txtEmail.getText().toString();
        String pass = txtPass.getText().toString();
        String regexEmail = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        if (nick.equals("")) {
            txtErrorSign.setText("El nick ya existe o no tiene un formato valido");
        } else if (email.equals("") || !email.matches(regexEmail)) {
            txtErrorSign.setText("El email no tiene un formato valido");
        } else if (pass.matches("")) {
            txtErrorSign.setText("Contraseña vacia");
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
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException | NullPointerException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(call.request());
                }
                if (responseRegister.getResult().equals("ok") && responseRegister.getInsertId() != 0) {
                    listenerLogin.backToLogin();
                    Toast.makeText(getContext(), "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
                } else if (responseRegister.getInsertId() == 0) {
                    txtErrorSign.setText("El email ya existe");
                }
                txtErrorSign.setText("Ha ocurrido algun problema");
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                Toast.makeText(getContext(), "Error al crear usuario", Toast.LENGTH_SHORT).show();
                System.out.println(t.getLocalizedMessage());
                t.getCause();
            }
        });
    }
}