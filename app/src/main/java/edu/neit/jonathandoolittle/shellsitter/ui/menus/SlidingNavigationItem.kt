package edu.neit.jonathandoolittle.shellsitter.ui.menus

/**
 *
 * An item to be rendered as a tab in a [SlidingNavigationBar]
 *
 * Each tab contains a title, and optionally a subtitle.
 *
 * @author Jonathan Doolittle
 * @version 0.1 - 9/10/2021
 *
 * @property title A string value to be displayed for a title. For non-dynamic tabs, use the titleResource instead. Each item requires a title or titleResource, but not both.
 * @property titleResource The ID of a string resource to be used a title.
 * @property subtitle A optional string value to be displayed as the subtitle. For non-dynamic tabs, use the subtitleResource instead.
 * @property subtitleResource An option resource ID to be displayed as the subtitle.
 */

data class SlidingNavigationItem(
    val title: String? = null,
    val titleResource: Int? = null,
    val subtitle: String? = null,
    val subtitleResource: Int? = null
)