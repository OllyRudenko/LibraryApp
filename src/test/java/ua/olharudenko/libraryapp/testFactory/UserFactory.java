package ua.olharudenko.libraryapp.testFactory;

import ua.olharudenko.libraryapp.enums.Role;
import ua.olharudenko.libraryapp.models.Order;
import ua.olharudenko.libraryapp.models.User;

import java.util.List;

public class UserFactory {

    private final static Long ID = 10L;

    private final static String FIRST_NAME = "Name";

    private final static String LAST_NAME = "Surname";

    private final static Role ROLE_ADMIN = Role.ADMIN;

    private final static Role ROLE_VISITOR = Role.VISITOR;

    private final static Role ROLE_LIBRARIAN = Role.LIBRARIAN;

    private final static String EMAIL = "user@gmail.com";

    private final static String EMAIL_ADMIN = "admin@gmail.com";

    private final static String EMAIL_LIBRARIAN = "librarian@gmail.com";

    private final static String PASSWORD = "useR2022";

    private final static String PHONE = "380997774545";

    private final static String ADRESS = "Adress";

    private final static List<Order> ORDERS = List.of(OrderFactory.createWithUnconfirmedStatusOrder());

    public UserFactory() {
    }

    public static User createAdmin() {
        return new User(ID, FIRST_NAME, LAST_NAME, ROLE_ADMIN, EMAIL_ADMIN, PASSWORD, PHONE, ADRESS, ORDERS);
    }

    public static User createVisitor() {
        return new User(ID, FIRST_NAME, LAST_NAME, ROLE_VISITOR, EMAIL, PASSWORD, PHONE, ADRESS, ORDERS);
    }

    public static User createLibrarian() {
        return new User(ID, FIRST_NAME, LAST_NAME, ROLE_LIBRARIAN, EMAIL_LIBRARIAN, PASSWORD, PHONE, ADRESS, ORDERS);
    }
}
