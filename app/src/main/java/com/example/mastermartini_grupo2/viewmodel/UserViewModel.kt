package com.example.mastermartini_grupo2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mastermartini_grupo2.model.User
import com.example.mastermartini_grupo2.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepository(application)

    fun addUser(user: User) {
        viewModelScope.launch {
            repository.addUser(user)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            repository.updateUser(user)
        }
    }

    suspend fun getUser(): User? {
        return repository.getUser()
    }

    fun validateUser(user: User): Boolean {
        return getValidationErrors(user).isEmpty()
    }

    fun getValidationErrors(user: User): Set<String> {
        val errors = mutableSetOf<String>()

        if (user.nombre.isBlank()) {
            errors.add("nombre")
        }

        if (user.correo.isBlank() || !isValidEmail(user.correo)) {
            errors.add("correo")
        }

        if (user.telefono.isBlank() || !isValidTelefono(user.telefono)) {
            errors.add("telefono")
        }

        if (user.direccion.isBlank()) {
            errors.add("direccion")
        }

        if (user.rutEmpresa.isBlank() || !isValidRut(user.rutEmpresa)) {
            errors.add("rutEmpresa")
        }

        if (user.tipoNegocio.isBlank()) {
            errors.add("tipoNegocio")
        }

        if (user.tamanoNegocio.isBlank()) {
            errors.add("tamanoNegocio")
        }

        if (user.preferenciasContenido.isBlank()) {
            errors.add("preferenciasContenido")
        }

        return errors
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS
            .matcher(email)
            .matches()
    }

    private fun isValidTelefono(telefono: String): Boolean {
        return telefono.matches(
            Regex("^(9\\d{8}|\\+569\\d{8})$")
        )
    }

    private fun isValidRut(rut: String): Boolean {

        val rutLimpio = rut
            .replace(".", "")
            .replace("-", "")
            .uppercase()

        if (!rutLimpio.matches(Regex("\\d{7,8}[0-9K]"))) {
            return false
        }

        val cuerpo = rutLimpio.dropLast(1)
        val digitoVerificador = rutLimpio.last()

        var suma = 0
        var multiplicador = 2

        for (digito in cuerpo.reversed()) {
            suma += digito.digitToInt() * multiplicador

            multiplicador++

            if (multiplicador > 7) {
                multiplicador = 2
            }
        }

        val resto = 11 - (suma % 11)

        val digitoEsperado = when (resto) {
            11 -> '0'
            10 -> 'K'
            else -> resto.digitToChar()
        }

        return digitoVerificador == digitoEsperado
    }

    // Validación del formulario de inicio de sesión.
    // La lógica de validación queda centralizada en el ViewModel.
    fun getLoginValidationErrors(
        correo: String,
        contrasena: String
    ): Set<String> {

        val errors = mutableSetOf<String>()

        if (correo.isBlank()) {
            errors.add("correo")
        }

        if (contrasena.isBlank()) {
            errors.add("contrasena")
        }

        return errors
    }

    fun validarLogin(
        correo: String,
        contrasena: String,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {

            val usuario = repository.getUser()

            val resultado = usuario != null &&
                    usuario.correo.equals(
                        correo.trim(),
                        ignoreCase = true
                    ) &&
                    usuario.correo.equals(
                        contrasena.trim(),
                        ignoreCase = true
                    )

            onResult(resultado)
        }
    }
}