package data.functional.forObjects.micro.comparators;

import data.objects.micro_objects.Kamikaze;

import java.util.Comparator;

public class NameComparator implements Comparator<Kamikaze> {
    @Override
    public int compare(Kamikaze o1, Kamikaze o2) {
        return o2.getName().getText().compareTo(o1.getName().getText());
    }
}
