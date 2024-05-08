package Lenguajes_Automatas2.AnalizadorLexico;

import Lenguajes_Automatas2.FAs.DFA_L1;
import Lenguajes_Automatas2.FAs.NFAE_L4;
import Lenguajes_Automatas2.FAs.NFA_L6;
import Utilidades.LexicalUtility;
import Utilidades.Lista;

/**
 * Requerimientos para esta actividad:
 * <ol>
 * <li>Tomando como base la versión uno del léxico mínimo, copiar y modificar
 * éste para implementar la versión dos.</li>
 * <li>El programa en su versión dos mostrará el contenido de la tabla de
 * símbolos donde aparecen los lexemas únicos y el listado de todas las líneas
 * donde se encuentran.</li>
 * <li>Tomando como base la versión dos, copiar y modificar éste para
 * implementar la versión tres.</li>
 * <li>El programa en su versión tres tomará en cuenta la eliminación de los
 * comentarios que pudiera tener el archivo y mostrará el contenido de la
 * versión uno y de la dos.</li>
 * <li>Desarrollar cada programa como proyecto independiente.</li>
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
 * <li>Lo indicado en su versión.</li>
 * </ul>
 */

public class AnalizadorLexicoBasico extends LexicalUtility {
	public static void main(String[] args) throws Exception {
		String string = getTextFile("src/Lenguajes_Automatas2/txt/AnalizadorLexicoBasico.txt");
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
