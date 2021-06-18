package com.lockwood.replicant.locale

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import com.lockwood.automata.android.ApplicationContext
import java.util.*

class AndroidLocaleManager(
    private val context: ApplicationContext,
) : LocaleManager {

    private val configuration: Configuration
        get() = context.application.resources.configuration

    override var locale: Locale
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.locales.get(0)
        } else {
            configuration.locale
        }
        set(value) {
            Locale.setDefault(value)
            configuration.setLocale(value)
        }

    override fun createLocaleContext(context: Context, locale: Locale): Context {
        Locale.setDefault(locale)
        return createConfigurationContext(context, locale)
    }

    private fun createConfigurationContext(context: Context, locale: Locale): Context {
        val localeConfiguration = configuration.apply { setLocale(locale) }
        return context.createConfigurationContext(localeConfiguration)
    }

}
