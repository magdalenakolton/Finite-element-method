import java.util.Scanner;

public class daneGlobalne {
    private int nH;
    private int nB;
    private double simTime;
    private double timeStamp;
    private double initialTemp;
    private double ambientTemp;
    private double alfa;
    private double H;
    private double B;
    private double cieploWlasciwe;
    private double conductivity;
    private double gestosc;

    public daneGlobalne() {
		nH=4;
		nB=4;
		alfa=25;
		H=0.1;
		B=0.1;
		cieploWlasciwe = 700;
		gestosc = 7800;
    }

    public int getNH() {
        return nH;
    }
    public int getNB() {
        return nB;
    }
    public double getB() {
        return B;
    }
    public double getH() {
        return H;
    }
    public double getGestosc() {
        return gestosc;
    }
    public double getSimTime() {
        return simTime;
    }
    public double getTimeStamp() {
        return timeStamp;
    }
    public double getCieploWlasciwe() {
        return cieploWlasciwe;
    }
    public double getConductivity() {
        return conductivity;
    }
    public double getInitialTemp() {
        return initialTemp;
    }
    public double getAmbientTemp() {
        return ambientTemp;
    }
    public double getAlfa() {
        return alfa;
    }
}
