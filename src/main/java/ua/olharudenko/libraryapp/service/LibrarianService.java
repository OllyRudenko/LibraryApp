package ua.olharudenko.libraryapp.service;

import ua.olharudenko.libraryapp.enums.OrderStatus;
import ua.olharudenko.libraryapp.models.Order;

import java.util.List;

public interface LibrarianService {

    public void confirmOrder(Order order);

    public List<Order> getAllOrders();

    public List<Order> getOrdersWithStatus(OrderStatus orderStatus);

    public void cancelOrder(Order order);

    public void deleteOrder(Order order);
}
