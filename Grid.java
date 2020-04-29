public class Grid {
    GlobalData a;
    Node[] nodes;
    Element[] elements;

    Grid(GlobalData a) {
        this.a = a;
        nodes = new Node[a.n_o_nodes()];
        elements = new Element[a.n_o_el()];
    }

    void nodes_generation() {
        int k = 0;
        for (int i = 0; i < a.nH; i++) {
            for (int j = 0; j < a.nW; j++) {
                nodes[k].x = i * a.element_width();
                nodes[k].y = j * a.element_length();
                k++;
            }
        }
    }
}