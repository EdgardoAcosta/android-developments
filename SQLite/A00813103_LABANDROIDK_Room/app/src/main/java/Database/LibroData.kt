package Database

import android.arch.persistence.room.TypeConverters
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.edgardo.a00813103_labandroidk_room.R
import java.sql.Date
import java.util.ArrayList


@TypeConverters(Converters::class)
class LibroData(val context: Context) {
    val libroList: MutableList<Libro> = ArrayList()

    init {
        dataList()
    }
    private fun getBitmap(imageId:Int): Bitmap = BitmapFactory.decodeResource(context.resources, imageId)

    fun dataList(){
        libroList.add(Libro("Swift programming","123456789",
                Converters.toDate("2018/01/23") as Date?,
                Converters.getByteArray(getBitmap(R.drawable.android_book)) ))

        libroList.add(Libro("Swift programming","987654321",
                Converters.toDate("2017/02/12") as Date?,
                Converters.getByteArray(getBitmap(R.drawable.swift_book)) ))

        libroList.add(Libro("Haskell programming","147258369",
                Converters.toDate("2018/11/01") as Date?,
                Converters.getByteArray(getBitmap(R.drawable.haskell_book)) ))

    }

}