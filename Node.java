public class Node {
    public double x, y; //wspolrzedne
    public double temp; //temperatura
    public Boolean BC; // flaga, 0 jesli skrajny lub 1 jesli w srodku elementu

    public Node(double x, double y, double temp, Boolean BC) {
        this.x = x;
        this.y = y;
        this.temp = temp;
        this.BC = BC;
    }
}
