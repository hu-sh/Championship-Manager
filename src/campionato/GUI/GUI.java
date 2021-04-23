package campionato.GUI;

import campionato.Campionato;
import campionato.Partita;
import campionato.PartitaEsistenteException;
import campionato.Squadra;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * Classe JFrame che gestisce l'intera interfaccia grafica.
 * 
 * @author Davide Petillo, Cristian Pratesi
 */
public class GUI extends JFrame implements ActionListener {

    public static String LOGO_MENU          =   "pnlMenu";
    public static String LOGO_ADD_PARTITA   =   "pnlAddPartite";
    public static String LOGO_CLASSIFICA    =   "pnlClassifica";
    public static String LOGO_SQUADRA       =   "pnlSquadra";
    public static String LOGO_PARTITE       =   "pnlPartite";
    public static String LOGO_MODIFICA      =   "pnlModifica";

    private Campionato campionato;

    private Container   container;
    private BasePanel   actualPanel;
    private CardLayout  cardLayout;

    private HashMap componentMap = new HashMap<String, BasePanel>();

    private MenuPanel               pnlMenu;
    private AggiungiPartitaPanel    pnlAddPartita;
    private ClassificaPanel         pnlClassifica;
    private SquadraPanel            pnlSquadra;
    private PartitePanel            pnlPartite;
    private ModificaPanel           pnlModifica;

    
    JMenuBar mb = new JMenuBar();
    JMenu menuGestione = new JMenu("Operazioni", true);
    
    JMenuItem mCaricaFile   =   new JMenuItem("Carica partite");
    JMenuItem mSquadra      =   new JMenuItem("Squadre");
    JMenuItem mSalva        =   new JMenuItem("Salva");
    JMenuItem mSelFile      =   new JMenuItem("Scegli file di salvataggio");
    JMenuItem mAzzera       =   new JMenuItem("Azzera dati, partite e squadre");

    
    
    public GUI(Campionato c) throws PartitaEsistenteException, IOException {
        setSize(1000, 800);
        setLocation(100, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(Color.yellow);
        //pack();
        campionato = c;

        boolean scelta = question("Vuoi inserire adesso il file da cui caricare e su cui salvare le partite?");

        if (scelta) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                campionato.setFile(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    System.out.println("aa");
                    campionato.caricaParite();
                } catch (PartitaEsistenteException e) {
                    JOptionPane.showMessageDialog(this, "OPS! Alcune partite si sovrappongono!", "Errore!", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "File selezionato non valido.", "Errore!", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            campionato.setFile("");
        }

        setup();

        setVisible(true);

    }
    
    /**
     * Metodo per creare le varie istanze dei pannelli utilizzati, creare il LayoutManager,
     * impostare le varie impostazioni e mostrare a video il JPanel del Menu.
     */
    private void setup() {
        pnlMenu = new MenuPanel(this, campionato);
        pnlAddPartita   =   new AggiungiPartitaPanel(campionato, this);
        pnlClassifica   =   new ClassificaPanel(campionato, this);
        pnlSquadra      =   new SquadraPanel(campionato, this);
        pnlPartite      =   new PartitePanel(campionato, this);
        pnlModifica     =   new ModificaPanel(campionato, this);

        setResizable(false);   //Altrimenti problemi con sfondo
        
        container = getContentPane();
        cardLayout = new CardLayout(10, 10);
        container.setLayout(cardLayout);
     
        container.add(LOGO_MENU, pnlMenu);
        container.add(LOGO_ADD_PARTITA, pnlAddPartita);
        container.add(LOGO_CLASSIFICA, pnlClassifica);
        container.add(LOGO_SQUADRA, pnlSquadra);
        container.add(LOGO_PARTITE, pnlPartite);
        container.add(LOGO_MODIFICA, pnlModifica);

        componentMap.put(LOGO_MENU, pnlMenu);
        componentMap.put(LOGO_ADD_PARTITA, pnlAddPartita);
        componentMap.put(LOGO_CLASSIFICA, pnlClassifica);
        componentMap.put(LOGO_SQUADRA, pnlSquadra);
        componentMap.put(LOGO_PARTITE, pnlPartite);
        componentMap.put(LOGO_MODIFICA, pnlModifica);

        
        mb.add(menuGestione);

        menuGestione.add(mCaricaFile);
        menuGestione.add(mSquadra);
        menuGestione.add(mSalva);
        menuGestione.add(mSelFile);
        menuGestione.add(mAzzera);

        mCaricaFile.addActionListener(this);
        mSquadra.addActionListener(this);
        mSalva.addActionListener(this);
        mSelFile.addActionListener(this);
        mAzzera.addActionListener(this);

        setJMenuBar(mb);

        
        actualPanel = pnlMenu;
        cardLayout.show(container, LOGO_MENU);

    }

    /**
     * Metodo che aggiorna il pannello visualizzato.
     * 
     * @param p il logo identificativo del pannello.
     */
    public void aggiornaPanel(String p) {
        
        if ((actualPanel instanceof PartitePanel || actualPanel instanceof ModificaPanel) && componentMap.get(p) instanceof PartitePanel) {
            actualPanel = (BasePanel) componentMap.get(p);
            ((PartitePanel) actualPanel).update();
        } else {
            actualPanel = (BasePanel) componentMap.get(p);
            actualPanel.reset();
        }
        //pack();
        
        cardLayout.show(container, p);
    }
    
    /**
     * Metodo che aggiorna il pannello visualizzato, adibito al JPanel SquadraPanel.
     * 
     * @param squadra la squadra di cui si deve visualizzare le informazioni.
     * @param precedente il pannello a cui fare ritorno se si preme il pulsante indietro.
     */
    public void aggiornaPanelSquadra(Squadra squadra, String precedente) {
        pnlSquadra.update(squadra, precedente);
        actualPanel = pnlSquadra;
        
        //pack();
        
        cardLayout.show(container, LOGO_SQUADRA);
    }
    
    /**
     * Metodo che aggiorna il pannello visualizzato, adibito al JPanel ModificaPanel.
     * 
     * @param p la partita da modificare.
     */
    public void aggiornaPanelModifica(Partita p) {
        pnlModifica.update(p);
        actualPanel = pnlModifica;
        
        //pack();
        
        cardLayout.show(container, LOGO_MODIFICA);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == mCaricaFile) {
            String pathToFile = selFile();

            if (pathToFile == null) {
                errore("File non selezionato!");
                return;
            }

            boolean scelta = question("<html>Vuoi caricare questo file?<br/>" + pathToFile + "</html>");

            if (!scelta) {
                return;
            }

            if (campionato.getFile().equals(pathToFile)) {
                errore("Non puoi selezionare lo stesso file!");
                return;
            }

            campionato.setFile(pathToFile);
            try {
                campionato.caricaParite();
            } catch (PartitaEsistenteException ex) {
                scelta = question("<html>Delle partite si sovrappongono con quelle già salvate!<br/>Vuoi aggiungerle lo stesso escludendo quelle che si sovrappongono?</html>");

                try {
                    campionato.caricaParite(scelta);
                } catch (PartitaEsistenteException | IOException ex1) {
                    errore("OPS! Qualcosa è andato storto.");
                }

            } catch (IOException ex) {
                errore("OPS! Qualcosa è andato storto.");
            }

        }

        if (ae.getSource() == mSalva) {
            if (campionato.getFile().equals("")) {
                String pathToFile = selFile();

                if (pathToFile == null) {
                    return;
                }

                boolean scelta = question("<html>Vuoi salvare le partite su questo file?<br/>" + pathToFile + "</html>");
                if (!scelta) {
                    return;
                }

                campionato.setFile(pathToFile);

            }
            try {
                campionato.salva();
                info("Partite salvate!");
            } catch (IOException ex) {
                errore("Il salvataggio è fallito!");
            }

            return;
        }

        if (ae.getSource() == mSelFile) {
            String pathToFile = selFile();

            if (pathToFile == null) {
                return;
            }

            boolean scelta = question("<html>Vuoi selezionare questo file come file di salvataggio?<br/>" + pathToFile + "</html>");
            if (!scelta) {
                return;
            }

            campionato.setFile(pathToFile);
        }

        if (ae.getSource() == mAzzera) {
            campionato.azzera();
        }
        
        if(ae.getSource() == mAzzera || ae.getSource() == mCaricaFile ){
            aggiornaPanel(LOGO_MENU);
        }
    }
    
    /**
     * Metodo che permette di selezionare un file.
     * @return il path al file
     */
    private String selFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }

        return null;
    }

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
