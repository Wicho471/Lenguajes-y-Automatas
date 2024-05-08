package Lenguajes_Automatas2.FAs;

import Utilidades.StringHandler;

/**
 * <h1>Automata Finito Determinista (DFA)</h1>
 * <p>
 * L = {w | w = (xy)^+}
 * </p>
 * <p>
 * <ul>
 * Definicion formal
 * <li>Conjunto de estados (Q): {q0, q1, q2, q3}</li>
 * <li>Alfabeto de entrada (Σ): {x, y}</li>
 * <li>Función de transición (δ):
 * <ol>
 * <li>δ(q0, x) = {q1}</li>
 * <li>δ(q0, y) = {q3}</li>
 * <li>δ(q1, x) = {q3}</li>
 * <li>δ(q1, y) = {q2}</li>
 * <li>δ(q2, x) = {q1}</li>
 * <li>δ(q2, y) = {q3}</li>
 * <li>δ(q3, x) = {q3}</li>
 * <li>δ(q3, y) = {q3}</li>
 * </ol>
 * </li>
 * <li>Estado inicial (q0): q0</li>
 * <li>Conjunto de estados de aceptación (F): {q1}</li>
 * </ul>
 * </p>
 * <p>
 * Descripción: La expresión regular "(xy)^+" representa una cadena que consiste
 * en una o más repeticiones de la secuencia "xy". Por ejemplo, algunas cadenas
 * que coincidirían con esta expresión regular son "xy", "xyxy", "xyxyxy", etc.
 * </p>
 * 
 * @author Luis Emmanuel Torres Olvera
 * @see
 *      <ol>
 *      Para una mejor comprension del codigo, sugiero revisar los siguietes
 *      metodos (ctrl + click izquierdo → Sobre el metodo)
 *      <li>{@link StringHandler#validateAlphabet(String, char[])}</li>
 *      <li>{@link StringHandler#getChar(String, int)}</li>
 *      <li>{@link StringHandler#subCadena(String, int)}</li>
 *      <li>{@link StringHandler#isEmpty(String)}</li>
 *      </ol>
 */

public class DFA_TokenA extends StringHandler {

	private final static char ALPHABET[] = { 'x', 'y' }; // Alfabeto del lenguaje

	public static void main(String[] args) { // Ejemplo de la validacion de una cadena
		String str = "xyxy";
		if (validateString(str)) {
			System.out.println("La cadena es valida");
		} else {
			System.out.println("La cadena es invalida");
		}
	}

	public static boolean validateString(String str) {
		if (validateAlphabet(str, ALPHABET)) {
			return q0(str);
		} else {
			return false;
		}
	}

	// ---------------Estados de transicion----------------
	private static boolean q0(String str) {
		if (isEmpty(str)) {
			return false;
		}
		char currentChar = getChar(str, 0);
		if (currentChar == 'x') {
			return q1(subCadena(str, 1));
		} else if (currentChar == 'y') {
			return q3(subCadena(str, 1));
		} else {
			return false; // Sentencia solo para satisfacer al compilador
		}
	}

	private static boolean q1(String str) {
		if (isEmpty(str)) {
			return false;
		}
		char currentChar = getChar(str, 0);
		if (currentChar == 'x') {
			return q3(subCadena(str, 1));
		} else if (currentChar == 'y') {
			return q2(subCadena(str, 1));
		} else {
			return false; // Sentencia solo para satisfacer al compilador
		}
	}

	private static boolean q2(String str) {
		if (isEmpty(str)) {
			return true;
		}
		char currentChar = getChar(str, 0);
		if (currentChar == 'x') {
			return q1(subCadena(str, 1));
		} else if (currentChar == 'y') {
			return q3(subCadena(str, 1));
		} else {
			return false; // Sentencia solo para satisfacer al compilador
		}
	}

	private static boolean q3(String str) {
		if (isEmpty(str)) {
			return false;
		}
		char currentChar = getChar(str, 0);
		if (currentChar == 'x') {
			return q3(subCadena(str, 1));
		} else if (currentChar == 'y') {
			return q3(subCadena(str, 1));
		} else {
			return false; // Sentencia solo para satisfacer al compilador
		}
	}
}
