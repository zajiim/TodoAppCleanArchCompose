package com.example.notescleancompose.di

import android.content.Context
import androidx.room.Room
import com.example.notescleancompose.data.local.NotesDao
import com.example.notescleancompose.data.local.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesNoteDao(database: NotesDatabase): NotesDao {
        return database.notesDao
    }

    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext context: Context,
    ): NotesDatabase = Room.databaseBuilder(
        context,
        NotesDatabase::class.java,
        "notes"
    ).build()
}