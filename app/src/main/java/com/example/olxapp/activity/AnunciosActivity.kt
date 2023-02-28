package com.example.olxapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.olxapp.R
import com.example.olxapp.databinding.ActivityAnunciosBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class AnunciosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnunciosBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnunciosBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
    //Method called only one once to crate menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    //Metodo chamado toda vez que a activity é apresentada
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if(auth.currentUser == null){
            menu?.setGroupVisible(R.id.group_deslogado,true)
        } else {
            menu?.setGroupVisible(R.id.group_logado,true)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.menu_cadastrar -> {
                startActivity(Intent( this, CadastroActivity::class.java))
                true
            }
            R.id.menu_sair -> {
                auth.signOut()
                invalidateOptionsMenu()
                true
            }
            R.id.menu_anuncios -> {
                startActivity(Intent( this, AnunciosActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

        return super.onOptionsItemSelected(item)
    }


}