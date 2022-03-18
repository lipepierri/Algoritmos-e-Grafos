import java.util.*;

public class SubConjuntoDTO {
  public Vertex inicio;
  public Vertex fim;
  public Vertex isolado;

  public SubConjuntoDTO(Vertex inicio, Vertex fim, Vertex isolado) {
    this.inicio = inicio;
    this.fim = fim;
    this.isolado = isolado;
  }

  public static Set<SubConjuntoDTO> separarSubConjuntos(HashMap<Integer, Vertex> vertex_set) {
    Set<SubConjuntoDTO> subconjuntos = new HashSet<SubConjuntoDTO>();

    List<Integer> keys = new ArrayList<Integer>(vertex_set.keySet());

    Vertex v1 = vertex_set.get(keys.get(0));
    Vertex v2 = vertex_set.get(keys.get(1));
    Vertex v3 = vertex_set.get(keys.get(2));

    SubConjuntoDTO subconjunto1 = new SubConjuntoDTO(v1, v2, v3);
    SubConjuntoDTO subconjunto2 = new SubConjuntoDTO(v1, v3, v2);
    SubConjuntoDTO subconjunto3 = new SubConjuntoDTO(v2, v3, v1);

    subconjuntos.add(subconjunto1);
    subconjuntos.add(subconjunto2);
    subconjuntos.add(subconjunto3);

    return subconjuntos;
  }
}