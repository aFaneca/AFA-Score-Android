package com.afaneca.afascore.domain.repository

import com.afaneca.afascore.common.Resource
import com.afaneca.afascore.domain.model.Match

/**
 * Created by António Faneca on 2/13/2023.
 */
interface MatchesRepository {
    suspend fun getMatches(): Resource<List<Match>>
}