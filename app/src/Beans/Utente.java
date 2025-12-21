package Beans;

public class Utente {
    private int utenteID;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private boolean isAdmin;
    
    // Costruttore vuoto (obbligatorio per i JavaBean)
    public Utente() {}

    // Costruttore completo
    public Utente(
        int utenteID, String nome, String cognome, String email, String password, boolean isAdmin)
    {
        this.utenteID = utenteID;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    // Getter e Setter

    public int getUtenteID() { return utenteID; }

    public void setUtenteID(int utenteID) { this.utenteID = utenteID; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }

    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public boolean isAdmin() { return isAdmin; }

    public void setAdmin(boolean isAdmin) { this.isAdmin = isAdmin; }

    @Override public String toString()
    {
        return String.format(
            "Utente [utenteId=%s, nome=%s, cognome=%s, email=%s, password=%s, isAdmin=%s]",
            utenteID, nome, cognome, email, password, isAdmin);
    }
}
