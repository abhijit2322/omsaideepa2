package abhijit.osdm2.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class TestDB {

    /*

      /usr/share/java

      http://dev.mysql.com/doc/connector-j/5.1/en/

      https://jdbc.postgresql.org/documentation/documentation.html

    */

   static Connection conn = null;   

   //public static void main(String[] args) {

      public static void testDB()throws Exception{
      // PostgreSQL

      try {

         System.out.println("Loading Class org.postgresql.Driver");

        // Class.forName("org.postgresql.Driver");

         System.out.println("Loading org.postgresql.Driver Successful");
		  String usr="bddimlbibvfbuz";
          String pass="66da64caf6ea84d26d5c4bcfc7d51ca60ea501b73acb22e703113659b4b34813";

         String url = "jdbc:postgresql://ec2-18-235-97-230.compute-1.amazonaws.com:5432/d7n94veohdls3q";
		 
		 


         Properties props = new Properties();

         props.setProperty("user",usr);

         props.setProperty("password",pass);

         props.setProperty("ssl","true");
		 
		 
		 //Class.forName("org.postgresql.Driver");
          System.out.println(" url "+url+"    usr  :"+usr+"   pass  :"+pass);
        // conn = DriverManager.getConnection(url, props); 
		 conn =DriverManager.getConnection(url, usr, pass);
		 //DATABASE_URL: postgres://bddimlbibvfbuz:66da64caf6ea84d26d5c4bcfc7d51ca60ea501b73acb22e703113659b4b34813@ec2-18-235-97-230.compute-1.amazonaws.com:5432/d7n94veohdls3q

         // or  export CLASSPATH = ${CLASSPATH};postgresql-9.2-1002.jdbc3.jar

       //  url = "jdbc:postgresql://localhost/database?user=user&password=password&ssl=true";
        // Class.forName("org.postgresql.Driver");
         //Connection conn = DriverManager.getConnection(url);

         // Do something with the Connection

         System.out.println("Test Connection Successful");

      } catch (SQLException ex) {

         // handle any errors

         System.out.println("SQLException: " + ex.getMessage());

         System.out.println("SQLState: " + ex.getSQLState());

         System.out.println("VendorError: " + ex.getErrorCode());

      }

   }

}