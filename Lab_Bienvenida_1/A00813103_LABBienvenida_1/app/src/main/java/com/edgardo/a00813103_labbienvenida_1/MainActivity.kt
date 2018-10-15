package com.edgardo.a00813103_labbienvenida_1

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.content.Context.INPUT_METHOD_SERVICE
import android.support.v4.content.ContextCompat.getSystemService




class MainActivity : AppCompatActivity() {


    var idImagenes: IntArray? = null

    private var mensaje: String? = null
    private var indiceImagenes: Int = 0

    companion object {
        private const val MSG_LABEL = "mensaje"
        private const val IMG_LABEL = "image"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        idImagenes = intArrayOf(R.drawable.image1, R.drawable.image2, R.drawable.image3)


        button_saludar.setOnClickListener { v -> click(v) }
        button_mostrar.setOnClickListener { click(it) }

    }


    override fun onSaveInstanceState(savedInstanceState: Bundle?) {
        super.onSaveInstanceState(savedInstanceState)

        savedInstanceState?.putString(MSG_LABEL, mensaje)
        savedInstanceState?.putInt(IMG_LABEL, indiceImagenes)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        mensaje = savedInstanceState?.getString(MSG_LABEL)
        indiceImagenes = savedInstanceState?.getInt(IMG_LABEL)!!

        text_mensaje_saludar?.text = mensaje
        image_foto.setImageDrawable(getDrawable(idImagenes!![indiceImagenes]))
    }

    fun click(view: View) {


        when (view.id) {
            R.id.button_mostrar -> {
                val rand: Random = Random()
                indiceImagenes = rand.nextInt(3)
                var imagenBitmap = BitmapFactory.decodeResource(resources, idImagenes!![indiceImagenes]);
                image_foto.setImageBitmap(imagenBitmap)

            }
            R.id.button_saludar -> {
                val nombre = edit_nombre.text.toString()
                mensaje = resources.getString(R.string.mensaje_bienvenida_1) +
                        nombre +
                        resources.getString(R.string.mensaje_bienvenida_2)
                text_mensaje_saludar.text = mensaje

            }
        }
    }
}
