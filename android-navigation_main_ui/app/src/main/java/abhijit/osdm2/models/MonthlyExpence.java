/**
 * Generated on Thu Mar 05 06:45:37 UTC 2020 by ObjGen 3.0
 */
package abhijit.osdm2.models;

import java.util.List;

/**
 * Description for MonthlyExpence
 */
public class MonthlyExpence {
 
   /** Property itemname */
   List<String> itemname;
 
   /** Property itemcost */
   List<String> itemcost;
 
   /** Property startbal */
   String startbal;
 
   /** Property totalexpence */
   String totalexpence;
 
   /** Property remainingbal */
   String remainingbal;
 
   /** Property month */
   String month;
 
   /** Property id */
   int id;
 
   /**
    * Constructor
    */
   public MonthlyExpence() {
   }
 
   /**
    * Gets the itemname
    */
   public List<String> getItemname() {
      return this.itemname;
   }
 
   /**
    * Sets the itemname
    */
   public void setItemname(List<String> value) {
      this.itemname = value;
   }
 
   /**
    * Gets the itemcost
    */
   public List<String> getItemcost() {
      return this.itemcost;
   }
 
   /**
    * Sets the itemcost
    */
   public void setItemcost(List<String> value) {
      this.itemcost = value;
   }
 
   /**
    * Gets the startbal
    */
   public String getStartbal() {
      return this.startbal;
   }
 
   /**
    * Sets the startbal
    */
   public void setStartbal(String value) {
      this.startbal = value;
   }
 
   /**
    * Gets the totalexpence
    */
   public String getTotalexpence() {
      return this.totalexpence;
   }
 
   /**
    * Sets the totalexpence
    */
   public void setTotalexpence(String value) {
      this.totalexpence = value;
   }
 
   /**
    * Gets the remainingbal
    */
   public String getRemainingbal() {
      return this.remainingbal;
   }
 
   /**
    * Sets the remainingbal
    */
   public void setRemainingbal(String value) {
      this.remainingbal = value;
   }
 
   /**
    * Gets the month
    */
   public String getMonth() {
      return this.month;
   }
 
   /**
    * Sets the month
    */
   public void setMonth(String value) {
      this.month = value;
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
}