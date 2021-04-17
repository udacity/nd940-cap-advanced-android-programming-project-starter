package com.example.android.politicalpreparedness

import android.app.Application
import com.example.android.politicalpreparedness.data.database.ElectionDao
import com.example.android.politicalpreparedness.data.database.ElectionDatabase
import com.example.android.politicalpreparedness.data.repository.*
import com.example.android.politicalpreparedness.data.repository.election.DefaultElectionRepository
import com.example.android.politicalpreparedness.data.repository.election.ElectionLocalDataSource
import com.example.android.politicalpreparedness.data.repository.election.ElectionRemoteDataSource
import com.example.android.politicalpreparedness.data.repository.election.ElectionRepository
import com.example.android.politicalpreparedness.data.repository.representative.DefaultRepresentativeRepository
import com.example.android.politicalpreparedness.data.repository.representative.RepresentativeRemoteDataSource
import com.example.android.politicalpreparedness.data.repository.representative.RepresentativeRepository
import com.example.android.politicalpreparedness.data.repository.voterInfo.DefaultVoterInfoRepository
import com.example.android.politicalpreparedness.data.repository.voterInfo.VoterInfoRemoteDataSource
import com.example.android.politicalpreparedness.data.repository.voterInfo.VoterInfoRepository
import com.example.android.politicalpreparedness.election.ElectionsViewModel
import com.example.android.politicalpreparedness.election.VoterInfoViewModel
import com.example.android.politicalpreparedness.representative.RepresentativeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class CapApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val viewModels = module {
            viewModel {
                ElectionsViewModel(
                        get() as ElectionRepository,
                )
            }
            viewModel {
                VoterInfoViewModel(
                        get(),
                        get() as VoterInfoRepository,
                        get() as ElectionRepository,
                )
            }
            viewModel {
                RepresentativeViewModel(
                        get()
                )
            }
        }

        val voterInfoRepository = module {
            single {
                DefaultVoterInfoRepository(
                        VoterInfoRemoteDataSource()
                ) as VoterInfoRepository
            }
        }

        val electionRepository = module {
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

        val representativeRepository = module {
            single {
                DefaultRepresentativeRepository(
                        RepresentativeRemoteDataSource()
                ) as RepresentativeRepository
            }
        }

        startKoin {
            modules(listOf(
                    viewModels,
                    voterInfoRepository,
                    electionRepository,
                    representativeRepository
            ))
            androidContext(this@CapApplication)
        }
    }
}