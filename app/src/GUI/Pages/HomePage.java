package GUI.Pages;

import Beans.Edizione;
import Beans.Lettura;
import Beans.Recensione;
import Database.DB;
import GUI.Form.EdizioneForm;
import GUI.Form.LibroForm;
import GUI.Form.PrefazioneForm;
import GUI.Global;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class HomePage {
    private final ArrayList<Edizione> edizioni = new ArrayList<>();
    private final ArrayList<Lettura> letture = new ArrayList<>();
    private JPanel gotoBookPanel = new JPanel(new FlowLayout());
    private JSpinner spinner = new JSpinner();

    private void renderAdmin(JPanel panel)
    {
        JButton addBookBtn = new JButton("Add Book");
        JButton addEditionBtn = new JButton("Add Edition");
        JButton addPrefazioneBtn = new JButton("Add Prefazione");

        addBookBtn.setBounds(Global.mainFrame.getWidth() - 150, 20, 120, 30);
        addEditionBtn.setBounds(Global.mainFrame.getWidth() - 150, 70, 120, 30);
        addPrefazioneBtn.setBounds(Global.mainFrame.getWidth() - 220, 120, 200, 30);

        addBookBtn.addActionListener(e -> {
            var libroForm = new LibroForm();
            libroForm.setVisible(true);
        });
        addEditionBtn.addActionListener(e -> {
            var edizioneForm = new EdizioneForm();
            edizioneForm.setVisible(true);
        });
        addPrefazioneBtn.addActionListener(e -> {
            var prefazioneForm = new PrefazioneForm();
            prefazioneForm.setVisible(true);
        });

        panel.add(addBookBtn);
        panel.add(addEditionBtn);
        panel.add(addPrefazioneBtn);
    }

    private int refreshText(JTextArea booksReadTextArea, JTextArea booksToReadTextArea)
    {
        edizioni.clear();
        letture.clear();
        getBookList();
        booksReadTextArea.setText("LETTI:\n\n");
        booksToReadTextArea.setText("DA LEGGERE:\n\n");

        int c = 0;
        for (int i = 0; i < edizioni.size(); i++) {
            Lettura l = letture.get(i);
            String str = String.format(" %s) %s\n", c + 1, edizioni.get(i).toString());

            if (l.getStatus().equals("finito")) {
                booksReadTextArea.append(str);
                c++;
            }
            else if (l.getStatus().equals("da_leggere")) {
                booksToReadTextArea.append(str);
                c++;
            }
        }
        spinner.setModel(new SpinnerNumberModel(1, 1, edizioni.size(), 1));
        // se ci sono solo libri leggenti torna 0
        return c;
    }

    private void renderUser(JPanel panel)
    {
        JTextField searchBookTextField = new JTextField();
        JButton searchBookBtn = new JButton("Cerca");

        int offset = 150;
        searchBookTextField.setBounds(Global.mainFrame.getWidth() / 2 - offset, 200, 250, 30);
        searchBookBtn.setBounds(Global.mainFrame.getWidth() / 2 + 250 - offset, 200, 80, 30);

        JTextArea booksReadTextArea = new JTextArea();
        booksReadTextArea.setEditable(false);

        JTextArea booksToReadTextArea = new JTextArea();
        booksToReadTextArea.setEditable(false);

        booksReadTextArea.setBounds(10, 350, Global.mainFrame.getWidth() / 2 - 10, 300);
        booksToReadTextArea.setBounds(
            Global.mainFrame.getWidth() / 2 + 10, 350, Global.mainFrame.getWidth() / 2 - 10, 300);

        int c = refreshText(booksReadTextArea, booksToReadTextArea);

        if (!edizioni.isEmpty() && c != 0) {
            spinner.setModel(new SpinnerNumberModel(1, 1, edizioni.size(), 1));
            JButton viewBtn = new JButton("VAI AL LIBRO");

            gotoBookPanel.add(spinner);
            gotoBookPanel.add(viewBtn);

            viewBtn.addActionListener((e) -> {
                BookFrame bp = new BookFrame(
                    edizioni.get(Integer.parseInt(spinner.getValue().toString()) - 1));
                bp.setVisible(true);
            });

            gotoBookPanel.setBounds(10, 270, Global.mainFrame.getWidth() / 2, 30);
            Global.mainFrame.add(gotoBookPanel);
        }

        searchBookBtn.addActionListener(e -> {
            String titoloEdizione = searchBookTextField.getText();
            if (titoloEdizione.isEmpty())
                return;
            ResultSet rs = Global.db.query(String.format(DB.searchBookQuery, titoloEdizione));
            LibriTrovatiFrame libriTrovatiPage = new LibriTrovatiFrame();
            try {
                while (rs.next()) {
                    Edizione edizione = new Edizione();

                    edizione.setEdizioneID(rs.getInt("e1.edizioneID"));
                    edizione.setEditoreID(rs.getInt("e1.editoreID"));
                    edizione.setLibroID(rs.getInt("e1.libroID"));
                    edizione.setTitolo(rs.getString("e1.titolo"));
                    edizione.setLingua(rs.getString("e1.lingua"));
                    edizione.setDidascalia(rs.getString("e1.didascalia"));
                    edizione.setNumeroPagine(rs.getObject("e1.numeroPagine", Integer.class));

                    // e2: Editore
                    String s = String.format("%s - %s %s (%s)", edizione, rs.getString("a.nome"),
                        rs.getString("a.cognome"), rs.getString("e2.nome"));
                    libriTrovatiPage.addLibro(s, edizione);
                }
            }
            catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            libriTrovatiPage.setVisible(true);
        });

        if (!edizioni.isEmpty()) {
            panel.add(booksReadTextArea);
            panel.add(booksToReadTextArea);
        }
        panel.add(searchBookTextField);
        panel.add(searchBookBtn);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            if (refreshText(booksReadTextArea, booksToReadTextArea) == 0)
                gotoBookPanel.setVisible(false);
            else
                gotoBookPanel.setVisible(true);
        });
        refreshButton.setBounds(30, 40, 100, 20);
        panel.add(refreshButton);
    }

    private void getBookList()
    {
        ResultSet rs =
            Global.db.query(String.format(DB.userBookQuery, Global.utente.getUtenteID()));
        if (rs == null)
            return;
        try {
            while (rs.next()) {
                Recensione recensione = new Recensione(rs.getObject("voto", Integer.class),
                    rs.getObject("mezzo", Boolean.class), rs.getString("descrizione"),
                    rs.getObject("dataFine", LocalDate.class));

                Lettura lettura = new Lettura();

                lettura.setUtenteID(rs.getInt("utenteID"));
                lettura.setEdizioneID(rs.getInt("edizioneID"));
                lettura.setStatus(rs.getString("status"));
                lettura.setRecensione(recensione);

                Edizione edizione = new Edizione();

                edizione.setEdizioneID(rs.getInt("e.edizioneID"));
                edizione.setEditoreID(rs.getInt("editoreID"));
                edizione.setLibroID(rs.getInt("libroID"));
                edizione.setTitolo(rs.getString("titolo"));
                edizione.setLingua(rs.getString("lingua"));
                edizione.setDidascalia(rs.getString("didascalia"));
                edizione.setNumeroPagine(rs.getObject("numeroPagine", Integer.class));

                edizioni.add(edizione);
                letture.add(lettura);
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void setVisible()
    {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        getBookList();

        String welcomeString = String.format(
            "Welcome %s%s!", (Global.utente.isAdmin() ? "admin " : ""), Global.utente.getNome());
        JLabel welcomeLabel = new JLabel(welcomeString);

        welcomeLabel.setBounds(30, 10, welcomeString.length() * 20, 40);

        panel.add(welcomeLabel);

        if (Global.utente.isAdmin())
            renderAdmin(panel);
        renderUser(panel);

        Global.mainFrame.add(panel);
        Global.mainFrame.revalidate();
        Global.mainFrame.repaint();
    }
}
