package Utilidades;

import java.util.NoSuchElementException;

/**
 * <h1>Lista Simplemente Enlazada</h1>
 * <p>
 * Esta clase representa una lista enlazada genérica, donde los elementos están
 * enlazados secuencialmente. Permite operaciones básicas como añadir, eliminar
 * y obtener elementos de la lista, tanto al inicio como al final o en una
 * posición específica. Además, ofrece métodos para verificar si un elemento
 * está presente, si la lista está vacía y para obtener el tamaño de la lista.
 * Es una implementación desde cero sin usar paquetes externos, permitiendo un
 * control total sobre las operaciones y la estructura de datos.
 * </p>
 * 
 * @param <E> El tipo de los elementos que contiene la lista
 * @author Luis Emmanuel Torres Olvera.
 * @see {@link nodoLista}
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class Lista<E> {
	private nodoLista<E> root;
	private int size;

	/**
	 * Constructor que inicializa una nueva lista vacía.
	 */
	public Lista() {
		this.root = null;
		this.size = 0;
	}

	/**
	 * Constructor que inicializa un elemento.
	 */
	public Lista(E data) {
		addToEnd(data);
	}
	
	/**
	 * Constructor que inicializa un vector de elementos.
	 */
	
	public Lista(E... data) {
		for (int i = 0; i < data.length; i++) {
			addToEnd(data[i]);
		}
	}

	/**
	 * Constructor que copia los elementos de una lista
	 * 
	 * @param list La nueva lista
	 */
	public Lista(Lista<E> list) {
		for (int i = 0; i < list.getSize(); i++) {
			addToEnd(list.getElement(i));
		}
	}

	// Metodos para eleminar elementos de la lista
	/**
	 * Elimina y retorna el último elemento de la lista. Si la lista está vacía,
	 * retorna null.
	 *
	 * @return El último elemento de la lista, o null si la lista está vacía.
	 */
	public E removeLastElement() {
		if (isEmpty() || this.root.getNext() == null) { // Verifica que el elemento de la lista no sea el ultimo o este
														// vacio
			this.root = null;
			this.size = 0;
			return null;
		} else {
			nodoLista<E> aux = this.root;
			while (aux.getNext().getNext() != null) {
				aux = aux.getNext();
			}
			nodoLista<E> res = new nodoLista<>(aux.getNext().getData());
			aux.setNext(null);
			this.size--;
			return res.getData();
		}

	}

	/**
	 * Elimina y retorna el primer elemento de la lista. Si la lista está vacía,
	 * retorna null.
	 *
	 * @return El primer elemento de la lista, o null si la lista está vacía.
	 */
	public E removeFirstElement() {
		if (!isEmpty() || this.root.getNext() != null) {
			E res = getFirstElement();
			this.root = this.root.getNext();
			this.size--;
			return res;
		}
		return null;
	}

	/**
	 * Elimina un elemento de la lista en el índice especificado. Si el índice es
	 * menor que cero o mayor o igual que el tamaño de la lista, se ajusta para
	 * eliminar el elemento al principio o al final, respectivamente.
	 *
	 * @param index El índice del elemento a eliminar.
	 * @throws IndexOutOfBoundsException Si el índice está fuera del rango y la
	 *                                   lista no está vacía.
	 */
	public void remove(int index) {
		if (!isEmpty()) { // Primero verifica que la lista no este vacia
			if (index <= 0) { // Si el index es menor a 0 lo quita el elemtno al inicio de la lista
				removeFirstElement();
			} else if (index >= this.size) { // Si el index es mayor al tamaño de la lista actual lo quita un elemento
												// al
				// final de la lista
				removeLastElement();
			} else {
				nodoLista<E> current = this.root;
				int cont = 0;
				while (current != null && cont < index - 1) {
					current = current.getNext();
					cont++;
				}
				if (current == null || current.getNext() == null) {
					throw new IndexOutOfBoundsException("Indice fuera de rango: " + index);
				}
				current.setNext(current.getNext().getNext());
				this.size--;
			}
		}
	}

	/**
	 * Elimina todos los elementos duplicados de la lista. Cada elemento quedará
	 * solo una vez.
	 */
	public void removeDuplicates() {
		nodoLista<E> current = this.root;
		while (current != null && current.getNext() != null) {
			nodoLista<E> checker = current;
			while (checker.getNext() != null) {
				if (current.getData().equals(checker.getNext().getData())) {
					// Duplicado encontrado, eliminar el nodo duplicado.
					checker.setNext(checker.getNext().getNext());
					this.size--; // Decrementar el tamaño de la lista
				} else {
					// Avanzar al siguiente nodo si no es un duplicado
					checker = checker.getNext();
				}
			}
			current = current.getNext();
		}
	}

	/**
	 * Elimina todas las recurrencias del elemento especificado en la lista. Este
	 * método recorre la lista y elimina cada nodo que contiene el dato igual al
	 * elemento proporcionado.
	 *
	 * @param element El elemento a eliminar de la lista.
	 * @return true si se eliminó al menos un elemento, false si el elemento no
	 *         estaba presente en la lista.
	 */
	public boolean removeAllOccurrences(E element) {
		if (this.root == null) {
			return false; // Si la lista está vacía, no hay nada que eliminar.
		}

		boolean found = false;
		nodoLista<E> current = this.root;
		nodoLista<E> prev = null;

		// Verificar y eliminar cualquier coincidencia en el primer nodo.
		while (this.root != null && this.root.getData().equals(element)) {
			this.root = this.root.getNext(); // Mover la raíz al siguiente nodo.
			this.size--; // Decrementar el tamaño de la lista.
			found = true;
		}

		// Continuar revisando el resto de la lista.
		current = this.root;
		while (current != null) {
			while (current != null && !current.getData().equals(element)) {
				prev = current;
				current = current.getNext();
			}

			if (current == null) {
				break; // Salir del bucle si no hay más nodos.
			}

			// Eliminar el nodo actual.
			prev.setNext(current.getNext());
			current = prev.getNext();
			this.size--; // Decrementar el tamaño.
			found = true;
		}

		return found;
	}

	// Metodos para añadir elementos a la lista
	/**
	 * Añade un elemento al inicio de la lista.
	 *
	 * @param data El elemento a añadir.
	 */
	public void addToStart(E data) {
		if (isEmpty()) {
			this.root = new nodoLista<E>(data);
		} else {
			this.root = new nodoLista<>(data, this.root);
		}
		this.size++;
	}

	/**
	 * Añade un arreglo de elementos al inicio de la lista.
	 *
	 * @param arrayData El arreglo de elementos a añadir.
	 */
	public void addToStart(E... arrayData) {
		for (int i = 0; i < arrayData.length; i++) {
			addToStart(arrayData[i]);
		}
	}

	/**
	 * Añade un elemento al final de la lista.
	 *
	 * @param data El elemento a añadir.
	 */
	public void addToEnd(E data) {
		if (isEmpty()) {
			this.root = new nodoLista<E>(data);
		} else {
			nodoLista<E> aux = this.root;
			nodoLista<E> aux2 = new nodoLista<>(data);
			while (aux.getNext() != null) {
				aux = aux.getNext();
			}
			aux.setNext(aux2);
		}
		this.size++;
	}

	/**
	 * Añade un arreglo de elementos al final de la lista.
	 *
	 * @param arrayData El arreglo de elementos
	 */

	public void addToEnd(E... arrayData) {
		for (int i = 0; i < arrayData.length; i++) {
			addToEnd(arrayData[i]);
		}
	}

	/**
	 * Añade un elemento en un índice específico de la lista. Si el índice es menor
	 * que cero, añade al inicio. Si es mayor o igual que el tamaño, añade al final.
	 *
	 * @param data  El elemento a añadir.
	 * @param index El índice donde se añadirá el elemento.
	 */
	public void add(E data, int index) {
		if (!isEmpty()) { // Primero verifica que la lista no este vacia
			if (index <= 0) { // Si el index es menor a 0 lo añande al inicio de la lista
				addToStart(data);
			} else if (index >= this.size) { // Si el index es mayor al tamaño de la lista actual lo añade al final de
												// la
				// lista
				addToEnd(data);
			} else {
				nodoLista<E> aux = new nodoLista<E>(data);
				nodoLista<E> current = this.root;
				int contador = 0;
				while (current != null && contador < index - 1) {
					current = current.getNext();
					contador++;
				}
				if (current == null) {
					throw new IndexOutOfBoundsException("Indice fuera de rango: " + index);
				}
				aux.setNext(current.getNext());
				current.setNext(aux);
			}
			this.size++;
		}

	}

	// Metodos para la obtencion de datos en la lista
	/**
	 * Obtiene el último elemento de la lista.
	 *
	 * @return El último elemento de la lista, o null si la lista está vacía.
	 * @throws NoSuchElementException Si es que la lista esta vacia
	 */
	public E getLastElement() {
		if (isEmpty()) {
			throw new NoSuchElementException("La lista esta vacia");
		}
		nodoLista<E> aux = this.root;
		while (aux.getNext() != null) {
			aux = aux.getNext();
		}
		return aux.getData();
	}

	/**
	 * Obtiene el elemento en el índice especificado.
	 *
	 * @param index El índice del elemento a obtener.
	 * @return El elemento en el índice especificado.
	 * @throws IndexOutOfBoundsException Si el índice está fuera de rango.
	 */
	public E getElement(int index) {
		nodoLista<E> aux = this.root;
		int cont = 0;
		while (aux != null) {
			if (cont == index) {
				return aux.getData();
			}
			cont++;
			aux = aux.getNext();
		}
		throw new IndexOutOfBoundsException("Indice fuera de rango: " + index);
	}

	/**
	 * Retorna el primer elemento de la lista.
	 *
	 * @return El primer elemento de la lista.
	 * @throws NoSuchElementException Si es que la lista esta vacia
	 */
	public E getFirstElement() {
		if (isEmpty()) {
			throw new NoSuchElementException("La lista esta vacia");
		}
		return this.root.getData();
	}

	// Otras funciones
	/**
	 * Verifica si un elemento específico está en la lista.
	 *
	 * @param data El elemento a buscar en la lista.
	 * @return true si el elemento está en la lista, false de lo contrario.
	 */
	public boolean contains(E data) {
		if (this.root == null) {
			return false;
		}
		nodoLista<E> aux = this.root;
		while (aux != null) {
			if (aux.getData().equals(data)) {
				return true;
			}
			aux = aux.getNext();
		}
		return false;
	}

	/**
	 * Imprime todos los elementos de la lista en consola.
	 */
	public void printList() {
		nodoLista<E> aux = this.root;
		System.out.print("[Inicio]--->");
		while (aux != null) {
			System.out.print("[" + aux.getData() + "]--->");
			aux = aux.getNext();
		}
		System.out.println("[Final]");
	}

	/**
	 * Obtiene todos los elementos de la lista
	 * @return Una cadena con todos los elementos de la lista
	 */
	public String listToString() {
		String str = "";
		nodoLista<E> aux = this.root;
		while (aux != null) {
			str += aux.getData() + " ";
			aux = aux.getNext();
		}
		return str;
	}

	/**
	 * Limpia la lista, eliminando todos los elementos.
	 */
	public void clear() {
		this.root = null;
	}

	/**
	 * Verifica si la lista está vacía.
	 *
	 * @return true si la lista no tiene elementos, false de lo contrario.
	 */
	public boolean isEmpty() {
		return this.root == null;
	}

	/**
	 * Retorna el tamaño de la lista.
	 *
	 * @return El número de elementos en la lista.
	 */
	public int getSize() {
		return this.size;
	}
}
