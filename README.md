## Library Management System (Java Swing + MySQL)

Desktop Library Management app with Admin and Student roles.

### Requirements
- Java 17+
- Maven 3.8+
- XAMPP MySQL (running on localhost)

### Database Setup (XAMPP MySQL)
1. Start MySQL from XAMPP Control Panel
2. Create database and tables:
   - Option A: Using phpMyAdmin → import `schema.sql`
   - Option B: Command line:
     ```bash
     mysql -u root -p < schema.sql
     ```
3. Optional: Insert your student registration numbers into `students` table.

### Configure DB Credentials
Edit `lms/db/Database.java` and set username/password. They are intentionally empty with a comment showing where to change:
```java
private static final String DB_USERNAME = ""; // <-- enter your MySQL username (e.g., "root")
private static final String DB_PASSWORD = ""; // <-- enter your MySQL password (often empty on XAMPP)
```
Default URL uses database `library_db`:
```
jdbc:mysql://localhost:3306/library_db?useSSL=false&serverTimezone=UTC
```

### Run
```bash
mvn -q -DskipTests exec:java
```
Or from IDE, run `lms.Main`.

### Admin Login
- Username: `admin`
- Password: `admin123`

You can change these in `lms/ui/AdminLoginFrame.java`.

### Features
- Student:
  - Login with Registration Number
  - View available books and counts
  - Borrow selected book (decrements count)
  - Return previously borrowed book (increments count)
  - Logout
- Admin:
  - View active borrowed books with student Reg. No
  - Add Book (name + count)

### Notes
- For XAMPP, MySQL default user is often `root` with an empty password. Update `Database.java` accordingly.
- If you change the DB name/port, update `DB_URL` in `Database.java`.


