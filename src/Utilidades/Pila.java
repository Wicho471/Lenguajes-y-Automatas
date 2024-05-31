package Utilidades;

import java.util.EmptyStackException;

/**
 * <h1>Pilas</h1>
 * <p>
 * Esta clase representa una estructura de datos tipo pila, que opera bajo el
 * principio de último en entrar, primero en salir (LIFO). Utiliza internamente
 * una instancia de la clase {@link Lista} para almacenar los elementos. Permite
 * operaciones básicas de una pila como añadir, remover y observar el elemento
 * en la cima, además de verificar si la pila está vacía, imprimir su contenido
 * y limpiar todos los elementos.
 * </p>
 * @param <E> El tipo de elementos que la pila puede almacenar.
 * @author Luis Emmanuel Torres Olvera
 * @see Lista
 * @version 1.0
 */
public class Pila<E> {
	private Lista<E> List;
	private int top;

	/**
	 * Constructor que inicializa una nueva pila. Inicializa el contador de
	 * elementos (top) en cero.
	 */
	public Pila() {
		top = 0;
		List = new Lista<E>();
	}

	/**
	 * Añade un elemento al tope de la pila. Incrementa el contador top tras añadir
	 * el elemento.
	 * 
	 * @param data El elemento a añadir.
	 * @return El elemento añadido.
	 */
	public E push(E data) {
		List.addToEnd(data);
		top++;
		return data;
	}

	/**
	 * Elimina y retorna el elemento en el tope de la pila. Si la pila está vacía,
	 * lanza una EmptyStackException.
	 * 
	 * @return El elemento en el tope de la pila.
	 * @throws EmptyStackException Si la pila está vacía.
	 */
	public E pop() {
		E data;
		if (isEmpty()) {
			System.out.println("Pila vacia");
			throw new EmptyStackException();
		} else {
			data = peek();
			List.removeLastElement();
			top--;
		}
		return data;
	}

	/**
	 * Retorna el elemento en el tope de la pila sin eliminarlo. Si la pila está
	 * vacía, lanza una EmptyStackException.
	 * 
	 * @return El elemento en el tope de la pila.
	 * @throws EmptyStackException Si la pila está vacía.
	 */
	public E peek() {
		E data;
		if (isEmpty()) {
			System.out.println("Pila vacia");
			throw new EmptyStackException();
		} else {
			data = List.getLastElement();
		}
		return data;
	}

	// Otros metodos
	/**
	 * Imprime todos los elementos de la pila en el orden correcto sin alterar el
	 * estado de la pila. Utiliza una pila auxiliar para invertir el orden
	 * temporalmente y luego restaurar el estado original.
	 */
	public void printStack() {
		System.out.print("[Base]--->");
		Pila<E> aux = new Pila<>();
		while (!isEmpty()) {
			aux.push(pop());
		}
		while (!aux.isEmpty()) { // Imprimo los elementos de la lista al mismo tiempo que los regreso a su pila
									// original
			E temp = aux.pop();
			System.out.print("[" + temp + "]--->");
			this.push(temp);
		}
		System.out.println("[Top]");
	}

	/**
	 * Verifica si la pila está vacía.
	 * 
	 * @return true si la pila no tiene elementos, false en caso contrario.
	 */
	public boolean isEmpty() {
		return this.top == 0;
	}

	/**
	 * Limpia la pila, eliminando todos sus elementos.
	 */
	public void clear() {
		this.List = null;
	}

	/**
	 * Retorna el tamaño actual de la pila, es decir, el número de elementos
	 * contenidos en ella.
	 * 
	 * @return El número de elementos en la pila.
	 */
	public int getSize() {
		return this.top;
	}
	
	/**
	 * Se obtiene el elemento segun el index
	 * @param index
	 * @return El elemento del index
	 */
	public E get(int index) {
		if (index > List.getSize()) {
			throw new IndexOutOfBoundsException("Index fuera de rango "+index);
		} else if (isEmpty()) {
			throw new EmptyStackException();
		}
		return this.List.getElement(index);
	}
}