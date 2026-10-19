# Student Management System (Java Stream API)

A console, menu-driven Java application for managing students. The user picks
an option from a menu to register students, search and filter them, manage
courses and skills, view performance statistics and apply bonus marks.

The project is meant to practise Java Collections, Object-Oriented
Programming (OOP) and the most common Stream API methods.

## Project Structure

| File | Purpose |
|------|---------|
| `Student.java` | The `Student` class (data, constructor, getters, setters, `toString()` and `getAverage()`) |
| `Main.java` | The menu and all the features, split into small reusable methods |

## How the System Works

### Data storage
- All students are stored in a `List<Student>`.
- Each student keeps their courses in a `Map<String, Double>` (course name -> marks).
- Each student keeps their skills in a `List<String>`.
- A `Map<Integer, Double>` remembers the original average of students who
  received the bonus, so the original and final averages can both be shown.

### Student class
`Student` has an `id`, `studentName`, `age`, `courses` and `skills`. The
method `getAverage()` calculates the average of all marks in the courses map
using streams, and every feature that needs an average uses it.

### Menu
When the program starts it shows the menu and reads the choice with `Scanner`.
A `switch` sends the choice to the matching method. After each action the menu
is shown again until the user enters `0`. An invalid choice shows an error
message, and if no students are registered yet the program tells the user.

## Features and Stream Methods Used

### Task 1: Registration and management
- **Register New Student**: asks for ID, name, age, number of courses, each
  course name and mark, and skills (separated by commas).
- **Display All Students** and **Search Student by ID**.
- Duplicate IDs are not allowed.
- Streams used: `stream()`, `forEach()`, `filter()`, `findFirst()`, `anyMatch()`

### Task 2: Search, filter and sort
- Passing students (average 65 or above) and failing students (below 65).
- Search students by name (not case sensitive, partial names work).
- Sort by name (A-Z) and by average marks (highest to lowest).
- Streams used: `filter()`, `map()`, `sorted()`, `collect()`, `average()`

### Task 3: Courses and skills
- Display all courses, and unique courses without duplicates.
- Search students registered in a given course.
- Display all unique skills.
- Group students by course. A student with several courses appears in each group.
- Streams used: `flatMap()`, `distinct()`, `filter()`, `sorted()`, `groupingBy()`

### Task 4: Performance and statistics
- For each student: average, highest mark, lowest mark, sum of marks and number of courses.
- Number of students who passed, and the student with the highest average.
- Top 3 students by average.
- Streams used: `mapToDouble()`, `summaryStatistics()`, `max()`, `min()`, `count()`, `limit()`, `sorted()`

### Task 5: Bonus marks and final results
- Students with an original average **above 50** qualify for the bonus.
- Each eligible student gets **+10 marks in every course**.
- No mark can go above **100**.
- The bonus is applied **only once** per student, even if the option is chosen again.
- The original average and final average of each student are displayed.
- The final ranking shows students from highest to lowest average.
- Streams used: `filter()`, `map()`, `forEach()`, `sorted()`, `reduce()`

## Rules and Validation
- Student ID must be a positive number and must not already exist.
- Age must be between 1 and 120.
- A student must have at least one course, and the same course cannot be added twice for one student.
- Marks must be between 0 and 100.
- Name and course name cannot be empty.
- A student **passes** when their average is **65 or above**.

## How to Run

Requires Java 17 or newer.

1. Clone or download this repository.
2. Open a terminal in the project folder.
3. Compile the files:
   ```
   javac Student.java Main.java
   ```
4. Run the program:
   ```
   java Main
   ```
5. Choose an option by typing its number and pressing Enter. Enter `0` to exit.

## Example Usage

1. Choose `1` to register a few students.
2. Choose `2` to see all students.
3. Try the filter, sort and course options (`4` to `13`).
4. Choose `14` to see the performance report.
5. Choose `17` to apply bonus marks, then `18` to see the final ranking.
