package GUI.Form;

import Database.DB;
import GUI.Global;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EdizioneForm extends JFrame {
    private JTextField txtEditoreID;
    private JTextField txtLibroID;
    private JTextField txtTitolo;
    private JTextField txtLingua;
    private JTextField txtDidascalia;
    private JTextField txtNumeroPagine;

    public EdizioneForm()
    {
        setTitle("Inserimento Edizione");
        setSize(400, 400);
        setLocationRelativeTo(null); // centra finestra

        initUI();
    }

    private void initUI()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2, 5, 5));

        panel.add(new JLabel("Editore ID:"));
        txtEditoreID = new JTextField();
        panel.add(txtEditoreID);

        panel.add(new JLabel("Libro ID:"));
        txtLibroID = new JTextField();
        panel.add(txtLibroID);

        panel.add(new JLabel("Titolo:"));
        txtTitolo = new JTextField();
        panel.add(txtTitolo);

        panel.add(new JLabel("Lingua:"));
        txtLingua = new JTextField();
        panel.add(txtLingua);

        panel.add(new JLabel("Didascalia:"));
        txtDidascalia = new JTextField();
        panel.add(txtDidascalia);

        panel.add(new JLabel("Numero pagine:"));
        txtNumeroPagine = new JTextField();
        panel.add(txtNumeroPagine);

        JButton btnInvia = new JButton("Invia");
        btnInvia.addActionListener(e -> invia());

        panel.add(new JLabel()); // spazio vuoto
        panel.add(btnInvia);

        add(panel);
    }

    private void invia()
    {
        int editoreID = Integer.parseInt(txtEditoreID.getText());
        int libroID = Integer.parseInt(txtLibroID.getText());
        String titolo = txtTitolo.getText();
        String lingua = txtLingua.getText();
        String didascalia = txtDidascalia.getText();
        int numeroPagine = Integer.parseInt(txtNumeroPagine.getText());

        Global.db.update(String.format(DB.insertEdizioneQuery, 0, editoreID, libroID, titolo,
            lingua, didascalia, numeroPagine));
        setVisible(false);
    }
}
