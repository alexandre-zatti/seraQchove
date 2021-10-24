package com.example.seraqchove.data.interfaces
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.seraqchove.data.AppDatabase
import com.example.seraqchove.data.entities.Location
import com.example.seraqchove.data.entities.User
import com.example.seraqchove.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class LocationDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var locationDao: LocationDao
    private lateinit var userDao: UserDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        locationDao = database.locationDao()
        userDao = database.userDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun createLocation() = runBlockingTest{
        val user = User(1,"userTest","passwTest",true)
        val location = Location(1,1, "London, United Kingdom")

        userDao.createUser(user)
        locationDao.createLocation(location)
        val userLocations = locationDao.getLocationByUser(1).getOrAwaitValue()

        assertThat(userLocations).contains(location)
    }

    @Test
    fun updateLocation() = runBlockingTest{
        val user = User(1,"userTest","passwTest",true)
        val location = Location(1,1, "London, United Kingdom")
        val updatedLocation = Location(1,1, "Rio de Janeiro, Brazil")

        userDao.createUser(user)
        locationDao.createLocation(location)
        locationDao.updateLocation(1,"London, United Kingdom", "Rio de Janeiro, Brazil")

        val userLocations = locationDao.getLocationByUser(1).getOrAwaitValue()

        assertThat(userLocations).contains(updatedLocation)
    }

    @Test
    fun deleteLocation() = runBlockingTest{
        val user = User(1,"userTest","passwTest",true)
        val location = Location(1,1, "London, United Kingdom")

        userDao.createUser(user)
        locationDao.createLocation(location)
        locationDao.deleteLocation(1,"London, United Kingdom")

        val userLocations = locationDao.getLocationByUser(1).getOrAwaitValue()

        assertThat(userLocations).doesNotContain(location)
    }

}