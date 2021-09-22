package edu.neit.jonathandoolittle.shellsitter.ui.util

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.neit.jonathandoolittle.shellsitter.R
import edu.neit.jonathandoolittle.shellsitter.ui.menus.WebPageScreen
import edu.neit.jonathandoolittle.shellsitter.ui.theme.BookMarkIcon
import edu.neit.jonathandoolittle.shellsitter.ui.theme.RainyLightGreen
import edu.neit.jonathandoolittle.shellsitter.ui.theme.Typography

const val ICON_SPACER = "    "

enum class BannerAnchor {
    LEFT,
    RIGHT,
    CENTER
}

@Preview(showBackground = true)
@Composable
fun Banner(
    title: String = "Welcome to Shell Sitter!",
    subtitle: String = "Turtle & Tortoise Care Guide",
    imageResource: Int? = null,
    anchor: BannerAnchor = BannerAnchor.CENTER
) {
    Row(modifier = Modifier
        .fillMaxWidth()
    ) {
    var imageBox: @Composable () -> Unit = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = if(anchor == BannerAnchor.LEFT) 0.25f else 1f)
                    .height(60.dp)
            ) {
                if (imageResource != null) {
                    Image(painter = painterResource(id = imageResource), contentDescription = null)
                }
            }
        }

        if(anchor == BannerAnchor.LEFT) {
            imageBox.invoke()
        }

        Column(modifier = Modifier
            .align(Alignment.CenterVertically)
            .fillMaxWidth(fraction = if(anchor == BannerAnchor.RIGHT) 0.75f else 1f),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = title, style = Typography.h2)
            Text(text = subtitle, style = Typography.body2)
            Divider(modifier = Modifier.padding(top = 5.dp))
        }

        if(anchor == BannerAnchor.RIGHT) {
            imageBox.invoke()
        }
    }
}

@Composable
fun ReadingCard(
    title: String,
    contentPreview: String,
    markerColor: Color = Blue,
    expandText: String = "Getting Started",
    expandIcon: ImageVector = Icons.Default.PlayArrow,
    onExpand: () -> Unit = {}
) {

    var expanded by remember {
        mutableStateOf(false)
    }


    val previewLength: Int = 85 //TODO

    Card(elevation = 5.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()) {

        Column(modifier = Modifier.padding(horizontal = 10.dp)) {

            Row() {
                Text(text = title,
                    modifier = Modifier.padding(vertical = 2.dp),
                    style =  TextStyle(
                        fontFamily = FontFamily.Default, //TODO Move text styles
                        fontWeight = FontWeight.W300,
                        fontSize = 27.sp),
                    maxLines = 1
                )
                Box(modifier = Modifier
                    .fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    BookMarkIcon(modifier = Modifier
                        .absoluteOffset(y = (-3).dp)
                        .padding(end = 8.dp),
                    color = markerColor)
                }
            }

            Row(
                Modifier
                    .padding(vertical = 10.dp)
                    .align(Alignment.End)
            ) {
                Box(Modifier.fillMaxWidth(fraction = .70f)) {
                    Text(text = if(expanded) "" else contentPreview.take(previewLength-3)
                            + if(contentPreview.length > previewLength)
                            {"..."} else
                            {""},
                        modifier = Modifier,
                        style =  TextStyle(
                            fontFamily = FontFamily.Default, //TODO Move text styles
                            fontWeight = FontWeight.W300,
                            fontSize = 12.sp),
                        maxLines = 3
                    )
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Top)
                    .padding(top = 10.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    ExpandButton(onClick = {
                        expanded = !it
                    })
                }
            }

            if(expanded) {
                WebPageScreen(urlToRender = "https://jonathandoolittle.com/shell-sitter")
                Row(
                    Modifier
                        .align(Alignment.End)
                        .padding(4.dp)
                        .padding(top = 0.dp)
                ) {
                    CardButton(text= R.string.to_top, icon = Icons.Default.KeyboardArrowUp, onClick = {
                        //TODO Scroll to top
                    })
                }
                onExpand.invoke()
            }

        }
    }
}

/////////////////////////////////
//// BUTTONS
////////////////////////////////

//TODO clean
@Preview(showBackground = true)
@Composable
fun ExpandButton(
    text: String = "expand",
    icon: ImageVector = Icons.Default.Add,
    textExpand: String? = "return",
    iconExpand: ImageVector? = Icons.Default.KeyboardArrowDown,
    onClick: (expanded: Boolean) -> Unit = {},
) {

    var size by remember { mutableStateOf(Size(16f, 16f)) }
    val color by animateColorAsState(targetValue = if(size.width > 16f)
        RainyLightGreen.copy(alpha = 0.25f)
        else LightGray)
    var maxSize by remember { mutableStateOf(16f)}
    val expanded = size.width > 16f


    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.CenterEnd)
            .clickable {
                size = if (expanded) {
                    Size(16f, 16f)
                } else {
                    Size(maxSize * 0.75f, 16f)
                }

                onClick.invoke(expanded)

            }
            .onGloballyPositioned {
                maxSize = it.size.width.toFloat()
            }

    ) {
        Box(
            modifier = Modifier
                .animateContentSize()
                .height(size.height.dp)
                .width(size.width.dp)
                .clip(RoundedCornerShape(percent = 100))
                .background(color)
                .layoutId("bubble")
        ) {}
        Text(text = (if(expanded && textExpand != null) textExpand else text)
            .trim().uppercase() + "   ",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 5.dp, end = 5.dp),
            style =  TextStyle(
                fontFamily = FontFamily.Default, //TODO Move text styles
                fontWeight = FontWeight.W400,
                fontSize = 9.sp,
                color = DarkGray),
            maxLines = 1,
            letterSpacing = 2.sp
        )
        Icon(imageVector = if(expanded && iconExpand != null) iconExpand else icon,
            contentDescription = "test",
            tint = DarkGray,
            modifier = Modifier
                .size(12.dp)
                .align(Alignment.CenterEnd)
                .offset(x = (-3).dp)
        )
    }
}

/**
 * A button to be displayed on a [ReadingCard]
 *
 * @param text The string resource to display
 * @param icon The icon to display
 * @param foregroundColor The color of the icon, and text
 * @param backgroundColor The color of the background shape
 * @param onClick The lambda to be executed when this button is clicked
 */
@Preview(showBackground = true)
@Composable
fun CardButton(
    text: Int = R.string.returning,
    icon: ImageVector = Icons.Default.KeyboardArrowUp,
    foregroundColor: Color = DarkGray,
    backgroundColor: Color = LightGray,
    onClick: () -> Unit = {},
) {
    // TODO Animate when clicked (I am thinking it grows then shrinks)
    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.Center)
            .clickable {
                onClick.invoke()
            }
    ) {
        // Background
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clip(RoundedCornerShape(percent = 100))
                .background(backgroundColor)
        ) {
            // Foreground
            Text(text = stringResource(id = text).trim().uppercase() + ICON_SPACER,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 5.dp, end = 5.dp),
                style =  TextStyle(
                    fontFamily = FontFamily.Default, //TODO Move text styles
                    fontWeight = FontWeight.W400,
                    fontSize = 9.sp,
                    color = foregroundColor),
                maxLines = 1,
                letterSpacing = 2.sp
            )
        }
        Icon(imageVector = icon,
            contentDescription = "",
            tint = foregroundColor,
            modifier = Modifier
                .size(12.dp)
                .align(Alignment.CenterEnd)
                .offset(x = (-3).dp)
        )

    }
}

/**
 * Allows for an image to be selected from local storage
 * and rendered as a circle with a thin border. Displays
 * with a size of 150dp.
 *
 * TODO Move set values as parameters with default values
 *
 * @param onImageSelected Callback for when an image is selected. This function takes care of displaying the image.
 */
@Composable
fun CircleImagePicker(
    onImageSelected: (Uri?) -> Unit = {}
) {

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap =  remember { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
        onImageSelected.invoke(imageUri)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        val modifier = Modifier
            .size(150.dp)
            .align(Alignment.CenterVertically)
            .border(1.dp, Color.LightGray.copy(alpha = 0.25f), CircleShape)
            .clip(CircleShape)
            .clickable {
                launcher.launch("image/*")
            }

        if(imageUri == null) {
            PainterOrBitmapImage(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                modifier = modifier
            )
        } else {
            imageUri?.let {
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap.value = MediaStore.Images
                        .Media.getBitmap(context.contentResolver,it)
                } else {
                    val source = ImageDecoder
                        .createSource(context.contentResolver,it)
                    bitmap.value = ImageDecoder
                        .decodeBitmap(source)
                }
                bitmap.value?.let {  btm ->
                    PainterOrBitmapImage(
                        bitmap = btm.asImageBitmap(),
                        modifier = modifier
                    )
                }
            }
        }
    }
}

/**
 * Renders the given painter or bitmap as an image with
 * the given modifier.
 *
 * @param painter The painter to use, if any
 * @param bitmap The bitmap to use, if any
 * @param contentDescription The content description of this image. Defaults to null
 * @param modifier
 */
@Composable
fun PainterOrBitmapImage(
    painter: Painter? = null,
    bitmap: ImageBitmap? = null,
    contentDescription: String? = null,
    modifier: Modifier = Modifier
) {
    if(painter != null) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = modifier,
        )
    } else if(bitmap != null) {
        Image(
            bitmap = bitmap,
            contentDescription = contentDescription,
            modifier = modifier,
        )
    }
}

