/**
 * Class that contains static methods that represent commonly used operations used for many ciphers, especially for encryption and decryption.
 * 
 * @author sophia song
 * @version java 11.0.9, 5/19/22
 */
public class Operations
{
    public static final int ASCII_DIFF = (int)'A';
    public static final int MODULUS = 26;

    /**
     * Calculates the greatest common divisor of two integers recursively using the Euclidean Algorithm.
     * 
     * @param   a   first integer
     * @param   b   second integer
     */
    public static int gcd(int a, int b)
    {
        if (b > a) {
            int temp = b;
            b = a;
            a = temp;
        }
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    /**
     * Calculates the least common multiple of two integers.
     * 
     * @param   a   first integer
     * @param   b   second integer
     */
    public static int lcm(int a, int b)
    {
        return (a * b)/gcd(a, b);
    }

    /**
     * Converts the given letter to its corresponding number, mod 26.
     * 
     * @param   letter  the letter to be converted to a number
     * @return  the corresponding number, from 0-25 for A-Z
     */
    public static int letterToNumber(char letter)
    {
        return (int)Character.toUpperCase(letter) - ASCII_DIFF;
    }

    /**
     * Converts the given number to its corresponding letter.
     * 
     * @param   number  the number to be converted to a letter
     * @return  the corresponding letter, from A-Z for 0-25
     */
    public static char numberToLetter(int number)
    {
        return (char)(number + ASCII_DIFF);
    }

    /**
     * Edits the text to remove all spaces and punctuation and capitalize all letters.
     * 
     * @param text the original text
     * @return the edited text
     */
    public static String convertText(String text)
    {
        text = text.replaceAll("\\p{P}", "");
        text = text.replaceAll(" ", "");
        return text.toUpperCase();
    }

    /**
     * Calculates the modular inverse of any divisor.
     * 
     * @param   dividend    the dividend
     * @param   divisor   the divisor
     * @return  the dividend's modular inverse
     */
    public static int modInverse(int dividend, int divisor) throws ArithmeticException
    {
        if (Operations.gcd(dividend, divisor) != 1) {
            throw new ArithmeticException("Modular inverse of " + dividend + " (mod " + divisor + ") does not exist!");
        }
        else {
            int n = 1;
            while (Math.floorMod(n * dividend, divisor) != 1) {
                n++;
            }
            return n;
        }
    }

    /**
     * Calculates the modular inverse with a divisor of 26.
     * 
     * @param   dividend    the dividend
     * @return  the dividend's modular inverse mod 26
     */
    public static int modInverse(int dividend)
    {
       return modInverse(dividend, 26); 
    }
}