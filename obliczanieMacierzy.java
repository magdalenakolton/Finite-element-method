public class obliczanieMacierzy {
    private double[][] localH;
    private double[] localP;
    private double[][] globalH;
    private double[] globalP;
    private daneGlobalne daneGlobalne;
    private Siatka siatka;
    private double[][] punktyCalkowania;
    private double[][] localC;
    private Wezel[][] pomoc;
    powierzchniaLokalna[] powierzchnieLokalne;

    public obliczanieMacierzy(daneGlobalne container, double[][] globalH, double[] globalP, Siatka siatka) {
        this.daneGlobalne = daneGlobalne;
        localH = new double[container.getNH()][container.getNB()]; //4x4
        localC = new double[container.getNH()][container.getNB()]; //4x4
        localP = new double[container.getNH()]; //4
        this.globalH=globalH;
        this.globalP=globalP;
        this.siatka=siatka;
        uzupelniamPC();
        fillPomoc();
    }

    private void fillPomoc() {
        // TODO Auto-generated method stub
        powierzchnieLokalne = new powierzchniaLokalna[4];
        powierzchnieLokalne[0] = new powierzchniaLokalna( new Wezel( - 1.0, 1.0 / Math.sqrt( 3 ),0.1,false ), new Wezel( - 1.0, - 1.0 / Math.sqrt( 3.0 ),0.1,false ) );
        powierzchnieLokalne[1] = new powierzchniaLokalna( new Wezel( - 1.0 / Math.sqrt( 3 ), - 1.0,0.1,false ), new Wezel( 1.0 / Math.sqrt( 3.0 ), - 1.0 ,0.1,false) );
        powierzchnieLokalne[2] = new powierzchniaLokalna( new Wezel( 1.0, - 1.0 / Math.sqrt( 3 ) ,0.1,false), new Wezel( 1.0, 1.0 / Math.sqrt( 3.0  ),0.1,false) );
        powierzchnieLokalne[3] = new powierzchniaLokalna( new Wezel( 1.0 / Math.sqrt( 3 ), 1.0 ,0.1,false), new Wezel( - 1.0 / Math.sqrt( 3.0 ), 1.0 ,0.1,false) );

        for ( int i = 0; i < 4; i++ ) {
            for ( int j = 0; j < 2; j++ ) {
                powierzchnieLokalne[i].N[j][0] = N1( powierzchnieLokalne[i].nodes[j].getX(), powierzchnieLokalne[i].nodes[j].getY() );
                powierzchnieLokalne[i].N[j][1] = N2( powierzchnieLokalne[i].nodes[j].getX(), powierzchnieLokalne[i].nodes[j].getY() );
                powierzchnieLokalne[i].N[j][2] = N3( powierzchnieLokalne[i].nodes[j].getX(), powierzchnieLokalne[i].nodes[j].getY() );
                powierzchnieLokalne[i].N[j][3] = N4( powierzchnieLokalne[i].nodes[j].getX(), powierzchnieLokalne[i].nodes[j].getY() );
            }
        }
    }

    private void uzupelniamPC() { //macierz PC, OK

        punktyCalkowania = new double[4][2];

        punktyCalkowania[0][0]= -1/Math.sqrt(3); //1PC
        punktyCalkowania[0][1]= -1/Math.sqrt(3);

        punktyCalkowania[1][0]=  1/Math.sqrt(3); //2PC
        punktyCalkowania[1][1]= -1/Math.sqrt(3);

        punktyCalkowania[2][0]= -1/Math.sqrt(3); //3PC
        punktyCalkowania[2][1]=  1/Math.sqrt(3);

        punktyCalkowania[3][0]=  1/Math.sqrt(3); //4PC
        punktyCalkowania[3][1]=  1/Math.sqrt(3);
    }

    public void licz() {
        double[] dNpodX=new double[4];
        double[] dNpodY=new double[4];
        double[] X;
        double[] Y;
        double[] temps;
        int[] id;
        double intTemp;
        ElementUniwersalny el;
        double[][] dNpodE= new double[4][4];
        double[][] dNpodN= new double[4][4];
        double[][] N = new double[4][4];
        double[][] NPow = new double[2][4];

        for ( int i = 0; i < 4; i++ ) {
            dNpodE[i][0] = N1_ksi( punktyCalkowania[i][0] );
            dNpodE[i][1] = N2_ksi( punktyCalkowania[i][0] );
            dNpodE[i][2] = N3_ksi( punktyCalkowania[i][0] );
            dNpodE[i][3] = N4_ksi( punktyCalkowania[i][0] );

/*
            System.out.println(" dNpodE dla PC " + (i+1));
            System.out.println(dNpodE[i][0]);
            System.out.println(dNpodE[i][1]);
            System.out.println(dNpodE[i][2]);
            System.out.println(dNpodE[i][3]);
            System.out.println(" ");
*/
            dNpodN[i][0] = N1_eta( punktyCalkowania[i][1] );
            dNpodN[i][1] = N2_eta( punktyCalkowania[i][1] );
            dNpodN[i][2] = N3_eta( punktyCalkowania[i][1] );
            dNpodN[i][3] = N4_eta( punktyCalkowania[i][1] );

            N[i][0] = N1( punktyCalkowania[i][0], punktyCalkowania[i][1] );
            N[i][1] = N2( punktyCalkowania[i][0], punktyCalkowania[i][1] );
            N[i][2] = N3( punktyCalkowania[i][0], punktyCalkowania[i][1] );
            N[i][3] = N4( punktyCalkowania[i][0], punktyCalkowania[i][1] );
/*
            System.out.println(" dNpodN dla PC " + (i+1));
            System.out.println("pierwsza dla " + (i+1) + + dNpodN[i][0]);
            System.out.println("druga dla " + (i+1) + dNpodN[i][1]);
            System.out.println("trzecia dla "  + (i+1) + dNpodN[i][2]);
            System.out.println("czwarta dla " + (i+1) + dNpodN[i][3]);
            System.out.println(" ");
*/
        }



        //Petla po elementach
        int liczbaElem = siatka.getTabElem().length;
        double help=0;
        for(int i=0;i<liczbaElem;i++) {
            localH = new double[daneGlobalne.getNH()][daneGlobalne.getNB()];
            localP = new double[daneGlobalne.getNH()];
            X=new double[daneGlobalne.getNH()];
            Y=new double[daneGlobalne.getNB()];
            temps=new double[daneGlobalne.getNB()];
            el=siatka.getTabElem()[i];
            id = el.getTabID();

            //Zerowanie macierzy lokalnych
            for(int j=0;j<4;j++) {
                for(int n=0;n<4;n++) {
                    localH[j][n]=0;
                    localC[j][n]=0;
                }
                localP[j]=0;
            }

            //Dane elementu i jego wezlow
            for(int j=0; j<4; j++) {
                X[j]=siatka.getTabWez()[id[j]-1].getX(); //zapisuje wspolrzedna X wezla danego elementu
                Y[j]=siatka.getTabWez()[id[j]-1].getY(); // -||- y wezla danego elementu
                //temps[j]=siatka.getTabWez()[id[j]-1].getTemperature();
            }

            for(int p = 0; p<4; p++) { //petla po PC
                //System.out.println("Wspolrzedne X wezlow elementu " + p + ": "  + X[0] + " " + X[1] + " " + X[2]+ " " + X[3]);
                //System.out.println("Wspolrzedne Y wezlow elementu " + p + ": "  + Y[0] + " " + Y[1] + " " + Y[2]+ " " + Y[3]);
                //System.out.println(" ");

                double dXpodE=punktyCalkowania[p][1]/4*(X[0] - X[1] +X[2] - X[3])
                        +(-X[0]	+ X[1] + X[2] - X[3])/4;

                double dYpodN=punktyCalkowania[p][0]/4*(Y[0] - Y[1] + Y[2] - Y[3])
                        +(-Y[0] - Y[1] + Y[2] + Y[3])/4;

                double dYpodE=punktyCalkowania[p][1]/4*(Y[0] - Y[1] + Y[2] - Y[3])
                        +(-Y[0] + Y[1] + Y[2] - Y[3])/4;

                double dXpodN=punktyCalkowania[p][0]/4*(X[0] - X[1] + X[2] - X[3])
                        +(-X[0] - X[1] + X[2] + X[3])/4;

                double detJ=dXpodE * dYpodN - dYpodE * dXpodN;

                //Petla po wezlach
                for(int k=0;k<4;k++) {
                    dNpodX[k]=(dYpodN*dNpodE[p][k]+(-dXpodN)*dNpodN[p][k])/detJ;
                    dNpodY[k]=((-dYpodE)*dNpodE[p][k]+dXpodE*dNpodN[p][k])/detJ;
                }

                intTemp = 0;
                //Obliczanie macierzy lokalnej H i P
                for(int n=0;n<4;n++) {
                    for(int o=0;o<4;o++) {
                        localH[n][o] += (dNpodX[n]*dNpodX[o]+dNpodY[n]*dNpodY[o])*detJ;
                        localP[n] += daneGlobalne.getCieploWlasciwe()*daneGlobalne.getGestosc()*N[i][n]*N[i][o]*detJ;
                    }
                }
            }

            System.out.println("******************** \n     Agregacja: \n");
            for(int n=0;n<4;n++) {
                for(int o=0;o<4;o++) {
                    System.out.println("Uzupelniam komorke  x:"+(id[n]-1)+", y:"+(id[o]-1));
                    globalH[id[n]-1][id[o]-1]+=localH[n][o];
                    System.out.println(globalH[id[n]-1][id[o]-1] + " "); // tabela id przechowuje id wezlow danego elementu
                }
                globalP[id[n]-1]+=localP[n];
            }
        }

    }

///////////////////pochodna N po ksi - wzory
    private double N1_ksi ( double eta ) {
        return ( - 0.25 * ( 1 - eta ) );
    }
    private double N2_ksi ( double eta ) {
        return ( 0.25 * ( 1 - eta ) );
    }
    private double N3_ksi ( double eta ) {
        return ( 0.25 * ( 1 + eta ) );
    }
    private double N4_ksi ( double eta ) {
        return ( - 0.25 * ( 1 + eta ) );
    }

////////////////////pochodna N po eta - wzory
    private double N1_eta ( double ksi ) {
        return ( - 0.25 * ( 1 - ksi ) );
    }
    private double N2_eta ( double ksi ) {
        return ( - 0.25 * ( 1 + ksi ) );
    }
    private double N3_eta ( double ksi ) {
        return ( 0.25 * ( 1 + ksi ) );
    }
    private double N4_eta ( double ksi ) {
        return ( 0.25 * ( 1 - ksi ) );
    }

//////////////////////
    private double N1 ( double ksi, double eta ) {
        return 0.25 * ( 1.0 - ksi ) * ( 1.0 - eta );
    }

    private double N2 ( double ksi, double eta ) {
        return 0.25 * ( 1.0 + ksi ) * ( 1.0 - eta );
    }

    private double N3 ( double ksi, double eta ) {
        return 0.25 * ( 1.0 + ksi ) * ( 1.0 + eta );
    }

    private double N4 ( double ksi, double eta ) {
        return ((1.0/4.0) * ( 1.0 - ksi ) * ( 1.0 + eta ));
    }
}
