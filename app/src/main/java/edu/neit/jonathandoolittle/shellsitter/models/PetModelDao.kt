package edu.neit.jonathandoolittle.shellsitter.models

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 *
 * Interface Description - TODO
 *
 * @author Jonathan Doolittle
 * @version 0.1 - 9/22/2021
 *
 */

@Dao
interface PetModelDao {

    @Insert
    suspend fun insert(vararg petModels: PetModel)

    @Delete
    suspend fun delete(vararg petModels: PetModel)

    @Update
    suspend fun update(petModel: PetModel)

    @Query("SELECT * FROM petmodel WHERE petId = :id LIMIT 1")
    fun getById(id: Long): PetModel

    @Query("SELECT * FROM petmodel")
    fun getAll(): LiveData<List<PetModel>>

}