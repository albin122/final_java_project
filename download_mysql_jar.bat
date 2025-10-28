@echo off
echo Downloading MySQL Connector/J...

REM Create .m2 repository directory if it doesn't exist
mkdir "%USERPROFILE%\.m2\repository\com\mysql\mysql-connector-j\9.0.0" 2>nul

REM Download MySQL Connector
powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/9.0.0/mysql-connector-j-9.0.0.jar' -OutFile '%USERPROFILE%\.m2\repository\com\mysql\mysql-connector-j\9.0.0\mysql-connector-j-9.0.0.jar'"

echo Done! Now run build_and_run.bat
pause

