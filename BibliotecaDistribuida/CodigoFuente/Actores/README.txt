Para compilar y empaquetar en un archivo .jar:

    mvn clean compile
    mvn clean package

Para ejecutar se debe hacer desde la terminal:

usage:
    java -jar actores.jar [-h] -ipGestor IPGESTOR -puertoRD PUERTORD -puertoP PUERTOP
java -jar actores.jar -ipGestor localhost -puertoRD 5556 -puertoP 5557
    named arguments:
      -h, --help             show this help message and exit
      -ipGestor IPGESTOR     Ip del Gestor de Carga
      -puertoRD PUERTORD     Puerto de los actores de Renovaación y Devolución
      -puertoP PUERTOP       Puerto del actor de Préstamo
