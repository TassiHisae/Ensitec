package com.example.ensitec.MainActivitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.ensitec.Class_Fragment.Class_Fragment_Cadastro;
import com.example.ensitec.Class_Fragment.Class_Fragment_Login;
import com.example.ensitec.Class_Fragment.Class_Fragment_Reset_Pass;
import com.example.ensitec.Login;
import com.example.ensitec.MainActivity;
import com.example.ensitec.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Patterns;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.lang.Object ;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import  com.google.firebase.*;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Activity_Login extends AppCompatActivity {
    Switch aSwitch;

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuario = database.child("usuario");
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private EditText login, pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.Fragmnet_Alpha, new Class_Fragment_Login());
        ft.commit();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        aSwitch = (Switch) findViewById(R.id.Switch_cadastro_login);

        aSwitch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (aSwitch.isChecked()) {


                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();

                    ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    ft.replace(R.id.Fragmnet_Alpha, new Class_Fragment_Cadastro());
                    ft.commit();
                } else {

                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();

                    ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    ft.replace(R.id.Fragmnet_Alpha, new Class_Fragment_Login());
                    ft.commit();

                }
            }
        });

        login = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.senha);

    }

    public void onClickResetPass(View v) {

        aSwitch.setChecked(true);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        ft.replace(R.id.Fragmnet_Alpha, new Class_Fragment_Reset_Pass());
        ft.commit();

    }

    public void Login(View view) {
        System.out.println("login: "+login.getText().toString()+" senha: "+pass.getText().toString());
        String email = login.getText().toString();
        String senha = pass.getText().toString();
        Login l = new Login(email,senha);
//        startActivity(new Intent(this, Activity_Home.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean campoVazio(String usuario, String senha) {
        boolean vazio = false;
        if (usuario.isEmpty() || senha.isEmpty()) {
            vazio = true;
        }
        return vazio;
    }

    public boolean eEmail(String email) {
        boolean eEmail = false;
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            eEmail = true;
        }
        return eEmail;
    }

    public static class Usuario {
        public String senha;

        public Usuario() {

        }


    }
}
