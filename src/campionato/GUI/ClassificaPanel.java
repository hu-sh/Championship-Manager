package campionato.GUI;

import campionato.Campionato;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import campionato.Squadra;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


/**
 * Classe adibita alla creazione del JPanel per la visualizzazione della classifica.
 * 
 * @author Davide Petillo
 */
public class ClassificaPanel extends BasePanel implements ActionListener, MouseListener{ 
    
    JButton btnTorna = new JButton(new ImageIcon("src/immagini/back.png"));
    
    JLabel lblClassifica = new JLabel("CLASSIFICA");
    JTable tblClassifica;
    
    JPanel centro;
    JScrollPane scrollPane;
    
    
    ClassificaPanel(Campionato campionato, GUI g) {
        this.g = g;
        this.c = campionato;
        
        size = new Dimension(1500, 800);

        setLayout(new BorderLayout());
        
        
        ////////ALTO//////
        lblClassifica.setHorizontalAlignment(JLabel.CENTER);
        btnTorna.setHorizontalAlignment(JButton.CENTER);
        btnTorna.setSize(1, 1);
        
        JPanel altoSx = new JPanel(new GridLayout(1, 5));
        altoSx.add(btnTorna);
        altoSx.add(new JLabel(""));
        altoSx.add(new JLabel(""));
        altoSx.add(new JLabel(""));
        altoSx.add(new JLabel(""));
        
        JPanel alto = new JPanel(new GridLayout(1, 3));
        
        alto.add(altoSx);
        alto.add(lblClassifica);
        alto.add(new JLabel(""));
        
        ////////CENTRO////////
        centro = new JPanel(new GridLayout(1, 1));
        reset(); 
        
        
        /////////////////////////////////////////////////////
        
        //add(basso, BorderLayout.SOUTH);
        add(alto, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        
        addListener();
    }
    
    void addListener(){
        btnTorna.addActionListener(this);
    }

    @Override
    void reset() {
        try{
            centro.remove(scrollPane);
        } catch (Exception e) {}
        
        ArrayList<Squadra> classifica = c.getClassifica();
        String[] colonne = new String[]{"Nome",
                                        "Differenza reti",
                                        "Partite giocate",
                                        "Vinte",
                                        "Pareggiate",
                                        "Perse",
                                        "Punteggio"
                                        };
        
        Object[][] data = new Object[classifica.size()][];
        
        for(int i = 0; i<classifica.size(); i++){
            Squadra s = classifica.get(i);
            data[i] = new Object[]{ s.getNome(),
                                    s.getDiffReti(),
                                    s.getPGiocate(),
                                    s.getpVinte(),
                                    s.getpPareggiate(),
                                    s.getpPerse(),
                                    s.getPunti()
                                    };
            System.out.println(classifica.get(i).getNome());
        }
        
        tblClassifica = new JTable(data, colonne){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
                };
        
        tblClassifica.getColumnModel().getColumn(0).setPreferredWidth(150);
        tblClassifica.getColumnModel().getColumn(colonne.length-1).setPreferredWidth(120);
        
        
        DefaultTableCellRenderer centreCell = new DefaultTableCellRenderer();
        centreCell.setHorizontalAlignment( JLabel.CENTER );
        for(int i=0; i<colonne.length; i++){
            tblClassifica.getColumnModel().getColumn(i).setResizable(false);
            tblClassifica.getColumnModel().getColumn(i).setCellRenderer(centreCell);
        }
        
        tblClassifica.setVisible(true);
        tblClassifica.setColumnSelectionAllowed(false);
        tblClassifica.addMouseListener(this);               //LISTENER
        tblClassifica.setFillsViewportHeight(true);
        tblClassifica.getTableHeader().setReorderingAllowed(false);
        
        scrollPane = new JScrollPane(tblClassifica);
        
        centro.add(scrollPane);
        
        setPreferredSize(size);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == btnTorna){
            g.aggiornaPanel(GUI.LOGO_MENU);
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if(me.getSource() == tblClassifica){
            if (me.getClickCount() == 2) {
                int index = tblClassifica.rowAtPoint(me.getPoint());

                String squadra = (String) tblClassifica.getValueAt(index, 0);

                g.aggiornaPanelSquadra(c.cerca(squadra), GUI.LOGO_CLASSIFICA);
            
            }
            
            return;
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
