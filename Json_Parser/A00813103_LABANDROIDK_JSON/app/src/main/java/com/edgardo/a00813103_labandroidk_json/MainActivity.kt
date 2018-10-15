package com.edgardo.a00813103_labandroidk_json

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.URL
import kotlinx.coroutines.experimental.*

import NetworkUtility.NetworkConnection
import android.support.v7.app.AlertDialog
import kotlinx.coroutines.experimental.android.UI

class MainActivity : AppCompatActivity() {

    lateinit var url: URL
    var data: String? = null
    companion object {
        val LAT: Double = 25.6504639
        val LON: Double = -100.2900726
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        text_scroll.movementMethod = ScrollingMovementMethod()

        try {
            url = NetworkConnection.buildUrl(LAT, LON)!!
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        button_get.setOnClickListener { onClick(it) }
        button_parse.setOnClickListener { onClick(it) }

    }

    fun onClick(view: View?) {
        when (view?.id) {
            R.id.button_get -> {
                if (isNetworkConnected()){
                    weatherCoroutime()
                }
                else{
                    alertMessage()
//                    Toast.makeText(applicationContext, "No Internet Connection", Toast.LENGTH_SHORT).show()
                }

            }
            R.id.button_parse -> {
                if (data != null) {
                    text_scroll.text = parseJson(data!!)
                } else {
                    Toast.makeText(applicationContext, "No Data Found!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun weatherCoroutime() {
        launch(UI) {
            data = async(context = CommonPool) { NetworkConnection.getResponsefromHttpUrl(url) }.await()!!
            text_scroll.text = data
        }
    }

    private fun isNetworkConnected(): Boolean {

        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    protected fun parseJson(jsonString: String): String {

        var outputData = ""

        val jsonObject = JSONObject(jsonString)
        try {

            val dataJsonObject = jsonObject.getJSONObject("city")
            val city = dataJsonObject.getString("name")
            val county = dataJsonObject.getString("country")

            val dataJsonArray = jsonObject.getJSONArray("list")

            for (i in 0 until dataJsonArray.length()) {
                val itemJson = dataJsonArray.getJSONObject(i)

                val objectMain = itemJson.getJSONObject("main")
                val temp = objectMain.getDouble("temp")
                val tempMax = objectMain.getDouble("temp_max")
                val tempMin = objectMain.getDouble("temp_min")

                outputData += "\n$city | $county | $temp | $tempMax | $tempMin | "
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return outputData
    }
    fun alertMessage(){

        // Initialize a new instance of
        val builder = AlertDialog.Builder(this@MainActivity)

        // Set the alert dialog title
        builder.setTitle("No Internet Connection")

        // Display a message on alert dialog
        builder.setMessage("Please check your internet connection and try again")

//        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("ACEPTAR"){dialog, which ->
            // Do something when user press the positive button
//            Toast.makeText(applicationContext,"Ok, we change the app background.",Toast.LENGTH_SHORT).show()


        }


        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }
}
