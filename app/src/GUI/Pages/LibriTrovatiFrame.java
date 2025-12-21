package GUI.Pages;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

public class LibriTrovatiFrame extends JFrame {
    private final ArrayList<String> libriTrovati = new ArrayList<>();
    private final ArrayList<Integer> edizioniID = new ArrayList<>();

    public LibriTrovatiFrame()
    {
        setSize(400, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // centra finestra
        setTitle("Edizioni Trovate");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    public void addLibro(String s, Integer edizioneID)
    {
        libriTrovati.add(s);
        edizioniID.add(edizioneID);
    }
    @Override public void setVisible(boolean b)
    {
        if (!b)
            return;

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        textArea.setText("\n\n");
        if (!libriTrovati.isEmpty()) {
            int c = 1;
            for (String s : libriTrovati) textArea.append(String.format("  %s) %s\n\n", c++, s));

            JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, libriTrovati.size(), 1));
            JButton viewBtn = new JButton("VAI AL LIBRO");

            JPanel subPanel = new JPanel(new FlowLayout());

            subPanel.add(spinner);
            subPanel.add(viewBtn);

            viewBtn.addActionListener((e) -> {
                BookFrame bp = new BookFrame(
                    edizioniID.get(Integer.parseInt(spinner.getValue().toString()) - 1));
                bp.setVisible(true);
            });

            add(textArea, BorderLayout.CENTER);
            add(subPanel, BorderLayout.PAGE_END);
        }
        else
            textArea.append("\t"
                + "NESSUNA EDIZIONE TROVATA");

        super.setVisible(true);
    }
}
