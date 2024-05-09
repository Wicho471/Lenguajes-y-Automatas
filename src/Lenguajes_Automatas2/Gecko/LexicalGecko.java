package Lenguajes_Automatas2.Gecko;

import Utilidades.LexicalUtility;
import Utilidades.Lista;
import Utilidades.myToken;

public class LexicalGecko extends LexicalUtility {
	
	public static void main(String[] args) throws Exception {
		LexicalGecko lexical = new LexicalGecko("src\\Lenguajes_Automatas2\\txt\\Gecko.txt");
		lexical.printTokenTable();
		lexical.printSymbolTable();
	}
	
	// Variables globales para un facil acceso
	private final String input; // Cadena a analizar
	private int charPos; // Posicion del char actual
	private int row; // Valor entero de la linea actual
	private int col; // Valor entero de la columna actual
	
	// Aqui se guardan las palabras reservadas
	private final Lista<String> keyWords = new Lista<>("Begin", "End", "if", "else", "Integer", "Float", "String",
			"Character", "Boolean", "for", "while", "do", "switch", "True", "False", "continue", "return", "new",
			"this", "null"); // Aqui van las palabras reservadas

	// Aqui se guardan los posibles simbolos a leer
	private final Lista<Character> symbolsList = new Lista<>('=', '<', '>', '(', ')', '{', '}', '[', ']', ':', '"',
			'\'', ';'); // Aqui van los simbolos

	// Aqui van los operadores arimeticos
	private final Lista<Character> artmieticOperator = new Lista<>('+', '-', '*', '/', '^', '%');

	// Aqui van los operadores logicos
	private final Lista<Character> logicOperator = new Lista<>('!', '&', '|');

	// Constructor de la clase que recibe como parametro la direccion del archivo de
	// texto
	public LexicalGecko(String path) throws Exception {
		this.input = deleteComments(getTextFile(path));
		this.charPos = 0;
		this.row = 1;
		this.col = 1;
		tokenize();
		getSymbolTable(tokenTable);
	}

	// Metodo para generar la lista de tokens
	public Lista<myToken> tokenize() {
		while (charPos < input.length()) { // Analiza caracter por caracter hasta que no haya mas elementos
			char current = currentChar(); // Guarda el caracter actual
			if (Character.isWhitespace(current)) { // Si el caracter actual es un espacio en blanco
				if (current == '\n') { // Si el caracter actual es salto de linea
					row++; // Incrementa la linea en 1
					col = 1; // Reinicia el contador de columnas
					consumeChar(); // Consume el char si hacer nada mas
				} else {
					consumeChar(); // Consume el char si hacer nada mas
				}
			} else if (Character.isDigit(current)) { // Si el caracter actual es un digito
				tokenTable.addToEnd(tokenizeNumberOrReal()); // Llama la funcion para tokenizar el numero

			} else if (Character.isLetter(current)) { // Si el caracter actual es una letra
				tokenTable.addToEnd(tokenizeIdentifierOrKeyword()); // Manda llamar la funcion que verifica si es un Id o
																	// una palabra reservada
			} else if (isSymbol(current)) { // Si el caracter actual es un simbolo
				tokenTable.addToEnd(tokenizeOperatorOrSymbol()); // Se llama la funcion que verifica si es un operador o
																// es un symbolo

			} else { // Si ninguna de las anteriores se cumplio
				throw new RuntimeException("Unexpected character: " + current); // Finaliza el programa porque el
																				// caracter no pudo ser identificado
			}
		}
		return tokenTable;
	}

	private myToken tokenizeOperatorOrSymbol() {
		char current = consumeChar();
		if ("=<>!".indexOf(current) != -1 && currentChar() == '=') { // Operadores de comparacion
			String value = current + String.valueOf(consumeChar());
			return new myToken(value, value, row, null, col++);

		} else if (isArtmieticOperator(current) && currentChar() == '=') { // Posibles metodos de asginacion compuesta
			String value = current + String.valueOf(consumeChar());
			return new myToken(value, value, row, null, col++);

		} else if ("+-&|".indexOf(current) != -1 && current == currentChar()) { // Posible incremento u operador logico
			String value = current + String.valueOf(consumeChar());
			return new myToken(value, value, row, null, col++);

		} else if (current == '"') {
			return getString(current);
		} else if (current == '\'') {
			return getChar(current);
		} else {
			return new myToken(current + "", current + "", row, null, col++); // Cualquiero otro simbolo simple de la
																				// lista
		}
	}

	private myToken tokenizeIdentifierOrKeyword() {
		StringBuilder identifier = new StringBuilder();
		while (Character.isLetterOrDigit(currentChar()) || currentChar() == '_') {
			identifier.append(consumeChar());
		}
		String value = identifier.toString();
		if (isKeyword(value)) {
			return new myToken(value, value, row, null, col++);
		} else {
			return new myToken("Identifier", value, row, null, col++);
		}
	}

	private myToken tokenizeNumberOrReal() {
		StringBuilder number = new StringBuilder();
		while (Character.isDigit(currentChar())) {
			number.append(consumeChar());
		}
		if (currentChar() == '.') {
			number.append(consumeChar());
			while (Character.isDigit(currentChar())) {
				number.append(consumeChar());
			}
			return new myToken("Float", number.toString(), row, String.valueOf(number), col++);
		} else {
			return new myToken("Integer", number.toString(), row, String.valueOf(number), col++);
		}
	}

	private myToken getString(char currentChar) {
		StringBuilder value = new StringBuilder();
		value.append(currentChar);
		while (currentChar() != '"') {
			value.append(consumeChar());
		}
		value.append(consumeChar());
		return new myToken("String", value.toString(), row, String.valueOf(value), col++);
	}

	private myToken getChar(char currentChar) {
		StringBuilder value = new StringBuilder();
		value.append(currentChar); // La primera coma simple
		value.append(consumeChar());
		value.append(consumeChar());
		return new myToken("Character", value.toString(), row, String.valueOf(value), col++);
	}

	// Metodos para la obtencion de caracteres
	private char consumeChar() {
		return input.charAt(charPos++);
	}

	private char currentChar() {
		return charPos < input.length() ? input.charAt(charPos) : '\0';
	}

	// Metodos booleanos
	private boolean isKeyword(String value) {
		return keyWords.contains(value);
	}

	private boolean isSymbol(char value) {
		return symbolsList.contains(value) || isLogicOperator(value) || isArtmieticOperator(value);
	}

	private boolean isLogicOperator(char value) {
		return logicOperator.contains(value);
	}

	private boolean isArtmieticOperator(char value) {
		return artmieticOperator.contains(value);
	}
	
	//Otros metodos
	public void printTokenTable() {
		System.out.println("--------------------Tabla de tokens--------------------");
		printTable(tokenTable);
	}
	
	public void printSymbolTable() {
		System.out.println("--------------------Tabla de simbolos--------------------");
		printTable(symbolTable);
	}

	public Lista<myToken> getSymbolTable() {
		return symbolTable;
	}
	
	public Lista<myToken> getTokenTable() {
		return tokenTable;
	}
	
	public void printInput() {
		printInput(this.input);
	}
}
