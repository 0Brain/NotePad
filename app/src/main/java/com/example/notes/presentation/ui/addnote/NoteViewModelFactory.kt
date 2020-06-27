package com.example.notes.presentation.ui.addnote

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NoteViewModelFactory(private var application: Application, noteId:String) : ViewModelProvider.NewInstanceFactory(){
    private var mNoteId:String = noteId

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddNewNoteViewModel(application,mNoteId) as T
    }
}