public class Siatka {

    private int numberOfElements, numberofNodes;
    private Element[] elementsArray;
    private Wezel[] nodesArray;
    private int nH;
    private int nB;
    private double H;
    private double B;
    private double temp;

    public Siatka(double H, double B, int nH, int nB, double temp) {
        this.H = H;
        this.B = B;
        this.nB = nB;
        this.nH =nH;
        this.temp = temp;
        this.numberOfElements = (nH-1)*(nB-1);
        this.numberofNodes = nH*nB;
        elementsArray = new Element[numberOfElements];
        nodesArray = new Wezel[numberofNodes];

        int nrElem=0;
        for(int i=0;i<nH-1;i++) {
            for(int j=0;j<nB-1;j++) {
                elementsArray[nrElem]=new Element();
                elementsArray[nrElem].setID(1+i*(nB)+j,
                        1+(i+1)*(nB)+j,
                        (1+(i+1)*(nB))+j+1,
                        (1+i*(nB))+1+j);
                nrElem++;
            }
        }
        nrElem=0;
        for(int i=0;i<nH;i++) {
            for(int j=0;j<nB;j++) {
                Boolean edge=false;
                if(i==0||i==(nH-1)||j==0||j==(nB-1)) edge=true;
                nodesArray[nrElem]=new Wezel(H/(nH-1)*i,B/(nB-1)*j,temp,edge);
                nrElem++;
            }
        }
    }

    public Element[] getTabEl() {
        return elementsArray;
    }
    public Wezel[] getTabWez() {
        return nodesArray;
    }
}
