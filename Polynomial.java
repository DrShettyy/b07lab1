import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Polynomial {
    private double[] coefficients; // Non-zero coefficients
    private int[] exponents; // Corresponding exponents

    // No-argument constructor: initializes polynomial to zero.
    public Polynomial() {
        this.coefficients = new double[] { 0 };
        this.exponents = new int[] { 0 };
    }

    // Constructor: initializes polynomial with given coefficients and exponents.
    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    // Constructor: initializes polynomial from a file.
    public Polynomial(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        String polynomialStr = scanner.nextLine();
        scanner.close();

        ArrayList<Double> coefficientsList = new ArrayList<>();
        ArrayList<Integer> exponentsList = new ArrayList<>();

        String[] terms = polynomialStr.split("(?=\\+)|(?=\\-)");
        for (String term : terms) {
            term = term.trim();
            if (term.contains("x")) {
                String[] parts = term.split("x\\^");
                double coeff = parts[0].isEmpty() || parts[0].equals("+") ? 1 : parts[0].equals("-") ? -1 : Double.parseDouble(parts[0]);
                int exp = parts.length > 1 ? Integer.parseInt(parts[1]) : 1;
                coefficientsList.add(coeff);
                exponentsList.add(exp);
            } else {
                coefficientsList.add(Double.parseDouble(term));
                exponentsList.add(0);
            }
        }

        this.coefficients = new double[coefficientsList.size()];
        this.exponents = new int[exponentsList.size()];

        for (int i = 0; i < coefficientsList.size(); i++) {
            this.coefficients[i] = coefficientsList.get(i);
            this.exponents[i] = exponentsList.get(i);
        }
    }

    // Add two polynomials.
    public Polynomial add(Polynomial other) {
        ArrayList<Double> resultCoefficients = new ArrayList<>();
        ArrayList<Integer> resultExponents = new ArrayList<>();

        int i = 0, j = 0;
        while (i < this.coefficients.length && j < other.coefficients.length) {
            if (this.exponents[i] == other.exponents[j]) {
                double sum = this.coefficients[i] + other.coefficients[j];
                if (sum != 0) {
                    resultCoefficients.add(sum);
                    resultExponents.add(this.exponents[i]);
                }
                i++;
                j++;
            } else if (this.exponents[i] < other.exponents[j]) {
                resultCoefficients.add(this.coefficients[i]);
                resultExponents.add(this.exponents[i]);
                i++;
            } else {
                resultCoefficients.add(other.coefficients[j]);
                resultExponents.add(other.exponents[j]);
                j++;
            }
        }

        while (i < this.coefficients.length) {
            resultCoefficients.add(this.coefficients[i]);
            resultExponents.add(this.exponents[i]);
            i++;
        }

        while (j < other.coefficients.length) {
            resultCoefficients.add(other.coefficients[j]);
            resultExponents.add(other.exponents[j]);
            j++;
        }

        double[] resultCoeffsArray = new double[resultCoefficients.size()];
        int[] resultExponentsArray = new int[resultExponents.size()];
        for (int k = 0; k < resultCoefficients.size(); k++) {
            resultCoeffsArray[k] = resultCoefficients.get(k);
            resultExponentsArray[k] = resultExponents.get(k);
        }

        return new Polynomial(resultCoeffsArray, resultExponentsArray);
    }

    // Multiply two polynomials.
    public Polynomial multiply(Polynomial other) {
        ArrayList<Double> resultCoefficientsList = new ArrayList<>();
        ArrayList<Integer> resultExponentsList = new ArrayList<>();

        for (int i = 0; i < this.coefficients.length; i++) {
            for (int j = 0; j < other.coefficients.length; j++) {
                double newCoeff = this.coefficients[i] * other.coefficients[j];
                int newExp = this.exponents[i] + other.exponents[j];

                // Combine like terms if they already exist.
                boolean termExists = false;
                for (int k = 0; k < resultExponentsList.size(); k++) {
                    if (resultExponentsList.get(k) == newExp) {
                        resultCoefficientsList.set(k, resultCoefficientsList.get(k) + newCoeff);
                        termExists = true;
                        break;
                    }
                }

                if (!termExists) {
                    resultCoefficientsList.add(newCoeff);
                    resultExponentsList.add(newExp);
                }
            }
        }

        double[] resultCoeffsArray = new double[resultCoefficientsList.size()];
        int[] resultExponentsArray = new int[resultExponentsList.size()];
        for (int k = 0; k < resultCoefficientsList.size(); k++) {
            resultCoeffsArray[k] = resultCoefficientsList.get(k);
            resultExponentsArray[k] = resultExponentsList.get(k);
        }

        return new Polynomial(resultCoeffsArray, resultExponentsArray);
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

    // Save the polynomial to a file.
    public void saveToFile(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        StringBuilder polynomialStr = new StringBuilder();

        for (int i = 0; i < coefficients.length; i++) {
            if (coefficients[i] > 0 && i != 0) {
                polynomialStr.append("+");
            }
            if (exponents[i] == 0) {
                polynomialStr.append(coefficients[i]);
            } else {
                polynomialStr.append(coefficients[i]).append("x^").append(exponents[i]);
            }
        }

        writer.write(polynomialStr.toString());
        writer.close();
    }
}
