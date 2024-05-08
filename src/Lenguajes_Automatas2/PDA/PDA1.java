package Lenguajes_Automatas2.PDA;

import Utilidades.Pila;
import Utilidades.StringHandler;

/**
 * @author Luis Emmanuel
 * @version 2.0
 * 
 * @apiNote L = {w c w^r | w is in (1+0)^*} M =
 *          ({q0,q1},{0,1},{R,G,B},sigma,q0,R,O)
 */

public class PDA1 extends StringHandler {
	// Variables de tipo global para un facil acceso al codigo
	private static final char[] alfabeto = { '1', '0', 'c' }; // Alfabeto del PDA
	private static Pila<Character> aux = new Pila<Character>(); // Pila auxiliar

	public static void main(String[] args) { // Metodo principal de la clase
		String x = "10c01"; // Esta es la cadena que se va procesar
		System.out.println("Cadena a procesar: " + x);

		if (validateString(x)) { // Se manda llamar el metodo que valida la cadena
			System.out.println("La cadena " + x + " es valida");
		} else {
			System.out.println("La cadena " + x + " es no valida");
		}
	}

	public static boolean validateString(String x) { // Funcion para usar externamente
		aux.clear(); // Se vacia la pila aux por si se uso antes
		boolean ans = false;
		if (!validateAlphabet(x, alfabeto)) { // Primero se valida que la cadena este dentro del alfabeto
			System.out.println("La cadena no se encuentra dentro del alfabeto!");
			return ans;
		}
		aux.push('R'); // Se a�ade el plato rojo al inicio de la pila
		aux.printStack(); // Se imprime
		return q0(x); // Estado inicial q0
	}

	// Estados de transicion
	private static boolean q0(String x) {
		if (x == "") { // Si se acabaron los datos de entrada siginifica que no hay c
			return false;
		}
		char temp = getChar(x, 0); // Lectura del caracter
		System.out.println("\nCaracter a procesar: " + temp);
		if (temp == 'c') {
			return q1(subCadena(x, 1));
		} else {
			aux.push(setValue(temp));
			aux.printStack();
			return q0(subCadena(x, 1));
		}
	}

	private static boolean q1(String x) {
		if (x == "" && aux.peek() == 'R') {
			return true;
		} else if (x == "" && aux.peek() != 'R') {
			return false;
		}
		char temp = getChar(x, 0), temp2 = aux.pop();
		System.out.println("\nCaracter a procesar: " + temp);
		if (temp2 == setValue(temp)) {
			aux.printStack();
			return q1(subCadena(x, 1));
		} else {
			System.out.println("Discrepancia en [" + temp2 + "] y [" + temp + "]");
			return false;
		}
	}

	// Otros metodos
	private static char setValue(char n) {
		if (n == '1') {
			return 'G';
		} else if (n == '0') {
			return 'B';
		} else {
			return ' ';
		}
	}
}
