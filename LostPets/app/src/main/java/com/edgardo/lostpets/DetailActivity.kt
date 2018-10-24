package com.edgardo.lostpets

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
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
    var checkBoxStatus = false
    lateinit var action: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //<editor-fold desc="Adapter Spinner">
        val spinner = findViewById<Spinner>(R.id.spinner_race)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.races_array, android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        //</editor-fold>

        //<editor-fold desc="Permissions">

        // Permission for taking picture from camera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            button_image.isEnabled = false

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), PHOTO_REQUEST_PERMISSION)
        }

        //</editor-fold>


        //<editor-fold desc="Get data from intent">
        val extras = intent.extras ?: return
        action = extras.getString(MainActivity.ACTION_KEY)

        when (action) {
            "showDetail" -> {
                val p: Pet = extras.getParcelable(MainActivity.PET_KEY)!!

                // Function to parse information and set values
                showDetail(p)

                // Set spinner
                spinner_race.setSelection(p.Race)
                spinner.isEnabled = false
                spinner.isClickable = false

            }
            "addPet" -> {
                // Set bar name
                supportActionBar!!.title = resources.getString(R.string.detail_add_actionbar_name)

                // Hide favorite button
                checkBox_favorites.visibility = View.GONE

            }
            "editDetail" -> {
                val p: Pet = extras.getParcelable(MainActivity.PET_KEY)!!
                editDetail(p)
                // Set spinner
                spinner_race.setSelection(p.Race)
                spinner.isEnabled = false
                spinner.isClickable = false
            }
        }
        //</editor-fold>

        //<editor-fold desc="Button listener">
        button_image.setOnClickListener { onClick(it) }
        button_save.setOnClickListener { onClick(it) }
        button_cancel.setOnClickListener { onClick(it) }
        checkBox_favorites.setOnClickListener { onClick(it) }
        //</editor-fold>


    }


    //<editor-fold desc="Button click listener">
    fun onClick(view: View) {
        when (view.id) {
            R.id.button_save -> {
                if (action == "addPet"){
                    registerNewPet()
                }
                updatePet()
            }
            R.id.button_cancel -> {
                setResult(RESULT_CANCELED)
                finish()
            }
            R.id.button_image -> {
                // Start camera
                activateCamera()
            }
            R.id.checkBox_favorites -> {
                if (checkBoxStatus != checkBox_favorites.isChecked) {
                    button_save.isEnabled = true
                    button_save.isClickable = true

                } else {
                    button_save.isEnabled = false
                    button_save.isClickable = false
                }

            }
        }
    }
    //</editor-fold>

    // Save values on register
    private fun registerNewPet() {
        if (validateInputs()) {
            // TODO: realizaar query para guardar datos

            setResult(Activity.RESULT_OK)
        }
    }

    private fun updatePet(){
        // TODO: Agregar query para actualizar una mascota
        setResult(Activity.RESULT_OK)
    }


    //<editor-fold desc="Take photo">
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

    private fun activateCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, TAKE_PHOTO_REQUEST)
        }
    }
    //</editor-fold>


    //<editor-fold desc="Helpers">

    // Function to set values in view
    private fun showDetail(p: Pet) {
        // Set bar name
        supportActionBar!!.title = resources.getString(R.string.detail_show_actionbar_name) + p.Name

        // Set Name
        label_name.setText(p.Name)
        disableEditText(label_name)


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

    private fun editDetail(p: Pet) {
        // Set bar name
        supportActionBar!!.title = resources.getString(R.string.detail_edit_actionbar_name) + p.Name

        // Set Name
        label_name.setText(p.Name)
        disableEditText(label_name)


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
        checkBoxStatus = (p.Favorite == 1)
        checkBox_favorites.isChecked = (p.Favorite == 1)

        // Button save disable on enter, change only if checkbox status changes
        button_save.isEnabled = false
        button_save.isClickable = false

    }

    // Disable all features of a input label
    private fun disableEditText(editText: EditText) {
        editText.isFocusable = false
        editText.isEnabled = false
        editText.isCursorVisible = false
        editText.keyListener = null
//        editText.setBackgroundColor(Color.TRANSPARENT)
    }

    // Validate inputs are not empty
    private fun validateInputs(): Boolean {

        var verified = true


        label_name.error = null
        label_email.error = null
        label_phone.error = null
        label_address.error = null

        if (label_name.text.toString().trim().isEmpty()) {
            label_name.error = resources.getString(R.string.detail_msg_required_filed)
            verified = false
        }
        if (!hasThumbnail) {
            val drawable = resources.getDrawable(R.drawable.default_dog) as BitmapDrawable
            val bitmap = drawable.getBitmap();


            thumbnailBitmap = bitmap
        }
        if (label_email.text.toString().trim().isEmpty()) {
            label_email.error = resources.getString(R.string.detail_msg_required_filed)
            verified = false
        }
        if (label_phone.text.toString().trim().isEmpty()) {
            label_phone.error = resources.getString(R.string.detail_msg_required_filed)
            verified = false
        }
        if (label_address.text.toString().trim().isEmpty()) {
            label_address.error = resources.getString(R.string.detail_msg_required_filed)
            verified = false
        }

        return verified

    }

    // On Click of cancel button
    fun finishActivity(v: View) {
        finish()
    }
    //</editor-fold>

}


