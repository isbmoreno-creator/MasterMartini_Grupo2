package com.example.mastermartini_grupo2.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.mastermartini_grupo2.model.User
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class UserRepository(private val context: Context) {

    companion object {
        val NOMBRE = stringPreferencesKey("nombre")
        val CORREO = stringPreferencesKey("correo")
        val TELEFONO = stringPreferencesKey("telefono")
        val DIRECCION = stringPreferencesKey("direccion")
        val RUT_EMPRESA = stringPreferencesKey("rut_empresa")
        val TIPO_NEGOCIO = stringPreferencesKey("tipo_negocio")
        val TAMANO_NEGOCIO = stringPreferencesKey("tamano_negocio")
        val PREFERENCIAS = stringPreferencesKey("preferencias")
    }

    suspend fun addUser(user: User) {
        context.dataStore.edit { preferences ->
            preferences[NOMBRE] = user.nombre
            preferences[CORREO] = user.correo
            preferences[TELEFONO] = user.telefono
            preferences[DIRECCION] = user.direccion
            preferences[RUT_EMPRESA] = user.rutEmpresa
            preferences[TIPO_NEGOCIO] = user.tipoNegocio
            preferences[TAMANO_NEGOCIO] = user.tamanoNegocio
            preferences[PREFERENCIAS] = user.preferenciasContenido
        }
    }
    suspend fun updateUser(user: User) {
        context.dataStore.edit { preferences ->
            preferences[NOMBRE] = user.nombre
            preferences[CORREO] = user.correo
            preferences[TELEFONO] = user.telefono
            preferences[DIRECCION] = user.direccion
            preferences[RUT_EMPRESA] = user.rutEmpresa
            preferences[TIPO_NEGOCIO] = user.tipoNegocio
            preferences[TAMANO_NEGOCIO] = user.tamanoNegocio
            preferences[PREFERENCIAS] = user.preferenciasContenido
        }
    }

    suspend fun getUser(): User? {
        val preferences = context.dataStore.data.first()

        val nombre = preferences[NOMBRE] ?: return null
        val correo = preferences[CORREO] ?: return null

        return User(
            nombre = nombre,
            correo = correo,
            telefono = preferences[TELEFONO] ?: "",
            direccion = preferences[DIRECCION] ?: "",
            rutEmpresa = preferences[RUT_EMPRESA] ?: "",
            tipoNegocio = preferences[TIPO_NEGOCIO] ?: "",
            tamanoNegocio = preferences[TAMANO_NEGOCIO] ?: "",
            preferenciasContenido = preferences[PREFERENCIAS] ?: ""
        )
    }
}
