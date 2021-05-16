package campionato;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Classe che si occupa di gestire il campionato.
 * 
 * @author Davide Petillo
 */
public class Campionato {
    private ArrayList<Squadra> classifica;
    private ArrayList<Partita> partite;
    private GestoreFile gestore;
    
    /**
     *
     */
    public Campionato(){
        classifica = new ArrayList<>();
        partite = new ArrayList<>();
        gestore = new GestoreFile("");
    }
    
    /**
     * Metodo che sfrutta GestoreFile per caricare le partite da file
     * 
     * @param ifExistExclude parametro che permette di scegliere se escludere le partite esistenti, invece di lanciare l'eccezione
     * @throws PartitaEsistenteException eccezione lanciata se viene trovata una partita esistente
     * @throws IOException 
     */
    public void caricaParite(boolean ifExistExclude) throws PartitaEsistenteException, IOException{
        ArrayList<Partita> partite_t = gestore.caricaPartite();
        for(Partita p : partite_t){
            addPartita(p, ifExistExclude);
        }
        update();
    }
    /**
     * Metodo che sfrutta GestoreFile per caricare le partite da file.
     * 
     * @throws PartitaEsistenteException eccezione lanciata se viene trovata una partita esistente
     * @throws IOException 
     */
    public void caricaParite() throws PartitaEsistenteException, IOException{
        caricaParite(false);
    }
    
    /**
     * Metodo usato per aggiungere una partita.
     * 
     * @param p la partita da aggiungere
     * @param ifExistExclude parametro che permette di scegliere se escludere le partite esistenti, invece di lanciare l'eccezione
     * @throws PartitaEsistenteException eccezione lanciata se viene trovata una partita esistente
     */
    public void addPartita(Partita p, boolean ifExistExclude) throws PartitaEsistenteException{
        if(partitaEsiste(p)) {
            if(ifExistExclude){
                return;
            }
            throw new PartitaEsistenteException("La partita esiste già in questa giornata.");
        }
        
        String casa_s   = p.getCasa().substring(0, 1).toUpperCase() + p.getCasa().substring(1);
        String ospite_s = p.getOspite().substring(0, 1).toUpperCase() + p.getOspite().substring(1);
        
        System.out.println(casa_s);
        
        p.setCasa(casa_s);
        p.setOspite(ospite_s);
        
        partite.add(p);
        
        Squadra casa = cerca(p.getCasa());
        Squadra ospite = cerca(p.getOspite());
        
        
        if(casa != null){
            casa.addPartita(p.getGolCasa(), p.getGolOspite());
        } else {
            Squadra s = new Squadra(casa_s);
            classifica.add(s);
            s.addPartita(p.getGolCasa(), p.getGolOspite());
        }
        
        if(ospite != null){
            ospite.addPartita(p.getGolOspite(), p.getGolCasa());
        } else {
            Squadra s = new Squadra(ospite_s);
            classifica.add(s);
            s.addPartita(p.getGolOspite(), p.getGolCasa());
        }
        
        update();
    }
    /**
     * Metodo che permette di aggiungere una partita.
     * 
     * @param p la partita da aggiungere
     * @throws PartitaEsistenteException eccezione lanciata se viene trovata una partita esistente 
     */
    public void addPartita(Partita p) throws PartitaEsistenteException{
        addPartita(p, false);
    }
    
    /**
     * Metodo chiamato dopo una modifica alle partite, si occupa di ordinare la classifica e le partite nel modo corretto.
     */
    private void update() {
       Collections.sort(classifica, new Comparator<Squadra>(){
                                        @Override
                                        public int compare(Squadra t, Squadra t1) {
                                            if(t.getPunti() < t1.getPunti()){  
                                                return 1;
                                            }
                                            if(t.getPunti() == t1.getPunti()){
                                                if(t.getDiffReti() < t1.getDiffReti()){
                                                     return  1;
                                                }
                                            }
                                            return -1;
                                        }
                                    });
       
       Collections.sort(partite, new Comparator<Partita>() {
                                    @Override
                                    public int compare(Partita t, Partita t1) {
                                        if(t.getGiornata() < t1.getGiornata()){
                                            return -1;
                                        }
                                        return 1;
                                    }
                                });
    }
    
    /**
     * Metodo per impostare il nome del file su cui salvare le partite, o da cui caricarle.
     * 
     * @param path il path al file
     */
    public void setFile(String path){
        gestore.setNome(path);
    }
    /**
     * Metodo getter per ottenere il path del file impostato.
     * @return il path al file
     */
    public String getFile(){
        return gestore.getNome();
    }
    
    /**
     * Metodo che cerca una Squadra a partire dal nome, in classifica.
     * @param squadra il nome della Squadra
     * @return l'oggetto Squadra trovato, o null
     */
    public Squadra cerca(String squadra){
        for(Squadra s : classifica){
            if(s.getNome().equalsIgnoreCase(squadra))
                return s;
        }
        
        return null;
    }
    /**
     * Metodo che cerca una Partita nella lista di partite.
     * 
     * @param casa nome della squadra di casa
     * @param ospite nome della squadra ospite
     * @param golCasa gol fatti dalla squadra di casa
     * @param golOspite gol fatti dalla squadra ospite
     * @param giornata la giornata
     * @return la Partita trovata, o null
     */
    public Partita cerca(String casa, String ospite, int golCasa, int golOspite, int giornata){
        for(Partita p : partite){
            if( p.getCasa().equalsIgnoreCase(casa)      &&
                p.getOspite().equalsIgnoreCase(ospite)  &&
                p.getGolCasa()  ==  golCasa             &&
                p.getGolOspite()==  golOspite           &&
                p.getGiornata() ==  giornata                 )
                {
                    return p;
                }
        }
        
        return null;
    }
    
    /**
     * Metodo che rimuove una partita dal campionato (chiamando poi il metodo update())
     * @param partita la partita da rimuovere
     */
    public void remove(Partita partita){
        Squadra casa = cerca(partita.getCasa());
        Squadra ospite = cerca(partita.getOspite());
        
        casa.remPartita(partita.getGolCasa(), partita.getGolOspite());
        ospite.remPartita(partita.getGolOspite(), partita.getGolCasa());
        
        if(casa.isAzzerato()){
            classifica.remove(casa);
        }
        
        if(ospite.isAzzerato()){
            classifica.remove(ospite);
        }
        
        partite.remove(partita);
    }
    
    /**
     * Metodo per modificare una partita.
     * 
     * @param partita la Partita da modificare
     * @param casa nome della squadra di casa
     * @param ospite nome della squadra ospite
     * @param golCasa gol fatti dalla squadra di casa
     * @param golOspite gol fatti dalla squadra ospite
     * @param giornata la giornata
     * @throws PartitaEsistenteException metodo lanciato se la modifica effettuata coincide con una Partita esistente
     * @throws PartitaNonTrovataException metodo lanciato se la partita che si vuole modificare non è stata trovata tra le partite salvate
     */
    public void modificaPartita(Partita partita, String casa, String ospite, int golCasa, int golOspite, int giornata) throws PartitaEsistenteException, PartitaNonTrovataException{
        int index = 0;
        boolean trovato = false;
        for(; index<partite.size() && !trovato; index++){
            if(partita == partite.get(index)) trovato =  true;
        }
        index--;
        
        if(trovato){
            System.out.println("campionato: "+partite.get(index));
            remove(partite.get(index));
            addPartita(new Partita(giornata, casa, ospite, golCasa, golOspite));
        } else {
            throw new PartitaNonTrovataException("La partita da modificare non è stata trovata.");
        }
        
        update();
        
    }
    
    /**
     * Metodo che salva le partite sul file.
     * 
     * @throws IOException 
     */
    public void salva() throws IOException{
        gestore.salvaPartite(partite);
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Squadra> getClassifica() {
        return classifica;
    }

    public ArrayList<Partita> getPartite() {
        return partite;
    }
    
    /**
     * Metodo che trova l'ultima giornata tra le partite salvate.
     * @return la giornata più grande trovata tra le partite
     */
    public int getLastGiornata(){
        try{
            return Collections.max(partite, new Comparator() {
                @Override
                public int compare(Object o, Object o1) {
                   Partita t = (Partita) o;
                   Partita t1 = (Partita) o1;
                   if(t.getGiornata() < t1.getGiornata())
                       return -1;
                   else
                       return 1;
                }
            }).getGiornata();
        } catch (Exception e){ return -1;}
    }
    
    /**
     * Metodo che verifica se una Partite esiste tra quelle dell'ArrayList partite.
     * 
     * @param p la partita di cui verificare l'esistenza
     * @return true se la partita p viene trovata, false altrimenti
     */
    private boolean partitaEsiste(Partita p) {
        for(Partita n : partite){
            if(n.getGiornata()== p.getGiornata()){
                if(p.getCasa().equalsIgnoreCase(n.getCasa()) && p.getOspite().equalsIgnoreCase(n.getOspite()))
                    return true;
            }
        }
        return false;
    }
    
    /**
     * Metodo che azzera completamente i dati del campionato.
     */
    public void azzera() {
        partite.clear();
        classifica.clear();
        gestore.setNome("");
    }
    
    
}
