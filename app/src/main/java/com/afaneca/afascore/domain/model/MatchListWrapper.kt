package com.afaneca.afascore.domain.model

/**
 * Created by António Faneca on 2/16/2023.
 */
data class MatchListWrapper(
    val matchList: List<Match>,
    val filterData: FilterData,
)

