public class powierzchniaLokalna {
    public double[][] N;
    public Wezel nodes[];

    public powierzchniaLokalna ( Wezel n1, Wezel n2 ) {
        N = new double[2][4];
        nodes = new Wezel[2];
        nodes[0] = n1;
        nodes[1] = n2;
    }
}