package abhijit.osdm_wop.models;

public class SuppTable {

    /** Property id */
    int id;

    /** Property type */
    String type;

    /** Property name */
    String name;



    /** Property cphoneno */
    String cphoneno;

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

    public SuppTable() {
    }


    public SuppTable(int id, String type, String name, String cphoneno,String apartmentid) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.cphoneno = cphoneno;
        this.apartmentid = apartmentid;
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
     * Gets the type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Sets the type
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the cphoneno
     */
    public String getCphoneno() {
        return this.cphoneno;
    }

    /**
     * Sets the cphoneno
     */
    public void setCphoneno(String value) {
        this.cphoneno = value;
    }
}
