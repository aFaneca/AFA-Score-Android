package com.afaneca.afascore.common

/**
 * Created by António Faneca on 2/13/2023.
 */
object Constants {

    const val API_VERSION = "v1"

    sealed class GAME_STATUS(val statusTag: String) {
        object NOT_STARTED : GAME_STATUS("NOT_STARTED")
        object ONGOING : GAME_STATUS("ONGOING")
        object FINISHED : GAME_STATUS("FINISHED")
    }
}