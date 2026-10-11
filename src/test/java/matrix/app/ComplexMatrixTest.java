package matrix.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComplexMatrixTest {

    private ComplexMatrix fillA() {
        ComplexMatrix m = new ComplexMatrix(2);
        m.set(0, 0, new ComplexNumber(1, 0));
        m.set(0, 1, new ComplexNumber(2, 0));
        m.set(1, 0, new ComplexNumber(3, 0));
        m.set(1, 1, new ComplexNumber(4, 0));
        return m;
    }

    private ComplexMatrix fillB() {
        ComplexMatrix m = new ComplexMatrix(2);
        m.set(0, 0, new ComplexNumber(-1, 0));
        m.set(0, 1, new ComplexNumber(-2, 0));
        m.set(1, 0, new ComplexNumber(-3, 0));
        m.set(1, 1, new ComplexNumber(-4, 0));
        return m;
    }

    private void sameMatrix(ComplexMatrix a, ComplexMatrix b) {
        assertEquals(a.getSize(), b.getSize());
        for (int i = 0; i < a.getSize(); i++) {
            for (int j = 0; j < a.getSize(); j++) {
                assertEquals(a.get(i, j).getRe(), b.get(i, j).getRe(), 1e-9);
                assertEquals(a.get(i, j).getIm(), b.get(i, j).getIm(), 1e-9);
            }
        }
    }

    private void sameComplex(ComplexNumber a, ComplexNumber b) {
        assertEquals(a.getRe(), b.getRe(), 1e-9);
        assertEquals(a.getIm(), b.getIm(), 1e-9);
    }

    @Test
    void detOfTwoByTwo() {
        ComplexNumber d = fillA().determinant();
        sameComplex(new ComplexNumber(-2, 0), d);
    }

    @Test
    void detOfOneByOne() {
        ComplexMatrix m = new ComplexMatrix(1);
        m.set(0, 0, new ComplexNumber(5, 0));
        sameComplex(new ComplexNumber(5, 0), m.determinant());
    }

    @Test
    void detOfThreeByThree() {
        ComplexMatrix m = new ComplexMatrix(3);
        m.set(0, 0, new ComplexNumber(1, 0));
        m.set(0, 1, new ComplexNumber(2, 0));
        m.set(0, 2, new ComplexNumber(3, 0));
        m.set(1, 0, new ComplexNumber(0, 0));
        m.set(1, 1, new ComplexNumber(1, 0));
        m.set(1, 2, new ComplexNumber(4, 0));
        m.set(2, 0, new ComplexNumber(5, 0));
        m.set(2, 1, new ComplexNumber(6, 0));
        m.set(2, 2, new ComplexNumber(0, 0));
        sameComplex(new ComplexNumber(1, 0), m.determinant());
    }

    @Test
    void plusWorks() {
        ComplexMatrix expected = new ComplexMatrix(2);
        expected.set(0, 0, new ComplexNumber(0, 0));
        expected.set(0, 1, new ComplexNumber(0, 0));
        expected.set(1, 0, new ComplexNumber(0, 0));
        expected.set(1, 1, new ComplexNumber(0, 0));
        sameMatrix(expected, fillA().plus(fillB()));
    }

    @Test
    void minusWorks() {
        ComplexMatrix expected = new ComplexMatrix(2);
        expected.set(0, 0, new ComplexNumber(2, 0));
        expected.set(0, 1, new ComplexNumber(4, 0));
        expected.set(1, 0, new ComplexNumber(6, 0));
        expected.set(1, 1, new ComplexNumber(8, 0));
        sameMatrix(expected, fillA().minus(fillB()));
    }

    @Test
    void timesWorks() {
        ComplexMatrix expected = new ComplexMatrix(2);
        expected.set(0, 0, new ComplexNumber(-7, 0));
        expected.set(0, 1, new ComplexNumber(-10, 0));
        expected.set(1, 0, new ComplexNumber(-15, 0));
        expected.set(1, 1, new ComplexNumber(-22, 0));
        sameMatrix(expected, fillA().times(fillB()));
    }

    @Test
    void overWorks() {
        ComplexMatrix expected = new ComplexMatrix(2);
        expected.set(0, 0, new ComplexNumber(-1, 0));
        expected.set(0, 1, new ComplexNumber(0, 0));
        expected.set(1, 0, new ComplexNumber(0, 0));
        expected.set(1, 1, new ComplexNumber(-1, 0));
        sameMatrix(expected, fillA().over(fillB()));
    }

    @Test
    void overThrowsOnSingular() {
        ComplexMatrix singular = new ComplexMatrix(2);
        singular.set(0, 0, new ComplexNumber(1, 0));
        singular.set(0, 1, new ComplexNumber(2, 0));
        singular.set(1, 0, new ComplexNumber(2, 0));
        singular.set(1, 1, new ComplexNumber(4, 0));
        assertThrows(IllegalArgumentException.class, () -> fillA().over(singular));
    }

    @Test
    void transposeWorks() {
        ComplexMatrix expected = new ComplexMatrix(2);
        expected.set(0, 0, new ComplexNumber(1, 0));
        expected.set(0, 1, new ComplexNumber(3, 0));
        expected.set(1, 0, new ComplexNumber(2, 0));
        expected.set(1, 1, new ComplexNumber(4, 0));
        sameMatrix(expected, fillA().transpose());
    }

    @Test
    void complexPlus() {
        sameComplex(new ComplexNumber(3, 2), new ComplexNumber(1, 1).plus(new ComplexNumber(2, 1)));
    }

    @Test
    void complexMinus() {
        sameComplex(new ComplexNumber(-1, 0), new ComplexNumber(1, 1).minus(new ComplexNumber(2, 1)));
    }

    @Test
    void complexTimes() {
        sameComplex(new ComplexNumber(0, 2), new ComplexNumber(1, 1).times(new ComplexNumber(1, 1)));
    }

    @Test
    void complexOver() {
        sameComplex(new ComplexNumber(1, 0), new ComplexNumber(2, 0).over(new ComplexNumber(2, 0)));
    }

    @Test
    void complexOverThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new ComplexNumber(1, 1).over(new ComplexNumber(0, 0)));
    }
}