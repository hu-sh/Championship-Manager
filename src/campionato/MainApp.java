package campionato;

import campionato.GUI.GUI;
import java.io.IOException;

/**
 * Classe principale, adibita al lancio dell'applicazione.
 * 
 * @author Davide Petillo, Cristian Pratesi
 */
public class MainApp {
    public static void main(String[] args) throws IOException, PartitaEsistenteException {
        Campionato campionato = new Campionato();
        //campionato.caricaParite();
        GUI gui = new GUI(campionato);
        
    }
}
