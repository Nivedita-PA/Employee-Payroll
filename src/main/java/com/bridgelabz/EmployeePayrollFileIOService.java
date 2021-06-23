package com.bridgelabz;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class EmployeePayrollFileIOService {

    public static String PAYROLL_FILE_NAME = "E:\\Employee-Payroll\\src\\main\\resources\\payroll_data.txt";

    public void writeData(List<EmployeePayrollData> employeePayrollList) throws IOException{
        StringBuffer newEmpBuffer = new StringBuffer();
        employeePayrollList.forEach(employee-> {
            String employeeDataString = employee.toString().concat("\n");
            newEmpBuffer.append(employeeDataString);
        });

        try{
            Files.write(Paths.get(PAYROLL_FILE_NAME), newEmpBuffer.toString().getBytes());
        }catch (IOException e) { }
    }

    public void printData(){
        try{
            Files.lines(new File(PAYROLL_FILE_NAME).toPath())
                    .forEach(System.out::println);
        }catch (IOException e) { }
    }

    public long countEntries(){
        long entries = 0;
        try{
            entries = Files.lines(new File(PAYROLL_FILE_NAME).toPath())
                    .count();
        }catch (IOException e){ }
      return entries;
    }
}
