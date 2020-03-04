/**
 * Generated on Wed Feb 19 10:34:59 UTC 2020 by ObjGen 3.0
 */
package net.simplifiedcoding.navigationdrawerexample.models;
 
/**
 * Description for MonthlyExpence class
 */
public class MonthlyExpence {
 
   /** Property id */
   int id;
 
   /** Property itemname */
   String itemname;
 
   /** Property itemexpense */
   String itemexpense;
 
   /** Property credit */
   String credit;
 
   /** Property debit */
   String debit;
 
   /** Property month */
   String month;
   
   /** Property Total Expanse */
   String totalexp;
 
   /**
    * Constructor
    */
   public MonthlyExpence() {
   }
 
   
   public MonthlyExpence(int id, String itemname, String itemexpense, String credit, String debit, String month,String totalexp) {
       this.id = id;
       this.itemname = itemname;
       this.itemexpense = itemexpense;
       this.credit = credit;
       this.debit = debit;
       this.month = month;
       this.totalexp = totalexp;
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
    * Gets the itemname
    */
   public String getItemname() {
      return this.itemname;
   }
 
   /**
    * Sets the itemname
    */
   public void setItemname(String value) {
      this.itemname = value;
   }
 
   /**
    * Gets the itemexpense
    */
   public String getItemexpense() {
      return this.itemexpense;
   }
 
   /**
    * Sets the itemexpense
    */
   public void setItemexpense(String value) {
      this.itemexpense = value;
   }
 
   /**
    * Gets the credit
    */
   public String getCredit() {
      return this.credit;
   }
 
   /**
    * Sets the credit
    */
   public void setCredit(String value) {
      this.credit = value;
   }
 
   /**
    * Gets the debit
    */
   public String getDebit() {
      return this.debit;
   }
 
   /**
    * Sets the debit
    */
   public void setDebit(String value) {
      this.debit = value;
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
 * @return the totalexp
 */
public String getTotalexp() {
	return totalexp;
}

/**
 * @param totalexp the totalexp to set
 */
public void setTotalexp(String totalexp) {
	this.totalexp = totalexp;
}
}
