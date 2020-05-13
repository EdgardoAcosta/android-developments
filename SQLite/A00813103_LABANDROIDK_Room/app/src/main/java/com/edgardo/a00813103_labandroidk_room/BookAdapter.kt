package com.edgardo.a00813103_labandroidk_room

import Database.Converters
import Database.Libro
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row.view.*

class BookAdapter(val libros: List<Libro>, val listener: CustomItemClickListener) : RecyclerView.Adapter<BookAdapter.LibroViewHolder>() {

    lateinit var libro: Libro
    private var numberOfItems = libros.size

    override fun onCreateViewHolder(ViewGroup: ViewGroup, p1: Int): LibroViewHolder {
        val bookViewHolder: LibroViewHolder

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
            itemView.text_fecha_publicacion.text = Converters.toString(libro.fecha_pulicacion)
            itemView.image_libro.setImageBitmap(Converters.toBitmap(libro.imagenLibro))

        }

        override fun onClick(p0: View?) {
            val book = libros[adapterPosition]
            listener.onCustomItemClickListener(book)
        }
    }

}
