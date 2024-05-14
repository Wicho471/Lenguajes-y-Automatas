package Lenguajes_Automatas2.Gecko;

import Utilidades.Lista;
import Utilidades.StringHandler;
import Utilidades.myToken;

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
public class SintacticoGecko extends StringHandler {

	public static void main(String[] args) throws Exception {
		long tiempoInicio = System.currentTimeMillis();

		LexicalGecko lexical = new LexicalGecko("src\\Lenguajes_Automatas2\\txt\\Gecko.txt");
		lexical.printInput();
		lexical.printTokenTable();
		lexical.printSymbolTable();
		SintacticoGecko sintactico = new SintacticoGecko(lexical);
		sintactico.syntaxAnalysis(); // Metodo para inciar el analisis sintactico

		long tiempoFin = System.currentTimeMillis();
		long tiempoTotal = tiempoFin - tiempoInicio;
		System.out.println("El tiempo total de ejecución es: " + tiempoTotal + " milisegundos");
	}

	// Lista de tokens a analizar y su index actual
	Lista<myToken> tokenList = new Lista<myToken>();
	private static int pos = 0;

	public SintacticoGecko(LexicalGecko Lexico) {
		this.tokenList = Lexico.getTokenTable();
	}

	// Produccionnes
	private void syntaxAnalysis() { // Produccion Gecko
		match(BEGIN);
		parseBlock();
		match(END);
		System.out.println("\nLa sintaxis es correcta :D");
	}

	private void parseBlock() { // Produccion bloque
		while (!compareStrings(currentToken(), END) && !compareStrings(currentToken(), RIGHT_BRACE)) {
			parseStatement();
		}
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

	private void parseDeclaration() { // Produccion declaracion
		match(currentToken());
		match(IDENTIFIER);
		if (compareStrings(currentToken(), SEMICOLON)) {
			match(SEMICOLON); // ';'
		} else {
			match(ASSIGN); // '='
			parseExpression();
			match(SEMICOLON); // ';'
		}
	}

	private void parseAssignment() { // Produccion asginacion
		match(currentToken());
		if (compareStrings(currentToken(), INCREMENT) || compareStrings(currentToken(), DECREMENT)) {
			match(currentToken()); // ++ ó --
		} else {
			match(ASSIGN); // '='
			parseExpression();
		}
		match(SEMICOLON); // ';'
	}

	private void parseIf() { // Produccion if
		match(IF);
		match(LEFT_PAREN); // '('
		parseExpression();
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
		parseExpression();
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
			match(token);
			match(IDENTIFIER);
			match(ASSIGN);
			parseExpression();
		} else if (compareStrings(token, IDENTIFIER)) {
			match(IDENTIFIER);
			match(ASSIGN);
			parseExpression();
		} else {
			throw new RuntimeException("Unexpected token in expression: " + currentToken());
		}
	}

	private void parseStepFor() {
		match(IDENTIFIER);
		String token = currentToken();
		if (compareStrings(token, INCREMENT) || compareStrings(token, DECREMENT)) {
			match(token);
		} else if (compareStrings(token, ASSIGN)) {
			match(ASSIGN);
			parseArithmeticExpression();
		} else {
			throw new RuntimeException("Unexpected token in expression: " + token);
		}
	}
	// Aqui termina las producciones de for

	private void parseWhile() { // Produccion while
		match(WHILE);
		match(LEFT_PAREN); // '('
		parseExpression();
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
		parseExpression();
		match(RIGHT_PAREN); // ')'
		match(SEMICOLON); // ';'

	}

	private void parseSwitch() { // Produccion switch
		match(SWITCH);
		match(LEFT_PAREN); // ')'
		parseExpression();
		match(RIGHT_PAREN); // ')'
		match(LEFT_BRACE); // '{'
		while (compareStrings(currentToken(), CASE) || compareStrings(currentToken(), DEFAULT)) {
			if (compareStrings(currentToken(), CASE)) {
				match(CASE);
				match(currentToken());
				match(COLON); // ':'
				while (!compareStrings(currentToken(), BREAK)) {
					parseStatement();
				}
				match(BREAK);
				match(SEMICOLON);
			} else {
				match(DEFAULT);
				match(COLON); // ':'
				while (!compareStrings(currentToken(), BREAK)) {
					parseStatement();
				}
				match(BREAK);
				match(SEMICOLON);
			}
		}
		match(RIGHT_BRACE); // }
	}

	// Producciones para las expresiones
	private void parseExpression() {
		parseLogicalExpression();
		if (compareStrings(currentToken(), TERNARY_OPERATOR)) {
			match(TERNARY_OPERATOR); // Match ?
			parseExpression(); // Evalúa la expresión verdadera
			match(COLON); // Match :
			parseExpression(); // Evalúa la expresión falsa
		}
	}

	private void parseLogicalExpression() {
		parseComparisonExpression();
		while (currentToken().equals(AND) || currentToken().equals(OR)) {
			match(currentToken()); // Match && or ||
			parseComparisonExpression();
		}
	}

	private void parseComparisonExpression() {
		parseArithmeticExpression();
		while (isComparisonOperator(currentToken())) {
			match(currentToken()); // Match ==, !=, <, <=, >, >=
			parseArithmeticExpression();
		}
	}

	private void parseArithmeticExpression() {
		parseTerm();
		while (compareStrings(currentToken(), PLUS) || compareStrings(currentToken(), MINUS)) {
			match(currentToken()); // Match + or -
			parseTerm();
		}
	}

	private void parseTerm() {
		parseFactor();
		while (compareStrings(currentToken(), TIMES) || compareStrings(currentToken(), DIVIDE)
				|| compareStrings(currentToken(), MODULO)) {
			match(currentToken()); // Match *, /, %
			parseFactor();
		}
	}

	private void parseFactor() {
		String token = currentToken();
		if (compareStrings(token, NOT)) {
			match(NOT); // Match !
			parseFactor();
		} else if (compareStrings(token, LEFT_PAREN)) {
			match(LEFT_PAREN); // Match (
			parseExpression();
			match(RIGHT_PAREN); // Match )
		} else if (isDataType(token) || compareStrings(token, IDENTIFIER) || compareStrings(token, TRUE)
				|| compareStrings(token, FALSE)) {
			match(token); // Match number or identifier
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

	@SuppressWarnings("unused")
	private static boolean isOperator(String token) {
		return isArithmeticOperator(token) || isComparisonOperator(token) || isLogicalOperator(token);
	}

	private static boolean isArithmeticOperator(String token) {
		return compareStrings(token, PLUS) || compareStrings(token, MINUS) || compareStrings(token, TIMES)
				|| compareStrings(token, DIVIDE) || compareStrings(token, MODULO);
	}

	private static boolean isLogicalOperator(String token) {
		return compareStrings(token, AND) || compareStrings(token, OR);
	}

	private static boolean isComparisonOperator(String token) {
		return compareStrings(token, EQUALS) || compareStrings(token, NOT_EQUALS) || compareStrings(token, LESS)
				|| compareStrings(token, LESS_EQUAL) || compareStrings(token, GREATER)
				|| compareStrings(token, GREATER_EQUAL);
	}
}
