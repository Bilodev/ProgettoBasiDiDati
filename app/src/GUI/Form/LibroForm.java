package GUI.Form;

import Database.DB;
import GUI.Global;
import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;

public class LibroForm extends JFrame {
    private JTextField txtTitolo;
    private JTextField txtDataPrimaEdizione;
    private JTextField txtAutoreID;

    public LibroForm()
    {
        setTitle("Inserimento Edizione");
        setSize(400, 400);
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

        panel.add(new JLabel("Data prima edizione (YYYY-MM-DD):"));
        txtDataPrimaEdizione = new JTextField();
        panel.add(txtDataPrimaEdizione);

        panel.add(new JLabel("Autore ID:"));
        txtAutoreID = new JTextField();
        panel.add(txtAutoreID);

        JButton btnInvia = new JButton("Invia");
        btnInvia.addActionListener(e -> invia());

        panel.add(new JLabel());
        panel.add(btnInvia);

        add(panel);
    }

    private void invia()
    {
        String titolo = txtTitolo.getText();
        LocalDate dataPrimaEdizione = LocalDate.parse(txtDataPrimaEdizione.getText());
        int autoreID = Integer.parseInt(txtAutoreID.getText());

        Global.db.update(String.format(DB.insertBookQuery, 0, titolo, dataPrimaEdizione, autoreID));
        setVisible(false);

    }
}
