/**
 * Represents an affine cipher equation of the form y = ax + b.
 * 
 * @author sophia song
 * @version java 11.0.9, 6/8/22
 */
public class Affine
{
    private int a;
    private int b;

    /**
     * Constructs an affine equation with a known a and b.
     */
    public Affine(int coefficient, int constant) throws ArithmeticException
    {
        if (Operations.gcd(coefficient, Operations.MODULUS) != 1) {
            throw new ArithmeticException(String.format("%d is not invertible mod %d!", coefficient, Operations.MODULUS));
        }
        a = coefficient;
        b = constant;
    }

    /**
     * Solves for a and b using a system of equations, given two plain letters with their corresponding cipher letters.
     * 
     * @param p1 the first plain letter
     * @param c1 the first cipher letter, corresponding to p1
     * @param p2 the second plain letter
     * @param c2 the second cipher letter, corresponding to p2
     */
    public static Affine findEquation(char p1, char c1, char p2, char c2)
    {
        int x1 = Operations.letterToNumber(p1);
        int y1 = Operations.letterToNumber(c1);
        int x2 = Operations.letterToNumber(p2);
        int y2 = Operations.letterToNumber(c2);

        // solving for a
        int deltaY = Math.floorMod(y1 - y2, 26);
        int deltaX = x1 - x2;
        int n = 1;
        while (Math.floorMod(n * deltaX, 26) != deltaY) {
            n++;
        }
        int coefficient = n;

        // solving for b
        int lstComMul = Operations.lcm(x1, x2);
        int factor1 = lstComMul/x1;
        int factor2 = lstComMul/x2;
        int deltaB = factor1 - factor2;
        deltaY = Math.floorMod(factor1 * y1 - factor2 * y2, 26);
        n = 1;
        while (Math.floorMod(n * deltaB, 26) != deltaY) {
            n++;
        }
        int constant = n;

        return new Affine(coefficient, constant);
    }

    /**
     * Encrypts a given plaintext using this equation.
     * 
     * @param plaintext the plaintext
     * @return the ciphertext
     */
    public String encrypt(String plaintext)
    {
        plaintext = Operations.convertText(plaintext);
        String ciphertext = "";
        for (int i = 0; i < plaintext.length(); i++) {
            ciphertext += Operations.numberToLetter(Math.floorMod(a * Operations.letterToNumber(plaintext.charAt(i)) + b, Operations.MODULUS));
        }
        return ciphertext;
    }

    /**
     * Decrypts a given ciphertext using this equation's inverse.
     * 
     * @param ciphertext the ciphertext
     * @return the plaintext
     */
    public String decrypt(String ciphertext)
    {
        ciphertext = Operations.convertText(ciphertext);
        String plaintext = "";
        for (int i = 0; i < ciphertext.length(); i++) {
            plaintext += Operations.numberToLetter(Math.floorMod(Operations.modInverse(a) * (Operations.letterToNumber(ciphertext.charAt(i)) - b), Operations.MODULUS));
        }
        return plaintext;
    }

    /**
     * Returns this affine equation as a string representation.
     * 
     * @return  the affine equation
     */
    public String toString()
    {
        return String.format("y = %dx + %d", a, b);
    }
}
