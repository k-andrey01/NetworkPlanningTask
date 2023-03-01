import java.util.ArrayList;

public class Way {
    private int x1;
    private int x2;
    private int weight;

    public Way(int x1, int x2, int weight) {
        this.x1 = x1;
        this.x2 = x2;
        this.weight = weight;
    }
    public Way(){}

    public Way getWay(int x1, int x2, ArrayList<Way> list){
        for (Way w: list) {
            if (w.x1==x1 && w.x2==x2)
                return w;
        }
        return null;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
