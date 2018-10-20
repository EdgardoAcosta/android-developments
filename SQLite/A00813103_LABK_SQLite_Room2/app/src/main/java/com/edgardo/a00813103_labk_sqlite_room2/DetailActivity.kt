package com.edgardo.a00813103_labk_sqlite_room2

import Database.Converters
import Database.Libro
import NetworkUtility.Executor
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*
import java.io.File
//import com.sun.tools.corba.se.idl.Util.getAbsolutePath
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.util.Log
import kotlinx.android.synthetic.main.row.*
import java.nio.file.Files.exists


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val extras = intent.extras ?: return

        val _id = extras.getInt(MainActivity.BOOK_KEY)
        val title = extras.getString(MainActivity.BOOK_TITLE)
        val isbn = extras.getString(MainActivity.ISBN)
        val date = extras.getString(MainActivity.FECHA_PUBLICACION)


        text_name.text = title
        text_isbn.text = isbn
        text_date.text = date
        val idImagen = Converters.toBitmap(extras.getByteArray(MainActivity.ID_IMAGE_KEY)!!)
        image_libroDetail.setImageBitmap(idImagen)


        button_ok.setOnClickListener { view ->
            this.finish()
        }
    }
    private fun populateUI(libroLive: LiveData<Libro>){

        libroLive.observe(this, object : Observer<Libro>{
            override fun onChanged(libro: Libro?){

                libroLive.removeObserver(this)
                text_titulo.text = libro?.titulo

                supportActionBar!!.title = libro?.titulo

                text_isbn_d.text = libro?.isbn
                text_fecha_publicacion.text = Converters.toString(libro?.fecha_pulicacion)
                image_libro.setImageBitmap(Converters.toBitmap(libro?.imagenLibro!!))
            }
        })
    }
}
