/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package matrixsolver;

import java.util.*;

public class Matrix {
    List<Row> rows = new ArrayList(); //the rows
    List<String> keys = new ArrayList(); //the keys in each row
    
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
    
    public Matrix(ChemEquation ce){
        
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
        List<String> keyss = new ArrayList<>(rowj.parts.keySet());
        for(String key: keyss){
            Term curTerm = rowj.getTerm(key);
            Term subTerm = new Term(curTerm.coeff, curTerm.variable);
            if(subTerm.variable.equals("constant")){
                subTerm.coeff.multiplyScalar(-1);
            }
            subTerm.coeff.multiplyScalar(-1);
            rowi.addTerm(subTerm);
        }
    }
    
    public void solve(){
        int i = 0;
        for(int keyNum = keys.size() - 1; keyNum > 0; keyNum--){
            
            //find pivot
            String curKey = keys.get(keyNum);
            System.out.println("Elimination of: " + curKey);
            int c = i;
            for(int checkPivot = i; checkPivot < this.rows.size(); checkPivot ++){
                Row r = this.rows.get(checkPivot);
                if(r.getValue(curKey).getValue() == 1){
                    this.swap(i, checkPivot);
                }
            }
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
            
            System.out.println("Pivot is " + pivot);
            
            if(c == rows.size()){
                continue;
            }
            
            pivot.divideScalar(pivotCoeff);
            
            System.out.println("Pivot changed to " + pivot);
            //Eliminate the variable
            
            for(int j = i + 1; j < this.rows.size(); j++){
                Row curRow = rows.get(j);
                System.out.println("Removing " + curKey + " from row: " + (j+1) + " " + curRow);
                Fraction curRowCoeff = curRow.getValue(curKey);
                if(curRowCoeff.getValue() == 0){
                    continue;
                }
                
                pivot.multiplyScalar(curRowCoeff);
                System.out.println("Multiply pivot by: " + curRowCoeff);
                System.out.println("Pivot row is now " + pivot);
                //SUBTRACT THE TWO
                this.subtract(j,i);
                System.out.println("Row " + (j+1) + " is now " + curRow);
                
                pivot.divideScalar(curRowCoeff);
                pivot.multiplyScalar(new Fraction(-1/1));
                System.out.println("Restoring pivot: " + pivot);
            }
            
            i++;
            if(i > rows.size()){
                break;
            }
            System.out.println("");
        }
        
        System.out.println("Row-Echelon Form");
        System.out.println(this.toString());
        System.out.println("");
        
        i = this.rows.size()-1;
        for(int keyNum = 1;keyNum < this.keys.size(); keyNum++){
            String curKey = keys.get(keyNum);
            Row pivot = this.rows.get(i);
            if(pivot.getValue(curKey).getValue() == 0){
                i--;
                if(i< 0){
                    break;
                }
                continue;
            }
            
            for(int j = i - 1; j >= 0; j--){
                Row curRow = rows.get(j);
                System.out.println("Removing " + curKey + " from row: " + (j+1) + " " + curRow);
                Fraction curRowCoeff = curRow.getValue(curKey);
                if(curRowCoeff.getValue() == 0){
                    continue;
                }
                
                pivot.multiplyScalar(curRowCoeff);
                System.out.println("Multiply pivot by: " + curRowCoeff);
                System.out.println("Pivot row is now " + pivot);
                //SUBTRACT THE TWO
                this.subtract(j,i);
                System.out.println("Row " + (j+1) + " is now " + curRow);
                
                pivot.divideScalar(curRowCoeff);
                pivot.multiplyScalar(new Fraction(-1/1));
                System.out.println("Restoring pivot: " + pivot);
                System.out.println("");    
            }
            
            i--;
            if(i < 0){
                break;
            }
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
