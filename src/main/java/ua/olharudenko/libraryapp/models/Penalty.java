package ua.olharudenko.libraryapp.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class Penalty extends BaseEntity {

    private Long id;

    private Long userId;

    private Order order;

    private double summ;

    public Penalty() {
    }

    public Penalty(Long userId, Order order, double summ) {
        this.userId = userId;
        this.order = order;
        this.summ = summ;
    }

    public Penalty(Long id, Long userId, Order order, double summ) {
        this.id = id;
        this.userId = userId;
        this.order = order;
        this.summ = summ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public double getSumm() {
        return summ;
    }

    public void setSumm(double summ) {
        this.summ = summ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Penalty penalty = (Penalty) o;
        return Double.compare(penalty.summ, summ) == 0 && Objects.equals(id, penalty.id) && Objects.equals(userId, penalty.userId) && Objects.equals(order, penalty.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, order, summ);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("createdDate", createdDate)
                .append("updatedDate", updatedDate)
                .append("id", id)
                .append("userId", userId)
                .append("order", order)
                .append("summ", summ)
                .toString();
    }
}
