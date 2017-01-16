/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package matrixsolver;

import java.util.*;

public class Matrix {
    List<Row> rows = new ArrayList();
    List<String> keys = new ArrayList();
    
    public Matrix(List<Row> stuff){
        //List<String> keys = new ArrayList();
        for(Row r: stuff){
            List<String> curKeys = new ArrayList<>(r.parts.keySet());
            for(String key: curKeys){
                if(!this.keys.contains(key)){
                    this.keys.add(key);
                }
            }
            this.rows.add(r);
        }
        
        for(Row r: this.rows){
            for(String key: keys){
                if(!r.parts.containsKey(key)){
                    r.parts.put(key, new Fraction(0,1));
                }
            }
        }
        
        
    }
    
    public void swap(int i, int j){ //swap the position of row i and row j in matrix
        Collections.swap(this.rows, i, j);
    }
    
    public void add(int i, int j){ //add row i to row j in matrix ie row j = row j + row i
        Row rowi = this.rows.get(i);
        Row rowj = this.rows.get(j);
        List<String> keys = new ArrayList<>(rowi.parts.keySet());
        for(String key: keys){
            rowj.addTerm(rowi.getTerm(key));
        }
    }
    
    public void subtract(int i, int j){ //subtract j from i
        Row rowi = this.rows.get(i);
        Row rowj = this.rows.get(j);
        List<String> keys = new ArrayList<>(rowi.parts.keySet());
        for(String key: keys){
            rowj.addTerm(rowi.getTerm(key));
        }
    }
    
    public void solve(){
        int i = 0;
        for(int keyNum = 0; keyNum < keys.size(); keyNum++/*int i = 0; i < this.rows.size() - 1; i++*/){
            
            //find pivot
            String curKey = keys.get(keyNum);
            int c = i;
            Row pivot = this.rows.get(i);
            Fraction pivotCoeff = pivot.getValue(curKey);
            while(pivotCoeff.getValue() == 0){
               c++;
               if(c == rows.size()){
                   break;
               }
               this.swap(i,c);
               pivot = this.rows.get(i);
               pivotCoeff = pivot.getValue(curKey);
            }
            if(c == rows.size()){
                continue;
            }
            
            pivot.divideScalar(pivotCoeff);
            
            //Eliminate the variable
            for(int j = i; j < this.rows.size(); j++){
                Row curRow = rows.get(j);
                Fraction curRowCoeff = curRow.getValue(curKey);
                if(curRowCoeff.getValue() == 0){
                    continue;
                }
                
                pivot.multiplyScalar(curRowCoeff);
                //SUBTRACT THE TWO
                
            }
            
            i++;
        }
    }
    
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(Row i: this.rows){
            s.append(i.toString()).append("\n");
        }
        return s.toString();
    }
}
