package id.yuana.compose.movieapp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.yuana.compose.movieapp.data.local.database.entity.RemoteKeyEntity

@Dao
interface RemoteKeyEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKeyEntity>)

    @Query("SELECT * FROM remote_keys WHERE movie_id = :movieId")
    suspend fun getRemoteKeyByMovieID(movieId: Int): RemoteKeyEntity?

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAll()

    @Query("SELECT created_at FROM remote_keys ORDER BY created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}