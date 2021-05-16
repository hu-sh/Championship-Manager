package campionato.GUI;

import campionato.Campionato;
import campionato.Partita;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Classe per mostrare le varie partite, divise per giornate, del campionato
 * 
 * @author Davide Petillo
 */
public class PartitePanel extends BasePanel implements ActionListener, MouseListener{
    
    int giornata;
    
    JButton btnTorna = new JButton(new ImageIcon("src/immagini/back.png"));
    JButton btnPrec = new JButton("<-");
    JButton btnNext = new JButton("->");
    JButton btnModifica = new JButton("Modifica");
    JButton btnUltimaGiornata = new JButton("Vai all'ultima giornata");
    
    private JLabel lblGiornate;
    private JTable tblPartite;
    
    JPanel alto;
    JPanel centro;
    JScrollPane scrollPane;
    
    PartitePanel(Campionato campionato, GUI g) {
        this.g = g;
        this.c = campionato;
        size = new Dimension(1500, 800);
        
        setLayout(new BorderLayout());
        
        
        ////////ALTO//////
        lblGiornate = new JLabel("GIORNATA " + giornata);
        lblGiornate.setHorizontalAlignment(JLabel.CENTER);
        btnTorna.setHorizontalAlignment(JButton.CENTER);
        btnTorna.setSize(1, 1);
        btnUltimaGiornata.setHorizontalAlignment(JButton.CENTER);
        
        JPanel altoSx = new JPanel(new GridLayout(1, 5));
        altoSx.add(btnTorna);
        altoSx.add(new JLabel("")); altoSx.add(new JLabel("")); altoSx.add(new JLabel("")); altoSx.add(new JLabel(""));
        
        JPanel altoDx = new JPanel(new GridLayout(1, 2));
        altoDx.add(new JLabel("")); 
        altoDx.add(btnUltimaGiornata);
        
        
        alto = new JPanel(new GridLayout(1, 3));
        
        alto.add(altoSx);
        alto.add(lblGiornate);
        alto.add(altoDx);
        
        
        ////////CENTRO////////
        centro = new JPanel(new GridLayout(1, 1));
        reset();
        
        ////BASSO////
        JPanel basso = new JPanel(new GridLayout(1, 11));
        basso.add(btnPrec);
        basso.add(new JLabel("")); basso.add(new JLabel("")); basso.add(new JLabel("")); basso.add(new JLabel(""));
        basso.add(btnModifica);
        basso.add(new JLabel("")); basso.add(new JLabel("")); basso.add(new JLabel("")); basso.add(new JLabel(""));
        basso.add(btnNext);
        
        
        /////////////////////////////////////////////////////
        
        add(basso, BorderLayout.SOUTH);
        add(alto, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        
        addListener();
    }
    
    void addListener(){
        btnTorna.addActionListener(this);
        
        btnNext.addActionListener(this);
        btnPrec.addActionListener(this);
        btnModifica.addActionListener(this);
        btnUltimaGiornata.addActionListener(this);
    }

    @Override
    void reset() {
        giornata = c.getLastGiornata();
        
        if (giornata == -1){ return; }
        
        update();
    }
    
    /**
     * Metodo usato per aggiornare il pannello, quando si ritorna da una modifica a una partita
     * o quando si preme le freccette per cambiare giornata.
     */
    public void update(){
        try{
            centro.remove(scrollPane);
        } catch (Exception e) {}
        
        
        
        ((JLabel)alto.getComponent(1)).setText("GIORNATA " + giornata);
        
        ArrayList<Partita> partite = new ArrayList<>();
        ArrayList<Partita> partite_t = c.getPartite();
        for(Partita p : partite_t){
            if(p.getGiornata() == giornata)
                partite.add(p);
        }
        
        String[] colonne = new String[]{"Casa",
                                        "Gol casa",
                                        "Gol ospite",
                                        "Ospite",
                                        };
        
        Object[][] data = new Object[partite.size()][];
        
        for(int i = 0; i<partite.size(); i++){
            Partita s = partite.get(i);
            data[i] = new Object[]{ s.getCasa(),
                                    s.getGolCasa(),
                                    s.getGolOspite(),
                                    s.getOspite()
                                    };
        }
        
        tblPartite = new JTable(data, colonne){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
                };
        
        tblPartite.getColumnModel().getColumn(0).setPreferredWidth(150);
        tblPartite.getColumnModel().getColumn(colonne.length-1).setPreferredWidth(120);
        
        
        DefaultTableCellRenderer centreCell = new DefaultTableCellRenderer();
        centreCell.setHorizontalAlignment( JLabel.CENTER );
        for(int i=0; i<colonne.length; i++){
            tblPartite.getColumnModel().getColumn(i).setResizable(false);
            tblPartite.getColumnModel().getColumn(i).setCellRenderer(centreCell);
        }
        
        tblPartite.setVisible(true);
        tblPartite.addMouseListener(this);              //LISTENER
        tblPartite.setFillsViewportHeight(true);
        tblPartite.getTableHeader().setReorderingAllowed(false);
        
        scrollPane = new JScrollPane(tblPartite);
        
        centro.add(scrollPane);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == btnTorna){
            g.aggiornaPanel(GUI.LOGO_MENU);
            return;
        }
        
        if(ae.getSource() == btnPrec){
            if(giornata>1){
                giornata--;
                update();
                g.aggiornaPanel(GUI.LOGO_PARTITE);
                System.out.println("precedente");
            }
            return;
        }
        if(ae.getSource() == btnNext){
            if(giornata<c.getLastGiornata()){
                giornata++;
                update();
                g.aggiornaPanel(GUI.LOGO_PARTITE);
                System.out.println("successivo");
            }
            return;
        }
        
        if(ae.getSource() == btnModifica){
            try{
                int row = tblPartite.getSelectedRow();
                Partita p = c.cerca((String)    tblPartite.getValueAt(row, 0), 
                                    (String)    tblPartite.getValueAt(row, 3), 
                                    (Integer)   tblPartite.getValueAt(row, 1), 
                                    (Integer)   tblPartite.getValueAt(row, 2), 
                                                giornata);
                g.aggiornaPanelModifica(p);
            } catch (Exception e){}
            return;
        }
        
        if(ae.getSource() == btnUltimaGiornata){
            reset();
            g.aggiornaPanel(GUI.LOGO_PARTITE);
            
            return;
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if(me.getSource() == tblPartite)
        if (me.getClickCount() == 2) {
            int row = tblPartite.rowAtPoint(me.getPoint());
            int column = tblPartite.columnAtPoint(me.getPoint());
            
            System.out.println(column);
                    
            
            if(column == 1 || column == 2) return;
            
            String squadra = (String) tblPartite.getValueAt(row, column);
            
            g.aggiornaPanelSquadra(c.cerca(squadra), GUI.LOGO_PARTITE);
        } 
    }

    @Override
    public void mousePressed(MouseEvent me) {}
    @Override
    public void mouseReleased(MouseEvent me) {}
    @Override
    public void mouseEntered(MouseEvent me) {}
    @Override
    public void mouseExited(MouseEvent me) {}
    
}
