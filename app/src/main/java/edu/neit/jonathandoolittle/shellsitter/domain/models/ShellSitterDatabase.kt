package edu.neit.jonathandoolittle.shellsitter.domain.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.time.LocalDate
import java.time.LocalTime
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
@Database(entities = [PetModel::class, FeedingModel::class], version = 4)
@TypeConverters(Converters::class)
abstract class ShellSitterDatabase : RoomDatabase() {

    abstract fun petModelDao(): PetModelDao

    companion object {

        @Volatile private var INSTANCE: ShellSitterDatabase? = null

        fun getInstance(context: Context): ShellSitterDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        // TODO Build real migrations!
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                ShellSitterDatabase::class.java, "ShellSitter.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()

    }
}

class Converters {

    @TypeConverter
    fun fromLongToLocalDate(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    @TypeConverter
    fun fromLocalDateToLong(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }

    @TypeConverter
    fun fromLongToLocalTime(value: Long?): LocalTime? {
        return value?.let { LocalTime.ofSecondOfDay(it) }
    }

    @TypeConverter
    fun fromLocalTimeToLong(time: LocalTime?): Long? {
        return time?.toSecondOfDay()?.toLong()
    }

    @TypeConverter
    fun fromLongToDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun fromDateToLong(date: Date?): Long? {
        return date?.time
    }
}