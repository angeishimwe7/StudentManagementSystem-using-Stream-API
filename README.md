# StudentManagementSystem-using-Stream-API

A console, menu-driven Java application for managing students. It practises
Java Collections, OOP and the Stream API.

## Features
- Register students (ID, name, age, courses with marks, skills) and block duplicate IDs
- Display, search (by ID, name, course), filter (pass/fail) and sort students
- Show all courses, unique courses, unique skills and group students by course
- Performance report (average, highest, lowest, sum, number of courses)
- Best student, top 3 students and pass count
- Bonus marks (+10 per course for students with an average above 50, capped at 100, applied once)
- Final ranking after the bonus

A student passes when their average is 65 or above.

## Files
- `Student.java` - the Student class
- `Main.java` - menu and all the features

## How to run
Requires Java 21 or newer.

```
javac Student.java Main.java
java Main
```

Choose an option from the menu by typing its number. Enter `0` to exit.
