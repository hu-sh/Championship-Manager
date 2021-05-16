package campionato.GUI;

import campionato.Campionato;
import campionato.Partita;
import campionato.PartitaEsistenteException;
import campionato.PartitaNonTrovataException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Classe utilizzata per mostrare il pannello che permette di modificare una partita.
 * 
 * @author Davide Petillo
 */
public class ModificaPanel extends BasePanel implements ActionListener, KeyListener{
    
    JLabel lblSquadra1 = new JLabel("");
    JLabel lblSquadra2 = new JLabel("");
    JLabel lblGol1 = new JLabel("");
    JLabel lblGol2 = new JLabel("");
    JLabel lblTrattino = new JLabel("-");
    JLabel lblGiornata = new JLabel("GIORNATA");
    
    JTextField txtSquadra1 = new JTextField(20);
    JTextField txtSquadra2 = new JTextField(20);
    JTextField txtGol1 = new JTextField(2);
    JTextField txtGol2 = new JTextField(2);
    
    JButton btnTorna = new JButton(new ImageIcon("src/immagini/back.png"));
    JButton btnMSalva = new JButton("Modifica e Salva");
    JButton btnModifica = new JButton("Modifica");
    JButton btnElimina = new JButton("Elimina");
    JButton btnESalva = new JButton("Elimina e salva");
    
    JComboBox cmbGiornate;
    
    Partita p;
    
    public ModificaPanel(Campionato c, GUI g){
        this.c = c;
        this.g = g;
        this.p = null;
        size = new Dimension(1500, 800);
        
        setLayout(new BorderLayout());
        
        
        
        ////////ALTO//////
        cmbGiornate = new JComboBox(getGiornateDisponibili());
        lblGiornata.setHorizontalAlignment(JLabel.CENTER);
        btnTorna.setHorizontalAlignment(JButton.CENTER);
        btnTorna.setSize(1, 1);
        
        JPanel altoSX = new JPanel(new GridLayout(1, 4));
        altoSX.add(btnTorna);
        altoSX.add(new JLabel("")); altoSX.add(new JLabel("")); altoSX.add(new JLabel(""));
        
        JPanel alto = new JPanel(new GridLayout(1, 5));
        
        alto.add(altoSX);
        alto.add(new JLabel(""));
        alto.add(lblGiornata);
        alto.add(cmbGiornate);
        alto.add(new JLabel(""));
        
        
        
        ////////CENTRO////////
        
        lblGol1.setHorizontalAlignment(JLabel.CENTER);
        lblGol2.setHorizontalAlignment(JLabel.CENTER);
        lblTrattino.setHorizontalAlignment(JLabel.CENTER);
        lblSquadra1.setHorizontalAlignment(JLabel.LEFT);
        lblSquadra2.setHorizontalAlignment(JLabel.RIGHT);
        
        txtGol1.setHorizontalAlignment(JTextField.CENTER);
        txtGol1.setSize(new Dimension(2, 2));
        txtGol2.setHorizontalAlignment(JTextField.CENTER);
        txtGol2.setSize(new Dimension(2, 2));
        
        txtSquadra1.setHorizontalAlignment(JTextField.CENTER);
        txtSquadra2.setHorizontalAlignment(JTextField.CENTER);
        
        
        JPanel centroUp = new JPanel(new GridLayout(1, 11));
        centroUp.add(new JLabel(""));
        centroUp.add(lblSquadra1);
        centroUp.add(new JLabel("")); centroUp.add(new JLabel("")); 
        centroUp.add(lblGol1); centroUp.add(lblTrattino); centroUp.add(lblGol2);
        centroUp.add(new JLabel("")); centroUp.add(new JLabel("")); 
        centroUp.add(lblSquadra2);
        centroUp.add(new JLabel(""));
        
        JPanel centroDown = new JPanel(new GridLayout(1, 4));
        centroDown.add(txtSquadra1);
        centroDown.add(txtGol1);
        centroDown.add(txtGol2);
        centroDown.add(txtSquadra2);
        
        
        JPanel centroExt = new JPanel(new GridLayout(3, 1));
        centroExt.add(centroUp);
        centroExt.add(centroDown);
        centroExt.add(new JPanel());
        
        
        ///////BASSO/////////
        btnMSalva.setHorizontalAlignment(JButton.CENTER);
        btnModifica.setHorizontalAlignment(JButton.CENTER);
        
        JPanel basso = new JPanel(new GridLayout(1, 7));
        
        
        basso.add(btnElimina);
        basso.add(btnESalva);
        basso.add(new JLabel("")); basso.add(new JLabel(""));
        basso.add(btnMSalva);
        basso.add(btnModifica);
        
        /////////////////////////////////////////////////////
        
        add(basso, BorderLayout.SOUTH);
        add(alto, BorderLayout.NORTH);
        add(centroExt, BorderLayout.CENTER);

        
        addListener();
    }
    
    /**
     * Metodo che prende le giornate disponibili per il selezionamento
     * per l'aggiunta di una partite.
     * 
     * @return Array di stringhe contenente le giornate disponibili
     */
    private String[] getGiornateDisponibili(){
        int mag;
        
        if(c.getClassifica().size() == 0){
            mag = 1;
        } else {
            mag = Collections.max(c.getPartite(), new Comparator<Partita>(){
                                                        @Override
                                                        public int compare(Partita t, Partita t1) {
                                                            if (t.getGiornata() > t1.getGiornata()){
                                                                return 1;
                                                            }
                                                            return -1;
                                                        }

                                                    }).getGiornata();
        }
        String[] ret = new String[mag+1];
        
        for(int i = 1; i<=mag+1; i++){
            ret[i-1] = Integer.toString(i);
        }       
        return ret;
    }
    
    void addListener(){
        txtGol1.addKeyListener(this);
        txtGol2.addKeyListener(this);
        
        btnMSalva.addActionListener(this);
        btnModifica.addActionListener(this);
        btnTorna.addActionListener(this);
        btnElimina.addActionListener(this);
        btnESalva.addActionListener(this);
    }
    
    
    /**
     * Metodo che aggiorna il pannello
     * 
     * @param p la partita da modificare
     */
    public void update(Partita p){
        txtSquadra1.setText("");
        txtSquadra2.setText("");
        txtGol1.setText("");
        txtGol2.setText("");
        
        this.p = p;
        
        cmbGiornate.setSelectedIndex(p.getGiornata()-1);
        
        lblSquadra1.setText(p.getCasa());
        lblSquadra2.setText(p.getOspite());
        lblGol1.setText(Integer.toString(p.getGolCasa()));
        lblGol2.setText(Integer.toString(p.getGolOspite()));
        
    }
    
    @Override
    void reset() {}
    
    @Override
    public void keyTyped(KeyEvent e) {
        char car = e.getKeyChar();
        if (car < '0' || car > '9') {
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {}
    @Override
    public void keyReleased(KeyEvent ke) {}
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnMSalva || ae.getSource() == btnModifica){
            try{
                int gg = Integer.parseInt((String)cmbGiornate.getSelectedItem());
                System.out.println(gg);

                int gol1 = Integer.parseInt((String)txtGol1.getText());
                int gol2 = Integer.parseInt((String)txtGol2.getText());

                String squadra1 = (String)txtSquadra1.getText();
                String squadra2 = (String)txtSquadra2.getText();
                
                if(squadra1.equals(squadra2)) {
                    errore("Una squadra non può giocare contro se stessa.");
                    return;
                }
                
                if(squadra1.length() <= 0 && squadra2.length() <= 0){
                    errore("OPS! Qualcosa è andato storto!\nAssicurati di inserire tutti i dati correttamente.");
                    return;
                }
                
                System.out.println(p);
                c.modificaPartita(p, squadra1, squadra2, gol1, gol2, gg);
                
                if(ae.getSource() == btnMSalva){
                    salva("modificata");
                } else {
                    info("Partita modificata!");
                }
                
            } catch (PartitaEsistenteException e){
                errore("Non puoi modificare la partita in questo modo! Ne esiste già una simile!");
            } catch (PartitaNonTrovataException ex) {
                errore("Si è verificato un errore, per qualche motivo la partita da modificare non è stata trovata!");
                g.aggiornaPanel(GUI.LOGO_PARTITE);
            }
            
            return;
        }
        
        if(ae.getSource() == btnElimina || ae.getSource() == btnESalva){
            try{
                if (!question("Sei sicuro di voler eliminare la partita?")) return;
                c.remove(p);

                if(ae.getSource() == btnESalva){
                    salva("rimossa");
                } else {
                    info("Partita rimossa!");
                }

                g.aggiornaPanel(GUI.LOGO_PARTITE);
            } catch (Exception e) {
                System.out.println(e);
                errore("OPS! Qualcosa è andato storto!");
            }
            return;
        }
        if(ae.getSource() == btnTorna){
            g.aggiornaPanel(GUI.LOGO_PARTITE);
            return;
        }
    }
    
    /**
     * Metodo che salva i dati su file.
     * 
     * @param msg messaggio, se i dati sono eliminati, creati o modificati
     */
    private void salva(String msg){
        if (c.getFile().equals("")){
            
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            int result = fileChooser.showOpenDialog(this);
            
            if(result == JFileChooser.APPROVE_OPTION){
                c.setFile(fileChooser.getSelectedFile().getAbsolutePath());
                System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
                
                try {
                    c.caricaParite();
                    c.salva();
                    info("Partita " + msg + " e salvata!");
                } catch (Exception e){
                    errore("OPS! Alcune partite si sovrappongono o forse non è stato possibile caricare partite da questo file!");
                    boolean salva = question("Vuoi comunque salvare le partite su questo file?");
                    if(salva) {
                        try {
                            c.salva();
                            info("Partita " + msg + " e salvata!");
                        } catch (IOException ex) {
                            errore("OPS! Qualcosa è andato storto!");
                        }
                        
                    }
                }
            }
        } else {
            try {
                c.salva();
                info("Partita " + msg + " e salvata!");
            } catch (IOException ex) {
                errore("OPS! Qualcosa è andato storto!");
            }
        }
    }
    
}
