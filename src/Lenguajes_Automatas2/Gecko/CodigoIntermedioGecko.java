package Lenguajes_Automatas2.Gecko;

import java.util.HashMap;

import Utilidades.Arbol;
import Utilidades.Lista;
import Utilidades.StringHandler;
import Utilidades.myToken;
import Utilidades.Pila;
import Utilidades.nodoArbol;

/**
 * @apiNote Analiza sintaca y semanticamente ademas de crear el Arbol semantico
 *          y generar el codigo de 3 direcciones a partir de este
 * @see Constantes
 */

public class CodigoIntermedioGecko extends StringHandler {

	public static void main(String[] args) throws Exception {
		long tiempoInicio = System.currentTimeMillis();

		LexicalGecko lexical = new LexicalGecko("src\\Lenguajes_Automatas2\\txt\\Gecko3.txt");
		//lexical.printInput();
		//lexical.printTokenTable();

		CodigoIntermedioGecko sintactico = new CodigoIntermedioGecko(lexical);
		sintactico.syntaxAnalysis(); // Metodo para iniciar el análisis sintáctico

		sintactico.printSemanticTree(); // Imprimir el árbol semántico
		Lista<String> addCode = sintactico.semanticTree.generateThreeAddressCode();

		System.out.println("\nGeneracion de codigo de 3 direcciones:\n");
		for (int i = 0; i < addCode.getSize(); i++) {
			System.out.println(addCode.getElement(i));
		}

		long tiempoFin = System.currentTimeMillis();
		long tiempoTotal = tiempoFin - tiempoInicio;
		System.out.println("\nEl tiempo total de ejecución es: " + tiempoTotal + " milisegundos");
	}

	private Lista<myToken> tokenList;
	private Pila<HashMap<String, String>> symbolTables;
	private static int pos = 0;
	private Arbol<String> semanticTree; // ARBOL -> Declaracion del arbol

	public CodigoIntermedioGecko(LexicalGecko Lexico) {
		this.tokenList = Lexico.getTokenTable();
		this.symbolTables = new Pila<>();
		this.semanticTree = new Arbol<>(); // ARBOL -> Asgnacion del arbol
	}

	private void enterScope() {
		symbolTables.push(new HashMap<>());
	}

	private void exitScope() {
		symbolTables.pop();
	}

	private void declareVariable(String identifier, String type) {
		HashMap<String, String> currentTable = symbolTables.peek();
		if (currentTable.containsKey(identifier)) {
			throw new RuntimeException(
					"Variable '" + identifier + "' already declared in this scope, line ->" + currentLine());
		}
		currentTable.put(identifier, dataTypeToLiteral(type));
	}

	private String lookupVariable(String identifier) {
		for (int i = symbolTables.getSize() - 1; i >= 0; i--) {
			HashMap<String, String> table = symbolTables.get(i);
			if (table.containsKey(identifier)) {
				return table.get(identifier);
			}
		}
		throw new RuntimeException("Variable '" + identifier + "' not declared, line ->" + currentLine());
	}

	private void checkTypeCompatibility(String expectedType, String actualType) {
		if (!expectedType.equals(actualType)) {
			throw new RuntimeException("Type mismatch: cannot convert from " + expectedType + " to " + actualType
					+ ", line ->" + currentLine());
		}
	}

	private void syntaxAnalysis() {
		match(BEGIN);
		nodoArbol<String> root = new nodoArbol<>(BEGIN); // ARBOL -> Generacion
		semanticTree.setRoot(root);
		parseBlock(root);
		match(END);
		System.out.println("\nLa sintaxis y semántica es correcta :D");
	}

	private void parseBlock(nodoArbol<String> parentNode) {
		enterScope();
		nodoArbol<String> blockNode = new nodoArbol<>(BLOCK); // ARBOL -> Generacion
		while (!compareStrings(currentToken(), END) && !compareStrings(currentToken(), RIGHT_BRACE)) {
			nodoArbol<String> stmtNode = parseStatement(); // ARBOL -> Generacion
			if (stmtNode != null) {
				blockNode.addNodeToRight(stmtNode);
			}
		}
		parentNode.addNodeToRight(blockNode); // ARBOL -> Union de nodos a la derecha
		exitScope();
	}

	private nodoArbol<String> parseStatement() {
		String token = currentToken();
		if (isDataType(token)) {
			nodoArbol<String> declarationNode = parseDeclaration(); // ARBOL -> Generacion
			match(SEMICOLON);
			return declarationNode;
		} else if (compareStrings(token, IF)) {
			return parseIf();
		} else if (compareStrings(token, FOR)) {
			return parseFor();
		} else if (compareStrings(token, WHILE)) {
			return parseWhile();
		} else if (compareStrings(token, DO)) {
			return parseDoWhile();
		} else if (compareStrings(token, SWITCH)) {
			return parseSwitch();
		} else {
			nodoArbol<String> assignmentNode = parseAssignment(); // ARBOL -> Generacion
			match(SEMICOLON);
			return assignmentNode;
		}
	}

	private nodoArbol<String> parseDeclaration() {
		String type = currentToken();
		match(type);
		String identifier = currentLexeme();
		match(IDENTIFIER);
		declareVariable(identifier, type);
		if (compareStrings(currentToken(), ASSIGN)) {
			nodoArbol<String> assginNode = new nodoArbol<>(ASSIGN); // ARBOL -> Generacion
			assginNode.addNodeToLeft(new nodoArbol<String>(identifier)); // ARBOL -> Union de nodos a la izquierda
			match(ASSIGN);
			nodoArbol<String> exprNode = parseExpression(); // ARBOL -> Generacion
			checkTypeCompatibility(dataTypeToLiteral(type), exprNode.getType());
			assginNode.addNodeToRight(exprNode); // ARBOL -> Union de nodos a la derecha
			return assginNode;
		}
		return null;
	}

	private nodoArbol<String> parseAssignment() {
		String identifier = currentLexeme();
		String type = lookupVariable(identifier);
		match(IDENTIFIER);
		nodoArbol<String> assignmentNode = new nodoArbol<>(ASSIGN); // ARBOL -> Generacion
		nodoArbol<String> idNode = new nodoArbol<>(identifier); // ARBOL -> Generacion
		idNode.setType(type);
		assignmentNode.addNodeToRight(idNode); // ARBOL -> Union de nodos a la derecha

		if (compareStrings(currentToken(), INCREMENT) || compareStrings(currentToken(), DECREMENT)) {
			checkTypeCompatibility(NUMBER, type);
			match(currentToken());
		} else {
			match(ASSIGN);
			nodoArbol<String> exprNode = parseExpression(); // ARBOL -> Generacion
			checkTypeCompatibility(type, exprNode.getType());
			assignmentNode.addNodeToRight(exprNode); // ARBOL -> Union de nodos a la derecha
		}
		return assignmentNode;
	}

	private nodoArbol<String> parseIf() {
		match(IF);
		nodoArbol<String> ifNode = new nodoArbol<>(IF); // ARBOL -> Generacion

		match(LEFT_PAREN);
		nodoArbol<String> exprNode = parseExpression(); // ARBOL -> Generacion
		checkTypeCompatibility(BOOLEAN, exprNode.getType());
		ifNode.addNodeToRight(exprNode); // ARBOL -> Union de nodos a la derecha
		match(RIGHT_PAREN);

		match(LEFT_BRACE);
		parseBlock(ifNode);
		match(RIGHT_BRACE);

		if (compareStrings(currentToken(), ELSE)) {
			ifNode.addNodeToRight(parseElse()); // ARBOL -> Union de nodos a la derecha
		}
		return ifNode;
	}

	private nodoArbol<String> parseElse() {
		match(ELSE);
		nodoArbol<String> elseNode = new nodoArbol<>(ELSE); // ARBOL -> Generacion

		if (compareStrings(currentToken(), IF)) {
			nodoArbol<String> ifNode = parseIf(); // ARBOL -> Generacion
			elseNode.addNodeToRight(ifNode); // ARBOL -> Union de nodos a la derecha
		} else {
			match(LEFT_BRACE);
			parseBlock(elseNode);
			match(RIGHT_BRACE);
		}
		return elseNode;
	}

	private nodoArbol<String> parseFor() {
		match(FOR);
		nodoArbol<String> forNode = new nodoArbol<>(FOR); // ARBOL -> Generacion

		match(LEFT_PAREN);
		nodoArbol<String> declNode = parseDeclaration(); // ARBOL -> Generacion
		forNode.addNodeToRight(declNode); // ARBOL -> Union de nodos a la derecha
		match(SEMICOLON);

		nodoArbol<String> exprNode = parseExpression(); // ARBOL -> Generacion
		checkTypeCompatibility(BOOLEAN, exprNode.getType());
		forNode.addNodeToRight(exprNode); // ARBOL -> Union de nodos a la derecha
		match(SEMICOLON);

		nodoArbol<String> assignNode = parseAssignment(); // ARBOL -> Generacion
		forNode.addNodeToRight(assignNode); // ARBOL -> Union de nodos a la derecha
		match(RIGHT_PAREN);

		match(LEFT_BRACE);
		parseBlock(forNode);
		match(RIGHT_BRACE);

		return forNode;
	}

	private nodoArbol<String> parseWhile() {
		match(WHILE);
		nodoArbol<String> whileNode = new nodoArbol<>(WHILE); // ARBOL -> Generacion

		match(LEFT_PAREN);
		nodoArbol<String> exprNode = parseExpression(); // ARBOL -> Generacion
		checkTypeCompatibility(BOOLEAN, exprNode.getType());
		whileNode.addNodeToRight(exprNode); // ARBOL -> Union de nodos a la derecha
		match(RIGHT_PAREN);

		match(LEFT_BRACE);
		parseBlock(whileNode);
		match(RIGHT_BRACE);

		return whileNode;
	}

	private nodoArbol<String> parseDoWhile() {
		match(DO);
		nodoArbol<String> doWhileNode = new nodoArbol<>(DO + "-" + WHILE); // ARBOL -> Generacion

		match(LEFT_BRACE);
		parseBlock(doWhileNode);
		match(RIGHT_BRACE);

		match(WHILE);
		match(LEFT_PAREN);
		nodoArbol<String> exprNode = parseExpression(); // ARBOL -> Generacion
		checkTypeCompatibility(BOOLEAN, exprNode.getType());
		doWhileNode.addNodeToRight(exprNode); // ARBOL -> Union de nodos a la derecha
		match(RIGHT_PAREN);
		match(SEMICOLON);

		return doWhileNode;
	}

	private nodoArbol<String> parseSwitch() {
		match(SWITCH);
		nodoArbol<String> switchNode = new nodoArbol<>(SWITCH); // ARBOL -> Generacion

		match(LEFT_PAREN);
		nodoArbol<String> exprNode = parseExpression(); // ARBOL -> Generacion
		if (compareStrings(exprNode.getType(), BOOLEAN)) {
			throw new RuntimeException(
					"Cannot switch on a value of type boolean. Only convertible int values, strings or enum variables are permitted");
		}
		switchNode.addNodeToRight(exprNode); // ARBOL -> Union de nodos a la derecha
		match(RIGHT_PAREN);

		match(LEFT_BRACE);
		while (compareStrings(currentToken(), CASE) || compareStrings(currentToken(), DEFAULT)) {
			nodoArbol<String> caseNode; // ARBOL -> Generacion
			if (compareStrings(currentToken(), CASE)) {
				enterScope();
				match(CASE);
				caseNode = new nodoArbol<>("CASE");
				checkTypeCompatibility(currentToken(), exprNode.getType());
				caseNode.addNodeToRight(new nodoArbol<>(currentToken())); // ARBOL -> Union de nodos a la derecha
				match(currentToken());
				match(COLON);

				nodoArbol<String> blockNode = new nodoArbol<>("BLOCK"); // ARBOL -> Generacion
				while (!compareStrings(currentToken(), BREAK)) {
					nodoArbol<String> stmtNode = parseStatement(); // ARBOL -> Generacion
					if (stmtNode != null) {
						blockNode.addNodeToRight(stmtNode); // ARBOL -> Union de nodos a la derecha
					}
				}
				caseNode.addNodeToRight(blockNode); // ARBOL -> Union de nodos a la derecha
				match(BREAK);
				match(SEMICOLON);
				exitScope();
			} else {
				enterScope();
				match(DEFAULT);
				caseNode = new nodoArbol<>("DEFAULT"); // ARBOL -> Generacion
				match(COLON);

				nodoArbol<String> blockNode = new nodoArbol<>("BLOCK"); // ARBOL -> Generacion
				while (!compareStrings(currentToken(), BREAK)) {
					nodoArbol<String> stmtNode = parseStatement(); // ARBOL -> Generacion
					if (stmtNode != null) {
						blockNode.addNodeToRight(stmtNode); // ARBOL -> Union de nodos a la derecha
					}
				}
				caseNode.addNodeToRight(blockNode); // ARBOL -> Union de nodos a la derecha
				match(BREAK);
				match(SEMICOLON);
				exitScope();
			}
			switchNode.addNodeToRight(caseNode); // ARBOL -> Union de nodos a la derecha
		}
		match(RIGHT_BRACE);

		return switchNode;
	}

	private nodoArbol<String> parseExpression() {
		nodoArbol<String> exprNode = parseLogicalExpression();
		if (compareStrings(currentToken(), TERNARY_OPERATOR)) {
			match(TERNARY_OPERATOR);
			nodoArbol<String> trueExpr = parseExpression(); // ARBOL -> Generacion
			match(COLON);
			nodoArbol<String> falseExpr = parseExpression(); // ARBOL -> Generacion
			if (!trueExpr.getType().equals(falseExpr.getType())) {
				throw new RuntimeException(
						"Type mismatch in ternary operator: " + trueExpr.getType() + " vs " + falseExpr.getType());
			}
			nodoArbol<String> ternaryNode = new nodoArbol<>(TERNARY_OPERATOR); // ARBOL -> Generacion
			ternaryNode.addNodeToRight(exprNode); // ARBOL -> Union de nodos a la derecha
			ternaryNode.addNodeToRight(trueExpr); // ARBOL -> Union de nodos a la derecha
			ternaryNode.addNodeToRight(falseExpr); // ARBOL -> Union de nodos a la derecha
			ternaryNode.setType(trueExpr.getType());
			return ternaryNode;
		}
		return exprNode;
	}

	private nodoArbol<String> parseLogicalExpression() {
		boolean flag = false;
		nodoArbol<String> leftNode = parseComparisonExpression(); // ARBOL -> Generacion
		while (compareStrings(currentToken(), AND) || compareStrings(currentToken(), OR)) {
			flag = true;
			String operator = currentToken();
			match(currentToken());
			nodoArbol<String> rightNode = parseComparisonExpression(); // ARBOL -> Generacion
			checkTypeCompatibility(BOOLEAN, leftNode.getType());
			checkTypeCompatibility(BOOLEAN, rightNode.getType());
			nodoArbol<String> logicalNode = new nodoArbol<>(operator); // ARBOL -> Generacion
			logicalNode.addNodeToRight(leftNode); // ARBOL -> Union de nodos a la derecha
			logicalNode.addNodeToRight(rightNode); // ARBOL -> Union de nodos a la derecha
			logicalNode.setType(BOOLEAN);
			leftNode = logicalNode;
		}
		if (flag) {
			return leftNode;
		} else {
			return leftNode;
		}
	}

	private nodoArbol<String> parseComparisonExpression() {
		boolean flag = false;
		nodoArbol<String> leftNode = parseArithmeticExpression(); // ARBOL -> Generacion
		while (isComparisonOperator(currentToken())) {
			flag = true;
			String operator = currentToken();
			match(currentToken());
			nodoArbol<String> rightNode = parseArithmeticExpression(); // ARBOL -> Generacion
			checkTypeCompatibility(leftNode.getType(), rightNode.getType());
			nodoArbol<String> comparisonNode = new nodoArbol<>(operator); // ARBOL -> Generacion
			comparisonNode.addNodeToRight(leftNode); // ARBOL -> Union de nodos a la derecha
			comparisonNode.addNodeToRight(rightNode); // ARBOL -> Union de nodos a la derecha
			comparisonNode.setType(BOOLEAN);
			leftNode = comparisonNode;
		}
		if (flag) {
			return leftNode;
		} else {
			return leftNode;
		}
	}

	private nodoArbol<String> parseArithmeticExpression() {
		nodoArbol<String> leftNode = parseTerm(); // ARBOL -> Generacion
		while (compareStrings(currentToken(), PLUS) || compareStrings(currentToken(), MINUS)) {
			String operator = currentToken();
			match(currentToken());
			nodoArbol<String> rightNode = parseTerm(); // ARBOL -> Generacion
			checkTypeCompatibility(leftNode.getType(), rightNode.getType());
			nodoArbol<String> arithmeticNode = new nodoArbol<>(operator); // ARBOL -> Generacion
			arithmeticNode.addNodeToRight(leftNode); // ARBOL -> Union de nodos a la derecha
			arithmeticNode.addNodeToRight(rightNode); // ARBOL -> Union de nodos a la derecha
			arithmeticNode.setType(leftNode.getType());
			leftNode = arithmeticNode;
		}
		return leftNode;
	}

	private nodoArbol<String> parseTerm() {
		nodoArbol<String> leftNode = parseFactor(); // ARBOL -> Generacion
		while (compareStrings(currentToken(), TIMES) || compareStrings(currentToken(), DIVIDE)
				|| compareStrings(currentToken(), MODULO)) {
			String operator = currentToken();
			match(currentToken());
			nodoArbol<String> rightNode = parseFactor(); // ARBOL -> Generacion
			checkTypeCompatibility(leftNode.getType(), rightNode.getType());
			nodoArbol<String> termNode = new nodoArbol<>(operator); // ARBOL -> Generacion
			termNode.addNodeToRight(leftNode); // ARBOL -> Union de nodos a la derecha
			termNode.addNodeToRight(rightNode); // ARBOL -> Union de nodos a la derecha
			termNode.setType(leftNode.getType());
			leftNode = termNode;
		}
		return leftNode;
	}

	private nodoArbol<String> parseFactor() {
		String token = currentToken();
		if (compareStrings(token, NOT)) {
			match(NOT);
			nodoArbol<String> factorNode = parseFactor(); // ARBOL -> Generacion
			checkTypeCompatibility(BOOLEAN, factorNode.getType());
			nodoArbol<String> notNode = new nodoArbol<>(NOT); // ARBOL -> Generacion
			notNode.addNodeToRight(factorNode); // ARBOL -> Union de nodos a la derecha
			notNode.setType(BOOLEAN);
			return notNode;
		} else if (compareStrings(token, LEFT_PAREN)) {
			match(LEFT_PAREN);
			nodoArbol<String> exprNode = parseExpression(); // ARBOL -> Generacion
			match(RIGHT_PAREN);
			return exprNode;
		} else if (isLiteralType(token)) {
			String value = tokenList.getElement(pos).getValue();
			match(token);
			nodoArbol<String> literalNode = new nodoArbol<>(value); // ARBOL -> Generacion
			literalNode.setType(token);
			return literalNode;
		} else if (compareStrings(token, IDENTIFIER)) {
			String identifier = currentLexeme();
			match(IDENTIFIER);
			String type = lookupVariable(identifier);
			nodoArbol<String> idNode = new nodoArbol<>(identifier); // ARBOL -> Generacion
			idNode.setType(type);
			return idNode;
		} else if (compareStrings(token, TRUE) || compareStrings(token, FALSE)) {
			match(token);
			nodoArbol<String> booleanNode = new nodoArbol<>(token); // ARBOL -> Generacion
			booleanNode.setType(BOOLEAN);
			return booleanNode;
		} else {
			throw new RuntimeException("Error de sintaxis, palabra reservada ('" + token
					+ "') en lugar de una expresión, Token ->" + (pos + 1));
		}
	}

	// Otros métodos
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
		} else if (compareStrings(dataType, STRING)) {
			return TEXT;
		} else if (compareStrings(dataType, CHAR)) {
			return CHARACTER;
		} else if (compareStrings(dataType, BOOL)) {
			return BOOLEAN;
		} else {
			throw new RuntimeException("Unknown data type: " + dataType);
		}
	}

	// Métodos booleanos
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

	public void printSemanticTree() {
		System.out.println("\nÁrbol Semántico:");
		semanticTree.printTree();
	}
}
