package Utilidades;

public class Arbol<E> extends Constantes{
	// Variables globales
	private nodoArbol<E> root;

	// Constructores
	public Arbol() {
		this.root = null;
	}

	public Arbol(nodoArbol<E> root) {
		this.root = root;
	}

	// Metodos la busqueda de nodos por informacion
	public nodoArbol<E> findNode(E data) {
		return findNodeRoute(this.root, data);
	}

	public nodoArbol<E> findNode(nodoArbol<E> node, E data) {
		return findNodeRoute(node, data);
	}

	private nodoArbol<E> findNodeRoute(nodoArbol<E> node, E data) {
		if (node == null) {
			return null;
		}
		if (node.getData().equals(data)) {

			return node;
		}
		Lista<nodoArbol<E>> children = node.getAllNodes();
		for (int i = 0; i < children.getSize(); i++) {
			nodoArbol<E> result = findNodeRoute(children.getElement(i), data);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	public void findNode(E data, NodoAccion<E> action) {
		findNodeRoute(this.root, data, action);
	}

	public void findNode(nodoArbol<E> node, E data, NodoAccion<E> action) {
		findNodeRoute(node, data, action);
	}

	private void findNodeRoute(nodoArbol<E> node, E data, NodoAccion<E> action) {
		if (node.getData().equals(data)) {
			action.apply(node);
		}
		Lista<nodoArbol<E>> children = node.getAllNodes();
		for (int i = 0; i < children.getSize(); i++) {
			findNodeRoute(children.getElement(i), data, action);
		}
	}

	// Metodos para el recorrido del arbol
	public void preOrder(NodoAccion<E> action) {
		preOrderRoute(this.root, action);
	}

	public void preOrder(nodoArbol<E> node, NodoAccion<E> action) {
		preOrderRoute(node, action);
	}

	private void preOrderRoute(nodoArbol<E> node, NodoAccion<E> action) {
		if (node != null) {

			System.out.println(node.getData());
			Lista<nodoArbol<E>> children = node.getAllNodes();
			for (int i = 0; i < children.getSize(); i++) {
				preOrderRoute(children.getElement(i), action);
			}
		}
	}

	public void postOrder(NodoAccion<E> action) {
		postOrderRoute(this.root, action);
	}

	public void postOrder(nodoArbol<E> node, NodoAccion<E> action) {
		postOrderRoute(node, action);
	}

	private void postOrderRoute(nodoArbol<E> node, NodoAccion<E> action) {
		if (node != null) {
			Lista<nodoArbol<E>> children = node.getAllNodes();
			for (int i = 0; i < children.getSize(); i++) {
				postOrderRoute(children.getElement(i), action);
			}
			System.out.println(node.getData());
		}
	}

	// Método para imprimir el árbol
	public void printTree() {
		printTreeRoute(this.root, "", true);
	}

	private void printTreeRoute(nodoArbol<E> node, String prefix, boolean isTail) {
		if (node != null) {
			System.out.println(prefix + (isTail ? "└── " : "├── ") + node.getData());
			Lista<nodoArbol<E>> children = node.getAllNodes();
			for (int i = 0; i < children.getSize() - 1; i++) {
				printTreeRoute(children.getElement(i), prefix + (isTail ? "    " : "│   "), false);
			}
			if (children.getSize() > 0) {
				printTreeRoute(children.getElement(children.getSize() - 1), prefix + (isTail ? "    " : "│   "), true);
			}
		}
	}

	/**
	 * Metodo para generar el codigo de 3 direcciones 
	 * @return Una lista de con codigos de 3 direcciones
	 */
	public Lista<String> generateThreeAddressCode() {
	    Lista<String> codeList = new Lista<>();
	    int[] tempCont = { 0 };
	    int[] labelCont = { 0 };
	    if (!isEmpty()) {
	    	generateThreeAddressCodeRoute(this.root, codeList, tempCont, labelCont);
	    }
	    return codeList;
	}

	
	/**
	 * Genera el código de tres direcciones para el subárbol a partir del nodo dado.
	 * 
	 * Este método recorre recursivamente el árbol sintáctico abstracto (AST) 
	 * y genera las instrucciones de código intermedio en forma de tres direcciones.
	 * Cada nodo del AST representa una construcción del lenguaje, como una operación 
	 * aritmética, una asignación, un condicional, un bucle, etc. El método genera 
	 * las instrucciones correspondientes para cada tipo de nodo.
	 * 
	 * @param nodo El nodo actual del árbol sintáctico abstracto.
	 * @param codeList La lista donde se almacenan las instrucciones de código de tres direcciones.
	 * @param tempCounter Contador para las variables temporales utilizadas en el código de tres direcciones.
	 * @param labelCounter Contador para las etiquetas utilizadas en el código de tres direcciones.
	 * @return El nombre de la variable que almacena el resultado de la operación representada por el nodo actual.
	 */
	private String generateThreeAddressCodeRoute(nodoArbol<E> nodo, Lista<String> codeList, int[] tempCounter, int[] labelCounter) {
	    if (nodo.isLeaf()) {
	        return nodo.getData().toString();
	    }
	    Lista<nodoArbol<E>> children = nodo.getAllNodes();
	    String leftOperand, rightOperand, condition, trueLabel, falseLabel, endLabel, code;
	    switch (nodo.getData().toString()) {
	        case ASSIGN:
	            leftOperand = generateThreeAddressCodeRoute(children.getElement(0), codeList, tempCounter, labelCounter);
	            rightOperand = generateThreeAddressCodeRoute(children.getElement(1), codeList, tempCounter, labelCounter);
	            code = leftOperand + " = " + rightOperand;
	            codeList.addToEnd(code);
	            return leftOperand;
	        case PLUS:
	        case MINUS:
	        case TIMES:
	        case DIVIDE:
	        case MODULO:
	            leftOperand = generateThreeAddressCodeRoute(children.getElement(0), codeList, tempCounter, labelCounter);
	            rightOperand = generateThreeAddressCodeRoute(children.getElement(1), codeList, tempCounter, labelCounter);
	            String tempVar = "t" + tempCounter[0]++;
	            code = tempVar + " = " + leftOperand + " " + nodo.getData().toString() + " " + rightOperand;
	            codeList.addToEnd(code);
	            return tempVar;
	        case EQUALS:
	        case NOT_EQUALS:
	        case LESS:
	        case LESS_EQUAL:
	        case GREATER:
	        case GREATER_EQUAL:
	            leftOperand = generateThreeAddressCodeRoute(children.getElement(0), codeList, tempCounter, labelCounter);
	            rightOperand = generateThreeAddressCodeRoute(children.getElement(1), codeList, tempCounter, labelCounter);
	            tempVar = "t" + tempCounter[0]++;
	            code = tempVar + " = " + leftOperand + " " + nodo.getData().toString() + " " + rightOperand;
	            codeList.addToEnd(code);
	            return tempVar;
	        case AND:
	        case OR:
	            leftOperand = generateThreeAddressCodeRoute(children.getElement(0), codeList, tempCounter, labelCounter);
	            rightOperand = generateThreeAddressCodeRoute(children.getElement(1), codeList, tempCounter, labelCounter);
	            tempVar = "t" + tempCounter[0]++;
	            code = tempVar + " = " + leftOperand + " " + nodo.getData().toString() + " " + rightOperand;
	            codeList.addToEnd(code);
	            return tempVar;
	        case TERNARY_OPERATOR:
	            condition = generateThreeAddressCodeRoute(children.getElement(0), codeList, tempCounter, labelCounter);
	            trueLabel = "L" + labelCounter[0]++;
	            falseLabel = "L" + labelCounter[0]++;
	            endLabel = "L" + labelCounter[0]++;
	            codeList.addToEnd("if " + condition + " goto " + trueLabel);
	            codeList.addToEnd("goto " + falseLabel);
	            codeList.addToEnd(trueLabel + ":");
	            leftOperand = generateThreeAddressCodeRoute(children.getElement(1), codeList, tempCounter, labelCounter);
	            codeList.addToEnd("goto " + endLabel);
	            codeList.addToEnd(falseLabel + ":");
	            rightOperand = generateThreeAddressCodeRoute(children.getElement(2), codeList, tempCounter, labelCounter);
	            codeList.addToEnd(endLabel + ":");
	            tempVar = "t" + tempCounter[0]++;
	            code = tempVar + " = " + leftOperand + " ? " + rightOperand;
	            codeList.addToEnd(code);
	            return tempVar;
	        case IF:
	            condition = generateThreeAddressCodeRoute(children.getElement(0), codeList, tempCounter, labelCounter);
	            trueLabel = "L" + labelCounter[0]++;
	            falseLabel = "L" + labelCounter[0]++;
	            endLabel = "L" + labelCounter[0]++; // Final label for the entire if-else construct
	            codeList.addToEnd("if " + condition + " goto " + trueLabel);
	            codeList.addToEnd("goto " + falseLabel);
	            codeList.addToEnd(trueLabel + ":");
	            generateThreeAddressCodeRoute(children.getElement(1), codeList, tempCounter, labelCounter);
	            codeList.addToEnd("goto " + endLabel); // Ensure to jump to endLabel after the if block
	            codeList.addToEnd(falseLabel + ":");
	            if (children.getSize() == 3) { // Else part
	                generateThreeAddressCodeRoute(children.getElement(2), codeList, tempCounter, labelCounter);
	            }
	            codeList.addToEnd(endLabel + ":"); // Place the endLabel after processing else block if it exists
	            return "";
	        case ELSE:
	            for (int i = 0; i < nodo.getAllNodes().getSize(); i++) {
	            	generateThreeAddressCodeRoute(children.getElement(i), codeList, tempCounter, labelCounter);
				}
	            return "";
	        case FOR:
	            String init = generateThreeAddressCodeRoute(children.getElement(0), codeList, tempCounter, labelCounter);
	            condition = generateThreeAddressCodeRoute(children.getElement(1), codeList, tempCounter, labelCounter);
	            String step = generateThreeAddressCodeRoute(children.getElement(2), codeList, tempCounter, labelCounter);
	            String startLabel = "L" + labelCounter[0]++;
	            trueLabel = "L" + labelCounter[0]++;
	            endLabel = "L" + labelCounter[0]++;
	            codeList.addToEnd(startLabel + ":");
	            codeList.addToEnd("if " + condition + " goto " + trueLabel);
	            codeList.addToEnd("goto " + endLabel);
	            codeList.addToEnd(trueLabel + ":");
	            generateThreeAddressCodeRoute(children.getElement(3), codeList, tempCounter, labelCounter);
	            codeList.addToEnd(step);
	            codeList.addToEnd("goto " + startLabel);
	            codeList.addToEnd(endLabel + ":");
	            return "";
	        case WHILE:
	            startLabel = "L" + labelCounter[0]++;
	            trueLabel = "L" + labelCounter[0]++;
	            endLabel = "L" + labelCounter[0]++;
	            codeList.addToEnd(startLabel + ":");
	            condition = generateThreeAddressCodeRoute(children.getElement(0), codeList, tempCounter, labelCounter);
	            codeList.addToEnd("if " + condition + " goto " + trueLabel);
	            codeList.addToEnd("goto " + endLabel);
	            codeList.addToEnd(trueLabel + ":");
	            generateThreeAddressCodeRoute(children.getElement(1), codeList, tempCounter, labelCounter);
	            codeList.addToEnd("goto " + startLabel);
	            codeList.addToEnd(endLabel + ":");
	            return "";
	        case DO + "-" + WHILE:
	            trueLabel = "L" + labelCounter[0]++;
	            endLabel = "L" + labelCounter[0]++;
	            codeList.addToEnd(trueLabel + ":");
	            generateThreeAddressCodeRoute(children.getElement(0), codeList, tempCounter, labelCounter);
	            condition = generateThreeAddressCodeRoute(children.getElement(1), codeList, tempCounter, labelCounter);
	            codeList.addToEnd("if " + condition + " goto " + trueLabel);
	            codeList.addToEnd(endLabel + ":");
	            return "";
	        case BLOCK:
	        case BEGIN:
	        	for (int i = 0; i < nodo.getAllNodes().getSize(); i++) {
	            	generateThreeAddressCodeRoute(children.getElement(i), codeList, tempCounter, labelCounter);
				}
	        default:
	            return nodo.getData().toString();
	    }
	}

	// Otros metodos
	public boolean isEmpty() {
		return this.root == null;
	}

	public int getHeight() {
		return (this.isEmpty()) ? 0 : this.root.height();
	}

	public int getSize() {
		return (this.isEmpty()) ? 0 : this.root.size();
	}

	public void cutDown() {
		this.root = new nodoArbol<>();
	}

	public nodoArbol<E> getRoot() {
		return this.root;
	}

	public void setRoot(nodoArbol<E> root) {
		this.root = root;
	}

	// Interfaz funcional para aplicar acciones a los nodos durante los recorridos
	@FunctionalInterface
	interface NodoAccion<E> {
		void apply(nodoArbol<E> node);
	}
}
