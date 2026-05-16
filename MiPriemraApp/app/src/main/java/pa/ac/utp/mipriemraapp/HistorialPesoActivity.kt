package pa.ac.utp.mipriemraapp

import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HistorialPesoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_peso)

        val lvHistorial = findViewById<ListView>(R.id.lvHistorial)

        // Fuente de datos estática
        val listaRegistros = listOf(
            RegistroPeso("01/01/2024", 95.5, 42.4),
            RegistroPeso("16/01/2024", 93.8, 31.8),
            RegistroPeso("31/01/2024", 92.0, 31.2),
            RegistroPeso("15/02/2024", 90.5, 30.7),
            RegistroPeso("01/03/2024", 88.0, 29.8),
            RegistroPeso("14/06/2024", 72.0, 24.4),
            RegistroPeso("29/07/2024", 67.5, 22.9)
        )

        // Inicializar adaptador y conectarlo al ListView [cite: 111, 116]
        val adapter = PesoAdapter(this, listaRegistros)
        lvHistorial.adapter = adapter
    }
}