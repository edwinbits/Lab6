package com.example.applabs6elopez_aperrando;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText user, pass;
    TextView registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user=(EditText)findViewById(R.id.txtUser);
        pass=(EditText)findViewById(R.id.txtPass);
        this.Registro2();
    }

    public void Registro2(){

        //Este metodo me permite volver parte del TextView un Span(Texto con clickeable)

        registro=(TextView)findViewById(R.id.Registrar);

        //Guardo el mismo texto puesto en el Textview en una variable
        String text = "¿No tienes cuenta? Registrate";

        //Creo el objeto tipo SpannableString para volver parte del texto Span
        SpannableString res = new SpannableString(text);

        //Este objeto/metodo me permite convetir el texto Span en Clickeable
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick( View widget) {
                activity_Registro();
            }
            //El onClick que me manda a registro
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                //y con este metodo le cambio el color al texto span para denotarlo
                super.updateDrawState(ds);
                ds.setColor(Color.GRAY);
                ds.setUnderlineText(false);
            }
        };

        //En esta funcion establezco que parte del texto quiero que sea el span
        res.setSpan(clickableSpan1, 19, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //Mando el Texto nuevo al textView
        registro.setText(res);
        registro.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void activity_Registro(){
        Intent reg = new Intent(MainActivity.this, Registro.class);
        startActivity(reg);
    }

    public void Conectar(View view) throws IOException {
        //Este if llama al metodo Validar() para corroborar que ese ingresen datos
        if (Validar()) {

            SharedPreferences SPref = getSharedPreferences("Login", Context.MODE_PRIVATE);

            //Guardo el Shared Preferences en variables

            String UserReg = SPref.getString("Correo", "N/H");
            int PassReg = SPref.getInt("Contraseña", 0);

            String userEsc = user.getText().toString();
            int passEsc = Integer.parseInt(pass.getText().toString());

            if (UserReg.equals("N/H")) {
                Toast.makeText(getApplicationContext(), "Usted no esta registrado", Toast.LENGTH_LONG).show();
            } else {
                if (UserReg.equals(userEsc) && PassReg == passEsc && Validar()) {
                    Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_LONG).show();
                    //Si cumple la funcion, entonces abre el Activity Welcome
                    Intent i = new Intent(MainActivity.this, Welcome.class);
                    //i.putExtra me mandara el usuario al siguiente Activity para comparar si proviene de Archivo Registro
                    i.putExtra("dato", userEsc);
                    startActivity(i);
                } else {

                    //Leo el Archivo
                    BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("Registrados.txt")));

                    //Lo guardo en una variable
                    String lectura = br.readLine();
                    br.close();

                    //Creo un vector y guargo los datos de la variable, separado cada uno con el ";"
                    String[] datos = lectura.split(";");

                    if (userEsc.equals(datos[3]) && passEsc == Integer.parseInt(datos[0])) {
                        Toast.makeText(getApplicationContext(), "Bienvenida especial", Toast.LENGTH_LONG).show();

                        //Si cumple la funcion, entonces abre el Activity Welcome
                        Intent i = new Intent(MainActivity.this, Welcome.class);
                        //i.putExtra me mandara el usuario al siguiente Activity para comparar si proviene de Archivo Registro
                        i.putExtra("dato", userEsc);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Usuario o Contraseña erroneos", Toast.LENGTH_LONG).show();

                    }
                }

            }
        }
    }







    public boolean Validar() {

        //Valida que se escriban datos en el Login
        boolean retorno = true;
        String User1 = user.getText().toString();
        String Pass1 = pass.getText().toString();

        if (User1.isEmpty()) {
            user.setError("Falta Usuario");
            retorno = false;
        }
        if (Pass1.isEmpty()) {
            pass.setError("Falta Contraseña");
            retorno = false;
        }
        return retorno;
    }

}
