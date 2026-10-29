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
        return user.nombre.isNotBlank() &&
                user.correo.isNotBlank() &&
                user.telefono.isNotBlank() &&
                user.direccion.isNotBlank() &&
                user.rutEmpresa.isNotBlank() &&
                user.tipoNegocio.isNotBlank() &&
                user.tamanoNegocio.isNotBlank() &&
                user.preferenciasContenido.isNotBlank()
    }

    fun getValidationErrors(user: User): Set<String> {
        val errors = mutableSetOf<String>()

        if (user.nombre.isBlank()) {
            errors.add("nombre")
        }

        if (user.correo.isBlank()) {
            errors.add("correo")
        }

        if (user.telefono.isBlank()) {
            errors.add("telefono")
        }

        if (user.direccion.isBlank()) {
            errors.add("direccion")
        }

        if (user.rutEmpresa.isBlank()) {
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
}