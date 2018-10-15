package com.edgardo.a00813103_tareak_weather

import NetworkUtility.NetworkConnection
import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.URL

class MainActivity : AppCompatActivity() {

    lateinit var url: URL
    private var message: String? = null
    companion object {
        private const val MSG_LABEL = "scroll_label"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar.isIndeterminate = true
        progressBar.visibility = View.GONE

        val spinner = findViewById<Spinner>(R.id.spinner_city)
        val adapter = ArrayAdapter.createFromResource(this,
                R.array.cities_array, android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter



        button_search.setOnClickListener{click(it)}

    }

    private fun click(v: View?){
        when (v?.id) {
            R.id.button_search -> {
                progressBar.visibility = View.VISIBLE
                text_scroll.text = ""
                if (isNetworkConnected()) {
                    try {
                        val spinnerValue = spinner_city.selectedItem.toString()
                        url = NetworkConnection.buildUrlByName(spinnerValue)!!
                        weatherCoroutime()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                } else {
                    alertMessage()
                    progressBar.visibility = View.GONE
//                    Toast.makeText(applicationContext, "No Internet Connection", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun weatherCoroutime() {
        launch(UI) {
            val data = async(context = CommonPool) { NetworkConnection.getResponsefromHttpUrl(url) }.await()!!
            message = parseJson(data)
            text_scroll.text = message
        }
    }

    private fun parseJson(jsonString: String): String {

        var outputData = ""

        val jsonObject = JSONObject(jsonString)
        try {
            val city = jsonObject.getString("name")
            val coordJsonObject = jsonObject.getJSONObject("coord")
            val lon = coordJsonObject.getString("lon")
            val lat = coordJsonObject.getString("lat")

            val main = jsonObject.getJSONObject("main")
            val temp = main.getString("temp")
            val temp_max = main.getString("temp_min")
            val temp_min = main.getString("temp_max")
            val pressure = main.getDouble("pressure")
            val humidity = main.getDouble("humidity")


            val weather = jsonObject.getJSONArray("weather").getJSONObject(0)
            val description = weather.getString("description")

            val wind = jsonObject.getJSONObject("wind")
            val speed = wind.getString("speed")

            val country = jsonObject.getJSONObject("sys").getString("country")




            outputData = "City : $city \n" +
                    "Coord (lon, lat) : $lon,$lat \n" +
                    "Temp : $temp \n" +
                    "Max,Min temp : $temp_max,$temp_min\n" +
                    "Pressure : $pressure\n" +
                    "Humidity: $humidity\n" +
                    "Description : $description\n" +
                    "Wind Speed : $speed \n" +
                    "Country : $country"

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        progressBar.visibility = View.GONE
        return outputData
    }

    private fun isNetworkConnected(): Boolean {

        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun alertMessage(){

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

    override fun onSaveInstanceState(savedInstanceState: Bundle?) {
        super.onSaveInstanceState(savedInstanceState)

        savedInstanceState?.putString(MSG_LABEL, message)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        message = savedInstanceState?.getString(MSG_LABEL)
        text_scroll?.text = message

    }
}
