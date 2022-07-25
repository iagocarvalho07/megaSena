package com.example.megasena

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.megasena.databinding.ActivityMainBinding
import kotlin.math.log
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)


        // buscar objetos e ter referencia para eles
        val editText: EditText = findViewById(R.id.Imput_Number)
        val txtResult: TextView = findViewById(R.id.text_result)
        val btnGenerator: Button = findViewById(R.id.button_generator)

        //banco de dados de resultados para o jogo
        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", null)
        if (result != null){
            txtResult.text = "ultima aposta  $result"
        }

        // metodo mais simples bloco de codigo
        btnGenerator.setOnClickListener {
           // aqui podemos colocar a logica de programação porque sera disparado depois do touth do usuario
           // Log.i("teste", "testando o clique segundo metodo")

            val text = editText.text.toString()

            Button_Generator(text, txtResult)
        }


        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    private fun Button_Generator(text: String, textResult: TextView) {
       if (text.isNotBlank()){

           val  qtd = text.toInt() // converter string para int

           if (qtd in 6..15){

               val numbers = mutableListOf<Int>()
               val random = Random

               while (true){
                   val number = random.nextInt(60) // 0,, 59
                   numbers.add(number + 1)

                   if (numbers.size == qtd){
                       break
                   }
               }

               textResult.text = numbers.joinToString(" - ")

               val editor = prefs.edit()
               editor.putString("result", textResult.text.toString())
               editor.apply() // sincrona commit = assicrona

           }else {
               Toast.makeText(this, "digite um numero valido", Toast.LENGTH_SHORT).show()
           }
       }else{
           Toast.makeText(this, "Informe um numero entre 6 e 15" , Toast.LENGTH_SHORT).show()
       }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

   /*fun buttonClicked(view: View) {
        Log.i("teste", "testando o clique")
    }*/

}