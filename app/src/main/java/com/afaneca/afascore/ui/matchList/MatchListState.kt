package com.afaneca.afascore.ui.matchList

import com.afaneca.afascore.domain.model.Match
import com.afaneca.afascore.ui.model.MatchUiModel

/**
 * Created by António Faneca on 2/13/2023.
 */
data class MatchListState(
    val isLoading: Boolean = false,
    val matchList: List<MatchUiModel>? = null,
    val error: String? = null,
    val isFiltering: Boolean = false
)