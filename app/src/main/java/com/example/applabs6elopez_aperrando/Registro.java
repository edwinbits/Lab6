package com.example.applabs6elopez_aperrando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {

    EditText contra,nomb,ced,mail,desc;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //inicializo los controladores
        contra=(EditText)findViewById(R.id.contra_registro);
        nomb=(EditText)findViewById(R.id.nombre_registro);
        ced=(EditText)findViewById(R.id.cedula_registro);
        mail=(EditText)findViewById(R.id.correo_registro);
        desc=(EditText)findViewById(R.id.descripcion_registro);
        radioGroup = findViewById(R.id.radio);

    }

    public void Enviar (View view){
        //Aqui comienzan los if de validacion
        if(Validar()) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);

        //Guardo los datos introducidos en variables
        int Contra1=Integer.parseInt(contra.getText().toString());
        String nombre1=nomb.getText().toString();
        String cedula1=ced.getText().toString();
        String correo1=mail.getText().toString();
        String descripcion1=desc.getText().toString();
        String TipoUsuario = radioButton.getText().toString();

        //Creo el Shared Preferences
        SharedPreferences SPref1=getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=SPref1.edit();

        //Los guardo con sus respectivas Keys
        edit.putString("Correo",correo1);
        edit.putInt("Contraseña",Contra1);
        edit.putString("Nombre",nombre1);
        edit.putString("Cedula",cedula1);
        edit.putString("Descripción",descripcion1);
        edit.putString("Tipo",TipoUsuario);

        edit.commit();


            Toast.makeText(this, "Registro Completo " + correo1, Toast.LENGTH_SHORT).show();
            Intent login = new Intent(this, MainActivity.class);
            startActivity(login);
        }
    }

    public boolean Validar() {
        //Valida que se escriban datos
        boolean retorno = true;
        //Este objeto me permite validar si el formato es de un correo electronico
        Pattern pattern = Patterns.EMAIL_ADDRESS;

        String Contra = contra.getText().toString();
        String Nombre = nomb.getText().toString();
        String Cedula = ced.getText().toString();
        String Correo = mail.getText().toString();
        String Descripcion = desc.getText().toString();

        //Aqui corroboro si el correo introducido es correcto
        boolean email= pattern.matcher(Correo).matches();

        //Aqui comienzan los if de validacion
        if (Correo.isEmpty()) {
            mail.setError("Falta Correo");
            retorno = false;
        }
        if(!email && Correo != ""){
            mail.setError("Correo inválido");
            retorno = false;
        }
        if (Contra.isEmpty()) {
            contra.setError("Falta Contraseña");
            retorno = false;
        }
        if (Nombre.isEmpty()) {
            nomb.setError("Falta Nombre");
            retorno = false;
        }
        if (Cedula.isEmpty()) {
            ced.setError("Falta Cedula");
            retorno = false;
        }

        if (Descripcion.isEmpty()) {
            desc.setError("Falta Descripcion");
            retorno = false;
        }
        return retorno;
    }
}



