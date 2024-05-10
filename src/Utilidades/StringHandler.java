package Utilidades;

/**
 * Clase utilitaria para manejar cadenas de texto en diversas operaciones
 * comunes como validación, extracción de subcadenas, comparación y manipulación
 * de caracteres. Incluye métodos estáticos que facilitan la validación de
 * caracteres dentro de un alfabeto, la manipulación y búsqueda de subcadenas, y
 * la comparación precisa de dos cadenas.
 * <p>
 * Metodos que no pueden ser replicados debido a su alto grado de complejidad
 * debido a que hay trabajar a un nivel bastante bajo, lo cual no es realmente
 * factible en Java puro dado que las cadenas son abstracciones de alto nivel
 * sobre arrays de caracteres y está gestionada por la clase String internamente
 * <ol>
 * <li>{@link java.lang.String#length()}</li>
 * <li>{@link java.lang.String#toCharArray()}</li>
 * </ol>
 * </p>
 * 
 * @author Luis Emmanuel Torres Olvera
 * @version 1.0
 */
public class StringHandler extends Constanes{

	/**
	 * Verifica si cada carácter de la cadena dada está contenido en un conjunto de
	 * caracteres permitidos (alfabeto). Itera a través de cada carácter de la
	 * cadena y lo compara con cada carácter del alfabeto proporcionado. Si un
	 * carácter no pertenece al alfabeto, la función retorna falso inmediatamente.
	 *
	 * @param string   La cadena que será validada.
	 * @param alphabet Un arreglo de caracteres que representa el conjunto de
	 *                 caracteres permitidos.
	 * @return {@code true} si todos los caracteres de la cadena están en el
	 *         alfabeto; {@code false} si al menos uno no lo está.
	 * @see #getChar(String, int)
	 */
	public static boolean validateAlphabet(String string, char[] alphabet) {
		for (int i = 0; i < string.length(); i++) { // For para recorrer la cadena "x"
			for (int j = 0; j < alphabet.length; j++) { // For para recorrer el alfabeto dado "char [] a"
				if (getChar(string, i) == alphabet[j]) { // Se comprueba si la cadena en su posicion "i" es un carcter
					// del alfabeto
					break;
				}
				if (alphabet.length - 1 == j) { // Si se recorrio todo el alfabeto porque no encontro conicidencia, la
												// funcion retorna falso directamente
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Extrae una subcadena de la cadena dada, comenzando desde un índice
	 * especificado hasta el final de la cadena. Esencialmente, realiza la misma
	 * operación que el método {@code String.substring(int startIndex)}.
	 *
	 * @param string     La cadena original de la que se extraerá la subcadena.
	 * @param startIndex El índice desde el cual comenzará la extracción de la
	 *                   subcadena.
	 * @return La subcadena extraída desde el índice especificado hasta el final de
	 *         la cadena original.
	 * @throws IllegalArgumentException Si el indice esta fuera de rango o es
	 *                                  invalido.
	 * @see #getChar(String, int)
	 */
	public static String subCadena(String string, int startIndex) {
		if (startIndex > string.length() || startIndex < 0) {
			throw new IllegalArgumentException("Indice fuera de rango o inválidos");
		}
		String y = "";
		for (int i = startIndex; i < string.length(); i++) {
			y += getChar(string, i);
		}
		return y;
	}

	/**
	 * Extrae una subcadena de una cadena principal, comenzando en el índice
	 * startIndex hasta endIndex - 1. Utiliza un StringBuilder para construir la
	 * subcadena de manera eficiente.
	 *
	 * @param string     La cadena principal de la cual extraer la subcadena.
	 * @param startIndex El índice inicial desde donde comenzar la extracción
	 *                   (inclusive).
	 * @param endIndex   El índice final hasta donde extraer la subcadena
	 *                   (exclusive).
	 * @return La subcadena extraída de la cadena principal.
	 * @throws IllegalArgumentException Si los índices son inválidos o están fuera
	 *                                  de rango.
	 * 
	 * @see #getChar(String, int)
	 */
	public static String subCadena(String string, int startIndex, int endIndex) {
		if (startIndex < 0 || endIndex > string.length() || startIndex > endIndex) {
			throw new IllegalArgumentException("Índices fuera de rango o inválidos");
		}
		String y = "";
		for (int i = startIndex; i < endIndex; i++) {
			y += getChar(string, i);
		}
		return y;
	}

	/**
	 * Retorna el carácter en un índice específico de una cadena dada. Funciona de
	 * manera similar al método {@code String.charAt(int index)}, extrayendo el
	 * carácter de la posición especificada.
	 *
	 * @param string La cadena de la cual se extraerá el carácter.
	 * @param index  El índice del carácter que se desea obtener.
	 * @return El carácter en el índice especificado de la cadena.
	 * @throws ArrayIndexOutOfBoundsException Si el índice proporcionado es negativo
	 *                                        o mayor que el tamaño de la cadena.
	 */
	public static char getChar(String string, int index) throws ArrayIndexOutOfBoundsException {
		char[] data = string.toCharArray();
		return data[index];
	}

	/**
	 * Reemplaza todas las ocurrencias de una subcadena específica en una cadena
	 * principal por otra subcadena. Este método busca iterativamente la subcadena
	 * especificada en {@code search} dentro de la cadena {@code original} y la
	 * reemplaza por la cadena {@code replacement} cada vez que se encuentra una
	 * coincidencia.
	 *
	 * @param soruce    La cadena original donde se realizarán los reemplazos.
	 * @param target      La subcadena que se busca para ser reemplazada. Si esta
	 *                    cadena está vacía, el método retorna la cadena original
	 *                    sin cambios.
	 * @param replacement La cadena que reemplazará cada ocurrencia de
	 *                    {@code search} en {@code original}.
	 * @return Una nueva cadena que resulta de reemplazar todas las ocurrencias de
	 *         {@code search} en {@code original} por {@code replacement}.
	 * @see #subCadena(String, int)
	 * @see #compareStrings(String, String)
	 * @see #getChar(String, int)
	 */
	public static String replace(String soruce, String target, String replacement) {
		if (isEmpty(target)) {
			return soruce; // Retorna la cadena original si la cadena a buscar está vacía.
		}
		String ans = "";
		int i = 0;
		while (i <= soruce.length() - target.length()) {
			String currentSub = subCadena(soruce, i, i + target.length());
			if (compareStrings(currentSub, target)) {
				ans += replacement;
				i += target.length(); // Avanza el índice más allá de la palabra encontrada
			} else {
				ans += getChar(soruce, i);
				i++;
			}
		}
		// Añadir los caracteres restantes que no fueron evaluados por el bucle
		if (i < soruce.length()) {
			ans += subCadena(soruce, i, soruce.length());
		}
		return ans;
	}

	/**
	 * Busca la primera ocurrencia de una subcadena (key) dentro de una cadena
	 * principal (string) y retorna el índice de inicio de esta ocurrencia. Si la
	 * subcadena no se encuentra, retorna -1.
	 * 
	 * @param string La cadena principal en la cual buscar la subcadena.
	 * @param key    La subcadena que se busca dentro de la cadena principal.
	 * @return El índice en el que comienza la subcadena dentro de la cadena
	 *         principal, o -1 si la subcadena no se encuentra.
	 * @see #compareStrings(String, String)
	 * @see #subCadena(String, int, int)
	 */
	public static int contains(String string, String key) {
		for (int i = 0; i < string.length() - key.length(); i++) {
			if (compareStrings(subCadena(string, i, i + key.length()), key)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Comprueba si la cadena proporcionada está vacía.
	 *
	 * Este método devuelve {@code true} si la cadena es nula o no tiene caracteres.
	 * Es útil para validaciones donde las cadenas vacías y las nulas se tratan de
	 * la misma manera.
	 *
	 * @param str la cadena a verificar
	 * @return {@code true} si la cadena es nula o vacía, {@code false} en caso
	 *         contrario
	 * @see #compareStrings(String, String)
	 */
	public static boolean isEmpty(String str) {
		return str == null || compareStrings(str, "");
	}

	/**
	 * Compara dos cadenas de texto para determinar si son exactamente iguales en
	 * contenido y longitud.
	 * <p>
	 * Este método evalúa si dos cadenas, {@code str1} y {@code str2}, tienen la
	 * misma longitud y los mismos caracteres en cada posición. Primero verifica si
	 * las longitudes de ambas cadenas son iguales. Si no lo son, retorna
	 * {@code false} inmediatamente. Si las longitudes son iguales, el método
	 * procede a comparar los caracteres de las cadenas uno a uno.
	 * </p>
	 * <p>
	 * Se convierten ambas cadenas en arrays de caracteres, y se realiza una
	 * comparación elemento por elemento. Si algún par de caracteres no coincide en
	 * la misma posición, el método retorna {@code false}. Si todos los caracteres
	 * coinciden en las mismas posiciones a lo largo de las cadenas, retorna
	 * {@code true}.
	 * </p>
	 *
	 * @param str1 La primera cadena de texto para comparar.
	 * @param str2 La segunda cadena de texto para comparar.
	 * @return {@code true} si las cadenas son iguales en longitud y contenido;
	 *         {@code false} de lo contrario.
	 */
	public static boolean compareStrings(String str1, String str2) {
		if (str1.length() != str2.length()) {
			return false;
		}
		char charStr1[] = str1.toCharArray();
		char charStr2[] = str2.toCharArray();
		for (int i = 0; i < str1.length(); i++) {
			if (charStr1[i] != charStr2[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Revierte el orden de los caracteres en una cadena proporcionada.
	 * <p>
	 * Este método toma una cadena de entrada y devuelve una nueva cadena cuyos
	 * caracteres están en el orden inverso. Implementa la inversión mediante un
	 * bucle que recorre la cadena de entrada desde el final hasta el principio,
	 * acumulando cada carácter en una nueva cadena de resultado. Esta
	 * implementación evita el uso de {@code StringBuilder} o cualquier otra clase
	 * auxiliar, siguiendo el requisito de minimizar la dependencia de clases y
	 * métodos predefinidos.
	 * </p>
	 * <p>
	 * Si la cadena de entrada es {@code null}, el método retorna {@code null}. Si
	 * la cadena tiene una longitud de 1 o menos, se devuelve tal cual porque ya
	 * está en su forma invertida. Este enfoque asegura que el método maneje casos
	 * básicos y excepcionales de manera adecuada.
	 * </p>
	 *
	 * @param str La cadena de texto que se desea invertir.
	 * @return Una nueva cadena con los caracteres en orden inverso. Si la cadena
	 *         original es nula, se devuelve nula.
	 * @see #getChar(String, int)
	 */
	public static String reverse(String str) {
		if (str == null) {
			return null;
		}
		if (str.length() <= 1) {
			return str;
		}
		String rev = "";
		for (int i = str.length() - 1; i >= 0; i--) {
			rev += getChar(str, i);
		}
		return rev;
	}

	/**
	 * Verifica si un carácter dado es un carácter en blanco. Los caracteres en
	 * blanco incluyen espacios (' '), saltos de línea ('{@code \n}'), retornos de
	 * carro ('{@code \r}'), tabulaciones ('{@code \t}') y avances de página
	 * ('{@code \f}').
	 * 
	 * @param c el carácter a verificar
	 * @return true si el carácter es un carácter en blanco, false en caso contrario
	 */
	public static boolean isWhiteSpace(char c) {
		return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == '\f';
	}

	// ------------------------Otros------------------------
	/**
	 * Remplaza todos los elementos no visibles por carcteres visibles para una
	 * mejor comprension de la cadena
	 * 
	 * @param str
	 * @return Retorna la nueva cadena ahora con todos los elementos visibles
	 */
	public static String debugString(String str) {
		String aux = "";
		for (int i = 0; i < str.length(); i++) {
			if (getChar(str, i) == '\r') {
				aux += "/r";
			} else if (getChar(str, i) == '\t') {
				aux += "/t";
			} else if (getChar(str, i) == '\f') {
				aux += "/f";
			} else if (getChar(str, i) == '\n') {
				aux += "/n";
			} else if (getChar(str, i) == ' ') {
				aux += "-";
			} else {
				aux += getChar(str, i);
			}
		}
		return aux;
	}

	public static void printPDAtransition(int state, char currentChar, char pilaChar) {
		System.out.println("Transicion -> δ(q" + state + ", " + currentChar + ", " + Character.toUpperCase(pilaChar)
				+ ") = {(q0, " + Character.toUpperCase(currentChar) + Character.toUpperCase(pilaChar) + ")}");
	}

	public static void printFAtransition() {

	}
} // Llave final de clase
