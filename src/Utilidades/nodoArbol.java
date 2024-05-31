package Utilidades;

import java.util.NoSuchElementException;

/**
 * Clase que representa un nodo de un árbol n-ario. Cada nodo contiene un dato
 * de tipo genérico E y una lista de nodos hijos.
 *
 * @param <E> El tipo de los elementos que contiene el nodo
 * @see Lista
 * @author Luis Emmanuel Torres Olvera
 */
@SuppressWarnings("unchecked")
public class nodoArbol<E> {
	// Dato almacenado en el nodo
	private E data;
	// El tipo de dato almacenado
	private E type;
	// Lista de nodos hijos
	private Lista<nodoArbol<E>> pointers;

	//CONSTRUCTORES
	/**
	 * Constructor por defecto. Inicializa un nodo sin dato y con una lista de nodos
	 * hijos vacía.
	 */
	public nodoArbol() {
		this.data = null;
		this.type = null;
		this.pointers = new Lista<nodoArbol<E>>();
	}

	/**
	 * Constructor que inicializa el nodo con un dato.
	 * 
	 * @param data El dato a almacenar en el nodo.
	 */
	public nodoArbol(E data) {
		this.data = data;
		this.type = null;
		this.pointers = new Lista<nodoArbol<E>>();
	}

	/**
	 * Constructor que inicializa el nodo con un dato y un nodo hijo.
	 * 
	 * @param data El dato a almacenar en el nodo.
	 * @param node El nodo hijo a añadir al final de la lista de nodos hijos.
	 */
	public nodoArbol(E data, nodoArbol<E> node) {
		this.data = data;
		this.type = null;
		this.pointers = new Lista<nodoArbol<E>>(node);
	}

	/**
	 * Constructor que inicializa el nodo con un dato y varios nodos hijos. Los
	 * nodos hijos se añaden al inicio o al final de la lista de nodos hijos según
	 * la bandera.
	 * 
	 * @param data El dato a almacenar en el nodo.
	 * @param band Bandera que indica si añadir al final (true) o al inicio (false).
	 * @param node Los nodos hijos a añadir.
	 */
	public nodoArbol(E data, boolean band, nodoArbol<E>... node) {
		this.type = null;
		this.data = data;
		this.pointers = new Lista<nodoArbol<E>>();
		if (band) {
			for (int i = 0; i < node.length; i++) {
				this.pointers.addToEnd(node[i]);
			}
		} else {
			for (int i = 0; i < node.length; i++) {
				this.pointers.addToStart(node[i]);
			}
		}
	}

	/**
	 * Constructor que inicializa el nodo con un dato y una lista de nodos hijos
	 * existente.
	 * 
	 * @param data  El dato a almacenar en el nodo.
	 * @param nodes La lista de nodos hijos a asociar con el nodo actual.
	 */
	public nodoArbol(E data, Lista<nodoArbol<E>> nodes) {
		this.type = null;
		this.data = data;
		this.pointers = new Lista<nodoArbol<E>>(nodes);
	}

	//ASIGNACION DE DATOS Y NODOS
	/**
	 * Establece el dato del nodo.
	 * 
	 * @param data El nuevo dato a almacenar en el nodo.
	 */
	public void setData(E data) {
		this.data = data;
	}

	/**
	 * Establece el tipo de dato del nodo.
	 * 
	 * @param data El nuevo tipo de dato a almacenar en el nodo.
	 */
	public void setType(E type) {
		this.type = type;
	}
	
	/**
	 * Añade un nodo hijo al final de la lista de nodos hijos.
	 * 
	 * @param node El nodo hijo a añadir.
	 */
	public void addNodeToRight(nodoArbol<E> node) {
		this.pointers.addToEnd(node);
	}

	/**
	 * Añade varios nodos hijos al final de la lista de nodos hijos.
	 * 
	 * @param node Los nodos hijos a añadir.
	 */
	public void addNodeToRight(nodoArbol<E>... node) {
		for (int i = 0; i < node.length; i++) {
			this.pointers.addToEnd(node[i]);
		}
	}

	/**
	 * Añade un nodo hijo al inicio de la lista de nodos hijos.
	 * 
	 * @param node El nodo hijo a añadir.
	 */
	public void addNodeToLeft(nodoArbol<E> node) {
		this.pointers.addToStart(node);
	}

	/**
	 * Añade varios nodos hijos al inicio de la lista de nodos hijos.
	 * 
	 * @param node Los nodos hijos a añadir.
	 */
	public void addNodeToLeft(nodoArbol<E>... node) {
		for (int i = 0; i < node.length; i++) {
			this.pointers.addToStart(node[i]);
		}
	}

	/**
	 * Añade un nodo hijo en una posición específica de la lista de nodos hijos.
	 * 
	 * @param node  El nodo hijo a añadir.
	 * @param index El índice donde añadir el nodo hijo.
	 */
	public void addNode(int index, nodoArbol<E> node) {
		if (index >= this.pointers.getSize() || index < 0) {
			throw new IndexOutOfBoundsException("Index fuera de parametros de los nodos: " + index);
		}
		this.pointers.add(node, index);
	}

	/**
	 * Añade varios nodos hijos al inicio o al final de la lista de nodos hijos
	 * según la bandera.
	 * 
	 * @param band Bandera que indica si añadir al final (true) o al inicio (false).
	 * @param node Los nodos hijos a añadir.
	 */
	public void addNode(boolean band, nodoArbol<E>... node) {
		if (band) {
			for (int i = 0; i < node.length; i++) {
				this.pointers.addToEnd(node[i]);
			}
		} else {
			for (int i = 0; i < node.length; i++) {
				this.pointers.addToStart(node[i]);
			}
		}
	}

	//OBTENCION DE DATOS Y NODOS
	/**
	 * Obtiene el dato almacenado en el nodo.
	 * 
	 * @return El dato del nodo.
	 */
	public E getData() {
		return data;
	}

	/**
	 * Obtiene el tipo de dato almacenado en el nodo.
	 * 
	 * @return El tipo de dato del nodo.
	 */
	public E getType() {
		return type;
	}
	
	/**
	 * Obtiene el primer nodo hijo de la lista de nodos hijos.
	 * 
	 * @return El primer nodo hijo.
	 * @throws NoSuchElementException Si el nodo no tiene nodos hijos.
	 */
	public nodoArbol<E> getLeftNode() {
		if (this.pointers.isEmpty()) {
			throw new NoSuchElementException("El nodo no contiene nodos hijo");
		}
		return this.pointers.getFirstElement();
	}

	/**
	 * Obtiene el último nodo hijo de la lista de nodos hijos.
	 * 
	 * @return El último nodo hijo.
	 * @throws NoSuchElementException Si el nodo no contiene nodos hijos.
	 */
	public nodoArbol<E> getRightNode() {
		if (this.pointers.isEmpty()) {
			throw new NoSuchElementException("El nodo no contiene nodos hijo");
		}
		return this.pointers.getLastElement();
	}

	/**
	 * Obtiene un nodo hijo específico de la lista de nodos hijos por índice.
	 * 
	 * @param index El índice del nodo hijo a obtener.
	 * @return El nodo hijo en el índice especificado.
	 * @throws NoSuchElementException    Si el nodo no tiene nodos hijos.
	 * @throws IndexOutOfBoundsException Si el índice está fuera de los límites.
	 */
	public nodoArbol<E> getNode(int index) {
		if (this.pointers.isEmpty()) {
			throw new NoSuchElementException("El nodo no contiene nodos hijo");
		} else if (index >= this.pointers.getSize() || index < 0) {
			throw new IndexOutOfBoundsException("Index fuera de parametros: " + index);
		}
		return this.pointers.getElement(index);
	}

	/**
	 * Obtiene la lista completa de nodos hijos.
	 * 
	 * @return La lista de nodos hijos.
	 */
	public Lista<nodoArbol<E>> getAllNodes() {
		return this.pointers;
	}

	// OTROS METODOS
	/**
	 * Este metodo verifica que este nodo sea una hoja, lo que quiere decir que no
	 * tiene nodos hijos
	 * 
	 * @return Verdadero si es una hoja falso de lo contrario
	 */
	public boolean isLeaf() {
		return this.pointers.isEmpty();
	}

	/**
	 * Este metodo retorna cual es la altura a partir de este nodo hacia abajo
	 * 
	 * @return La altura
	 */
	public int height() {
		if (this.isLeaf()) {
			return 1;
		}
		int maxHeight = 0;
		for (int i = 0; i < this.pointers.getSize(); i++) {
			nodoArbol<E> child = this.pointers.getElement(i);
			int childHeight = child.height();
			if (childHeight > maxHeight) {
				maxHeight = childHeight;
			}
		}
		return 1 + maxHeight;
	}

	/**
	 * Este metodo calcula la cantidad de nodos que hay por debajo a partir de este
	 * nodo
	 * 
	 * @return La cantidad de hijos que tiene este nodo
	 */
	public int size() {
		int size = 1; // Contar este nodo
		for (int i = 0; i < this.pointers.getSize(); i++) {
			nodoArbol<E> child = this.pointers.getElement(i);
			size += child.size();
		}
		return size;
	}

	/**
	 * Este metodo crea una copia exacta de este nodo junto con todos los subnodos
	 * que lo componen
	 * 
	 * @return Un nodoArbol que contiene todos los elementos de este nodo y sus
	 *         hijos
	 */
	public nodoArbol<E> deepCopy() {
		nodoArbol<E> newNode = new nodoArbol<>(this.data);
		for (int i = 0; i < this.pointers.getSize(); i++) {
			nodoArbol<E> child = this.pointers.getElement(i);
			newNode.addNodeToLeft(child.deepCopy());
			;
		}
		return newNode;
	}
}
