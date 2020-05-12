public class ElementUniwersalny {
    private int tabID[];
    private int pow[];
    public ElementUniwersalny() {
        tabID= new int[4];
        pow = new int[4]; //cztery powierzchnie elementu skonczonego
    }
    public void setPow(int p0,int p1, int p2, int p3) { //dodawanie wartosci f.ksz.
        pow[0]=p0;
        pow[1]=p1;
        pow[2]=p2;
        pow[3]=p3;
    }
    public void setID(int ID0,int ID1,int ID2,int ID3) {
        tabID[0]=ID0;
        tabID[1]=ID1;
        tabID[2]=ID2;
        tabID[3]=ID3;
    }

    public int getID0() {
        return tabID[0];
    }
    public int getID1() {
        return tabID[1];
    }
    public int getID2() {
        return tabID[2];
    }
    public int getID3() {
        return tabID[3];
    }
    public int[] getTabID() {
        return tabID;
    }
}


