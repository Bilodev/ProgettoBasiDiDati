package GUI.Pages;

import Beans.Edizione;
import Database.DB;
import GUI.Global;
import java.awt.Image;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

public class BookFrame extends JFrame {
    private final Edizione edizione;
    public BookFrame(Edizione e) { this.edizione = e; }
    @Override public void setVisible(boolean b)
    {
        if (!b) {
            super.setVisible(false);
            return;
        }

        // ---- VARIABILI (scope esterno) ----
        String status = null;
        Integer voto = null;
        Boolean mezzo = null;
        String recensione = null;

        String didascaliaEdizione = null;
        String titoloEdizione = null;
        String lingua = null;

        String nomeAutore = null;
        String cognomeAutore = null;

        String nomeEditore = null;
        String nazioneEditore = null;

        ArrayList<String> generi = new ArrayList<>();

        boolean trovato = false;

        Double votoMedio = null;
        try {
            ResultSet rsVoto =
                Global.db.query(String.format(DB.getMediaLibro, edizione.getLibroID()));
            if (rsVoto != null && rsVoto.next()) {
                votoMedio = rsVoto.getDouble("votoMedio");
                if (rsVoto.wasNull())
                    votoMedio = null;
            }
            rsVoto.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        // ---- QUERY ----
        ResultSet rs = Global.db.query(
            String.format(DB.masterQuery, Global.utente.getUtenteID(), edizione.getEdizioneID()));

        try {
            if (rs != null && rs.next()) {
                trovato = true;

                // ---- LETTURA (opzionale) ----
                status = rs.getString("status");
                voto = rs.getObject("voto") != null ? rs.getInt("voto") : null;
                mezzo = rs.getObject("mezzo") != null ? rs.getBoolean("mezzo") : null;
                recensione = rs.getString("recensione");

                // ---- EDIZIONE ----
                titoloEdizione = rs.getString("titoloEdizione");
                lingua = rs.getString("lingua");
                didascaliaEdizione = rs.getString("didascaliaEdizione");

                // ---- AUTORE ----
                nomeAutore = rs.getString("nomeAutore");
                cognomeAutore = rs.getString("cognomeAutore");

                // ---- EDITORE ----
                nomeEditore = rs.getString("nomeEditore");

                // ---- GENERI (multi-riga) ----
                do {
                    String genere = rs.getString("genere");
                    if (genere != null)
                        generi.add(genere);
                } while (rs.next());
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        if (!trovato) {
            System.out.println("Libro non trovato");
            return;
        }

        setSize(700, 650);
        setLayout(null);

        setTitle(titoloEdizione);
        JLabel autoreLabel = new JLabel(nomeAutore + " " + cognomeAutore);
        JLabel editoreLabel = new JLabel(nomeEditore);
        JLabel linguaLabel = new JLabel(lingua.substring(0, 3));
        JTextArea didascaliaTextArea = new JTextArea();
        didascaliaTextArea.setEditable(false);
        didascaliaTextArea.setText(didascaliaEdizione);
        didascaliaTextArea.setBounds(50, 180, 240, 200);
        didascaliaTextArea.setLineWrap(true);

        autoreLabel.setBounds(100, 100, 200, 30);
        editoreLabel.setBounds(100, 120, 200, 30);
        linguaLabel.setBounds(100, 130, 200, 30);

        String imagePath = String.format("src/Database/img/%s.png", edizione.getEdizioneID());
        try {
            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage();

            Image scaled = img.getScaledInstance(200, 270, Image.SCALE_SMOOTH);

            ImageIcon coverIcon = new ImageIcon(scaled);
            JLabel lblCover = new JLabel();
            lblCover.setIcon(coverIcon);
            lblCover.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            lblCover.setBounds(400, 70, 200, 270); // esempio: in alto a sinistra
            add(lblCover);
            add(javax.swing.Box.createVerticalStrut(10));
        }
        catch (Exception e) {
        }

        ArrayList<String> statusPossibili = new ArrayList<>();
        if (status == null)
            statusPossibili.add("");
        else
            statusPossibili.add(status);
        for (String s : Global.statusPossibili) {
            if (status == null || !status.equals(s))
                statusPossibili.add(s);
        }

        Object[] array = statusPossibili.toArray();
        JComboBox statiComboBox = new JComboBox(array);
        statiComboBox.setBounds(50, 400, 120, 30);

        JButton confirmBtn = new JButton("Salva");
        confirmBtn.setBounds(100, 560, 100, 20);

        if (status == null)
            confirmBtn.addActionListener(e -> {
                String selectedStatus = statiComboBox.getSelectedItem().toString();
                if (selectedStatus.isEmpty())
                    return;
                Global.db.update(String.format(DB.aggiungiLettura, Global.utente.getUtenteID(),
                    edizione.getEdizioneID(), selectedStatus));
                BookFrame.this.dispose();
            });
        else if (!status.equals("finito"))
            confirmBtn.addActionListener(e -> {
                String selectedStatus = statiComboBox.getSelectedItem().toString();
                if (selectedStatus.isEmpty())
                    return;
                if (selectedStatus.equals("finito"))
                    Global.db.update(String.format(
                        DB.finisciLettura, Global.utente.getUtenteID(), edizione.getEdizioneID()));
                else
                    Global.db.update(String.format(DB.aggiornaLettura, selectedStatus,
                        Global.utente.getUtenteID(), edizione.getEdizioneID()));
                BookFrame.this.dispose();
            });
        else if (status.equals("finito")) {
            SpinnerNumberModel model = new SpinnerNumberModel(0.0, 0.0, 10.0, 0.5);
            JSpinner spinner = new JSpinner(model);
            Double v = .0;
            if (voto != null)
                v = voto + (0.5 * (mezzo ? 1 : 0));
            spinner.setValue(v);
            spinner.setBounds(480, 400, 55, 30);
            add(spinner);

            JTextArea descrizioneTextArea = new JTextArea();
            descrizioneTextArea.setText(recensione);
            descrizioneTextArea.setBounds(210, 400, 250, 150);
            add(descrizioneTextArea);

            confirmBtn.addActionListener(e -> {
                String selectedStatus = statiComboBox.getSelectedItem().toString();
                if (selectedStatus.isEmpty())
                    return;
                if (!selectedStatus.equals("finito"))
                    return;

                int votoDato = (int) Math.floor((Double) spinner.getValue());
                boolean mezzoDato = ((Double) spinner.getValue() - votoDato) > 0;
                Global.db.update(String.format(DB.aggiungiRecensione, votoDato, mezzoDato,
                    descrizioneTextArea.getText(), Global.utente.getUtenteID(),
                    edizione.getEdizioneID()));
                BookFrame.this.dispose();
            });
        }

        if (votoMedio != null) {
            JLabel votoMedioLabel = new JLabel(votoMedio + "/10");
            votoMedioLabel.setBounds(400, 360, 100, 20);
            add(votoMedioLabel);
        }

        add(statiComboBox);
        add(confirmBtn);

        add(autoreLabel);
        add(editoreLabel);
        add(linguaLabel);
        add(didascaliaTextArea);

        super.setVisible(true);
    }
}
