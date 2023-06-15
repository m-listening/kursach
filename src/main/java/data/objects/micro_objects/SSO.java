package data.objects.micro_objects;

import data.functional.forObjects.micro.MethodsOfMicro;

public class SSO extends SimpleSoldier {
    private Kamikaze aim;

    public SSO(String name, double health,double x, double y) {
        super(name, health,x,y);
        setMove(0.9);
        setArmor(100);
        setDamage(0.6);
        getCircle().setRadius(200);
    }

    @Override
    public void lifeCycle() {
        fight();
        if (!isElect() && !isInMacro() && isActive()) {
            if (isEmptyAim()) {
                MethodsOfMicro.whatToDo(this);
            } else {
                if ((Math.abs(getAim().getX() - getX()) + Math.abs(getAim().getY() - getY())) < 5.0) {
                    clearAim();
                } else {
                    double signDeltaX = Math.signum(getAim().getX() - getX());
                    double dx = (Math.min(Math.abs(getAim().getX() - getX()), getMove())) * signDeltaX;

                    double signDeltaY = Math.signum(getAim().getY() - getY());
                    double dy = (Math.min(Math.abs(getAim().getY() - getY()), getMove())) * signDeltaY;
                    moveActive(dx, dy);
                }
            }
        }
    }

    public void clearAim() {
        aim = null;
    }

    public boolean isEmptyAim() {
        return aim == null;
    }

    @Override
    public void inflictDamage(Kamikaze warrior) {
        damage(warrior);
    }

    public Kamikaze getAim() {
        return aim;
    }

    public void setAim(Kamikaze aim) {
        this.aim = aim;
    }
}
