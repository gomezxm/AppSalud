package pa.ac.utp.mipriemraapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat

class CardPeso : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_card_peso)

        // 1. Referencias de la UI
        val etEdad = findViewById<EditText>(R.id.etEdad)
        val etPeso = findViewById<EditText>(R.id.etPeso)
        val etEstatura = findViewById<EditText>(R.id.etEstatura)
        val swPeso = findViewById<SwitchCompat>(R.id.swPesoUnit)
        val swEstatura = findViewById<SwitchCompat>(R.id.swEstaturaUnit)
        val btnCalcular = findViewById<Button>(R.id.btnCalcular)
        val tvIMC = findViewById<TextView>(R.id.tvImcVal)
        val tvPesoIdeal = findViewById<TextView>(R.id.tvPesoIdealVal)
        val tvGrasa = findViewById<TextView>(R.id.tvGrasaVal)
        val tvClasificacion = findViewById<TextView>(R.id.tvClasificacion)
        val btnHistorial = findViewById<Button>(R.id.btnHistorial)

        // 2. Listeners dinámicos para los Hints
        swPeso.setOnCheckedChangeListener { _, isChecked ->
            etPeso.hint = if (isChecked) "Peso (Lb)" else "Peso (Kg)"
            etPeso.text.clear()
        }
        swEstatura.setOnCheckedChangeListener { _, isChecked ->
            etEstatura.hint = if (isChecked) "Estatura (in)" else "Estatura (cm)"
            etEstatura.text.clear()
        }

        btnHistorial.setOnClickListener {
            val intent = Intent(this, HistorialPesoActivity::class.java)
            startActivity(intent)
        }

        // 3. Listener del botón
        btnCalcular.setOnClickListener {
            val sEdad = etEdad.text.toString()
            val sPeso = etPeso.text.toString()
            val sEstatura = etEstatura.text.toString()

            // Validación de campos vacíos
            if (sEdad.isEmpty() || sPeso.isEmpty() || sEstatura.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Salir del listener si falta información
            }

                val edad = sEdad.toInt()
                var peso = sPeso.toDouble()
                var estatura = sEstatura.toDouble()

                // Normalización de Peso a KG
                if (swPeso.isChecked) {
                    peso *= 0.453592
                }

                // Normalización de Estatura a CM
                if (swEstatura.isChecked) {
                    estatura *= 2.54
                }

                // Cálculos
                val estaturaMetros = estatura / 100
                if (estaturaMetros == 0.0) return@setOnClickListener

                val imc = peso / (estaturaMetros * estaturaMetros)
                val pesoIdeal = 22 * (estaturaMetros * estaturaMetros)
                val grasa = (1.20 * imc) + (0.23 * edad) - 16.2

                // 4. Mostrar resultados
                tvIMC.text = String.format("%.1f", imc)
                tvPesoIdeal.text = String.format("%.1f kg", pesoIdeal)
                tvGrasa.text = String.format("%.1f%%", grasa)
                tvClasificacion.text = categorizarIMC(imc)

        }
    }

    private fun categorizarIMC(imc: Double): String {
        return when {
            imc < 18.5 -> "Bajo Peso"
            imc < 25 -> "Normal"
            imc < 30 -> "Sobrepeso"
            imc < 35 -> "Obesidad I"
            imc < 40 -> "Obesidad II"
            else -> "Obesidad III"
        }
    }
}