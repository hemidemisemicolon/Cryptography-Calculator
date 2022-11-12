/**
 * Represents a matrix of integers using a 2D Array.
 * 
 * @author sophia song
 * @version java 11.0.9, 5/23/22
 */
public class Matrix
{
    private int[][] matrix;

    /**
     * Constructs an empty matrix with the given dimensions.
     */
    public Matrix(int row, int column)
    {
        matrix = new int[row][column];
    }

    /**
     * Constructs a matrix from a predefined 2D array.
     */
    public Matrix(int[][] array)
    {
        matrix = array;
    }

    /**
     * Gets the number of rows of this matrix.
     * 
     * @return  number of rows
     */
    public int rows()
    {
        return matrix.length;
    }

    /**
     * Gets the number of columns of this matrix.
     * 
     * @return  number of columns
     */
    public int columns()
    {
        return matrix[0].length;
    }

    /**
     * Gets the element of a given position of this matrix.
     * 
     * @param   row     the row index of the element
     * @param   column  the column index of the element
     * @return  the element at the given position
     * @exception   ArrayIndexOutOfBoundsException if the given row or column index exceeds the actual dimensions of this matrix
     */
    public int getElement(int row, int column) throws ArrayIndexOutOfBoundsException
    {
        if (row < this.rows() && column < this.columns()) {
            return matrix[row][column];
        }
        else
        {
            throw new ArrayIndexOutOfBoundsException("Index exceeds matrix dimensions");
        }
    }

    /**
     * Gets the array of all elements in this matrix.
     * 
     * @return the array of all elements
     */
    public int[][] getArray()
    {
        return matrix;
    }

    /**
     * Sets an element at a given position of this matrix, and returns the original element.
     * 
     * @param   row     the row index of the element
     * @param   column  the column index of the element
     * @param   element the new element to be assigned
     * @return  the original element at the given position
     * @exception   ArrayIndexOutOfBoundsException if the given row or column index exceeds the actual dimensions of this matrix
     */
    public int setElement(int row, int column, int element) throws ArrayIndexOutOfBoundsException
    {
        if (row < this.rows() && column < this.columns()) {
            int original = matrix[row][column];
            matrix[row][column] = element;
            return original;
        }
        else
        {
            throw new ArrayIndexOutOfBoundsException("Index exceeds matrix dimensions");
        }
    }

    /**
     * Gets the row vector of a matrix at a given row index.
     * 
     * @param   row     the row index of the element
     * @return  the row vector at the given index
     * @exception   ArrayIndexOutOfBoundsException if the given row index exceeds the actual dimensions of this matrix
     */
    public int[] getRow(int row) throws ArrayIndexOutOfBoundsException
    {
        if (row < this.rows()) {
            return matrix[row];
        }
        else
        {
            throw new ArrayIndexOutOfBoundsException("Index exceeds number of rows in the matrix");
        }
    }
    
    /**
     * Gets the column vector of a matrix at a given column index.
     * 
     * @param   column  the column index of the element
     * @return  the column vector at the given index
     * @exception   ArrayIndexOutOfBoundsException if the given column index exceeds the actual dimensions of this matrix
     */
    public int[] getColumn(int column) throws ArrayIndexOutOfBoundsException
    {
        if (column < this.columns()) {
            int[] columnVector = new int[this.rows()];
            for (int i = 0; i < this.rows(); i++) {
                columnVector[i] = matrix[i][column];
            }
            return columnVector;
        }
        else
        {
            throw new ArrayIndexOutOfBoundsException("Index exceeds number of rows in the matrix");
        }
    }

    /**
     * Recursively calculates the determinant of this matrix using a Laplace expansion, expanding along row 0.
     * This matrix must be a square matrix.
     *
     * @return  the determinant
     * @exception   ArithmeticException if the number of rows don't equal the number of columns
     */
    public int determinant() throws ArithmeticException
    {
        if (this.rows() == this.columns()) {
            int det = 0;
            if (this.rows() == 1) {
                return this.getElement(0, 0);
            }
            else {
                int sign = 1;
                for (int i = 0; i < this.columns(); i++) {
                    det += sign * this.getElement(0, i) * this.getMinor(0, i).determinant();
                    sign *= -1;
                }
                return det;
            }
        }
        else {
            throw new ArithmeticException("Matrix is not a square matrix; determinant does not exist");
        }
    }

    /**
     * Support method that obtains the minor matrix from omitting the row and column of the given indices.
     * 
     * @param   row     the row from the original matrix to be omitted
     * @param   column  the column from the original matrix to be omitted
     * @return  the minor matrix
     */
    private Matrix getMinor(int row, int column)
    {
        int[][] minor = new int[this.rows() - 1][this.columns() - 1];
        int rowOffset = 0;
        for (int i = 0; i < this.rows(); i++) {
            int columnOffset = 0;
            if (i == row) {
                rowOffset = 1;
                continue;
            }
            for (int j = 0; j < this.columns(); j++) {
                if (j == column) {
                    columnOffset = 1;
                    continue;
                }
                minor[i - rowOffset][j - columnOffset] = this.getElement(i, j);
            }
        }
        return new Matrix(minor);
    }

    /**
     * Calculates and returns the transpose of this matrix.
     * 
     * @return  the transpose matrix
     */
    public Matrix transpose()
    {
        int[][] transposition = new int[this.columns()][this.rows()];
        for (int i = 0; i < transposition.length; i++) {
            transposition[i] = this.getColumn(i);
        }
        return new Matrix(transposition);
    }

    /**
     * Calculates and returns the classical adjoint of this matrix.
     * This matrix must be a square matrix.
     * 
     * @return  the classical adjoint matrix
     * @exception   ArithmeticException if the number of rows don't equal the number of columns
     */
    public Matrix adjoint() throws ArithmeticException
    {
        if (this.rows() == this.columns()) {
            int[][] adj = new int[this.rows()][this.columns()];
            for (int i = 0; i < this.rows(); i++) {
                for (int j = 0; j < this.columns(); j++) {
                    adj[i][j] = (int)Math.pow(-1, i + j) * this.getMinor(i, j).determinant();
                }
            }
            return (new Matrix(adj)).transpose();
        }
        else {
            throw new ArithmeticException("Matrix is not a square matrix; adjoint does not exist");
        }
    }
    
    /**
     * Calculates and returns the inverse of this matrix.
     * This matrix must be a square matrix.
     * 
     * @return  the inverse matrix
     * @exception ArithmeticException if the number of rows don't equal the number of columns, or if the determinant is 0
     */
    public Matrix inverse() throws ArithmeticException
    {
        if (this.rows() != this.columns()) {
            throw new ArithmeticException("Matrix is not a square matrix; inverse does not exist");
        }
        else if (this.determinant() == 0) {
            throw new ArithmeticException("Determinant is zero; adjoint does not exist");
        }
        else {
            return this.adjoint().multiply(1 / this.determinant());
        }
    }

    /**
     * Multiplies this matrix by a scalar.
     * 
     * @param   scalar  the scalar that multiplies this matrix
     * @return  the product of this matrix and the scalar
     */
    public Matrix multiply(int scalar)
    {
        int[][] product = new int[this.rows()][this.columns()];
        for (int i = 0; i < this.rows(); i++) {
            for (int j = 0; j < this.columns(); j++) {
                product[i][j] = scalar * this.getElement(i, j);
            }
        }
        return new Matrix(product);
    }

    /**
     * Multiplies this matrix by another matrix.
     * 
     * @param   factor  the matrix that multiplies this matrix
     * @return  the product of the two matrices
     * @exception   ArithmeticException if the column number of this matrix does not match the row number of the factor matrix
     */
    public Matrix multiply(Matrix factor) throws ArithmeticException
    {
        if (this.columns() == factor.rows()) {
            int[][] product = new int[this.rows()][factor.columns()];
            for (int i = 0; i < product.length; i++) {
                for (int j = 0; j < product[i].length; j++) {
                    product[i][j] = dotProduct(this.getRow(i), factor.getColumn(j));
                }
            }
            return new Matrix(product);
        }
        else {
            throw new ArithmeticException("Column number of first matrix does not match row number of second matrix");
        }
    }
    
    /**
     * Support method that gets the dot product of two 1D arrays.
     * 
     * @param   vector1 the first vector
     * @param   vector2 the second vector
     * @return  the dot product
     * @exception   ArithmeticException if the vectors are not the same size.
     */
    private int dotProduct(int[] vector1, int[] vector2) throws ArithmeticException
    {
        if (vector1.length == vector2.length) {
            int product = 0;
            for (int i = 0; i < vector1.length; i++) {
                product += vector1[i] * vector2[i];
            }
            return product;
        }
        else {
            throw new ArithmeticException("Vectors are not the same size");
        }
    }

    /**
     * Returns this matrix as a string representation
     * 
     * @return string representation of matrix
     */
    public String toString()
    {
        String matrixStr = "";
        for (int i = 0; i < this.rows(); i++) {
            matrixStr += "|";
            for (int j = 0; j < this.columns(); j++) {
                matrixStr += String.format("%4d", this.getElement(i, j));
            }
            matrixStr += "|\n";
        }
        return matrixStr;
    }
}