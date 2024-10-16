package com.app.tp3lab3loginconavatar.request;

import android.content.Context;

import android.util.Log;
import android.widget.Toast;


import com.app.tp3lab3loginconavatar.modelo.Usuario;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class ApiClient {

    private static File archivoUsuario;

    private static File conectar(Context context) {
        if (archivoUsuario == null) {
            archivoUsuario = new File(context.getFilesDir(), "Usuario.obj");
        }
        return archivoUsuario;
    }

    public static void guardar(Context context, Usuario usuario) {
        archivoUsuario = conectar(context);
        try (FileOutputStream fos = new FileOutputStream(archivoUsuario, false);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(usuario);
            oos.flush();
            Toast.makeText(context, "Usuario guardado con éxito", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException ex) {
            Toast.makeText(context, "¡Error de archivo!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(context, "¡Error de entrada/salida! No se pudo escribir el archivo.", Toast.LENGTH_SHORT).show();
        }
    }

    public static Usuario leer(Context context) {
        Usuario usuario = null;
        archivoUsuario = conectar(context);

        if (!archivoUsuario.exists()) {
            Toast.makeText(context, "El archivo de usuario no existe", Toast.LENGTH_SHORT).show();
            return null;
        }

        try (FileInputStream fis = new FileInputStream(archivoUsuario);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            usuario = (Usuario) ois.readObject();
        } catch (FileNotFoundException e) {
            Toast.makeText(context, "¡Error de archivo!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(context, "¡Error de entrada/salida!", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            Toast.makeText(context, "Error inesperado!", Toast.LENGTH_SHORT).show();
        }

        return usuario;
    }

    public static Usuario login(Context context, String email, String password) {
        Usuario usuario = leer(context);

        if (usuario == null) {
            return null;
        }

        if (!email.equals(usuario.getEmail()) || !password.equals(usuario.getPassword())) {
            return null;
        }

        return usuario;
    }
}