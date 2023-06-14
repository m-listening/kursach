package data.objects.micro_objects;

import java.util.Comparator;

public class MurdersComparator implements Comparator<Kamikaze> {
    @Override
    public int compare(Kamikaze o1, Kamikaze o2) {
        return Integer.compare(o1.getMurders().getCount(), o2.getMurders().getCount());
    }
}