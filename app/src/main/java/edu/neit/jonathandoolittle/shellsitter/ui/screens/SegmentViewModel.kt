package edu.neit.jonathandoolittle.shellsitter.ui.screens

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import edu.neit.jonathandoolittle.shellsitter.domain.models.PageModel
import edu.neit.jonathandoolittle.shellsitter.ui.menus.SlidingNavigationItem

/**
 *
 * A view model for a segment within the application. In this case,
 * each bottom navigation item represents a segment, so there are four
 * view models derived from this class. Each segment contains a title
 * resource to be displayed in the top corner, and a list of pages.
 * Each page generates it's own tab, and it's models are rendered
 * according to the [ShellSitterModelRender].
 *
 * @author Jonathan Doolittle
 * @version 0.1 - 9/19/2021
 *
 * @property titleResource The resource ID for the title of this segment
 */
open abstract class SegmentViewModel(
    application: Application,
    var titleResource: Int
) : AndroidViewModel(application) {

    // With composable functions, I find that mutable lists don't
    // play nice. As a work around, I found using a private backing mutable
    // list with a forward-facing non-mutable list that is reassigned seems to work.
    private val pageValues = mutableListOf<PageModel>()

    var pages by mutableStateOf(listOf<PageModel>())

    // ******************************
    // Public methods
    // ******************************

    /**
     * Maps the pages to their tab counter-parts
     *
     * @return The tabs for each page
     */
    fun getTabs(): List<SlidingNavigationItem> {
        return pages.map {
            it.tabItem
        }
    }

    /**
     * Adds a page to this segment, which will force a
     * recomposition and create a new tab.
     *
     * @param page The page to add
     */
    fun addPage(page: PageModel) {
        pageValues.add(page)
        pages = pageValues.toMutableList()
    }


    fun replacePages(newPages: List<PageModel>) {
        pageValues.clear()
        pageValues.addAll(newPages)
        pages = pageValues.toMutableList()
    }

    /**
     * Accesses the model for a page at a given index
     *
     * @param index The index to access
     * @return The page model
     */
    fun getPage(index: Int): PageModel {
        return pages[index]
    }

}