package edu.neit.jonathandoolittle.shellsitter.ui.screens

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
 * @version 0.1 - 9/30/2021
 *
 */

sealed class UiState {
    object ContentLoading : UiState()
    object NoContent : UiState()
    object Error : UiState()
    object HasContent : UiState()
}