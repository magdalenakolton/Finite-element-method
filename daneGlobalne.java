
public class daneGlobalne {

    private double initialTemp;
    private double simulationTime;
    private double simulationStepTime;
    private double ambientTemp;
    private double alfa;
    private double H;
    private double B;
    private int nH;
    private int nB;
    private double specificHeat;
    private double conductivity;
    private double density;

    public daneGlobalne() {
        nH=4;
        nB=4;
        alfa=300;
        H=0.1;
        B=0.1;
        specificHeat = 700;
        density = 7800;
        simulationTime=500;
        simulationStepTime=50;
        initialTemp=100;
        ambientTemp=1200;
        conductivity=25;
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
    public double getDensity() {
        return density;
    }
    public double getSimTime() {
        return simulationTime;
    }
    public double getTimeStamp() {
        return simulationStepTime;
    }
    public double getSpecificHeat() {
        return specificHeat;
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
