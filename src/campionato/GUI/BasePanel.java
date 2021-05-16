package campionato.GUI;

import campionato.Campionato;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 * Classe astratta base per tutti i pannelli utilizzati.
 * 
 * @author Davide Petillo
 */
abstract public class BasePanel extends JPanel {
    public Dimension size;
    
    GUI g;
    Campionato c;
    
    
    /**
     * Metodo astratto, viene chiamato ogni volta che il pannello viene aggiornato.
     */
    abstract void reset();
    
    /**
     * Metodo per aggiungere i listener associati al pannello.
     */
    abstract void addListener();
    
    /**
     * Metodo che mostra a video un messaggio di errore.
     * 
     * @param msg 
     */
    public void errore(String msg){
        JOptionPane.showMessageDialog(this, msg, "Errore!", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Metodo che mostra a video un messaggio informativo.
     * 
     * @param msg 
     */
    public void info(String msg){
        JOptionPane.showMessageDialog(this, msg, "Info!", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Metodo che chiede conferma.
     * 
     * @param msg 
     */
    public boolean question (String msg){
        int scelta = JOptionPane.showConfirmDialog(this, msg, "Conferma!", JOptionPane.OK_OPTION);
        
        return scelta==0?true:false;
    }
}
