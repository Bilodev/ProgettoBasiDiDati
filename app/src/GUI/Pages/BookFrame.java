package GUI.Pages;

import javax.swing.JFrame;

public class BookFrame extends JFrame {
    private final Integer edizioneID;
    public BookFrame(Integer edizioneID) { this.edizioneID = edizioneID; }
    @Override public void setVisible(boolean b)
    {
        if (!b)
            super.setVisible(false);

        System.out.println(edizioneID);

        // QUERY

        // MOSTRA LIBRO

        // CAMBIA STATUS

        super.setVisible(true);
    }
}
