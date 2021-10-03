package edu.neit.jonathandoolittle.shellsitter.domain.models

import androidx.compose.ui.graphics.Color

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
 * @version 0.1 - 9/18/2021
 *
 */

//TODO Button & Icon
data class ArticleModel(
    val title: String,
    val preview: String,
    val markerColor: Color,
    val uri: String)