package GUI.Pages;

import Beans.Utente;
import Database.DB;
import GUI.Global;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPage {
    public void setVisible()
    {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // DEFINITION

        JLabel emailLabel = new JLabel("Email");
        JTextField emailTextField = new JTextField();

        JLabel passwordLabel = new JLabel("Password");
        JPasswordField passwordTextField = new JPasswordField();

        JButton submitButton = new JButton("Submit");
        JLabel orLabel = new JLabel("or");
        JButton signUpButton = new JButton("Sign Up");

        // ACTIONS

        submitButton.addActionListener(e -> {
            Global.utente = handleLogin(emailTextField.getText(), passwordTextField.getPassword());
            if (Global.utente != null) {
                Global.mainFrame.remove(panel);
                HomePage homePage = new HomePage();
                homePage.setVisible();
            }
        });

        signUpButton.addActionListener(e -> {
            Global.mainFrame.remove(panel);
            SignUpPage signUp = new SignUpPage();
            signUp.setVisible();
        });

        // POSITIONING

        int width = Global.mainFrame.getWidth() / 2;
        int height = Global.mainFrame.getHeight() / 2;

        int textFieldWidth = 200;
        int textFieldHeight = 30;

        emailTextField.setBounds(width - textFieldWidth / 2, height - textFieldHeight / 2 - 100,
            textFieldWidth, textFieldHeight);
        passwordTextField.setBounds(width - textFieldWidth / 2, height - textFieldHeight / 2 - 30,
            textFieldWidth, textFieldHeight);

        emailLabel.setBounds(width - textFieldWidth / 2, height - textFieldHeight / 2 - 130,
            textFieldWidth, textFieldHeight);

        passwordLabel.setBounds(width - textFieldWidth / 2, height - textFieldHeight / 2 - 60,
            textFieldWidth, textFieldHeight);

        submitButton.setBounds(width - 50, height + 20, 90, 20);
        orLabel.setBounds(width - 12, height + 50, 40, 20);
        signUpButton.setBounds(width - 50, height + 80, 90, 20);

        panel.add(emailLabel);
        panel.add(emailTextField);
        panel.add(passwordLabel);
        panel.add(passwordTextField);
        panel.add(submitButton);
        panel.add(orLabel);
        panel.add(signUpButton);

        Global.mainFrame.add(panel);
        Global.mainFrame.revalidate();
        Global.mainFrame.repaint();
    }

    public Utente handleLogin(String email, char[] password)
    {
        String pwd = String.copyValueOf(password);

        if (email.isEmpty() || pwd.isEmpty())
            return null;

        String query = String.format(DB.loginQuery, email, pwd);
        ResultSet rs = Global.db.query(query);

        try {
            if (!rs.next()) {
                System.out.println("Utente Non Trovato");
                return null;
            }
            System.out.println("Login Effettuato");
            Utente u =
                new Utente(rs.getInt("utenteID"), rs.getString("nome"), rs.getString("cognome"),
                    rs.getString("email"), rs.getString("password"), rs.getBoolean("isAdmin"));
            return u;
        }
        catch (SQLException e) {
            return null;
        }
    }
}
