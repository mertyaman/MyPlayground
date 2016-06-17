import oracle.jdbc.pool.OracleDataSource;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.sql.*;

/**
 * Created by mertyaman on 16/06/16 for MyPlayground.
 */
public class OracleDBConnection {


    private final String propertyFileName = System.getProperty("user.dir")+"/src/main/resources/passwords.properties";
    PropertiesConfiguration config = new PropertiesConfiguration(propertyFileName);

    String host = config.getString("DBhost");
    String username = config.getString("DBusername");
    String password = config.getString("DBpassword");

    public OracleDBConnection() throws ConfigurationException {
    }

    public static Connection createConnection(){
        Connection con = null;
        try {
            OracleDBConnection oracleDBConnection =new OracleDBConnection();
            con = DriverManager.getConnection(oracleDBConnection.host, oracleDBConnection.username, oracleDBConnection.password );
            System.out.println("db connection basarili.");
        }
        catch (SQLException | ConfigurationException e) {
            System.out.println(e.getMessage());
        }

        return con;
    }

    public static void endConnection(ResultSet rs, Statement preparedStatement, Connection con){
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("ResultSet could not be closed!");
                System.out.println(e.getMessage());
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e1) {
                System.out.println("Statement could not be closed!");
                System.out.println(e1.getMessage());
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e2) {
                System.out.println("Connection could not be closed!");
                System.out.println(e2.getMessage());
            }
        }
    }
}
