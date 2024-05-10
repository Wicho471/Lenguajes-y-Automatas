package Utilidades;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * LexicalUtility es una clase enfocada a faciliar el trabajo gracias a la
 * reutilizacion de codigo en las distintas versiones del analizador lexico de
 * la version 1 a la 10, analizador lexico minimo, analizador lexico basico, y
 * analizador lexico para expresiones artmeticas simples (pEAS)
 * 
 * @author Luis Emmanuel Torres Olvera
 * @see {@link StringHandler}, {@link Lista}, {@link myToken}
 * @version 1.0
 */
public class LexicalUtility extends StringHandler {

	protected static Lista<myToken> symbolTable = new Lista<myToken>();
	protected static Lista<myToken> tokenTable = new Lista<myToken>();
	
	private final static char DELIMITER = ';';

	// Lexical v1
	/**
	 * Lee el contenido completo de un archivo de texto ubicado en la ruta
	 * especificada y lo devuelve como una cadena de texto.
	 * 
	 * @param path La ruta al archivo de texto que se desea leer.
	 * @return El contenido del archivo como una cadena de texto. Si el archivo no
	 *         se encuentra, retorna una cadena vacía y muestra un mensaje de error
	 *         en la consola de errores.
	 * @throws FileNotFoundException Si el archivo especificado en {@code dir} no
	 *                               existe o no puede ser leído.
	 */
	protected static String getTextFile(String path) throws FileNotFoundException{
		String text = "";
		File doc = new File(path);
		Scanner read = null;
		try {
			read = new Scanner(doc);
			if (read.hasNextLine()) {
				text += read.nextLine(); // Agrega la primera línea sin añadir un salto de línea
			}
			while (read.hasNextLine()) {
				text += "\n" + read.nextLine(); // Para las siguientes líneas, añade primero un salto de línea
			}
		} catch (FileNotFoundException e) {
			System.out.println("Archivo no encontrado en la dirección: " + path);
			throw new FileNotFoundException(); //Se finaliza la ejecucion del programa
		} finally {
			if (read != null) {
				read.close(); // Asegúrate de cerrar el Scanner para liberar recursos
			}
		}
		return text;
	}

	// Lexical v2
	/**
	 * Elimina todos los espacios en blanco, incluidos espacios, tabulaciones,
	 * saltos de línea y retornos de carro, de una cadena dada.
	 *
	 * @param str La cadena de la cual se eliminarán los espacios en blanco.
	 * @return Una nueva cadena que contiene todos los caracteres de {@code x}
	 *         excepto los espacios en blanco.
	 */
	protected static String clearWhiteSpaces(String str) {
		String newStr = "";
		for (int i = 0; i < str.length(); i++) {
			if (!isWhiteSpace(getChar(str, i))) {
				newStr += getChar(str, i);
			}
		}
		return newStr;
	}

	// Lexical v3
	/**
	 * Elimina todos los comentarios de un código fuente dado en formato de cadena.
	 * Soporta la eliminación de comentarios de una sola línea, que comienzan con
	 * "//", y comentarios de múltiples líneas, que comienzan con "/*" y terminan
	 * con "* /".
	 *
	 * @param str La cadena de texto que contiene el código fuente del cual se
	 *            eliminarán los comentarios.
	 * @return Una nueva cadena que contiene el código fuente con los comentarios
	 *         eliminados.
	 *
	 * @throws IndexOutOfBoundsException Si un comentario no está correctamente
	 *                                   cerrado antes del final de la cadena.
	 */
	protected static String deleteComments(String str) {
		String newString = "";
		for (int i = 0; i < str.length(); i++) { // For para recorrer caracter por caracter
			try {
				// Aqui se verifica si hay el token de comentario para una sola linea
				if (getChar(str, i) == '/' && getChar(str, i + 1) == '/') {
					i += 2;
					while (!(getChar(str, i) == '\n')) {
						i++;
					}
					// Aqui se verifica si hay el token de comentario para multiples lineas
				} else if (getChar(str, i) == '/' && getChar(str, i + 1) == '*') {
					i += 2;
					while (!(getChar(str, i) == '*' && getChar(str, i + 1) == '/')) {
						i++;
					}
					i++;
				} else {
					newString += getChar(str, i);
				}
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Comentario al final");
				return newString;
			}
		}
		return newString;
	}

	

	// Lexical v5
	/**
	 * Busca en una cadena de texto proporcionada y registra los números de línea
	 * donde aparece un delimitador específico. Cada vez que se encuentra el
	 * delimitador, el número de línea actual se añade a una lista.
	 *
	 * @param string La cadena de texto en la que se buscarán las ocurrencias del
	 *               delimitador.
	 * @return Una lista de enteros, donde cada entero representa el número de línea
	 *         en la que se encontró el delimitador.
	 * @apiNote Es necesario que la cadena no tenga ningun comentario para que
	 *          funcione correctamente
	 */
	protected static Lista<Integer> searchLines(String string) { // Metodo para buscar en que linea estan las cadenas
		Lista<Integer> lines = new Lista<>();
		int cont = 1;
		for (int i = 0; i < string.length(); i++) {
			if (getChar(string, i) == '\n') {
				cont++;
			}
			if (getChar(string, i) == DELIMITER) {
				lines.addToEnd(cont);
			}
		}
		// Aqui se agrega la linea de cada lexema
		for (int i = 0; i < lines.getSize(); i++) {
			tokenTable.getElement(i).addLine(lines.getElement(i));
		}

		return lines;
	}

	// Analizador Lexico Minimo
	/**
	 * Encuentra la posición de cada subcadena en un texto multilínea.
	 *
	 * Este método analiza un texto que puede contener múltiples líneas separadas
	 * por saltos de línea. Cada línea puede contener varias subcadenas separadas
	 * por uno o más espacios. Devuelve una lista de objetos que representan la
	 * posición (fila y columna) de cada subcadena encontrada.
	 *
	 * @param text el texto multilínea a analizar
	 * @return lista de objetos que representan la posición de cada subcadena
	 */
	protected static Lista<String> searchLinesAndColums(String text) {
		String[] lines = text.split("\n"); // Divide el texto en líneas
		Lista<String> positions = new Lista<>();

		for (int i = 0; i < lines.length; i++) {
			String[] substrings = lines[i].trim().split("\\s+"); // Divide cada línea en subcadenas por uno o más
																	// espacios
			for (int j = 0; j < substrings.length; j++) {
				if (!substrings[j].isEmpty()) {
					positions.addToEnd(String.format("La cadena \"%s\" se encuentra en la fila %d columna %d",
							substrings[j], i + 1, j + 1));
				}
			}
		}
		return positions;
	}

	// Lexical v6
	/**
	 * Divide una cadena de texto en múltiples subcadenas basándose en un
	 * delimitador específico (en este caso, un punto y coma ';'). Cada vez que se
	 * encuentra el delimitador, la subcadena acumulada hasta ese punto se agrega a
	 * una lista y se reinicia el acumulador.
	 *
	 * @param str La cadena de texto que será dividida en subcadenas.
	 * @return Una lista que contiene todas las subcadenas resultantes, excluyendo
	 *         los delimitadores.
	 */
	protected static Lista<String> splitStrings(String str) { // Separa las cadenas con un caracter especial ";"
		String aux = "";
		Lista<String> strings = new Lista<String>(); // Se crea un ArrayList debido a que no se sabe con exactidud el
														// numero de cadenas que contenga "x"
		for (int i = 0; i < str.length(); i++) {
			if (getChar(str, i) == DELIMITER) {
				strings.addToEnd(aux);
				aux = "";
			} else {
				aux += getChar(str, i);
			}
		}
		// Aqui se agrega el lexema a la tabla
		for (int i = 0; i < strings.getSize(); i++) {
			myToken lexeme = new myToken(strings.getElement(i));
			tokenTable.addToEnd(lexeme);
		}
		return strings;
	}

	// AnalizadorLexicoMinimo
	/**
	 * Separa una cadena original en subcadenas basándose en los espacios en blanco
	 * como delimitadores. Este método recorre cada carácter de la cadena original y
	 * construye subcadenas que no contienen espacios. Cada subcadena se añade a una
	 * lista cuando se encuentra un carácter de espacio en blanco, y después se
	 * reinicia la construcción de la siguiente subcadena. Si al finalizar la cadena
	 * original aún queda una subcadena sin añadir (porque no terminaba en espacio),
	 * esta se añade a la lista.
	 *
	 * @param original La cadena de texto original que será dividida en subcadenas.
	 *                 Cada parte es separada por uno o más espacios en blanco.
	 * @return Una {@link Lista} de cadenas, donde cada elemento es una subcadena de
	 *         {@code original} que estaba separada por uno o más espacios en
	 *         blanco.
	 */
	protected static Lista<String> separateStrings(String original) {
		Lista<String> aux = new Lista<String>();
		StringBuilder string = new StringBuilder();
		for (int i = 0; i < original.length(); i++) {
			char currentChar = getChar(original, i);
			if (Character.isWhitespace(currentChar)) {
				if (string.length() > 0) {
					aux.addToEnd(string.toString());
					string.setLength(0);
				}
			} else {
				string.append(currentChar);
			}
		}
		if (!string.isEmpty()) {
			aux.addToEnd(string.toString());
		}
		return aux;
	}

	// Lexical v7
	/**
	 * Consolida tokens en una lista asegurando que cada lexema sea único y todas
	 * sus líneas asociadas estén en un solo token. Esto se hace sin usar
	 * estructuras de control predefinidas en Java como Map.
	 * 
	 * @param table Lista de tokens que pueden tener lexemas repetidos con
	 *              diferentes líneas.
	 * @return Una nueva lista de tokens consolidados por lexema, con todas las
	 *         líneas combinadas en un solo token por lexema.
	 */
	protected static Lista<myToken> getSymbolTable(Lista<myToken> table) {
	    Lista<myToken> uniqueTokens = new Lista<>(); // Lista para almacenar tokens consolidados.
	    
	    for (int i = 0; i < table.getSize(); i++) {
	        myToken currentToken = table.getElement(i);
	        boolean found = false;

	        // Buscar en la lista de tokens únicos para ver si el lexema ya está.
	        for (int j = 0; j < uniqueTokens.getSize(); j++) {
	            myToken existingToken = uniqueTokens.getElement(j);
	            if (compareStrings(existingToken.getLexeme(), currentToken.getLexeme())) {
	                // Si el lexema ya existe, añadir todas las líneas del token actual al token existente.
	                existingToken.addLine(currentToken.getLines());
	                found = true;
	                break;
	            }
	        }

	        if (!found) {
	            // Si el lexema no está en uniqueTokens, añadir el token a la lista.
	            myToken newToken = new myToken(currentToken.getToken(), currentToken.getLexeme(), currentToken.getLines(), currentToken.getValue());
	            uniqueTokens.addToEnd(newToken);
	        }
	    }
	    symbolTable = uniqueTokens;
	    return uniqueTokens;
	}

	/**
	 * <p>
	 * Imprime una tabla con los detalles de cada token en una lista de tokens. La
	 * tabla se formatea para asegurar que las columnas estén alineadas
	 * correctamente, basándose en la longitud máxima de los elementos en cada
	 * columna. Esto incluye los campos de token, lexema, líneas y valor.
	 * </p>
	 * <p>
	 * El método calcula el ancho máximo necesario para cada columna antes de
	 * imprimir los encabezados y los valores de cada token, de modo que los
	 * encabezados y valores están alineados verticalmente de forma coherente.
	 * </p>
	 * <ol>
	 * Pasos del método:
	 * <li>Calcula el ancho máximo necesario para cada columna basándose en la
	 * longitud de los strings de cada campo en todos los tokens. Esto asegura que
	 * todas las entradas en una columna específica estén alineadas
	 * correctamente.</li>
	 * <li>Imprime los encabezados de las columnas usando los anchos máximos
	 * calculados para garantizar la alineación.</li>
	 * <li>Itera a través de cada token en la lista, imprimiendo cada uno en una
	 * nueva línea con el mismo formato utilizado para los encabezados para mantener
	 * la consistencia de la alineación.</li>
	 * </ol>
	 * 
	 * @param table Lista de tokens que se imprimirán en la tabla. Cada token debe
	 *               tener un tipo de token, un lexema, una lista de líneas y un
	 *               valor opcional.
	 */
	public static void printTable(Lista<myToken> table) {
		 
		int maxTokenLen = "Token".length();
		int maxLexemeLen = "Lexema".length();
		int maxLinesLen = "Líneas".length();
		int maxValueLen = "Valor".length();
		int maxColumLen = "Columna".length();

		// Determinar el ancho máximo necesario para cada columna
		for (int i = 0; i < table.getSize(); i++) {
			myToken token = table.getElement(i);
			maxTokenLen = Math.max(maxTokenLen, token.getToken().length());
			maxLexemeLen = Math.max(maxLexemeLen, token.getLexeme().length());
			maxLinesLen = Math.max(maxLinesLen, token.getLines().listToString().length());
			if (token.getValue() != null) {
				maxValueLen = Math.max(maxValueLen, token.getValue().length());
			}
			if (token.getCol() != null) {
				maxColumLen = Math.max(maxColumLen, Integer.toString(token.getCol()).length());
			}
		}

		// Encabezados de la tabla
		System.out.printf(
				"%-5s %-" + maxTokenLen + "s  %-" + maxLexemeLen + "s  %-" + maxLinesLen + "s  %-" + maxColumLen +"s  %-" + maxValueLen + "s%n",
				"No.", "Token", "Lexema", "Líneas", "Columna", "Valor");

		// Imprimir los datos de cada token en la lista
		for (int i = 0; i < table.getSize(); i++) {
			myToken token = table.getElement(i);
			String valueString = (token.getValue() == null) ? "N/A" : token.getValue();
			String valueString2 = (token.getCol() == null) ? "N/A" : Integer.toString(token.getCol());
			System.out.printf(
					"%-5d %-" + maxTokenLen + "s  %-" + maxLexemeLen + "s  %-" + maxLinesLen + "s  %-" + maxColumLen +"s  %-" + maxValueLen + "s%n",
					(i+1),token.getToken(), token.getLexeme(), token.getLines().listToString(), valueString2, valueString);
		}
	}
	
	public static void printInput(String input) {
		Scanner read = new Scanner(input);
		int index = 1;
		System.out.println("--------------------Input--------------------");
		while (read.hasNextLine()) {
			System.out.println(index+".- "+read.nextLine());
			index++;
		}
		read.close();
		System.out.println();
	}
}
