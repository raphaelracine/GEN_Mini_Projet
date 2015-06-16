/**
 * Cette classe représente la connexion à la base de données
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */

package scotlandyardserver.database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseConnection {

    private Connection connection;
    private Statement statement;

    public DataBaseConnection() {
        String url = "jdbc:mysql://localhost:3306/scotlandyard?user=scotlandyard&password=scotlandyard";

        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();

            executeCRUDrequest("UPDATE user SET connected=false");
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet getSQLSelection(String selectionQuery) throws SQLException {
        return statement.executeQuery(selectionQuery);
    }

    public boolean executeCRUDrequest(String CRUDrequest) throws SQLException {
        return statement.execute(CRUDrequest);
    }
}
