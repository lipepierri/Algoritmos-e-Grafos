import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;

public class Graph {

    // set de vértices
    protected HashMap< Integer, Vertex> vertex_set;

    // conjunto de 3 vértices isolados
    protected Set<HashMap<Integer, Vertex>> subset_list;

    // é AT-FREE
    Boolean isAT_free = true;

    public Graph() {
        vertex_set = new HashMap<>();
    }

    public void print() {
        for( Vertex v : vertex_set.values())
            v.print();

        System.out.println("");
    }

    public void add_vertex( int id ) {
        Vertex v = new Vertex( id );
        vertex_set.put( v.id, v );
    }

    public void add_edge( Integer id1, Integer id2) {
        Vertex v1 = vertex_set.get( id1 );
        Vertex v2 = vertex_set.get( id2 );

        if ( v1 == null || v2 == null ) {
            if (v1 == null) {
                add_vertex(id1);
                v1 = vertex_set.get( id1 );
            }
            if (v2 == null){
                add_vertex(id2);
                v2 = vertex_set.get( id2 );
            }
        }

        v1.add_neighbor( v2 );
        v2.add_neighbor( v1 );
    }

    // verify if is AT-FREE
    public boolean aTFreeRecognition() {
        this.setAllTreeVertexSubset();

        for( HashMap<Integer, Vertex> subset : subset_list ) {
            if (!this.saoVizinhos(subset)) {
                Set<SubConjuntoDTO> divididos = SubConjuntoDTO.separarSubConjuntos(subset);

                for( SubConjuntoDTO dto : divididos ) {
                    // Z = dto.isolado
                    // Visita todos os caminhos do verticie inicio até o vertice fim
                    this.visita(dto.inicio, dto, new HashSet<Vertex>());

                    if (!this.isAT_free) {
                        System.out.println("");
                        System.out.println("Alerta - Subgrafo com vértices " + dto.inicio.id + ", " + dto.fim.id + " & " + dto.isolado.id + " formam Tripla Asteroidal.\n");

                        return false;
                    }
                }
            }
        }
        return this.isAT_free;
    }
    
    private void setAllTreeVertexSubset() {
        // Estrutura que ira armazenar os conjuntos de 3 vértices isolados
        Set<HashMap<Integer, Vertex>> vertexSubsetList = new HashSet<>();

        // Pra cada vértice no grafo
        for(Integer i: this.vertex_set.keySet()) {
            Vertex v1 = this.vertex_set.get(i);
            for (Integer j: this.vertex_set.keySet()) {
                Vertex v2 = this.vertex_set.get(j);
                for (Integer k: this.vertex_set.keySet()) {
                    Vertex v3 = this.vertex_set.get(k);
                    // Se os 3 vértices forem diferentes
                    if (v1 != v2 && v1 != v3 && v2 != v3) {
                        // Cria um conjunto com os 3 vértices
                        HashMap<Integer, Vertex> subset = new HashMap<Integer, Vertex>();
                        subset.put(v1.id, v1);
                        subset.put(v2.id, v2);
                        subset.put(v3.id, v3);

                        // Se o conjunto não estiver na lista
                        if (!vertexSubsetList.contains(subset)) {
                            // Adiciona o conjunto na lista
                            vertexSubsetList.add(subset);
                        }
                    }
                }
            }
        }

        this.subset_list = vertexSubsetList;
    }

    public boolean saoVizinhos(HashMap<Integer, Vertex> subset) {
        List<Integer> subsetKeyList = new ArrayList<Integer>(subset.keySet());

        Vertex v1 = subset.get(subsetKeyList.get(0));
        Vertex v2 = subset.get(subsetKeyList.get(1));
        Vertex v3 = subset.get(subsetKeyList.get(2));
        
        if (v1.nbhood.containsKey(v2.id) || v1.nbhood.containsKey(v3.id) || v2.nbhood.containsKey(v3.id)) {
            return true;
        }

        return false;
    }

    public void visita( Vertex v, SubConjuntoDTO dto, HashSet<Vertex> visitados) {
        // Se o vértice já foi visitado ou o Grafo não é AT-FREE
        if (visitados.contains(v) || !this.isAT_free) {
            return;
        }

        // Visita o vértice
        visitados.add(v);
        
        // Se o vertice visitado está na vizinhança do isolado ou é o proprio isolado
        if (dto.isolado.nbhood.containsKey(v.id) || dto.isolado.id == v.id) {
            return;
        }

        // Se o vertice é o vertice destino
        if (v.id == dto.fim.id) {
            isAT_free = false;
            return;
        }
        
        // Visita o proximo vertice
        for( Integer i : v.nbhood.keySet() ) {
            Vertex u = v.nbhood.get(i);
            visita(u, dto, new HashSet<Vertex>(visitados));
        }
    }

}