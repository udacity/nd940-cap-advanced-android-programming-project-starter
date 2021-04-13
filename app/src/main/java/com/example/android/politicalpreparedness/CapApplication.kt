package com.example.android.politicalpreparedness

import android.app.Application
import com.example.android.politicalpreparedness.data.database.ElectionDao
import com.example.android.politicalpreparedness.data.database.ElectionDatabase
import com.example.android.politicalpreparedness.data.repository.*
import com.example.android.politicalpreparedness.election.ElectionsViewModel
import com.example.android.politicalpreparedness.election.VoterInfoViewModel
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
                )
            }
            viewModel {
                VoterInfoViewModel(
                    get() as ElectionDao
                )
            }
            single {
                DefaultElectionRepository(
                        ElectionRemoteDataSource(),
                        ElectionLocalDataSource(get())
                ) as ElectionRepository
            }
            single {
                ElectionDatabase.getInstance(this@CapApplication).electionDao as ElectionDao
            }
        }

        startKoin {
            modules(listOf(module))
        }
    }
}