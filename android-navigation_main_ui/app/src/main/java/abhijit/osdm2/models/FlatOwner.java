/**
 * Generated on Wed Feb 19 10:15:44 UTC 2020 by ObjGen 3.0
 */
package abhijit.osdm2.models;
 
/**
 * Description for MyClass
 */
public class FlatOwner {
 
   /** Property id */
   int id;
 
   /** Property ownername */
   String ownername;
 
   /** Property flatnumber */
   String flatnumber;
 
   /** Property ownercontactno */
   String ownercontactno;
 
   /** Property maintaincepaid */
   String maintaincepaid;
   
   String email;
   String isrented;

   String mntcaretaker;

   public String getIsrented() {
      return isrented;
   }

   public void setIsrented(String isrented) {
      this.isrented = isrented;
   }

   public String getMntcaretaker() {
      return mntcaretaker;
   }

   public void setMntcaretaker(String mntcaretaker) {
      this.mntcaretaker = mntcaretaker;
   }


 
   /**
    * Constructor
    */
   public FlatOwner() {
   }
   
   public FlatOwner(int id, String ownername, String flatnumber, String ownercontactno, String maintaincepaid, String email,String isrented,String mntcaretaker) {
       this.id = id;
       this.ownername = ownername;
       this.flatnumber = flatnumber;
       this.ownercontactno = ownercontactno;
       this.maintaincepaid = maintaincepaid;
       this.email = email;
       this.isrented=isrented;
       this.mntcaretaker=mntcaretaker;
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
    * Gets the ownername
    */
   public String getOwnername() {
      return this.ownername;
   }
 
   /**
    * Sets the ownername
    */
   public void setOwnername(String value) {
      this.ownername = value;
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
    * Gets the ownercontactno
    */
   public String getOwnercontactno() {
      return this.ownercontactno;
   }
 
   /**
    * Sets the ownercontactno
    */
   public void setOwnercontactno(String value) {
      this.ownercontactno = value;
   }
 
   /**
    * Gets the maintaincepaid
    */
   public String getMaintaincepaid() {
      return this.maintaincepaid;
   }
 
   /**
    * Sets the maintaincepaid
    */
   public void setMaintaincepaid(String value) {
      this.maintaincepaid = value;
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
