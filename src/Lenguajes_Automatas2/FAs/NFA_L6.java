package Lenguajes_Automatas2.FAs;

import Utilidades.Lista;
import Utilidades.StringHandler;

/**
 * <h1>Automata Finito No Determinista (NFA)</h1>
 * <p>
 * L6 = {w | w begins with 1}
 * </p>
 * <p>
 * <ul>
 * Definicion formal
 * <li>Conjunto de estados: Q = {q0, q1}</li>
 * <li>Alfabeto de entrada (Σ): {1, 0}</li>
 * <li>Función de transición (δ):
 * <ol>
 * <li>δ(q0, 1) = {q1}</li>
 * <li>δ(q1, 0) = {q1}</li>
 * <li>δ(q1, 1) = {q1}</li>
 * </ol>
 * </li>
 * <li>Estado inicial (q0): q0</li>
 * <li>Conjunto de estados de aceptación (F): {q1}</li>
 * </ul>
 * </p>
 * <p>
 * Descripción: Cadenas conformadas por 1 y 0 que empiezen con 1
 * </p>
 * 
 * @author Luis Emmanuel Torres Olvera
 * 
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
public class NFA_L6 extends StringHandler {

	private static final char[] ALPHABET = { '1', '0' };
	private static Lista<String> estadosFinales = new Lista<>();

	public static void main(String[] args) { // Ejemplo de funcionamiento

		NFA_L6 fa = new NFA_L6();

		if (fa.validateString("01")) { // Esta es una cadena no valida
			System.out.println("Es una cadena valida");
		} else {
			System.out.println("No es una cadena valida");
		}

		if (fa.validateString("10")) { // Esta es una cadena valida
			System.out.println("Es una cadena valida");
		} else {
			System.out.println("No es una cadena valida");
		}

	}

	public boolean validateString(String x) {
		estadosFinales.clear(); // Por si se uso mas de una vez el metodo
		if (validateAlphabet(x, ALPHABET)) {
			q0(x);
			return estadosFinales.contains("q1");
		} else {
			return false;
		}
	}

	private static void q0(String str) {
		if (isEmpty(str)) {
			estadosFinales.addToEnd("q0");
		} else {
			if (getChar(str, 0) == '1') {
				q1(subCadena(str, 1));
			} else {
				estadosFinales.addToEnd("q0");
			}
		}
	}

	private static void q1(String str) {
		if (isEmpty(str)) {
			estadosFinales.addToEnd("q1");
		} else {
			q1(subCadena(str, 1));
		}
	}
}
