package com.afaneca.afascore.data.remote

import com.afaneca.afascore.common.Constants.API_VERSION
import com.afaneca.afascore.data.remote.dto.MatchEntity
import retrofit2.http.GET

/**
 * Created by António Faneca on 2/13/2023.
 */
interface AfaScoreAPI {

    @GET("/${API_VERSION}/results")
    suspend fun getResults(): List<MatchEntity>
}