package Utilidades;

public class ArbolBinario<E> {
	// Variables globales
	nodoArbol<E> root;

	// Constructores
	public ArbolBinario() {
		this.root = null;
	}

	public ArbolBinario(nodoArbol<E> node) {
		this.root = node;
	}

	public ArbolBinario(nodoArbol<E> left, nodoArbol<E> right, E data) {
		this.root = new nodoArbol<E>(left, right, data);
	}

	// Metodos para el recorido
	public void preOrder() {
		this.preOrderRoute(this.root);
	}

	public void inOrder() {
		this.inOrderRoute(this.root);
	}

	public void postOrder() {
		this.postOrderRoute(this.root);
	}

	private void preOrderRoute(nodoArbol<E> node) {
		if (node != null) {
			System.out.println(node.getData());
			preOrderRoute(node.getLeft());
			preOrderRoute(node.getRight());
		}
	}

	private void inOrderRoute(nodoArbol<E> node) {
		if (node != null) {
			inOrderRoute(node.getLeft());
			System.out.println(node.getData());
			inOrderRoute(node.getRight());
		}
	}

	private void postOrderRoute(nodoArbol<E> node) {
		if (node != null) {
			postOrderRoute(node.getLeft());
			postOrderRoute(node.getRight());
			System.out.println(node.getData());
		}
	}
}
