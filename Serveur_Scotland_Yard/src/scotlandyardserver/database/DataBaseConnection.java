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

    /**
     * Constructeur
     */
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

    /**
     * Permet de faire une requête de sélection sur la base de données
     * @param selectionQuery Requête de sélection SQL
     * @return Un ResultSet qui contient le résultat de la requête
     * @throws SQLException 
     */
    public ResultSet getSQLSelection(String selectionQuery) throws SQLException {
        return statement.executeQuery(selectionQuery);
    }

    /**
     * Permet d'exécuter une requête de type (créate update delete) sur la base de données
     * @param CRUDrequest La requête SQL à effectuer
     * @return Retourne true si la requête s'est bien passée, false sinon
     * @throws SQLException 
     */
    public boolean executeCRUDrequest(String CRUDrequest) throws SQLException {
        return statement.execute(CRUDrequest);
    }
}
