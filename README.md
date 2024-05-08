# Lenguajes y Autómatas

El proyecto "Lenguajes y Autómatas" presenta una guía exhaustiva para comprender y aplicar análisis léxico, autómatas finitos y de pila, así como utilidades para manipular cadenas de texto en Java. Desde un analizador léxico básico hasta uno avanzado capaz de manejar expresiones aritméticas simples, junto con una variedad de autómatas finitos y de pila con diferentes reglas y aplicaciones específicas. Además, ofrece un paquete de utilidades que incluye clases para manipular listas enlazadas, nodos, pilas y cadenas de texto, facilitando operaciones como validación, extracción de subcadenas y comparaciones precisas.

## Contenido del Proyecto

### 1. **Analizador Léxico**
Diez versiones del analizador léxico:

1. **Lexical_v1**: Cree un programa Java para el primer análisis léxico y tome su DFA M(L) o el de su preferencia, para que la cadena se lea desde el archivo. Por ejemplo, si el contenido del archivo myString tiene: 1011101, el análisis léxico indicará: la cadena 1011101 no pertenece al lenguaje. Puede utilizar el método que prefiera para leer los caracteres del archivo.

2. **Lexical_v2**: Incluya espacios, tabulaciones y saltos de línea en el archivo myString y mejore el analizador léxico para que limpie el archivo de entrada. Deberá eliminar los espacios en blanco, las tabulaciones y los saltos de línea.

3. **Lexical_v3**: Cree un programa Java para el análisis léxico que omita los comentarios. Omitirá comentarios de una línea, múltiples líneas o parte de una línea.

4. **Lexical_v4**: Añada un segundo Autómata Finito (FA) al analizador léxico. Este enviará tokens según el lenguaje (FA) como muestra la tabla anterior. Intente hacer que el FA sea exclusivamente mutuo. Usted deberá decidir manualmente (modificando el código fuente) qué autómata funcionará.

5. **Lexical_v5**: Incluya más de dos FA en el analizador léxico para que no sean mutuamente exclusivos. Enviarán tokens según el lenguaje (FA). Intente usar DFA, NFA y NFA-ε. Establezca una prioridad para que el léxico seleccione automáticamente uno de ellos (mutuo o no). Mostrará cada token, lexema y línea encontrados en el archivo myString. Si alguna cadena no pertenece (se encuentra) en algún autómata, se escribirá el mensaje "La cadena <0000> no pertenece a ningún lenguaje".

6. **Lexical_v6**: Incluya varias cadenas en el archivo myString y defina el carácter especial para separar una cadena de otra. Configure el analizador léxico para que muestre cada token.

7. **Lexical_v7**: Integre una tabla de símbolos en el analizador léxico para que guarde cada token del archivo myString. La tabla de símbolos también almacenará cada número de línea donde se encuentre el lexema. La tabla tendrá una instancia para cada lexema. El análisis léxico mostrará la tabla de símbolos.

8. **Lexical_v8**: Seleccione uno de los autómatas para que proporcione el valor en lugar del lexema en el análisis léxico. La tabla de símbolos también guardará este token. El análisis léxico mostrará la tabla de símbolos.

9. **Lexical_v9**: Seleccione algunos autómatas en el análisis léxico para que sean palabras reservadas y otros para que representen un solo carácter. El análisis léxico mostrará la tabla de símbolos incluyendo las palabras reservadas y los tokens de un solo carácter.

10. **Lexical_v10_SAE**: Mejore el analizador léxico para expresiones aritméticas simples. Se le permite elegir qué autómatas representarán cada token.

### 2. **Autómatas Finitos**
Siete autómatas finitos:

- **L1**: {w | w begins with 0 and ends with 1}
- **L2**: {w | w begins with 0 and ends with 0}
- **L3**: {w | |w| = 2}
- **L4**: {w | w is composed by sequences of 10}
- **L5**: {w | w has the sequence of 000 (three consecutive 0’s)}
- **L6**: {w | w begins with 1}
- **L7**: {w | w has two consecutive 1’s}

### 3. **Autómatas de Pila (PDA)**
- **L1**: {w | w is a palindrome of a's and b's}
- **L2**: {w c w^r | w is in (1+0)^*}
- **L3**: {w w^r | w is in (1+0)^*}
- **L4**: {a^n b^n | n>1}

### 4. **Paquete de Utilidades**
Este paquete contiene:

- **Clase "Lista.java"**
  - **Descripcion**
     -  Esta clase representa una lista enlazada genérica, donde los elementos están enlazados secuencialmente. Permite operaciones básicas como añadir, eliminar y obtener elementos de la lista, tanto al inicio como al final o en una posición específica. Además, ofrece métodos para verificar si un elemento está presente, si la lista está vacía y para obtener el tamaño de la lista. Es una implementación desde cero sin usar paquetes externos, permitiendo un control total sobre las operaciones y la estructura de datos.
  - **Variables**
    - `root: nodoLista<E>`
    - `size: int`
  - **Constructores**
    - `Lista()`
    - `Lista(E)`
    - `Lista(E...)`
    - `Lista(Lista<E>)`
  - **Métodos**
    - `add(E, int)`
    - `addToEnd(E)`
    - `addToEnd(E...)`
    - `addToStart(E)`
    - `addToStart(E...)`
    - `clear()`
    - `contains(E)`
    - `getElement(int)`
    - `getFirstElement()`
    - `getLastElement()`
    - `getRoot()`
    - `getSize()`
    - `isEmpty()`
    - `listToString()`
    - `printList()`
    - `remove(int)`
    - `removeAllOccurrences(E)`
    - `removeDuplicates()`
    - `removeFirstElement()`
    - `removeLastElement()`

- **Clase "nodoLista.java"**
  - **Descripcion**
     -  Clase que representa un nodo individual en una lista enlazada. Cada nodo contiene un dato y una referencia al siguiente nodo en la lista. Esta clase está diseñada para ser utilizada internamente por la clase Lista para manejar de forma eficiente las operaciones sobre los elementos. La clase proporciona métodos para configurar y obtener tanto el dato almacenado como la referencia al siguiente nodo, permitiendo así manipular la estructura de la lista enlazada desde la clase Lista.
  - **Variables**
    - `data: E`
    - `next: nodoLista<E>`
  - **Constructores**
    - `nodoLista()`
    - `nodoLista(E)`
    - `nodoLista(E, nodoLista<E>)`
    - `nodoLista(nodoLista<E>)`
  - **Métodos**
    - `getData()`
    - `getNext()`
    - `setData(E)`
    - `setNext(nodoLista<E>)`

- **Una clase Pila**
  - **Variables**
    - `List: Lista<E>`
    - `top: int`
  - **Métodos**
    - `Pila()`
    - `clear()`
    - `getSize()`
    - `isEmpty()`
    - `peek()`
    - `pop()`
    - `printStack()`
    - `push(E)`

- **Clase "StringHandler"**
  - **Descripcion**
     -  Clase utilitaria para manejar cadenas de texto en diversas operaciones comunes como validación, extracción de subcadenas, comparación y manipulación de caracteres. Incluye métodos estáticos que facilitan la validación de caracteres dentro de un alfabeto, la manipulación y búsqueda de subcadenas, y la comparación precisa de dos cadenas.
  - **Métodos**
    - `compareStrings(String, String)`
    - `contains(String, String)`
    - `debugString(String)`
    - `getChar(String, int)`
    - `isEmpty(String)`
    - `isWhiteSpace(char)`
    - `printFAtransition()`
    - `printPDAtransition(int, char, char)`
    - `replace(String, String, String)`
    - `reverse(String)`
    - `subCadena(String, int)`
    - `subCadena(String, int, int)`
    - `validateAlphabet(String, char[])`

- **Clase "LexicalUtility.java"**
  - **Descripcion**
     -  LexicalUtility es una clase enfocada a facilitar el trabajo gracias a la reutilización de código en las distintas versiones del analizador léxico de la versión 1 a la 10, analizador léxico mínimo, analizador léxico básico, y analizador léxico para expresiones aritméticas simples (pEAS).
  - **Constantes**
    - `DELIMITER: char`
  - **Variables**
    - `FinalLexicalResult: Lista<myToken>`
    - `OriginalLexeme: Lista<myToken>`
  - **Métodos**
    - `clearWhiteSpaces(String)`
    - `deleteComments(String)`
    - `getSymbolTable(Lista<myToken>)`
    - `getTextFile(String)`
    - `printInput(String)`
    - `printTable(Lista<myToken>)`
    - `searchLines(String)`
    - `searchLinesAndColumns(String)`
    - `separateStrings(String)`
    - `splitStrings(String)`

- **Clase "myToken.java"**
  - **Descripcion**
     -  La clase Token representa un token léxico en un contexto de análisis sintáctico, almacenando detalles como el tipo de token, el lexema asociado, las líneas y columnas donde aparece, y un valor entero opcional asociado con el token.
  - **Variables**
    - `col: Integer`
    - `lexeme: String`
    - `rows: Lista<Integer>`
    - `token: String`
    - `value: String`
  - **Constructores**
    - `myToken()`
    - `myToken(String)`
    - `myToken(String, String, Integer)`
    - `myToken(String, String, Integer, String, Integer)`
    - `myToken(String, String, Lista<Integer>, String)`
  - **Métodos**
    - `addLine(Integer)`
    - `addLine(Lista<Integer>)`
    - `getCol()`
    - `getLexeme()`
    - `getLines()`
    - `getToken()`
    - `getValue()`
    - `setCol(Integer)`
    - `setLexeme(String)`
    - `setToken(String)`
    - `setValue(String)`

### 5. **Analizador Léxico**

Ahora ya puedes pasar Lenguajes y Automatas :D

# Licencia

Este proyecto está licenciado bajo [Licencia Creative Commons Atribución 4.0 Internacional (CC BY 4.0)](LICENSE).