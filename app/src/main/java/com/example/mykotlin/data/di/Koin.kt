package com.example.mykotlin.data.di

import com.example.mykotlin.data.NoteRepo
import com.example.mykotlin.data.provider.FirestoreDataProvider
import com.example.mykotlin.data.provider.RemoteDataProvider
import com.example.mykotlin.ui.login.LoginViewModel
import com.example.mykotlin.ui.main.MainViewModel
import com.example.mykotlin.ui.second.SecondViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirestoreDataProvider(get(), get()) } bind RemoteDataProvider::class
    single { NoteRepo(get()) }
}

val secondModule = module {
    viewModel { SecondViewModel(get()) }
}

val mainModule = module {
    viewModel { MainViewModel(get()) }
}

val loginModule = module {
    viewModel { LoginViewModel(get()) }
}