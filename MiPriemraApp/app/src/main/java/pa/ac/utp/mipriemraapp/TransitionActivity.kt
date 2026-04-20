package pa.ac.utp.mipriemraapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class TransitionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition) // Crea un XML con un ImageView centrado

        val logo = findViewById<ImageView>(R.id.imgTransitionLogo)
        val destino = intent.getStringExtra("DESTINO")

        // 1. Asignar la imagen según la tarjeta presionada
        when (destino) {
            "PESO" -> logo.setImageResource(R.drawable.ic_peso)
            "PRESION" -> logo.setImageResource(R.drawable.ic_presion)
            "GLUCOSA" -> logo.setImageResource(R.drawable.ic_glucosa)
            "ACTIVIDAD" -> logo.setImageResource(R.drawable.ic_actividad)
            "AGUA" -> logo.setImageResource(R.drawable.ic_agua)
            "MEDICINA" -> logo.setImageResource(R.drawable.ic_medicina)
        }

        // 2. Aplicar una animación (opcional pero recomendado)
        val animacion = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        logo.startAnimation(animacion)

        // 3. Temporizador para ir a la clase real
        Handler(Looper.getMainLooper()).postDelayed({
            val intentFinal = when (destino) {
                "PESO" -> Intent(this, CardPeso::class.java)
                // "PRESION" -> Intent(this, CardPresion::class.java) <-- Crea estas clases después
                else -> Intent(this, MainMenu2::class.java)
            }
            startActivity(intentFinal)
            finish() // Cerramos la transición para no regresar a ella
        }, 2000) // 2 segundos de duración
    }
}