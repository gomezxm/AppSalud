package pa.ac.utp.mipriemraapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class cardPresion : AppCompatActivity() {

    private lateinit var btnFecha: Button
    private lateinit var btnHora: Button
    private lateinit var npSistolica: NumberPicker
    private lateinit var npDiastolica: NumberPicker
    private lateinit var npPulso: NumberPicker
    private lateinit var rgBrazo: RadioGroup
    private lateinit var txtResultado: TextView
    private lateinit var btnAnalizar: Button

    private var fechaSeleccionada: String = ""
    private var horaSeleccionada: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_card_presion)

        // Inicialización
        btnFecha = findViewById(R.id.btnFecha)
        btnHora = findViewById(R.id.btnHora)
        npSistolica = findViewById(R.id.npSistolica)
        npDiastolica = findViewById(R.id.npDiastolica)
        npPulso = findViewById(R.id.npPulso)
        rgBrazo = findViewById(R.id.rgBrazo)
        txtResultado = findViewById(R.id.txtResultado)
        btnAnalizar = findViewById(R.id.btnAnalizar)

        // Configuración de NumberPickers (Obligatorio poner min y max)
        npSistolica.apply { minValue = 50; maxValue = 220; value = 120 }
        npDiastolica.apply { minValue = 30; maxValue = 150; value = 80 }
        npPulso.apply { minValue = 30; maxValue = 200; value = 70 }

        // Listeners
        btnFecha.setOnClickListener { mostrarDatePicker() }
        btnHora.setOnClickListener { mostrarTimePicker() }
        btnAnalizar.setOnClickListener { analizarMedicion() }
    }

    private fun mostrarDatePicker() {
        val c = Calendar.getInstance()
        val picker = DatePickerDialog(this, { _, year, month, day ->
            fechaSeleccionada = "$day/${month + 1}/$year"
            btnFecha.text = fechaSeleccionada
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
        picker.show()
    }

    private fun mostrarTimePicker() {
        val c = Calendar.getInstance()
        val picker = TimePickerDialog(this, { _, hour, minute ->
            horaSeleccionada = String.format("%02d:%02d", hour, minute)
            btnHora.text = horaSeleccionada
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true)
        picker.show()
    }

    private fun analizarMedicion() {
        if (fechaSeleccionada.isEmpty() || horaSeleccionada.isEmpty()) {
            Toast.makeText(this, "Seleccione fecha y hora", Toast.LENGTH_SHORT).show()
            return
        }

        val seleccionadoId = rgBrazo.checkedRadioButtonId
        if (seleccionadoId == -1) {
            Toast.makeText(this, "Seleccione el brazo", Toast.LENGTH_SHORT).show()
            return
        }

        val sistolica = npSistolica.value
        val diastolica = npDiastolica.value
        val pulso = npPulso.value
        val brazo = findViewById<RadioButton>(seleccionadoId).text
        val clasificacion = clasificarPresion(sistolica, diastolica)

        txtResultado.text = """
            RESUMEN:
            Fecha: $fechaSeleccionada | Hora: $horaSeleccionada
            Presión: $sistolica/$diastolica mmHg
            Pulso: $pulso BPM ($brazo)
            Resultado: $clasificacion
        """.trimIndent()
    }

    private fun clasificarPresion(sistolica: Int, diastolica: Int): String {
        return when {
            sistolica < 90 || diastolica < 60 -> "Hipotensión (Baja)"
            sistolica in 90..119 && diastolica in 60..79 -> "Normal"
            sistolica in 120..129 && diastolica < 80 -> "Elevada"
            sistolica in 130..139 || diastolica in 80..89 -> "Hipertensión Nivel 1"
            else -> "Hipertensión Nivel 2"
        }
    }
}