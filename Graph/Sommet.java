public class Sommet{
    public int value;

    Sommet(){
        value = 0;
    }

    Sommet(int val){
        value = val;
    }

    public int getSommet(){
        return value;
    }

    public String toString(){
        return "[" + getSommet() + "]";
    }

    public static void main(String[] args){
        Sommet p1 = new Sommet(5);
        Sommet p2 = new Sommet(7);
        System.out.println(p1);
        System.out.println(p2);

      }
}