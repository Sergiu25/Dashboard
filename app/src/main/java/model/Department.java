package model;

public enum Department {
    MANAGER("Manager"),
    HR("HR"),
    IT("IT"),
    Finance("Finance"),
    MARKETING("Marketing"),
    PURCHASING("Purchasing"),
    LOGISTICS("Logistics");

    private final String displayName;
    Department(String displayName) {
        this.displayName = displayName;
    }
    @Override
    public String toString() {
        return displayName;
    }

}
