package abhijit.osdm_wop.models;

public class ImageDB {
	 
	 

	/** Property Id */
	   int Id;
	 
	   /** Property imagename */
	   String imagename;
	 
	   /** Property imagedata */
	   String imagedata;
	 
	   /** Property month */
	   String month;
	 
	   /**
	    * Constructor
	    */
	   String apartmentid;
	/**
	 * @return the apartmentid
	 */
	public String getApartmentid() {
		return apartmentid;
	}


	/**
	 * @param apartmentid the apartmentid to set
	 */
	public void setApartmentid(String apartmentid) {
		this.apartmentid = apartmentid;
	}
	   public ImageDB() {
	   }
	 
	   
	   public ImageDB(int id, String imagename, String imagedata, String month,String apartmentid ) {
			super();
			Id = id;
			this.imagename = imagename;
			this.imagedata = imagedata;
			this.month = month;
		   this.apartmentid = apartmentid;
		}
	   
	   
	   /**
	    * Gets the Id
	    */
	   public int getId() {
	      return this.Id;
	   }
	 
	   /**
	    * Sets the Id
	    */
	   public void setId(int value) {
	      this.Id = value;
	   }
	 
	   /**
	    * Gets the imagename
	    */
	   public String getImagename() {
	      return this.imagename;
	   }
	 
	   /**
	    * Sets the imagename
	    */
	   public void setImagename(String value) {
	      this.imagename = value;
	   }
	 
	   /**
	    * Gets the imagedata
	    */
	   public String getImagedata() {
	      return this.imagedata;
	   }
	 
	   /**
	    * Sets the imagedata
	    */
	   public void setImagedata(String value) {
	      this.imagedata = value;
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
	}