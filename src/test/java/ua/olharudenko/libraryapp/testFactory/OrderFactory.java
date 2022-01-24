package ua.olharudenko.libraryapp.testFactory;

import ua.olharudenko.libraryapp.enums.AdminOrderStatus;
import ua.olharudenko.libraryapp.enums.BillStatus;
import ua.olharudenko.libraryapp.enums.OrderStatus;
import ua.olharudenko.libraryapp.models.Book;
import ua.olharudenko.libraryapp.models.Order;
import ua.olharudenko.libraryapp.models.User;

import java.time.LocalDate;

public class OrderFactory {

    private final static Long ID = 10L;

    private final static Book BOOK = BookFactory.createBook();

    private final static User VISITOR = UserFactory.createVisitor();

    private final static OrderStatus ORDER_STATUS_SUBSCRIPTION = OrderStatus.SUBSCRIPTION;

    private final static AdminOrderStatus ADMIN_ORDER_STATUS = AdminOrderStatus.UNCONFIRMED;

    private final static LocalDate TAKED_DATE = LocalDate.of(2022, 01, 19);

    private final static LocalDate RETURN_DATE = LocalDate.of(2022, 02, 19);

    private final static int QUANTITY = 1;

    private final static double BILL = 0.0;

    private final static BillStatus BILL_STATUS = BillStatus.BLANK;

    public OrderFactory() {
    }

    public static Order createWithUnconfirmedStatusOrder(){
        return new Order(ID, BOOK, VISITOR, ORDER_STATUS_SUBSCRIPTION, ADMIN_ORDER_STATUS, TAKED_DATE, RETURN_DATE, QUANTITY, BILL, BILL_STATUS);
    }

    public static Order createOrderWithAdminStatus(AdminOrderStatus adminOrderStatus){
        return new Order(ID, BOOK, VISITOR, ORDER_STATUS_SUBSCRIPTION, adminOrderStatus, TAKED_DATE, RETURN_DATE, QUANTITY, BILL, BILL_STATUS);
    }

    public static Order createOrderWithOrderStatus(OrderStatus orderStatus){
        return new Order(ID, BOOK, VISITOR, orderStatus, ADMIN_ORDER_STATUS, TAKED_DATE, RETURN_DATE, QUANTITY, BILL, BILL_STATUS);
    }
}
