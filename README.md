# Master Martini - Aplicación Móvil

## Descripción

Aplicación móvil desarrollada para el caso de Master Martini Chile SPA.

El proyecto busca entregar una plataforma móvil orientada a clientes y usuarios relacionados con el rubro de la panadería, pastelería, chocolate, heladería, restaurantes y servicios de alimentación.

La aplicación permite gestionar información del usuario y acceder a contenidos relacionados con productos, recetas y servicios de Master Martini.

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
- Inicio de sesión.
- Visualización del perfil.
- Edición de preferencias del usuario.

### Perfil de usuario

- Visualización de los datos registrados.
- Actualización de preferencias.
- Captura de fotografía mediante la cámara del dispositivo.
- Navegación entre las diferentes pantallas de la aplicación.

### Recetas

- Visualización de recetas almacenadas.
- Búsqueda de recetas.
- Visualización del detalle de una receta.
- Agregar nuevas recetas.
- Selección de fotografías para las recetas.
- Persistencia local de las recetas.
- Eliminación de recetas.

### Productos y servicios

- Visualización de productos relacionados con Master Martini.
- Visualización de servicios ofrecidos.
- Navegación entre las diferentes secciones de contenido.

### Recursos del dispositivo

- Acceso a la cámara del dispositivo.
- Selección de imágenes desde el dispositivo para asociarlas a recetas.

### Experiencia de usuario

- Interfaz desarrollada con Jetpack Compose.
- Navegación entre pantallas.
- Mensajes de confirmación.
- Animaciones para entregar retroalimentación al usuario.
- Diseño visual basado en la identidad de Master Martini.
- Interfaz organizada y adaptable al desplazamiento vertical.

## Tecnologías utilizadas

- Kotlin
- Android Studio
- Jetpack Compose
- Navigation Compose
- MVVM
- DataStore Preferences
- Git
- GitHub

## Arquitectura

El proyecto utiliza una estructura basada en MVVM, separando la interfaz de usuario, la lógica y el acceso a los datos.

```text
Model
   ↓
ViewModel
   ↓
Repository
   ↓
DataStore
```

Las pantallas de la aplicación se encuentran organizadas dentro del módulo de interfaz de usuario.

## Persistencia de datos

La aplicación utiliza DataStore Preferences para almacenar localmente información del usuario y las recetas.

Esto permite conservar los datos y recuperarlos posteriormente desde las diferentes funcionalidades de la aplicación.

## Recursos del dispositivo

La aplicación integra recursos del dispositivo móvil mediante:

- Cámara: permite tomar una fotografía de perfil.
- Selector de imágenes del dispositivo: permite seleccionar fotografías para asociarlas a las recetas.

Los permisos y accesos correspondientes se gestionan desde la aplicación antes de utilizar los recursos que lo requieren.

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