package Lenguajes_Automatas2.FAs;

import Utilidades.StringHandler;

/**
 * <h1>Automata Finito Determinista (DFA)</h1>
 * <p>
 * L1 = {w | w begins with 0 and ends with 1}
 * </p>
 * <p>
 * <ul>
 * Definicion formal
 * <li>Conjunto de estados (Q): {q0, q1, q2, q3}</li>
 * <li>Alfabeto de entrada (Σ): {1, 0}</li>
 * <li>Función de transición (δ):
 * <ol>
 * <li>δ(q0, 1) = {q3}</li>
 * <li>δ(q0, 0) = {q1}</li>
 * <li>δ(q1, 1) = {q2}</li>
 * <li>δ(q1, 0) = {q1}</li>
 * <li>δ(q2, 1) = {q2}</li>
 * <li>δ(q2, 0) = {q1}</li>
 * <li>δ(q3, 1) = {q3}</li>
 * <li>δ(q3, 0) = {q3}</li>
 * </ol>
 * </li>
 * <li>Estado inicial (q0): q0</li>
 * <li>Conjunto de estados de aceptación (F): {q2}</li>
 * </ul>
 * </p>
 * <p>
 * Descripción: Cadenas que inicien con 0 y terminen con 1
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

public class DFA_L1 extends StringHandler {

	private static final char[] ALPHABET = { '1', '0' };

	public static void main(String[] args) { // Ejemplos de funcionamiento
		DFA_L1 fa = new DFA_L1();

		if (fa.validateString("00001111")) { // Esta es una cadena valida
			System.out.println("Es una cadena valida");
		} else {
			System.out.println("No es una cadena valida");
		}

		if (fa.validateString("11110000")) { // Esta es una cadena no valida
			System.out.println("Es una cadena valida");
		} else {
			System.out.println("No es una cadena valida");
		}
	}

	// Metodo para usar externamente
	public boolean validateString(String x) {
		if (validateAlphabet(x, ALPHABET)) {
			return q0(x);
		} else {
			return false;
		}
	}

	// ------------------------Transiciones------------------------
	// Estado inicial
	private static boolean q0(String a) {
		if (isEmpty(a)) {
			return false;
		}
		char currentChar = getChar(a, 0);
		if (currentChar == '1') {
			return q3(subCadena(a, 1));
		} else if (currentChar == '0') {
			return q1(subCadena(a, 1));
		} else {
			return false; // Sentencia solo para satisfacer el compilador
		}
	}

	private static boolean q1(String a) {
		if (isEmpty(a)) {
			return false;
		}
		char currentChar = getChar(a, 0);
		if (currentChar == '1') {
			return q2(subCadena(a, 1));
		} else if (currentChar == '0') {
			return q1(subCadena(a, 1));
		} else {
			return false; // Sentencia solo para satisfacer el compilador
		}
	}

	private static boolean q2(String a) {
		if (isEmpty(a)) {
			return true;
		}
		char currentChar = getChar(a, 0);
		if (currentChar == '1') {
			return q2(subCadena(a, 1));
		} else if (currentChar == '0') {
			return q1(subCadena(a, 1));
		} else {
			return false; // Sentencia solo para satisfacer el compilador
		}
	}

	// Estado de error
	private static boolean q3(String a) {
		if (isEmpty(a)) {
			return false;
		}
		char currentChar = getChar(a, 0);
		if (currentChar == '1') {
			return q3(subCadena(a, 1));
		} else if (currentChar == '0') {
			return q3(subCadena(a, 1));
		} else {
			return false; // Sentencia solo para satisfacer el compilador
		}
	}
}
