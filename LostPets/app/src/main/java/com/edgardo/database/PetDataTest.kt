package com.edgardo.database

import android.arch.persistence.room.TypeConverters
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import com.edgardo.lostpets.R
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

// CLass to create default data
@TypeConverters(Converters::class)
class PetDataTest(val context: Context) {
    val petList: MutableList<Pet> = ArrayList()

    init {
        dataList()
    }

    private fun getBitmap(imageId: Int): Bitmap = BitmapFactory.decodeResource(context.resources, imageId)

    fun dataList() {
        val image = BitmapFactory.decodeResource(context.resources,R.drawable.default_pet)
        petList.add(
            Pet(
                "Perrito 1", 1, "Calle numero 1",
                "2018/01/23", "+52123456", "mail@gmail.com",
                Converters.toByteArray(image),
                1
            )
        )

        petList.add(
            Pet(
                "Perrito 2", 2, "Calle numero 2",
                "2018/01/23", "+52123456", "mail@gmail.com",
                Converters.toByteArray(image),
                0
            )
        )
        petList.add(
            Pet(
                "Perrito 3", 3, "Calle numero 3",
                "2018/01/23", "+52123456", "mail@gmail.com",
                Converters.toByteArray(image),
                0
            )
        )

    }

    // Function to save image on device, return the saved path
    private fun saveImage(image: ByteArray): String {

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