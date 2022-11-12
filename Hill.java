/**
 * Represents a Hill matrix.
 * 
 * @author sophia song
 * @version java 11.0.9, 6/1/22
 */
public class Hill extends Matrix
{
    
    public final char PLACEHOLDER = 'Z'; // used for encryption and decryption, when the length of the text is not a multiple of the size of the Hill matrix

    /**
     * Constructs an empty Hill matrix of the given size (i.e. a 2x2 matrix has size 2.)
     */
    public Hill(int size)
    {
        super(size, size);
    }

    /**
     * Constructs an empty Hill matrix from a predefined 2D array.
     */
    public Hill(int[][] array)
    {
        super(array);
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                this.setElement(i, j, Math.floorMod(this.getElement(i, j), Operations.MODULUS));
            }
        }
    }

    /**
     * Constructs a Hill matrix using a given key phrase. The length of the phrase MUST be a square number.
     */
    public Hill(String key)
    {
        this((int)Math.sqrt(key.length()));
        for (int i = 0; i < this.rows(); i++) {
            for (int j = 0; j < this.columns(); j++) {
                this.setElement(i, j, Operations.letterToNumber(key.charAt(i * this.columns() + j)));
            }
        }
    }

    /**
     * Support method that calculates the matrix congruent to the defined modulus.
     * 
     * @param   matrix  the original matrix
     * @return  the congruent matrix
     */
    private static Matrix modMatrix(Matrix matrix)
    {
        for (int i = 0; i < matrix.rows(); i++) {
            for (int j = 0; j < matrix.columns(); j++) {
                matrix.setElement(i, j, Math.floorMod(matrix.getElement(i, j), Operations.MODULUS));
            }
        }
        return matrix;
    }

    /**
     * Calculates and returns the determinant of this Hill matrix.
     * 
     * @return  the determinant congruent to the defined modulus
     */
    @Override
    public int determinant()
    {
        return Math.floorMod(super.determinant(), Operations.MODULUS);
    }

    /**
     * Calculates and returns the classical adjoint of this Hill matrix.
     * 
     * @return  the classical adjoint congruent to the defined modulus
     */
    @Override
    public Matrix adjoint()
    {
        Matrix adj = super.adjoint();
        return modMatrix(adj);
    }

    /**
     * Calculates and returns the inverse of this Hill matrix. This is also the decryption matrix.
     * 
     * @return  the inverse/decryption matrix
     */
    @Override
    public Hill inverse()
    {
        Matrix inv = this.adjoint().multiply(Operations.modInverse(this.determinant(), Operations.MODULUS));
        Matrix hillInv = new Hill(inv.getArray());
        return (Hill)modMatrix(hillInv);
    }

    /**
     * Multiplies this Hill matrix by another matrix. Recommended to be used with column vectors.
     * 
     * @param   factor  the matrix that multiplies this matrix
     * @return  the product of the two matrices
     */
    @Override
    public Matrix multiply(Matrix factor)
    {
        return modMatrix(super.multiply(factor));
    }

    /**
     * Encrypts the given plaintext using the stored key.
     * 
     * @param plaintext the original plaintext
     * @return the ciphertext
     */
    public String encrypt(String plaintext)
    {
        Matrix[] vectors = this.getVectors(plaintext);
        String ciphertext = "";
        for (Matrix vector : vectors) {
            Matrix cipherVector = this.multiply(vector);
            for (int i = 0; i < this.rows(); i++) {
                ciphertext += Operations.numberToLetter(cipherVector.getElement(i, 0));
            }
        }
        return ciphertext;
    }

    /**
     * Decrypts the given ciphertext using the inverse of the stored key.
     * 
     * @param ciphertext the original ciphertext
     * @return the plaintext
     */
    public String decrypt(String ciphertext)
    {
        Matrix[] vectors = this.getVectors(ciphertext);
        String plaintext = "";
        for (Matrix vector : vectors) {
            Matrix plainVector = this.inverse().multiply(vector);
            for (int i = 0; i < this.rows(); i++) {
                plaintext += Operations.numberToLetter(plainVector.getElement(i, 0));
            }
        }
        return plaintext;
    }
    
    /**
     * Converts a string of text into an array of column vectors of the same number of components as the size of this Hill matrix.
     * 
     * @param text the original text
     * @return the array of column vectors
     */
    private Matrix[] getVectors(String text)
    {
        text = Operations.convertText(text);
        Matrix[] vectors = new Matrix[(int)Math.ceil((double)text.length() / this.columns())];
        for (int i = 0; i < vectors.length; i++) {
            int[][] rowVector = new int[1][this.columns()];
            for (int j = 0; j < rowVector[0].length; j++) {
                if (text.equals("")) {
                    rowVector[0][j] = Operations.letterToNumber(PLACEHOLDER);
                }
                else {
                    rowVector[0][j] = Operations.letterToNumber(text.charAt(0));
                    text = text.substring(1);
                }
            }
            Matrix vector = new Matrix(rowVector);
            vectors[i] = vector.transpose(); // convert row vector to column vector
        }
        return vectors;
    }
    
    /**
     * Returns the key phrase that this Hill matrix uses.
     * 
     * @return the key phrase
     */
    public String getKey()
    {
        String key = "";
        for (int i = 0; i < this.rows(); i++) {
            for (int j = 0; j < this.columns(); j++) {
                key += Operations.numberToLetter(this.getElement(i, j));
            }
        }
        return key;
    }

    /**
     * Calculates the key matrix given plaintext, ciphertext, and the size of the matrix using the formula K = C * P^-1
     * K is the key matrix, C is the cipher matrix, and P is the plain matrix.
     * The length of each text must both equal the size of the matrix squared.
     * 
     * @param size the size of the nmatrix (i.e. matrix size length, not number of matrix entries)
     * @param plaintext the plaintext
     * @param ciphertext the corresponding ciphertext
     * @return the Hill matrix of the given size that maps the plaintext to the ciphertext
     * @exception ArithmeticException if plaintext length does not match ciphertext length, or if text length does not equal matrix size squared
     */
    public static Hill findKey(int size, String plaintext, String ciphertext) throws ArithmeticException
    {
        if (plaintext.length() != ciphertext.length()) {
            throw new ArithmeticException(String.format("Length of plaintext (%d) does not equal length of ciphertext (%d)!", plaintext.length(), ciphertext.length()));
        }
        else if (plaintext.length() != Math.pow(size, 2)) {
            throw new ArithmeticException(String.format("Length of text (%d) does not equal number of matrix entries (%d)!", plaintext.length(), Math.pow(size, 2)));
        }
        Matrix plain = new Hill(size);
        Matrix cipher = new Hill(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                plain.setElement(j, i, Operations.letterToNumber(plaintext.charAt(i * size + j)));
                cipher.setElement(j, i, Operations.letterToNumber(ciphertext.charAt(i * size + j)));
            }
        }
        Matrix key = new Hill(cipher.multiply(plain.inverse()).getArray());
        return (Hill)modMatrix(key);
    }
}