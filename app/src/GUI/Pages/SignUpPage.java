package GUI.Pages;

import Database.DB;
import GUI.Global;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignUpPage {
    public void setVisible()
    {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // DEFINITION

        JLabel nomeLabel = new JLabel("Nome");
        JTextField nomeTextField = new JTextField();

        JLabel cognomeLabel = new JLabel("Cognome");
        JTextField cognomeTextField = new JTextField();

        JLabel emailLabel = new JLabel("Email");
        JTextField emailTextField = new JTextField();

        JLabel passwordLabel = new JLabel("Password");
        JPasswordField passwordTextField = new JPasswordField();

        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        JPasswordField confirmPasswordTextField = new JPasswordField();

        JButton submitButton = new JButton("Submit");
        JLabel orLabel = new JLabel("or");
        JButton loginButton = new JButton("Login");

        // ACTIONS

        submitButton.addActionListener(e -> {
            handleSignUp(nomeTextField.getText(), cognomeTextField.getText(),
                emailTextField.getText(), passwordTextField.getPassword(),
                confirmPasswordTextField.getPassword());
            Global.mainFrame.remove(panel);
            LoginPage loginPage = new LoginPage();
            loginPage.setVisible();
        });

        loginButton.addActionListener(e -> {
            Global.mainFrame.remove(panel);
            LoginPage loginPage = new LoginPage();
            loginPage.setVisible();
        });

        // POSITIONING

        int width = Global.mainFrame.getWidth() / 2;
        int height = Global.mainFrame.getHeight() / 2;

        int textFieldWidth = 200;
        int textFieldHeight = 30;

        nomeLabel.setBounds(width - textFieldWidth / 2, height - textFieldHeight / 2 - 270,
            textFieldWidth, textFieldHeight);
        nomeTextField.setBounds(width - textFieldWidth / 2, height - textFieldHeight / 2 - 240,
            textFieldWidth, textFieldHeight);

        cognomeLabel.setBounds(width - textFieldWidth / 2, height - textFieldHeight / 2 - 200,
            textFieldWidth, textFieldHeight);
        cognomeTextField.setBounds(width - textFieldWidth / 2, height - textFieldHeight / 2 - 170,
            textFieldWidth, textFieldHeight);

        emailLabel.setBounds(width - textFieldWidth / 2, height - textFieldHeight / 2 - 130,
            textFieldWidth, textFieldHeight);
        emailTextField.setBounds(width - textFieldWidth / 2, height - textFieldHeight / 2 - 100,
            textFieldWidth, textFieldHeight);

        passwordLabel.setBounds(width - textFieldWidth / 2, height - textFieldHeight / 2 - 60,
            textFieldWidth, textFieldHeight);

        passwordTextField.setBounds(width - textFieldWidth / 2, height - textFieldHeight / 2 - 30,
            textFieldWidth, textFieldHeight);

        confirmPasswordLabel.setBounds(width - textFieldWidth / 2, height - textFieldHeight / 2,
            textFieldWidth, textFieldHeight);

        confirmPasswordTextField.setBounds(width - textFieldWidth / 2,
            height - textFieldHeight / 2 + 30, textFieldWidth, textFieldHeight);

        submitButton.setBounds(width - 50, height + 70, 90, 20);
        orLabel.setBounds(width - 12, height + 100, 40, 20);
        loginButton.setBounds(width - 50, height + 140, 90, 20);

        panel.add(nomeLabel);
        panel.add(nomeTextField);
        panel.add(cognomeLabel);
        panel.add(cognomeTextField);

        panel.add(emailLabel);
        panel.add(emailTextField);
        panel.add(passwordLabel);
        panel.add(passwordTextField);
        panel.add(confirmPasswordLabel);
        panel.add(confirmPasswordTextField);
        panel.add(submitButton);
        panel.add(orLabel);
        panel.add(loginButton);

        Global.mainFrame.add(panel);
        Global.mainFrame.validate();
        Global.mainFrame.repaint();
    }

    public void handleSignUp(
        String nome, String cognome, String email, char[] password, char[] password2)
    {
        String pwd = String.copyValueOf(password);
        String pwd2 = String.copyValueOf(password2);

        if (nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || pwd.isEmpty()
            || pwd2.isEmpty())
            return;
        if (!pwd.equals(pwd2))
            return;

        String query = String.format(DB.loginQuery, email, pwd);
        ResultSet rs = Global.db.query(query);

        try {
            if (rs.next()) {
                System.out.println("Utente Esistente");
                return;
            }
            query = String.format(DB.insertUserQuery, nome, cognome, email, pwd);
            if (Global.db.update(query) > 0) {
                System.out.println("Utente Registrato");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
