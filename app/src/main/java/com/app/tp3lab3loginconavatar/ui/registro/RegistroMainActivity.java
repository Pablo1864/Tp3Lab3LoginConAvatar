package com.app.tp3lab3loginconavatar.ui.registro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import androidx.activity.result.ActivityResult;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.app.tp3lab3loginconavatar.R;
import com.app.tp3lab3loginconavatar.databinding.ActivityRegistroMainBinding;

public class RegistroMainActivity extends AppCompatActivity {
    private RegistroViewModel mv;
    private ActivityRegistroMainBinding binding;
    private ActivityResultLauncher<Intent> arl;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegistroMainBinding.inflate(getLayoutInflater());
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RegistroViewModel.class);
        setContentView(binding.getRoot());
        abrirGaleria();

        mv.getUsuarioM().observe(this, usuario -> {
            binding.etNombre.setText(usuario.getNombre());
            binding.etApellido.setText(usuario.getApellido());
            binding.etEmail.setText(usuario.getEmail());
            binding.etPass.setText(usuario.getPassword());
            binding.etDni.setText(String.valueOf(usuario.getDni()));
        });

        mv.getAvatarM().observe(this, new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                binding.iVAvatar.setImageURI(uri);
            }
        });



        binding.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre, apellido, dni, email, password;
                nombre = binding.etNombre.getText().toString();
                apellido = binding.etApellido.getText().toString();
                dni = binding.etDni.getText().toString();
                email = binding.etEmail.getText().toString();
                password = binding.etPass.getText().toString();
                mv.registrar(nombre, apellido, dni, email, password);
            }
        });

        binding.btnSubirAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arl.launch(intent);
            }
        });


        mv.getDatos(getIntent());

    }

    public void abrirGaleria(){
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>(){
            @Override
            public void onActivityResult(ActivityResult o) {
                mv.recibirFoto(o);
            }
        });
    }
}


