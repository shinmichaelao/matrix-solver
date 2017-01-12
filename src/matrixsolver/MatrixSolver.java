/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package matrixsolver;

/**
 *
 * @author xum3131
 */
public class MatrixSolver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Row kappa = new Row("6x - 3y + 1z = x");
        System.out.println(kappa.parts);
        Term kappa2 = new Term("-x");
        System.out.println(kappa2);
    }
}
