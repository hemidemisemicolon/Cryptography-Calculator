/**
 * Test class to make sure everything works properly
 * 
 * @author sophia song
 * @version java 11.0.9, 6/10/22
 */
public class Test {
    public static void main(String[] args)
    {
        // System.out.println(Operations.convertText("This is a sentence: it has a lot of punctuation, so hopefully this works."));
        /*
        int[][] arr2D = {{1, 1, 1}, {1, 2, 3}, {1, 6, 6}};
        Matrix mat = new Matrix(arr2D);
        System.out.println(mat);
        System.out.println(mat.transpose());
        System.out.println(mat.adjoint());
        System.out.println(mat.determinant());
        System.out.println(mat.inverse());
        */
        /*
        String key = "GOLDFINCH";
        Hill hill = new Hill(key);
        System.out.println(hill.getKey());
        System.out.println(hill);
        */
        /*
        Matrix[] vectors;
        vectors = hill.getVectors("EDITTHEWIKI");
        for (Matrix vector : vectors) {
            System.out.println(vector);
        }
        */
        /*
        String text = " Edit the wiki ";
        System.out.println(hill.convertText(text));
        */
        // System.out.println(hill.inverse());
        // System.out.println(hill.decrypt("IOSTFKMWMBTMJRDXALKZJFCE"));
        /*
        int[][] a = {
            {4, 3, 8, 22},
            {19, 19, 7, 19},
            {4, 22, 8, 20}
        };
        
        Matrix aug = new Matrix(a);
        int[] solutions = Hill.solveSystem(aug);
        for (int sol : solutions) {
            System.out.println(sol);
        }
        */
        // Hill.findKey(2, "ABCD", "DCBA");
        Vigenere vig = new Vigenere("scioly");
        System.out.println(vig.encrypt("Science Olympiad is cool"));
        System.out.println(vig.decrypt("KEQSYAWQTMXNACLWDAGQT"));
        System.out.println(Vigenere.findKey("Science Olympiad is cool", "KEQSYAWQTMXNACLWDAGQT"));
    }
}
