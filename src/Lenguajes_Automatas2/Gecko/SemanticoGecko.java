package Lenguajes_Automatas2.Gecko;

import java.util.HashMap;

import Utilidades.Lista;
import Utilidades.StringHandler;
import Utilidades.myToken;
import Utilidades.Pila;

/**
 * <h1>Gramática Libre de Contexto para el Lenguaje de Programación Gecko</h1>
 * <ul>
 * <li>Símbolos No Terminales: Programa, Bloque, Sentencia, Declaracion,
 * Asignacion, If, ElseIfPart, ElsePart, For, InicializacionFor, PasoFor,
 * Incremento, While, DoWhile, Switch, Casos, Caso, Expresion,
 * OperadorAritmetico, OperadorComparacion, OperadorLogico, Tipo, ValorInicial,
 * Numero, NumeroConPunto, CadenaDeTexto, Caracter, ValorBooleano</li>
 * 
 * <li>Símbolos Terminales: 'Begin', 'End', 'if', 'else', 'else if', '{', '}',
 * 'for', 'while', 'do', 'switch', 'case', 'default', 'True', 'False', Tipos de
 * datos ('Integer', 'Float', 'String', 'Char', 'Boolean'), Identificadores,
 * Números, Operadores ('+', '-', '*', '/', '%', '^', '==', '!=', '<', '>',
 * '<=', '>=', '&&', '||', '!'), ';', '=', '++', '--', '"', '''</li>
 * 
 * <li>Producciones:
 * <ol>
 * <li>Gecko → 'Begin' Bloque 'End'</li>
 * 
 * <li>Bloque → Bloque Sentencia | Sentencia</li>
 * 
 * <li>Sentencia → Declaracion | Asignacion | If | For | While | DoWhile |
 * Switch</li>
 * 
 * <li>Declaracion → Tipo Identificador '=' ValorInicial ';' || Tipo
 * Identificador ';'</li>
 * 
 * <li>Asignacion → Identificador '=' Expresion ';' || Incremento ';'</li>
 * 
 * <li>If → 'if' '(' Expresion ')' '{' Bloque '}' Else</li>
 * <li>Else → 'else' '{' Bloque '}' || 'else' If || ε</li>
 * 
 * <li>For → 'for' '(' InicializacionFor ';' Expresion ';' PasoFor ')' '{'
 * Bloque '}'</li>
 * <li>InicializacionFor → Declaracion | Asignacion</li>
 * <li>PasoFor → Asignacion | Incremento</li>
 * 
 * <li>Incremento → Identificador '++' | Identificador '--'</li>
 * 
 * <li>While → 'while' '(' Expresion ')' '{' Bloque '}'</li>
 * 
 * <li>DoWhile → 'do' '{' Bloque '}' 'while' '(' Expresion ')' ';'</li>
 * 
 * <li>Switch → 'switch' '(' Expresion ')' '{' Casos '}'</li>
 * <li>Casos → Casos Caso | Caso | ε</li>
 * <li>Caso → 'case' Valor ':' Bloque 'break' ';' | 'default' ':' Bloque 'break'
 * ';'</li>
 * 
 * <li>Expresion → Expresion OperadorAritmetico Expresion | Expresion
 * OperadorComparacion Expresion | Expresion OperadorLogico Expresion | '!'
 * Expresion | Expresion '?' Expresion ':' Expresion ';' | '(' Expresion ')' |
 * Numero | Identificador</li>
 * 
 * <li>OperadorAritmetico → '+' | '-' | '*' | '/' | '%'</li>
 * <li>OperadorComparacion → '==' | '!=' | '<' | '>' | '<=' | '>='</li>
 * <li>OperadorLogico → '&&' | '||'</li>
 * <li>Tipo → 'Integer' | 'Float' | 'String' | 'Character' | 'Boolean'</li>
 * <li>ValorInicial → Numero | NumeroConPunto | CadenaDeTexto | Caracter |
 * ValorBooleano</li>
 * <li>Numero → '[0-9]+'</li>
 * <li>NumeroConPunto → '[0-9]*' '.' '[0-9]+'</li>
 * <li>CadenaDeTexto → '"' [^"]* '"'</li>
 * <li>Caracter → '\'' . '\''</li>
 * <li>ValorBooleano → 'True' | 'False'</li>
 * </ol>
 * </li>
 * 
 * <li>Símbolo inicial: Gecko</li>
 * </ul>
 * 
 * <ul>
 * Descripcion de las expresiones regulares
 * <li>[^"]* → Coincidirá con cualquier secuencia de caracteres que no contenga
 * comillas dobles.</li>
 * <li>[0-9]* → Coincidirá con cualquier secuencia de caracteres que contenga
 * cero o más dígitos del 0 al 9</li>
 * <li>[0-9]+ → Coincidirá con cualquier secuencia de caracteres que contenga
 * uno o más dígitos del 0 al 9.</li>
 * <li>'\'' . '\'' → esta expresión regular coincide con cualquier cadena que
 * comience y termine con una comilla simple y tenga un solo carácter en el
 * medio</li>
 * </ul>
 * 
 * @author Luis Emmanuel Torres Olvera
 * @see Constantes
 * @apiNote Analiza sintacticamente, condicionales (if, else, switch), ciclos de
 *          control (for, while, do while), operadores aritmeticos
 *          (+,-,*,/,%,^,++,--), operadores de comparacion(==,>=,<=,!=,<,>),
 *          operadores de asignacion compuestos (+=,-=,*=,/=,%=,^=), operadores
 *          logicos (&&,||,!), operador de asignacion (=) operadores ternarios
 *          (?,:) y tipos de variables (String, Character, Integer, Boolean,
 *          Float)
 * 
 */
public class SemanticoGecko extends StringHandler {

	public static void main(String[] args) throws Exception {
		long tiempoInicio = System.currentTimeMillis();

		LexicalGecko lexical = new LexicalGecko("src\\Lenguajes_Automatas2\\txt\\Gecko.txt");
		 lexical.printInput();
		lexical.printTokenTable();
		// lexical.printSymbolTable();
		SemanticoGecko sintactico = new SemanticoGecko(lexical);
		sintactico.syntaxAnalysis(); // Metodo para inciar el analisis sintactico

		long tiempoFin = System.currentTimeMillis();
		long tiempoTotal = tiempoFin - tiempoInicio;
		System.out.println("El tiempo total de ejecución es: " + tiempoTotal + " milisegundos");
	}

	// Lista de tokens a analizar y su index actual
	private Pila<HashMap<String, String>> symbolTables = new Pila<>();
	Lista<myToken> tokenList = new Lista<myToken>();
	private static int pos = 0;

	public SemanticoGecko(LexicalGecko Lexico) {
		this.tokenList = Lexico.getTokenTable();
	}

	// Nuevos metodos semanticos
	private void enterScope() {
		symbolTables.push(new HashMap<>());
	}

	// Salir de un alcance (bloque)
	private void exitScope() {
		symbolTables.pop();
	}

	// Declarar una variable en el alcance actual
	private void declareVariable(String identifier, String type) {
		HashMap<String, String> currentTable = symbolTables.peek();
		if (currentTable.containsKey(identifier)) {
			throw new RuntimeException(
					"Variable '" + identifier + "' already declared in this scope, Token ->" + pos);
		}
		System.out.println("Se declaro una variable id:" + identifier + " tipo:" + type);
		currentTable.put(identifier, type);
	}

	// Buscar el tipo de una variable en los alcances disponibles
	private String lookupVariable(String identifier) {
		for (int i = symbolTables.getSize() - 1; i >= 0; i--) {
			HashMap<String, String> table = symbolTables.get(i);
			if (table.containsKey(identifier)) {
				return table.get(identifier);
			}
		}
		throw new RuntimeException("Variable '" + identifier + "' not declared, Token ->" + pos);
	}

	// Compatibilidad de datos
	private void checkTypeCompatibility(String expectedType, String actualType) {
		if (!expectedType.equals(actualType)) {
			throw new RuntimeException("Type mismatch: cannot convert from " + expectedType + " to " + actualType
					+ ", Token ->" + pos);
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
		enterScope();
		while (!compareStrings(currentToken(), END) && !compareStrings(currentToken(), RIGHT_BRACE)) {
			parseStatement();
		}
		exitScope();
	}

	private void parseStatement() { // Produccion sentencia
		String token = currentToken();
		if (isDataType(token)) {
			parseDeclaration();
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
		} else if (compareStrings(token, BREAK) || compareStrings(token, CONTINUE)) {
			match(token);
			match(SEMICOLON);
		} else {
			parseAssignment();
		}
	}

	private void parseDeclaration() {
		String type = currentToken();
		System.out.println("Declaracion de tipo: " + type);
		match(type);
		String identifier = tokenList.getElement(pos).getLexeme();
		match(IDENTIFIER);
		declareVariable(identifier, type); // SEMANTICO -> Declarar la variable

		if (compareStrings(currentToken(), ASSIGN)) {
			match(ASSIGN);
			String exprType = parseExpression();
			System.out.println("Expresion de tipo: " + exprType);
			checkTypeCompatibility(type, exprType); // SEMANTICO -> Compatibilidad de datos
			match(SEMICOLON);
		} else {
			match(SEMICOLON);
		}
	}

	private void parseAssignment() { // Produccion asginacion
		String identifier = tokenList.getElement(pos).getLexeme();
		String type = lookupVariable(identifier); // SEMANTICO -> Verifica la existencia de la var
		match(IDENTIFIER);
		if (compareStrings(currentToken(), INCREMENT) || compareStrings(currentToken(), DECREMENT)) {
			checkTypeCompatibility(INTEGER, type); // SEMANTICO -> Compatibilidad de datos
			match(currentToken()); // ++ ó --
		} else {
			match(ASSIGN); // '='
			String exprType = parseExpression();
			checkTypeCompatibility(type, exprType); // SEMANTICO -> Compatibilidad de datos
		}
		match(SEMICOLON); // ';'
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
		parseIniFor();
		match(SEMICOLON); // ';'
		String exprType = parseExpression();
		checkTypeCompatibility(BOOLEAN, exprType); // SEMANTICO -> Compatibilidad de datos
		match(SEMICOLON); // ';'
		parseStepFor();
		match(RIGHT_PAREN); // ')'
		match(LEFT_BRACE); // '{'
		parseBlock();
		match(RIGHT_BRACE); // '}'
	}

	private void parseIniFor() {
		String token = currentToken();
		if (isDataType(token)) {
			String type = currentToken();
			match(type);
			String identifier = tokenList.getElement(pos).getLexeme();
			match(IDENTIFIER);
			declareVariable(identifier, type); // SEMANTICO -> Declarar la variable
			match(ASSIGN);
			String exprType = parseExpression();
			checkTypeCompatibility(type, exprType); // SEMANTICO -> Compatibilidad de datos
		} else if (compareStrings(token, IDENTIFIER)) {
			String type = lookupVariable(tokenList.getElement(pos).getLexeme()); // SEMANTICO -> Verifica la existencia de la var
			match(IDENTIFIER);
			match(ASSIGN);
			String exprType = parseExpression();
			checkTypeCompatibility(type, exprType); // SEMANTICO -> Compatibilidad de datos
		} else {
			throw new RuntimeException("Unexpected token in expression: " + currentToken());
		}
	}

	private void parseStepFor() {
		String identifier = tokenList.getElement(pos).getLexeme();
		String type = lookupVariable(identifier); // SEMANTICO -> Verifica la existencia de la var
		match(IDENTIFIER);
		String token = currentToken();
		if (compareStrings(token, INCREMENT) || compareStrings(token, DECREMENT)) {
			checkTypeCompatibility(INTEGER, type); // SEMANTICO -> Compatibilidad de datos
			match(token);
		} else if (compareStrings(token, ASSIGN)) {
			match(ASSIGN);
			String exprType = parseExpression();
			checkTypeCompatibility(type, exprType); // SEMANTICO -> Compatibilidad de datos
		} else {
			throw new RuntimeException("Unexpected token in expression: " + token);
		}
	}
	// Aqui termina las producciones de for

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
		match(LEFT_PAREN); // ')'
		String exprType = parseExpression();
		match(RIGHT_PAREN); // ')'
		match(LEFT_BRACE); // '{'
		while (compareStrings(currentToken(), CASE) || compareStrings(currentToken(), DEFAULT)) {
			if (compareStrings(currentToken(), CASE)) {
				enterScope();
				match(CASE);
				checkTypeCompatibility(currentToken(), exprType); // SEMANTICO -> Compatibilidad de datos
				match(currentToken());
				match(COLON); // ':'
				while (!compareStrings(currentToken(), BREAK)) {
					parseStatement();
				}
				match(BREAK);
				match(SEMICOLON);
				exitScope();
			} else {
				enterScope();
				match(DEFAULT);
				match(COLON); // ':'
				while (!compareStrings(currentToken(), BREAK)) {
					parseStatement();
				}
				match(BREAK);
				match(SEMICOLON);
				exitScope();
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
				throw new RuntimeException("Type mismatch in ternary operator: " + trueType + " vs " + falseType);
			}
			System.out.println("Desde operador ternario retorna: " + trueType);
			return trueType; // El tipo de un operador ternario es el tipo de sus ramas verdadera y falsa
		}
		System.out.println("Desde parseExpression retorna: " + exprType);
		return exprType;
	}

	// Método para analizar expresiones lógicas
	private String parseLogicalExpression() {
		boolean flag = false;
		String leftType = parseComparisonExpression();
		while (compareStrings(currentToken(), AND) || compareStrings(currentToken(), OR)) {
			flag=true;
			match(currentToken());
			String rightType = parseComparisonExpression();
			checkTypeCompatibility(BOOLEAN, leftType); // SEMANTICO -> Compatibilidad de datos
			checkTypeCompatibility(BOOLEAN, rightType); // SEMANTICO -> Compatibilidad de datos
		}
		if (flag) {
			return BOOLEAN;
		} else {
			return leftType;
		}
	}

	// Método para analizar expresiones de comparación
	private String parseComparisonExpression() {
		boolean flag = false;
		String leftType = parseArithmeticExpression();
		while (isComparisonOperator(currentToken())) {
			flag=true;
			match(currentToken());
			String rightType = parseArithmeticExpression();
			// Usualmente, las comparaciones resultan en un tipo booleano
			checkTypeCompatibility(leftType, rightType); // SEMANTICO -> Compatibilidad de datos
		}
		if (flag) {
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
		System.out.println("Desde parseArithmeticExpression retorna: " + type);
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
		System.out.println("Desde parseTerm retorna: " + type);
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
		} else if (isDataType(token)) {
			match(token);
			System.out.println("Es un tipo de dato en la expresion: " + token);
			return token; // Si es un tipo de dato, regresa el tipo
		} else if (compareStrings(token, IDENTIFIER)) {
			String identifier = tokenList.getElement(pos).getLexeme();
			match(IDENTIFIER);
			String type = lookupVariable(identifier); // SEMANTICO -> Verifica la existencia de la var
			return type; // Regresa el tipo de la variable
		} else if (compareStrings(token, TRUE) || compareStrings(token, FALSE)) {
			match(token);
			return BOOLEAN;
		} else {
			throw new RuntimeException("Unexpected token in expression: " + token);
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

	// Metodos booleanos
	private static boolean isDataType(String token) {
		return compareStrings(token, INTEGER) || compareStrings(token, FLOAT) || compareStrings(token, STRING)
				|| compareStrings(token, CHAR) || compareStrings(token, BOOLEAN);
	}

	private static boolean isComparisonOperator(String token) {
		return compareStrings(token, EQUALS) || compareStrings(token, NOT_EQUALS) || compareStrings(token, LESS)
				|| compareStrings(token, LESS_EQUAL) || compareStrings(token, GREATER)
				|| compareStrings(token, GREATER_EQUAL);
	}
}