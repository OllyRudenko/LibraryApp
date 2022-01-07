package ua.olharudenko.libraryapp.service;

import ua.olharudenko.libraryapp.enums.OrderStatus;
import ua.olharudenko.libraryapp.enums.Role;
import ua.olharudenko.libraryapp.models.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {

    public List<Order> makeOrder(Long userId, List<Long> bookIds, OrderStatus orderStatus, int quantity);

    public List<Order>changeAdminOrderStatus(List<Long> orderIds, Role userRole);

    public List<Order> getAllOrders(Long userId, Role role) throws SQLException;

    Boolean delete(Order order, Long userId) throws SQLException;
}
