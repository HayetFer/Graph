import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Properties;
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

  /*
   * public void nettoie(){
   * 
   * Predicate<Arc> condition = s->s.getFin()==null || s.getFin()!=null;
   * relie.removeIf(condition);
   * 
   * }
   */

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
        // size = " + relie.size());*/
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

  public int degreSommet(int pos, boolean debut) {
    int compteur = 0;
    if (debut) {
      for (int i = relie.size() - 1; i >= 0; i--) {
        if (relie.get(i).getDebut() == relie.get(pos).getDebut() && relie.get(pos).getFin() != null
            && relie.get(i).getFin() != null) {
          compteur++;
        }
        if (relie.get(pos).getDebut() == relie.get(i).getFin() && relie.get(pos).getFin() != null
            && relie.get(i).getFin() != null) {

          compteur++;
        }
      }
    } else {
      for (int i = relie.size() - 1; i >= 0; i--) {
        if (relie.get(i).getFin() == relie.get(pos).getFin() && relie.get(pos).getFin() != null
            && relie.get(i).getFin() != null) {
          compteur++;

        }
        if (relie.get(i).getDebut() == relie.get(pos).getFin() && relie.get(pos).getFin() != null
            && relie.get(i).getFin() != null) {
          compteur++;

        }
      }
    }
    return compteur;
  }

  public boolean isIsole(int pos, boolean debut) {
    return degreSommet(pos, debut) == 0;
  }

  public boolean isSousGraph(Graph g) {
    List<String> thisGraph = relie.stream().map(p -> p.toString()).collect(Collectors.toList());
    List<String> gGraph = g.relie.stream().map(p -> p.toString()).collect(Collectors.toList());
    if (gGraph.containsAll(thisGraph)) {
      return true;
    }
    return false;
  }

  /*
   * public int longueurChemin(int sommet1, int sommet2){
   * int index = 0;
   * int inde = 0;
   * for(int i=0;i<relie.size();i++){
   * if(relie.get(i).getDebut().getSommet()==sommet1){
   * index = i;
   * break;
   * }
   * }
   * for(int i=0;i<relie.size();i++){
   * if(relie.get(i).getFin().getSommet()==sommet2){
   * inde = i;
   * break;
   * }
   * }
   * 
   * while(relie.get(i).getFin()!=relie.get(inde).getFin()){
   * s
   * }
   * return index+inde;
   * }
   */

  public static void main(String[] args) {
    Sommet p1 = new Sommet(50);
    Sommet s2 = new Sommet(12);
    Sommet s3 = new Sommet(13);
    Sommet s5 = new Sommet(17);
    Sommet s6 = new Sommet(16);
    Graph t = new Graph(p1);
    Graph tCopie = new Graph(s2);
    tCopie.ajouteElement(s3, s2, 2);
    t.ajouteElement(s2);
    t.ajouteElement(s3, s2, 1);
    Arc s4 = new Arc(p1, s2);
    tCopie.ajouteArc(s4);
    t.ajouteArc(s4);
    // t.ajouteElement(s2, s3, 2);
    t.ajouteElement(s3, s2, 2);
    // t.ajouteElement(s3, s6, 1);
    // t.ajouteElement(s5);
    t.ajouteElement(s5, s2, 2);
    // tCopie.ajouteElement(s6);
    t.ajouteElement(s6, p1, 2);
    // t.ajouteBoucle(s2);
    /*
     * System.out.print("-------------------- \n");
     * System.out.println(t);
     */
    t.clean();

    tCopie.clean();
    System.out.print(t);
    System.out.print("-------------------- \n");
    // System.out.print(t.longueurChemin(50, 16));
    // System.out.println(tCopie.isSousGraph(t));
  }
}