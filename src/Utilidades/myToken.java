package Utilidades;

/**
 * La clase Token representa un token lexical en un contexto de análisis
 * sintáctico, almacenando detalles como el tipo de token, el lexema asociado,
 * las líneas y columnas donde aparece, y un valor entero opcional asociado con
 * el token.
 * 
 * @author Luis Emmanuel
 * @version 1.0
 * @see Lista
 */
public class myToken {
	private String token; // El identificador del tipo de token.
	private String lexeme; // El lexema asociado al token.
	private Lista<Integer> rows; // Lista de números de línea donde el token aparece.
	private String value; // Un valor entero opcional asociado con el token (puede ser null).
	private Integer col; // La columna donde aparece el lexema

	// Constructores
	/**
	 * Constructor que inicializa el token con un identificador de tipo de token
	 * específico.
	 * 
	 * @param token El identificador del tipo de token.
	 */
	public myToken(String lexeme) {
		this.token = "";
		this.lexeme = lexeme;
		this.rows = new Lista<Integer>();
		this.value = null;
	}

	/**
	 * Constructor completo que inicializa el token con un identificador de tipo, un
	 * lexema, un número de línea, un valor entero y la columna.
	 * 
	 * @param token  El identificador del tipo de token.
	 * @param lexeme El lexema asociado al token.
	 * @param line   El número de línea donde el token aparece.
	 * @param value  El valor entero opcional asociado al token.
	 * @param col    El numoer de la columna donde aparece
	 */
	public myToken(String token, String lexeme, Integer line, String value, Integer col) {
		this.token = token;
		this.lexeme = lexeme;
		this.rows = new Lista<Integer>(line);
		this.value = value;
		this.col = col;
	}

	public myToken(String token, String lexeme, Integer line) {
		this.token = token;
		this.lexeme = lexeme;
		this.rows = new Lista<Integer>(line);
		this.value = null;
		this.col = null;
	}

	public myToken(String token, String lexeme, Lista<Integer> lines, String value) {
		this.token = token;
		this.lexeme = lexeme;
		this.rows = new Lista<Integer>(lines);
		this.value = value;
		this.col = null;
	}
	
	// Métodos para instanciar los atributos
	/**
	 * Establece el identificador del tipo de token.
	 * 
	 * @param token El identificador del tipo de token.
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Establece el lexema asociado al token.
	 * 
	 * @param lexeme El lexema asociado al token.
	 */
	public void setLexeme(String lexeme) {
		this.lexeme = lexeme;
	}

	/**
	 * Añade un número de línea a la lista de líneas donde el token aparece.
	 * 
	 * @param line El número de línea a añadir.
	 */
	public void addLine(Integer line) {
		this.rows.addToEnd(line);
	}

	/**
	 * Añade una lista de línea a la lista de líneas donde el token aparece.
	 * 
	 * @param lines El número de línea a añadir.
	 */
	public void addLine(Lista<Integer> lines) {
	    for (int i = 0; i < lines.getSize(); i++) {
	        Integer line = lines.getElement(i);
	        if (!this.rows.contains(line)) {
	            this.rows.addToEnd(line);
	        }
	    }
	}

	/**
	 * Establece el valor asociado al token.
	 * 
	 * @param value El valor del lexema.
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Establece el valor de la columna
	 * 
	 * @param col El valor entero
	 */
	public void setCol(Integer col) {
		this.col = col;
	}

	// Métodos para obtener los atributos
	/**
	 * Obtiene el identificador del tipo de token.
	 * 
	 * @return El identificador del tipo de token.
	 */
	public String getToken() {
		return this.token;
	}

	/**
	 * Obtiene el lexema asociado al token.
	 * 
	 * @return El lexema del token.
	 */
	public String getLexeme() {
		return this.lexeme;
	}

	/**
	 * Obtiene una representación en cadena de las líneas donde el token aparece.
	 * 
	 * @return Una cadena que representa la lista de líneas donde el token aparece.
	 */
	public Lista<Integer> getLines() {
		return this.rows;
	}

	/**
	 * Obtiene el valor entero asociado al token.
	 * 
	 * @return El valor entero del token.
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * Obtiene la columna
	 * 
	 * @return El valor entero de la columna
	 */
	public Integer getCol() {
		return this.col;
	}
}