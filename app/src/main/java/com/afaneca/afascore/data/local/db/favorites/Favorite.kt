package com.afaneca.afascore.data.local.db.favorites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
class Favorite(
    @PrimaryKey @ColumnInfo(name = "match_id") val matchId: String,
)