package matrix.app;

public class ComplexNumber {
    private double re;
    private double im;

    public ComplexNumber() {
        this(0, 0);
    }

    public ComplexNumber(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }

    public ComplexNumber plus(ComplexNumber b) {
        return new ComplexNumber(re + b.re, im + b.im);
    }

    public ComplexNumber minus(ComplexNumber b) {
        return new ComplexNumber(re - b.re, im - b.im);
    }

    public ComplexNumber times(ComplexNumber b) {
        double newRe = re * b.re - im * b.im;
        double newIm = re * b.im + im * b.re;
        return new ComplexNumber(newRe, newIm);
    }

    public ComplexNumber over(ComplexNumber b) {
        double denom = b.re * b.re + b.im * b.im;
        if (denom == 0) {
            throw new IllegalArgumentException("Division by zero complex");
        }
        double newRe = (re * b.re + im * b.im) / denom;
        double newIm = (im * b.re - re * b.im) / denom;
        return new ComplexNumber(newRe, newIm);
    }

    public boolean isZero() {
        return re == 0 && im == 0;
    }

    @Override
    public String toString() {
        if (isZero()) {
            return "0";
        }
        if (im == 0) {
            return trim(re);
        }
        if (re == 0) {
            return imPart(im);
        }
        if (im > 0) {
            return trim(re) + " + " + imPart(im);
        }
        return trim(re) + " - " + imPart(-im);
    }

    private String imPart(double value) {
        if (value == 1) {
            return "i";
        }
        if (value == -1) {
            return "-i";
        }
        return trim(value) + "i";
    }

    private String trim(double value) {
        if (value == (long) value) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }
}