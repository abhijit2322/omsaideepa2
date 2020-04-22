/**
 * Generated on Wed Feb 19 10:37:37 UTC 2020 by ObjGen 3.0
 */
package abhijit.osdm2.models;
 
/**
 * Description for Admin class
 */
public class Admin {
 
   /** Property id */
   int id;
 
   /** Property adminname */
   String adminname;
 
   /** Property adminpassword */
   String adminpassword;
 
   /** Property mntnoticationsend */
   String mntnoticationsend;
   
   String admin_rule;
 
   /**
    * Constructor
    */
   public Admin() {
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
    * Gets the adminname
    */
   public String getAdminname() {
      return this.adminname;
   }
 
   /**
    * Sets the adminname
    */
   public void setAdminname(String value) {
      this.adminname = value;
   }
 
   /**
    * Gets the adminpassword
    */
   public String getAdminpassword() {
      return this.adminpassword;
   }
 
   /**
    * Sets the adminpassword
    */
   public void setAdminpassword(String value) {
      this.adminpassword = value;
   }
 
   /**
    * Gets the mntnoticationsend
    */
   public String getMntnoticationsend() {
      return this.mntnoticationsend;
   }
 
   /**
    * Sets the mntnoticationsend
    */
   public void setMntnoticationsend(String value) {
      this.mntnoticationsend = value;
   }

/**
 * @return the admin_rule
 */
public String getAdmin_rule() {
	return admin_rule;
}

/**
 * @param admin_rule the admin_rule to set
 */
public void setAdmin_rule(String admin_rule) {
	this.admin_rule = admin_rule;
}
}
