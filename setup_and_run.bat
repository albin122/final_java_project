@echo off
echo ========================================
echo Library Management System Setup & Run
echo ========================================
echo.

REM Set Java Path (adjust if your JDK is in a different location)
set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.8.9-hotspot"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo Step 1: Checking Java...
java -version
if %errorlevel% neq 0 (
    echo ERROR: Java is not found!
    echo Please install Java or update JAVA_HOME in this file.
    pause
    exit /b 1
)
echo.

echo Step 2: Checking MySQL Connector...
if not exist "mysql-connector.jar" (
    echo Downloading MySQL Connector...
    powershell -ExecutionPolicy Bypass -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/9.0.0/mysql-connector-j-9.0.0.jar' -OutFile 'mysql-connector.jar'"
    echo.
)
echo.

echo Step 3: Compiling Java files...
javac -d target/classes -encoding UTF-8 -cp "mysql-connector.jar" src/main/java/lms/Main.java src/main/java/lms/db/Database.java src/main/java/lms/model/Book.java src/main/java/lms/model/Student.java src/main/java/lms/model/BorrowRecord.java src/main/java/lms/dao/BookDao.java src/main/java/lms/dao/StudentDao.java src/main/java/lms/dao/BorrowDao.java src/main/java/lms/ui/RoleSelectionFrame.java src/main/java/lms/ui/StudentLoginFrame.java src/main/java/lms/ui/StudentDashboardFrame.java src/main/java/lms/ui/AdminLoginFrame.java src/main/java/lms/ui/AdminDashboardFrame.java src/main/java/lms/ui/AddBookDialog.java

if %errorlevel% neq 0 (
    echo ERROR: Compilation failed!
    pause
    exit /b %errorlevel%
)
echo Compilation successful!
echo.

echo Step 4: Starting Library Management System...
echo Make sure XAMPP MySQL is running and database is created!
echo.
java -cp "target/classes;mysql-connector.jar" lms.Main

pause

