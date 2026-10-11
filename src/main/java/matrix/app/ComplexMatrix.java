package matrix.app;

public class ComplexMatrix {
    private ComplexNumber[][] cells;
    private int dim;

    public ComplexMatrix(int dim) {
        if (dim < 1) {
            throw new IllegalArgumentException("Dimension must be >= 1");
        }
        this.dim = dim;
        this.cells = new ComplexNumber[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                cells[i][j] = new ComplexNumber();
            }
        }
    }

    public int getSize() {
        return dim;
    }

    public ComplexNumber get(int i, int j) {
        return cells[i][j];
    }

    public void set(int i, int j, ComplexNumber value) {
        cells[i][j] = value;
    }

    public ComplexMatrix transpose() {
        ComplexMatrix res = new ComplexMatrix(dim);
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                res.cells[i][j] = cells[j][i];
            }
        }
        return res;
    }

    public ComplexMatrix without(int row, int col) {
        ComplexMatrix res = new ComplexMatrix(dim - 1);
        int r = 0;
        for (int i = 0; i < dim; i++) {
            if (i == row) continue;
            int c = 0;
            for (int j = 0; j < dim; j++) {
                if (j == col) continue;
                res.cells[r][c] = cells[i][j];
                c++;
            }
            r++;
        }
        return res;
    }

    public ComplexNumber determinant() {
        if (dim == 1) {
            return cells[0][0];
        }
        if (dim == 2) {
            return cells[0][0].times(cells[1][1]).minus(cells[0][1].times(cells[1][0]));
        }
        ComplexNumber sum = new ComplexNumber();
        for (int j = 0; j < dim; j++) {
            ComplexNumber term = cells[0][j].times(without(0, j).determinant());
            if (j % 2 == 0) {
                sum = sum.plus(term);
            } else {
                sum = sum.minus(term);
            }
        }
        return sum;
    }

    private void checkSame(ComplexMatrix other) {
        if (dim != other.dim) {
            throw new IllegalArgumentException("Dimensions do not match");
        }
    }

    public ComplexMatrix plus(ComplexMatrix other) {
        checkSame(other);
        ComplexMatrix res = new ComplexMatrix(dim);
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                res.cells[i][j] = cells[i][j].plus(other.cells[i][j]);
            }
        }
        return res;
    }

    public ComplexMatrix minus(ComplexMatrix other) {
        checkSame(other);
        ComplexMatrix res = new ComplexMatrix(dim);
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                res.cells[i][j] = cells[i][j].minus(other.cells[i][j]);
            }
        }
        return res;
    }

    public ComplexMatrix times(ComplexMatrix other) {
        checkSame(other);
        ComplexMatrix res = new ComplexMatrix(dim);
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                ComplexNumber acc = new ComplexNumber();
                for (int k = 0; k < dim; k++) {
                    acc = acc.plus(cells[i][k].times(other.cells[k][j]));
                }
                res.cells[i][j] = acc;
            }
        }
        return res;
    }

    public ComplexMatrix over(ComplexNumber scalar) {
        ComplexMatrix res = new ComplexMatrix(dim);
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                res.cells[i][j] = cells[i][j].over(scalar);
            }
        }
        return res;
    }

    public ComplexMatrix over(ComplexMatrix other) {
        checkSame(other);
        ComplexNumber det = other.determinant();
        if (det.isZero()) {
            throw new IllegalArgumentException("Division impossible: zero determinant");
        }

        if (dim == 1) {
            ComplexMatrix res = new ComplexMatrix(1);
            res.cells[0][0] = cells[0][0].over(other.cells[0][0]);
            return res;
        }

        ComplexMatrix adj = new ComplexMatrix(dim);
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                ComplexNumber m = other.without(i, j).determinant();
                if ((i + j) % 2 == 0) {
                    adj.cells[i][j] = m;
                } else {
                    adj.cells[i][j] = m.times(new ComplexNumber(-1, 0));
                }
            }
        }

        ComplexMatrix inv = adj.transpose().over(det);
        return this.times(inv);
    }

    @Override
    public String toString() {
        int[] widths = new int[dim];
        for (int j = 0; j < dim; j++) {
            for (int i = 0; i < dim; i++) {
                widths[j] = Math.max(widths[j], cells[i][j].toString().length());
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                String cell = cells[i][j].toString();
                String padded = cell;
                while (padded.length() < widths[j]) {
                    padded += " ";
                }
                if (dim == 1) {
                    sb.append("[ ").append(padded).append(" ]");
                } else if (j == 0) {
                    sb.append("[ ").append(padded).append(" ");
                } else if (j == dim - 1) {
                    sb.append(padded).append(" ]\n");
                } else {
                    sb.append(padded).append(" ");
                }
            }
        }
        return sb.toString();
    }
}