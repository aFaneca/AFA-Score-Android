package com.afaneca.afascore.ui

/**
 * Created by António Faneca on 2/13/2023.
 */
sealed class Screen(val route: String){
    object MatchListScreen: Screen("match_list_screen")
}
