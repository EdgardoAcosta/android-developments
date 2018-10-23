package com.edgardo.lostpets

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.edgardo.database.Converters
import com.edgardo.database.Pet
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {

        const val TAKE_PHOTO_REQUEST = 2
        const val PHOTO_REQUEST_PERMISSION = 3
        const val HAS_THUMBNAIL_KEY = ""
        const val IMAGE_THUMBNAIL_KEY = ""
    }

    var hasThumbnail = false
    var thumbnailBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Start -> Adapter for spinner

        val spinner = findViewById<Spinner>(R.id.spinner_race)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.races_array, android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // End -> Adapter for spinner

        // Start -> Get data from intent

        val extras = intent.extras ?: return

        when (extras.getString(MainActivity.ACTION_KEY)) {
            "showDetail" -> {
                val p: Pet = extras.getParcelable(MainActivity.PET_KEY)!!

                // Set bar name
                supportActionBar!!.title = resources.getString(R.string.detail_show_actionbar_name) + p.Name

                // Set Name
                label_name.setText(p.Name)
                disableEditText(label_name)

                // Set spinner
                spinner_race.setSelection(p.Race)
                spinner.isEnabled = false
                spinner.isClickable = false

                // Set image
                image_foto.setImageBitmap(Converters.toBitmap(p.ImagePet!!))
                button_image.isEnabled = false
                button_image.isClickable = false

                // Set email
                label_email.setText(p.Email)
                disableEditText(label_email)

                // Set phone
                label_phone.setText(p.Phone)
                disableEditText(label_phone)

                // set address
                label_address.setText(p.LocationFound)
                disableEditText(label_address)

                // set checkbox
                checkBox_favorites.isChecked = (p.Favorite == 1)
                checkBox_favorites.isEnabled = false
                checkBox_favorites.isClickable = false

                // hide buttons
                button_save.visibility = View.GONE


            }
            "addPet" -> {
                // Set bar name
                supportActionBar!!.title = resources.getString(R.string.detail_add_actionbar_name)
            }
            "editDetail" -> {

            }
        }


        // End -> Get data from intent


    }

    // Strat -> take photo
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        hasThumbnail = false

        if (requestCode == TAKE_PHOTO_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                thumbnailBitmap = data?.extras?.get("data") as Bitmap
                hasThumbnail = true
                image_foto.setImageBitmap(thumbnailBitmap)


            } else {
                // Image Canceled
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)

        outState?.putBoolean(HAS_THUMBNAIL_KEY, hasThumbnail)
        if (hasThumbnail) {
            outState?.putParcelable(IMAGE_THUMBNAIL_KEY, thumbnailBitmap)
        }

    }
    // End -> take photo


    // Start -> Helpers
    private fun disableEditText(editText: EditText) {
        editText.isFocusable = false
        editText.isEnabled = false
        editText.isCursorVisible = false
        editText.keyListener = null
//        editText.setBackgroundColor(Color.TRANSPARENT)
    }

    fun finishActivity(v: View) {
        finish()
    }

    // End -> Helpers


}


