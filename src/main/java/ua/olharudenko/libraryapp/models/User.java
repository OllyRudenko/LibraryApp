package ua.olharudenko.libraryapp.models;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ua.olharudenko.libraryapp.enums.Role;

import java.util.List;
import java.util.Objects;

public class User extends BaseEntity {

    private Long id;

    private String firstName;

    private String lastName;

    private Role role;

    private String email;

    private String password;

    private String phone;

    private String adress;

    private List<Order> orders;

    public User() {
    }

    public User(String firstName, String lastName, Role role, String email, String password, String phone, String adress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.adress = adress;
    }

    public User(Long id, String firstName, String lastName, Role role, String email, String password, String phone, String adress, List<Order> orders) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.adress = adress;
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && role == user.role && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(phone, user.phone) && Objects.equals(adress, user.adress) && Objects.equals(orders, user.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, role, email, password, phone, adress, orders);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("createdDate", createdDate)
                .append("updatedDate", updatedDate)
                .append("id", id)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("role", role)
                .append("email", email)
                .append("password", password)
                .append("phone", phone)
                .append("adress", adress)
                .append("orders", orders)
                .toString();
    }
}
