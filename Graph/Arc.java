public class Arc {
    public Sommet debut;
    public Sommet fin;

    Arc(){
        debut = null;
        fin = null;
    }

    Arc(Sommet deb, Sommet 
    end){
        this.debut = deb;
        this.fin = end;
    }

    

    public Sommet getDebut(){
        return debut;
    }

    public Sommet getFin(){
        return fin;
    }

    public void setDebut(Sommet deb){
        debut = deb;
    }
    public void setFin(Sommet end){
        fin = end;
    }

    public String toString(){
        return getDebut() + "->" + getFin() + "\n";
    }



    public static void main(String[] args){
        Sommet p1 = new Sommet(7);
        Sommet p2 = new Sommet(9);
        Arc a = new Arc(p1, p2);
        Arc b = new Arc(p2, p1);
      }
}
