package ua.olharudenko.libraryapp.dao;

import ua.olharudenko.libraryapp.enums.BillStatus;
import ua.olharudenko.libraryapp.enums.OrderStatus;
import ua.olharudenko.libraryapp.models.Book;
import ua.olharudenko.libraryapp.models.Order;
import ua.olharudenko.libraryapp.models.User;
import ua.olharudenko.libraryapp.utils.DataBaseConnection;

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

    @Override
    public Optional<Order> get(long id) throws SQLException {
        Order order = null;
        Connection connection = null;
        PreparedStatement pstatement = null;
        ResultSet resultSet = null;
        String sql = "select * from orders  where id = ?";
        try {
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            pstatement.setLong(1, id);
            if (pstatement.execute()) {
                resultSet = pstatement.executeQuery();
                while (resultSet.next()) {
                    order = new Order();
                    order.setId(resultSet.getLong("id"));
                    order.setBook(new Book());
// todo                    order.setBook(resultSet.getLong("book_id"));
                    order.setQuantity(resultSet.getInt("quantity"));
                    order.setUser(new User());
// todo                    order.setUser(resultSet.getInt("user_id"));
                    order.setOrderStatus(OrderStatus.valueOf(resultSet.getString("order_status")));
                    order.setTakedDate(resultSet.getTimestamp("taked_date").toLocalDateTime().toLocalDate());
                    order.setReturnDate(resultSet.getTimestamp("return_date").toLocalDateTime().toLocalDate());
                    order.setBill(resultSet.getInt("bill"));
                    order.setBillStatus(BillStatus.valueOf(resultSet.getString("bill_status")));
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
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            resultSet = pstatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getLong("id"));
                order.setBook(new Book());
// todo                    order.setBook(resultSet.getLong("book_id"));
                order.setQuantity(resultSet.getInt("quantity"));
                order.setUser(new User());
// todo                    order.setUser(resultSet.getInt("user_id"));
                order.setOrderStatus(OrderStatus.valueOf(resultSet.getString("order_status")));
                order.setTakedDate(resultSet.getTimestamp("taked_date").toLocalDateTime().toLocalDate());
                order.setReturnDate(resultSet.getTimestamp("return_date").toLocalDateTime().toLocalDate());
                order.setBill(resultSet.getInt("bill"));
                order.setBillStatus(BillStatus.valueOf(resultSet.getString("bill_status")));
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
        if (allOrders.size() == 0) {
            throw new SQLException("table ORDERS is empty");
        }
        return allOrders;
    }

    @Override
    public Order save(Order order) {
        Connection connection = null;
        PreparedStatement pstatement = null;
        String sql = "insert into orders(book_id, quantity, user_id, order_status, taked_date, return_date, bill, bill_status) values (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstatement.setLong(1, order.getBook().getId());
            pstatement.setInt(2, order.getQuantity());
            pstatement.setLong(3, order.getUser().getId());
            pstatement.setString(4, order.getOrderStatus().toString());
            pstatement.setTimestamp(5, Timestamp.valueOf(order.getTakedDate().atStartOfDay()));
            pstatement.setTimestamp(6, Timestamp.valueOf(order.getReturnDate().atStartOfDay()));
            pstatement.setInt(7, order.getBill());
            pstatement.setString(8, order.getBillStatus().toString());

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
                connection.close();
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
        String sql = "UPDATE orders SET book_id=?, quantity=?, user_id=?, order_status=?, taked_date=?, return_date=?, bill=?, bill_status=? WHERE id =?";
        try {
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            if (get(order.getId()) != null) {
                pstatement.setLong(9, order.getId());
                pstatement.setLong(1, order.getBook().getId());
                pstatement.setInt(2, order.getQuantity());
                pstatement.setLong(3, order.getUser().getId());
                pstatement.setString(4, order.getOrderStatus().toString());
                pstatement.setTimestamp(5, Timestamp.valueOf(order.getTakedDate().atStartOfDay()));
                pstatement.setTimestamp(6, Timestamp.valueOf(order.getReturnDate().atStartOfDay()));
                pstatement.setInt(7, order.getBill());
                pstatement.setString(8, order.getBillStatus().toString());
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
            connection = DataBaseConnection.getInstance().getConn();
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
}
