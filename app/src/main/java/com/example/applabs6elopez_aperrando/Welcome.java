package com.example.applabs6elopez_aperrando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Welcome extends AppCompatActivity {

    TextView Nombre1, Correo1, Cedula1, Tipo1, Desc1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //inicializo los controladores
        Nombre1=(TextView)findViewById(R.id.Nombre);
        Cedula1=(TextView)findViewById(R.id.Cedula);
        Correo1=(TextView)findViewById(R.id.Correo);
        Desc1=(TextView)findViewById(R.id.Descripcion);
        Tipo1=(TextView)findViewById(R.id.Tipo);

        //Como no se puede colocar excepcion en el OnCreate, entonces se usa Trycatch
        try {
            this.Leer();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public boolean onCreateOptionsMenu (Menu menu) {
        SharedPreferences SPref = getSharedPreferences("Login", Context.MODE_PRIVATE);

        String tipo = SPref.getString("Tipo", "N/H");

        //Este monton de try catch es por la excepcion, No se puede poner IOEXCEPCION ya que el metodo es boolean
       //1. Try catch para el Buffered Reader
        BufferedReader br= null;
        try {
            br = new BufferedReader( new InputStreamReader(openFileInput("Registrados.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //2. Try catch para la guardar los datos del archivo en una variable
        String lectura2 = null;
        try {
            lectura2 = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //3. Try catch para cerrar el Buffered Reader
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Se guardan la variable con el archivo en un vector, separados por ";"
        String[] datos2 = lectura2.split(";");

        //Se verifica si el usuario es administrador
        if(tipo.equals("Administrador") || (datos2[4].equals("Administrador"))) {
            getMenuInflater().inflate(R.menu.menu, menu);
            return true;
        }else {
            return false;
            }


    }

    public boolean onOptionsItemSelected(MenuItem item){
        //Si el usuario era admin, entonces se invoca este menu
        switch (item.getItemId()){
            case R.id.item:
                Intent i = new Intent(getApplicationContext(), Registro_especial.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Leer() throws IOException {

         //En esta parte me permite leer los datos para mostrarlos en los diferentes textview
        SharedPreferences SPref = getSharedPreferences("Login", Context.MODE_PRIVATE);

        String UserReg = SPref.getString("Correo", "N/H");

        Bundle extra = getIntent().getExtras();
        String d1 = extra.getString("dato");


        //aqui comienzo a cargar todos los datos de registro en el array
        BufferedReader br= new BufferedReader( new InputStreamReader(openFileInput("Registrados.txt")));
        String lectura = br.readLine();
        br.close();
        String[] datos = lectura.split(";");

        String name = datos[1];
        String id = datos[2];
        String type = datos[3];
        String email = datos[4];
        String script = datos[5];



        //con este if establezco que si el usuario y la contraseña es igual a los de shared preferences, entonces se imprimen, en caso contrario, se imprime el de registro

        if(d1.equals(UserReg)){
                Imprimir2();
            Toast.makeText(this, datos[1], Toast.LENGTH_LONG).show();
        }else{
            Nombre1.setText(name);
            Cedula1.setText(id);
            Correo1.setText(email);
            Tipo1.setText(type);
            Desc1.setText(script);

        }
    }



    public void Imprimir2(){


        SharedPreferences SPref = getSharedPreferences("Login", Context.MODE_PRIVATE);

        //Guardo los shared preferences en variables
        String name = SPref.getString("Nombre", "N/H");
        String id = SPref.getString("Cedula", "N/H");
        String type = SPref.getString("Tipo", "N/H");
        String email = SPref.getString("Correo", "N/H");
        String script = SPref.getString("Descripción", "N/H");

        //Los mando a los TextView
        Nombre1.setText(name);
        Cedula1.setText(id);
        Correo1.setText(email);
        Tipo1.setText(type);
        Desc1.setText(script);
    }
}

