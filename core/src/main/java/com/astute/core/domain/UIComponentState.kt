package com.astute.core.domain

sealed class UIComponentState {
    object Show: UIComponentState()

    object Hide: UIComponentState()

}
