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

package id.yuana.compose.movieapp.data.local.database


import androidx.paging.PagingSource
import androidx.room.*
import java.util.*

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val titleOriginal: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseDate: Date,
    val runtime: Int,
    val tagline: String
)

@Dao
interface MovieEntityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieEntity)

    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun find(movieId: Int): MovieEntity?

    @Query("DELETE FROM movies WHERE id = :movieId")
    suspend fun delete(movieId: Int)

    @Query("SELECT * FROM movies")
    fun paginate(): PagingSource<Int, MovieEntity>
}
