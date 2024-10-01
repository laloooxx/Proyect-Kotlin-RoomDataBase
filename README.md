# Aplicación de Notas en Kotlin

Este es un proyecto de aplicación de notas desarrollado en Kotlin para dispositivos Android, utilizando Room como base de datos local y los patrones de diseño DAO y Repository para la gestión de datos. La aplicación permite a los usuarios crear, editar, eliminar y listar notas.

## Características:
Añadir, editar y eliminar notas.
Almacenamiento local de notas usando Room.
Patrones de diseño DAO y Repository para una mejor organización y separación de responsabilidades en la gestión de la base de datos.
Implementación de LiveData para observar los cambios en la base de datos y actualizar automáticamente la interfaz de usuario.
Uso de ViewModel para mantener la lógica de negocio separada de la UI.
Aplicación de Coroutines para operaciones de base de datos asíncronas, evitando bloquear la interfaz de usuario.


## Tecnologías Utilizadas: 
Kotlin: Lenguaje de programación principal.
Room: ORM para Android que facilita el manejo de la base de datos.
Coroutines: Para realizar operaciones de base de datos de manera asíncrona.
LiveData: Observa los cambios en la base de datos y actualiza la UI automáticamente.
Jetpack Compose: Para la interfaz de usuario declarativa.
MVVM: Patrón de arquitectura para mantener la separación de responsabilidades.


## Arquitectura:
El proyecto sigue una arquitectura basada en MVVM (Model-View-ViewModel) para mantener un código modular, limpio y de fácil mantenimiento.


## Componentes Principales
NoteDatabase: Clase que define la base de datos de Room, configurando la entidad Note y el DAO asociado (NotesDao). Asegura una única instancia de la base de datos mediante un patrón Singleton.

NotesDao: Interfaz que define las operaciones de acceso a la base de datos (insert, update, delete, getNotes, findById). Utiliza anotaciones de Room para definir las consultas necesarias.

NotesRepository: Clase que interactúa con NotesDao para realizar las operaciones de base de datos. Utiliza coroutines para realizar las tareas de forma asíncrona.

NoteViewModel: Clase que maneja la lógica de la aplicación. Mantiene la información de las notas y gestiona la interacción con el repositorio.

MainActivity y CrudScreen: Interfaz de usuario construida utilizando Jetpack Compose, permitiendo al usuario interactuar con las notas mediante un diseño moderno y reactivo.

## Requisitos Previos: 
Android Studio 4.0 o superior.
Kotlin 1.4 o superior.
Conexión a internet para descargar dependencias.

## Instalación: 
Clona este repositorio en tu máquina local
Abre el proyecto en Android Studio.
Ejecuta la aplicación en un emulador o dispositivo físico.


## Cómo Usar la Aplicación

Crear una nota: Pulsa el botón flotante (+) para abrir el cuadro de diálogo y escribir el contenido de la nueva nota.

Editar una nota: Haz clic en el ícono de edición junto a la nota que deseas modificar.

Eliminar una nota: Pulsa el ícono de eliminación junto a la nota que deseas borrar.

Visualizar todas las notas: Todas las notas se muestran en la pantalla principal, y la lista se actualiza automáticamente cuando se añaden, editan o eliminan notas.

## Contribuciones: 
Las contribuciones son bienvenidas. Por favor, abre un "issue" para discutir cualquier cambio importante antes de realizar una "pull request".
