package ejecucion;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;


public class LineaComandos {

    public static int puertoPS;
    public static int puertoP;
    public static int puertoRD;

    public void analizarArgumentos(String[] args) {

        // Crear el analizador de argumentos
        ArgumentParser parser = ArgumentParsers.newFor("\njava -jar gestorCarga.jar")
                .addHelp(true)
                .build();

        // Agregar opciones y argumentos
        parser.addArgument("-puertoPS")
                .type(Integer.class)
                .required(true)
                .help("Puerto del Proceeso Solicitante");

        parser.addArgument("-puertoRD")
                .type(Integer.class)
                .required(true)
                .help("Puerto de los actores de Renovaación y Devolución");

        parser.addArgument("-puertoP")
                .type(Integer.class)
                .required(true)
                .help("Puerto del actor de Préstamo");

        try {
            Namespace namespace = parser.parseArgs(args);
            puertoPS = namespace.getInt("puertoPS");
            puertoRD = namespace.getInt("puertoRD");
            puertoP = namespace.getInt("puertoP");
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }
    }
}
