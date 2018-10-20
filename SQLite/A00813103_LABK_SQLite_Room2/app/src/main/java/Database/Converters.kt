package Database

import android.arch.persistence.room.TypeConverter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*


class Converters {
    companion object {
        const val DATE_FORMAT: String = "yyyy/MM/dd"

        @TypeConverter
        @JvmStatic
        fun toString(date: Date?): String? {
            val format = SimpleDateFormat(DATE_FORMAT)
            return format.format(date)
        }

        @JvmStatic
        @TypeConverter
        fun toDate(dateString: String): Date? {
            return if (dateString == null) null else SimpleDateFormat(DATE_FORMAT).parse(dateString)
        }


        @TypeConverter
        fun toByteArray(bitmap: Bitmap): ByteArray {
            val stream = ByteArrayOutputStream()

            return if (bitmap == null){
                ByteArray(0)
            } else{
                bitmap.compress(Bitmap.CompressFormat.JPEG ,100,stream)
                stream.toByteArray()
            }
        }

        @TypeConverter
        fun toBitmap(image: ByteArray): Bitmap {
            return BitmapFactory.decodeByteArray(image, 0, image.size)
        }


    }
}