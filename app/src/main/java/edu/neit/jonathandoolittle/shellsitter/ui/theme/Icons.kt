package edu.neit.jonathandoolittle.shellsitter.ui.theme

import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import edu.neit.jonathandoolittle.shellsitter.R



@Composable
fun BookMarkIcon(
    modifier: Modifier = Modifier,
    color: Color = Black
) {
    Icon(
        painter = painterResource(R.drawable.outline_bookmark_24),
        contentDescription = "bookmark",
        modifier = modifier,
        tint = color
    )
}


@Composable
fun GetIcon(
    resId: Int? = null,
    size: Dp = 30.dp
) {
    if(resId != null) {
        Icon(painter = painterResource(id = resId), null, modifier = Modifier.size(size))
    }
}


