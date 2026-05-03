package pa.ac.utp.mipriemraapp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class cardPresion : AppCompatActivity() {

    // 1. Las variables deben declararse a nivel de clase, no dentro de onCreate
    private lateinit var btnFecha: Button
    private lateinit var txtFecha: TextView
    private lateinit var timePicker: TimePicker
    private lateinit var npSistolica: NumberPicker
    private lateinit var npDiastolica: NumberPicker
    private lateinit var npPulso: NumberPicker
    private lateinit var rgBrazo: RadioGroup
    private lateinit var txtResultado: TextView
    private lateinit var btnAnalizar: Button

    // Inicializamos con un string vacío para evitar errores de nulabilidad
    private var fechaSeleccionada: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_card_presion)

        // 2. Inicialización de controles
        btnFecha = findViewById(R.id.btnFecha)
        txtFecha = findViewById(R.id.txtFecha)
        timePicker = findViewById(R.id.timePicker)
        npSistolica = findViewById(R.id.npSistolica)
        npDiastolica = findViewById(R.id.npDiastolica)
        npPulso = findViewById(R.id.npPulso)
        rgBrazo = findViewById(R.id.rgBrazo)
        txtResultado = findViewById(R.id.txtResultado)
        btnAnalizar = findViewById(R.id.btnAnalizar)

        // Configuración de rangos
        timePicker.setIs24HourView(true)
        npSistolica.minValue = 80
        npSistolica.maxValue = 200
        npDiastolica.minValue = 40
        npDiastolica.maxValue = 130
        npPulso.minValue = 40
        npPulso.maxValue = 180

        // Listeners
        btnFecha.setOnClickListener {
            mostrarDatePicker()
        }

        btnAnalizar.setOnClickListener {
            analizarMedicion()
        }
    }

    // 3. Las funciones deben estar fuera del onCreate
    private fun mostrarDatePicker() {
        val calendario = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                fechaSeleccionada = "$dayOfMonth/${month + 1}/$year"
                txtFecha.text = "Fecha: $fechaSeleccionada"
            },
            calendario.get(Calendar.YEAR),
            calendario.get(Calendar.MONTH),
            calendario.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun analizarMedicion() {
        // Validaciones
        if (fechaSeleccionada.isEmpty()) {
            Toast.makeText(this, "Debe seleccionar una fecha", Toast.LENGTH_SHORT).show()
            return
        }

        val seleccionadoId = rgBrazo.checkedRadioButtonId
        if (seleccionadoId == -1) {
            Toast.makeText(this, "Debe seleccionar el brazo de medición", Toast.LENGTH_SHORT).show()
            return
        }

        val sistolica = npSistolica.value
        val diastolica = npDiastolica.value
        val pulso = npPulso.value
        val hora = String.format("%02d:%02d", timePicker.hour, timePicker.minute)

        // Obtener el texto del RadioButton seleccionado
        val brazo = findViewById<RadioButton>(seleccionadoId).text
        val clasificacion = clasificarPresion(sistolica, diastolica)

        txtResultado.text = """
            Resumen de la medición
            
            Fecha: $fechaSeleccionada
            Hora: $hora
            Presión sistólica: $sistolica mmHg
            Presión diastólica: $diastolica mmHg
            Pulso cardíaco: $pulso BPM
            Brazo utilizado: $brazo
            Clasificación: $clasificacion
        """.trimIndent()
    }

    private fun clasificarPresion(sistolica: Int, diastolica: Int): String {
        return when {
            sistolica < 90 || diastolica < 60 -> "Presión baja"
            sistolica in 90..119 && diastolica in 60..79 -> "Presión normal"
            sistolica in 120..129 && diastolica < 80 -> "Presión elevada"
            else -> "Hipertensión"
        }
    }
}