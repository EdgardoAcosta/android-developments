package com.edgardo.a00813103_labandroidk_recyclerview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row.view.*


class MainActivity : AppCompatActivity(), CustomItemClickListener {

    private val books: ArrayList<Libro> = LibroData().listaLibros

    companion object {
        const val BOOK_KEY: String = "titulo"
        const val ID_IMAGE_KEY: String = "idImage"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this)
        recyclerview_lista_libros.layoutManager = layoutManager

        val adapter = BookAdapter(books, this)
        recyclerview_lista_libros.adapter = adapter
        adapter.notifyDataSetChanged()


    }

    override fun onCustomItemClickListener(libro: Libro) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(BOOK_KEY, libro.titulo)
        intent.putExtra(ID_IMAGE_KEY, libro.idImagen)
        startActivity(intent)
    }

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
            itemView.text_fecha_publicacion.text = libro.fecha_publicacion
            itemView.image_libro.setImageResource(libro.idImagen)

        }

        override fun onClick(p0: View?) {
            val book = libros[adapterPosition]
            listener.onCustomItemClickListener(book)
        }
    }

}

