package com.example.notes

import android.app.Application
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class NoteApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this);
        if(BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}