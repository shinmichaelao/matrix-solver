/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package matrixsolver;

/**
 *
 * @author xum3131
 */
import java.util.*;
public class MatrixSolver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Row kappa = new Row("6x - 3y + z = x");
        Row kappa2 = new Row("x + y - z + a= 0");
        List<Row> keepo = new ArrayList<>();
        keepo.add(kappa);
        keepo.add(kappa2);
        Matrix m = new Matrix(keepo);
        System.out.println(m);
        m.add(0, 1);
        System.out.println(m);
    }
}
