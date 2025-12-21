package Beans;

public class Edizione {
    private int edizioneID;
    private int editoreID;
    private int libroID;
    private String titolo;
    private String lingua;
    private String didascalia;
    private Integer numeroPagine;

    public Edizione() {}

    public Edizione(int edizioneID, int editoreID, int libroID, String titolo, String lingua,
        String didascalia, Integer numeroPagine)
    {
        this.edizioneID = edizioneID;
        this.editoreID = editoreID;
        this.libroID = libroID;
        this.titolo = titolo;
        this.lingua = lingua;
        this.didascalia = didascalia;
        this.numeroPagine = numeroPagine;
    }

    public int getEdizioneID() { return edizioneID; }

    public void setEdizioneID(int edizioneID) { this.edizioneID = edizioneID; }

    public int getEditoreID() { return editoreID; }

    public void setEditoreID(int editoreID) { this.editoreID = editoreID; }

    public int getLibroID() { return libroID; }

    public void setLibroID(int libroID) { this.libroID = libroID; }

    public String getTitolo() { return titolo; }

    public void setTitolo(String titolo) { this.titolo = titolo; }

    public String getLingua() { return lingua; }

    public void setLingua(String lingua) { this.lingua = lingua; }

    public String getDidascalia() { return didascalia; }

    public void setDidascalia(String didascalia) { this.didascalia = didascalia; }

    public Integer getNumeroPagine() { return numeroPagine; }

    public void setNumeroPagine(Integer numeroPagine) { this.numeroPagine = numeroPagine; }

    @Override public String toString() { return titolo; }
}
