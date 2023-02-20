package com.afaneca.afascore.common

import com.afaneca.afascore.BuildConfig

/**
 * Created by António Faneca on 2/13/2023.
 */
object Constants {

    const val API_VERSION = "v1"
    const val API_BASE_URL = BuildConfig.AFA_SCORE_API_BASE_URL

    sealed class GameStatus(val statusTag: String) {
        object NotStarted : GameStatus("NOT_STARTED")
        object Ongoing : GameStatus("ONGOING")
        object Finished : GameStatus("FINISHED")
        object Unknown : GameStatus("UNKNOWN")

        override fun toString(): String {
            return statusTag
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is GameStatus) return false

            if (statusTag != other.statusTag) return false

            return true
        }

        override fun hashCode(): Int {
            return statusTag.hashCode()
        }
    }
}