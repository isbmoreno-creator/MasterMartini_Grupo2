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
}