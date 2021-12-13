package ua.olharudenko.libraryapp.models;

import org.apache.commons.lang3.builder.ToStringBuilder;
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

    private int amountDays;

    private Penalty penalty;

    public Order() {
    }

    public Order(Book book, User user, OrderStatus orderStatus, LocalDate takedDate, LocalDate returnDate, int amountDays, Penalty penalty) {
        this.book = book;
        this.user = user;
        this.orderStatus = orderStatus;
        this.takedDate = takedDate;
        this.returnDate = returnDate;
        this.amountDays = amountDays;
        this.penalty = penalty;
    }

    public Order(Long id, Book book, User user, OrderStatus orderStatus, LocalDate takedDate, LocalDate returnDate, int amountDays, Penalty penalty) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.orderStatus = orderStatus;
        this.takedDate = takedDate;
        this.returnDate = returnDate;
        this.amountDays = amountDays;
        this.penalty = penalty;
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

    public int getAmountDays() {
        return amountDays;
    }

    public void setAmountDays(int amountDays) {
        this.amountDays = amountDays;
    }

    public Penalty getPenalty() {
        return penalty;
    }

    public void setPenalty(Penalty penalty) {
        this.penalty = penalty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return amountDays == order.amountDays && Objects.equals(id, order.id) && Objects.equals(book, order.book) && Objects.equals(user, order.user) && orderStatus == order.orderStatus && Objects.equals(takedDate, order.takedDate) && Objects.equals(returnDate, order.returnDate) && Objects.equals(penalty, order.penalty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, book, user, orderStatus, takedDate, returnDate, amountDays, penalty);
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
                .append("amountDays", amountDays)
                .append("penalty", penalty)
                .toString();
    }
}
