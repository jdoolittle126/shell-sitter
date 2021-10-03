package edu.neit.jonathandoolittle.shellsitter.domain.models

import androidx.lifecycle.LiveData

/**
 *
 * Class Description - TODO
 *
 * Class Logic - TODO
 *
 * <pre>
 *  Class Usage - TODO
 * </pre>
 *
 * @author Jonathan Doolittle
 * @version 0.1 - 9/22/2021
 *
 */

class PetModelRepository(
    private val petModelDao: PetModelDao
) {

    val allData : LiveData<List<PetModel>> = petModelDao.getAll()

    fun getPetById(id: Long): PetModel {
        return petModelDao.getById(id)
    }

    suspend fun addPet(petModel: PetModel) {
        petModelDao.insert(petModel)
    }

    suspend fun updatePet(petModel: PetModel) {
        petModelDao.update(petModel)
    }

    suspend fun deletePet(petModel: PetModel) {
        petModelDao.delete(petModel)
    }

}