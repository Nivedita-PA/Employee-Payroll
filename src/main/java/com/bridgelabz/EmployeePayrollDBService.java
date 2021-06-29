package com.bridgelabz;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBService {

    private PreparedStatement employeePayrollDataStatement;
    private static EmployeePayrollDBService employeePayrollDBService;

    public static EmployeePayrollDBService getInstance(){
        if(employeePayrollDBService == null)
            employeePayrollDBService = new EmployeePayrollDBService();
        return employeePayrollDBService;
    }

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
            employeePayrollDataList = this.getEmployeePayrollData(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return employeePayrollDataList;
    }

    public int updateEmployeeData(String name, double salary) {
        return this.updateEmployeeDataUsingStatement(name, salary);
    }

    private int updateEmployeeDataUsingStatement(String name, double salary) {
       String sql = String.format("update employee_payroll set salary = %.2f where name = '%s';",salary,name);
       try(Connection connection = this.getConnection()){
           Statement statement = connection.createStatement();
           return statement.executeUpdate(sql);
       }catch (SQLException e){
           e.printStackTrace();
       }
        return 0;
    }

    public List<EmployeePayrollData> getEmployeePayrollData(String name) {
        List<EmployeePayrollData> employeePayrollDataList = null;
        if(this.employeePayrollDataStatement == null)
            this.preparedStatementForEmployeeData();
        try{
            employeePayrollDataStatement.setString(1,name);
            ResultSet resultSet = employeePayrollDataStatement.executeQuery();
            employeePayrollDataList = this.getEmployeePayrollData(resultSet);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return employeePayrollDataList;
    }

    private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) {
        List<EmployeePayrollData> employeePayrollDataList = new ArrayList<>();
        try{
                while(resultSet.next()){
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    double salary = resultSet.getDouble("salary");
                    LocalDate start = resultSet.getDate("start").toLocalDate();
                    employeePayrollDataList.add(new EmployeePayrollData(id, name, salary, start));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return employeePayrollDataList;
    }

    private void preparedStatementForEmployeeData() {
        try{
           Connection connection = this.getConnection();
           String sql = "SELECT * FROM employee_payroll WHERE name = ?";
           employeePayrollDataStatement = connection.prepareStatement(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
