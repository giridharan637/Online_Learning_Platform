# Online Learning Platform (Java CLI)

A beginner-friendly, in-memory Java console project using Model → Repository → Service → CLI architecture.

## Requirements
Java JDK 8+ and a terminal (PowerShell recommended on Windows).

## Compile and run
From the `OnlineLearningPlatform` folder in PowerShell:

```powershell
javac -d out (Get-ChildItem src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
java -cp out Main
```

On macOS/Linux:

```sh
javac -d out $(find src -name '*.java')
java -cp out Main
```

## Example flow
1. Open **Course Management**; create a course (ID 10, price 0 for a free course).
2. Add a section to the course, then add at least three lessons to that section. Available lesson types: Video, Reading, Quiz.
3. Publish the course.
4. Open **Enrollment Management**; enroll sample student ID **101** in course ID **10**, using a new enrollment ID (e.g. 55).
5. Open **Learning Progress**; complete lessons in order: watch >=90% of video minutes, confirm reading, pass quiz with score >=60.
6. Open **Certificates**; request certificate using enrollment ID **55** once status is COMPLETED.

For paid courses, after enrollment use **Payment Management** to simulate a successful payment before starting lessons.

## Sample users
- Student: 101, Arun
- Instructor: 201, Priya
- Admin: 301, Admin

## Important limitations
- Data is stored only in ArrayLists; it is lost when the program exits.
- Payments are **simulated**, not connected to a payment gateway.
- User accounts are seeded in `Main.java` (there is no login or user creation menu).
- Lesson watch/read/quiz state is stored in the shared Lesson model. With multiple students enrolled in the same course, their lesson action state is **not isolated**; completion records are per-enrollment, but actions can carry over. Do not use this project for production or grading multiple students' independent quiz attempts.
- The console currently expects numeric input at numeric prompts; entering letters may cause an input error.
- Course withdrawal, instructor earnings, price changes, and administrative controls are not exposed as CLI actions.

## Validation performed
- All `.java` files compiled successfully.
- End-to-end free course test: create course → section → 3 lessons → publish → enroll → complete lessons → issue certificate.
- Paid course test: publish → pending enrollment → simulated payment success → refund.
