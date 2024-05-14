package Utilidades;

public class Main extends StringHandler{
	
    public static void main(String[] args) {
    	
    	String s1 = new String("Hello");
        String s2 = new String("Hello");

        System.out.println("s1 hashCode: " + s1.hashCode());
        System.out.println("s2 hashCode: " + s2.hashCode());

        System.out.println("s1 identityHashCode: " + System.identityHashCode(s1));
        System.out.println("s2 identityHashCode: " + System.identityHashCode(s2));
        
    	String hexadecimal = Integer.toHexString(s1.hashCode());
        System.out.println("El número " + s1.hashCode() + " en hexadecimal es: " + hexadecimal);
  

        Integer a;
	}
}
