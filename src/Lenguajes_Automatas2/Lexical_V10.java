package Lenguajes_Automatas2;

import Lenguajes_Automatas2.FAs.*;
import Utilidades.LexicalUtility;
import Utilidades.Lista;

public class Lexical_V10 extends LexicalUtility {

	private static String originalString;
	private static String finalString;

	public static void main(String[] args) throws Exception {

		// Lectura y modelado del texto
		originalString = getTextFile("src/Lenguajes_Automatas2/txt/myString.txt"); // Lexical v1
		// System.out.println("Texto escaneado: \n\n" + originalString);
		originalString = deleteComments(originalString); // Lexical v3
		// System.out.println("\nTexto sin comentarios: \n\n"+originalString);
		finalString = clearWhiteSpaces(originalString); // Lexical v2
		// System.out.println("\nTexto sin espacios en blanco: \n\n"+finalString);

		// Obtencion de datos
		splitStrings(finalString); // Lexical v6
		searchLines(originalString);
		getSymbolTable(OriginalLexeme); // Lexical v7

		System.out.println("Tabla de simbolos");
		printTable(OriginalLexeme);
		// Creamos los FA como objetos para poder usar sus metodos de comprobacion
		NFAE_L4 fa1 = new NFAE_L4();
		DFA_L1 fa2 = new DFA_L1();
		NFA_L6 fa3 = new NFA_L6();
		Reservadas res = new Reservadas();
		EAS eas = new EAS();

		// Lexical v4, genera tokens
		for (int i = 0; i < FinalLexicalResult.getSize(); i++) {
			boolean band = true;

			// Palabras reservadas
			if (res.BEGIN(FinalLexicalResult.getElement(i).getLexeme())) {
				FinalLexicalResult.getElement(i).setToken("Begin");
				continue;
			}

			if (res.END(FinalLexicalResult.getElement(i).getLexeme())) {
				FinalLexicalResult.getElement(i).setToken("End"); 
				continue;
			}

			// EAS
			if (eas.leftPar(FinalLexicalResult.getElement(i).getLexeme())) {
				FinalLexicalResult.getElement(i).setToken("("); 
				continue;
			}
			if (eas.rightPar(FinalLexicalResult.getElement(i).getLexeme())) {
				FinalLexicalResult.getElement(i).setToken(")"); 
				continue;
			}
			if (eas.minus(FinalLexicalResult.getElement(i).getLexeme())) {
				FinalLexicalResult.getElement(i).setToken("-"); 
				continue;
			}
			if (eas.leftPar(FinalLexicalResult.getElement(i).getLexeme())) {
				FinalLexicalResult.getElement(i).setToken("+"); 
				continue;
			}

			// Otros tokens
			if (fa1.validateString(FinalLexicalResult.getElement(i).getLexeme())) {
				// Lexical v8
				FinalLexicalResult.getElement(i).setToken("c"); // Lexical v9
				FinalLexicalResult.getElement(i).setValue("10"); // Lexical v8
				band = false;
			}

			if (fa2.validateString(FinalLexicalResult.getElement(i).getLexeme())) {
				FinalLexicalResult.getElement(i).setToken("b");
				FinalLexicalResult.getElement(i).setValue("20");
				band = false;
			}

			if (fa3.validateString(FinalLexicalResult.getElement(i).getLexeme())) {
				FinalLexicalResult.getElement(i).setToken("a");
				FinalLexicalResult.getElement(i).setValue("30");
				band = false;
			}
			if (band) { // Si no encontro alguna coincidencia lo marca (Lexical v5)
				FinalLexicalResult.getElement(i).setToken("TokenError");
			}
		}

		System.out.println();
		printTable(FinalLexicalResult); // Lexical v10
	}
	public Lista<String> getTokenList() {
		Lista<String> list = new Lista<String>();
		for (int i = 0; i < FinalLexicalResult.getSize(); i++) {
			list.addToEnd(FinalLexicalResult.getElement(i).getToken());
		}
		return list;
	}
}
