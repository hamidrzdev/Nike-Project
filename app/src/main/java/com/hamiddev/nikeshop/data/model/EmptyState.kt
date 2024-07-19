package com.hamiddev.nikeshop.data.model

import androidx.annotation.StringRes

data class EmptyState(
    var mustShow: Boolean,
    @StringRes var messageResId: Int=0,
    var mustShowCallToActionButton:Boolean=false
    )