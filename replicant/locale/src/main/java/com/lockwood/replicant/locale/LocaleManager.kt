package com.lockwood.replicant.locale

import android.content.Context
import java.util.*

interface LocaleManager {

    var locale: Locale

    fun createLocaleContext(context: Context, locale: Locale): Context
}
