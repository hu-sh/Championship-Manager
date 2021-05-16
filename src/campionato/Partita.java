package campionato;

import java.io.Serializable;

/**
 * Classe rappresentante una partita.
 * 
 * @author Davide Petillo
 */
public class Partita implements Serializable{
    int giornata;
    String casa;
    String ospite;
    int golCasa;
    int golOspite;

    public Partita(int giornata, String casa, String ospite, int golCasa, int golOspite) {
        this.giornata = giornata;
        this.casa = casa;
        this.ospite = ospite;
        this.golCasa = golCasa;
        this.golOspite = golOspite;
    }
    
    /**
     * Metodo che modifica una partita.
     * 
     * @param golCasa i nuovi gol fatti dalla squadra di casa
     * @param golOspite i nuovi gol fatti dalla squadra ospite
     */
    public void modifica(int golCasa, int golOspite){
        this.golCasa=golCasa;
        this.golOspite=golOspite;
    }
    
    public int getGiornata() {
        return giornata;
    }

    public String getCasa(){
        return casa;
    }
    
    public String getOspite(){
        return ospite;
    }

    public int getGolCasa() {
        return golCasa;
    }

    public int getGolOspite() {
        return golOspite;
    }

    public void setGiornata(int giornata) {
        this.giornata = giornata;
    }

    public void setCasa(String casa) {
        this.casa = casa;
    }

    public void setOspite(String ospite) {
        this.ospite = ospite;
    }

    public void setGolCasa(int golCasa) {
        this.golCasa = golCasa;
    }

    public void setGolOspite(int golOspite) {
        this.golOspite = golOspite;
    }
    
    
    @Override
    public String toString() {
        return "Partita{" + "giornata=" + giornata + ", casa=" + casa + ", ospite=" + ospite + ", golCasa=" + golCasa + ", golOspite=" + golOspite + '}';
    }
        
}
