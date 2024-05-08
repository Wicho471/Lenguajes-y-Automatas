package Utilidades;

/**<h1>Nodo para las Listas Simplente Enlazadas</h1>
 * Clase que representa un nodo individual en una lista enlazada. Cada nodo
 * contiene un dato y una referencia al siguiente nodo en la lista. Esta clase
 * está diseñada para ser utilizada internamente por la clase {@link Lista} para
 * manejar de forma eficiente las operaciones sobre los elementos. La clase
 * proporciona métodos para configurar y obtener tanto el dato almacenado como
 * la referencia al siguiente nodo, permitiendo así manipular la estructura de
 * la lista enlazada desde la clase Lista.
 *
 * @param <E> El tipo de los elementos almacenados en el nodo.
 * @author Luis Emmanuel Torres Olvera
 * @version 1.0
 */
public class nodoLista<E> {
	private E data; // Elemento de datos almacenado en este nodo.
    private nodoLista<E> next; // Referencia al siguiente nodo en la lista.

	/**
	 * Constructor por defecto. Inicializa un nodo sin datos y sin referencia al
	 * siguiente nodo. Este constructor se utiliza generalmente para crear un nodo
	 * cabecera o final en la lista.
	 */
	nodoLista() {
		this.next = null;
		this.data = null;
	}

	/**
	 * Inicializa un nodo con datos específicos y una referencia al siguiente nodo.
	 * Este constructor es útil para insertar un nuevo nodo entre dos nodos
	 * existentes o al final de la lista.
	 *
	 * @param data El dato a almacenar en el nodo.
	 * @param nodo El siguiente nodo en la lista después de este nodo.
	 */
	nodoLista(E data, nodoLista<E> nodo) {
		this.next = nodo;
		this.data = data;
	}

	/**
	 * Constructor que copia la referencia al siguiente nodo pero no los datos.
	 * Utilizado principalmente para manejar referencias sin duplicar contenido.
	 *
	 * @param nodo El nodo que se tomará como siguiente.
	 */
	nodoLista(nodoLista<E> nodo) {
		this.next = nodo;
	}

	/**
	 * Inicializa un nodo con datos y sin un siguiente nodo. Este constructor es
	 * especialmente útil para añadir un nuevo nodo al final de la lista.
	 *
	 * @param data El dato a almacenar en el nodo.
	 */
	nodoLista(E data) { // Este constructor sirve para el ultimo elemento de la lista
		this.data = data;
		this.next = null;
	}

	// Funciones especificas
	/**
	 * Devuelve los datos almacenados en este nodo.
	 * 
	 * @return El dato almacenado en el nodo.
	 */
	public E getData() {
		return this.data;
	}

	/**
	 * Establece o modifica los datos almacenados en este nodo.
	 *
	 * @param data El nuevo dato a almacenar en el nodo.
	 */
	public void setData(E data) {
		this.data = data;
	}

	/**
	 * Establece la referencia al siguiente nodo en la lista.
	 *
	 * @param nodo El nuevo siguiente nodo para este nodo.
	 */
	public void setNext(nodoLista<E> nodo) {
		this.next = nodo;
	}

	/**
	 * <p>
	 * Obtiene la referencia al siguiente nodo en la lista.
	 * </p>
	 * <p>
	 * Este es el uno de los metodos, si no es que el mas importante de esta clase
	 * </p>
	 * 
	 * @return El siguiente nodo en la lista, o null si no hay siguiente.
	 */
	public nodoLista<E> getNext() {
		return this.next;
	}
}
