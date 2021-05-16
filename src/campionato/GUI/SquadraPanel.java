package campionato.GUI;

import campionato.Campionato;
import campionato.Squadra;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe per mostrare a video le informazioni relative a una squadra.
 * 
 * @author Davide Petillo
 */
public class SquadraPanel extends BasePanel implements ActionListener{
    Squadra s;
    String precedente;
    
    JButton btnTorna = new JButton(new ImageIcon("src/immagini/back.png"));
    JLabel lblSquadra;
    
    public SquadraPanel(Campionato c, GUI g){
        this.c = c;
        this.g = g;
        this.s = null;
        this.precedente = null;
        size = new Dimension(700, 600);
    }
    
    private void setup(){
        if(precedente == null || s == null) return;
        
        try{
            removeAll();
        } catch (Exception e) {}
        
        
        lblSquadra = new JLabel(s.getNome());
        
        ////////ALTO//////
        lblSquadra.setHorizontalAlignment(JLabel.CENTER);
        btnTorna.setHorizontalAlignment(JButton.CENTER);
        btnTorna.setSize(1, 1);
        
        JPanel alto = new JPanel(new GridLayout(1, 10));
        
        alto.add(btnTorna);
        alto.add(new JLabel(""));
        alto.add(new JLabel(""));
        alto.add(new JLabel(""));
        alto.add(lblSquadra);
        alto.add(new JLabel(""));
        alto.add(new JLabel(""));
        alto.add(new JLabel(""));
        alto.add(new JLabel(""));
    
    
        /////////////////////////////////////////////////////
        
        //add(basso, BorderLayout.SOUTH);
        add(alto, BorderLayout.NORTH);
        //add(centro, BorderLayout.CENTER);
        
        addListener();
    }
    
    void addListener() {
        btnTorna.addActionListener(this);
    }
    
    public void update(Squadra s, String precedente){
        this.s = s;
        this.precedente = precedente;
        
        setup();
    }
    
    @Override
    void reset() {
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == btnTorna){
            g.aggiornaPanel(precedente);
        }
    }
    
}
