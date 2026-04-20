package pa.ac.utp.mipriemraapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainMenu2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_menu2)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuración de los clics para cada CardView
        configurarClic(R.id.cardPeso, "PESO")
        configurarClic(R.id.cardPresion, "PRESION")
        configurarClic(R.id.cardGlucosa, "GLUCOSA")
        configurarClic(R.id.cardActividad, "ACTIVIDAD")
        configurarClic(R.id.cardAgua, "AGUA")
        configurarClic(R.id.cardMedicina, "MEDICINA")
    }

    private fun configurarClic(id: Int, destino: String) {
        findViewById<CardView>(id).setOnClickListener {
            val intent = Intent(this, TransitionActivity::class.java)
            intent.putExtra("DESTINO", destino)
            startActivity(intent)
        }
    }
}