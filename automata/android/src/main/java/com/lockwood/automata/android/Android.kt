package com.lockwood.automata.android

import android.content.Context

val Context.isTablet: Boolean
    get() = resources.getBoolean(R.bool.is_tablet)

val Context.isRightToLeft: Boolean
    get() = resources.getBoolean(R.bool.is_right_to_left)