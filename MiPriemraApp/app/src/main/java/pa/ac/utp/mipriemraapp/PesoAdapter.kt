package pa.ac.utp.mipriemraapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class PesoAdapter(private val context: Context, private val data: List<RegistroPeso>): BaseAdapter() {

    override fun getCount(): Int = data.size
    override fun getItem(position: Int): Any = data[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_historial_peso, parent, false)

        val item = data[position]
        val ivIcono = view.findViewById<ImageView>(R.id.ivIconoIMC)
        val tvFecha = view.findViewById<TextView>(R.id.tvFecha)
        val tvPesoIMC = view.findViewById<TextView>(R.id.tvPesoIMC)

        // Llenar datos con formato
        tvFecha.text = "Fecha: ${item.fecha}"
        tvPesoIMC.text = String.format(
            Locale.getDefault(), "Peso: %.1f kg | IMC: %.1f",
            item.peso, item.imc
        )

        // Lógica dinámica para el icono según el IMC
        val nombreIcono = when {
            item.imc < 18.5 -> "bajopeso"   // ← era "pesobajo", nombre incorrecto
            item.imc < 25.0 -> "normal"
            item.imc < 30.0 -> "sobrepeso"
            item.imc < 35.0 -> "obesidad1"
            item.imc < 40.0 -> "obesidad2"
            else             -> "obesidad3"
        }

        // Obtener el ID del drawable dinámicamente
        val resId = context.resources.getIdentifier(nombreIcono, "drawable", context.packageName)
        if (resId != 0) {
            ivIcono.setImageResource(resId)
        } else {
            ivIcono.setImageResource(android.R.drawable.ic_menu_info_details)
        }

        return view  // ← antes era return TODO("Provide the return value")
    }
}