package edu.neit.jonathandoolittle.shellsitter.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

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
 * @version 0.1 - 9/21/2021
 *
 */

@Entity
data class PetModel(
    @PrimaryKey(autoGenerate = true)
    val petId: Long = 0L,
    val name: String = "PET NAME",
    val species: String = "PET SPECIES",
    val gotchaDay: LocalDate? = null,
    val photoUri: String = "URI",
    val feedingTimer: LocalTime? = null,
    val feedingNotes: String? = null
) {}

