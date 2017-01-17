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
        Row kappa = new Row("2x + y - z = 8");
        Row kappa2 = new Row("-3x -y +2z = -11");
        Row kappa3 = new Row("-2x +y +2z = -3");
        List<Row> keepo = new ArrayList<>();
        keepo.add(kappa);
        keepo.add(kappa2);
        keepo.add(kappa3);
        Matrix m = new Matrix(keepo);
        System.out.println(m);
        m.solve();
        System.out.println(m);
        
    }
}
