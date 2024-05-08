package Lenguajes_Automatas2.FAs;

import Utilidades.StringHandler;

/**<h1>Automata Finito Determinista (DFA)</h1>
 * <p>
 * L = {w | w y(x+y)^*}
 * </p>
 * <p>
 * <ul>
 * Definicion formal
 * <li>Conjunto de estados (Q): {q0, q1, q2}</li>
 * <li>Alfabeto de entrada (Σ): {x, y}</li>
 * <li>Función de transición (δ):
 * <ol>
 * <li>δ(q0, y) = {q1}</li>
 * <li>δ(q1, x) = {q1}</li>
 * <li>δ(q1, y) = {q1}</li>
 * <li>δ(q0, x) = {q2}</li>
 * <li>δ(q2, x) = {q2}</li>
 * <li>δ(q2, y) = {q2}</li>
 * </ol>
 * </li>
 * <li>Estado inicial (q0): q0</li>
 * <li>Conjunto de estados de aceptación (F): {q1}</li>
 * </ul>
 * </p>
 * <p>
 * Descripcion: La expresion regular "y(x+y)^*" representa una cadena que
 * comienza con "y" seguida opcionalmente por una secuencia de "x" o "y", que
 * puede repetirse cero o más veces. Por ejemplo, algunas cadenas que
 * coincidirían con esta expresión regular serían "yx", "yyx", "yyxy", "yyxyyx",
 * etc.
 * </p>
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
public class DFA_TokenB extends StringHandler {

	private final static char ALPHABET[] = { 'x', 'y' };

	public static void main(String[] args) { //Ejemplo de la validacion de una cadena
		String str = "yyxyyx";
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
	
	//Estados de transicion
	private static boolean q0(String str) {
		if (isEmpty(str)) {
			return false;
		} 
		char currentChar = getChar(str, 0);
		if (currentChar=='x') {
			return q2(subCadena(str, 1));
		} else if (currentChar=='y') {
			return q1(subCadena(str, 1));
		} else {
			return false; //Sentencia solo para satisfacer al compilador
		}
	}
	private static boolean q1(String str) {
		if (isEmpty(str)) {
			return true;
		} 
		char currentChar = getChar(str, 0);
		if (currentChar=='x') {
			return q1(subCadena(str, 1));
		} else if (currentChar=='y') {
			return q1(subCadena(str, 1));
		} else {
			return false; //Sentencia solo para satisfacer al compilador
		}
	}
	
	private static boolean q2(String str) {
		if (isEmpty(str)) {
			return false;
		}  
		char currentChar = getChar(str, 0);
		if (currentChar=='x') {
			return q2(subCadena(str, 1));
		} else if (currentChar=='y') {
			return q2(subCadena(str, 1));
		} else {
			return false; //Sentencia solo para satisfacer al compilador
		}
	}
}
