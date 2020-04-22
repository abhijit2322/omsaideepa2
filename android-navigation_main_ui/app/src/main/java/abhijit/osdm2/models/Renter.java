/**
 * Generated on Wed Feb 19 10:20:05 UTC 2020 by ObjGen 3.0
 */
package abhijit.osdm2.models;
 
/**
 * Description for Renter class
 */
public class Renter {
 
   /** Property id */
   int id;
 
   /** Property rentername */
   String rentername;
 
   /** Property flatnumber */
   String flatnumber;
 
   /** Property rentercontactno */
   String rentercontactno;
 
   /** Property rmaintaincepaid */
   String rmaintaincepaid;
   
   String email;
 
   /**
    * Constructor
    */
   public Renter() {
   }
 
   
   public Renter(int id, String rentername, String flatnumber, String rentercontactno, String rmaintaincepaid, String email) {
       this.id = id;
       this.rentername = rentername;
       this.flatnumber = flatnumber;
       this.rentercontactno = rentercontactno;
       this.rmaintaincepaid = rmaintaincepaid;
       this.email = email;
   }
   /**
    * Gets the id
    */
   public int getId() {
      return this.id;
   }
 
   /**
    * Sets the id
    */
   public void setId(int value) {
      this.id = value;
   }
 
   /**
    * Gets the rentername
    */
   public String getRentername() {
      return this.rentername;
   }
 
   /**
    * Sets the rentername
    */
   public void setRentername(String value) {
      this.rentername = value;
   }
 
   /**
    * Gets the flatnumber
    */
   public String getFlatnumber() {
      return this.flatnumber;
   }
 
   /**
    * Sets the flatnumber
    */
   public void setFlatnumber(String value) {
      this.flatnumber = value;
   }
 
   /**
    * Gets the rentercontactno
    */
   public String getRentercontactno() {
      return this.rentercontactno;
   }
 
   /**
    * Sets the rentercontactno
    */
   public void setRentercontactno(String value) {
      this.rentercontactno = value;
   }
 
   /**
    * Gets the rmaintaincepaid
    */
   public String getRmaintaincepaid() {
      return this.rmaintaincepaid;
   }
 
   /**
    * Sets the rmaintaincepaid
    */
   public void setRmaintaincepaid(String value) {
      this.rmaintaincepaid = value;
   }
   
   public String getEmail() {
	      return this.email;
	   }
	 
	   /**
	    * Sets the maintaincepaid
	    */
	   public void setEmail(String value) {
	      this.email = value;
	   }
}
