/**
 * Represents a Vigenere cipher with a keyword.
 * 
 * @author sophia song
 * @version java 11.0.9, 6/10/22
 */
public class Vigenere
{
    String key;

    /**
     * Constructs a Vigenere cipher using a keyword.
     */
    public Vigenere(String keyword)
    {
        key = Operations.convertText(keyword);
    }

    /**
     * Encrypts the given plaintext using the stored key through a Vigenere cipher.
     * 
     * @param plaintext the plaintext
     * @retunn the ciphertext, obtained from a Vigenere cipher
     */
    public String encrypt(String plaintext)
    {
        plaintext = Operations.convertText(plaintext);
        String ciphertext = "";
        for (int i = 0; i < plaintext.length(); i++) {
            // plain letter + keyword letter = cipher letter (mod 26)
            ciphertext += Operations.numberToLetter(Math.floorMod(Operations.letterToNumber(plaintext.charAt(i)) + Operations.letterToNumber(key.charAt(i % key.length())), Operations.MODULUS));
        }
        return ciphertext;
    }

    /**
     * Decrypts the given ciphertext using the stored key through a Vigenere cipher.
     * 
     * @param ciphertext the ciphertext
     * @return the plaintext, obtained from a Vigenere cipher
     */
    public String decrypt(String ciphertext)
    {
        ciphertext = Operations.convertText(ciphertext);
        String plaintext = "";
        for (int i = 0; i < ciphertext.length(); i++) {
            // cipher letter - keyword letter = plain letter (mod 26)
            plaintext += Operations.numberToLetter(Math.floorMod(Operations.letterToNumber(ciphertext.charAt(i)) - Operations.letterToNumber(key.charAt(i % key.length())), Operations.MODULUS));
        }
        return plaintext;
    }

    /**
     * Finds the keyword given a plaintext and its corresponding ciphertext.
     * 
     * @param plaintext the plaintext
     * @param ciphertext the ciphertext
     * @return the keyword
     * @exception IllegalArgumentException if the lengths of the plaintext and ciphertext are not the same
     */
    public static Vigenere findKey(String plaintext, String ciphertext)
    {
        plaintext = Operations.convertText(plaintext);
        ciphertext = Operations.convertText(ciphertext);
        if (plaintext.length() != ciphertext.length()) {
            throw new IllegalArgumentException(String.format("Length of plaintext (%d) does not equal length of ciphertext (%d)!", plaintext.length(), ciphertext.length()));
        }
        String keyword = "";
        for (int i = 0; i < plaintext.length(); i++) {
            // cipher letter - plain letter = keyword letter (mod 26)
            keyword += Operations.numberToLetter(Math.floorMod(Operations.letterToNumber(ciphertext.charAt(i)) - Operations.letterToNumber(plaintext.charAt(i)), Operations.MODULUS));
        }
        return new Vigenere(keyword);
    }

    /**
     * Returns this Vigenere as a String representation (i.e. the keyword)
     * 
     * @return String representation (keyword)
     */
    public String toString()
    {
        return key;
    }
}