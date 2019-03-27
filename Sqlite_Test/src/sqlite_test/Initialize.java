
package sqlite_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Initialize {
    private static Connection con = null;

    // Main Function
      public static void main( String args[] ) {
          ResultSet rs = null;
      try {
        Connection con = getConnection();
          System.out.println("con is : " + con);
          if(con != null){
              //tableCreate();
              insertData();
              rs = veiwData();
              while(rs.next())
              {
                  System.out.println( "ID = " + rs.getInt("id"));
                  System.out.println( "NAME = " + rs.getString("name") );
              }
              //deleteData();
              
              rs.close();
              con.close();
          
          }
      } catch ( Exception e ) {
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);
      }
      
   }

// DB Connection    
    public static Connection getConnection(){
      try {
         Class.forName("org.sqlite.JDBC");
         con = DriverManager.getConnection("jdbc:sqlite:test.db");
          //System.out.println("con = " + con);
          return con;
      } catch ( Exception e ) {
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);
    }
      return con;
    }

// Table Create
    private static void tableCreate() throws SQLException {
        Connection con = getConnection();
        Statement stmt = con.createStatement();
        String sqlTable = "CREATE TABLE product(id integer, name varchar(50))";
        String dropTable = "DROP TABLE product;";
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("Select * from product;");
            if(!rs.next()){
                System.out.println("Droping product table.");
                stmt.execute(dropTable);
                System.out.println("Building a table.");
                stmt.execute(sqlTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
    }
// Insert Data    
    private static void insertData() throws SQLException{
        Connection con = getConnection();
        Statement st = con.createStatement();
        String sqlInsert = "INSERT INTO product VALUES(1,'Roman')";
        st.executeUpdate(sqlInsert);
        st.close();

        con.close();
        
        /*
        String sqlInsert = "INSERT INTO product (id, name) VALUES(?,?)";
        PreparedStatement ps = con.prepareCall(sqlInsert);
        ps.setString(1, "Roman");
        ps.execute();
        System.out.println("Data saved successfully.");
*/

    
    }
// View Data   
    private static ResultSet veiwData() throws SQLException{
    Connection con = getConnection();
    Statement st = con.createStatement();
    String sqlSelect = "SELECT * FROM product";
    ResultSet rs = st.executeQuery(sqlSelect);
        return rs;
    
    }
// Delete data
    private static void deleteData() throws SQLException {
    Connection con = getConnection();
    Statement st = con.createStatement();
    String sqlSelect = "DELETE FROM product";   
    st.executeUpdate(sqlSelect);
    }
}
