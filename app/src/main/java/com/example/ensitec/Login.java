package com.example.ensitec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuario = database.child("usuario");
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private EditText login, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (EditText) findViewById(R.id.txt_login);
        pass = (EditText) findViewById(R.id.txt_senha);
    }

    public void entrar(View view) {
        final String loginnovo = login.getText().toString();
        String senhanova = pass.getText().toString();
        if(!campoVazio(loginnovo,senhanova)) {
            if(eEmail(loginnovo)){
                firebaseAuth.signInWithEmailAndPassword(loginnovo,senhanova)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(Login.this, "Sucesso no login!", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(Login.this, Menu.class));
                                }else{
                                    AlertDialog.Builder mensagem = new AlertDialog.Builder(Login.this);
                                    mensagem.setTitle("Erro");
                                    mensagem.setMessage("Email ou senha incorreta!");
                                    mensagem.show();
                                }
                            }
                        });
            }else {
                Query query = usuario.orderByChild("senha").equalTo(senhanova);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Usuario usu = dataSnapshot.child(loginnovo).getValue(Usuario.class);
                        try{
                            System.out.println(usu.senha);
                            startActivity(new Intent(Login.this, Menu.class));
                        }catch(Exception ex){
                            AlertDialog.Builder mensagem = new AlertDialog.Builder(Login.this);
                            mensagem.setTitle("Erro");
                            mensagem.setMessage("Usuário ou senha incorreta!");
                            mensagem.show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        AlertDialog.Builder mensagem = new AlertDialog.Builder(Login.this);
                        mensagem.setTitle("Erro");
                        mensagem.setMessage("Ocorreu um erro!");
                        mensagem.show();
                    }
                });
            }
        }
    }

    public boolean campoVazio(String usuario, String senha){
        boolean vazio = false;
        if(usuario.isEmpty() || senha.isEmpty()){
            vazio = true;
        }
        return vazio;
    }

    public boolean eEmail(String email){
        boolean eEmail = false;
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            eEmail = true;
        }
        return eEmail;
    }

    public static class Usuario{
        public String senha;

        public Usuario(){

        }

        public Usuario(String senha){
            setSenha(senha);
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }

    }
}
