package com.ngdb.services;

import com.ngdb.entities.Employee;
import com.ngdb.entities.Participation;
import com.ngdb.entities.Staff;
import com.ngdb.entities.article.Game;

import java.util.List;
import java.util.Scanner;

public class StaffParser {

    public Staff createFrom(String content, Game game, List<Employee> employees) {
        Staff staff = new Staff();
        Scanner scanner = new Scanner(content);
        while (scanner.hasNextLine()) {
            insertRolesIn(scanner, staff, game, employees);
        }
        return staff;
    }

    private void insertRolesIn(Scanner scanner, Staff staff, Game game, List<Employee> employees) {
        String role = scanner.nextLine();
        while (scanner.hasNextLine()) {
            String employeeName = scanner.nextLine();
            if (employeeName.isEmpty()) {
                return;
            } else {
                Employee employee = find(employeeName, employees);

                Participation participation = new Participation(employee, role, game);
                staff.add(participation);
            }
        }
    }

    private Employee find(String employeeName, List<Employee> employees) {
        for (Employee employee : employees) {
            if (employee.hasName(employeeName)) {
                return employee;
            }
        }
        return new Employee(employeeName);
    }
}
