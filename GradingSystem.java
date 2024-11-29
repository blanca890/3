import java.io.*;
import java.util.*;

public class GradingSystem {

    // Store data in memory using collections
    private static final List<Student> students = new ArrayList<>();
    private static final List<Teacher> teachers = new ArrayList<>();
    private static final List<Course> courses = new ArrayList<>();

    // Predefined credentials for simplicity
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Welcome to the Grading System ===");
        System.out.println("Please select your role:");
        System.out.println("1. Admin");
        System.out.println("2. Teacher");
        System.out.println("3. Student");
        System.out.print("Enter your choice: ");
        int role = scanner.nextInt();

        switch (role) {
            case 1 -> adminLogin(scanner);
            case 2 -> teacherInterface(scanner);
            case 3 -> studentInterface(scanner);
            default -> System.out.println("Invalid role. Exiting system.");
        }
    }

    // Admin Login and Interface
    private static void adminLogin(Scanner scanner) {
        System.out.print("\nEnter Admin Username: ");
        scanner.nextLine(); // Consume newline
        String username = scanner.nextLine();
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();

        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            System.out.println("Admin Login Successful!");
            adminInterface(scanner);
        } else {
            System.out.println("Invalid credentials. Exiting system.");
        }
    }

    private static void adminInterface(Scanner scanner) {
        int choice;
        do {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Manage Students");
            System.out.println("2. Manage Teachers");
            System.out.println("3. Manage Courses");
            System.out.println("4. View Grades");
            System.out.println("5. Generate Report");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> manageStudents(scanner);
                case 2 -> manageTeachers(scanner);
                case 3 -> manageCourses(scanner);
                case 4 -> viewGrades();
                case 5 -> generateReport();
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    // Teacher Interface
    private static void teacherInterface(Scanner scanner) {
        System.out.println("\nWelcome Teacher! Please log in.");
        System.out.print("Enter Teacher ID: ");
        scanner.nextLine(); // Consume newline
        String teacherId = scanner.nextLine();
        Teacher teacher = findTeacherById(teacherId);

        if (teacher != null) {
            System.out.println("Teacher Login Successful!");
            int choice;
            do {
                System.out.println("\n=== Teacher Menu ===");
                System.out.println("1. Assign Grades");
                System.out.println("2. View Grades");
                System.out.println("0. Logout");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> assignGrades(scanner);
                    case 2 -> viewGrades();
                    case 0 -> System.out.println("Logging out...");
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 0);
        } else {
            System.out.println("Teacher not found. Exiting system.");
        }
    }

    // Student Interface
    private static void studentInterface(Scanner scanner) {
        System.out.println("\nWelcome Student! Please log in.");
        System.out.print("Enter Student ID: ");
        scanner.nextLine(); // Consume newline
        String studentId = scanner.nextLine();
        Student student = findStudentById(studentId);

        if (student != null) {
            System.out.println("Student Login Successful!");
            System.out.println("\n=== Student Menu ===");
            System.out.println("Viewing Grades...");
            System.out.println(student);
        } else {
            System.out.println("Student not found. Exiting system.");
        }
    }

    // Manage Students
    private static void manageStudents(Scanner scanner) {
        System.out.println("\n=== Manage Students ===");
        System.out.print("Enter student name: ");
        scanner.nextLine(); // Consume newline
        String name = scanner.nextLine();
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        students.add(new Student(name, id));
        System.out.println("Student added successfully!");
    }

    // Manage Teachers
    private static void manageTeachers(Scanner scanner) {
        System.out.println("\n=== Manage Teachers ===");
        System.out.print("Enter teacher name: ");
        scanner.nextLine(); // Consume newline
        String name = scanner.nextLine();
        System.out.print("Enter teacher ID: ");
        String id = scanner.nextLine();
        teachers.add(new Teacher(name, id));
        System.out.println("Teacher added successfully!");
    }

    // Manage Courses
    private static void manageCourses(Scanner scanner) {
        System.out.println("\n=== Manage Courses ===");
        System.out.print("Enter course name: ");
        scanner.nextLine(); // Consume newline
        String name = scanner.nextLine();
        System.out.print("Enter course code: ");
        String code = scanner.nextLine();
        courses.add(new Course(name, code));
        System.out.println("Course added successfully!");
    }

    // Assign Grades
    private static void assignGrades(Scanner scanner) {
        System.out.println("\n=== Assign Grades ===");
        System.out.print("Enter student ID: ");
        scanner.nextLine(); // Consume newline
        String studentId = scanner.nextLine();
        Student student = findStudentById(studentId);

        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        Course course = findCourseByCode(courseCode);

        if (course == null) {
            System.out.println("Course not found!");
            return;
        }

        System.out.print("Enter grade: ");
        String grade = scanner.nextLine();
        student.addGrade(course, grade);
        System.out.println("Grade assigned successfully!");
    }

    // View Grades
    private static void viewGrades() {
        System.out.println("\n=== View Grades ===");
        for (Student student : students) {
            System.out.println(student);
        }
    }

    // Generate Report
    private static void generateReport() {
        System.out.println("\n=== Generate Report ===");
        try (PrintWriter writer = new PrintWriter(new FileWriter("report.txt"))) {
            for (Student student : students) {
                writer.println(student);
            }
            System.out.println("Report generated successfully! Check the file: report.txt");
        } catch (IOException e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }

    // Helper Methods
    private static Student findStudentById(String id) {
        return students.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
    }

    private static Teacher findTeacherById(String id) {
        return teachers.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
    }

    private static Course findCourseByCode(String code) {
        return courses.stream().filter(c -> c.getCode().equals(code)).findFirst().orElse(null);
    }
}

// Supporting Classes: Student, Teacher, Course
class Student {
    private String name;
    private String id;
    private Map<Course, String> grades = new HashMap<>();

    public Student(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void addGrade(Course course, String grade) {
        grades.put(course, grade);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", grades=" + grades +
                '}';
    }
}

class Teacher {
    private String name;
    private String id;

    public Teacher(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}

class Course {
    private String name;
    private String code;

    public Course(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}