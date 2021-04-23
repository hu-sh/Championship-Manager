package campionato;

/**
 * Classe rappresentativa di una squadra.
 * 
 * @author Davide Petillo, Cristian Pratesi
 */
public class Squadra {
    private String nome;
    private int punti;
    private int pVinte;
    private int pPerse;
    private int pPareggiate;
    private int golFatti;
    private int golSubiti;

    public Squadra(String nome) {
        this.nome = nome;
        this.punti = 0;
        this.pVinte = 0;
        this.pPerse = 0;
        this.pPareggiate = 0;
        this.golFatti = 0;
        this.golSubiti = 0;
    }

    public Squadra(Squadra s) {
        this.nome = s.nome;
        this.punti = s.punti;
        this.pVinte = s.pVinte;
        this.pPerse = s.pPerse;
        this.pPareggiate = s.pPareggiate;
        this.golFatti = s.golFatti;
        this.golSubiti = s.golSubiti;
    }
    
    
    public String getNome() {
        return nome;
    }

    public int getPunti() {
        return punti;
    }

    public int getpVinte() {
        return pVinte;
    }

    public int getpPerse() {
        return pPerse;
    }

    public int getpPareggiate() {
        return pPareggiate;
    }

    public int getGolFatti() {
        return golFatti;
    }

    public int getGolSubiti() {
        return golSubiti;
    }

    public int getPGiocate() {
        return pVinte + pPerse + pPareggiate;
    }
    
    /**
     * Metodo che restituisce la differenza di punteggio fatto e punteggio subito.
     * 
     * @return 
     */
    public int getDiffReti(){
        return golFatti-golSubiti;
    }
    
    /**
     * Metodo che aggiunge una partita, quindi i gol e il corrispettivo punteggio ottenuto.
     * 
     * @param golFatti i gol fatti
     * @param golSubiti i gol subiti
     */
    public void addPartita(int golFatti, int golSubiti) {
        
        this.golFatti+=golFatti;
        this.golSubiti+=golSubiti;
        
        if(golFatti==golSubiti){
            pPareggiate++;
            punti++;
            return;
        }
        
        if(golFatti>golSubiti){
            pVinte++;
            punti+=3;
            return;
        }
        
        if(golFatti<golSubiti){
            pPerse++;
            return;
        }
    }
    
    /**
     * Metodo che rimuove  una partita, quindi i gol e il corrispettivo punteggio ottenuto.
     * @param golFatti i gol fatti 
     * @param golSubiti i gol subiti
     */
    public void remPartita(int golFatti, int golSubiti){
        this.golFatti-=golFatti;
        this.golSubiti-=golSubiti;
        
        if(golFatti==golSubiti){
            pPareggiate--;
            punti--;
            return;
        }
        
        if(golFatti>golSubiti){
            pVinte--;
            punti-=3;
            return;
        }
        
        if(golFatti<golSubiti){
            pPerse--;
            return;
        }
    }
    
    /**
     * Metodo che verifica che una Squadra sia effettivamente utilizzata.
     * 
     * @return true se tutti i valori sono a 0, false altrimenti
     */
    public boolean isAzzerato(){
        return getPGiocate()==0;
    }

}
