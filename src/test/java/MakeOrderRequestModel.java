import java.util.List;

public class MakeOrderRequestModel {
    public String firstName;
    public String lastName;
    public String address;
    public int metroStation;
    public String phone;
    public int rentTime;
    public String deliveryDate;
    public String comment;
    public List<String> color;

    public MakeOrderRequestModel(String firstName, String lastName, String address, int metroStation,
                                 String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public String getFirstName() {

        return firstName;
    }

    public String getLastName() {

        return lastName;
    }

    public String getAddress() {

        return address;
    }

    public int getMetroStation() {

        return metroStation;
    }

    public String getPhone() {

        return phone;
    }

    public int getRentTime() {

        return rentTime;
    }

    public String getDeliveryDate() {

        return deliveryDate;
    }

    public String getComment() {

        return comment;
    }

    public List<String> getColor() {

        return color;
    }
}
