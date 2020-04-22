package abhijit.osdm2.models;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;

public class TransactionExample {

    //public static void main(String[] args) {
        public static void connectDB() {
		
		try {
		Class.forName("org.postgresql.Driver");
		}
		catch(Exception u){}

         System.out.println("Loading org.postgresql.Driver Successful");
		  String usr="bddimlbibvfbuz";
          String pass="66da64caf6ea84d26d5c4bcfc7d51ca60ea501b73acb22e703113659b4b34813";

         String url = "jdbc:postgresql://ec2-18-235-97-230.compute-1.amazonaws.com:5432/d7n94veohdls3q";
		 
		 System.out.println(" url "+url+"    usr  :"+usr+"   pass  :"+pass);
		

        try (Connection conn = DriverManager.getConnection(
                url,usr,pass);
             Statement statement = conn.createStatement();
             PreparedStatement psInsert = conn.prepareStatement(SQL_INSERT);
             PreparedStatement psUpdate = conn.prepareStatement(SQL_UPDATE)) {

            statement.execute(SQL_TABLE_DROP);
            statement.execute(SQL_TABLE_CREATE);

            // start transaction block
            conn.setAutoCommit(false); // default true

            // Run list of insert commands
            psInsert.setString(1, "Abhijit1");
            psInsert.setBigDecimal(2, new BigDecimal(10));
            psInsert.setTimestamp(3, Timestamp.valueOf("20-02-2020"));//LocalDateTime.now()));
            psInsert.execute();

            psInsert.setString(1, "Abhijit2");
            psInsert.setBigDecimal(2, new BigDecimal(20));
            psInsert.setTimestamp(3, Timestamp.valueOf("20-02-2020"));//Timestamp.valueOf(LocalDateTime.now()));
            psInsert.execute();

            // Run list of update commands

            // error, test roolback
            // org.postgresql.util.PSQLException: No value specified for parameter 1.
           // psUpdate.setBigDecimal(2, new BigDecimal(999.99));
            //psUpdate.setBigDecimal(1, new BigDecimal(999.99));
            //psUpdate.setString(1, "mkyong");
           // psUpdate.execute();

            // end transaction block, commit changes
            conn.commit();

            // good practice to set it back to default true
            conn.setAutoCommit(true);

            System.out.println("DB Insert  Successful...............");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static final String SQL_INSERT = "INSERT INTO EMPLOYEE (NAME, SALARY, CREATED_DATE) VALUES (?,?,?)";

    private static final String SQL_UPDATE = "UPDATE EMPLOYEE SET SALARY=? WHERE NAME=?";

    private static final String SQL_TABLE_CREATE = "CREATE TABLE EMPLOYEE"
            + "("
            + " ID serial,"
            + " NAME varchar(100) NOT NULL,"
            + " SALARY numeric(15, 2) NOT NULL,"
            + " CREATED_DATE timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,"
            + " PRIMARY KEY (ID)"
            + ")";

    private static final String SQL_TABLE_DROP ="DROP TABLE EMPLOYEE";

}
