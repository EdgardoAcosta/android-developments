package com.edgardo.lostpets

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import java.text.SimpleDateFormat

import kotlinx.android.synthetic.main.activity_detail.*
import java.util.Calendar

class DetailActivity : AppCompatActivity() {

    companion object {

        const val TAKE_PHOTO_REQUEST = 2
        const val PHOTO_REQUEST_PERMISSION = 3
        const val HAS_THUMBNAIL_KEY = ""
        const val IMAGE_THUMBNAIL_KEY = ""
        const val NEW_REGISTER: String = "show_add"
        const val EDIT_REGISTER: String = "show_edit"

    }

    var hasThumbnail = false
    var thumbnailBitmap: Bitmap? = null
    var checkBoxStatus = false
    lateinit var action: String
    var p: Pet? = null


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
                p = extras.getParcelable(MainActivity.PET_KEY)!!

                // Function to parse information and set values
                showDetail(p!!)

                // Set spinner
                spinner_race.setSelection(p!!.Race)
                spinner.isEnabled = false
                spinner.isClickable = false

            }
            "addPet" -> {
                // Set bar name
                supportActionBar!!.title = resources.getString(R.string.detail_add_actionbar_name)

                // Hide favorite button
                checkBox_favorites.visibility = View.GONE

                image_foto.isEnabled = true
                image_foto.isClickable = true

                // hide label for date added
                label_date_found.visibility = View.GONE

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
                if (action == "addPet") {
                    registerNewPet()
                } else if (action == "showDetail") {
                    updatePet()
                }
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
    @SuppressLint("SimpleDateFormat")
    private fun registerNewPet() {
        if (validateInputs()) {
            val name = label_name.text.toString()
            val race = spinner_race.selectedItemPosition
            val location = label_address.text.toString()

            //Create new date of creation
            val df = SimpleDateFormat("dd-MM-yy -  h:mm a")
            val date = df.format(Calendar.getInstance().getTime()).toString()
            val phone = label_phone.text.toString()
            val email = label_email.text.toString()
            val favorite = if (checkBox_favorites.isChecked) 1 else 0


            val newPet =
                Pet(name, race, location, date, phone, email, Converters.toByteArray(thumbnailBitmap!!), favorite)

            val intent = Intent(this, MainActivity::class.java)

            intent.putExtra(NEW_REGISTER, newPet)
            setResult(Activity.RESULT_OK, intent)

            // Retun to main
            finish()
        }
    }

    private fun updatePet() {
        // TODO: Agregar query para actualizar una mascota
        val intent = Intent(this, MainActivity::class.java)
        p!!.Favorite = if (checkBox_favorites.isChecked) 1 else 0
        val newPet = p
        intent.putExtra(EDIT_REGISTER, newPet)
        setResult(Activity.RESULT_OK, intent)
        finish()
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

        // set date of added
        label_date_found.text = p.DateFound

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
            val drawable = BitmapFactory.decodeResource(resources, R.drawable.default_pet)

            thumbnailBitmap = drawable
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


