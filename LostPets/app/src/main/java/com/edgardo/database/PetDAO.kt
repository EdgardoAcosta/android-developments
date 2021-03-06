//////////////////////////////////////////////////////////
//Class: PetDAO
// Description: Definition of what each sql command should do
// Author: Edgardo Acosta Leal
// Date created: 22/10/2018
// Last modification: 25/10/2018
//////////////////////////////////////////////////////////

package com.edgardo.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

// Data access object
@Dao
interface PetDAO {

    // Get all pets in the table
    @Query("SELECT * FROM Pet ORDER BY Name")
    fun loadAllPets(): LiveData<List<Pet>>

    @Query("SELECT * FROM Pet WHERE Favorite = 1 ORDER BY Name")
    fun loadFavoritePets() : LiveData<List<Pet>>

    @Query("SELECT * FROM Pet WHERE Race = :race")
    fun searchPet(race: Int): LiveData<List<Pet>>

    @Query("SELECT COUNT (*) FROM Pet")
    fun getAnyPet(): Int

    // Insert List of pets
    @Insert
    fun insertPetkList(PetList: List<Pet>)

    // Insert single pet
    @Insert
    fun insertPet(pet: Pet)

    // Update information of a pet
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePet(pet: Pet)

    // Delete single pet from table
    @Delete
    fun deletePet(pet: Pet)

}