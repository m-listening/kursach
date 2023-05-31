package data.micro_objects;

import data.Methods.Utilities;

import static app.Play.*;

public class SSO extends SimpleSoldier {
    private Kamikaze aim;

    public SSO(String name, double health) {
        super(name, health);
        setMove(7);
        setArmor(100);
        setDamage(7);
        getCircle().setRadius(200);
    }

    @Override
    public void lifeCycle() {
        world.getAllWarriors().forEach(e -> {
            if (!e.equals(this) && e.getTeam() != this.getTeam() && !this.isInMacro() && !e.isInMacro()) {
                if (e.getImageView().getBoundsInParent().intersects(this.getCircle().getBoundsInParent())) {
                    this.inflictDamage(e);
                    if (e.getHealth() <= 0) this.getMurders().implementCount();
                }
            }
        });
        if (!isElect() && !isInMacro() && isActive()) {
            if (isEmptyAim()) {
                Utilities.whatToDo(this);
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
        if (warrior.getArmor() > 0)
            warrior.setArmor(warrior.getArmor() - this.getDamage());
        else if (warrior.getArmor() < 0) {
            warrior.setHealth(warrior.getHealth() - Math.abs(warrior.getArmor()));
            warrior.setArmor(0);
        } else if (warrior.getArmor() == 0)
            warrior.setHealth(warrior.getHealth() - this.getDamage());
    }

    public Kamikaze getAim() {
        return aim;
    }

    public void setAim(Kamikaze aim) {
        this.aim = aim;
    }
}
