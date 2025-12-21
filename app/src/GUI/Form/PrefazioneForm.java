package GUI.Form;

import Database.DB;
import GUI.Global;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PrefazioneForm extends JFrame {
    private JTextField txtTitolo;
    private JTextField txtAutoreID;
    private JTextField txtEdizioneID;

    public PrefazioneForm()
    {
        setTitle("Inserimento Prefazione");
        setSize(350, 250);
        setLocationRelativeTo(null); // centra finestra

        initUI();
    }

    private void initUI()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 5, 5));

        panel.add(new JLabel("Titolo:"));
        txtTitolo = new JTextField();
        panel.add(txtTitolo);

        panel.add(new JLabel("Autore ID:"));
        txtAutoreID = new JTextField();
        panel.add(txtAutoreID);

        panel.add(new JLabel("Edizione ID:"));
        txtEdizioneID = new JTextField();
        panel.add(txtEdizioneID);

        JButton btnInvia = new JButton("Invia");
        btnInvia.addActionListener(e -> invia());

        panel.add(new JLabel()); // spazio vuoto
        panel.add(btnInvia);

        add(panel);
    }

    private void invia()
    {
        String titolo = txtTitolo.getText();
        int autoreID = Integer.parseInt(txtAutoreID.getText());
        int edizioneID = Integer.parseInt(txtEdizioneID.getText());

        Global.db.update(String.format(DB.insertPrefazioneQuery, 0, titolo, autoreID, edizioneID));

        setVisible(false);
    }
}
