# 🍸 Master Martini Grupo 2

Aplicación móvil desarrollada para **Master Martini Chile SPA**, orientada a facilitar el acceso a productos y recetas desde una aplicación Android.

El proyecto fue desarrollado utilizando **Kotlin, Jetpack Compose, Retrofit y APIs externas**, incorporando almacenamiento local, consumo de servicios REST, operaciones CRUD y pruebas unitarias.

---

## 👥 Integrantes

- **Katherin Moreno**
- **Jhon Villegas**

---

## 📱 Tecnologías utilizadas

- Kotlin
- Android Studio
- Jetpack Compose
- Material 3
- Retrofit
- Gson
- DummyJSON API
- DataStore Preferences
- Coroutines
- JUnit
- MockK
- Robolectric
- Git y GitHub

---

## ✨ Funcionalidades

### 👤 Usuarios

- Registro de usuario.
- Inicio de sesión.
- Validación de datos.
- Almacenamiento local de información mediante DataStore.
- Edición de información del usuario.
- Cierre de sesión.
- Gestión de contraseña.

### 🛒 Productos

- Visualización de productos obtenidos desde una API externa.
- Consulta de productos mediante Retrofit.
- Creación de productos mediante API.
- Actualización de productos mediante API.
- Eliminación de productos mediante API.
- Visualización de información del producto.
- Validación de datos al crear productos.

### 🍰 Recetas

- Exploración de recetas obtenidas desde una API externa.
- Visualización del detalle de una receta.
- Sección de recetas propias.
- Creación de recetas propias.
- Edición de recetas.
- Eliminación de recetas.

### 🧪 Pruebas unitarias

El proyecto incorpora pruebas unitarias para validar la lógica de:

- ProductViewModel
- RecipeViewModel
- UserViewModel
- RecipeRepository
- UserRepository

Las pruebas utilizan **JUnit, MockK, Coroutines Test y Robolectric**.

Actualmente se ejecutan **41 pruebas unitarias correctamente**.

---

## 🌐 API externa

El proyecto utiliza **DummyJSON** como API externa.

### Productos

Base URL:

`https://dummyjson.com/`

Endpoints utilizados:

| Método | Endpoint | Función |
|---|---|---|
| GET | `/products` | Obtener productos |
| POST | `/products/add` | Crear producto |
| PUT | `/products/{id}` | Actualizar producto |
| DELETE | `/products/{id}` | Eliminar producto |

### Recetas

Base URL:

`https://dummyjson.com/`

La aplicación utiliza los endpoints de recetas proporcionados por DummyJSON para consultar y mostrar información de recetas.

---

## 🏗️ Arquitectura

La aplicación se organiza utilizando una estructura basada en:

```text
UI / Screens
     ↓
ViewModel
     ↓
Repository
     ↓
Retrofit / DataStore
     ↓
API externa / almacenamiento local
```

### ViewModel

Los ViewModel administran el estado de la aplicación y contienen la lógica necesaria para comunicarse con los repositorios.

### Repository

Los Repository se encargan de acceder a las fuentes de datos.

- `ProductRepository` → productos mediante Retrofit.
- `RecipeRepository` → recetas y almacenamiento de recetas propias.
- `UserRepository` → información del usuario mediante DataStore.

### Retrofit

Retrofit permite realizar las solicitudes HTTP hacia la API externa y convertir las respuestas JSON a objetos Kotlin.

### DataStore

DataStore se utiliza para almacenar localmente la información del usuario.

---

## 🧪 Pruebas y cobertura

El proyecto cuenta con pruebas unitarias utilizando:

- JUnit
- MockK
- Robolectric
- Kotlin Coroutines Test

Se realizaron pruebas sobre la lógica de los ViewModel y Repository, cubriendo los principales casos de funcionamiento y validación.

**Total de pruebas ejecutadas: 41**

Resultado:

```text
41 tests passed
```

La cobertura de la lógica evaluada en ViewModel y Repository supera el **80% requerido**.

---

## 🔐 APK firmado

La aplicación cuenta con una versión **Release firmada**.

Archivo generado:

```text
app-release.apk
```

La aplicación fue firmada utilizando un archivo de claves:

```text
mastermartini-release.jks
```

El APK corresponde a la versión Release de la aplicación y puede instalarse en dispositivos Android compatibles.

---

## ▶️ Ejecución del proyecto

### Requisitos

- Android Studio
- JDK 17
- Android SDK
- Dispositivo Android o emulador
- Conexión a Internet para utilizar las APIs externas

### Pasos

1. Clonar el repositorio:

```bash
git clone https://github.com/isbmoreno-creator/MasterMartini_Grupo2.git
```

2. Abrir el proyecto en Android Studio.

3. Esperar la sincronización de Gradle.

4. Seleccionar un dispositivo físico o emulador Android.

5. Ejecutar la aplicación desde Android Studio.

---

## 📂 Estructura principal

```text
app/
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com.example.mastermartini_grupo2/
    │   │       ├── api/
    │   │       ├── model/
    │   │       ├── repository/
    │   │       ├── ui/
    │   │       └── viewmodel/
    │   │
    │   └── AndroidManifest.xml
    │
    └── test/
        └── java/
            └── com.example.mastermartini_grupo2/
                ├── ProductViewModelTest.kt
                ├── RecipeRepositoryTest.kt
                ├── RecipeViewModelTest.kt
                ├── UserRepositoryTest.kt
                └── UserViewModelTest.kt
```

---

## 📌 Estado del proyecto

- ✅ Interfaz móvil implementada.
- ✅ Registro e inicio de sesión.
- ✅ Gestión de productos.
- ✅ Consumo de API externa mediante Retrofit.
- ✅ Gestión de recetas.
- ✅ Almacenamiento local mediante DataStore.
- ✅ Operaciones CRUD.
- ✅ Pruebas unitarias.
- ✅ 41 pruebas ejecutadas correctamente.
- ✅ APK Release generado.
- ✅ APK firmado.
- ✅ Keystore generado.
- ✅ Proyecto gestionado mediante Git y GitHub.