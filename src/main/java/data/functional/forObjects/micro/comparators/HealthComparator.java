package data.functional.forObjects.micro.comparators;

import data.objects.micro_objects.Kamikaze;

import java.util.Comparator;

public class HealthComparator implements Comparator<Kamikaze> {
    @Override
    public int compare(Kamikaze o1, Kamikaze o2) {
        return Double.compare(o2.getHealth(), o1.getHealth());
    }
}
