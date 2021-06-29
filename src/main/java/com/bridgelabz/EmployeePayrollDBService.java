package com.bridgelabz;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBService {

    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service2?allowPublicKeyRetrieval=true&useSSL=false";
        String userName = "root";
        String password = "mysql";
        Connection connection;
        System.out.println("Connecting to database:" +jdbcURL);
        connection = DriverManager.getConnection(jdbcURL, userName, password);
        System.out.println("connection is successful!!!" +connection);
        return connection;
    }

    public List<EmployeePayrollData> readData() {
       String sql = " SELECT * FROM employee_payroll; ";
       List<EmployeePayrollData> employeePayrollDataList = new ArrayList<>();
        try (Connection connection = this.getConnection();){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            double salary = resultSet.getDouble("salary");
            LocalDate start = resultSet.getDate("start").toLocalDate();
            employeePayrollDataList.add(new EmployeePayrollData(id, name, salary, start));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return employeePayrollDataList;
    }

}
