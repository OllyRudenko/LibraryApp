package ua.olharudenko.libraryapp.dao;

import ua.olharudenko.libraryapp.enums.AdminOrderStatus;
import ua.olharudenko.libraryapp.enums.BillStatus;
import ua.olharudenko.libraryapp.enums.OrderStatus;
import ua.olharudenko.libraryapp.models.Book;
import ua.olharudenko.libraryapp.models.Order;
import ua.olharudenko.libraryapp.utils.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDaoImpl implements ModelDAO<Order> {

    private BookDAOImpl bookDAO = new BookDAOImpl();

    private UserDAOImpl userDAO = new UserDAOImpl();

    @Override
    public Optional<Order> get(long id) throws SQLException {
        Order order = null;
        Connection connection = null;
        PreparedStatement pstatement = null;
        ResultSet resultSet = null;
        String sql = "select * from orders  where id = ?";
        try {
            connection = ConnectionPool.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            pstatement.setLong(1, id);
            if (pstatement.execute()) {
                resultSet = pstatement.executeQuery();
                while (resultSet.next()) {
                    order = new Order();
                    order.setId(resultSet.getLong("id"));
                    order.setBook(bookDAO.get(resultSet.getLong("book_id")).get());
                    order.setQuantity(resultSet.getInt("quantity"));
                    order.setUser(userDAO.get(resultSet.getInt("user_id")).get());
                    order.setOrderStatus(OrderStatus.valueOf(resultSet.getString("order_status")));
                    order.setAdminOrderStatus(AdminOrderStatus.valueOf(resultSet.getString("admin_order_status")));
                    order.setBill(resultSet.getDouble("bill"));
                    order.setBillStatus(BillStatus.valueOf(resultSet.getString("bill_status")));
                    if (resultSet.getTimestamp("taked_date") != null) {
                        order.setTakedDate(resultSet.getTimestamp("taked_date").toLocalDateTime().toLocalDate());
                        order.setReturnDate(resultSet.getTimestamp("return_date").toLocalDateTime().toLocalDate());
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLException("ORDER NOT FOUND BY ID", e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                pstatement.close();
            } catch (SQLException e) {
                throw new SQLException("get: ResultSet or PreparedStatement didn't close", e);
            }
        }
        return Optional.ofNullable(order);
    }

    //todo mayby shoud write: public Optional<List<Order>> getAll() ?
    @Override
    public List<Order> getAll() throws SQLException {
        List<Order> allOrders = new ArrayList<Order>();
        Connection connection = null;
        PreparedStatement pstatement = null;
        ResultSet resultSet = null;
        String sql = "select * from orders";
        try {
            connection = ConnectionPool.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            resultSet = pstatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getLong("id"));
                order.setBook(new Book());
                order.setBook(bookDAO.get(resultSet.getLong("book_id")).get());
                order.setQuantity(resultSet.getInt("quantity"));
                order.setUser(userDAO.get(resultSet.getInt("user_id")).get());
                order.setOrderStatus(OrderStatus.valueOf(resultSet.getString("order_status")));
                order.setAdminOrderStatus(AdminOrderStatus.valueOf(resultSet.getString("admin_order_status")));
                order.setBill(resultSet.getDouble("bill"));
                order.setBillStatus(BillStatus.valueOf(resultSet.getString("bill_status")));
                if (resultSet.getTimestamp("taked_date") != null) {
                    order.setTakedDate(resultSet.getTimestamp("taked_date").toLocalDateTime().toLocalDate());
                    order.setReturnDate(resultSet.getTimestamp("return_date").toLocalDateTime().toLocalDate());
                }
                allOrders.add(order);
            }
        } catch (SQLException e) {
            throw new SQLException("ORDERS NOT FOUND", e);
        } finally {
            try {
                resultSet.close();
                pstatement.close();
            } catch (SQLException e) {
                throw new SQLException("findAll(): ResultSet or PreparedStatement didn't close", e);
            }
        }
        return allOrders;
    }

    @Override
    public Order save(Order order) {
        Connection connection = null;
        PreparedStatement pstatement = null;
        String sql = "insert into orders(book_id, user_id, order_status, admin_order_status, quantity, bill, bill_status, taked_date, return_date) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = ConnectionPool.getInstance().getConn();
            pstatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstatement.setLong(1, order.getBook().getId());
            pstatement.setLong(2, order.getUser().getId());
            pstatement.setString(3, order.getOrderStatus().toString());
            pstatement.setString(4, order.getAdminOrderStatus().toString());
            pstatement.setInt(5, order.getQuantity());
            pstatement.setDouble(6, order.getBill());
            pstatement.setString(7, order.getBillStatus().toString());
            if (order.getTakedDate() != null) {
                pstatement.setTimestamp(8, Timestamp.valueOf(order.getTakedDate().atStartOfDay()));
                pstatement.setTimestamp(9, Timestamp.valueOf(order.getReturnDate().atStartOfDay()));
            }
            if (pstatement.executeUpdate() == 0) {
                throw new SQLException("Adding book to database failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pstatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Adding order to database failed, no ID obtained");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pstatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return order;
    }

    public Order makeOrder(Order order) {
        Connection connection = null;
        PreparedStatement pstatement = null;
        String sql = "insert into orders(book_id, user_id, order_status, admin_order_status, quantity, bill, bill_status) values (?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = ConnectionPool.getInstance().getConn();
            pstatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstatement.setLong(1, order.getBook().getId());
            pstatement.setLong(2, order.getUser().getId());
            pstatement.setString(3, order.getOrderStatus().toString());
            pstatement.setString(4, order.getAdminOrderStatus().toString());
            pstatement.setInt(5, order.getQuantity());
            pstatement.setDouble(6, order.getBill());
            pstatement.setString(7, order.getBillStatus().toString());
            if (pstatement.executeUpdate() == 0) {
                throw new SQLException("Adding book to database failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pstatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Adding order to database failed, no ID obtained");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pstatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return order;
    }

    @Override
    public void update(Order order) throws SQLException {
        Connection connection = null;
        PreparedStatement pstatement = null;
        String sql = "UPDATE orders SET book_id=?, quantity=?, user_id=?, order_status=?, admin_order_status=?, bill=?, bill_status=?, taked_date=?, return_date=? WHERE id =?";
        try {
            connection = ConnectionPool.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            if (get(order.getId()) != null) {
                pstatement.setLong(10, order.getId());
                pstatement.setLong(1, order.getBook().getId());
                pstatement.setInt(2, order.getQuantity());
                pstatement.setLong(3, order.getUser().getId());
                pstatement.setString(4, order.getOrderStatus().toString());
                pstatement.setString(5, order.getAdminOrderStatus().toString());
                pstatement.setDouble(6, order.getBill());
                pstatement.setString(7, order.getBillStatus().toString());
                if (order.getTakedDate() != null) {
                    pstatement.setTimestamp(8, Timestamp.valueOf(order.getTakedDate().atStartOfDay()));
                    pstatement.setTimestamp(9, Timestamp.valueOf(order.getReturnDate().atStartOfDay()));
                }
                pstatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException("ORDER DIDN'T UPDATE");
        } finally {
            try {
                pstatement.close();
            } catch (SQLException e) {
                throw new SQLException("update: PreparedStatement didn't close", e);
            }
        }
    }

    @Override
    public void delete(Order order) throws SQLException {
        Connection connection = null;
        PreparedStatement pstatement = null;
        String sql = "delete from orders where id = ?";
        try {
            connection = ConnectionPool.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            if (get(order.getId()) != null) {
                pstatement.setLong(1, order.getId());
                pstatement.executeUpdate();
            } else {
                throw new SQLException("ORDER DIDN'T DELETE");
            }
        } catch (SQLException e) {
            throw new SQLException("ORDER DIDN'T DELETE");
        } finally {
            try {
                pstatement.close();
            } catch (SQLException e) {
                throw new SQLException("delete: PreparedStatement didn't close", e);
            }
        }
    }


    public List<Order> getAllByUser(Long id) throws SQLException {
        List<Order> allOrders = new ArrayList<Order>();
        Connection connection = null;
        PreparedStatement pstatement = null;
        ResultSet resultSet = null;
        String sql = "select * from orders where user_id = ?";
        try {
            connection = ConnectionPool.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            pstatement.setLong(1, id);
            resultSet = pstatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getLong("id"));
                order.setBook(new Book());
                order.setBook(bookDAO.get(resultSet.getLong("book_id")).get());
                order.setQuantity(resultSet.getInt("quantity"));
                order.setUser(userDAO.get(resultSet.getInt("user_id")).get());
                order.setOrderStatus(OrderStatus.valueOf(resultSet.getString("order_status")));
                order.setAdminOrderStatus(AdminOrderStatus.valueOf(resultSet.getString("admin_order_status")));
                order.setBill(resultSet.getDouble("bill"));
                order.setBillStatus(BillStatus.valueOf(resultSet.getString("bill_status")));
                if (resultSet.getTimestamp("taked_date") != null) {
                    order.setTakedDate(resultSet.getTimestamp("taked_date").toLocalDateTime().toLocalDate());
                    order.setReturnDate(resultSet.getTimestamp("return_date").toLocalDateTime().toLocalDate());
                }
                allOrders.add(order);
            }
        } catch (SQLException e) {
            throw new SQLException("ORDERS NOT FOUND", e);
        } finally {
            try {
                pstatement.close();
            } catch (SQLException e) {
                throw new SQLException("findAll():PreparedStatement didn't close", e);
            }
        }
        if (allOrders.size() == 0) {
            throw new SQLException("table ORDERS is empty");
        }
        return allOrders;
    }


    public List<Order> getConfirmedOrdersByUser(Long id, String adminOrderStatus) throws SQLException {
        List<Order> allOrders = new ArrayList<Order>();
        Connection connection = null;
        PreparedStatement pstatement = null;
        ResultSet resultSet = null;
        String sql = "select * from orders where user_id = ? and admin_order_status = ?";
        try {
            connection = ConnectionPool.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            pstatement.setLong(1, id);
            pstatement.setString(2, adminOrderStatus);
            resultSet = pstatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getLong("id"));
                order.setBook(new Book());
                order.setBook(bookDAO.get(resultSet.getLong("book_id")).get());
                order.setQuantity(resultSet.getInt("quantity"));
                order.setUser(userDAO.get(resultSet.getInt("user_id")).get());
                order.setOrderStatus(OrderStatus.valueOf(resultSet.getString("order_status")));
                order.setAdminOrderStatus(AdminOrderStatus.valueOf(resultSet.getString("admin_order_status")));
                order.setBill(resultSet.getDouble("bill"));
                order.setBillStatus(BillStatus.valueOf(resultSet.getString("bill_status")));
                if (resultSet.getTimestamp("taked_date") != null) {
                    order.setTakedDate(resultSet.getTimestamp("taked_date").toLocalDateTime().toLocalDate());
                    order.setReturnDate(resultSet.getTimestamp("return_date").toLocalDateTime().toLocalDate());
                }
                allOrders.add(order);
            }
        } catch (SQLException e) {
            throw new SQLException("ORDERS NOT FOUND", e);
        } finally {
            try {
                pstatement.close();
            } catch (SQLException e) {
                throw new SQLException("findAll():PreparedStatement didn't close", e);
            }
        }
        if (allOrders.size() == 0) {
            throw new SQLException("table ORDERS is empty");
        }
        return allOrders;
    }
}
