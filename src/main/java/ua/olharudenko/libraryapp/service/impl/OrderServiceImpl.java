package ua.olharudenko.libraryapp.service.impl;

import ua.olharudenko.libraryapp.dao.BookDAOImpl;
import ua.olharudenko.libraryapp.dao.OrderDaoImpl;
import ua.olharudenko.libraryapp.dao.UserDAOImpl;
import ua.olharudenko.libraryapp.enums.AdminOrderStatus;
import ua.olharudenko.libraryapp.enums.OrderStatus;
import ua.olharudenko.libraryapp.enums.Role;
import ua.olharudenko.libraryapp.models.Book;
import ua.olharudenko.libraryapp.models.Order;
import ua.olharudenko.libraryapp.service.OrderService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    OrderDaoImpl orderDao = new OrderDaoImpl();
    BookDAOImpl bookDAO = new BookDAOImpl();
    UserDAOImpl userDAO = new UserDAOImpl();

    @Override
    public List<Order> makeOrder(Long userId, List<Long> bookIds, OrderStatus orderStatus, int quantity) {
        bookIds.stream().forEach(i ->
        {
            try {
                var createdOrder = orderDao.makeOrder(createOrder(userId, i, orderStatus, quantity));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        List<Order> orders = null;
        try {
            orders = orderDao.getAllByUser(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private Order createOrder(Long userId, Long bookId, OrderStatus orderStatus, int quantity) throws SQLException {
        return new Order(bookDAO.get(bookId).get(), userDAO.get(userId).get(), orderStatus, quantity);
    }

    @Override
    public List<Order> changeAdminOrderStatus(List<Long> orderIds, Role userRole) {
        List<Order> orders = new ArrayList<Order>();
        if (userRole.equals(Role.ADMIN) || userRole.equals(Role.LIBRARIAN)) {
            orderIds.stream().forEach(i -> adminOrdering(i));
            try {
                orders = orderDao.getAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return orders;
    }

    private void adminOrdering(Long orderId) {
        try {
            var order = orderDao.get(orderId);

            if (order.isPresent() && order.get().getAdminOrderStatus().equals(AdminOrderStatus.UNCONFIRMED)) {
                order.get().setAdminOrderStatus(AdminOrderStatus.CONFIRMED);
                order.get().setTakedDate(LocalDate.now());
                order.get().setReturnDate(LocalDate.now().plusDays(30));
                orderDao.update(order.get());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //todo back to front
    private int calculateBookItems(Long bookId, int quantity) {
        Optional<Book> book = null;
        int items = 0;
        try {
            book = bookDAO.get(bookId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (book.isPresent()) {
            items = book.get().getItems();
        }
        return items;
    }

    @Override
    public List<Order> getAllOrders(Long userId, Role role) throws SQLException {
        if (role.equals(Role.VISITOR)) {
            return orderDao.getAllByUser(userId);
        } else {
            return orderDao.getAll();
        }
    }

    @Override
    public List<Order> getConfirmedOrders(Long userId, Role role) throws SQLException {
        if (role.equals(Role.VISITOR)) {
            return orderDao.getConfirmedOrdersByUser(userId, AdminOrderStatus.CONFIRMED.name());
        } else {
            return orderDao.getAll();
        }
    }

    public List<Order> getConfirmedOrdersBy(LocalDate stDate, LocalDate eDate, Long userId, Role role) throws SQLException {
        List<Order> orders = new ArrayList<>();
        List<Order> sortedOrders = new ArrayList<>();
        if (role.equals(Role.VISITOR)) {
            orders = orderDao.getConfirmedOrdersByUser(userId, AdminOrderStatus.CONFIRMED.name());

            for (Order o : orders) {
                if((o.getTakedDate().isAfter(stDate) || o.getTakedDate().isEqual(stDate))
                && (o.getTakedDate().isBefore(eDate) || o.getTakedDate().isEqual(eDate))){
                    sortedOrders.add(o);
                }
            }
        }
        return sortedOrders;
    }

    @Override
    public Boolean delete(Order order, Role userRole) throws SQLException {
        if (userRole.equals(Role.VISITOR)
                && order.getAdminOrderStatus().equals(AdminOrderStatus.UNCONFIRMED)) {
            orderDao.delete(order);
            return true;
        } else if (userRole.equals(Role.ADMIN) || userRole.equals(Role.LIBRARIAN)) {
            orderDao.delete(order);
            return true;
        }
        return false;
    }
}
