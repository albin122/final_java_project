package lms.model;

public class Student {
    private String registrationNumber;
    private String name;

    public Student(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Student(String registrationNumber, String name) {
        this.registrationNumber = registrationNumber;
        this.name = name;
    }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}    


