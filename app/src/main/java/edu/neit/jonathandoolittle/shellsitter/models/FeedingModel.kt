package edu.neit.jonathandoolittle.shellsitter.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

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

@Entity
data class FeedingModel(
    @PrimaryKey(autoGenerate = true)
    val feedId: Long = 0L,
    val owner: Long,
    val trigger: Date,
    val repeatHours: Int = 24,
    val notes: String?
)