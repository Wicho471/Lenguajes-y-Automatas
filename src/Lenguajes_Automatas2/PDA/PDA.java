package Lenguajes_Automatas2.PDA;

import Utilidades.Pila;
import Utilidades.StringHandler;

/**
 * <h1>Automata de pila (PDA)</h1>
 * <ol>
 * <li>L = { w | w is a palindrome of a's and b's}</li>
 * 
 * <li>PDA = (Q, Σ, Γ, δ, q0, Z, F) donde:</li>
 * <ul>
 * <li>Q = {q0, q1, q2}</li>
 * <li>Σ = {a, b}</li>
 * <li>Γ = {A, B, Z}</li>
 * <li>q0 = estado inicial</li>
 * <li>Z = símbolo inicial de la pila</li>
 * <li>F = {q2}</li>
 * </ul>
 * 
 * <li>Transiciones de Apilamiento:</li>
 * <ul>
 * <li>δ(q0, a, Z) = {(q0, AZ)}</li>
 * <li>δ(q0, b, Z) = {(q0, BZ)}</li>
 * <li>δ(q0, a, A) = {(q0, AA)}</li>
 * <li>δ(q0, b, A) = {(q0, BA)}</li>
 * <li>δ(q0, a, B) = {(q0, AB)}</li>
 * <li>δ(q0, b, B) = {(q0, BB)}</li>
 * </ul>
 * 
 * <li>Transición de Cambio de Fase:</li>
 * <ul>
 * <li>δ(q0, ε, Z) = {(q1, Z)}</li>
 * <li>δ(q0, ε, A) = {(q1, A)}</li>
 * <li>δ(q0, ε, B) = {(q1, B)}</li>
 * </ul>
 * 
 * <li>Transiciones de Desapilamiento:</li>
 * <ul>
 * <li>δ(q1, a, A) = {(q1, ε)}</li>
 * <li>δ(q1, b, B) = {(q1, ε)}</li>
 * </ul>
 * 
 * <li>Transición a Estado de Aceptación:</li>
 * <ul>
 * <li>δ(q1, ε, Z) = {(q2, ε)}</li>
 * </ul>
 * </ol>
 */
public class PDA extends StringHandler {
	// Variables de tipo global para un facil acceso al codigo
	private static final char[] alfabeto = { 'a', 'b' }; // Alfabeto del PDA
	private static Pila<Character> pila = new Pila<Character>(); // Pila auxiliar

	public static void main(String[] args) { // Metodo principal de la clase

		String x = "abba"; // Esta es la cadena que se va procesar
		System.out.println("Cadena a procesar: " + x);

		if (validateString(x)) { // Se manda llamar el metodo que valida la cadena
			System.out.println("La cadena " + x + " es valida");
		} else {
			System.out.println("La cadena " + x + " es no valida");
		}
	}

	public static boolean validateString(String x) { // Funcion para usar externamente
		pila.clear(); // Se vacia la pila aux por si se uso antes
		if (!validateAlphabet(x, alfabeto) && x.length() % 2 == 0) { // Primero se valida que la cadena este dentro del alfabeto
			System.out.println("La cadena no se encuentra dentro del alfabeto!");
			return false;
		}
		pila.push('Z'); // Se a�ade el plato rojo al inicio de la pila
		pila.printStack(); // Se imprime

		int i = 0;
		char currentChar;
		// Fase de apilamiento: leer y apilar caracteres hasta la mitad
		while (i < x.length() / 2) {
			currentChar = x.charAt(i++);
			printPDAtransition(0,currentChar, pila.peek());
			pila.push(currentChar);
			pila.printStack();
		}

		// Si la longitud es impar, omitir el caracter del medio
		if (x.length() % 2 != 0) {
			i++;
			pila.printStack();
		}

		// Fase de desapilamiento: verificar si la segunda mitad refleja la primera
		while (true) {
			currentChar = x.charAt(i++);
			printPDAtransition(1,currentChar, pila.peek());
			if (pila.pop() != currentChar) {
				return false;
			}
			if (pila.peek() == 'Z') {
				if (i == x.length()) {
					return true;
				} else {
					return false;
				}
			}
			pila.printStack();
		}
	}
}
