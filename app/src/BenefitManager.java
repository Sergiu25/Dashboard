import java.util.ArrayList;
import java.util.List;
public class BenefitManager {
    private List<Benefit> benefits = new ArrayList<>();

    //add the benefit
    public void addBenefit(Benefit benefit){
        benefits.add(benefit);
    }

    //return all the benefits
    public List<Benefit> getAllBenefits() {
        return benefits;
    }

    //Search the benefit by id
    public Benefit getBenefitById(int id){
        for (Benefit b : benefits) {
            if (b.getId() == id){
                return b;
            }
        }
        return null;
    }

    //Delete the benefit by id
    public boolean removeBenefit(int id){
        Benefit toRemove = getBenefitById(id);
        if (toRemove != null){
            benefits.remove(toRemove);
            return true;
        }
        return false;
    }

    //update the benefit
    public boolean updateBenefit(int id,String newName,double newCost){
        Benefit toUpdate=getBenefitById(id);
        if (toUpdate!=null){
            toUpdate.setName(newName);
            toUpdate.setCost(newCost);
            return true;
        }
        return false;
    }
}
