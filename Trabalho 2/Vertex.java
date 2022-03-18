import java.util.HashMap;

public class Vertex implements Comparable<Vertex> {
    protected Integer id;

    protected HashMap< Integer, Vertex> nbhood;

    private int parent;

    private boolean isVisited;

    public Vertex ( int id ) {
        // id >= 1
        this.id = id;
        nbhood = new HashMap<>();
    }

    public void print() {
        System.out.print("\nId do vértice " + id + ", Vizinhança: " );
        for( Vertex v : nbhood.values())
            System.out.print(" " + v.id );
    }

    // Used to sort by degree
    @Override public int compareTo( Vertex otherVertex ) {
        if( otherVertex.degree() > this.degree())
            return 1;
        else
            return -1;
    }

    public void add_neighbor( Vertex viz ) {
        nbhood.put(viz.id, viz);
    }

    public int degree() {
        return nbhood.size();
    }

    public int getParent() {
        return this.parent;
    }

    public void setParent(int v) {
        this.parent = v;
    }

    public boolean isVisited() {
        return this.isVisited;
    }

    public void visit() {
        this.isVisited = true;
    }

}