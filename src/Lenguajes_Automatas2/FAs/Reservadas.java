package Lenguajes_Automatas2.FAs;

import Utilidades.LexicalUtility;

public class Reservadas extends LexicalUtility {
	public boolean BEGIN(String lexeme) {
		return compareStrings(lexeme, "BEGIN");
	}
	
	public boolean END(String lexeme) {
		return compareStrings(lexeme, "END");
	}
}
