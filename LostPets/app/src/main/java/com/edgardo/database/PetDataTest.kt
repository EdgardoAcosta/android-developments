//////////////////////////////////////////////////////////
//Class: PetDataTest
// Description: Dummy data to test the application
// Author: Edgardo Acosta Leal
// Date created: 22/10/2018
// Last modification: 25/10/2018
//////////////////////////////////////////////////////////
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
import java.text.SimpleDateFormat
import java.util.*

// CLass to create default data
@TypeConverters(Converters::class)
class PetDataTest(val context: Context) {
    val petList: MutableList<Pet> = ArrayList()

    init {
        dataList()
    }

    private fun getBitmap(imageId: Int): Bitmap = BitmapFactory.decodeResource(context.resources, imageId)

    fun dataList() {
        val image = BitmapFactory.decodeResource(context.resources, R.drawable.default_pet)
        val df = SimpleDateFormat("dd-MM-yy -  h:mm a")
            val date = df.format(Calendar.getInstance().time).toString()
        petList.add(
            Pet(
                "Perrito 1", 1, "Calle numero 1",
                date, "+52123456", "mail@gmail.com",
                Converters.toByteArray(image),
                1
            )
        )

        petList.add(
            Pet(
                "Perrito 2", 2, "Calle numero 2",
                date, "+52123456", "mail@gmail.com",
                Converters.toByteArray(image),
                0
            )
        )
        petList.add(
            Pet(
                "Perrito 3", 3, "Calle numero 3",
                date, "+52123456", "mail@gmail.com",
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