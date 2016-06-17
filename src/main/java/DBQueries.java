import org.apache.commons.configuration.ConfigurationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by mertyaman on 16/06/16 for MyPlayground.
 */
public class DBQueries extends OracleDBConnection{

    public DBQueries() throws ConfigurationException {
    }

    public static String getCityName(String plateNumber){
        Connection con=createConnection();

        PreparedStatement preparedStatement=null;
        ResultSet rs=null;
        String cityName = "my144sw";

        try {
            preparedStatement=con.prepareStatement("select c.name from LBR.city_lookup c where c.id=?");
            preparedStatement.setString(1, plateNumber);
            rs=preparedStatement.executeQuery();

            if(rs.next())
            {
                cityName=rs.getString(1);
                return cityName;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        endConnection(rs,preparedStatement,con);
        return null;

    }
}
