import java.io.File;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) {
        try {
            // Test the no-argument constructor
            Polynomial p = new Polynomial();
            System.out.println("p(3) = " + p.evaluate(3)); // Expected: 0.0

            // Test constructor with coefficients and exponents arrays
            double[] c1 = { 6, -2, 5 };
            int[] e1 = { 0, 1, 3 };
            Polynomial p1 = new Polynomial(c1, e1);
            System.out.println("p1(2) = " + p1.evaluate(2)); // Example evaluation

            // Test constructor with another set of coefficients and exponents
            double[] c2 = { -2, -9 };
            int[] e2 = { 1, 4 };
            Polynomial p2 = new Polynomial(c2, e2);

            // Test addition of two polynomials
            Polynomial sum = p1.add(p2);
            System.out.println("sum(0.1) = " + sum.evaluate(0.1)); // Expected: sum evaluation

            // Test root check
            if (sum.hasRoot(1)) {
                System.out.println("1 is a root of sum");
            } else {
                System.out.println("1 is not a root of sum");
            }

            // Test multiplication of two polynomials
            Polynomial product = p1.multiply(p2);
            System.out.println("product(1) = " + product.evaluate(1)); // Example evaluation

            // Test constructor from file
            File file = new File("polynomial.txt");
            Polynomial pFromFile = new Polynomial(file);
            System.out.println("pFromFile(1) = " + pFromFile.evaluate(1)); // Example evaluation

            // Test saving a polynomial to a file
            sum.saveToFile("sum_output.txt");
            System.out.println("Polynomial sum saved to sum_output.txt");

        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
