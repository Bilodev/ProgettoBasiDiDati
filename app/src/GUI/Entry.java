package GUI;

import GUI.Pages.LoginPage;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Database.DB;

public class Entry {
    public Entry(String title, int WIDTH, int HEIGTH)
    {
        Global.mainFrame = new JFrame(title);
        Global.db = new DB();

        Global.mainFrame.setSize(WIDTH, HEIGTH);
        Global.mainFrame.setLocationRelativeTo(null);
        Global.mainFrame.setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        // Entry Point
        LoginPage loginPage = new LoginPage();
        loginPage.setVisible();

        Global.mainFrame.addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { close(); }
        });
    }

    public void setVisible() { Global.mainFrame.setVisible(true); }

    public void close()
    {
        Global.db.close();
        System.exit(0);
    }
}
