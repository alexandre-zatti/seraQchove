package com.example.seraqchove.data.interfaces

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.seraqchove.data.AppDatabase
import com.example.seraqchove.data.entities.User
import com.example.seraqchove.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class UserDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        userDao = database.userDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun createUser() = runBlocking {
        val user = User(1,"userTest","passwTest",true)
        userDao.createUser(user)

        val createdUser = userDao.getUserByUsername("userTest")

        assertThat(createdUser).contains(user)
    }

    @Test
    fun getLoggedUserWhenUserLogged() = runBlocking {
        val user = User(1,"userTest","passwTest",true)

        userDao.createUser(user)

        val loggedUser = userDao.getLoggedUser().getOrAwaitValue()

        assertThat(loggedUser).contains(user)
    }

    @Test
    fun getLoggedUserWhenUserNotLogged() = runBlocking {
        val user = User(1,"userTest","passwTest",false)

        userDao.createUser(user)

        val loggedUser = userDao.getLoggedUser().getOrAwaitValue()

        assertThat(loggedUser).doesNotContain(user)
    }

    @Test
    fun updateUserToLoggedStatus() = runBlocking {
        val userNotLogged  = User(1,"userTest","passwTest",false)
        val userLogged = User(1,"userTest","passwTest",true)

        userDao.createUser(userNotLogged)
        userDao.updateUserLoggedStatus(1,true)

        val loggedUser = userDao.getLoggedUser().getOrAwaitValue()

        assertThat(loggedUser).contains(userLogged)
    }

    @Test
    fun updateUserToNotLoggedStatus() = runBlocking {
        val userLogged = User(1,"userTest","passwTest",true)

        userDao.createUser(userLogged)
        userDao.updateUserLoggedStatus(1,false)

        val loggedUser = userDao.getLoggedUser().getOrAwaitValue()

        assertThat(loggedUser).doesNotContain(userLogged)
    }

}