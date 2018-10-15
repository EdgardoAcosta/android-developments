package com.edgardo.a00813103_labandroidk_recyclerview

class LibroData {


    var listaLibros: ArrayList<Libro> = ArrayList()

    init {
        dataList()
    }

    fun dataList(){

        listaLibros.add(Libro("Libro 1","12345","12/12/1993",R.drawable.book1))
        listaLibros.add(Libro("Libro 2","67890","12/12/1993",R.drawable.book2))
        listaLibros.add(Libro("Libro 3","11121","12/12/1993",R.drawable.book3))
        listaLibros.add(Libro("Libro 4","123323","12/12/1993",R.drawable.book3))
        listaLibros.add(Libro("Libro 5","23433","12/12/1993",R.drawable.book3))
        listaLibros.add(Libro("Libro 6","345634","12/12/1993",R.drawable.book3))
        listaLibros.add(Libro("Libro 7","11121","12/12/1993",R.drawable.book3))
        listaLibros.add(Libro("Libro 8","756734","12/12/1993",R.drawable.book3))
        listaLibros.add(Libro("Libro 9","11121","12/12/1993",R.drawable.book3))
        listaLibros.add(Libro("Libro 10","47563","12/12/1993",R.drawable.book3))
        listaLibros.add(Libro("Libro 1","4567","12/12/1993",R.drawable.book3))
        listaLibros.add(Libro("Libro 12","1174563121","12/12/1993",R.drawable.book3))
    }
}