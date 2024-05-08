package Lenguajes_Automatas2.Gramaticas;

public class Semantico_pEAS {
    private String input;
    private int index;
    private char currentChar;

    public Semantico_pEAS(String input) {
        this.input = input;
        this.index = 0;
        this.currentChar = input.charAt(index);
    }

    private void consumeChar() {
        index++;
        if (index < input.length()) {
            currentChar = input.charAt(index);
        } else {
            currentChar = '\0';  // EOF
        }
    }

    private void match(char expectedChar) {
        if (currentChar == expectedChar) {
            consumeChar();
        } else {
            throw new RuntimeException("Unexpected character: " + currentChar);
        }
    }

    public void parse() {
        parseE();
        if (currentChar != '\0') {
            throw new RuntimeException("Unexpected character at end of input: " + currentChar);
        }
    }

    private void parseE() {
        parseT();
        while (currentChar == '+') {
            match('+');
            parseT();
        }
    }

    private void parseT() {
        parseF();
        while (currentChar == '*') {
            match('*');
            parseF();
        }
    }

    private void parseF() {
        if (currentChar == 'a' || currentChar == 'b') {
            consumeChar();
        } else if (currentChar == '(') {
            match('(');
            parseE();
            match(')');
        } else {
            throw new RuntimeException("Unexpected character in parseF: " + currentChar);
        }
    }

    public static void main(String[] args) {
        Semantico_pEAS parser = new Semantico_pEAS("a+b*(a+b)");
        parser.parse();
        System.out.println("The input is correctly parsed.");
    }
}