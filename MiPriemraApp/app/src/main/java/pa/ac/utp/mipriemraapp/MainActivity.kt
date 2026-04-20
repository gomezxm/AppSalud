package pa.ac.utp.mipriemraapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Configurar el padding para que respete las barras del sistema (status bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencia al botón del XML
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)

        // Al presionar el botón, vamos al menú de los CardViews
        btnEntrar.setOnClickListener {
            val intent = Intent(this, MainMenu2::class.java)
            startActivity(intent)
            // No usamos finish() aquí por si el usuario quiere regresar a la bienvenida
        }
    }
}