import java.util.Scanner;
/**
 * Driver class for the cryptography calculator.
 * 
 * @author sophia song
 * @version java 11.0.9, 6/15/22
 */
public class Main {
    public static void main(String[] args)
    {
        final String EXIT = "Exit";
        final String[] MAIN_MENU_OPTIONS = {"Affine cipher", "Hill cipher", "Vigenere cipher", EXIT};
        final Menu MAIN_MENU = new Menu("CRYPTOGRAPHY CALCULATOR", MAIN_MENU_OPTIONS);

        Affine affine = new Affine(1, 1);
        Hill hill = new Hill(2);
        Vigenere vigenere = new Vigenere("A");
        int userOption = 0;
        Scanner scan = new Scanner(System.in);
        while (!MAIN_MENU.getOption(userOption).equals(EXIT)) {
            MAIN_MENU.printMenu();
            System.out.print("Your choice: ");
            userOption = Integer.parseInt(scan.nextLine());
            System.out.print("\f");
            
            switch (userOption) {
                case 1: // Affine
                    affine = affineOperations(affine, scan);
                    break;
                    
                case 2: // Hill
                    hill = hillOperations(hill, scan);
                    break;
                
                case 3: // Vigenere
                    vigenere = vigenereOperations(vigenere, scan);
                    break;
                    
                case 4: // Exit
                    System.out.println("\nSystem has been closed.");
                    break;
            }
        }
        scan.close();
    }

    /**
     * Support method that handles the Affine cipher operations, and updates the stored affine equation.
     * 
     * @param aff the original affine equation
     * @param scan scanner
     * @return the updated affine equation
     */
    private static Affine affineOperations(Affine aff, Scanner scan)
    {
        int userOption = 0;
        while (userOption != 5) {
            System.out.println("AFFINE CIPHER CALCULATOR");
            System.out.println("Current affine equation:");
            System.out.println(aff);
            System.out.println("Options:");
            System.out.println("1. Set an equation");
            System.out.println("2. Find an equation");
            System.out.println("3. Encrypt a message");
            System.out.println("4. Decrypt a message");
            System.out.println("5. Back to main menu");
            do {
                System.out.print("Your option: ");
                userOption = Integer.parseInt(scan.nextLine());
                if (userOption < 1 || userOption > 5) {
                    System.out.println("Input is invalid!\n");
                }
            }
            while (userOption < 1 || userOption > 5);

            switch (userOption) {
                case 1: // Set an equation
                    int coefficient = 1;
                    boolean validCoefficient = false;
                    do {
                        try {
                            System.out.print("Enter the coefficient for the equation: ");
                            coefficient = Integer.parseInt(scan.nextLine());
                            Operations.modInverse(coefficient);
                            validCoefficient = true;
                        }
                        catch (Exception e) {
                            System.out.println(String.format("Coefficient must be an integer with a modular inverse (mod %d).", Operations.MODULUS));
                        }
                    }
                    while (!validCoefficient);

                    int constant = 1;
                    boolean validConstant = false;
                    do {
                        try {
                            System.out.print("Enter the constant for the equation: ");
                            constant = Integer.parseInt(scan.nextLine());
                            validConstant = true;
                        }
                        catch (Exception e) {
                            System.out.println("Constant must be an integer.");
                        }
                    }
                    while (!validConstant);

                    aff = new Affine(coefficient, constant);
                    break;
                    
                case 2: // Find an equation
                    char p1 = 'A';
                    boolean valid = false;
                    do {
                        try {
                            System.out.print("Enter the first plaintext character: ");
                            p1 = scan.nextLine().charAt(0);
                            valid = true;
                        }
                        catch (Exception e) {
                            System.out.println("Input is not valid!");
                        }
                    }
                    while (!valid);

                    char c1 = 'A';
                    valid = false;
                    do {
                        try {
                            System.out.print("Enter the first ciphertext character: ");
                            c1 = scan.nextLine().charAt(0);
                            valid = true;
                        }
                        catch (Exception e) {
                            System.out.println("Input is not valid!");
                        }
                    }
                    while (!valid);

                    char p2 = 'A';
                    valid = false;
                    do {
                        try {
                            System.out.print("Enter the second plaintext character: ");
                            p2 = scan.nextLine().charAt(0);
                            valid = true;
                        }
                        catch (Exception e) {
                            System.out.println("Input is not valid!");
                        }
                    }
                    while (!valid);

                    char c2 = 'A';
                    valid = false;
                    do {
                        try {
                            System.out.print("Enter the second ciphertext character: ");
                            c2 = scan.nextLine().charAt(0);
                            valid = true;
                        }
                        catch (Exception e) {
                            System.out.println("Input is not valid!");
                        }
                    }
                    while (!valid);

                    Affine keyEquation = Affine.findEquation(p1, c1, p2, c2);
                    System.out.println("Equation: " + keyEquation);
                    System.out.println("Store this equation? (yes or no)");
                        if (scan.nextLine().toLowerCase().equals("yes")) {
                            aff = keyEquation;
                        }

                    break;
                    
                case 3: // encrypt
                    System.out.print("Enter a message to encrypt: ");
                    String plaintext = scan.nextLine();
                    System.out.println("Encrypted message: " + aff.encrypt(plaintext));
                    break;
                
                case 4: // decrypt
                    System.out.print("Enter a message to decrypt: ");
                    String ciphertext = scan.nextLine();
                    System.out.println("Encrypted message: " + aff.decrypt(ciphertext));
                    break;
                
                case 5: // exit
                    break;
            }
            if (userOption != 5) {
                System.out.print("Enter anything to continue: ");
                scan.nextLine();
            }
            System.out.print("\f");
        }
        return aff;
    }

    /**
     * Support method that handles Hill cipher operations, and updates the stored Hill matrix.
     * 
     * @param hl the original Hill matrix
     * @param scan scanner
     * @return the updated Hill matrix
     */
    private static Hill hillOperations(Hill hl, Scanner scan)
    {
        int userOption = 0;
        while (userOption != 6) {
            System.out.println("HILL CIPHER CALCULATOR");
            System.out.println("Current hill matrix:");
            System.out.println(hl);
            System.out.println("Options:");
            System.out.println("1. Set an encryption matrix using integers");
            System.out.println("2. Set an encryption matrix using a word");
            System.out.println("3. Encrypt a message using the stored encryption matrix");
            System.out.println("4. Decrypt a message using the inverse of this stored encryption matrix");
            System.out.println("5. Input a plaintext and a corresponding ciphertext to find an encryption matrix");
            System.out.println("6. Back to main menu");
            do {
                System.out.print("Your option: ");
                userOption = Integer.parseInt(scan.nextLine());
                if (userOption < 1 || userOption > 6) {
                    System.out.println("Input is invalid!\n");
                }
            }
            while (userOption < 1 || userOption > 6);

            switch (userOption) {
                case 1: // integers
                    System.out.println("Size of hill matrix: ");
                    int size = Integer.parseInt(scan.nextLine());
                    int[][] hillArr = new int[size][size];
                    for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) {
                            System.out.print(String.format("Enter integer #%d: ", i * size + j + 1));
                            hillArr[i][j] = Integer.parseInt(scan.nextLine());
                        }
                    }
                    hl = new Hill(hillArr);
                    break;
                    
                case 2: // letter word
                    String word;
                    do {
                        System.out.print("Enter a word with a square number of letters: ");
                        word = scan.nextLine();
                        if (Math.pow((int)Math.sqrt(word.length()), 2) != word.length()) {
                            System.out.println("Input is invalid!\n");
                        }
                    }
                    while (Math.pow((int)Math.sqrt(word.length()), 2) != word.length());
                    hl = new Hill(word);
                    break;
                    
                case 3: // encrypt
                    System.out.print("Enter a message to encrypt: ");
                    String plaintext = scan.nextLine();
                    System.out.println("Encrypted message: " + hl.encrypt(plaintext));
                    break;
                
                case 4: // decrypt
                    try {
                        Operations.modInverse(hl.determinant());
                        System.out.print("Enter a message to decrypt: ");
                        String ciphertext = scan.nextLine();
                        System.out.println("Encrypted message: " + hl.decrypt(ciphertext));
                    }
                    catch (Exception e) {
                        System.out.println("The stored matrix does not have a decryption matrix!");
                    }
                    break;
                
                case 5: // find key
                    try {
                        String plain;
                        do {
                            System.out.print("Enter a plaintext with a square number of letters: ");
                            plain = scan.nextLine();
                            if (Math.pow((int)Math.sqrt(plain.length()), 2) != plain.length()) {
                                System.out.println("Input is invalid!\n");
                            }
                        }
                        while (Math.pow((int)Math.sqrt(plain.length()), 2) != plain.length());
                        String cipher;
                        do {
                            System.out.print("Enter a CORRESPONDING ciphertext with the same number of letters: ");
                            cipher = scan.nextLine();
                            if (Math.pow((int)Math.sqrt(cipher.length()), 2) != cipher.length()) {
                                System.out.println("Input is invalid!\n");
                            }
                        }
                        while (cipher.length() != plain.length());
                        Hill keyHill = Hill.findKey((int)Math.sqrt(plain.length()), plain, cipher);
                        System.out.println("Key matrix:");
                        System.out.println(keyHill);
                        System.out.println("Keyword: " + keyHill.getKey());
                        System.out.println("Store this matrix? (yes or no)");
                        if (scan.nextLine().toLowerCase().equals("yes")) {
                            hl = keyHill;
                        }
                    }
                    catch (Exception e) {
                        System.out.println("An encryption matrix cannot be found that maps the plaintext to the ciphertext.");
                    }
                    break;
                
                case 6: // back to main menu
                    break;
            }
            if (userOption != 6) {
                System.out.print("Enter anything to continue: ");
                scan.nextLine();
            }
            System.out.print("\f");
        }
        return hl;
    }

    /**
     * Support method that handles the Vigenere cipher operations, and updates the stored Vignere keyword.
     * 
     * @param vig the original Vigenere keyword
     * @param scan scanner
     * @return the updated Vigenere keyword
     */
    private static Vigenere vigenereOperations(Vigenere vig, Scanner scan)
    {
        int userOption = 0;
        while (userOption != 5) {
            System.out.println("VIGENERE CIPHER CALCULATOR");
            System.out.println("Current vigenere keyword:");
            System.out.println(vig);
            System.out.println("Options:");
            System.out.println("1. Set a keyword");
            System.out.println("2. Find a keyword");
            System.out.println("3. Encrypt a message");
            System.out.println("4. Decrypt a message");
            System.out.println("5. Back to main menu");
            do {
                System.out.print("Your option: ");
                userOption = Integer.parseInt(scan.nextLine());
                if (userOption < 1 || userOption > 5) {
                    System.out.println("Input is invalid!\n");
                }
            }
            while (userOption < 1 || userOption > 5);

            switch (userOption) {
                case 1: // Set a keyword
                    System.out.print("Enter a new vigenere keyword: ");
                    vig = new Vigenere(scan.nextLine());
                    break;
                    
                case 2: // Find a keyword
                    System.out.print("Enter the plaintext: ");
                    String plain = scan.nextLine();
                    String cipher;
                    do {
                        System.out.print("Enter the ciphertext: ");
                        cipher = scan.nextLine();
                        if (plain.length() != cipher.length()) {
                            System.out.println("Ciphertext must be same length as plaintext.");
                        }
                    }
                    while (plain.length() != cipher.length());
                    
                    Vigenere keyword = Vigenere.findKey(plain, cipher);
                    System.out.println("Keyword: " + keyword);
                    System.out.println("Store this keyword? (yes or no)");
                        if (scan.nextLine().toLowerCase().equals("yes")) {
                            vig = keyword;
                        }
                    break;
                    
                case 3: // encrypt
                    System.out.print("Enter a message to encrypt: ");
                    String plaintext = scan.nextLine();
                    System.out.println("Encrypted message: " + vig.encrypt(plaintext));
                    break;
                
                case 4: // decrypt
                    System.out.print("Enter a message to decrypt: ");
                    String ciphertext = scan.nextLine();
                    System.out.println("Encrypted message: " + vig.decrypt(ciphertext));
                    break;
                
                case 5: // exit
                    break;
            }
            if (userOption != 5) {
                System.out.print("Enter anything to continue: ");
                scan.nextLine();
            }
            System.out.print("\f");
        }
        return vig;
    }
}