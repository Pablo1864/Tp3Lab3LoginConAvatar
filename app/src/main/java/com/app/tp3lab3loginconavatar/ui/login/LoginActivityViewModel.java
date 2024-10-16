package com.app.tp3lab3loginconavatar.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.app.tp3lab3loginconavatar.request.ApiClient;
import com.app.tp3lab3loginconavatar.ui.registro.RegistroMainActivity;


public class LoginActivityViewModel extends AndroidViewModel {
    private Context context;

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void verificarUsuario(String email, String password) {


        if (ApiClient.login(context, email, password) != null) {
            Intent intent = new Intent(context, RegistroMainActivity.class);
            intent.putExtra("existe", true);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            Toast.makeText(context, "Bienvenido", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
        }


    }

    public void registrarUsuario() {
        Intent intent = new Intent(context, RegistroMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}