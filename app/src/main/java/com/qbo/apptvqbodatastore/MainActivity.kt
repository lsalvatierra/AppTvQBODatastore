package com.qbo.apptvqbodatastore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope

import com.qbo.apptvqbodatastore.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var userManager: LoginMaganer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Get reference to our userManager class
        userManager = LoginMaganer(applicationContext)

        binding.btnlogin.setOnClickListener {
            // Gets the user input and saves it
            storeUser()
        }

        // this function retrieves the saved data
        // as soon as they are stored and even
        // after app is closed and started again
        observeData()

    }

    // this function saves the data to
    // preference data store on click of
    // save Button
    private fun storeUser() {
        val usuario = binding.etusuario.text.toString()
        val password = binding.etpassword.text.toString()

        // Stores the values
        // Since the storeUser function of UserManager
        // class is a suspend function
        // So this has to be done in a coroutine scope
        lifecycleScope.launch {
            userManager.storeUser(usuario, password)
            Toast.makeText(
                this@MainActivity,
                "User Saved",
                Toast.LENGTH_SHORT
            ).show()

            //clear edit text
            binding.etusuario.text.clear()
            binding.etpassword.text.clear()
            //
        }
        //startActivity(Intent(this@MainActivity, HomeActivity::class.java))
    }

private fun observeData() {
/**
         * Updates age
         * every time user age changes it will be observed by userAgeFlow
         * here it refers to the value returned from the userAgeFlow function
         * of UserManager class
 */
        userManager.userNameFlow.asLiveData().observe(this) { usu ->
            usu?.let {
                binding.tvnombres.text = "Nombres: $usu"
            }
        }
    userManager.userAgeFlow.asLiveData().observe(this) { edad ->
        edad?.let {
            binding.tvapellidos.text = "Apellidos: $edad"
        }
    }
    }

}