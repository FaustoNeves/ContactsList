package br.com.fausto.mypeople.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ContactsListApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}