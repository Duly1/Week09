package edu.farmingdale.datastoredemo

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import edu.farmingdale.datastoredemo.data.local.UserPreferencesRepository


// Define a constant for the DataStore name
private const val LAYOUT_PREFERENCE_NAME = "layout_preferences"

// Create a DataStore delegate for Preferences
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = LAYOUT_PREFERENCE_NAME
)

// Application class for initializing DataStore
class EmojiReleaseApplication : Application() {
    // Declare the repository for user preferences
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        // Initialize the repository with the DataStore instance
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }
}
