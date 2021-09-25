package com.astute.core

import com.astute.core.domain.ProgressBarState
import com.astute.core.domain.UIComponent

sealed class DataState<T>{

    data class Response<T>(val uiComponent: UIComponent): DataState<T>()

    data class Data<T>(val data: T? = null): DataState<T>()

    data class Loading<T>(val progressBarState: ProgressBarState = ProgressBarState.Idle): DataState<T>()
}