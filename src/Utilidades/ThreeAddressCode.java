package Utilidades;

public class ThreeAddressCode {
	private Lista<String> labels;
	private String result;
	private String arg1;
	private String op;
	private String arg2;
	
	public ThreeAddressCode() {
		this.labels = null;
		this.result = null;
		this.arg1 = null;
		this.op = null;
		this.arg2 = null;
	}
	
	public ThreeAddressCode(String label, String result, String arg1, String op, String arg2) {
		this.labels = new Lista<String>(label);
		this.result = result;
		this.arg1 = arg1;
		this.op = op;
		this.arg2 = arg2;
	}
	
	//Metodos para asginar datos
	public void addLabel(String label) {
		this.labels.addToEnd(label);
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public void setArg1(String arg1) {
		this.arg1 = arg1;
	}
	
	public void setOp(String op) {
		this.op = op;
	}
	
	public void setArg2(String arg2) {
		this.arg2 = arg2;
	}
	
	//Metodos para la obtencion de datos
	public String labelsToString() {
		String res = "";
		for (int i = 0; i < labels.getSize(); i++) {
			res += labels.getElement(i) +" ";
		}
		return res + ":";
	}
	
	public String getResult() {
		return result;
	}
	
	public String getArg1() {
		return arg1;
	}
	
	public String getOp() {
		return op;
	}
	
	public String getArg2() {
		return arg2;
	}
	
	//Otros metodos
	public String printTAC() {
		String res = "";
		if (labels.isEmpty()) {
			res+=labelsToString();
		}
		if (result != null) {
			res+= " "+result;
		}
		if (arg1 != null) {
			res+= " "+arg1;
		}
		if (op != null) {
			res+= " "+op;
		}
		if (arg2 != null) {
			res+= " "+arg2;
		}
		return res;
	}
}
