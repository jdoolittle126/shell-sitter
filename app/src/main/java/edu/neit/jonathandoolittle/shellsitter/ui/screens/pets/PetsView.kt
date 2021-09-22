package edu.neit.jonathandoolittle.shellsitter.ui.screens.pets

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import edu.neit.jonathandoolittle.shellsitter.R
import edu.neit.jonathandoolittle.shellsitter.models.PetModel
import edu.neit.jonathandoolittle.shellsitter.ui.menus.AnimatedNavigationOptions
import edu.neit.jonathandoolittle.shellsitter.ui.theme.RainyDarkGreen
import edu.neit.jonathandoolittle.shellsitter.ui.theme.Typography
import edu.neit.jonathandoolittle.shellsitter.ui.util.CircleImagePicker
import edu.neit.jonathandoolittle.shellsitter.ui.util.PageView
import edu.neit.jonathandoolittle.shellsitter.ui.util.PainterOrBitmapImage
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import android.graphics.BitmapFactory
import android.os.Environment
import java.io.File
import java.lang.Exception
import java.time.temporal.ChronoUnit


/*
 TODO Fix this entire file. There is so much going on in this file that is all wrong.
 I really just forced things to work for the sake of getting this app done on time.
 */

@SuppressLint("RestrictedApi")
@ExperimentalPagerApi
@Composable
fun PetsView(
    navigationController: NavHostController,
    arguments: Bundle? = null
) {

    val viewModel = viewModel(modelClass = PetsViewModel::class.java)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // Create new pet
                navigationController.navigate(AnimatedNavigationOptions.PETS.route + "?edit=0")
            },
            backgroundColor = RainyDarkGreen,
            contentColor = Color.White) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        val arg = arguments?.getString("edit")
        if(arg == null) {
            PageView(viewModel = viewModel(modelClass = PetsViewModel::class.java), navigationController = navigationController) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.fillMaxHeight(fraction = 0.25f))
                    Text(text = stringResource(id = R.string.no_saved_pets), style = Typography.h2)
                    Spacer(modifier = Modifier.fillMaxHeight(fraction = 0.25f))
                    Image(painter = painterResource(id = R.drawable.ic_turtle_point), contentDescription = null)
                }
            }
        } else {
            // TODO Add update
                if(arg == "0") {
                    AddPet(navigationController, viewModel)
                } else {
                    arg.toLongOrNull()?.let {
                        val pet = viewModel.loadPet(id = it )
                        AddPet(navigationController, viewModel, pet)
                    }
                }
        }
    }
}

@Composable
fun AddPet(
    navigationController: NavHostController,
    viewModel: PetsViewModel,
    petModel: PetModel? = null
) {

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()


    Column {
        Row(modifier = Modifier.padding(5.dp), horizontalArrangement = Arrangement.Start) {
            Text(
                text = stringResource(id = R.string.my_pets),
                style = Typography.h1,
                maxLines = 1
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp)
        ) {

            CircleImagePicker(onImageSelected = {
                it?.let {
                    viewModel.imageUri.value = it.toString()
                }
            })

            // TODO Move to Strings
            // TODO Validation

            Text(text = "Click to add your own picture!")
            FormEntryTextField(label = "Pet Name", placeholder = "Scuba", onValueChanged = {viewModel.petName.value = it}, startValue = petModel?.let { it.name })
            FormEntryTextField(label = "Species", placeholder = "Box Turtle", onValueChanged = {viewModel.petSpecies.value = it}, startValue = petModel?.let { it.species })
            FormEntryDateField(label = "Gotcha Day", placeholder = "1/1/2021", dialogState = dateDialogState, onValueChanged = {viewModel.gotchaDay.value = it}, startValue = petModel?.let { it.gotchaDay })
            FormEntryTimeField(label = "Feeding Time", placeholder = "1:00pm", dialogState = timeDialogState, onValueChanged = {viewModel.feedingTime.value = it}, startValue = petModel?.let { it.feedingTimer })
            FormEntryTextField(label = "Feeding Notes", placeholder = "2 scoops of turtle flakes!", isSingleLine = false, onValueChanged = {viewModel.feedingNotes.value = it}, startValue = petModel?.let { it.feedingNotes })

            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)) {

                Box {
                    Button(onClick = {
                        navigationController.navigate(AnimatedNavigationOptions.PETS.parameterizedRoute)
                    }) {
                        Text(text = "Back")
                    }
                }

                Box {
                    Button(onClick = {
                        if(petModel != null) {
                            viewModel.updatePet(petModel.petId)
                        } else {
                            viewModel.createPet()
                        }

                        navigationController.navigate(AnimatedNavigationOptions.PETS.parameterizedRoute)
                    }) {
                        Text(text = "Submit")
                    }
                }

            }


        }
    }
}

@Composable
fun FormEntryTextField(
    label: String? = null,
    placeholder: String? = null,
    isSingleLine: Boolean = true,
    startValue: String? = null,
    onValueChanged: (String) -> Unit = {}
) {

    var text = remember { mutableStateOf(startValue ?: "")}

    // TODO Colors
    Column(Modifier.padding(horizontal = 55.dp)) {
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = text.value,
            onValueChange = {
                text.value = it
                onValueChanged.invoke(it)
            },
            label = { label?.let { Text(it) } },
            placeholder = {placeholder?.let { Text(it) }},
            singleLine = isSingleLine
        )
    }
}

@Composable
fun FormEntryDateField(
    label: String? = null,
    placeholder: String? = null,
    dialogState: MaterialDialogState,
    onClick: () -> Unit = {},
    startValue: LocalDate? = null,
    onValueChanged: (LocalDate) -> Unit = {}
) {

    var date = remember { mutableStateOf(startValue ?: LocalDate.MIN)}

    Column {
        MaterialDialog(
            dialogState = dialogState,
            buttons = {
                positiveButton("Ok")
                negativeButton("Cancel")
            }
        ) {
            datepicker { newDate ->
                date.value = newDate
                onValueChanged.invoke(newDate)
            }
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        OutlinedTextField(
            value = date.value.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)),
            label = { label?.let { Text(it) } },
            readOnly = true,
            placeholder = {placeholder?.let { Text(it) }},
            trailingIcon = { Icon(imageVector = Icons.Default.DateRange, contentDescription = null, modifier = Modifier.clickable {
                onClick.invoke()
                dialogState.show()
            })},
            onValueChange = {})
    }

}

@Composable
fun FormEntryTimeField(
    label: String? = null,
    placeholder: String? = null,
    dialogState: MaterialDialogState,
    onClick: () -> Unit = {},
    startValue: LocalTime? = null,
    onValueChanged: (LocalTime) -> Unit = {}
) {

    var time = remember { mutableStateOf(startValue ?: LocalTime.MIN)}

    Column {
        MaterialDialog(
            dialogState = dialogState,
            buttons = {
                positiveButton("Ok")
                negativeButton("Cancel")
            }
        ) {
            timepicker { newTime ->
                time.value = newTime
                onValueChanged.invoke(newTime)
            }

        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        OutlinedTextField(
            value = time.value.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)),
            label = { label?.let { Text(it) } },
            readOnly = true,
            placeholder = {placeholder?.let { Text(it) }},
            trailingIcon = { Icon(imageVector = Icons.Default.DateRange, contentDescription = null, modifier = Modifier.clickable {
                onClick.invoke()
                dialogState.show()
            })},
            onValueChange = {})
    }

}

// This will need to be redone
@Composable
fun DisplayPet(
    model: PetModel,
    viewModel: PetsViewModel,
    navigationController: NavHostController
) {

    val image: Bitmap? = try {
        MediaStore.Images.Media.getBitmap(viewModel.getApplication<Application>().contentResolver, Uri.parse(model.photoUri))
    } catch (e: Exception) {
        null
    }

    Column {
        Row(
            Modifier
                .padding(10.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.weight(1f)) {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    PainterOrBitmapImage(modifier = Modifier
                        .size(175.dp)
                        .border(1.dp, Color.LightGray.copy(alpha = 0.25f), CircleShape)
                        .clip(CircleShape),
                        bitmap = image?.asImageBitmap(),
                        painter = if(image == null) painterResource(id = R.drawable.ic_launcher_foreground) else null
                    )
                }
            }

            Box(Modifier.weight(1f)) {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = model.name, style = Typography.h2 )
                    Text(text = model.species, style = Typography.body2)
                }
            }
        }

        Text(text = "Days until Gotcha Day: ${
            if(model.gotchaDay != null) {
                
                val now = LocalDate.now()
                var nextDay = model.gotchaDay.withYear(now.year)
                if(nextDay.isBefore(now) || nextDay.isEqual(now)) {
                    nextDay = nextDay.plusYears(1)
                }
                
                ChronoUnit.DAYS.between(now, nextDay).toString() + " (" + model.gotchaDay.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)) + ")"
            } else {
                "N/A (No date specified!)"
            }
            
            
        }", style = Typography.subtitle1 )

        Text(text = "Next Meal: ${
            if(model.feedingTimer != null) { 
                 model.feedingTimer.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
            } else {
                "N/A (No time specified!)"
            }


        }", style = Typography.subtitle1 )

        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth().padding(top = 15.dp)) {
            Button(onClick = { viewModel.removePet(model.petId)}) {
                Text(text = "Delete")
            }

            Button(onClick = {
                navigationController.navigate(AnimatedNavigationOptions.PETS.route + "?edit=" + model.petId)
            }) {
                Text(text = "Edit")
            }
        }




    }





}