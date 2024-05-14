package Utilidades;

public class nodoArbol <E> {
	//Variables globales
	private nodoArbol <E> right;
	private nodoArbol <E> left;
	private E data ;
	
	//Constructores
	public nodoArbol() {
		this.right = null;
		this.left = null;
		this.data = null;
	}
	
	nodoArbol(E data){
		this.right = null;
		this.left = null;
		this.data = data;
	}
	
	public nodoArbol(nodoArbol<E> left, nodoArbol<E> right, E data) {
		this.right = right;
		this.left = left;
		this.data = data;
	}
	
	//Metodos para la obtencion de datos
	public nodoArbol<E> getRight() {
		return right;
	}
	
	public nodoArbol<E> getLeft() {
		return left;
	}
	
	public E getData() {
		return data;
	}
	
	//Metodos para la asignacion de datos
	public void setRight(nodoArbol<E> right) {
		this.right = right;
	}
	
	public void setLeft(nodoArbol<E> left) {
		this.left = left;
	}
	
	public void setData(E data) {
		this.data = data;
	}
	
	//Otros metodos
	public void printNode() {
		System.out.println("Node{"+"value=" + this.data +", leftChild=" + this.getLeft() +", rightChild=" + this.getRight() + '}');
	}
}
