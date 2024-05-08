package Lenguajes_Automatas2.FAs;

import Utilidades.StringHandler;

public class EAS extends StringHandler{
	
	public boolean leftPar(String lexeme) {
		return compareStrings(lexeme, "(");
	}
	public boolean rightPar(String lexeme) {
		return compareStrings(lexeme, ")");
	}
	public boolean plus(String lexeme) {
		return compareStrings(lexeme, "+");
	}
	public boolean minus(String lexeme) {
		return compareStrings(lexeme, "-");
	}
}
