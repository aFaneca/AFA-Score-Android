package com.afaneca.afascore.domain.useCase

import com.afaneca.afascore.common.Resource
import com.afaneca.afascore.domain.model.Match
import com.afaneca.afascore.domain.model.Scoreboard
import com.afaneca.afascore.domain.model.Team
import com.afaneca.afascore.domain.repository.MatchesRepository
import com.afaneca.afascore.ui.model.MatchUiModel
import com.afaneca.afascore.ui.model.ScoreboardUiModel
import com.afaneca.afascore.ui.model.TeamUiModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

/**
 * Created by António Faneca on 2/13/2023.
 */
class GetMatchesUseCase @Inject constructor(
    private val repository: MatchesRepository
) {
    operator fun invoke(): Flow<Resource<List<Match>>> = flow {
        /*emit(Resource.Loading())
        *//*emit(repository.getMatches())*//*
        delay(1000)*/
        emit(
            Resource.Success<List<Match>>(
                listOf(
                    Match(
                        "1", Team("FC Pampilhosa", "FCP", null),
                        Team("GD Calvão", "GDC", null), "ONGOING", Scoreboard(1, 1),
                        "18 de Fevereiro de 2023", "15:00", "1ª Divisão Distrital"
                    ),
                    Match(
                        "2", Team("Juve Force Pte de Vagos", "JVF", null),
                        Team("GD Mealhada", "GDM", null), "FINISHED", Scoreboard(1, 3),
                        "18 de Fevereiro de 2023", "15:00", "1ª Divisão Distrital"
                    )
                )
            )
        )
    }
}