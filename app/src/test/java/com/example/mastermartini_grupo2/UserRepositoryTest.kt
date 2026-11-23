package com.example.mastermartini_grupo2

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.example.mastermartini_grupo2.model.User
import com.example.mastermartini_grupo2.repository.UserRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UserRepositoryTest {

    private lateinit var repository: UserRepository

    @Before
    fun setup() {
        val application =
            ApplicationProvider.getApplicationContext<Application>()

        repository = UserRepository(application)
    }

    @Test
    fun getUser_whenNoUserSaved_returnsNull() = runTest {
        val user = repository.getUser()

        assertNull(user)
    }

    @Test
    fun addUser_thenGetUser_returnsSavedUser() = runTest {
        val user = validUser()

        repository.addUser(user)

        val result = repository.getUser()

        assertEquals(user, result)
    }

    @Test
    fun updateUser_thenGetUser_returnsUpdatedUser() = runTest {
        val originalUser = validUser()

        repository.addUser(originalUser)

        val updatedUser = originalUser.copy(
            nombre = "Pedro Gonzalez",
            correo = "pedro@gmail.com",
            telefono = "987654321",
            direccion = "Maipu",
            rutEmpresa = "76987654-3",
            tipoNegocio = "Cafeteria",
            tamanoNegocio = "Pequena",
            preferenciasContenido = "Promociones",
            contrasena = "654321"
        )

        repository.updateUser(updatedUser)

        val result = repository.getUser()

        assertEquals(updatedUser, result)
    }

    private fun validUser(): User {
        return User(
            nombre = "Juan Perez",
            correo = "juan@gmail.com",
            telefono = "912345678",
            direccion = "Santiago",
            rutEmpresa = "76123456-0",
            tipoNegocio = "Restaurante",
            tamanoNegocio = "Mediana",
            preferenciasContenido = "Recetas",
            contrasena = "123456"
        )
    }
}