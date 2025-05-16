public class Employee {
    private int id;
    private String name;
    private double salary;
    private String department;

    public Employee(int id, String name, double salary, String department) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.department = department;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getSalary() { return salary; }
    public String getDepartment() { return department; }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %d | Name: %s | Salary: Rs.%.2f | Dept: %s",
                id, name, salary, department
        );
    }
    public String toCSV() {
        return String.format("%d,%s,%.2f,%s", id, name, salary, department);
    }

    public static Employee fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid CSV line: " + csvLine);
        }
        int id = Integer.parseInt(parts[0].trim());
        String name = parts[1].trim();
        double salary = Double.parseDouble(parts[2].trim());
        String dept = parts[3].trim();
        return new Employee(id, name, salary, dept);
    }
}
