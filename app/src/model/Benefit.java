package model;

public class Benefit {
    private int id;
    private String name;
    private double cost;
    public Benefit(int id, String name, double cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }
    @Override
    public String toString(){
        return "model.Benefit{id="+id+", name="+name+", cost="+cost+"}";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
}
