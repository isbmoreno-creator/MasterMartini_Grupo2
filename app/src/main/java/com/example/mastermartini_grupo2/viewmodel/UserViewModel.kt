package com.example.mastermartini_grupo2.viewmodel
import androidx.lifecycle.ViewModel
import com.example.mastermartini_grupo2.model.User
import com.example.mastermartini_grupo2.repository.UserRepository

class UserViewModel : ViewModel(){

    private val repository = UserRepository()

    fun addUser(user: User) {
        repository.addUser(user)
    }

    fun getUsers(): List<User> {
        return repository.getUsers()
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