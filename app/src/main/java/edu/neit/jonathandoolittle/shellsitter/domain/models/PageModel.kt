package edu.neit.jonathandoolittle.shellsitter.domain.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import edu.neit.jonathandoolittle.shellsitter.ui.menus.SlidingNavigationItem

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
 * @version 0.1 - 9/19/2021
 *
 */

class PageModel(
    val tabItem: SlidingNavigationItem,
    shellSitterEntities: List<Any> = listOf()
) {

    private val entityValues = mutableListOf<Any>()
    var entities by mutableStateOf(listOf<Any>())

    init {
        shellSitterEntities.forEach(::addEntity)
    }

    // ******************************
    // Public methods
    // ******************************

    fun addEntity(shellSitterEntity: Any) {
        entityValues.add(shellSitterEntity)
        entities = entityValues.toMutableList()
    }

}