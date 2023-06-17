# Sistema de Préstamo de Libros Distribuido
El objetivo de este proyecto es desarrollar un sistema de préstamo de libros distribuido para la Biblioteca Caribe. El sistema funcionará en al menos dos sedes de la biblioteca, y cada sede contará con una base de datos replicada.

## Componentes del sistema
### Gestor de Carga
El gestor de carga recibe las peticiones de los procesos solicitantes y realiza las acciones correspondientes.

#### Instrucciones de ejecución: 
Consulta el archivo README del Gestor de Carga para obtener información detallada sobre cómo ejecutarlo.

### Procesos Solicitantes (PS)
Los procesos solicitantes ingresan las operaciones sobre los libros solicitados por los usuarios.

#### Operaciones disponibles:

1. Devolver un libro: permite a un usuario devolver un libro específico.
2. Renovar un libro: permite a un usuario renovar un libro que ya posee por una semana adicional.
3. Solicitar préstamo de un libro: permite solicitar el préstamo de un libro específico.
#### Configuración:
Puedes configurar los procesos solicitantes para generar automáticamente las solicitudes a partir de un archivo de texto preconfigurado o utilizar un generador de carga como JMETER.

#### Instrucciones de ejecución:
Consulta el archivo README del Proceso Solicitante para obtener información detallada sobre cómo ejecutarlo.

### Actores
Los actores interactúan con la base de datos y sus réplicas.

##### Actores para Devoluciones y Renovaciones: 
Existen al menos dos actores que utilizan el patrón Publicador/Suscriptor para gestionar las devoluciones y renovaciones de libros.

#### Actor para Solicitudes de Préstamo: 
Existe un actor dedicado a atender las solicitudes de préstamo.

#### Instrucciones de ejecución: 
Consulta el archivo README de cada Actor para obtener información detallada sobre cómo ejecutarlos.

### Base de datos MongoDB
El sistema utiliza MongoDB como base de datos para almacenar la información.

#### Configuración de la base de datos: 
Asegúrate de tener una instancia de MongoDB configurada y accesible para el correcto funcionamiento del sistema.

#### Instrucciones de configuración: 
Consulta la sección correspondiente en el archivo README del componente específico para obtener información detallada sobre cómo configurar la base de datos.

#### Replicación de la base de datos
La replicación de la base de datos debe configurarse manualmente para garantizar la consistencia entre las sedes.

#### Continuidad del servicio
En caso de que un gestor de una sede se caiga, las peticiones se reenviarán automáticamente al otro gestor disponible para asegurar la continuidad del servicio.

Asegúrate de revisar los archivos README de cada componente para obtener instrucciones detalladas sobre cómo ejecutarlos y configurar el entorno necesario.

Gracias por utilizar nuestro sistema de préstamo de libros distribuido. Esperamos que sea de utilidad para tu proyecto en la Biblioteca Caribe.

###### MÁS INFORMACIÓN EN EL PDF PRINCIPAL
