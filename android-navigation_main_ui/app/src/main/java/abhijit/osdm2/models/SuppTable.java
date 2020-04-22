package abhijit.osdm2.models;

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
    public SuppTable() {
    }


    public SuppTable(int id, String type, String name, String cphoneno) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.cphoneno = cphoneno;
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
