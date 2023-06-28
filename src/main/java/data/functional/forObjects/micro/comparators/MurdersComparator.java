package data.functional.forObjects.micro.comparators;

import data.objects.micro_objects.Kamikaze;

import java.util.Comparator;

public class MurdersComparator implements Comparator<Kamikaze> {
    @Override
    public int compare(Kamikaze o1, Kamikaze o2) {
        return Integer.compare(o2.getMurders().getCount(), o1.getMurders().getCount());
    }
}