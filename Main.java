import java.util.Arrays;

public class main {

    public static Siatka siatka;
    public static daneGlobalne container;
    private static obliczanieMacierzy fourrier;
    public static double[][] globalH;
    public static double[] globalP;
    public static double[] t;

    public static void main(String[] args) {
        container = new daneGlobalne();
        globalH = new double[container.getNH()*container.getNB()][container.getNH()*container.getNB()]; //4x4
        globalP = new double[container.getNH()*container.getNB()]; //4
        zeroData();
        generujSiatke();
        fourrier = new obliczanieMacierzy(container, globalH, globalP, siatka);

        zeroData();
        fourrier.licz();
        Gauss.licz(container.getNH()*container.getNB(),globalH,globalP);
    }

    private static void generujSiatke() {

        siatka = new Siatka(container.getNH(),
                container.getNB(),
                container.getH(),
                container.getB(),
                container.getInitialTemp());
        //siatka.showSiatka();
    }

    private static void zeroData() {
        for(int i=0;i<container.getNH()*container.getNB();i++) {
            for(int j=0;j<container.getNH()*container.getNB();j++) {
                globalH[i][j]=0;
            }
            globalP[i]=0;
        }
    }
}

