/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package id.yuana.compose.movieapp.data.local.database.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    @ColumnInfo(name = "title_original")
    val titleOriginal: String,
    val overview: String,
    val popularity: Double,
    @ColumnInfo(name = "poster_path")
    val posterPath: String,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,
    @ColumnInfo(name = "vote_count")
    val voteCount: Int,
    @ColumnInfo(name = "release_date")
    val releaseDate: Date,
    val runtime: Int,
    val tagline: String,
    val page: Int = -1
)