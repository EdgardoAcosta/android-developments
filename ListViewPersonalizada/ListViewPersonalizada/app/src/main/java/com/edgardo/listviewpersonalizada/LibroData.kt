package com.edgardo.listviewpersonalizada

class LibroData {


    var listaLibros: ArrayList<Libro> = ArrayList()

    init {
        dataList()
    }

    fun dataList(){

        listaLibros.add(Libro("Libro 1","12345","12/12/1993",R.drawable.book1))
        listaLibros.add(Libro("Libro 2","67890","12/12/1993",R.drawable.book2))
        listaLibros.add(Libro("Libro 3","11121","12/12/1993",R.drawable.book3))
    }
}