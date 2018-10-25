package com.edgardo.lostpets

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.edgardo.database.Pet
import com.edgardo.database.PetDatabase
import com.edgardo.database.PetDataTest
import kotlinx.android.synthetic.main.activity_main.*
import com.edgardo.networkUtility.Executor.Companion.ioThread
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_pet -> {
                supportActionBar!!.title = "HOME"
                status = 0
                spinner_search.visibility = View.GONE
                val pet = instanceDatabase.petDao().loadAllPets()
                pet.observe(this, Observer<List<Pet>> { pets ->
                    petListFragment.pets = pets ?: emptyList()
                    petListFragment.onPetClick = ::petClickShow
                })

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite -> {
                supportActionBar!!.title = "FAVORITES"
                status = 1
                spinner_search.visibility = View.GONE

                val pet = instanceDatabase.petDao().loadFavoritePets()
                pet.observe(this, Observer<List<Pet>> { pets ->
                    petListFragment.pets = pets ?: emptyList()
                    petListFragment.onPetClick = ::petClickShow
                })
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                supportActionBar!!.title = "SEARCH"
                spinner_search.visibility = View.VISIBLE

                status = 2
                val pet = instanceDatabase.petDao().searchPet(selectedRace)
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
    var status = 0

    val petListFragment = PetListFragment()
    var selectedRace = 0

    companion object {
        const val PET_KEY: String = "pet key"
        const val ACTION_KEY: String = "action to do"
        var REQUEST_DETAIL_SHOW: Int = 10
        var REQUEST_DETAIL_ADD: Int = 11


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.title = "HOME"
        spinner_search.visibility = View.GONE

        //<editor-fold desc="Adapter Spinner">
        val spinner = findViewById<Spinner>(R.id.spinner_search)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.races_array, android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedRace = position
//                Toast.makeText(
//                    applicationContext,
//                    applicationContext.resources.getStringArray(R.array.races_array)[position],
//                    Toast.LENGTH_SHORT
//                ).show()
                if(status == 2){
                    val pet = instanceDatabase.petDao().searchPet(position)
                    pet.observe(this@MainActivity, Observer<List<Pet>> { pets ->
                        petListFragment.pets = pets ?: emptyList()
                        petListFragment.onPetClick = ::petClickShow
                    })
                }


            }

        }


        //</editor-fold>

        instanceDatabase = PetDatabase.getInstance(this)

        ioThread {
            val petNum = instanceDatabase.petDao().getAnyPet()
            if (petNum == 0) {
                insertPets()
            } else {
                loadAllPets()
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

    private fun petClickShow(pet: Pet) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(PET_KEY, pet)
        intent.putExtra(ACTION_KEY, "showDetail")
        startActivityForResult(intent, REQUEST_DETAIL_SHOW)

    }

    // Start -> set initial data
    private fun insertPets() {
        val pets_list: List<Pet> = PetDataTest(applicationContext).petList
        Log.d("Sizes list ", pets_list.size.toString())
        ioThread {
            instanceDatabase.petDao().insertPetkList(pets_list)
            loadAllPets()
        }
    }

    private fun loadAllPets() {
        ioThread {
            val pet = instanceDatabase.petDao().loadAllPets()
            pet.observe(this, Observer<List<Pet>> { pets ->
                petListFragment.pets = pets ?: emptyList()
                petListFragment.onPetClick = ::petClickShow
            })
        }
    }

    private fun loadPetsFavorites() {
        ioThread {
            val pet = instanceDatabase.petDao().loadFavoritePets()
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
                    Toast.makeText(
                        applicationContext,
                        applicationContext.getString(R.string.main_msg_pet_added),
                        Toast.LENGTH_SHORT
                    ).show()

                    val newPet = data!!.getParcelableExtra(DetailActivity.NEW_REGISTER) as Pet
                    Log.d("Test", "test")
                    ioThread {
                        val pet = instanceDatabase.petDao().insertPet(newPet)
                        loadAllPets()
                    }

                    // TODO: Recargar la lista


                }

            }
            REQUEST_DETAIL_SHOW -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        Toast.makeText(
                            applicationContext,
                            applicationContext.getString(R.string.main_msg_pet_updated),
                            Toast.LENGTH_SHORT
                        ).show()
                        val newPet = data.getParcelableExtra(DetailActivity.EDIT_REGISTER) as Pet
                        ioThread {
                            val pet = instanceDatabase.petDao().updatePet(newPet)
                            if (status == 0) {
                                loadAllPets()
                            } else if (status == 1) {
                                loadPetsFavorites()
                            }
                        }
                    } else {
                        Toast.makeText(applicationContext, "Error updating pet", Toast.LENGTH_SHORT).show()
                    }

                }

            }
        }
    }


}