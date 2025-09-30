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

    public boolean deleteBenefit(int id) {
        return benefits.removeIf(b -> b.getId()==id);
    }

    public List<Benefit> getAllBenefits() {
        return benefits;
    }
}
