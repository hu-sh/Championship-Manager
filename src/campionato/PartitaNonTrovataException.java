package campionato;

/**
 * Eccezione lanciata se una partita non viene trovata.
 * 
 * @author Davide Petillo, Cristian Pratesi
 */
public class PartitaNonTrovataException extends Exception {

    public PartitaNonTrovataException(String msg) {
        super(msg);
    }
    public String toString(){
        return super.getMessage();
    }
    
}
