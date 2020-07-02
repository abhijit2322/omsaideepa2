package abhijit.osdm_wop.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties
public class ComplainBoxModel {

    String name;
    String flatnumber;
    String type;
    String desc;
    String key;
    String status;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }






    public ComplainBoxModel(){}

    public ComplainBoxModel(String name, String flatnumber, String type, String desc,String status,String key) {
        this.name = name;
        this.flatnumber = flatnumber;
        this.type = type;
        this.desc = desc;
        this.status=status;
        this.key=key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlatnumber() {
        return flatnumber;
    }

    public void setFlatnumber(String flatnumber) {
        this.flatnumber = flatnumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("flatnumber", flatnumber);
        result.put("type", type);
        result.put("desc", desc);
        result.put("status", status);

        return result;
    }
}
