import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class EmployeeService {
    private static final String FILE_PATH = "employees.txt";
    private List<Employee> employees = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private int nextEmployeeId =1;

    public EmployeeService() {
        loadEmployees();
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n====Employee System ====");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Update Salary");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 : addEmployee(); break;
                case 2 : viewEmployees(); break;
                case 3 : updateSalary(); break;
                case 4 : deleteEmployee(); break;
                case 5 : System.exit(0);
                default : System.out.println("Invalid choice!");
            }
        }
    }
    private void loadEmployees() {
        employees.clear();
        File file = new File(FILE_PATH);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Employee emp = Employee.fromCSV(line);
                employees.add(emp);
                // Update nextEmployeeId if needed
                if (emp.getId() >= nextEmployeeId) nextEmployeeId = emp.getId() + 1;
            }
        } catch (IOException e) {
            System.out.println("Error loading employees: " + e.getMessage());
        }
    }

    private void saveEmployees() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Employee emp : employees) {
                pw.println(emp.toCSV());
            }
        } catch (IOException e) {
            System.out.println("Error saving employees: " + e.getMessage());
        }
    }

    private void addEmployee() {
        int id = nextEmployeeId++;

        System.out.print("\nEnter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter Department: ");
        String dept = scanner.nextLine();

        employees.add(new Employee(id, name, salary, dept));
        saveEmployees();
        System.out.println("Employee added with ID: " + id + "\n");
    }

    private void viewEmployees() {
        System.out.println("\n=== Employee List ===");
        if (employees.isEmpty()) {
            System.out.println("No employees found.\n");
            return;
        }

        for (Employee emp : employees) {
            System.out.println(emp);
        }
    }

    private void updateSalary() {
        System.out.print("\nEnter Employee ID to update: ");
        int id = scanner.nextInt();

        for (Employee emp : employees) {
            if (emp.getId() == id) {
                System.out.print("Enter new salary: ");
                emp.setSalary(scanner.nextDouble());
                saveEmployees();
                System.out.println("Salary updated.\n");
                return;
            }
        }
        System.out.println("Employee not found.\n");
    }

    private void deleteEmployee() {
        System.out.print("\nEnter Employee ID to delete: ");
        int id = scanner.nextInt();

        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == id) {
                employees.remove(i);
                saveEmployees();
                System.out.println("Employee deleted.\n");
                return;
            }
        }
        System.out.println("Employee not found.\n");
    }
}
