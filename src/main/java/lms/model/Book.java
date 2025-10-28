package lms.model;

public class Book {
    private int id;
    private String title;
    private int availableCount;

    public Book(int id, String title, int availableCount) {
        this.id = id;
        this.title = title;
        this.availableCount = availableCount;
    }

    public Book(String title, int availableCount) {
        this(0, title, availableCount);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getAvailableCount() { return availableCount; }
    public void setAvailableCount(int availableCount) { this.availableCount = availableCount; }

    public String getAvailabilityStatus() {
        return availableCount > 0 ? "Available" : "Not Available";
    }
}


