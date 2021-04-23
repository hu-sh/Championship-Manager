package campionato.GUI;


import campionato.Campionato;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * Classe per la creazione del JPanel per il menu principale
 * 
 * @author Davide Petillo, Cristian Pratesi
 */
public class MenuPanel extends BasePanel implements ActionListener{
    
    JButton btnAddPartita = new JButton("Aggiungi partita");
    JButton btnClassifica = new JButton("Classifica");
    JButton btnPartite = new JButton("Partite");
    
    ImageIcon sfondo;
    
    
    
    public MenuPanel(GUI g, Campionato c){
        this.g = g;
        this.c = c;
        
        size = new Dimension(400, 400); 
        setPreferredSize(size);
        
        setLayout(new GridLayout(20, 6));
        
        sfondo = new ImageIcon("src/immagini/sfondo.jpg");
        //setBackground(Color.red);
        
        for(int i=0; i<(7*6)+1; i++){
            add(new JLabel(""));
        }
        add(btnClassifica, BorderLayout.EAST);
        for(int i=0; i<11; i++){
            add(new JLabel(""));
        }
        add(btnPartite, BorderLayout.EAST);
        for(int i=0; i<11; i++){
            add(new JLabel(""));
        }
        add(btnAddPartita, BorderLayout.EAST);
        for(int i=0; i<5; i++){
            add(new JLabel(""));
        }
        for(int i=0; i<8*6-1; i++){
            add(new JLabel(""));
        }
        
        addListener();
    }
    
    void addListener(){
        btnAddPartita.addActionListener(this);
        btnClassifica.addActionListener(this);
        btnPartite.addActionListener(this);
    }
    
    @Override
    void reset() {
        setPreferredSize(size);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAddPartita){
            g.aggiornaPanel(GUI.LOGO_ADD_PARTITA);
            return;
        }
        if (e.getSource() == btnClassifica){
            g.aggiornaPanel(GUI.LOGO_CLASSIFICA);
            return;
        }
        if (e.getSource() == btnPartite){
            if (c.getPartite().isEmpty()){
                errore("Non ci sono partite caricate.");
                return;
            } 
            g.aggiornaPanel(GUI.LOGO_PARTITE);
        }
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        
        g.drawImage(sfondo.getImage(), 0, 0, this);
        
    }
}
