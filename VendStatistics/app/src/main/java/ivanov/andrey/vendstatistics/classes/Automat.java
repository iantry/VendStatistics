package ivanov.andrey.vendstatistics.classes;

/**
 * Created by Andrey on 19.09.2016.
 */
public class Automat {

    String name;
    String number;

    public Automat (String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }
}
