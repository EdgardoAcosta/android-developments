package com.edgardo.listviewpersonalizada

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val extras = intent.extras ?: return
        val titulo = extras.getString(MainActivity.TITYLO_KEY)
        val idImagen = extras.getInt(MainActivity.ID_IMAGE_KEY)

        val imagenLibro = getDrawable(idImagen)
        image_libroDetail.setImageDrawable(imagenLibro)

        text_name.text = titulo
    }
}
