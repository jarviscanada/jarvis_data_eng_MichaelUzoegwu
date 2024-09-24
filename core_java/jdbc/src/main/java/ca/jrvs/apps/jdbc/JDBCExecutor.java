package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExecutor {
  public static void main (String[] args) {
    DatabaseConnectionManager dcm = new DatabaseConnectionManager(
        "localhost",
        "hplussport",
        "postgres",
        "password"
    );
    try {
      Connection connection = dcm.getConnection();
      CustomerDAO customerDAO = new CustomerDAO(connection);
      Customer customer = new Customer();
      customer.setFirstName("Marcus");
      customer.setLastName("Rashford");
      customer.setEmail("mr10@utd.co.uk");
      customer.setAddress("Old Trafford");
      customer.setCity("Mancheseter");
      customer.setState("North");
      customer.setPhone("123 456-789");
      customer.setZipCode("991183");

      Customer dbCustomer = customerDAO.create(customer);
      System.out.println(dbCustomer);
      dbCustomer = customerDAO.findById(dbCustomer.getId());
      System.out.println(dbCustomer);
      dbCustomer.setEmail("marcus10@utd.co.uk");
      dbCustomer = customerDAO.update(dbCustomer);
      System.out.println(dbCustomer);
      customerDAO.delete(dbCustomer.getId());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}