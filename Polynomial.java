public class Polynomial {
    private double[] coefficients;

    // No-argument constructor: initializes polynomial to zero.
    public Polynomial() {
        this.coefficients = new double[] { 0 };
    }

    // Constructor: initializes polynomial with given coefficients.
    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;
    }

    // Add two polynomials.
    public Polynomial add(Polynomial other) {
        int length = Math.max(this.coefficients.length, other.coefficients.length);
        double[] result = new double[length];

        for (int i = 0; i < length; i++) {
            double thisCoeff = i < this.coefficients.length ? this.coefficients[i] : 0;
            double otherCoeff = i < other.coefficients.length ? other.coefficients[i] : 0;
            result[i] = thisCoeff + otherCoeff;
        }

        return new Polynomial(result);
    }

    // Evaluate the polynomial for a given x value.
    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            result += this.coefficients[i] * Math.pow(x, i);
        }
        return result;
    }

    // Check if a value is a root of the polynomial.
    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }
}
