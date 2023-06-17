Para compilar y empaquetar en un archivo .jar:

    mvn clean compile
    mvn clean package

Para ejecutar se debe hacer desde la terminal:

usage:
    java -jar procesoSolicitante.jar [-h] -ipGestor1 IPGESTOR1 -ipGestor2 IPGESTOR2 -puertoGC PUERTOGC [-pathRequerimientos PATHREQUERIMIENTOS] -numArchivoRequerimientos NUMARCHIVOREQUERIMIENTOS

named arguments:
  -h, --help             show this help message and exit
  -ipGestor1 IPGESTOR1   Ip del Gestor de Carga de la sede 1
  -ipGestor2 IPGESTOR2   Ip del Gestor de Carga de la sede 2
  -puertoGC PUERTOGC     Puerto del Gestor de Carga
  -pathRequerimientos PATHREQUERIMIENTOS Path de los requerimientos
  -numArchivoRequerimientos NUMARCHIVOREQUERIMIENTOS Numero del archivo de requerimientos