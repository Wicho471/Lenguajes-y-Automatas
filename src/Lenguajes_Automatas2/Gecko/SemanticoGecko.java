package Lenguajes_Automatas2.Gecko;

import java.util.HashMap;

import Utilidades.Lista;
import Utilidades.StringHandler;
import Utilidades.myToken;
import Utilidades.Pila;

/**
 * @author Luis Emmanuel Torres Olvera
 * @see Constantes
 * @apiNote Analiza sintacticamente y semanticamente segun las siguientes reglas
 * 			1.- Las variables deben de estar declaradas
 * 			2.- Las variables no deben de estad duplicadas
 * 			3.- Debe de haber compatibilidad en lo datos
 * 			4.- Debe existir en alcance de las variables
 * 
 */
public class SemanticoGecko extends StringHandler {

	public static void main(String[] args) throws Exception {
		long tiempoInicio = System.currentTimeMillis();

		LexicalGecko lexical = new LexicalGecko("src\\Lenguajes_Automatas2\\txt\\Gecko3.txt");
		//lexical.printInput();
		//lexical.printTokenTable();
		// lexical.printSymbolTable();
		SemanticoGecko sintactico = new SemanticoGecko(lexical);
		sintactico.syntaxAnalysis(); // Metodo para inciar el analisis sintactico
		
		long tiempoFin = System.currentTimeMillis();
		long tiempoTotal = tiempoFin - tiempoInicio;
		System.out.println("\nEl tiempo total de ejecución es: " + tiempoTotal + " milisegundos");
	}

	// Lista de tokens a analizar y su index actual
	private Lista<myToken> tokenList;
	private Pila<HashMap<String, String>> symbolTables; // SEMANTICO -> Tablas de simbolos
	private static int pos = 0;

	public SemanticoGecko(LexicalGecko Lexico) {
		this.tokenList = Lexico.getTokenTable();
		this.symbolTables = new Pila<>(); // SEMANTICO -> Tablas de simbolos
	}

	//Entrar en el alcance de una variable
	private void enterScope() {
		symbolTables.push(new HashMap<>());
	}

	// Salir de un alcance (bloque)
	private void exitScope() {
		symbolTables.pop();
	}

	// Declarar una variable en el alcance actual
	private void declareVariable(String identifier, String type) {
		for (int i = symbolTables.getSize() - 1; i >= 0; i--) {
			HashMap<String, String> table = symbolTables.get(i);
			if (table.containsKey(identifier)) {
				throw new RuntimeException(
						"Variable '" + identifier + "' already declared in this scope, line ->" + currentLine());
			}
		}
		
		symbolTables.peek().put(identifier, dataTypeToLiteral(type));
	}

	// Buscar el tipo de una variable en los alcances disponibles
	private String lookupVariable(String identifier) {
		for (int i = symbolTables.getSize() - 1; i >= 0; i--) {
			HashMap<String, String> table = symbolTables.get(i);
			if (table.containsKey(identifier)) {
				return table.get(identifier);
			}
		}
		throw new RuntimeException("Variable '" + identifier + "' not declared, line ->" + currentLine());
	}

	// Compatibilidad de datos
	private void checkTypeCompatibility(String expectedType, String actualType) {
		if (!expectedType.equals(actualType)) {
			throw new RuntimeException("Type mismatch: cannot convert from " + expectedType + " to " + actualType
					+ ", line ->" + currentLine());
		}
	}

	// Produccionnes sintacticas
	private void syntaxAnalysis() { // Produccion Gecko
		match(BEGIN);
		parseBlock();
		match(END);
		System.out.println("\nLa sintaxis y semantica es correcta :D");
	}

	private void parseBlock() { // Produccion bloque
		enterScope(); // SEMANTICO -> Entra en alcance 
		while (!compareStrings(currentToken(), END) && !compareStrings(currentToken(), RIGHT_BRACE)) {
			parseStatement();
		}
		exitScope(); // SEMANTICO -> Sale de alncance
	}

	private void parseStatement() { // Produccion sentencia
		String token = currentToken();
		if (isDataType(token)) {
			parseDeclaration();
			match(SEMICOLON);
		} else if (compareStrings(token, IF)) {
			parseIf();
		} else if (compareStrings(token, FOR)) {
			parseFor();
		} else if (compareStrings(token, WHILE)) {
			parseWhile();
		} else if (compareStrings(token, DO)) {
			parseDoWhile();
		} else if (compareStrings(token, SWITCH)) {
			parseSwitch();
		} else {
			parseAssignment();
			match(SEMICOLON);
		}
	}

	private void parseDeclaration() {
		String type = currentToken();
		match(type);
		String identifier = currentLexeme();
		match(IDENTIFIER);
		
		declareVariable(identifier,type); // SEMANTICO -> Declarar la variable

		if (compareStrings(currentToken(), ASSIGN)) {
			match(ASSIGN);
			String exprType = parseExpression();
			checkTypeCompatibility(dataTypeToLiteral(type), exprType); // SEMANTICO -> Compatibilidad de datos
		}
	}

	private void parseAssignment() { // Produccion asginacion
		String identifier = currentLexeme();
		String type = lookupVariable(identifier); // SEMANTICO -> Verifica la existencia de la var
		match(IDENTIFIER);
		if (compareStrings(currentToken(), INCREMENT) || compareStrings(currentToken(), DECREMENT)) {
			checkTypeCompatibility(NUMBER, type); // SEMANTICO -> Compatibilidad de datos
			match(currentToken()); // ++ ó --
		} else {
			match(ASSIGN); // '='
			String exprType = parseExpression();
			checkTypeCompatibility(type, exprType); // SEMANTICO -> Compatibilidad de datos
		}
	}

	private void parseIf() { // Produccion if
		match(IF);
		match(LEFT_PAREN); // '('
		String exprType = parseExpression();
		checkTypeCompatibility(BOOLEAN, exprType); // SEMANTICO -> Compatibilidad de datos
		match(RIGHT_PAREN); // ')'
		match(LEFT_BRACE); // '{'
		parseBlock();
		match(RIGHT_BRACE); // '}'
		if (compareStrings(currentToken(), ELSE)) {
			parseElse();
		}
	}

	private void parseElse() { // Produccion else
		match(ELSE);
		if (compareStrings(currentToken(), IF)) {
			parseIf();
		} else {
			match(LEFT_BRACE); // '{'
			parseBlock();
			match(RIGHT_BRACE); // '}'
		}
	}

	// Metodos para for
	private void parseFor() { // Produccion for
		match(FOR);
		match(LEFT_PAREN); // '('
		parseDeclaration();
		match(SEMICOLON); // ';'
		String exprType = parseExpression();
		checkTypeCompatibility(BOOLEAN, exprType); // SEMANTICO -> Compatibilidad de datos
		match(SEMICOLON); // ';'
		parseAssignment();
		match(RIGHT_PAREN); // ')'
		match(LEFT_BRACE); // '{'
		parseBlock();
		match(RIGHT_BRACE); // '}'
	}

	private void parseWhile() { // Produccion while
		match(WHILE);
		match(LEFT_PAREN); // '('
		String exprType = parseExpression();
		checkTypeCompatibility(BOOLEAN, exprType); // SEMANTICO -> Compatibilidad de datos
		match(RIGHT_PAREN); // ')'
		match(LEFT_BRACE); // '{'
		parseBlock();
		match(RIGHT_BRACE); // '}'

	}

	private void parseDoWhile() { // Produccion do while
		match(DO);
		match(LEFT_BRACE); // '{'
		parseBlock();
		match(RIGHT_BRACE); // '}'
		match(WHILE);
		match(LEFT_PAREN); // '('
		String exprType = parseExpression();
		checkTypeCompatibility(BOOLEAN, exprType); // SEMANTICO -> Compatibilidad de datos
		match(RIGHT_PAREN); // ')'
		match(SEMICOLON); // ';'

	}

	private void parseSwitch() { // Produccion switch
		match(SWITCH);
		match(LEFT_PAREN); // '('
		String exprType = parseExpression();
		if (compareStrings(exprType, BOOLEAN)) { // SEMANTICO -> Compatibilidad de datos
			throw new RuntimeException("Cannot switch on a value of type boolean. Only convertible int values, strings or enum variables are permitted");
		}
		match(RIGHT_PAREN); // ')'
		match(LEFT_BRACE); // '{'
		while (compareStrings(currentToken(), CASE) || compareStrings(currentToken(), DEFAULT)) {
			if (compareStrings(currentToken(), CASE)) {
				enterScope(); // SEMANTICO -> Entra en alcance 
				match(CASE);
				checkTypeCompatibility(currentToken(), exprType); // SEMANTICO -> Compatibilidad de datos
				match(currentToken());
				match(COLON); // ':'
				while (!compareStrings(currentToken(), BREAK)) {
					parseStatement();
				}
				match(BREAK);
				match(SEMICOLON);
				exitScope(); // SEMANTICO -> Sale de alncance
			} else {
				enterScope(); // SEMANTICO -> Entra en alcance 
				match(DEFAULT);
				match(COLON); // ':'
				while (!compareStrings(currentToken(), BREAK)) {
					parseStatement();
				}
				match(BREAK);
				match(SEMICOLON);
				exitScope(); // SEMANTICO -> Sale de alncance
			}
		}
		match(RIGHT_BRACE); // }
	}

	// Producciones para las expresiones
	private String parseExpression() {
		String exprType = parseLogicalExpression();
		if (compareStrings(currentToken(), TERNARY_OPERATOR)) {
			match(TERNARY_OPERATOR); // Match ?
			String trueType = parseExpression();
			match(COLON); // Match :
			String falseType = parseExpression();
			if (!trueType.equals(falseType)) {
				throw new RuntimeException("Type mismatch in ternary operator: " + trueType + " vs " + falseType); // SEMANTICO -> Compatibilidad de datos
			}
			return trueType; // El tipo de un operador ternario es el tipo de sus ramas verdadera y falsa
		}
		return exprType;
	}

	// Método para analizar expresiones lógicas
	private String parseLogicalExpression() {
		boolean isBool = false;
		String leftType = parseComparisonExpression();
		while (compareStrings(currentToken(), AND) || compareStrings(currentToken(), OR)) {
			isBool=true;
			match(currentToken());
			String rightType = parseComparisonExpression();
			checkTypeCompatibility(BOOLEAN, leftType); // SEMANTICO -> Compatibilidad de datos
			checkTypeCompatibility(BOOLEAN, rightType); // SEMANTICO -> Compatibilidad de datos
		}
		if (isBool) {
			return BOOLEAN;
		} else {
			return leftType;
		}
	}

	// Método para analizar expresiones de comparación
	private String parseComparisonExpression() {
		boolean isBool = false;
		String leftType = parseArithmeticExpression();
		while (isComparisonOperator(currentToken())) {
			isBool=true;
			match(currentToken());
			String rightType = parseArithmeticExpression();
			// Usualmente, las comparaciones resultan en un tipo booleano
			checkTypeCompatibility(leftType, rightType); // SEMANTICO -> Compatibilidad de datos
		}
		if (isBool) {
			return BOOLEAN;
		} else {
			return leftType;
		}
	}

	// Método para analizar expresiones aritméticas
	private String parseArithmeticExpression() {
		String type = parseTerm();
		while (compareStrings(currentToken(), PLUS) || compareStrings(currentToken(), MINUS)) {
			match(currentToken());
			String rightType = parseTerm();
			checkTypeCompatibility(type, rightType); // SEMANTICO -> Compatibilidad de datos
		}
		return type; // El tipo de una expresión aritmética es el tipo de sus términos
	}

	// Método para analizar términos (parte de expresiones aritméticas)
	private String parseTerm() {
		String type = parseFactor();
		while (compareStrings(currentToken(), TIMES) || compareStrings(currentToken(), DIVIDE)
				|| compareStrings(currentToken(), MODULO)) {
			match(currentToken());
			String rightType = parseFactor();
			checkTypeCompatibility(type, rightType); // SEMANTICO -> Compatibilidad de datos
		}
		return type;
	}

	// Método para analizar factores (números, variables, sub-expresiones)
	private String parseFactor() {
		String token = currentToken();
		if (compareStrings(token, NOT)) {
			match(NOT); // Match !
			String type = parseFactor();
			checkTypeCompatibility(BOOLEAN, type); // SEMANTICO -> Compatibilidad de datos
			return BOOLEAN;
		} else if (compareStrings(token, LEFT_PAREN)) {
			match(LEFT_PAREN);
			String type = parseExpression();
			match(RIGHT_PAREN);
			return type;
		} else if (isLiteralType(token)) {
			match(token);
			return token; // Si es un tipo de dato, regresa el tipo
		} else if (compareStrings(token, IDENTIFIER)) {
			String identifier = currentLexeme();
			match(IDENTIFIER);
			String type = lookupVariable(identifier); // SEMANTICO -> Verifica la existencia de la var
			return type; // Regresa el tipo de la variable
		} else if (compareStrings(token, TRUE) || compareStrings(token, FALSE)) {
			match(token);
			return BOOLEAN;
		} else {
			throw new RuntimeException("Error de sintaxis, palabra reservada ('"+token+"') en lugar de una expresion, Token ->" + (pos + 1));
		}
	}

	// Otros metodos
	/**
	 * Metodo que compara si es el token que se esperaba
	 * 
	 * @param expectedToken
	 */
	private void match(String expectedToken) {
		String currentToken = currentToken();
		if (compareStrings(expectedToken, currentToken)) {
			pos++;
		} else {
			System.err.println("Error de sintaxis, se esperaba un '" + expectedToken + "' en lugar de '" + currentToken
					+ "' Token ->" + (pos + 1));
			throw new RuntimeException();
		}

	}

	private String currentToken() {
		return tokenList.getElement(pos).getToken();
	}
	
	private String currentLexeme() {
		return tokenList.getElement(pos).getLexeme();
	}
	
	private String currentLine() {
		return tokenList.getElement(pos).linesToString();
	}

	private String dataTypeToLiteral(String dataType) {
		if (compareStrings(dataType, INTEGER)) {
			return NUMBER;
		} else if (compareStrings(dataType, FLOAT)) {
			return REAL;
		}else if (compareStrings(dataType, STRING)) {
			return TEXT;
		}else if (compareStrings(dataType, CHAR)) {
			return CHARACTER;
		}else if (compareStrings(dataType, BOOL)) {
			return BOOLEAN;
		}else  {
			throw new RuntimeException("Unknow data type: "+dataType);
		}
	}
	
	// Metodos booleanos
	private static boolean isDataType(String token) {
		return compareStrings(token, INTEGER) || compareStrings(token, FLOAT) || compareStrings(token, STRING)
				|| compareStrings(token, CHAR) || compareStrings(token, BOOL);
	}
	
	private static boolean isLiteralType(String token) {
		return compareStrings(token, NUMBER) || compareStrings(token, REAL) || compareStrings(token, TEXT)
				|| compareStrings(token, CHARACTER) || compareStrings(token, BOOLEAN);

	}

	private static boolean isComparisonOperator(String token) {
		return compareStrings(token, EQUALS) || compareStrings(token, NOT_EQUALS) || compareStrings(token, LESS)
				|| compareStrings(token, LESS_EQUAL) || compareStrings(token, GREATER)
				|| compareStrings(token, GREATER_EQUAL);
	}
}