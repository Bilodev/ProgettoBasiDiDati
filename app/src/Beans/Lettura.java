package Beans;

public class Lettura  {

    private int utenteID;
    private int edizioneID;

    // da_leggere | in_lettura | finito
    private String status;

    private Recensione recensione;

    public Lettura() {
    }

    public Lettura(int utenteID, int edizioneID, String status, Recensione recensione) {
        this.utenteID = utenteID;
        this.edizioneID = edizioneID;
        this.status = status;
        this.recensione = recensione;
    }

    public int getUtenteID() {
        return utenteID;
    }

    public void setUtenteID(int utenteID) {
        this.utenteID = utenteID;
    }

    public int getEdizioneID() {
        return edizioneID;
    }

    public void setEdizioneID(int edizioneID) {
        this.edizioneID = edizioneID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Recensione getRecensione() {
        return recensione;
    }

    public void setRecensione(Recensione recensione) {
        this.recensione = recensione;
    }
}
