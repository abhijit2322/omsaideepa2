package abhijit.osdm_wop.models;

public class FirebaseChildDataStore {



    String Key;
    ComplainBoxModel complainBoxModel;

    public FirebaseChildDataStore(){}

    public FirebaseChildDataStore(String key, ComplainBoxModel complainBoxModel) {
        Key = key;
        this.complainBoxModel = complainBoxModel;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }



    public ComplainBoxModel getComplainBoxModel() {
        return complainBoxModel;
    }

    public void setComplainBoxModel(ComplainBoxModel complainBoxModel) {
        this.complainBoxModel = complainBoxModel;
    }

}
