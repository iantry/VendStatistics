package ivanov.andrey.vendstatistics.classes;

/**
 * Created by Andrey on 17.09.2016.
 */
public class Drink {

    private String name;
    private String price;

     public Drink(String name, String price){
        this.name = name;
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }
}
