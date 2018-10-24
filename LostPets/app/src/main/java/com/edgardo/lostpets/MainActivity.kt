package com.edgardo.lostpets

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.edgardo.database.Pet
import com.edgardo.database.PetDatabase
import com.edgardo.database.PetDataTest
import kotlinx.android.synthetic.main.activity_main.*
import com.edgardo.networkUtility.Executor.Companion.ioThread
import android.widget.Toast
import android.widget.Toast.makeText


class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_pet -> {
                supportActionBar!!.title = "HOME"

                val pet = instanceDatabase.petDao().loadAllPets()
                pet.observe(this, Observer<List<Pet>> { pets ->
                    petListFragment.pets = pets ?: emptyList()
                    petListFragment.onPetClick = ::petClickShow
                })

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite -> {
                supportActionBar!!.title = "FAVORITES"

                val pet = instanceDatabase.petDao().loadFavoritePets()
                pet.observe(this, Observer<List<Pet>> { pets ->
                    petListFragment.pets = pets ?: emptyList()
                    petListFragment.onPetClick = ::petClickDetail
                })
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                supportActionBar!!.title = "SEARCH"
                val pet = instanceDatabase.petDao().searchPet(3)
                pet.observe(this, Observer<List<Pet>> { pets ->
                    petListFragment.pets = pets ?: emptyList()
                    petListFragment.onPetClick = ::petClickShow
                })
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    lateinit var instanceDatabase: PetDatabase
    lateinit var adapter: PetAdapter

    val petListFragment = PetListFragment()

    companion object {
        const val PET_KEY: String = "pet key"
        const val ACTION_KEY: String = "action to do"
        const val NAME_KEY: String = "name"
        const val RACE_KEY: String = "race"
        const val EMAIL_KEY: String = "email"
        const val PHONE_KEY: String = "phone"
        const val FAVORITE_KEY: String = "favorites"
        const val IMAGE_KEY: String = "id_image"
        const val DATE_KEY: String = "date found"
        const val LOCATION_KEY: String = "location found"
        var REQUEST_DETAIL_SHOW: Int = 10
        var REQUEST_DETAIL_ADD: Int = 11


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.title = "HOME"

        instanceDatabase = PetDatabase.getInstance(this)

        ioThread {
            val petNum = instanceDatabase.petDao().getAnyPet()
            if (petNum == 0) {
                insertPets()
            } else {
                loadPets()
            }

        }

        floatingActionButton_add.setOnClickListener { v: View? ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(ACTION_KEY, "addPet")
            startActivityForResult(intent, REQUEST_DETAIL_ADD)
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        with(supportFragmentManager.beginTransaction()) {
            add(R.id.content, petListFragment)
            commit()
        }
    }

    fun petClickShow(pet: Pet) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(PET_KEY, pet)
        intent.putExtra(ACTION_KEY, "showDetail")
        startActivityForResult(intent, REQUEST_DETAIL_SHOW)

    }
    fun petClickDetail(pet: Pet) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(PET_KEY, pet)
        intent.putExtra(ACTION_KEY, "editDetail")
        startActivityForResult(intent, REQUEST_DETAIL_SHOW)

    }

    // Start -> set initial data
    private fun insertPets() {
        val pets_list: List<Pet> = PetDataTest(applicationContext).petList
        Log.d("Sizes list ", pets_list.size.toString())
        ioThread {
            instanceDatabase.petDao().insertPetkList(pets_list)
            loadPets()
        }
    }

    private fun loadPets() {
        ioThread {
            val pet = instanceDatabase.petDao().loadAllPets()
            pet.observe(this, Observer<List<Pet>> { pets ->
                petListFragment.pets = pets ?: emptyList()
                petListFragment.onPetClick = ::petClickShow
            })
        }
    }

    // End -> set initial data

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_DETAIL_ADD -> {
                if (resultCode == Activity.RESULT_OK) {
                    makeText(applicationContext, "Return from add", Toast.LENGTH_SHORT)
                    // TODO: Recargar la lista

                }

            }
            REQUEST_DETAIL_SHOW -> {
                if (resultCode == Activity.RESULT_OK) {
                    makeText(applicationContext, "Return from show", Toast.LENGTH_SHORT)
                    // TODO: Recargar la lista 

                }

            }
        }
    }


}