# Code Challenge Brubank – Mobile Developer

## Introducción
Este proyecto fue desarrollado como parte de un code challenge para una posición de desarrollador Android.

## Arquitectura y Patrones Utilizados
Para asegurar un desarrollo estructurado y mantenible, se implementó Clean Architecture junto con el patrón MVVM (Model-View-ViewModel). 
Decidi utilizar Clean Architecture, debido a que si este proyecto escala, podrimos tener los beneficios de este principio. Y que, conjuntamente a MVVM tenemos una arquitectura solida, facil de mantener, de extender y de testear.
A continuación, se describen brevemente cada uno de estos componentes:

### Clean Architecture
Divide el proyecto en capas bien definidas (presentation, domain, data), separando responsabilidades y facilitando la prueba y la evolución independiente de cada capa.

### MVVM (Model-View-ViewModel)
Separación clara de la lógica de negocio de la interfaz de usuario. El ViewModel actúa como intermediario entre los datos y la interfaz de usuario, utilizando componentes como Flow y StateFlow para una programación reactiva.
En el viewmodel se emite en un unico StateFlow el resultado de la lista completa de recetas o ademas se emite el resultado del filtro. Por lo que con unico StateFlow manejamos de manera reactiva los cambios de la vista.

[![N|Solid](https://miro.medium.com/v2/resize:fit:640/format:webp/0*mwVSPyoOCFtSufKh.png)](https://nodesource.com/products/nsolid)

## Tecnologías y Bibliotecas Clave
**Retrofit:** Biblioteca de cliente HTTP para comunicarse con la API de recetas.
**Hilt:** Biblioteca recomendada por Google para la inyección de dependencias en Android.
**Navigation Component:** Utilizado para la navegación entre las tres pantallas principales de la aplicación.
**Coroutines:** Para el manejo de operaciones asincrónicas de manera sencilla y eficiente.
**Flow y StateFlow:** Parte de la API de Kotlin Coroutines que facilita la programación reactiva. Flow es una construcción para emitir valores de manera asincrónica y continuamente, mientras que StateFlow es un contenedor de flujo de datos con un valor que puede ser observado y actualizado de manera reactiva.

## La aplicacion consta de 3 fragments
Home Screen: Lista de peliculas y lista de subscripciones.
Detail Screen: Detalles de la pelicula seleccionada.
Search Screen: Lista todas las peliculas y se puede filtar por medio del nombre de la misma.