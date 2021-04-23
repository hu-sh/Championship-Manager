package campionato.GUI;

import campionato.Campionato;
import campionato.Partita;
import campionato.PartitaEsistenteException;
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
 * Classe che si occupa di creare un JPanel per l'aggiunta di una partita.
 * @author hush
 */
public class AggiungiPartitaPanel extends BasePanel implements KeyListener, ActionListener{
    
    JLabel lblSquadra1 = new JLabel("CASA");
    JLabel lblSquadra2 = new JLabel("OSPITE");
    JLabel lblGol = new JLabel("GOL");
    JLabel lblGiornata = new JLabel("GIORNATA");
    
    JComboBox cmbGiornate;
    
    JTextField txtSquadra1 = new JTextField(20);
    JTextField txtSquadra2 = new JTextField(20);
    JTextField txtGol1 = new JTextField(2);
    JTextField txtGol2 = new JTextField(2);
    
    JButton btnTorna = new JButton(new ImageIcon("src/immagini/back.png"));
    JButton btnASalva = new JButton("Aggiungi e Salva");
    JButton btnAggiungi = new JButton("Aggiungi");
    
    
    public AggiungiPartitaPanel(Campionato c, GUI g){
        this.c = c;
        this.g = g;
        
        setLayout(new BorderLayout());
        
        size = new Dimension(500, 600);
        
        ////////ALTO//////
        cmbGiornate = new JComboBox(getGiornateDisponibili());
        cmbGiornate.setSelectedIndex(cmbGiornate.getItemCount()-2);
        lblGiornata.setHorizontalAlignment(JLabel.CENTER);
        btnTorna.setHorizontalAlignment(JButton.CENTER);
        btnTorna.setSize(1, 1);
        
        JPanel altoSx = new JPanel(new GridLayout(1,4));
        altoSx.add(btnTorna);
        altoSx.add(new JLabel(""));  altoSx.add(new JLabel("")); altoSx.add(new JLabel(""));
        
        JPanel alto = new JPanel(new GridLayout(1, 5));
        
        alto.add(altoSx);
        alto.add(new JLabel(""));
        alto.add(lblGiornata);
        alto.add(cmbGiornate);
        alto.add(new JLabel(""));
        
        
        ////////CENTRO////////
        
        lblGol.setHorizontalAlignment(JLabel.CENTER);
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
        centroUp.add(new JLabel("")); centroUp.add(new JLabel("")); centroUp.add(new JLabel(""));
        centroUp.add(lblGol);
        centroUp.add(new JLabel("")); centroUp.add(new JLabel("")); centroUp.add(new JLabel(""));
        centroUp.add(lblSquadra2);
        centroUp.add(new JLabel(""));
        
        JPanel centroDown = new JPanel(new GridLayout(3, 4));
        centroDown.add(txtSquadra1);
        centroDown.add(txtGol1);
        centroDown.add(txtGol2);
        centroDown.add(txtSquadra2);
        centroDown.add(new JLabel("")); centroDown.add(new JLabel("")); centroDown.add(new JLabel("")); centroDown.add(new JLabel(""));
        centroDown.add(new JLabel("")); centroDown.add(new JLabel("")); centroDown.add(new JLabel("")); centroDown.add(new JLabel(""));
        
        
        JPanel centroExt = new JPanel(new GridLayout(2, 1));
        centroExt.add(centroUp);
        centroExt.add(centroDown);
        
        
        ///////BASSO/////////
        btnASalva.setHorizontalAlignment(JButton.LEFT);
        btnAggiungi.setHorizontalAlignment(JButton.LEFT);
        
        JPanel basso = new JPanel();
        
        basso.add(btnASalva);
        basso.add(btnAggiungi);
        
        /////////////////////////////////////////////////////
        
        add(basso, BorderLayout.SOUTH);
        add(alto, BorderLayout.NORTH);
        add(centroExt, BorderLayout.CENTER);

        
        addListener();
    }
    
    
    void addListener(){
        txtGol1.addKeyListener(this);
        txtGol2.addKeyListener(this);
        
        btnASalva.addActionListener(this);
        btnAggiungi.addActionListener(this);
        btnTorna.addActionListener(this);
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
            mag = 0;
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
    
    @Override
    void reset() {
        txtGol1.setText("");
        txtGol2.setText("");
        txtSquadra1.setText("");
        txtSquadra2.setText("");
        
        cmbGiornate.removeAllItems();
        String[] items = getGiornateDisponibili();
        for(String n : items){
            cmbGiornate.addItem(n);
        }
        if (items.length > 1){
            cmbGiornate.setSelectedIndex(items.length-2);
        } else {
            cmbGiornate.setSelectedIndex(0);
        }
        
        //setPreferredSize(size);
        //etSize(size);
        
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        //System.out.println("keyTyped");
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
        if (ae.getSource() == btnASalva || ae.getSource() == btnAggiungi){
            try{
                int gg = Integer.parseInt((String)cmbGiornate.getSelectedItem());

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
                c.addPartita(new Partita(gg, squadra1, squadra2, gol1, gol2));
                
                if(ae.getSource() == btnASalva){
                    salva("aggiunta");
                } else {
                    info("Parita aggiunta!");
                }
                
            }  catch (PartitaEsistenteException e){
                errore(e.toString());
            }catch (Exception e){
                errore("OPS! Qualcosa è andato storto!\nAssicurati di inserire tutti i dati correttamente.");
            }
            
            reset();
            
            return;
        }
        if(ae.getSource() == btnTorna){
            g.aggiornaPanel(GUI.LOGO_MENU);
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
