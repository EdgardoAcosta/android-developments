package com.edgardo.a00813103_labandroidk_room

import Database.Converters
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*
import java.io.File
//import com.sun.tools.corba.se.idl.Util.getAbsolutePath
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.util.Log
import java.nio.file.Files.exists


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val extras = intent.extras ?: return
//        val _id = extras.getInt(MainActivity.BOOK_KEY)
        val title = extras.getString(MainActivity.BOOK_TITLE)
        val isbn = extras.getString(MainActivity.ISBN)
        val date = extras.getString(MainActivity.FECHA_PUBLICACION)
        val pathImage = extras.getString(MainActivity.IMAGE_PATH)




        text_name.text = title
        text_isbn.text = isbn
        text_date.text = date
        supportActionBar!!.title = title


        val imgFile =  File(pathImage)
        if (imgFile.exists()) {
            val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
            image_libroDetail.setImageBitmap(myBitmap)

        }
        else {
            val idImagen = Converters.toBitmap(extras.getByteArray(MainActivity.ID_IMAGE_KEY)!!)
            image_libroDetail.setImageBitmap(idImagen)
            Log.d("ErrorPhoto", "No photo found")
        }

        button_ok.setOnClickListener { view ->
            this.finish()
        }
    }
}
