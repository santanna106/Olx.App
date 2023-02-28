package com.example.olxapp.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View;
import com.example.olxapp.databinding.ActivityCadastroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class CadastroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun acessar(view: View){

        var email = binding.editCadastroEmail.text.toString()
        var senha = binding.editCadastroSenha.text.toString()
        Log.d("Email",email)
        var validaCampos = validarCampos(email,senha)

        if(validaCampos){
            if(binding.switchAcesso.isChecked()) {
                auth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener{
                        cadastro ->
                    if(cadastro.isSuccessful){
                        val snackbar = Snackbar.make(view,"Sucesso ao cadastrar usuário!",Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.BLUE)
                        snackbar.show()

                        binding.editCadastroEmail.setText("")
                        binding.editCadastroSenha.setText("")
                    }
                }.addOnFailureListener{exception ->
                    val mensagemErro = when(exception){
                        is FirebaseAuthWeakPasswordException -> "Digite uma senha com no mínimo 6(seis) caracteres!"
                        is FirebaseAuthInvalidCredentialsException -> "Digite um email válido!"
                        is FirebaseAuthUserCollisionException -> "Essa conta já foi cadastrada!"
                        is FirebaseNetworkException -> "Sem conexão com a internet"
                        else -> {
                            "Erro ao cadastrar usuário!"
                        }

                    }

                    val snackbar = Snackbar.make(view,mensagemErro,Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()

                }
            } else {
                auth.signInWithEmailAndPassword(email,senha).addOnCompleteListener { login ->

                    if(login.isSuccessful){
                        val snackbar = Snackbar.make(view,"Logado com Sucesso!",Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.BLUE)
                        snackbar.show()

                        startActivity(Intent( this, AnunciosActivity::class.java))

                        binding.editCadastroEmail.setText("")
                        binding.editCadastroSenha.setText("")
                    } else {
                        val snackbar = Snackbar.make(view,"Error ao fazer o login!",Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.RED)
                        snackbar.show()
                    }
                }
            }
        } else {
            val snackbar = Snackbar.make(view,"Preencha todos os campos",Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.show()
        }
    }

    fun validarCampos(email:String,senha:String): Boolean{
        var camposValidados: Boolean = true
        if(email == null || email.equals("")){
            camposValidados = false
        } else if (senha == null || senha.equals("")){
            camposValidados = false
        }
        return camposValidados
    }

}