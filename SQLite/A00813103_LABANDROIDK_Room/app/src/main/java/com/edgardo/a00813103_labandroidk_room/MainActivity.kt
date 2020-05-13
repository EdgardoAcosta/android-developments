package com.edgardo.a00813103_labandroidk_room

import Database.Converters
import Database.Libro
import Database.LibroData
import Database.LibroDatabase
import NetworkUtility.Executor.Companion.ioThread
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CustomItemClickListener {

    lateinit var instanceDatabase: LibroDatabase
    lateinit var adapter: BookAdapter


    companion object {
        const val ISBN: String = "isbn"
        const val BOOK_KEY: String = "_id"
        const val BOOK_TITLE: String = "titulo"
        const val ID_IMAGE_KEY: String = "idImage"
        const val IMAGE_PATH: String = "pathToImage"
        const val FECHA_PUBLICACION: String = "fecha_publicacion"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        instanceDatabase = LibroDatabase.getInstance(this)

        val layoutManager = LinearLayoutManager(this)
        recyclerview_lista_libros.layoutManager = layoutManager


        ioThread {
            val libros = instanceDatabase.bookDao().loadAllBooks()
            if (libros.isEmpty()) {
                insertBooks()

            } else {
                loadBooks()
            }

        }
    }

    private fun loadBooks() {
        ioThread {
            val libros = instanceDatabase.bookDao().loadAllBooks()
            runOnUiThread {
                adapter = BookAdapter(libros, this)

                recyclerview_lista_libros.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun insertBooks() {
        val libros: List<Libro> = LibroData(applicationContext).libroList
        ioThread {
            instanceDatabase.bookDao().insertBookList(libros)
            runOnUiThread {
                adapter = BookAdapter(libros, this)
                recyclerview_lista_libros.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCustomItemClickListener(libro: Libro) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(BOOK_KEY, libro._id)
        intent.putExtra(BOOK_TITLE, libro.titulo)
        intent.putExtra(ISBN, libro.isbn)
        intent.putExtra(FECHA_PUBLICACION, Converters.toString( libro.fecha_pulicacion))

        intent.putExtra(ID_IMAGE_KEY, libro.imagenLibro)
        intent.putExtra(IMAGE_PATH, libro.imagenLibroPath)
        startActivity(intent)

    }

}


interface CustomItemClickListener {
    fun onCustomItemClickListener(libro: Libro)
}



