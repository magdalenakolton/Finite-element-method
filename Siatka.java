import java.util.Random;

public class Siatka {
    private int liczba_elementow, liczba_wezlow;
    private ElementUniwersalny[] tab_elem;
    private Wezel[] tab_wez;
    private int nH;
    private int nB;
    private double H;
    private double B;
    private double temp;

    public Siatka(int nH, int nB, double H, double B, double temp) {
        this.temp=temp;
        this.H = H;
        this.B=B;
        this.nB=nB;
        this.nH=nH;
        this.liczba_elementow=(nH-1)*(nB-1);
        this.liczba_wezlow=nH*nB;
        tab_elem=new ElementUniwersalny[liczba_elementow];
        tab_wez=new Wezel[liczba_wezlow];

        int nrElem=0;
        for(int i=0;i<nH-1;i++) {
            for(int j=0;j<nB-1;j++) {
                tab_elem[nrElem]=new ElementUniwersalny();

                tab_elem[nrElem].setID((1+i*(nB))+1+j, //2
                        (1+(i+1)*(nB))+1+j, //6
                        1+(i+1)*(nB)+j, //5
                        1+i*(nB)+j); //2
                nrElem++;
            }
        }
        nrElem=0;
        for(int i=0;i<nH;i++) {
            for(int j=0;j<nB;j++) {
                Boolean krawedz=false;
                if(i==0||i==(nH-1)||j==0||j==(nB-1)) {
                    krawedz=true;
                }else {
                    krawedz=false;
                }
                tab_wez[nrElem]=new Wezel(H/(nH-1)*i,B/(nB-1)*j,temp,krawedz);
                nrElem++;
            }
        }
    }
    public void showSiatka(){
        int liczba=1;
        for(int i=0;i<nH-1;i++) {
            for(int j=0;j<nB-1;j++) {
                System.out.println("Element nr: "+liczba);
                System.out.println(tab_elem[liczba-1].getID0()+"-"+tab_elem[liczba-1].getID1());
                System.out.println("|  |");
                System.out.println(tab_elem[liczba-1].getID3()+"-"+tab_elem[liczba-1].getID2());
                liczba++;
            }
            System.out.println("\nNext column");
        }
    }
    public ElementUniwersalny[] getTabElem() {
        return tab_elem;
    }
    public Wezel[] getTabWez() {
        return tab_wez;
    }
}
