/******************************************************************************
 *  Name: Sophia Song
 ******************************************************************************/

Final Project Name: Cryptography Calculator

/******************************************************************************
 *  Describe how decided to implement this project. Is it original? or a spin-off
 *  a similar project? If it is the latter, submit a link or documentation to it.
 *****************************************************************************/
This is an original project, though it is heavily inspired by the Science
Olympiad Codebusters event: https://scioly.org/wiki/index.php/Codebusters
I implemented this project using multiple menus for each of the ciphers. There
is a main menu whose options lead to the submenus for each cipher. Each submenu
has multiple option such as setting a key for the cipher, encrypting/decrypting
a message, and solving for a key using a known plaintext and ciphertext.
Recursion algorithms are used in some of the methods, notably the Laplace
expansion used in the determinant method of the Matrix class, and the Euclidean
Algorithm used in the gcd method of the Operations class.


/******************************************************************************
 *  Describe step by step how to execute your project successfully.
 * If multiple conditions result in different outputs, describe the steps 
 * to achieve the different outcomes.
 ******************************************************************************/
zsh (MacOS) instructions:
1. This project is a terminal program, which requires JRE to be installed. JRE
can be downloaded from https://www.oracle.com/java/technologies/downloads/#jre8
2. On Terminal, navigate to the Final Project directory, using the cd command.
Shortcut: first type "cd " (without the quotation marks, with the space) into
Terminal, but do not press Enter. Drag and drop the Final Project folder into
Terminal, which automatically pastes the path to the Final Project directory.
Then press Enter.
3. Run the command "java Main" on Terminal to start the program.
4. Once the program is started, you can navigate through different menus for
different ciphers through user input. When entering an option, enter the
NUMBER of the option ONLY. Once you pick an option, follow what the program
tells you to enter.


/******************************************************************************
 *  Describe the data types you used to implement your project.
 *****************************************************************************/
The main data types used in the project are char, int, and String. String is used
for plaintext and ciphertext messages, while char and int are used for converting
individual characters to their corresponding numbers and back, as well as
mathematically encrypting and decrypting messages.
The main data structure that this project uses are 2D arrays, used to store values
for matrices. Certain methods also use 1D arrays to break up messages into a list
of vectors to encrypt/decrypt.


/******************************************************************************
 *  Describe the methods used in your ADTs
 *****************************************************************************/
Affine ADT:
- Can solve for an encryption equation given a two plaintext letters and two
ciphertext letters
- Can encrypt/decrypt a message using an affine cipher
Hill ADT:
- Can calculate the determinant, adjoint, and inverse of a matrix (mod 26)
- Can multiply a matrix by a scalar or another matrix (mod 26)
- Can solve for a matrix used to encrypt a plaintext into a ciphertext
- Can encrypt/decrypt a message using a Hill cipher
Vigenere ADT:
- Can calculate the keyword used to encrypt a plaintext into a ciphertext
- Can encrypt/decrypt a message using a Vigenere cipher
Operations:
- Can calculate gcd and lcm
- Can convert between letters and numbers
- Can remove spaces and punctuation from a text, and capitalize it
- Can calculate the modular inverse of a number


/******************************************************************************
 *  Describe the data needed for your project.
 *  Submit data file(s) to run your project. What is the name of the data file(s)?
 *  Describe the purpose of the data.
 *  Describe the multiple testing done to demonstrate a successful implementation.
 *****************************************************************************/
No outside data files are necessary to run the project.


/******************************************************************************
 *  Known bugs/limitations.
 *****************************************************************************/
Most of the ciphers (namely Affine and Hill) included in this project use
modular arithmetic, which has its own set of limitations. Ciphers involve both
encryption and decryption, and the latter often requires the use of modular
inverses. However, for an integer x to have a modular inverse with respect to a
modulus m, x must be coprime to m. In instances where x is not coprime to m,
such as if the coefficient of an Affine equation or the determinant of a Hill
matrix is not coprime to 26, a decryption key will not exist. Thus, one
limitation of this project is the fact that the user may input a encryption key
for which a decryption key does not exist.

The findEquation method in the Affine ADT has one limitation in that none of its
plaintext parameters can be the character 'A'. Finding an encryption equation
involves solving a system of equations, and one of its steps involves dividing by
the plaintext parameters. 'A' corresponds to 0, which causes a division by 0 error.

The findKey method in the Hill ADT has the limitation in which that the matrix
constructed from the plaintext hint must have a determinant that has a modular
inverse (mod 26). Calculating the encryption matrix involves multiplying by the
inverse of the matrix constructed from the plaintext numbers, which means its
determinant must have a modular inverse. Due to this constraint, the possible
plaintext hints that could be used to find an encryption matrix becomes very
limited.


/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course classmates, and friends)
 *  and attribute them by name.
 *****************************************************************************/
Other than my numerous visits to StackOverflow and constantly referencing the
SciOly Codebusters wiki page, I did not receive any help.

/******************************************************************************
 *  Describe any serious problems you encountered.                    
 *****************************************************************************/
I did not encounted any particularly serious problems in the code. That being
said, I learned (the hard way) that when using the Scanner class (in a situation
similar to mine,) only one instance of the Scanner class should be instantiated,
and that instance should be used throughout all methods and files in the program,
before closing the scanner when the program terminates.


/******************************************************************************
 * List any other comments here. Feel free to provide any feedback   
 * on how helpful the class meeting was and on how much you learned 
 * from doing the assignment, and whether you enjoyed doing it.
 *****************************************************************************/
I really enjoyed working on this assignment! Codebusters is one of my favorite
SciOly events because I really like working with the math and logic behind
many of the ciphers. So it was really fun to implement just that in a handy
tool that can be used to instantly encrypt/decrypt messages with a variety of
ciphers.