# Master Martini - Aplicación Móvil

## Descripción

Aplicación móvil desarrollada para el caso de Master Martini Chile SPA.

El proyecto busca entregar una plataforma móvil orientada a clientes y usuarios relacionados con el rubro de la panadería, pastelería, chocolate, heladería, restaurantes y servicios de alimentación.

La aplicación permite gestionar información del usuario y acceder a contenidos relacionados con la actividad de Master Martini.

## Integrantes

- Katherin Moreno
- Jhon Villegas

## Funcionalidades

### Gestión de usuarios

- Registro de usuarios.
- Validación de los datos ingresados.
- Validación de correo electrónico.
- Validación de teléfono.
- Validación de RUT de empresa.
- Mensajes visuales de validación.
- Persistencia local de los datos del usuario.
- Visualización del perfil.
- Edición de preferencias del usuario.

### Perfil de usuario

- Visualización de los datos registrados.
- Actualización de preferencias.
- Captura de fotografía mediante la cámara del dispositivo.
- Navegación entre las diferentes pantallas de la aplicación.

### Contenidos

- Recetas.
- Cursos.
- Videos.
- Visualización del detalle de contenidos.

### Recursos del dispositivo

- Acceso a la cámara del dispositivo.

### Experiencia de usuario

- Interfaz desarrollada con Jetpack Compose.
- Navegación entre pantallas.
- Mensajes de confirmación.
- Animaciones para entregar retroalimentación al usuario.
- Diseño visual basado en la identidad de Master Martini.

## Tecnologías utilizadas

- Kotlin
- Android Studio
- Jetpack Compose
- Navigation Compose
- MVVM
- DataStore
- Git
- GitHub

## Arquitectura

El proyecto utiliza una estructura basada en MVVM, separando la interfaz de usuario, la lógica y el acceso a los datos.

```text
model
    ↓
viewmodel
    ↓
repository
    ↓
DataStore
```

Las pantallas de la aplicación se encuentran organizadas dentro del módulo de interfaz de usuario.

## Persistencia de datos

La aplicación utiliza DataStore Preferences para almacenar localmente la información del usuario.

Esto permite conservar los datos registrados y recuperar la información posteriormente desde el perfil.

## Ejecución del proyecto

### Requisitos

- Android Studio.
- JDK compatible con la versión del proyecto.
- Emulador Android o dispositivo físico.

### Pasos

1. Clonar el repositorio desde GitHub.
2. Abrir el proyecto en Android Studio.
3. Esperar la sincronización de Gradle.
4. Seleccionar un emulador o dispositivo Android.
5. Ejecutar la aplicación.

## Repositorio

El proyecto se encuentra alojado en GitHub y utiliza ramas para permitir el trabajo independiente de los integrantes del equipo.

## Nota

El proyecto corresponde a una implementación académica basada en el caso de Master Martini Chile SPA.