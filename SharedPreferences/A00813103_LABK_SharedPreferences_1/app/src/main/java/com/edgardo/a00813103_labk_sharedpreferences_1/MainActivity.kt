package com.edgardo.a00813103_labk_sharedpreferences_1

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {


    companion object {
        var NAME_KEY: String = "name"
        var EMAIL_KEY: String = "email"
    }

    lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_save.setOnClickListener { click(it) }

        sharedPref = this.getSharedPreferences("Preferences", Context.MODE_PRIVATE) ?: return
        val defaultValueName = resources.getString(R.string.default_value_name)
        val defaultValueEmail = resources.getString(R.string.default_value_email)
        text_name.setText(sharedPref.getString(NAME_KEY, defaultValueName))
        text_email.setText(sharedPref.getString(EMAIL_KEY, defaultValueEmail))

    }

    private fun click(view: View?) {

        with(sharedPref.edit()) {
            putString(NAME_KEY, text_name.text.toString())
            putString(EMAIL_KEY, text_email.text.toString())
            commit()
        }
        Toast.makeText(this, "Saved!", Toast.LENGTH_LONG).show()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
