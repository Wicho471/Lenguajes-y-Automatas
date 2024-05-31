package Utilidades;

public class Constantes {

	// Control de flujo
	protected static final String BEGIN = "Begin", END = "End", FOR = "for", DO = "do", WHILE = "while", IF = "if",
			ELSE = "else", SWITCH = "switch", CASE = "case", DEFAULT = "default";

	// Control de bucles y estructuras
	protected static final String BREAK = "break", CONTINUE = "continue", YIELD = "yield";

	// Tipos de datos y manejo de objetos
	protected static final String ARRAY = "array", OBJECT = "object", NEW = "new", NULL = "null", TRUE = "true",
			FALSE = "false";

	// Retorno de funciones y manejo de objetos
	protected static final String RETURN = "return", THIS = "this";

	// Tipos de datos primitivos
	protected static final String BOOL = "bool", CHAR = "char", FLOAT = "float", INTEGER = "int", STRING = "str";

	// Tipos de literales
	protected static final String NUMBER = "Number", TEXT = "Text", REAL = "Real", CHARACTER = "Character",
			BOOLEAN = "Boolean";

	// Identificador de variables
	protected static final String IDENTIFIER = "Identifier";

	// Operadores de asignacion
	public static final String ADD_ASSIGN = "+=", SUBTRACT_ASSIGN = "-=", MULTIPLY_ASSIGN = "*=", DIVIDE_ASSIGN = "/=",
			MODULUS_ASSIGN = "%=", ASSIGN = "=";

	// Operadores aritméticos
	protected static final String DECREMENT = "--", DIVIDE = "/", INCREMENT = "++", MINUS = "-", MODULO = "%",
			PLUS = "+", TIMES = "*";

	// Operadores de comparación
	protected static final String EQUALS = "==", GREATER = ">", GREATER_EQUAL = ">=", LESS = "<", LESS_EQUAL = "<=",
			NOT_EQUALS = "!=";

	// Operadores lógicos
	protected static final String AND = "&&", NOT = "!", OR = "||";

	// Otros símbolos
	protected static final String TERNARY_OPERATOR = "?", COLON = ":", COMMA = ",", DOT = ".", LEFT_BRACE = "{",
			LEFT_BRACKET = "[", LEFT_PAREN = "(", RIGHT_BRACE = "}", RIGHT_BRACKET = "]", RIGHT_PAREN = ")",
			SEMICOLON = ";";

	// Cantidad maxima de tokens que se pueden leer
	protected static final Integer MAX = 500;
	
	//Otros
	protected static final String BLOCK = "block";
}
