package com.francisco.minhahorta

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.francisco.minhahorta.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var myReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        window.statusBarColor = Color.parseColor("#00BF63")

        database = FirebaseDatabase.getInstance()
        myReference = database.reference

        myReference.child("sensorData").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (sensorSnapshot in dataSnapshot.children) {
                    when (sensorSnapshot.key) {
                        "temperatura" -> {
                            val temperatura = sensorSnapshot.value.toString().toFloat()
                            binding.txtTemperatura.text = temperatura.toString()
                        }
                        "umidade" -> {
                            val umidade = sensorSnapshot.value.toString().toInt()
                            binding.txtPorcentagemAr.text = umidade.toString()
                        }
                        "solo" -> {
                            val umidadeSolo = sensorSnapshot.value.toString().toInt()
                            binding.txtPorcentagem.text = umidadeSolo.toString()
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(applicationContext, "Falha ao obter dados do banco de dados", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

