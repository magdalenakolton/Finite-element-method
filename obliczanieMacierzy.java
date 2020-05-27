
public class obliczanieMacierzy {

    private double[][] localH;
    private double[] localP;
    private double[][] globalH;
    private double[] globalP;
    private daneGlobalne daneGlob;
    private Siatka Siatka;
    private double[][] punktyCalkowania;
    powLokalna[] powierzchniaLokalna;

    public obliczanieMacierzy(daneGlobalne daneGlob, double[][] globalH, double[] globalP, Siatka siatka) {
        this.daneGlob = daneGlob;
        localH = new double[daneGlob.getNH()][daneGlob.getNB()];
        localP = new double[daneGlob.getNH()];
        this.globalH=globalH;
        this.globalP=globalP;
        this.Siatka=siatka;
        uzupPC();
        uzupPowLok();
    }

    private void uzupPowLok() {
        // wyznaczenie pkt całkowania dla każdej krawędzi (po 2 pkt)
        powierzchniaLokalna = new powLokalna[4];
        powierzchniaLokalna[0] = new powLokalna( new Wezel( - 1.0, 1.0 / Math.sqrt( 3 ),0.1,false ), new Wezel( - 1.0, - 1.0 / Math.sqrt( 3.0 ),0.1,false ) );
        powierzchniaLokalna[1] = new powLokalna( new Wezel( - 1.0 / Math.sqrt( 3 ), - 1.0,0.1,false ), new Wezel( 1.0 / Math.sqrt( 3.0 ), - 1.0 ,0.1,false) );
        powierzchniaLokalna[2] = new powLokalna( new Wezel( 1.0, - 1.0 / Math.sqrt( 3 ) ,0.1,false), new Wezel( 1.0, 1.0 / Math.sqrt( 3.0  ),0.1,false) );
        powierzchniaLokalna[3] = new powLokalna( new Wezel( 1.0 / Math.sqrt( 3 ), 1.0 ,0.1,false), new Wezel( - 1.0 / Math.sqrt( 3.0 ), 1.0 ,0.1,false) );

        // wyliczenie funkcji kształtu dla każdej krawędzi (po 4 funkcje na każdy z dwóch punktów całkowania)
        for ( int i = 0; i < 4; i++ ) {
            for ( int j = 0; j < 2; j++ ) {
                powierzchniaLokalna[i].N[j][0] = N1( powierzchniaLokalna[i].TabWez[j].getX(), powierzchniaLokalna[i].TabWez[j].getY() );
                powierzchniaLokalna[i].N[j][1] = N2( powierzchniaLokalna[i].TabWez[j].getX(), powierzchniaLokalna[i].TabWez[j].getY() );
                powierzchniaLokalna[i].N[j][2] = N3( powierzchniaLokalna[i].TabWez[j].getX(), powierzchniaLokalna[i].TabWez[j].getY() );
                powierzchniaLokalna[i].N[j][3] = N4( powierzchniaLokalna[i].TabWez[j].getX(), powierzchniaLokalna[i].TabWez[j].getY() );
            }
        }
    }

    private void uzupPC() {

        punktyCalkowania = new double[4][2];

        punktyCalkowania[0][0]= -1/Math.sqrt(3);
        punktyCalkowania[0][1]= -1/Math.sqrt(3);

        punktyCalkowania[1][0]=  1/Math.sqrt(3);
        punktyCalkowania[1][1]= -1/Math.sqrt(3);

        punktyCalkowania[2][0]=  -1/Math.sqrt(3);
        punktyCalkowania[2][1]=  1/Math.sqrt(3);

        punktyCalkowania[3][0]=  1/Math.sqrt(3);
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
        Element el;
        double[][] dNpodE= new double[4][4];
        double[][] dNpodN= new double[4][4];
        double[][] N = new double[4][4];

        for ( int i = 0; i < 4; i++ ) {

            N[i][0] = N1( punktyCalkowania[i][0], punktyCalkowania[i][1] );
            N[i][1] = N2( punktyCalkowania[i][0], punktyCalkowania[i][1] );
            N[i][2] = N3( punktyCalkowania[i][0], punktyCalkowania[i][1] );
            N[i][3] = N4( punktyCalkowania[i][0], punktyCalkowania[i][1] );

            dNpodE[i][0] = N1_ksi( punktyCalkowania[i][0] );
            dNpodE[i][1] = N2_ksi( punktyCalkowania[i][0] );
            dNpodE[i][2] = N3_ksi( punktyCalkowania[i][0] );
            dNpodE[i][3] = N4_ksi( punktyCalkowania[i][0] );

            dNpodN[i][0] = N1_eta( punktyCalkowania[i][1] );
            dNpodN[i][1] = N2_eta( punktyCalkowania[i][1] );
            dNpodN[i][2] = N3_eta( punktyCalkowania[i][1] );
            dNpodN[i][3] = N4_eta( punktyCalkowania[i][1] );
        }


        int liczbaElem = Siatka.getTabEl().length;
        double matrixC = 0;
        for(int i=0;i<liczbaElem;i++) {
            localH = new double[daneGlob.getNH()][daneGlob.getNB()];
            localP = new double[daneGlob.getNH()];
            X=new double[daneGlob.getNH()];
            Y=new double[daneGlob.getNB()];
            temps=new double[daneGlob.getNB()];
            el=Siatka.getTabEl()[i];
            id = el.getTabID();

            //Dane elementu i jego węzłów
            for(int j=0;j<4;j++) {
                X[j]=Siatka.getTabWez()[id[j]-1].getX();
                Y[j]=Siatka.getTabWez()[id[j]-1].getY();
                temps[j] = Siatka.getTabWez()[id[j]-1].getTemperature();
            }

            for(int PC = 0; PC<4; PC++) {

                double dXpodE = punktyCalkowania[PC][1]/4*(X[0] - X[1] +X[2] - X[3])  +(-X[0] + X[1] + X[2] - X[3])/4;;
                double dYpodN = punktyCalkowania[PC][0]/4*(Y[0] - Y[1] + Y[2] - Y[3]) +(-Y[0] - Y[1] + Y[2] + Y[3])/4;
                double dYpodE = punktyCalkowania[PC][1]/4*(Y[0] - Y[1] + Y[2] - Y[3]) +(-Y[0] + Y[1] + Y[2] - Y[3])/4;
                double dXpodN = punktyCalkowania[PC][0]/4*(X[0] - X[1] + X[2] - X[3]) +(-X[0] - X[1] + X[2] + X[3])/4;

                double detJ = dXpodE * dYpodN - dYpodE * dXpodN;
                intTemp = 0;

                for(int k=0;k<4;k++) {
                    dNpodX[k]=(dYpodN*dNpodE[PC][k]+(-dXpodN)*dNpodN[PC][k])/detJ;
                    dNpodY[k]=((-dYpodE)*dNpodE[PC][k]+dXpodE*dNpodN[PC][k])/detJ;
                    intTemp += temps[k]*N[PC][k];
                }

                for(int n=0;n<4;n++) {
                    for(int o=0;o<4;o++) {
                        matrixC = daneGlob.getSpecificHeat()*daneGlob.getDensity()*N[PC][n]*N[PC][o]*detJ;
                        localH[n][o] += daneGlob.getConductivity()*(dNpodX[n]*dNpodX[o]+dNpodY[n]*dNpodY[o])*detJ+matrixC/daneGlob.getTimeStamp();
                        localP[n] += matrixC / daneGlob.getTimeStamp() * intTemp;
                    }
                }
            }
            //Warunki brzegowe
            for(int k=0;k<4;k++) {
                // a to id krawędzi którą będziemy rozpatrywać
                int a=(k+1)%4;
                if(Siatka.getTabWez()[id[k%4]-1].getStatus()==true && Siatka.getTabWez()[id[(k+1)%4]-1].getStatus()==true) {
                    double detj = Math.sqrt( Math.pow( Siatka.getTabWez()[id[k%4]-1].getX() - Siatka.getTabWez()[id[(k+1)%4]-1].getX(), 2)
                                + Math.pow( Siatka.getTabWez()[id[k%4]-1].getY() - Siatka.getTabWez()[id[(k+1)%4]-1].getY(), 2)) / 2.0;

                    //Po dwóch punktach
                    for ( int p = 0; p < 2; p++ ) {
                        for ( int n = 0; n < 4; n++ ) {
                            for ( int l = 0; l < 4; l++ )
                                localH[n][l] += daneGlob.getAlfa() * powierzchniaLokalna[(k+1)%4].N[p][n] * powierzchniaLokalna[(k+1)%4].N[p][l] * detj; //dodajemy warunek brzegowy na powierzchni
                                localP[n] += daneGlob.getAlfa() * daneGlob.getAmbientTemp() * powierzchniaLokalna[a].N[p][n] * detj;
                        }
                    }
                }
            }

            //Agregacja
            for(int n=0;n<4;n++) {
                for(int o=0;o<4;o++) {
                    globalH[id[n]-1][id[o]-1]+=localH[n][o];
                }
                globalP[id[n]-1]+=localP[n];
            }
        }
    }

    //////////////////////////////////////
    private double N1_ksi ( double eta ) {
        return ( - ( 1.0 / 4.0 ) * ( 1 - eta ) );
    }
    private double N2_ksi ( double eta ) {
        return ( ( 1.0 / 4.0 ) * ( 1 - eta ) );
    }
    private double N3_ksi ( double eta ) {
        return ( ( 1.0 / 4.0 ) * ( 1 + eta ) );
    }
    private double N4_ksi ( double eta ) {
        return ( - ( 1.0 / 4.0 ) * ( 1 + eta ) );
    }

    //////////////////////////////////////
    private double N1_eta ( double ksi ) {
        return ( - ( 1.0 / 4.0 ) * ( 1 - ksi ) );
    }
    private double N2_eta ( double ksi ) {
        return ( - ( 1.0 / 4.0 ) * ( 1 + ksi ) );
    }
    private double N3_eta ( double ksi ) {
        return ( ( 1.0 / 4.0 ) * ( 1 + ksi ) );
    }
    private double N4_eta ( double ksi ) {
        return ( ( 1.0 / 4.0 ) * ( 1 - ksi ) );
    }

    ////////////////////////////////////
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
        return 0.25 * ( 1.0 - ksi ) * ( 1.0 + eta );
    }

    ////////////////////////////////////

    public static double[] obliczanieRownania ( int numberOfElements, double[][] globalHMatrix, double[] globalPVector ) {

        double m, s, e;
        e = Math.pow( 10, - 12 );
        double[] tabResult = new double[numberOfElements];
        double[][] tabAB = new double[numberOfElements][numberOfElements + 1];

        //////////////////////////////////////////////////

        for ( int i = 0; i < numberOfElements; i++ )
            for ( int j = 0; j < numberOfElements; j++ )
                tabAB[j][i] = globalHMatrix[j][i];

        for ( int i = 0; i < numberOfElements; i++ )
            tabAB[i][numberOfElements] = globalPVector[i];

        //////////////////////////////////////////////////

        for ( int i = 0; i < numberOfElements - 1; i++ ) {
            for ( int j = i + 1; j < numberOfElements; j++ ) {
                if ( Math.abs( tabAB[i][i] ) < e ) {
                    System.err.println( "dzielnik rowny 0" );
                    break;
                }

                m = - tabAB[j][i] / tabAB[i][i];
                for ( int k = 0; k < numberOfElements + 1; k++ )
                    tabAB[j][k] += m * tabAB[i][k];
            }
        }

        for ( int i = numberOfElements - 1; i >= 0; i-- ) {
            s = tabAB[i][numberOfElements];
            for ( int j = numberOfElements - 1; j >= 0; j-- )
                s -= tabAB[i][j] * tabResult[j];

            if ( Math.abs( tabAB[i][i] ) < e ) {
                System.err.println( "dzielnik rowny 0" );
                break;
            }

            tabResult[i] = s / tabAB[i][i];
        }
        return tabResult;
    }
}
