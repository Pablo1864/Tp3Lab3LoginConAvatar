package com.app.tp3lab3loginconavatar.ui.registro;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import static android.app.Activity.RESULT_OK;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.tp3lab3loginconavatar.modelo.Usuario;
import com.app.tp3lab3loginconavatar.request.ApiClient;
import com.app.tp3lab3loginconavatar.ui.login.MainActivity;

public class RegistroViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Usuario> Musuario;
    private Uri uri;
    private MutableLiveData<Uri> Mavatar;

    public RegistroViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Usuario> getUsuarioM(){
        if (Musuario == null){
            Musuario = new MutableLiveData<>();
        }
        return Musuario;
    }
    public LiveData<Uri> getAvatarM(){
        if (Mavatar == null){
            Mavatar = new MutableLiveData<>();
        }
        return Mavatar;
    }

    public void registrar(String nombre, String apellido, String dni, String email, String password) {
        if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            Usuario usuario = new Usuario(nombre, apellido, email, password, Long.parseLong(dni), uri != null? uri.toString(): null);
            Toast.makeText(context, "Usuario creado con éxito", Toast.LENGTH_SHORT).show();

            ApiClient.guardar(context, usuario);

            // Redirigir a la vista de login después de un registro exitoso
            Intent intent = new Intent(context, MainActivity.class);
            // FLAG_ACTIVITY_NEW_TASK se usa para lanzar una actividad desde el contexto del ViewModel
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public void getDatos(Intent intent){
        if (intent.getBooleanExtra("existe", false)){
            Usuario usuario = ApiClient.leer(context);
            if (usuario != null) {
                Musuario.setValue(usuario);
                String uriDeUsuario = usuario.getUriString();
                Log.d("uri", "null?: "+uriDeUsuario);
                if (uriDeUsuario != null){
                    Log.d("uri", "antes: "+uriDeUsuario);
                    uri = Uri.parse(uriDeUsuario);
                    Mavatar.setValue(uri);
                    Log.d("uri", "despues: "+Uri.parse(uriDeUsuario).toString());
                }
            } else {
                Toast.makeText(context, "Oops: ¡Ocurrio un error inesperado al recuperar su usuario!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void recibirFoto(ActivityResult o){
        if (o.getResultCode() == RESULT_OK){
            Intent data = o.getData();
            if (data != null){
                uri = data.getData();
                Mavatar.setValue(uri);
            }
        }
    }
}