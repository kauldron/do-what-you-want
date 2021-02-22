package com.lockwood.replicant.locale

import android.content.Context
import java.util.Locale

interface LocaleManager {

    val currentLocale: Locale

    fun createLocaleContext(context: Context, locale: Locale): Context
}