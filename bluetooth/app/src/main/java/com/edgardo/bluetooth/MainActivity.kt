package com.edgardo.bluetooth

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.bluetooth.BluetoothAdapter;
import android.content.pm.PackageManager
import android.widget.Toast
import android.content.Intent


class MainActivity : AppCompatActivity() {
    var btAdapter = BluetoothAdapter.getDefaultAdapter()
    var packName: String? = null
    var className: String? = null
    var found: Boolean = false

    companion object {
        const val DISCOVER_DURATION = 300
        const val REQUEST_BLU = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (btAdapter == null) {
            return
            // Device does not support Bluetooth
            // Inform user that we're done.
        }
        val pm: PackageManager = packageManager
        val appsList = pm.queryIntentActivities(intent, 0)
        if (appsList.size > 0) {
            for (info in appsList) {
                packName = info.activityInfo.packageName
                if (packageName == "com.android.bluetooth") {
                    className = info.activityInfo.name
                    found = true
                    break// found
                }
            }

            if (!found) {
                Toast.makeText(this, R.string.blu_notfound_inlist, Toast.LENGTH_SHORT).show()
                // exit
            }

        }
        //set our intent to launch Bluetooth
        intent.setClassName(packageName, className)
        startActivity(intent)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == DISCOVER_DURATION && requestCode == REQUEST_BLU) {
            // processing code goes here
        } else { // cancelled or error
            Toast.makeText(this, R.string.blu_cancelled, Toast.LENGTH_SHORT).show()

        }
    }

    fun enableBluetooth() {
        // enable device discovery - this will automatically enable Bluetooth
        val discoveryIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
        discoveryIntent.putExtra(
            BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,
            DISCOVER_DURATION
        )
        startActivityForResult(discoveryIntent, REQUEST_BLU)


    }
}
