package proiect.fis.CC.model;

public class User {

    private String username;
    private String password;
    private String email;
    private String address;
    private String hotelName;
    private String role;

    public User(String username, String password, String email, String address, String hotelName, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.hotelName = hotelName;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getRole() {
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        User user = (User) obj;

        if (!username.equals(user.username)) return false;
        if (!password.equals(user.password)) return false;
        return role.equals(user.role);    }
}
