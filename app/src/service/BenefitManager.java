package service;

import model.Benefit;

import java.util.ArrayList;
import java.util.List;

public class BenefitManager {
    private List<Benefit> benefits = new ArrayList<>();

    public void addBenefit(Benefit benefit) {
        benefits.add(benefit);
    }

    public Benefit findBenefitByName(String name) {
        for (Benefit b : benefits) {
            if (b.getName().equalsIgnoreCase(name)) {
                return b;
            }
        }
        return null;
    }

    public boolean updateBenefit(String name, Benefit updatedBenefit) {
        for (int i = 0; i < benefits.size(); i++) {
            if (benefits.get(i).getName().equalsIgnoreCase(name)) {
                benefits.set(i, updatedBenefit);
                return true;
            }
        }
        return false;
    }

    public boolean deleteBenefit(String name) {
        return benefits.removeIf(b -> b.getName().equalsIgnoreCase(name));
    }

    public List<Benefit> getAllBenefits() {
        return benefits;
    }
}
