package com.curvelo.loginaute.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.curvelo.loginaute.R;
import com.curvelo.loginaute.Util.ConfiguracaoBd;
import com.curvelo.loginaute.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {

    Usuario usuario;
    FirebaseAuth autentificacao;
    EditText campoNome, campoEmail,campoSenha;
    Button botaoCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        getSupportActionBar().hide();
            Inicializar();

    }
    private void Inicializar(){
        campoNome = findViewById(R.id.editTextName);
        campoEmail = findViewById(R.id.editTextEmail);
        campoSenha = findViewById(R.id.editTextSenha);
        botaoCadastrar = findViewById(R.id.buttonCadastrar);

    }

    public void validarCampos(View v){
        String nome = campoNome.getText().toString();
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        if (!nome.isEmpty()){
            // is Empty é vazio ou esta vazio.
            // ! diferente
            if (!email.isEmpty()){
                if (!senha.isEmpty()){

                    usuario = new Usuario();

                    usuario.setNome(nome);
                    usuario.setEmail(email);
                    usuario.setSenha(senha);


                    cadastarusuario();

                }else{
                    Toast.makeText(this, "Preencha a senha", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(this, "Preencha o email", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Prencha o nome", Toast.LENGTH_SHORT).show();
        }

    }

    private void cadastarusuario() {
        autentificacao = ConfiguracaoBd.firebaseAutentificacao();
        autentificacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){
                Toast.makeText(CadastroActivity.this, "Sucesso ao cadastrar o usuario", Toast.LENGTH_SHORT).show();
            } else{
               String excessao = "";
               try {
                    throw task.getException();
               }catch (FirebaseAuthWeakPasswordException e){
                   excessao = "Digite uma senha forte";
               }catch (FirebaseAuthInvalidCredentialsException e){
                   excessao = "Digete um email valido";
               } catch (FirebaseAuthUserCollisionException e){
                   excessao = "Esta conta ja existe";
               }catch (Exception e){
                   excessao = "Erro ao cadastrar"+ e.getMessage();
                   e.printStackTrace();
               }
                Toast.makeText(CadastroActivity.this, excessao, Toast.LENGTH_SHORT).show();
            }
            }
        });
    }
}