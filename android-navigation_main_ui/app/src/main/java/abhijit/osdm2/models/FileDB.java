package abhijit.osdm2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileDB {
	 
	   /** Property id */
	   @SerializedName("id")
	   @Expose
	   int id;
	 
	 	/** Property filename */
		@SerializedName("filename")
		@Expose
	   String filename;
	 
	   /** Property filetype */
	   @SerializedName("filetype")
	   @Expose
	   String filetype;
	 
	   /** Property filedata */
	   @SerializedName("filedata")
	   @Expose
	   byte[] filedata;
	 
	   /** Property month */
	   @SerializedName("month")
	   @Expose
	   String month;



	@SerializedName("stringdata")
	@Expose
	String stringdata;
	   /**
	    * Constructor
	    */
	   public FileDB() {
	   }
	   
	   
	   public FileDB(int id, String filename, String filetype, byte[] filedata, String month,String stringdata) {
			super();
			this.id = id;
			this.filename = filename;
			this.filetype = filetype;
			this.filedata = filedata;
			this.month = month;
			this.stringdata=stringdata;
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
	    * Gets the filename
	    */
	   public String getFilename() {
	      return this.filename;
	   }
	 
	   /**
	    * Sets the filename
	    */
	   public void setFilename(String value) {
	      this.filename = value;
	   }
	 
	   /**
	    * Gets the filetype
	    */
	   public String getFiletype() {
	      return this.filetype;
	   }
	 
	   /**
	    * Sets the filetype
	    */
	   public void setFiletype(String value) {
	      this.filetype = value;
	   }
	 
	   /**
	    * Gets the filedata
	    */
	   public byte[] getFiledata() {
	      return this.filedata;
	   }
	 
	   /**
	    * Sets the filedata
	    */
	   public void setFiledata(byte[] value) {
	      this.filedata = value;
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

	public void setStringdata(String stringdata) {
		this.stringdata = stringdata;
	}

	public String getStringdata() {
		return stringdata;
	}
	}