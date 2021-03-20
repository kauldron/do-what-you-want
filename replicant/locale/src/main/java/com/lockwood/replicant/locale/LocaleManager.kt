package com.lockwood.replicant.locale

import android.content.Context
import java.util.*

interface LocaleManager {

  val currentLocale: Locale

  fun createLocaleContext(context: Context, locale: Locale): Context
}
