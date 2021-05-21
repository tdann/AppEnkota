package tdannandjuliet.assignment.enkotaApp.helper;

public class User {
    private int id;
    private String email;
    private String password;
    private String phone;
    private String user_role;
    private String buyer_id;
    private String farmer_id;

    public User(int id, String email, String password, String phone, String user_role, String buyer_id, String farmer_id) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.user_role = user_role;
        this.buyer_id = buyer_id;
        this.farmer_id = farmer_id;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getUser_role() {
        return user_role;
    }

    public String getBuyer_id() {
        return buyer_id;
    }

    public String getFarmer_id() {
        return farmer_id;
    }
}
