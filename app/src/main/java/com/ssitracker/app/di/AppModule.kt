package com.ssitracker.app.di

import androidx.datastore.preferences.core.Preferences
import androidx.room.Room.databaseBuilder
import com.google.ai.client.generativeai.GenerativeModel
import com.ssitracker.app.BuildConfig
import com.ssitracker.app.data.ia.AiRepositoryImpl
import com.ssitracker.app.data.local.datastore.PreferenceRepositoryImpl
import com.ssitracker.app.data.local.datastore.ThemeManager
import com.ssitracker.app.data.local.datastore.dataStore
import com.ssitracker.app.data.local.db.AppDatabase
import com.ssitracker.app.data.local.repository.SSIRepositoryImpl
import com.ssitracker.app.domain.repository.AiRepository
import com.ssitracker.app.domain.repository.PreferenceRepository
import com.ssitracker.app.domain.repository.SSIRepository
import com.ssitracker.app.domain.usecase.GetAllSSIUseCase
import com.ssitracker.app.domain.usecase.GetDailyTipUseCase
import com.ssitracker.app.domain.usecase.GetSSIByIdUseCase
import com.ssitracker.app.domain.usecase.InsertSSIUseCase
import com.ssitracker.app.ui.presentation.addentry.AddEntryViewModel
import com.ssitracker.app.ui.presentation.history.HistoryViewModel
import com.ssitracker.app.ui.presentation.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    // Room Database
    single {
        databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "field_collect.db"
        ).build()
    }
    single { get<AppDatabase>().SSIDao() }

    // DataStore
    single { ThemeManager(androidContext()) }
    single<androidx.datastore.core.DataStore<Preferences>> { androidContext().dataStore }

    // Repository
    single<SSIRepository> { SSIRepositoryImpl(get()) }
    single<PreferenceRepository> { PreferenceRepositoryImpl(get()) }
    single<AiRepository> { AiRepositoryImpl(get()) }

    // Generative AI
    single {
        GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = BuildConfig.GEMINI_API_KEY
        )
    }

    // UseCase
    factory { InsertSSIUseCase(get()) }
    factory { GetAllSSIUseCase(get()) }
    factory { GetSSIByIdUseCase(get()) }
    factory { GetDailyTipUseCase(get(), get()) }

    // ViewModel
    viewModelOf(::HomeViewModel)
    viewModelOf(::AddEntryViewModel)
    viewModelOf(::HistoryViewModel)
}