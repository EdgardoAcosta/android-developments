package Database

import android.arch.persistence.room.TypeConverter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.util.*
import android.graphics.Bitmap.CompressFormat
import android.R.attr.bitmap



class Converters {
    companion object {
        const val DATE_FORMAT: String = "yyyy/MM/dd"

        @JvmStatic
        @TypeConverter
        fun toDate(timestamp: String?): Date? {
            return if (timestamp == null) null else Date(timestamp)
        }

        @JvmStatic
        @TypeConverter
        fun getByteArray(image: Bitmap?): ByteArray{
            val stream = ByteArrayOutputStream()

            return if (image == null){
                ByteArray(0)
            } else{
                image.compress(Bitmap.CompressFormat.JPEG ,100,stream)
                stream.toByteArray()
            }

        }


    }
}