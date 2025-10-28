@echo off
echo Compiling Library Management System...

REM Compile Java files
javac -d target/classes -encoding UTF-8 src/main/java/lms/Main.java src/main/java/lms/db/Database.java src/main/java/lms/model/*.java src/main/java/lms/dao/*.java src/main/java/lms/ui/*.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)

echo Compilation successful!
echo.
echo Running Library Management System...
echo.

REM Run the application
java -cp "target/classes;%USERPROFILE%\.m2\repository\com\mysql\mysql-connector-j\9.0.0\mysql-connector-j-9.0.0.jar" lms.Main

pause

