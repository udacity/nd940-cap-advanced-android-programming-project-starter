package com.example.android.politicalpreparedness.data.network.models

data class State (
    val name: String,
    val electionAdministrationBody: AdministrationBody
)

fun State.getCorrespondenceAddress(): String? {
    return electionAdministrationBody.correspondenceAddress?.toFormattedString()
}

fun State.getVotingLocationFinderUrl(): String? {
    return electionAdministrationBody.votingLocationFinderUrl
}

fun State.getBallotInfoUrl(): String? {
    return electionAdministrationBody.ballotInfoUrl
}