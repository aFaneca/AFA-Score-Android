package com.afaneca.afascore.domain.useCase

import com.afaneca.afascore.common.Constants
import com.afaneca.afascore.common.Resource
import com.afaneca.afascore.domain.model.FilterData
import com.afaneca.afascore.domain.model.FilterableCompetition
import com.afaneca.afascore.domain.model.FilterableStatus
import com.afaneca.afascore.domain.model.FilterableTeam
import com.afaneca.afascore.domain.model.Match
import com.afaneca.afascore.domain.model.MatchListWrapper
import com.afaneca.afascore.domain.model.Scoreboard
import com.afaneca.afascore.domain.model.Team
import com.afaneca.afascore.domain.repository.FilterRepository
import com.afaneca.afascore.domain.repository.MatchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.util.HashSet

import javax.inject.Inject

/**
 * Created by António Faneca on 2/13/2023.
 */
class GetMatchesUseCase @Inject constructor(
    private val matchesRepository: MatchesRepository,
    private val filtersRepository: FilterRepository,
    private val getReconciledFiltersUseCase: GetReconciledFiltersUseCase
) {
    suspend operator fun invoke(): Flow<Resource<MatchListWrapper>> = flow {
        // TODO fetch remote data
        /*emit(Resource.Loading())
        *//*emit(repository.getMatches())*//*
        delay(1000)*/
        val list = mutableListOf(
            Match(
                "2",
                Team(
                    "AD Nogueira Regedoura",
                    "ADNR",
                    "https://www.afatv.pt/img/equipas/nogueira_da_regedoura.png"
                ),
                Team("SC Esmoriz", "SCE", "https://www.afatv.pt/img/equipas/esmoriz.png"),
                Constants.GameStatus.NotStarted,
                Scoreboard(1, 3),
                "18 de Fevereiro de 2023",
                "15:00",
                "1ª Divisão Distrital"
            ),
            Match(
                "1",
                Team("AR Aguinense", "ARA", "https://www.afatv.pt/img/equipas/aguinense.png"),
                Team("GD Mealhada", "GDM", "https://www.afatv.pt/img/equipas/gdmealhada.png"),
                Constants.GameStatus.Ongoing,
                Scoreboard(1, 1),
                "18 de Fevereiro de 2023",
                "15:00",
                "1ª Divisão Distrital"
            ),
            Match(
                "2",
                Team("SC Bustelo", "SCB", "https://www.afatv.pt/img/equipas/bustelo.png"),
                Team("CCR Válega", "CCRV", "https://www.afatv.pt/img/equipas/valega.png"),
                Constants.GameStatus.Finished,
                Scoreboard(1, 3),
                "18 de Fevereiro de 2023",
                "15:00",
                "1ª Divisão Distrital"
            )
        )
        val filterData = getReconciledFiltersUseCase(list)
        emit(Resource.Success(MatchListWrapper(list, filterData)))
    }
}