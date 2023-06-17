Sistema de Préstamo de Libros Distribuido
El objetivo de este proyecto es desarrollar un sistema de préstamo de libros distribuido que funcione en múltiples sedes de la Biblioteca Caribe. El sistema cuenta con los siguientes componentes:

Gestor de Carga
Este componente se encarga de recibir las peticiones de los procesos solicitantes y coordinar las acciones correspondientes.

Consulta el README del Gestor de Carga para obtener instrucciones detalladas sobre cómo ejecutarlo.
Procesos Solicitantes (PS)
Estos procesos son responsables de ingresar las operaciones sobre los libros que requieren los usuarios.

Puedes configurar los procesos solicitantes para que generen automáticamente las solicitudes a partir de un archivo de texto preconfigurado o utilicen un generador de carga como JMETER.
Cada proceso solicitante tiene su propio README con instrucciones para ejecutarlo correctamente.
Actores
Los actores son los encargados de interactuar con la base de datos y sus réplicas.

Actores para Devoluciones y Renovaciones: Hay al menos dos actores que se comunican con el gestor utilizando el patrón Publicador/Suscriptor para gestionar las devoluciones y renovaciones de libros.
Actor para Solicitudes de Préstamo: Existe un actor destinado a atender las solicitudes de préstamos.
Cada actor tiene su propio README con instrucciones para ejecutarlo correctamente.
Base de datos MongoDB
La base de datos utilizada en este sistema es MongoDB. Debes asegurarte de tener una instancia de MongoDB configurada y accesible para el correcto funcionamiento del sistema.

Consulta la sección correspondiente en el README del componente específico para obtener más información sobre la configuración de la base de datos.
Replicación de la base de datos
Para la replicación de la base de datos, deberás realizar la configuración manualmente.

Continuidad del servicio
En caso de que un gestor de una sede se caiga, las peticiones serán reenviadas automáticamente al otro gestor disponible para garantizar la continuidad del servicio.

Asegúrate de revisar los README de cada componente para obtener instrucciones detalladas sobre cómo ejecutarlos y configurar el entorno necesario.

¡Gracias por utilizar nuestro sistema de préstamo de libros distribuido! Esperamos que sea de utilidad para tu proyecto en la Biblioteca Caribe.
