package com.edgardo.listviewsimple

import android.app.ListActivity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    lateinit var frutasIds: IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        frutasIds = intArrayOf(R.drawable.banana, R.drawable.pinapple, R.drawable.orange,
                R.drawable.watermelon)

        val stringFrutas = resources.getStringArray(R.array.array_fruits)

        val adapterFrutas = ArrayAdapter(this,
                android.R.layout.simple_list_item_1, stringFrutas)


        list_fruits.adapter = adapterFrutas
        list_fruits.onItemClickListener = this
//        listAdapter = adapterFrutas
//        listView.onItemClickListener = this
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val frutaId = frutasIds[position]

        val imagenFruta = getDrawable(frutaId)

        image_fruta.setImageDrawable(imagenFruta)
        Toast.makeText(applicationContext, "${resources.getString(R.string.app_toaster)} ${parent!!.getItemAtPosition(position)}",
                Toast.LENGTH_SHORT).show()
    }
}
