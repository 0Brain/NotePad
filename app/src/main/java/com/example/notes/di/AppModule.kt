package com.example.notes.di

import android.content.Context
import androidx.room.Room
import com.example.notes.persistence.DefaultNoteRepository
import com.example.notes.persistence.local.NoteDatabase
import com.example.notes.persistence.local.NotesLocalDataSource
import com.example.notes.persistence.source.NotesDataSource
import com.example.notes.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton


/**
 * Module to tell Hilt how to provide instances of types that cannot be constructor-injected.
 *
 * As these types are scoped to the application lifecycle using @Singleton, they're installed
 * in Hilt's ApplicationComponent.
 */
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalNotesDataSource

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            "note_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @LocalNotesDataSource
    @Provides
    fun provideNotesLocalDataSource(database:NoteDatabase,ioDispatcher:CoroutineDispatcher): NotesDataSource {
        return NotesLocalDataSource(database.noteDao(),ioDispatcher)
    }

    @Singleton
    @Provides
    fun provideIoDispatcher():CoroutineDispatcher = Dispatchers.IO

}


@Module
@InstallIn(ApplicationComponent::class)
object NoteRepositoryModule{
    @Singleton
    @Provides
    fun provideNoteRepository(@AppModule.LocalNotesDataSource localNotesDataSource:NotesDataSource,ioDispatcher: CoroutineDispatcher):NoteRepository{
        return DefaultNoteRepository(localNotesDataSource,ioDispatcher)
    }
}
