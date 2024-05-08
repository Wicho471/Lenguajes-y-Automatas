package Lenguajes_Automatas2.AnalizadorLexico;

import Lenguajes_Automatas2.FAs.*;
import Utilidades.LexicalUtility;
import Utilidades.Lista;

/**
 * Requerimientos para esta actividad:
 * <ol>
 * <li>Desarrollar el programa en un analizador léxico mínimo el cual leerá
 * desde un archivo las cadenas correspondientes a por lo menos 3 FA.</li>
 * <li>Cada FA le corresponde un token.</li>
 * <li>El nombre del FA corresponderá al nombre del token.</li>
 * <li>La selección de los FA queda a su criterio.</li>
 * <li>La lectura del archivo será carácter por carácter de modo que estos
 * caracteres pasen a los diferentes FA como sus símbolos de entrada.</li>
 * <li>Para separar las cadenas de entrada de los FA en el archivo, se
 * utilizarán los chars: espacios en blanco, tabuladores y saltos de línea.</li>
 * <li>El programa en su versión uno, mostrará un listado de todos los tokens,
 * su lexema y la línea encontrados en el archivo.</li>
 * <li>[Opcional] Mostrar de igual manera la columna donde inicia o termina el
 * token (lexema).</li>
 * <li>[Opcional] Mostrar de igual manera la columna donde inicia o termina el
 * token (lexema) cuando se muestre la tabla de símbolos.</li>
 * <li>[Opcional] Puede incluir un token (FA) para tomar en cuenta todas las
 * demás cadenas que no pertenecen a los FA seleccionados.</li>
 * <li>[Opcional] Puede incluir un token (FA) para el fin de archivo.</li>
 * </ol>
 * 
 * El programa debe mostrar:
 * <ul>
 * <li>El nombre del analizador léxico.</li>
 * <li>La descripción del analizador léxico.</li>
 * <li>Lo indicado en la versión uno.</li>
 * </ul>
 */

public class AnalizadorLexicoMinimo extends LexicalUtility {
	public static void main(String[] args) throws Exception {
		String string = getTextFile("src/Lenguajes_Automatas2/txt/AnalizadorLexicoMinimo.txt");
		Lista<Integer> ListLines = searchLines(string);
		string = clearWhiteSpaces(string);
		Lista<String> ListStrings = splitStrings(string);
		Lista<String> Tokens = new Lista<>();
		Lista<String> Lexema = new Lista<>();
		Lista<Integer> TableLines = new Lista<>();
		NFAE_L4 FA1 = new NFAE_L4();
		DFA_L1 FA2 = new DFA_L1();
		NFA_L6 FA3 = new NFA_L6();
		for (int i = 0; i < ListStrings.getSize(); i++) {
			boolean band = true;
			String currentString = ListStrings.getElement(i);
			if (FA1.validateString(currentString)) {
				Lexema.addToEnd(currentString);
				Tokens.addToEnd("C10");
				TableLines.addToEnd(ListLines.getElement(i));
				band = false;
			}

			if (FA2.validateString(currentString)) {
				Lexema.addToEnd(currentString);
				Tokens.addToEnd("B0E1");
				TableLines.addToEnd(ListLines.getElement(i));
				band = false;
			}

			if (FA3.validateString(currentString)) {
				Lexema.addToEnd(currentString);
				Tokens.addToEnd("B1");
				TableLines.addToEnd(ListLines.getElement(i));
				band = false;
			}
			if (band) { // Si no encontro alguna coincidencia lo marca (Lexical v5)
				Lexema.addToEnd(currentString);
				Tokens.addToEnd("Error");
				TableLines.addToEnd(ListLines.getElement(i));
			}
		}
		System.out.println("Analizador Lexico minimo\n");
		System.out.println("Este analizador lexico lee el texto de un archivo, " + "\nlimpia y separa las cadenas para "
				+ "\nvalidarlos en los distintos FAs y retornan tokens\n");
		System.out.println("Lexema \t Token \t Linea");
		for (int i = 0; i < Lexema.getSize(); i++) {
			System.out
					.println(Lexema.getElement(i) + " \t " + Tokens.getElement(i) + " \t " + TableLines.getElement(i));
		}
	}
}
