package com.bridgelabz;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployeePayrollServiceTest {
    @Test
    public void given3EmployeesWhenWrittenToFileShouldMatchEmployeeEntries() throws IOException {
        EmployeePayrollData[] arrayOfEmps = {
                new EmployeePayrollData(1,"Jeff Bezos",1000000.0),
                new EmployeePayrollData(2,"Bill Gates",2000000.0),
                new EmployeePayrollData(3,"Mark Zuckerberg",3000000.0)
        };

        EmployeePayrollService employeePayrollService;
        employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
        employeePayrollService.writeEmployeePayollData(EmployeePayrollService.IOService.FILE_IO);
        employeePayrollService.printData(EmployeePayrollService.IOService.FILE_IO);
        long entries = employeePayrollService.countEntries(EmployeePayrollService.IOService.FILE_IO);
        Assert.assertEquals(3,entries);
    }

    @Test
    public void givenFileOnReadingFromFileShouldMatchEmployeeCount(){
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        long entries = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.FILE_IO);
        Assert.assertEquals(3,entries);
    }

    @Test
    public void givenEmployeePayrollInDb_WhenRetrieved_Should_MatchEmployeeCount() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollDataList = employeePayrollService.readEmployeePayrollDataDB(EmployeePayrollService.IOService.DB_IO);
        Assert.assertEquals(3,employeePayrollDataList.size());
    }
}
