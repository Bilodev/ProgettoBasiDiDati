package Database;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class DB {
    private Connection connect;
    private Statement st;

    public static String loginQuery = "SELECT * FROM Utente WHERE email=\"%s\" AND password=\"%s\"";
    public static String insertUserQuery =
        "INSERT INTO Utente VALUES(NULL, \"%s\", \"%s\", \"%s\", \"%s\", 0)";

    public static String userBookQuery =
        "SELECT * FROM LETTURA l JOIN Edizione e ON l.edizioneID = e.edizioneID JOIN Utente u ON l.utenteID = u.utenteID WHERE l.utenteID = %s";

    public static String insertEdizioneQuery =
        "INSERT INTO Edizione (edizioneID, editoreID, libroID, titolo, lingua, didascalia, numeroPagine) VALUES (%s, %s, %s, \"%s\", \"%s\", \"%s\", %d);";

    public static String insertBookQuery =
        "INSERT INTO Libro (libroID, titolo, dataPrimaEdizione, autoreID) VALUES (%s, \"%s\", \"%s\", %s);";

    public static String insertPrefazioneQuery =
        "INSERT INTO Prefazione VALUES (%d, \"%s\", %d, %d)";

    public static String searchBookQuery =
        "SELECT * FROM Edizione e1 JOIN Editore e2 ON e1.editoreID = e2.editoreID JOIN Libro l ON l.libroID=e1.libroID JOIN Autore a ON a.autoreID = l.autoreID  WHERE e1.titolo LIKE \"%s\"";

    public DB()
    {
        Properties props = new Properties();
        Path envFile = Paths.get(".env");
        try (InputStream inputStream = Files.newInputStream(envFile)) {
            props.load(inputStream);
        }
        catch (IOException e) {
            System.out.println("Could not load .env");
            System.exit(1);
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connect = DriverManager.getConnection(
                props.get("URL").toString() + props.get("DBNAME").toString(),
                props.get("USERNAME").toString(), props.get("PASSWORD").toString());
            st = connect.createStatement();
        }
        catch (SQLException | ClassNotFoundException e) {
            System.out.println("Connection failed");
            System.exit(1);
        }
        System.out.println("Connection Established successfully");
    }

    public ResultSet query(String query)
    {
        ResultSet rs = null;
        try {
            rs = st.executeQuery(query);
        }
        catch (SQLException e) {
            System.out.printf("'%s' generated an error%n", query);
            System.out.println(e.getMessage());
        }
        return rs;
    }

    public int update(String query)
    {
        try {
            return st.executeUpdate(query);
        }
        catch (SQLException e) {
            System.out.printf("'%s' generated an error%n", query);
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public void close()
    {
        try {
            connect.close();
        }
        catch (SQLException e) {
            System.out.println("Could not close connection");
            System.exit(1);
        }
        System.out.println("Connection Closed");
    }
}
