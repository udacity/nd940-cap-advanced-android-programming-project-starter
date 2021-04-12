package com.example.android.politicalpreparedness

import android.app.Application
import com.example.android.politicalpreparedness.data.database.ElectionDatabase
import com.example.android.politicalpreparedness.data.repository.*
import com.example.android.politicalpreparedness.election.ElectionsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class CapApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val module = module {
            viewModel {
                ElectionsViewModel(
                        get() as ElectionRepository,
                        get()
                )
            }

            single {
                DefaultElectionRepository(
                        get() as ElectionDataSource,
                        get() as ElectionDataSource
                )
            }
            single {
                ElectionRemoteDataSource()
            }
            single {
                ElectionLocalDataSource(get())
            }
            single {
                ElectionDatabase.getInstance(this@CapApplication).electionDao
            }
        }
//
//        startKoin {
//            androidContext(this@CapApplication)
//            modules(listOf(module))
//        }
    }
}