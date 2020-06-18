package com.example.ensitec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuario = database.child("usuario");
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private EditText user, pass, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.senha);
        //user = (EditText) findViewById(R.id.user);

    }

    public void logar(View view) {
        startActivity(new Intent(this, Login.class));
    }

    public void cadastrar() {
        final String emailnovo = email.getText().toString();
        final String senhanova = pass.getText().toString();
        final String usuarionovo = user.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(emailnovo,senhanova)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            usuario.child(usuarionovo).child("senha").setValue(senhanova);
                            usuario.child(usuarionovo).child("id").setValue(usuarionovo);
                            usuario.child(usuarionovo).child("email").setValue(emailnovo);
                            Toast.makeText(MainActivity.this, "Usuário criado com sucesso!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivity.this, Menu.class));
                        }else{
                            AlertDialog.Builder mensagem = new AlertDialog.Builder(MainActivity.this);
                            mensagem.setTitle("Usuário não cadastrado");
                            mensagem.setMessage("Email inválido");
                            mensagem.show();
                        }
                    }
                });
    }

    public void usuarioExistente(View view){
        String emailnovo = email.getText().toString();
        String senhanova = pass.getText().toString();
        String usuarionovo = user.getText().toString();
        if(!campoVazio(emailnovo,senhanova,usuarionovo) && eEmail(emailnovo) && senhaCerta(senhanova)){
            Query query = usuario.orderByChild("id").equalTo(user.getText().toString()).limitToFirst(1);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Usuario usu = dataSnapshot.child(user.getText().toString()).getValue(Usuario.class);
                    if(dataSnapshot.exists()){
                        System.out.println(usu.id);
                        AlertDialog.Builder mensagem = new AlertDialog.Builder(MainActivity.this);
                        mensagem.setTitle("Erro");
                        mensagem.setMessage("Usuário já existe!");
                        mensagem.show();
                    }else{
                        emailExistente();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    AlertDialog.Builder mensagem = new AlertDialog.Builder(MainActivity.this);
                    mensagem.setTitle("Erro");
                    mensagem.setMessage("Ocorreu um erro!");
                    mensagem.show();
                }
            });
        }else{
            if(campoVazio(emailnovo,senhanova,usuarionovo)){
                AlertDialog.Builder mensagem = new AlertDialog.Builder(MainActivity.this);
                mensagem.setTitle("Erro");
                mensagem.setMessage("Campos Nulos!");
                mensagem.show();
            }else if(!eEmail(emailnovo)){
                AlertDialog.Builder mensagem = new AlertDialog.Builder(MainActivity.this);
                mensagem.setTitle("Erro");
                mensagem.setMessage("Email não possui características de email!");
                mensagem.show();
            }else if(!senhaCerta(senhanova)){
                AlertDialog.Builder mensagem = new AlertDialog.Builder(MainActivity.this);
                mensagem.setTitle("Erro");
                mensagem.setMessage("A senha deve ter no mínimo 8 caracteres!");
                mensagem.show();
            }
        }
    }
    public void emailExistente(){
        String emailnovo = email.getText().toString();
        Query query = usuario.orderByChild("email").equalTo(emailnovo).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usu = dataSnapshot.getValue(Usuario.class);
                if(dataSnapshot.exists()){
                    System.out.println(usu.email);
                    AlertDialog.Builder mensagem = new AlertDialog.Builder(MainActivity.this);
                    mensagem.setTitle("Erro");
                    mensagem.setMessage("Email já existe!");
                    mensagem.show();
                }else{
                    cadastrar();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                AlertDialog.Builder mensagem = new AlertDialog.Builder(MainActivity.this);
                mensagem.setTitle("Erro");
                mensagem.setMessage("Ocorreu um erro!");
                mensagem.show();
            }
        });
    }

    public boolean campoVazio(String usuario, String senha, String email){
        boolean vazio = false;
        if(usuario.isEmpty() || senha.isEmpty() || email.isEmpty()){
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
    public boolean senhaCerta(String senha){
        boolean senhaCerta = false;
        if(senha.length() >= 8){
            senhaCerta = true;
        }
        return senhaCerta;
    }

    public static class Usuario{
        public String senha;
        public String id;
        public String email;

        public Usuario(){

        }

        public Usuario(String senha, String id, String email){
            setSenha(senha);
            setId(id);
            setEmail(email);
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

}
