package com.example.applabs6elopez_aperrando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.OutputStreamWriter;
import java.util.regex.Pattern;

public class Registro_especial extends AppCompatActivity {
    EditText contra,nomb,ced,mail,desc;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_especial);

        //inicializo los controladores
        contra=(EditText)findViewById(R.id.contra_registro);
        nomb=(EditText)findViewById(R.id.nombre_registro);
        ced=(EditText)findViewById(R.id.cedula_registro);
        mail=(EditText)findViewById(R.id.correo_registro);
        desc=(EditText)findViewById(R.id.descripcion_registro);
        radioGroup = findViewById(R.id.radio);

    }

    public void Enviar (View view){
        //Este if llama al metodo Validar() para corroborar que ese ingresen datos
        if(Validar()) {
            int radioId = radioGroup.getCheckedRadioButtonId();
            radioButton = findViewById(radioId);

            //Try catch para guardar los datos en un archivo
            try {
                //direccion de guardado
                OutputStreamWriter osr = new OutputStreamWriter(openFileOutput("Registrados.txt", Context.MODE_PRIVATE));

                //se guardan con un ";" para separarlos cada uno (Esto se usara para separarlos y guardarlos en los demas Activity)
                osr.write(contra.getText().toString()+";");
                osr.write(nomb.getText().toString()+";");
                osr.write(ced.getText().toString()+";");
                osr.write(mail.getText().toString()+";");
                osr.write(radioButton.getText().toString()+";");
                osr.write(desc.getText().toString()+";");

                //Se cierra el registro
                osr.close();

                Toast.makeText(this, "Registro Especial Completo " + contra.getText().toString(), Toast.LENGTH_SHORT).show();

                //Se abre el Activity
                Intent login = new Intent(this, MainActivity.class);
                startActivity(login);

            } catch (Exception e) {
                Toast.makeText(this, "no sirve >:c", Toast.LENGTH_LONG).show();
            }
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

