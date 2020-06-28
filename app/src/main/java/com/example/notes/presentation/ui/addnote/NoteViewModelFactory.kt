package com.example.notes.presentation.ui.addnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.data.repository.NoteRepository

class NoteViewModelFactory(private var repo:NoteRepository, noteId:String) : ViewModelProvider.NewInstanceFactory(){
    private var mNoteId:String = noteId

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddNewNoteViewModel(repo,mNoteId) as T
    }
}