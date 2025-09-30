package model;

public class Benefit {
    private int id;
    private BenefitType type;
    private double cost;
    public Benefit(int id, BenefitType type, double cost) {
        this.id = id;
        this.type = type;
        this.cost = cost;
    }
    @Override
    public String toString(){
        return "model.Benefit{id="+id+", type="+type+", cost="+cost+"}";
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

    public BenefitType getType(){
        return type;
    }
    public void setType(BenefitType type){
        this.type = type;
    }
}
