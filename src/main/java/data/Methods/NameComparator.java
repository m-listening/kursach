package data.Methods;

import data.micro_objects.Kamikaze;

import java.util.Comparator;

public class NameComparator implements Comparator<Kamikaze> {
    @Override
    public int compare(Kamikaze o1, Kamikaze o2) {
        return o1.getName().getText().compareTo(o2.getName().getText());
    }
}