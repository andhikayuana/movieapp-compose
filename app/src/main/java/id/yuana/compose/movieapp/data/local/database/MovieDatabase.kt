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

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import id.yuana.compose.movieapp.data.local.database.dao.MovieEntityDao
import id.yuana.compose.movieapp.data.local.database.dao.RemoteKeyEntityDao
import id.yuana.compose.movieapp.data.local.database.entity.MovieEntity
import id.yuana.compose.movieapp.data.local.database.entity.RemoteKeyEntity
import java.util.*

@Database(entities = [MovieEntity::class, RemoteKeyEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieEntityDao(): MovieEntityDao

    abstract fun remoteKeyEntityDao(): RemoteKeyEntityDao

}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}