package NetworkUtility

import android.net.Uri
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class NetworkConnection {
    companion object {
        const val BASE_URL = "http://api.openweathermap.org"
        const val API_KEY = "b530dca615437c67c7a12fa5138ed501"


        fun buildUrl(lat: Double, lon: Double): URL? {
            val builtUri: Uri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath("data")
                    .appendPath("2.5")
                    .appendPath("forecast")
                    .appendQueryParameter("lat", lat.toString())
                    .appendQueryParameter("lon", lon.toString())
                    .appendQueryParameter("APPID", API_KEY)
                    .build()

            var url: URL? = null
            try {
                url = URL(builtUri.toString())
                Log.d("tag 1 " , builtUri.toString())
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }

            return url
        }

        @Throws(IOException::class)
        fun getResponsefromHttpUrl(url: URL): String? {

            val urlConnection = url.openConnection() as HttpURLConnection

            try {
                val input: InputStream = urlConnection.inputStream

                val scanner = Scanner(input)
                scanner.useDelimiter("\\A")

                return if (scanner.hasNext()) {
                    scanner.next()
                } else {
                    null
                }
            } finally {
                urlConnection.disconnect()
            }
        }
    }
}