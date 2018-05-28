package in.yefindia.yef.model;

public class BloodDonation {

    private String ID,City,Location,Date,Time,Contact;



    public BloodDonation(String ID,String city, String location, String date, String time, String contact) {
        this.ID=ID;
        City = city;
        Location = location;
        Date = date;
        Time = time;
        Contact = contact;
    }



    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
