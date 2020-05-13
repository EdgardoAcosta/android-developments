package Database

import android.arch.persistence.room.TypeConverters
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import com.edgardo.a00813103_labandroidk_room.R
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.math.log


@TypeConverters(Converters::class)
class LibroData(val context: Context) {
    val libroList: MutableList<Libro> = ArrayList()

    init {
        dataList()
    }
    private fun getBitmap(imageId:Int): Bitmap = BitmapFactory.decodeResource(context.resources, imageId)

    fun dataList(){
        libroList.add(Libro("Swift programming","123456789",
                Converters.toDate("2018/01/23"),
                Converters.toByteArray(getBitmap(R.drawable.android_book)),
                saveImage( Converters.toByteArray(getBitmap(R.drawable.android_book)))
        ))

        libroList.add(Libro("Swift programming","987654321",
                Converters.toDate("2017/02/12"),
                Converters.toByteArray(getBitmap(R.drawable.swift_book)),
                saveImage(  Converters.toByteArray(getBitmap(R.drawable.swift_book)))
        ))
        libroList.add(Libro("Haskell programming","147258369",
                Converters.toDate("2018/11/01"),
                Converters.toByteArray(getBitmap(R.drawable.haskell_book)),
                saveImage( Converters.toByteArray(getBitmap(R.drawable.haskell_book)))
        ))

    }
    private fun saveImage(image: ByteArray):String{

        val photo = File(Environment.getExternalStorageDirectory(), "${UUID.randomUUID()}.jpg")
        Log.d("SavedImage", "${photo.exists()}")
        if (photo.exists()) {
//            photo.delete()
        }
        val path = photo.path
        Log.d("SavedImage Path", photo.path)

        try {
            val fos = FileOutputStream(path)
            fos.write(image)
            fos.close()
        } catch (e: java.io.IOException) {
            Log.e("PictureTest", "Exception in photoCallback", e)
        }
        return path

    }

}