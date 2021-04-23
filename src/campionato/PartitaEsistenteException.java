package campionato;

/**
 * Eccezione lanciata se si tenta di aggiungere una partita già esistente.
 * 
 * @author Davide Petillo, Cristian Pratesi
 */
public class PartitaEsistenteException extends Exception {

    public PartitaEsistenteException(String msg) {
        super(msg);
    }
    
    public String toString(){
        return super.getMessage();
    }
    
}
