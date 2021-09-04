package com.alexandre.android.koombea.di

import com.alexandre.android.koombea.data.bl.IPostsRetrieve
import com.alexandre.android.koombea.data.repository.KoombeaRepository
import com.alexandre.android.koombea.viewModels.KoombeaViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repoModule = module {
    single<IPostsRetrieve> { KoombeaRepository() }
}

val viewModelModule = module {
    viewModel { KoombeaViewModel(get()) }
}