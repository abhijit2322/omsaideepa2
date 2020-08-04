package abhijit.osdm_wop.models;

public class Passbook_model {
int id;
String date ;
String flat_number;
String amount;
String paid_to;
String apartment_id;


public Passbook_model(int id, String date, String flat_number, String amount, String paid_to, String apartment_id) {
	super();
	this.id = id;
	this.date = date;
	this.flat_number = flat_number;
	this.amount = amount;
	this.paid_to = paid_to;
	this.apartment_id = apartment_id;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getFlat_number() {
	return flat_number;
}
public void setFlat_number(String flat_number) {
	this.flat_number = flat_number;
}
public String getAmount() {
	return amount;
}
public void setAmount(String amount) {
	this.amount = amount;
}
public String getPaid_to() {
	return paid_to;
}
public void setPaid_to(String paid_to) {
	this.paid_to = paid_to;
}
public String getApartment_id() {
	return apartment_id;
}
public void setApartment_id(String apartment_id) {
	this.apartment_id = apartment_id;
}

}
