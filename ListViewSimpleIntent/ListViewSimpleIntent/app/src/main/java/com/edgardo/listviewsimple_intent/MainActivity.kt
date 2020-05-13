package com.edgardo.listviewsimple_intent

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    lateinit var frutasIds: IntArray
    var frutaName = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        frutasIds = intArrayOf(R.drawable.banana, R.drawable.pinapple, R.drawable.orange,
                R.drawable.watermelon)

        val stringFrutas = resources.getStringArray(R.array.array_fruits)


        val adapterFrutas = ArrayAdapter(this, android.R.layout.simple_list_item_1,
                stringFrutas)


        list_frutas.adapter = adapterFrutas
        list_frutas.onItemClickListener = this

    }


    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        frutaName = frutasIds!![position]
        Log.d("Fruta name", "${parent!!.getItemAtPosition(position)}")

//        val FRUTA_POSITION_LABEL = position
//        val FRUTA_NAME_LABEL = frutaName


        val intent = Intent(applicationContext, DetailActivity::class.java)
        intent.putExtra("FRUTA_POSITION_LABEL", position)
        intent.putExtra("FRUTA_NAME_LABEL", parent!!.getItemAtPosition(position).toString())
        startActivity(intent)

        Toast.makeText(applicationContext, "${resources.getString(R.string.app_toaster)} " +
                "${parent!!.getItemAtPosition(position)}", Toast.LENGTH_SHORT).show()

    }
}
