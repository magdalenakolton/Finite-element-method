public class powLokalna {
    public double[][] N;
    public Wezel TabWez[];

    public powLokalna (Wezel n1, Wezel n2 ) {
        N = new double[2][4];
        TabWez = new Wezel[2];
        TabWez[0] = n1;
        TabWez[1] = n2;
    }
}
