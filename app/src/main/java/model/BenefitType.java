package model;

public enum BenefitType {
    HEALTH_INSURANCE("Health Insurance"),
    DENTAL_INSURANCE("Dental Insurance"),
    LIFE_INSURANCE("Life Insurance"),
    MEAL_TICKETS("Meal Tickets"),
    GYM_MEMBERSHIP("Gym Membership"),
    PUBLIC_TRANSPORT_PASS("Public Transport Pass"),
    COMPANY_CAR("Company Car"),
    PARKING_SPOT("Parking Spot"),
    TRAINING_BUDGET("Training Budget"),
    EXTRA_VACATION_DAYS("Extra Vacation Days"),
    CHILDCARE_SUPPORT("Childcare Support"),
    STOCK_OPTIONS("Stock Options"),
    PERFORMANCE_BONUS("Performance Bonus"),
    WELLNESS_PROGRAM("Wellness Program"),
    FREE_MEALS("Free Meals"),
    HOUSING_ALLOWANCE("Housing Allowance"),
    INTERNET_SUBSCRIPTION("Internet Subscription"),
    PHONE_SUBSCRIPTION("Phone Subscription"),
    EXTRA_VACATION("Extra Vacation");

    private final String displayName;
    BenefitType(String displayName) {
        this.displayName = displayName;
    }
    @Override
    public String toString() {
        return displayName;
    }

}
