package com.edgardo.a00813103_labandroidk_room

import Database.Libro
import Database.LibroData
import Database.LibroDatabase
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row.view.*
import java.util.concurrent.Executors
import sun.security.krb5.Confounder.bytes
import android.graphics.BitmapFactory



private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

class MainActivity : AppCompatActivity(), CustomItemClickListener  {

    var instanceDatabase = LibroDatabase
    var adapter = BookAdapter()


    companion object {
        const val BOOK_KEY: String = "_id"
        const val BOOK_TITLE: String = "titulo"
        const val ID_IMAGE_KEY: String = "idImage"
        const val FECHA_PUBLICACION: String = "fecha_publicacion"
        const val ISBN: String = "isbn"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this)
        recyclerview_lista_libros.layoutManager = layoutManager


        ioThread {
            val libros = instanceDatabase!!.libroDao().loadAllBooks()
            if (libros.isEmpty()){
                insertBooks(this)
            }else{
                loadBooks()
            }

        }
    }

    private fun loadBooks(){
        ioThread{
            val libros = instanceDatabase.libroDao()?.loadAllBooks()
            runOnUiThread{
                adapter.setLibro(libros)
                recyclerview_lista_libros.adapter?.notifyDataSetChange()
            }
        }
    }
     private fun insertBooks(){
         val libros: List<Libro> = LibroData(applicationContext).libroList
        ioThread{
            instanceDatabase?.libroDao()?.insertBookList(libros)
            runOnUiThread{
                adapter.setLibro(libros)
                recyclerview_lista_libros.adapter?.notifyDataSetChange()
            }
        }
    }
    override fun onCustomItemClickListener(libro: Libro) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(BOOK_KEY, libro.titulo)
        intent.putExtra(ISBN, libro.isbn)
        intent.putExtra(FECHA_PUBLICACION, libro.fecha_pulicacion)
        intent.putExtra(ID_IMAGE_KEY, libro.imagenLibro)
        startActivity(intent)
    }

}

fun ioThread(f: () -> Unit){
    IO_EXECUTOR.execute(f)
}

interface CustomItemClickListener {
    fun onCustomItemClickListener(libro: Libro)
}


class BookAdapter(val libros: List<Libro>, val listener: CustomItemClickListener) : RecyclerView.Adapter<BookAdapter.LibroViewHolder>() {

    lateinit var libro: Libro
    private var numberOfItems = libros.size

    override fun onCreateViewHolder(ViewGroup: ViewGroup, p1: Int): LibroViewHolder {
        val bookViewHolder: BookAdapter.LibroViewHolder

        val rowView = LayoutInflater.from(ViewGroup.context).inflate(R.layout.row, ViewGroup, false)

        bookViewHolder = LibroViewHolder(rowView)

        return bookViewHolder


    }

    override fun getItemCount(): Int {
        return numberOfItems
    }

    override fun onBindViewHolder(holder: LibroViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class LibroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        //Assign data

        fun bind(index: Int) {
            libro = libros[index]

            itemView.text_titulo.text = libro.titulo
            itemView.text_isbn.text = libro.isbn
            itemView.text_fecha_publicacion.text = libro.fecha_pulicacion.toString()
            itemView.image_libro.setImageBitmap(Bitmap.createBitmap(libro.imagenLibro) )

        }

        override fun onClick(p0: View?) {
            val book = libros[adapterPosition]
            listener.onCustomItemClickListener(book)
        }
    }

}

