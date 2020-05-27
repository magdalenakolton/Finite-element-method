public class Wezel {

    private double x,y,temp;
    private Boolean statusWezla;
    public Wezel(double x, double y, double temp, Boolean status_wezla) {
        this.x=x;
        this.y=y;
        this.temp=temp;
        this.statusWezla=status_wezla;
    }


    public void setX(double x) {
        this.x = x;
    }
    public void setTemp(double temp) {
        this.temp = temp;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void setStatus(Boolean status_wezla) {
        this.statusWezla = status_wezla;
    }


    public Boolean getStatus() {
        return this.statusWezla;
    }
    public double getTemperature() {
        return this.temp;
    }
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
}
