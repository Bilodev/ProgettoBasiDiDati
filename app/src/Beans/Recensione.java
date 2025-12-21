package Beans;
import java.time.LocalDate;

public class Recensione {
    private Integer voto; // 0–10
    private Boolean mezzo; // true = mezzo voto
    private String descrizione;
    private LocalDate dataFine;

    public Recensione() {}

    public Recensione(Integer voto, Boolean mezzo, String descrizione, LocalDate dataFine)
    {
        this.voto = voto;
        this.mezzo = mezzo;
        this.descrizione = descrizione;
        this.dataFine = dataFine;
    }

    public Double getVoto() { return voto + ((mezzo) ? 0.5 : 0); }

    public void setVoto(Integer voto) { this.voto = voto; }

    public void setMezzo(Boolean mezzo) { this.mezzo = mezzo; }

    public String getDescrizione() { return descrizione; }

    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public LocalDate getDataFine() { return dataFine; }

    public void setDataFine(LocalDate dataFine) { this.dataFine = dataFine; }
}
