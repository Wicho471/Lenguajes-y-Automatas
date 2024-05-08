package Lenguajes_Automatas2.Gramaticas;

public class Semantico {
    static String str; // Cadena a analizar
    static int index = 0; // Índice actual en la cadena
    static char chr; // Carácter actual

    public static void main(String[] args) {
        System.out.println("Analizador sintactico para CFG");
        System.out.println("Analiza palíndromos de a's y b's.\n" +
                           "G=({S}, {a, b}, P, S)\n" +
                           "Donde P es:\n" +
                           "• S -> ε\n" +
                           "• S -> a\n" +
                           "• S -> b\n" +
                           "• S -> aSa\n" +
                           "• S -> bSb");

        str = "aaabbb"; // Cadena a analizar
        chr = nextChar(); // Obtener el primer carácter de la cadena

        if (S()) { // Comprueba que se haya alcanzado el final de la cadena
            System.out.println("La cadena \"" + str + "\" pertenece a la gramática.");
        } else {
            System.out.println("La cadena \"" + str + "\" no pertenece a la gramática.");
            syntaxError();
        }
    }

    public static boolean S() {
        if (chr == 'a') {
            char expectedChar = chr; // Guarda el carácter actual para comparar después
            chr = nextChar(); // Siguiente carácter
            if (chr == '/') { // Verifica si es final de la cadena para el caso de 'a' solo
                return true;
            }
            if (S()) { // Recursión
                if (chr == expectedChar) { // Verifica el carácter esperado
                    chr = nextChar(); // Siguiente carácter
                    return true;
                }
            }
            return false;
        } else if (chr == 'b') {
            char expectedChar = chr; // Guarda el carácter actual para comparar después
            chr = nextChar(); // Siguiente carácter
            if (chr == '/') { // Verifica si es final de la cadena para el caso de 'b' solo
                return true;
            }
            if (S()) { // Recursión
                if (chr == expectedChar) { // Verifica el carácter esperado
                    chr = nextChar(); // Siguiente carácter
                    return true;
                }
            }
            return false;
        } else if (chr == '/') { // Fin de la cadena, considerado como ε
            return true;
        }
        return false;
    }

    public static char nextChar() {
        if (index < str.length()) {
            return str.charAt(index++); // Devuelve el carácter actual e incrementa el índice
        }
        return '/'; // Carácter para indicar el fin de la cadena
    }

    public static void syntaxError() {
        System.out.println("Error de sintaxis en la posición " + (index - 1) + ":");
        System.out.println(str);
        for (int i = 0; i < index - 1; i++) {
            System.out.print(" ");
        }
        System.out.println("^");
    }
}
