package ejecucion;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;


public class LineaComandos {

    public static String ipGestor;
    public static int puertoRD;
    public static int puertoP;

    public void analizarArgumentos(String[] args) {
        // Crear el analizador de argumentos
        ArgumentParser parser = ArgumentParsers.newFor("\njava -jar actores.jar")
                .addHelp(true)
                .build();

        // Agregar opciones y argumentos
        parser.addArgument("-ipGestor")
                .dest("ipGestor")
                .type(String.class)
                .required(true)
                .help("Ip del Gestor de Carga");

        parser.addArgument("-puertoRD")
                .dest("puertoRD")
                .type(Integer.class)
                .required(true)
                .help("Puerto de los actores de Renovaación y Devolución");

        parser.addArgument("-puertoP")
                .dest("puertoP")
                .type(Integer.class)
                .required(true)
                .help("Puerto del actor de Préstamo");

        try {
            Namespace namespace = parser.parseArgs(args);
            ipGestor = namespace.getString("ipGestor");
            puertoRD = namespace.getInt("puertoRD");
            puertoP = namespace.getInt("puertoP");
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }
    }
}
