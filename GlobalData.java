public class GlobalData {
    public int H, W; //wymiar
    public int nH, nW; //liczba wezlow

    GlobalData(int a, int b, int c, int d){ //konstruktor
        this.H = a;
        this.W = b;
        this.nH = c;
        this.nW = d;
    }

    public int n_o_el() {  return((nH - 1) * (nW - 1)); } // zwraca liczbe elementow

	int n_o_nodes(){ return(nH * nW);} //liczba wezlow

    int element_length(){  return(H / (nH - 1)); } //dlugosc elementu

    int element_width(){ return(W / (nW - 1)); } //szerokosc elementu
};