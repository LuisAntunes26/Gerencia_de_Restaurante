package database;

import java.util.ArrayList;
import java.util.List;

public class Row {
    private List<String> elements;

    public Row(){
        this.elements = new ArrayList<>();
    }

    public void add(String newElement){
        this.elements.add(newElement);
    }

    public List<String> getColumns(){
        return this.elements;
    }

    public void setElement(int index, String element){
        this.elements.set(index, element);
    }

    public String toString(){
        return this.elements.toString();
    }


}
