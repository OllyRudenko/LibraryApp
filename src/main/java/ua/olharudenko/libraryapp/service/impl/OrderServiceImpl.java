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
        if(userRole.equals(Role.ADMIN) || userRole.equals(Role.LIBRARIAN)) {
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
    private int calculateBookItems(Long bookId, int quantity){
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
    public Boolean delete(Order order, Long userId) throws SQLException {
        if (userDAO.get(userId).get().getRole().equals(Role.VISITOR)
                && order.getAdminOrderStatus().equals(AdminOrderStatus.UNCONFIRMED)) {
            orderDao.delete(order);
            return true;
        } else if (userDAO.get(userId).get().getRole().equals(Role.ADMIN) || userDAO.get(userId).get().getRole().equals(Role.LIBRARIAN)) {
            orderDao.delete(order);
            return true;
        }
        return false;
    }
}
