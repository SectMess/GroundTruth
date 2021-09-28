package com.astute.core.domain

sealed class UIComponent{

    data class Dialog(
        val title: String,
        val description: String
    ): UIComponent()

    //can extend to toast or snackbars

    data class None(
        val message: String
    ): UIComponent()

}