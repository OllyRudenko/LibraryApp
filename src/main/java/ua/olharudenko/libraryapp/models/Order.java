package ua.olharudenko.libraryapp.models;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ua.olharudenko.libraryapp.enums.BillStatus;
import ua.olharudenko.libraryapp.enums.OrderStatus;

import java.time.LocalDate;
import java.util.Objects;

public class Order extends BaseEntity {

    private Long id;

    private Book book;

    private User user;

    private OrderStatus orderStatus = OrderStatus.UNCONFIRMED;

    private LocalDate takedDate;

    private LocalDate returnDate;

    private int quantity;
// todo change to double
    private int bill;

    private BillStatus billStatus;

    public Order() {
    }

    public Order(Book book, User user, OrderStatus orderStatus, LocalDate takedDate, LocalDate returnDate, int quantity, int bill, BillStatus billStatus) {
        this.book = book;
        this.user = user;
        this.orderStatus = orderStatus;
        this.takedDate = takedDate;
        this.returnDate = returnDate;
        this.quantity = quantity;
        this.bill = bill;
        this.billStatus = billStatus;
    }

    public Order(Long id, Book book, User user, OrderStatus orderStatus, LocalDate takedDate, LocalDate returnDate, int quantity, int bill, BillStatus billStatus) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.orderStatus = orderStatus;
        this.takedDate = takedDate;
        this.returnDate = returnDate;
        this.quantity = quantity;
        this.bill = bill;
        this.billStatus = billStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDate getTakedDate() {
        return takedDate;
    }

    public void setTakedDate(LocalDate takedDate) {
        this.takedDate = takedDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getBill() {
        return bill;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }

    public BillStatus getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(BillStatus billStatus) {
        this.billStatus = billStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return quantity == order.quantity && Objects.equals(id, order.id) && Objects.equals(book, order.book) && Objects.equals(user, order.user) && orderStatus == order.orderStatus && Objects.equals(takedDate, order.takedDate) && Objects.equals(returnDate, order.returnDate) && Objects.equals(bill, order.bill) && Objects.equals(billStatus, order.billStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, book, user, orderStatus, takedDate, returnDate, quantity, bill, billStatus);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("createdDate", createdDate)
                .append("updatedDate", updatedDate)
                .append("id", id)
                .append("book", book)
                .append("user", user)
                .append("orderStatus", orderStatus)
                .append("takedDate", takedDate)
                .append("returnDate", returnDate)
                .append("quantity", quantity)
                .append("bill", bill)
                .append("billStatus", billStatus)
                .toString();
    }
}
