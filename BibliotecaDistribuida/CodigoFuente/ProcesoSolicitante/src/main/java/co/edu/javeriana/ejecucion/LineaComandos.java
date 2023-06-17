package co.edu.javeriana.ejecucion;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;


public class LineaComandos {

    public static String ipGestor1;
    public static String ipGestor2;
    public static int puertoGC;
    public static String pathRequerimientos;
    public static int numArchivoRequerimientos;

    public void analizarArgumentos(String[] args) {
        // Crear el analizador de argumentos
        ArgumentParser parser = ArgumentParsers.newFor("java -jar procesoSolicitante.jar")
                .addHelp(true)
                .build();

        // Agregar opciones y argumentos
        parser.addArgument("-ipGestor1")
                .dest("ipGestor1")
                .type(String.class)
                .required(true)
                .help("Ip del Gestor de Carga de la sede 1");

        parser.addArgument("-ipGestor2")
                .dest("ipGestor2")
                .type(String.class)
                .required(true)
                .help("Ip del Gestor de Carga de la sede 2");

        parser.addArgument("-puertoGC")
                .dest("puertoGC")
                .type(Integer.class)
                .required(true)
                .help("Puerto del Gestor de Carga");

        parser.addArgument("-pathRequerimientos")
                .dest("pathRequerimientos")
                .type(String.class)
                .setDefault("src/main/resources/")
                .help("Path de los requerimientos");

        parser.addArgument("-numArchivoRequerimientos")
                .dest("numArchivoRequerimientos")
                .type(Integer.class)
                .required(true)
                .help("Numero del archivo de requerimientos");

        try {
            Namespace namespace = parser.parseArgs(args);
            ipGestor1 = namespace.getString("ipGestor1");
            ipGestor2 = namespace.getString("ipGestor2");
            puertoGC = namespace.getInt("puertoGC");
            pathRequerimientos = namespace.getString("pathRequerimientos");
            numArchivoRequerimientos = namespace.getInt("numArchivoRequerimientos");

            System.out.println("ipGestor1: " + ipGestor1);
            System.out.println("ipGestor2: " + ipGestor2);
            System.out.println("puertoGC: " + puertoGC);
            System.out.println("pathRequerimientos: " + pathRequerimientos);
            System.out.println("numArchivoRequerimientos: " + numArchivoRequerimientos);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }
    }
}
