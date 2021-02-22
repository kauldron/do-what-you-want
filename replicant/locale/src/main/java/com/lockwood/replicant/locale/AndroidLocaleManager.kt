package com.lockwood.replicant.locale

import android.content.Context
import android.content.res.Configuration
import androidx.core.os.ConfigurationCompat
import java.util.Locale

class AndroidLocaleManager(
    private val context: Context,
) : LocaleManager {

    private val configuration: Configuration
        get() = context.applicationContext.resources.configuration

    override val currentLocale: Locale
        get() = ConfigurationCompat.getLocales(configuration).get(0)

    override fun createLocaleContext(context: Context, locale: Locale): Context {
        Locale.setDefault(locale)
        return createConfigurationContext(context, locale);
    }

    private fun createConfigurationContext(context: Context, locale: Locale): Context {
        val localeConfiguration = configuration.apply { setLocale(locale) }
        return context.createConfigurationContext(localeConfiguration)
    }

}