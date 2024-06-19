package com.alepagani.codechallengemovies.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
class GenreEntity(
    @PrimaryKey val genreId: Int,
    @ColumnInfo(name = "name")  val name: String
)