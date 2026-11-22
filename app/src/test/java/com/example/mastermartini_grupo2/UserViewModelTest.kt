package com.example.mastermartini_grupo2

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.example.mastermartini_grupo2.model.User
import com.example.mastermartini_grupo2.viewmodel.UserViewModel
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UserViewModelTest {

    private lateinit var viewModel: UserViewModel

    @Before
    fun setup() {
        val application =
            ApplicationProvider.getApplicationContext<Application>()

        viewModel = UserViewModel(application)
    }

    @Test
    fun loginValidation_withValidData_returnsNoErrors() {
        val errors = viewModel.getLoginValidationErrors(
            correo = "usuario@gmail.com",
            contrasena = "123456"
        )

        assertTrue(errors.isEmpty())
    }

    @Test
    fun loginValidation_withEmptyEmail_returnsEmailError() {
        val errors = viewModel.getLoginValidationErrors(
            correo = "",
            contrasena = "123456"
        )

        assertTrue(errors.contains("correo"))
    }

    @Test
    fun loginValidation_withEmptyPassword_returnsPasswordError() {
        val errors = viewModel.getLoginValidationErrors(
            correo = "usuario@gmail.com",
            contrasena = ""
        )

        assertTrue(errors.contains("contrasena"))
    }

    @Test
    fun loginValidation_withEmptyEmailAndPassword_returnsBothErrors() {
        val errors = viewModel.getLoginValidationErrors(
            correo = "",
            contrasena = ""
        )

        assertTrue(errors.contains("correo"))
        assertTrue(errors.contains("contrasena"))
    }

    @Test
    fun validateUser_withValidUser_returnsTrue() {
        val user = validUser()

        assertTrue(viewModel.validateUser(user))
    }

    @Test
    fun validateUser_withEmptyName_returnsFalse() {
        val user = validUser().copy(nombre = "")

        assertFalse(viewModel.validateUser(user))
    }

    @Test
    fun validateUser_withInvalidEmail_returnsFalse() {
        val user = validUser().copy(
            correo = "correo-invalido"
        )

        assertFalse(viewModel.validateUser(user))
    }

    @Test
    fun validateUser_withInvalidPhone_returnsFalse() {
        val user = validUser().copy(
            telefono = "12345"
        )

        assertFalse(viewModel.validateUser(user))
    }

    @Test
    fun validateUser_withInvalidRut_returnsFalse() {
        val user = validUser().copy(
            rutEmpresa = "12345678-0"
        )

        assertFalse(viewModel.validateUser(user))
    }

    @Test
    fun validateUser_withEmptyAddress_returnsFalse() {
        val user = validUser().copy(
            direccion = ""
        )

        assertFalse(viewModel.validateUser(user))
    }

    @Test
    fun validateUser_withEmptyBusinessType_returnsFalse() {
        val user = validUser().copy(
            tipoNegocio = ""
        )

        assertFalse(viewModel.validateUser(user))
    }

    @Test
    fun validateUser_withEmptyBusinessSize_returnsFalse() {
        val user = validUser().copy(
            tamanoNegocio = ""
        )

        assertFalse(viewModel.validateUser(user))
    }

    @Test
    fun validateUser_withEmptyPreferences_returnsFalse() {
        val user = validUser().copy(
            preferenciasContenido = ""
        )

        assertFalse(viewModel.validateUser(user))
    }

    @Test
    fun getValidationErrors_withValidUser_returnsNoErrors() {
        val errors = viewModel.getValidationErrors(validUser())

        assertTrue(errors.isEmpty())
    }

    @Test
    fun getValidationErrors_withMultipleInvalidFields_returnsAllErrors() {
        val user = validUser().copy(
            nombre = "",
            correo = "",
            telefono = "",
            direccion = "",
            rutEmpresa = "",
            tipoNegocio = "",
            tamanoNegocio = "",
            preferenciasContenido = ""
        )

        val errors = viewModel.getValidationErrors(user)

        assertTrue(errors.contains("nombre"))
        assertTrue(errors.contains("correo"))
        assertTrue(errors.contains("telefono"))
        assertTrue(errors.contains("direccion"))
        assertTrue(errors.contains("rutEmpresa"))
        assertTrue(errors.contains("tipoNegocio"))
        assertTrue(errors.contains("tamanoNegocio"))
        assertTrue(errors.contains("preferenciasContenido"))
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