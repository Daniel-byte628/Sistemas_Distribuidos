Para compilar y empaquetar en un archivo .jar:

    mvn clean compile
    mvn clean package

Para ejecutar se debe hacer desde la terminal:
usage:
    java -jar gestorCarga.jar [-h] -puertoPS PUERTOPS -puertoRD PUERTORD -puertoP PUERTOP
java -jar gestorCarga.jar -puertoPS 5555 -puertoRD 5556 -puertoP 5557
    named arguments:
      -h, --help             show this help message and exit
      -puertoPS PUERTOPS     Puerto del Proceeso Solicitante
      -puertoRD PUERTORD     Puerto de los actores de Renovaación y Devolución
      -puertoP PUERTOP       Puerto del actor de Préstamo

