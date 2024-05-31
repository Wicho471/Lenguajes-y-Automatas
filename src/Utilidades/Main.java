package Utilidades;

public class Main {
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			int res = Integer.valueOf("14"+i+"5"+i);
			System.out.print(res);
			if (res % 19 == 0) {
				System.out.println(" es divisible");
			} else {
				System.out.println(" no es divisible");
			}
		}
	}
}
