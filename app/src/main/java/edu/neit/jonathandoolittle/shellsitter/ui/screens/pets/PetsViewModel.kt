package edu.neit.jonathandoolittle.shellsitter.ui.screens.pets

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import edu.neit.jonathandoolittle.shellsitter.R
import edu.neit.jonathandoolittle.shellsitter.domain.models.AlarmReceiver
import edu.neit.jonathandoolittle.shellsitter.domain.models.PageModel
import edu.neit.jonathandoolittle.shellsitter.domain.models.PetModel
import edu.neit.jonathandoolittle.shellsitter.domain.models.PetModelRepository
import edu.neit.jonathandoolittle.shellsitter.domain.models.ShellSitterDatabase
import edu.neit.jonathandoolittle.shellsitter.ui.menus.SlidingNavigationItem
import edu.neit.jonathandoolittle.shellsitter.ui.screens.SegmentViewModel
import kotlinx.coroutines.launch
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
 * @version 0.1 - 9/10/2021
 *
 */

class PetsViewModel(
    application: Application
) : SegmentViewModel(
    titleResource = R.string.my_pets,
    application = application
) {

    // ******************************
    // Variables
    // ******************************


    // Database Variables
    private val repository: PetModelRepository
    val allData: LiveData<List<PetModel>>

    // Context Specific (Used only in pet creation / edits!)
    var petName =  mutableStateOf("")
    var gotchaDay = mutableStateOf(LocalDate.MIN)
    var petSpecies = mutableStateOf("")
    var imageUri = mutableStateOf("")
    var feedingTime = mutableStateOf(LocalTime.MIN)
    var feedingNotes = mutableStateOf("")

    init {
        repository = PetModelRepository(ShellSitterDatabase.getInstance(application).petModelDao())
        allData = repository.allData

        allData.observeForever {
            replacePages(it.map { pet ->
                PageModel(
                    tabItem = SlidingNavigationItem(title = pet.name, subtitle = pet.species),
                    shellSitterEntities = listOf(pet)
                )
            })
        }

    }

    // ******************************
    // Public methods
    // ******************************

    fun createPet() {

        val pet = PetModel(
            name = petName.value,
            species = petSpecies.value,
            photoUri = imageUri.value,
            gotchaDay = if(gotchaDay.value == LocalDate.MIN) null else gotchaDay.value,
            feedingTimer = feedingTime.value,
            feedingNotes = feedingNotes.value
        )


        // TODO redo all of this

        val alarmManager = getApplication<Application>().getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val intent = Intent(getApplication(), AlarmReceiver::class.java).let {
                intent ->
            intent.putExtra("name", petName.value)
            PendingIntent.getBroadcast(getApplication(), 0, intent, 0)
        }
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, feedingTime.value.hour)
            set(Calendar.MINUTE, feedingTime.value.minute)
            set(Calendar.SECOND, 0)
        }

        Log.d("TEST", alarmManager.toString())

        alarmManager?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            intent
        )

        viewModelScope.launch {
            repository.addPet(pet)
        }
    }

    fun updatePet(id: Long) {

        val pet = PetModel(
            petId = id,
            name = petName.value,
            species = petSpecies.value,
            photoUri = imageUri.value,
            gotchaDay = if(gotchaDay.value == LocalDate.MIN) null else gotchaDay.value,
            feedingTimer = feedingTime.value,
            feedingNotes = feedingNotes.value
        )

        viewModelScope.launch {
            repository.updatePet(pet)
        }
    }

    fun removePet(id: Long) {
        viewModelScope.launch {
            repository.deletePet(PetModel(id))
        }
    }

    fun loadPet(id: Long): PetModel {
        Log.d("TEST", "TEST")
        val pet  = repository.getPetById(id)
        pet.let {
            petName.value = it.name
            petSpecies.value = it.species
            imageUri.value = it.photoUri
            gotchaDay.value = it.gotchaDay ?: LocalDate.MIN
            feedingTime.value = it.feedingTimer ?: LocalTime.MIN
            feedingNotes.value = it.feedingNotes ?: ""
        }
        return pet

    }

    // ******************************
    // Private methods
    // ******************************

}