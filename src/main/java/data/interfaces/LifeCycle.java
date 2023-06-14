package data.interfaces;

import data.objects.micro_objects.Kamikaze;

import java.io.FileNotFoundException;

public interface LifeCycle {
    void lifeCycle() throws FileNotFoundException;

    void inflictDamage(Kamikaze kamikaze);
}
