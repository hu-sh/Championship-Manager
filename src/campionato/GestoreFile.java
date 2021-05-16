package campionato;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Classe che si occupa di salvare le partite su file e leggerle.
 * 
 * @author Davide Petillo
 */
class GestoreFile {
    private String nome;
    
    /**
     * Costruttore che prende il nome del file su cui operare.
     * 
     * @param nome 
     */
    public GestoreFile(String nome){
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * Metodo che si occupa di leggere le partite da file.
     * 
     * @return un ArrayList di Partita
     * @throws IOException 
     */
    public ArrayList<Partita> caricaPartite() throws IOException{
        FileInputStream inFile = new FileInputStream(nome);
        ObjectInputStream in = new ObjectInputStream(inFile);
        
        ArrayList<Partita> p = new ArrayList<>();
        
        boolean disponibili = true;
        while(disponibili){
            try{
                p.add((Partita)in.readObject());
            } catch(Exception e){
                disponibili = false;
            }
        }
        
        inFile.close();
        
        return p;
    }
    
    /**
     * Metodo che salva le partite su file.
     * 
     * @param p l'ArrayList di Partita che mantiene le partite che devono essere salvate
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void salvaPartite(ArrayList<Partita> p) throws FileNotFoundException, IOException{
        FileOutputStream outFile = new FileOutputStream(nome);
        ObjectOutputStream out = new ObjectOutputStream(outFile);
        
        for(Partita partita : p){
            out.writeObject(partita);
        }
        
        outFile.close();
    }
}
