package Lenguajes_Automatas2.AnalizadorLexico;

/**
 * Requerimientos para la actividad:
 * <ul>
 * <li>Tomando como referencia el analizador léxico versión tres, construir un
 * analizador léxico para una expresión aritmética simple.</li>
 * <li>El analizador léxico leerá una cadena de texto y proporcionará los tokens
 * referente a la expresión leída.</li>
 * <li>Una expresión aritmética simple estará tomada por los símbolos: a, b, +,
 * *, ( y ). Donde a y b son operandos. + y * son operadores y los paréntesis
 * son para agrupar nuevas expresiones aritméticas simples.</li>
 * <li>Por ejemplo, la siguiente es una expresión aritmética simple:
 * b+(a+a*a)+(a*(a+b))*b*b+b.</li>
 * <li>Considerar dos palabras reservadas (tokens): una para antes y otra para
 * después de la expresión. Los nombres quedan a su criterio.</li>
 * <li>Por ejemplo, Inicia y Termina.</li>
 * <li>Usted define los símbolos que se leerán en la cadena fuente para
 * determinar cada token.</li>
 * <li>Por ejemplo, si en el archivo hay:
 * <ul>
 * <li>Una secuencia de xy's como xyxyxy (FA para la expresión regular (xy)^+ )
 * corresponde al tokenA.</li>
 * <li>Una secuencia de y seguida de ninguna o más x o y's como yxxyxyyy (FA
 * para la expresión regular y(x+y)^* corresponde al tokenB.</li>
 * <li>La secuencia xy en vez de ser tokenA considerarla como la palabra
 * reservada tokenInicia.</li>
 * <li>La secuencia xyxy en vez de ser tokenA, considerarla como la palabra
 * reservada tokenTermina.</li>
 * </ul>
 * </li>
 * <li>Los nombres de los tokens quedan a su criterio. Por ejemplo: TokenA o
 * simplemente a.</li>
 * <li>A efecto de que ciertos tokens manejen valor en lugar de lexemas,
 * considere al tokenB para tal efecto. De modo que las secuencias de x y y
 * representen un número binario siendo x cero y la y uno.</li>
 * <li>Por ejemplo, la secuencia yyxy representaría al número binario 1101 cuyo
 * valor en el sistema decimal sería 13.</li>
 * <li>El valor del token se va obteniendo conforme se vaya leyendo los
 * caracteres de entrada. Así, si se lee una y, se tendría el valor 1, si se lee
 * una segunda y, se tendría el valor 3 y si se leyese una x y luego una y, se
 * tendría el valor 13 y así sucesivamente.</li>
 * <li>Recuerde que la cadena fuente puede tener comentarios, cuyo texto es
 * ignorado.</li>
 * <li>El analizador léxico incluye un token (FA) para tomar en cuenta todas las
 * demás cadenas que no pertenecen a los tokens de una expresión aritmética
 * simple.</li>
 * <li>El analizador léxico incluye un token (FA) para el fin de archivo.</li>
 * <li>El archivo debe contener texto suficiente para mostrar todos los
 * elementos del analizador léxico.</li>
 * <li>El archivo debe contener texto para tener lexemas duplicados.</li>
 * <li>El programa debe mostrar:
 * <ol>
 * <li>El nombre del analizador léxico.</li>
 * <li>La descripción del analizador léxico.</li>
 * <li>El listado de los tokens existentes en el archivo fuente. Por cada token
 * mostrado indicar, su nombre, lexema o valor y la línea donde se
 * encuentra.</li>
 * <li>El listado de la tabla de símbolos. Por cada token mostrado de la tabla,
 * indicar, su nombre, lexema (único) y las líneas donde se encuentran.</li>
 * <li>[opcional] Mostrar de igual manera la columna donde inicia o termina el
 * token (lexema) cuando se muestre el listado de todos los tokens y el listado
 * de la tabla de símbolos.</li>
 * </ol>
 * </li>
 * </ul>
 */

public class AnalizadorLexico_pEAS {

}
