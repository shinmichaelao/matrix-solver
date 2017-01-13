/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package matrixsolver;

import java.util.*;

public class Matrix {
    List<Row> rows = new ArrayList();
    
    public Matrix(List<Row> stuff){
        this.rows = stuff;
    }
    
    public void swap(int i, int j){ //swap the position of row i and row j in matrix
        Collections.swap(this.rows, i, j);
    }
    
    public void add(int i, int j){ //add row i to row j in matrix ie row j = row j + row i
        Row rowi = rows.get(i);
        Row rowj = rows.get(j);
        List<String> keys = new ArrayList<>(rowi.parts.keySet());
        for(String key: keys){
            rowj.addTerm(rowi.getTerm(key));
        }
    }
    
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(Row i: this.rows){
            s.append(i.toString() + "\n");
        }
        return s.toString();
    }
}
