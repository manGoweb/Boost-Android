package cz.mangoweb.appstore.di

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Vojtech Hrdina on 26/02/2018.
 */
@Module
class UtilModule {

    @Provides
    @Singleton
    fun providesSharedPreferences(application: Application):
            SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }
}