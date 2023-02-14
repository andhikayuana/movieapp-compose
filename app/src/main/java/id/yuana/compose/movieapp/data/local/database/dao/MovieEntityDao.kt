package id.yuana.compose.movieapp.data.local.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.yuana.compose.movieapp.data.local.database.entity.MovieEntity

@Dao
interface MovieEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(vararg movie: MovieEntity)

    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun find(movieId: Int): MovieEntity?

    @Query("DELETE FROM movies WHERE id = :movieId")
    suspend fun delete(movieId: Int)

    @Query("DELETE FROM movies")
    suspend fun deleteAll()

    @Query("SELECT * FROM movies ORDER BY page")
    fun paginate(): PagingSource<Int, MovieEntity>
}
