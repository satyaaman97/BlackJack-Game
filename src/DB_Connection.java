import java.sql.*;
import java.sql.Connection;
public class DB_Connection {

    public static Connection connect(){
         Connection conn= null;
        try{
            //Connecting to the local sqlite database which is "main.db"
            Class.forName("org.sqlite.JDBC");
            String url="jdbc:sqlite:identifier.sqlite:main.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connection Successful!");
            //Handling sql exception
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return conn;
    }

}

