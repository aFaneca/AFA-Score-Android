package com.afaneca.afascore.data.repository

import com.afaneca.afascore.common.Resource
import com.afaneca.afascore.data.remote.AfaScoreAPI
import com.afaneca.afascore.data.remote.dto.mapToDomain
import com.afaneca.afascore.domain.model.Match
import com.afaneca.afascore.domain.repository.MatchesRepository
import javax.inject.Inject

/**
 * Created by António Faneca on 2/13/2023.
 */
class LiveMatchesRepository @Inject constructor(
    private val api: AfaScoreAPI
) : MatchesRepository {
    override suspend fun getMatches(): Resource<List<Match>> {
        return try {
            Resource.Success(api.getResults().map { result -> result.mapToDomain() })
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "")
        }
    }
}