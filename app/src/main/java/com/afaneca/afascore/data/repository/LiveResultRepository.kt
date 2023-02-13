package com.afaneca.afascore.data.repository

import com.afaneca.afascore.common.Resource
import com.afaneca.afascore.data.remote.AFAScoreAPI
import com.afaneca.afascore.data.remote.dto.mapToDomain
import com.afaneca.afascore.domain.model.Match
import com.afaneca.afascore.domain.repository.ResultRepository
import javax.inject.Inject

/**
 * Created by António Faneca on 2/13/2023.
 */
class LiveResultRepository @Inject constructor(
    private val api: AFAScoreAPI
) : ResultRepository {
    override suspend fun getMatches(): Resource<List<Match>> {
        return try {
            Resource.Success(api.getResults().map { result -> result.mapToDomain() })
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "")
        }
    }
}