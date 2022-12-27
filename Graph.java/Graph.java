import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Properties;
import java.util.Queue;
import java.util.Set;

public class Graph {

  public List<Arc> relie;
  public Sommet som;

  Graph() {
    relie = null;
    som = null;
  }

  Graph(Sommet first) {
    relie = new ArrayList<Arc>();
    som = first;
    relie.add(new Arc(som, null));
  }

  public String toString() {
    String listString = relie
        .stream()
        .map(Object::toString)
        .collect(Collectors.joining("\n"));
    return listString;
  }

  // Ajoute un sommet sans rien.
  public void ajouteElement(Sommet s1) {
    Arc nl = new Arc(s1, null);
    relie.add(nl);
  }

  // Ajoute un sommet et le relie
  public void ajouteElement(Sommet s1, Sommet s2, int lien) {
    assert (lien == 1 || lien == 2);

    if (lien == 1) {
      Arc tr = new Arc(s1, s2);
      relie.add(tr);
    } else {
      Arc tr = new Arc(s1, s2);
      Arc rt = new Arc(s2, s1);
      relie.add(tr);
      relie.add(rt);
    }
  }

  public void nettoie() {

    Predicate<Arc> condition = s -> s.getFin() == null || s.getFin() != null;
    relie.removeIf(condition);

  }

  public void clean() {
    for (int i = relie.size() - 1; i >= 0; i--) {
      for (int j = i - 1; j >= 0; j--) {
        // System.out.println(i + " " +relie.get(i).getDebut() + relie.get(i).getFin()+"
        // "+ j + relie.get(j).getDebut()+relie.get(j).getFin());
        if ((relie.get(j).getDebut() == relie.get(i).getDebut() &&
            relie.get(j).getFin() == null &&
            relie.get(i).getFin() != null

        )

        ) {
          // System.out.println(i + " " +relie.get(i).getDebut() + relie.get(i).getFin()+"
          // "+ j + relie.get(j).getDebut()+relie.get(j).getFin());

          // System.out.println(relie + " " + relie.get(i) + " " + relie.size() + " i= " +
          // i + " j= " + j );
          relie.remove(j);
          j = 0;
          // i--;

        }
        // System.out.println(relie.get(i).getDebut() + "i = " + i + " j = " + j + "
        // size = " + relie.size()); /
        // System.out.println(relie.get(1).getFin() + "" + relie.get(3).getFin());
      }
    }
  }

  public void ajouteArc(Arc arc) {
    relie.add(arc);
  }

  public void ajouteBoucle(Sommet s1) {
    Arc boucle = new Arc(s1, s1);
    relie.add(boucle);
  }

  public boolean isSimple() {
    for (int i = relie.size() - 1; i >= 0; i--) {
      if (relie.get(i).getDebut() == relie.get(i).getFin()) {
        return false;
      }
      for (int j = i - 1; j >= 0; j--) {
        if (relie.get(j).getDebut() == relie.get(i).getDebut() ||
            relie.get(j).getDebut() == relie.get(i).getFin()) {
          if ((relie.get(j).getDebut() != null &&
              (relie.get(j).getFin() != null) &&
              (relie.get(i).getDebut() != null &&
                  (relie.get(i).getFin() != null)))) {
            return false;
          }
        }
      }
    }
    return true;
  }

  public int degreSommet(Sommet s1) {
    int compteur = 0;
    for (int i = 0; i < relie.size(); i++) {
      if ((relie.get(i).getDebut() == s1 && relie.get(i).getFin() != null || relie.get(i).getFin() == s1)
          && (relie.get(i).getDebut() != relie.get(i).getFin())) {
        compteur++;
      }
      if ((relie.get(i).getDebut() == s1 && relie.get(i).getFin() == s1)) {
        compteur += 2;
      }
    }

    return compteur;
  }

  public boolean isIsole(Sommet s1) {
    return degreSommet(s1) == 0;
  }

  public boolean isSousGraph(Graph g) {
    List<String> thisGraph = relie.stream().map(p -> p.toString()).collect(Collectors.toList());
    List<String> gGraph = g.relie.stream().map(p -> p.toString()).collect(Collectors.toList());
    if (gGraph.containsAll(thisGraph)) {
      return true;
    }
    return false;
  }

  public List<Sommet> adjacentsDebut(Sommet s) {
    List<Sommet> adjacents = new ArrayList<Sommet>();

    for (int i = 0; i < relie.size(); i++) {
      if (relie.get(i).getDebut() == s && (relie.get(i).getDebut() != (relie.get(i).getFin()))) {
        adjacents.add(relie.get(i).getFin());
      }
    }

    return adjacents;
  }

  public int nbrSommet() {
    int cpt = 0;
    List<String> beg = relie.stream().map(p -> p.getDebut().toString()).collect(Collectors.toList());
    List<String> end = relie.stream().map(p -> p.getFin().toString()).collect(Collectors.toList());

    beg.addAll(end);
    List<String> listWithoutDuplicates = beg.stream()
        .distinct()
        .collect(Collectors.toList());

    cpt += listWithoutDuplicates.size();
    return cpt;
  }

  public List<Sommet> SommetsListe() {
    List<Sommet> beg = relie.stream().map(p -> p.getDebut()).collect(Collectors.toList());
    List<Sommet> fin = relie.stream().map(p -> p.getFin()).collect(Collectors.toList());

    beg.addAll(fin);
    List<Sommet> noDuplicates = beg.stream()
        .distinct()
        .collect(Collectors.toList());

    for (int i = 0; i < noDuplicates.size(); i++) {
      if (noDuplicates.get(i) == null) {
        noDuplicates.remove(i);
        i--;
      }
    }

    return noDuplicates;
  }

  public List<Sommet> PathFinding(Sommet s1, Sommet s2) {
    LinkedList<Sommet> queue = new LinkedList<>();
    List<Sommet> visite = new ArrayList<>();
    Map<Sommet, Sommet> predecessorMap = new HashMap<>();
    if (s1 == s2) {
      visite.add(s1);
      return visite;
    }
    predecessorMap.put(s1, null);

    queue.add(s1);

    int i = 0;
    while (!queue.isEmpty()) {

      Sommet vertex = queue.poll();

      visite.add(vertex);
      if (vertex == s2)
        return constructPath(predecessorMap, s2);

      List<Sommet> adj = adjacentsDebut(vertex);

      // System.out.println("!!!!!!------- \n list adj " + adj );
      for (Sommet j : adj) {
        if (!predecessorMap.containsKey(j)) {
          predecessorMap.put(j, vertex);
          queue.add(j);

        }
      }

      i++;
    }

    return visite;

  }

  private List<Sommet> constructPath(Map<Sommet, Sommet> paths, Sommet s2) {
    // Create a list to store the path
    List<Sommet> path = new ArrayList<>();

    // Start at the ending vertex and work backwards
    Sommet vertex = s2;
    while (vertex != null) {
      // Add the current vertex to the front of the list
      path.add(0, vertex);
      // Look up the predecessor of the current vertex in the paths map
      vertex = paths.get(vertex);

    }

    return path;
  }

  public boolean isConnexe() {

    for (Sommet i : SommetsListe()) {
      // System.out.println(i);
      for (Sommet j : SommetsListe()) {
        // System.out.println("-------" + PathFinding(j, i));
        if (PathFinding(j, i).contains(null)) {

          return false;
        }
      }
    }

    return true;
  }

  public boolean containsCycle() {
    // Handle empty and single-vertex graphs
    if (relie == null || relie.size() <= 1) {
      return false;
    }

    // Initialize the visited set
    Set<Sommet> visited = new HashSet<>();

    // Iterate through all the arcs in the graph and perform DFS on each unvisited
    // vertex
    for (Arc arc : relie) {
      Sommet vertex = arc.getDebut();
      if (!visited.contains(vertex) && DFS(vertex, visited)) {
        return true;
      }
    }

    return false;
  }

  public boolean DFS(Sommet s1, Set<Sommet> visited) {
    // Mark the current vertex as visited
    visited.add(s1);

    // Perform DFS on all adjacent vertices
    for (Arc arc : relie) {
      Sommet vertex = arc.getDebut();
      if (vertex.equals(s1)) {
        Sommet adjVertex = arc.getFin();
        if (adjVertex == null) {
          continue;
        }
        if (visited.contains(adjVertex)) {
          // If an adjacent vertex is already marked as visited, then there is a cycle
          return true;
        }
        if (DFS(adjVertex, visited)) {
          return true;
        }
      }
    }

    return false;
  }

  public void WelshandPowell() {
    // Colors
    int noColor = 0;
    int rouge = 1;
    int bleu = 2;
    int vert = 3;
    int jaune = 4;

    // Sort the vertices by degre
    List<Sommet> SommetSorted = new ArrayList<>();
    for (Sommet i : SommetsListe()) {
      SommetSorted.add(i);
    }
    Collections.sort(SommetSorted, new DegComp());

    HashMap<Sommet, Integer> triSommet = new HashMap<>();
    for (Sommet i : SommetSorted) {
      triSommet.put(i, noColor);
    }
   
    //algortihm
    int k = 1;
    for(Sommet i : SommetSorted){
      if(triSommet.get(i)==0){
        if(
          for(Sommet j : )
        )
      }
    }

  }

  class DegComp implements Comparator<Sommet> {
    public int compare(Sommet s1, Sommet s2) {
      if (degreSommet(s1) > (degreSommet(s2))) {
        return 1;
      } else {
        return -1;
      }
    }

    public static void main(String[] args) {
      Sommet p1 = new Sommet(50);
      Sommet s2 = new Sommet(12);
      Sommet s3 = new Sommet(13);
      Sommet s5 = new Sommet(17);
      Sommet s6 = new Sommet(16);
      Sommet s7 = new Sommet(19);
      Sommet s8 = new Sommet(1);
      Graph t = new Graph(p1);
      Graph tCopie = new Graph(s2);
      tCopie.ajouteElement(s3, s2, 2);
      t.ajouteElement(s8);
      // t.ajouteElement(s3, s2, 1);
      // Arc s4 = new Arc(p1, s2);
      // tCopie.ajouteArc(s4);
      // t.ajouteArc(s4);
      // t.ajouteElement(s2, s3, 2);
      t.ajouteElement(s3, s2, 2);
      // t.ajouteElement(s3, s6, 1);
      // t.ajouteElement(s5);
      t.ajouteElement(s5, s2, 2);
      // tCopie.ajouteElement(s6);
      t.ajouteElement(s6, p1, 2);
      // t.ajouteElement(s5, p1, 2);
      t.ajouteElement(s6, s7, 1);
      t.ajouteElement(s7, s3, 1);
      // t.ajouteBoucle(s2);
      t.ajouteBoucle(s3);
      System.out.print("-------------------- \n");
      // System.out.println(t);

      t.clean();

      tCopie.clean();
      System.out.print("Graph : \n" + t);
      System.out.print("\n-------------------- \n");
      // t.foundPath(s6, s6);
      System.out.print("Chemin entre " + p1 + " et " + s5 + "\n \n " + t.PathFinding(p1, s5));
      System.out.print("\n-------------------- \n");
      System.out.print("Graph est cyclique ? " + t.containsCycle());
      System.out.print("\n-------------------- \n");
      System.out.print("Combien de degrés pour? " + " " + s3 + " " + t.degreSommet(s3));
      System.out.print("\n-------------------- \n");
      for (Sommet i : t.SommetsListe()) {
        System.out.print("Degrés " + i + " " + t.degreSommet(i) + "\n");
      }
      System.out.print("\n-------------------- \n");
      t.WelshandPowell();
    }
  }
}
