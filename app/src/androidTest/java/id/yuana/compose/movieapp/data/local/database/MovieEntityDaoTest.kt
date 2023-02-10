package id.yuana.compose.movieapp.data.local.database

import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import id.yuana.compose.movieapp.data.mapper.toEntity
import id.yuana.compose.movieapp.data.mapper.toModel
import id.yuana.compose.movieapp.data.remote.response.GetMoviePopularResponse
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class MovieEntityDaoTest {

    private lateinit var movieDatabase: MovieDatabase
    private lateinit var movieEntityDao: MovieEntityDao
    private lateinit var movieEntityDummy: MovieEntity

    @Before
    fun setup() {
        movieDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()

        movieEntityDao = movieDatabase.movieEntityDao()

        movieEntityDummy = createMovieEntity()
    }

    private fun createMovieEntity(): MovieEntity {
        val movieJson = "{\n" +
                "            \"adult\": false,\n" +
                "            \"backdrop_path\": \"/xDMIl84Qo5Tsu62c9DGWhmPI67A.jpg\",\n" +
                "            \"genre_ids\": [\n" +
                "            28,\n" +
                "            12,\n" +
                "            878\n" +
                "            ],\n" +
                "            \"id\": 505642,\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Black Panther: Wakanda Forever\",\n" +
                "            \"overview\": \"Queen Ramonda, Shuri, M’Baku, Okoye and the Dora Milaje fight to protect their nation from intervening world powers in the wake of King T’Challa’s death.  As the Wakandans strive to embrace their next chapter, the heroes must band together with the help of War Dog Nakia and Everett Ross and forge a new path for the kingdom of Wakanda.\",\n" +
                "            \"popularity\": 7332.994,\n" +
                "            \"poster_path\": \"/sv1xJUazXeYqALzczSZ3O6nkH75.jpg\",\n" +
                "            \"release_date\": \"2022-11-09\",\n" +
                "            \"title\": \"Black Panther: Wakanda Forever\",\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 7.5,\n" +
                "            \"vote_count\": 2763\n" +
                "        }"
        return Gson().fromJson(movieJson, GetMoviePopularResponse.Result::class.java)
            .toModel()
            .toEntity()
    }

    @After
    fun teardown() {
        movieDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun givenValidMovieEntityThenInsertSuccess() = runBlocking {
        //given
        val movieEntity = movieEntityDummy

        //when
        movieEntityDao.insert(movieEntity)

        //then
        val actual = movieEntityDao.find(movieEntity.id)
        assertThat(actual).isEqualTo(movieEntity)
    }

    @Test
    @Throws(Exception::class)
    fun givenValidMovieEntityThenUpdateSuccess() = runBlocking {
        val movieEntity = movieEntityDummy

        movieEntityDao.insert(movieEntity)

        val actual = movieEntityDao.find(movieEntity.id)
        assertThat(actual).isEqualTo(movieEntity)
        assertThat(actual?.title).isEqualTo("Black Panther: Wakanda Forever")

        val movieEntityUpdated = movieEntityDummy.copy(title = "Title Updated Here")

        movieEntityDao.insert(movieEntityUpdated)

        val actualUpdated = movieEntityDao.find(movieEntity.id)

        assertThat(actualUpdated).isEqualTo(movieEntityUpdated)
        assertThat(actualUpdated?.title).isEqualTo("Title Updated Here")
    }

    @Test
    @Throws(Exception::class)
    fun deleteMovieEntityThenSuccess(): Unit = runBlocking {
        val movieEntity = movieEntityDummy

        movieEntityDao.insert(movieEntity)

        val actual = movieEntityDao.find(movieEntity.id)
        assertThat(actual).isEqualTo(movieEntity)

        movieEntityDao.delete(movieEntity.id)

        assertThat(movieEntityDao.find(movieEntity.id)).isNull()
    }

    @Test
    @Throws(Exception::class)
    fun getPagingSourceThenSuccess(): Unit = runBlocking {
        val movieEntity = movieEntityDummy

        movieEntityDao.insert(movieEntity)

        val pagingSource = movieEntityDao.paginate()
        val actual = pagingSource.load(PagingSource.LoadParams.Refresh(1, 20, false))

        assertThat((actual as PagingSource.LoadResult.Page).data).contains(movieEntity)
    }
}