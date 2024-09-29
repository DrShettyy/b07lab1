import java.io.*;
import java.util.*;

public class Polynomial {
    private double[] coefficients; // Non-zero coefficients
    private int[] exponents; // Corresponding exponents

    // No-argument constructor: initializes the polynomial to zero.
    public Polynomial() {
        this.coefficients = new double[] {0};
        this.exponents = new int[] {0};
    }

    // Constructor that initializes polynomial with given coefficients and exponents.
    public Polynomial(double[] coefficients, int[] exponents) {
        if (coefficients.length != exponents.length) {
            throw new IllegalArgumentException("Coefficients and exponents must have the same length");
        }
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    // Constructor that initializes polynomial from a file.
    public Polynomial(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine().replaceAll("\\s+", ""); // Removing whitespace
        reader.close();
        
        String[] terms = line.split("(?=[+-])"); // Split into terms at + or -
        ArrayList<Double> coeffs = new ArrayList<>();
        ArrayList<Integer> exps = new ArrayList<>();
        
        for (String term : terms) {
            String[] parts = term.split("x\\^?");
            double coeff = parts.length > 0 ? Double.parseDouble(parts[0]) : 1;
            int exp = parts.length > 1 ? Integer.parseInt(parts[1]) : (term.contains("x") ? 1 : 0);
            coeffs.add(coeff);
            exps.add(exp);
        }
        
        this.coefficients = coeffs.stream().mapToDouble(d -> d).toArray();
        this.exponents = exps.stream().mapToInt(i -> i).toArray();
    }

    // Add two polynomials.
    public Polynomial add(Polynomial other) {
        TreeMap<Integer, Double> newPoly = new TreeMap<>();

        // Add this polynomial's terms to the map
        for (int i = 0; i < this.exponents.length; i++) {
            newPoly.put(this.exponents[i], this.coefficients[i]);
        }

        // Add other polynomial's terms, combining coefficients where exponents match
        for (int i = 0; i < other.exponents.length; i++) {
            newPoly.merge(other.exponents[i], other.coefficients[i], Double::sum);
        }

        // Convert map back to arrays
        double[] coeffs = new double[newPoly.size()];
        int[] exps = new int[newPoly.size()];
        int index = 0;
        for (Map.Entry<Integer, Double> entry : newPoly.entrySet()) {
            exps[index] = entry.getKey();
            coeffs[index] = entry.getValue();
            index++;
        }

        return new Polynomial(coeffs, exps);
    }

    // Multiply two polynomials.
    public Polynomial multiply(Polynomial other) {
        TreeMap<Integer, Double> result = new TreeMap<>();

        for (int i = 0; i < this.coefficients.length; i++) {
            for (int j = 0; j < other.coefficients.length; j++) {
                int exp = this.exponents[i] + other.exponents[j];
                double coeff = this.coefficients[i] * other.coefficients[j];
                result.merge(exp, coeff, Double::sum);
            }
        }

        double[] coeffs = new double[result.size()];
        int[] exps = new int[result.size()];
        int index = 0;
        for (Map.Entry<Integer, Double> entry : result.entrySet()) {
            exps[index] = entry.getKey();
            coeffs[index] = entry.getValue();
            index++;
        }

        return new Polynomial(coeffs, exps);
    }

    // Evaluate the polynomial for a given x value.
    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            result += this.coefficients[i] * Math.pow(x, this.exponents[i]);
        }
        return result;
    }

    // Check if a value is a root of the polynomial.
    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    // Save polynomial to a file.
    public void saveToFile(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.coefficients.length; i++) {
            sb.append((i > 0 && this.coefficients[i] > 0 ? "+" : "") + this.coefficients[i] + "x^" + this.exponents[i]);
        }
        writer.write(sb.toString());
        writer.close();
    }
}
