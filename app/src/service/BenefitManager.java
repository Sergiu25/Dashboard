package service;

import model.Benefit;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BenefitManager {
    private List<Benefit> benefits = new ArrayList<>();
    private final Gson gson = new Gson();

    public void addBenefit(Benefit benefit) {
        benefits.add(benefit);
    }

    public List<Benefit> getBenefits() { return benefits;}

    //Save to the file
    public void saveToFile(String fileName) {
        try(FileWriter writer=new FileWriter(fileName)){
            gson.toJson(benefits, writer);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadFromFile(String fileName) {
        try(FileReader reader=new FileReader(fileName)){
            Type listType = new TypeToken<ArrayList<Benefit>>() {}.getType();
            benefits = gson.fromJson(reader, listType);
            if(benefits==null){
                benefits = new ArrayList<>();
            }
        }catch(IOException e){
            benefits=new ArrayList<>();
        }
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
