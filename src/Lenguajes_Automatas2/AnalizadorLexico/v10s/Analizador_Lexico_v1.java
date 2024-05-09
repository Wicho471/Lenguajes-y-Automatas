package Lenguajes_Automatas2.AnalizadorLexico.v10s;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Lenguajes_Automatas2.FAs.DFA_L1;

/* 1. Cree un programa Java para el primer léxico y tome su DFA M(L) o superior de su preferencia, 
 * para que la cadena se lea del archivo. Llame a este programa Lexical_v1. Por ejemplo, 
 * si el contenido del archivo myString tiene: 1011101, el léxico diré: la cadena 1011101 
 * no pertenece al idioma. Puede utilizar el método que prefiera para leer los caracteres 
 * del archivo.
 */

public class Analizador_Lexico_v1  {
	public static void main(String[] args) {
		
		String text = "";
		File doc = new File("src/Lenguajes_Automatas2/txt/myStringV1.txt");
		Scanner read = null;
		try {
			read = new Scanner(doc);
		} catch (FileNotFoundException e) {
			System.err.println("Archivo no encontrado en la direccion -> " + doc.getPath() + " \nError -> " + e);
		}
		while (read.hasNextLine()) {
			text += read.nextLine();
		}
		read.close();
		
		System.out.println("Cadena leida: "+text);
		
		DFA_L1 dfa = new DFA_L1();
		if (dfa.validateString(text)) {
			System.out.println("La cadena "+text+" es valida");
		} else {
			System.out.println("La cadena "+text+" no es valida");
		}
	}
}
