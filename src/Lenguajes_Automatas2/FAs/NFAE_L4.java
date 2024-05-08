package Lenguajes_Automatas2.FAs;

import Utilidades.Lista;
import Utilidades.StringHandler;

/**
 * <h1>Automata Finito No Determinista con transiciones Epsilon (NFA-ε)</h1>
 * <p>
 * L4 = {w | w is composed by sequences of 10}
 * </p>
 * <p>
 * <ul>
 * Definicion formal
 * <li>Conjunto de estados (Q): {q0, q1, q2, q3}</li>
 * <li>Alfabeto de entrada (Σ): {1, 0}</li>
 * <li>Función de transición (δ):
 * <ol>
 * <li>δ(q0, x) = {q1}</li>
 * <li>δ(q0, 1) = {q1}</li>
 * <li>δ(q0, 0) = {q3}</li>
 * <li>δ(q1, ε) = {q3}</li>
 * <li>δ(q1, 0) = {q2}</li>
 * <li>δ(q2, ε) = {q0}</li>
 * <li>δ(q3, 1) = {q3}</li>
 * <li>δ(q3, 0) = {q3}</li>
 * </ol>
 * </li>
 * <li>Estado inicial (q0): q0</li>
 * <li>Conjunto de estados de aceptación (F): {q2}</li>
 * </ul>
 * </p>
 * <p>
 * Descripción: Cadenas conformadas por secuencias de 10s, como por ejemplo
 * "10", "1010", "101010",
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
 *      <li>{@link Lista#addToEnd(Object)}</li>
 *      <li>{@link Lista#contains(Object)}</li>
 *      <li>{@link Lista#clear()}</li>
 *      </ol>
 */
public class NFAE_L4 extends StringHandler {

	private static Lista<String> estadosFinales = new Lista<>();
	private static final char[] ALPHABET = { '1', '0' };

	public static void main(String[] args) { // Ejemplo de funcionamiento

		NFAE_L4 fa = new NFAE_L4();

		if (fa.validateString("101010")) { // Cadena valida
			System.out.println("La cadena es valida");
		} else {
			System.out.println("La cadena es invalida");
		}

		if (fa.validateString("010101")) { // Cadena no valida
			System.out.println("La cadena es valida");
		} else {
			System.out.println("La cadena es invalida");
		}
	}

	// Metodo para usar externamente
	public boolean validateString(String x) {
		estadosFinales.clear(); // Por si se uso mas de una vez el metodo
		if (validateAlphabet(x, ALPHABET)) {
			q0(x);
			return estadosFinales.contains("q2");
		} else {
			return false;
		}
	}

	// ------------------------Transiciones------------------------
	private static void q0(String str) {
		if (isEmpty(str)) {
			estadosFinales.addToEnd("q0");
		} else {
			if (getChar(str, 0) == '1') {
				q1(subCadena(str, 1));
			} else if (getChar(str, 0) == '0') {
				q3(subCadena(str, 1));
			}
		}
	}

	private static void q1(String str) {
		q3(str);
		if (isEmpty(str)) {
			estadosFinales.addToEnd("q1");
		} else {
			if (getChar(str, 0) == '0') {
				q2(subCadena(str, 1));
			}
		}
	}

	private static void q2(String str) {
		q0(str);
		if (isEmpty(str)) {
			estadosFinales.addToEnd("q2");
		}
	}

	private static void q3(String a) {
		if (isEmpty(a)) {
			estadosFinales.addToEnd("q3");
		} else {
			q3(subCadena(a, 1));
		}
	}
}