import java.util.*;
import java.util.stream.Collectors;

public class Main {

    static List<Student> students = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    // keeps the original average of students who received the bonus
    static Map<Integer, Double> originalAverages = new HashMap<>();
    static final double PASS_MARK = 65;

    public static void main(String[] args) {
        int choice;
        do {
            showMenu();
            choice = readInt("");

            switch (choice) {
                case 1 -> registerStudent();
                case 2 -> displayAllStudents();
                case 3 -> searchById();
                case 4 -> filterPassing();
                case 5 -> filterFailing();
                case 6 -> searchByName();
                case 7 -> sortByName();
                case 8 -> sortByAverage();
                case 9 -> displayAllCourses();
                case 10 -> displayUniqueCourses();
                case 11 -> searchByCourse();
                case 12 -> displayUniqueSkills();
                case 13 -> groupByCourse();
                case 14 -> showStatistics();
                case 15 -> showBestStudent();
                case 16 -> showTopThree();
                case 17 -> applyBonus();
                case 18 -> showFinalRanking();
                case 0 -> System.out.println("End of program!");
                default -> System.out.println("Invalid option. Please choose from the menu.");
            }
        } while (choice != 0);
    }

    // ---------- menu and input helpers ----------

    static void showMenu() {
        System.out.println();
        System.out.println("==========================================");
        System.out.println("        STUDENT MANAGEMENT SYSTEM");
        System.out.println("==========================================");
        System.out.println("1. Register New Student");
        System.out.println("2. Display All Students");
        System.out.println("3. Search Student by ID");
        System.out.println("4. Filter Passing Students");
        System.out.println("5. Filter Failing Students");
        System.out.println("6. Search Students by Name");
        System.out.println("7. Sort Students by Name");
        System.out.println("8. Sort Students by Average Marks");
        System.out.println("9. Display All Courses");
        System.out.println("10. Display Unique Courses");
        System.out.println("11. Search Students by Course");
        System.out.println("12. Display Unique Skills");
        System.out.println("13. Group Students by Course");
        System.out.println("14. View Student Performance Statistics");
        System.out.println("15. View Best Performing Student");
        System.out.println("16. View Top 3 Students");
        System.out.println("17. Apply Bonus Marks");
        System.out.println("18. View Final Student Ranking");
        System.out.println();
        System.out.println("0. Exit");
        System.out.println("==========================================");
        System.out.print("Enter your choice: ");
    }

    static int readInt(String message) {
        while (true) {
            System.out.print(message);
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                if (message.isEmpty()) {
                    return -1; // used by the menu, so it shows "invalid option"
                }
                System.out.println("Invalid number, try again.");
            }
        }
    }

    static double readMark(String message) {
        while (true) {
            System.out.print(message);
            String line = sc.nextLine().trim();
            try {
                double mark = Double.parseDouble(line);
                if (mark >= 0 && mark <= 100) {
                    return mark;
                }
                System.out.println("Marks must be between 0 and 100.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    static String readText(String message) {
        while (true) {
            System.out.print(message);
            String text = sc.nextLine().trim();
            if (!text.isEmpty()) {
                return text;
            }
            System.out.println("This field cannot be empty.");
        }
    }

    // returns true (and prints a message) when there are no students
    static boolean noStudents() {
        if (students.isEmpty()) {
            System.out.println("No students have been registered yet.");
            return true;
        }
        return false;
    }

    static void printList(List<Student> list) {
        list.forEach(System.out::println);
    }

    // ---------- Task 1 ----------

    static void registerStudent() {
        int id = readInt("Enter student ID: ");
        if (id <= 0) {
            System.out.println("ID must be a positive number.");
            return;
        }

        boolean exists = students.stream().anyMatch(input -> input.getId() == id);
        if (exists) {
            System.out.println("A student with this ID already exists.");
            return;
        }

        String name = readText("Enter student name: ");

        int age = readInt("Enter age: ");
        if (age < 1 || age > 120) {
            System.out.println("Invalid age. Registration cancelled.");
            return;
        }

        int numCourses = readInt("Enter number of courses: ");
        if (numCourses < 1) {
            System.out.println("A student must have at least one course.");
            return;
        }

        Map<String, Double> courses = new HashMap<>();
        for (int i = 1; i <= numCourses; i++) {
            String courseName = readText("Course " + i + " name: ");
            if (courses.containsKey(courseName)) {
                System.out.println("Course already added for this student.");
                i--;
                continue;
            }
            double mark = readMark("Marks for " + courseName + " (0-100): ");
            courses.put(courseName, mark);
        }

        System.out.print("Enter skills separated by commas: ");
        String skillsLine = sc.nextLine();
        List<String> skills = Arrays.stream(skillsLine.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        students.add(new Student(id, name, age, courses, skills));
        System.out.println("Student registered successfully.");
    }

    static void displayAllStudents() {
        if (noStudents()) return;
        students.stream().forEach(System.out::println);
    }

    static void searchById() {
        if (noStudents()) return;
        int id = readInt("Enter student ID to search: ");

        students.stream()
                .filter(input -> input.getId() == id)
                .findFirst()
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("No student found with ID " + id));
    }

    // ---------- Task 2 ----------

    static void filterPassing() {
        if (noStudents()) return;
        List<Student> result = students.stream()
                .filter(input -> input.getAverage() >= PASS_MARK)
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("No students have an average of 65 or above.");
        } else {
            printList(result);
        }
    }

    static void filterFailing() {
        if (noStudents()) return;
        List<Student> result = students.stream()
                .filter(input -> input.getAverage() < PASS_MARK)
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("No students have an average below 65.");
        } else {
            printList(result);
        }
    }

    static void searchByName() {
        if (noStudents()) return;
        String name = readText("Enter name to search: ").toLowerCase();

        List<Student> result = students.stream()
                .filter(input -> input.getStudentName().toLowerCase().contains(name))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("No student found with that name.");
        } else {
            printList(result);
        }
    }

    static void sortByName() {
        if (noStudents()) return;
        students.stream()
                .sorted(Comparator.comparing(input -> input.getStudentName().toLowerCase()))
                .forEach(System.out::println);
    }

    static void sortByAverage() {
        if (noStudents()) return;
        students.stream()
                .sorted(Comparator.comparingDouble(Student::getAverage).reversed())
                .forEach(System.out::println);
    }

    // ---------- Task 3 ----------

    static void displayAllCourses() {
        if (noStudents()) return;
        System.out.println("All registered courses (duplicates included):");
        students.stream()
                .flatMap(input -> input.getCourses().keySet().stream())
                .sorted()
                .forEach(c -> System.out.println("- " + c));
    }

    static void displayUniqueCourses() {
        if (noStudents()) return;
        System.out.println("Unique courses:");
        students.stream()
                .flatMap(input -> input.getCourses().keySet().stream())
                .distinct()
                .sorted()
                .forEach(c -> System.out.println("- " + c));
    }

    static void searchByCourse() {
        if (noStudents()) return;
        String course = readText("Enter course name: ");

        List<Student> result = students.stream()
                .filter(input -> input.getCourses().keySet().stream()
                        .anyMatch(c -> c.equalsIgnoreCase(course)))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("No students are registered in " + course);
        } else {
            printList(result);
        }
    }

    static void displayUniqueSkills() {
        if (noStudents()) return;
        List<String> skills = students.stream()
                .flatMap(input -> input.getSkills().stream())
                .map(String::toLowerCase)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        if (skills.isEmpty()) {
            System.out.println("No skills have been entered.");
        } else {
            System.out.println("Unique skills:");
            skills.forEach(skill -> System.out.println("- " + skill));
        }
    }

    static void groupByCourse() {
        if (noStudents()) return;

        // a student appears once for every course they take
        Map<String, List<String>> groups = students.stream()
                .flatMap(input -> input.getCourses().keySet().stream()
                        .map(course -> new AbstractMap.SimpleEntry<>(course, input.getStudentName())))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        TreeMap::new,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

        groups.forEach((course, names) -> System.out.println(course + ": " + names));
    }

    // ---------- Task 4 ----------

    static void showStatistics() {
        if (noStudents()) return;

        for (Student s : students) {
            DoubleSummaryStatistics stats = s.getCourses().values().stream()
                    .mapToDouble(Double::doubleValue)
                    .summaryStatistics();

            System.out.println("-------------------------------");
            System.out.println("Student: " + s.getStudentName() + " (ID " + s.getId() + ")");
            System.out.printf("Average marks: %.2f%n", stats.getAverage());
            System.out.println("Highest mark: " + stats.getMax());
            System.out.println("Lowest mark: " + stats.getMin());
            System.out.println("Sum of marks: " + stats.getSum());
            System.out.println("Number of courses: " + stats.getCount());
        }
        System.out.println("-------------------------------");

        long passed = students.stream()
                .filter(input -> input.getAverage() >= PASS_MARK)
                .count();
        System.out.println("Number of students who passed: " + passed);

        showBestStudent();
    }

    static void showBestStudent() {
        if (noStudents()) return;
        students.stream()
                .max(Comparator.comparingDouble(Student::getAverage))
                .ifPresent(input -> System.out.println("Best student: " + input));
    }

    static void showTopThree() {
        if (noStudents()) return;
        System.out.println("Top 3 students:");
        students.stream()
                .sorted(Comparator.comparingDouble(Student::getAverage).reversed())
                .limit(3)
                .forEach(System.out::println);
    }

    // ---------- Task 5 ----------

    static void applyBonus() {
        if (noStudents()) return;

        // students that qualify and have not received the bonus yet
        List<Student> eligible = students.stream()
                .filter(input -> input.getAverage() > 50)
                .filter(s -> !originalAverages.containsKey(s.getId()))
                .collect(Collectors.toList());

        if (eligible.isEmpty()) {
            System.out.println("No new students qualify for the bonus.");
        }

        eligible.forEach(s -> {
            originalAverages.put(s.getId(), s.getAverage());
            s.getCourses().replaceAll((course, mark) -> Math.min(100.0, mark + 10));
        });

        System.out.println("Original and final averages:");
        students.stream().forEach(s -> {
            double original = originalAverages.getOrDefault(s.getId(), s.getAverage());
            double total = s.getCourses().values().stream().reduce(0.0, Double::sum);
            System.out.printf("%s | Original: %.2f | Final: %.2f | Total marks: %.1f%n",
                    s.getStudentName(), original, s.getAverage(), total);
        });
    }

    static void showFinalRanking() {
        if (noStudents()) return;
        System.out.println("Final ranking:");

        List<Student> ranked = students.stream()
                .sorted(Comparator.comparingDouble(Student::getAverage).reversed())
                .collect(Collectors.toList());

        for (int i = 0; i < ranked.size(); i++) {
            Student s = ranked.get(i);
            System.out.printf("%d. %s - %.2f%n", i + 1, s.getStudentName(), s.getAverage());
        }
    }
}
