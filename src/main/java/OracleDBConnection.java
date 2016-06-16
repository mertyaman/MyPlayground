import oracle.jdbc.pool.OracleDataSource;

import java.sql.*;

/**
 * Created by mertyaman on 16/06/16 for MyPlayground.
 */
public class OracleDBConnection {
    private static final String host="jdbc:oracle:thin:host:port/service";
    private static final String username="username";
    private static final String password="password";
    private static OracleDataSource ods = null;

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
    public static Connection createConnection(){
        Connection con = null;
        try {
            con = DriverManager.getConnection( host, username, password );
            ods = new OracleDataSource();
            con = ods.getConnection();
            System.out.println("db connection basarili.");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return con;
    }
}
