package com.edgardo.listviewpersonalizada

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    companion object {
        const val TITYLO_KEY: String = "titulo"
        const val ID_IMAGE_KEY: String = "idImage"
    }

    val libros: ArrayList<Libro> = LibroData().listaLibros

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = LibroAdapter(this, libros)
        list_libro.adapter = adapter
        adapter?.notifyDataSetChanged()
        list_libro.setOnItemClickListener(this)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val libro: Libro = libros[position]
        val intent = Intent(this, DetailActivity::class.java)

        intent.putExtra(TITYLO_KEY, libro.titulo)
        intent.putExtra(ID_IMAGE_KEY, libro.idImagen)
        startActivity(intent)
    }
}

class LibroAdapter(context: Context, libros: ArrayList<Libro>) : ArrayAdapter<Libro>(context, 0, libros) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {


        val rowView = LayoutInflater.from(context).inflate(R.layout.row, parent, false)

        val libro = getItem(position)

        val tvTitulo = rowView.findViewById(R.id.text_titulo) as TextView
        val tvIsbn = rowView.findViewById(R.id.text_isbn) as TextView
        val tvFecha = rowView.findViewById(R.id.text_fecha_publicacion) as TextView
        val ivLibro = rowView.findViewById(R.id.image_libro) as ImageView

        tvTitulo.text = libro.titulo
        tvIsbn.text = libro.isbn
        tvFecha.text = libro.fecha_publicacion
        ivLibro.setImageResource(libro.idImagen)

        return rowView
    }
}
