import java.util.Arrays;

public class Main {

    private static Siatka grid;
    private static daneGlobalne daneGlob;
    public static obliczanieMacierzy obliczanie;
    public static double[][] globalH;
    public static double[] globalP;
    public static double[] t;

    public static void main(String []argv) {

        daneGlob = new daneGlobalne();
        globalH = new double[daneGlob.getNH()*daneGlob.getNB()][daneGlob.getNH()*daneGlob.getNB()];
        globalP = new double[daneGlob.getNH()*daneGlob.getNB()];
        generujSiatke(daneGlob);

        obliczanie = new obliczanieMacierzy(daneGlob, globalH, globalP, grid);
        int k=0;
        int numberOfSteps = (int)daneGlob.getSimTime()/(int)daneGlob.getTimeStamp();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("~ Temperatury min i max dla " + numberOfSteps + " kroków: ~");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        for(int c = 0; c < numberOfSteps; c++) {
            zerujDane();
            obliczanie.licz();
            t=obliczanie.obliczanieRownania(daneGlob.getNH()*daneGlob.getNB(), globalH, globalP);

            for(int i = 0;i<daneGlob.getNH()*daneGlob.getNB();i++)
                grid.getTabWez()[i].setTemp(t[i]);

            System.out.print( (k+1)*daneGlob.getTimeStamp() + "\t" );
            System.out.printf( "%.3f\t\t", getMin( t ) );
            System.out.printf( "%.3f", getMax( t ) );
            System.out.println();
            k++;
        }
    }

    private static void generujSiatke(daneGlobalne daneGlob) {
        double H = daneGlob.getH();
        double B = daneGlob.getB();
        int nH = daneGlob.getNH();
        int nB = daneGlob.getNB();
        double temp = daneGlob.getInitialTemp();
        grid = new Siatka(H, B, nH, nB, temp);
    }

    private static void zerujDane() {
        for(int i=0;i<daneGlob.getNH()*daneGlob.getNB();i++) {
            for(int j=0;j<daneGlob.getNH()*daneGlob.getNB();j++) {
                globalH[i][j]=0;
            }
            globalP[i]=0;
        }
    }

    public static double getMin ( double[] t ){
        return Arrays.stream( t ).min().getAsDouble();
    }
    public static double getMax ( double[] t ){
        return Arrays.stream( t ).max().getAsDouble();
    }
}
