package com.edgardo.a00813103_labandroidk_room

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.row.view.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val extras = intent.extras ?: return
        val _id = extras.getString(MainActivity.BOOK_KEY)
        val titulo = extras.getString(MainActivity.BOOK_TITLE)
        val isbn = extras.getString(MainActivity.ISBN)
        val fecha = extras.getString(MainActivity.FECHA_PUBLICACION)

        val idImagen = Bitmap.createBitmap( extras.getByteArray(MainActivity.ID_IMAGE_KEY))

        text_name.text = titulo
        text_isbn = isbn
        text_date = fecha

        image_libroDetail.setImageBitmap(idImagen)

    }
}
