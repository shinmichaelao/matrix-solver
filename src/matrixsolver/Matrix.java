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
        //sets all the rows up
        for(Row r: stuff){
            List<String> curKeys = new ArrayList<>(r.parts.keySet());
            for(String key: curKeys){
                if(!this.keys.contains(key)){
                    this.keys.add(key);
                }
            }
            this.rows.add(r);
        }
        
        //adds missing variables with "0" as the coefficent
        for(Row r: this.rows){
            for(String key: keys){
                if(!r.parts.containsKey(key)){
                    r.parts.put(key, new Fraction(0,1));
                }
            }
        }
        
        
    }
    
    public Matrix(ChemEquation ce){
        for(String element: ce.elements){ //for each element, make a row for it
            Row curRow = new Row(element, ce);
            
            for(String key: curRow.parts.keySet()){
                if(!this.keys.contains(key)){
                    this.keys.add(key);
                }
            }
            this.rows.add(curRow);
        }
        //put in variables with 0 coefficient if one of the compounds does have have the specific element in it
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
    
    //gaussian elimination
    public void solve(){
        int i = 0;
        for(int keyNum = keys.size() - 1; keyNum > 0; keyNum--){
            
            //find pivot
            String curKey = keys.get(keyNum);
            MatrixGUI.solveText += "Elimination of: " + curKey + "\n";
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

            MatrixGUI.solveText += "Pivot is " + pivot + "\n";
            
            if(c == rows.size()){
                continue;
            }
            
            pivot.divideScalar(pivotCoeff);
            
            MatrixGUI.solveText += "Pivot changed to " + pivot + "\n";
            
            //Eliminate the variable
            for(int j = i + 1; j < this.rows.size(); j++){
                Row curRow = rows.get(j);
                MatrixGUI.solveText += "Removing " + curKey + " from row " + (j+1) + ": " + curRow + "\n";
                Fraction curRowCoeff = curRow.getValue(curKey);
                if(curRowCoeff.getValue() == 0){
                    continue;
                }
                
                pivot.multiplyScalar(curRowCoeff);
                MatrixGUI.solveText += "Multiply pivot by: " + curRowCoeff + "\n";
                MatrixGUI.solveText += "Pivot row is now " + pivot + "\n";
                //SUBTRACT THE TWO
                this.subtract(j,i);
                MatrixGUI.solveText += "Row " + (j+1) + " is now " + curRow + "\n";
                
                pivot.divideScalar(curRowCoeff);
                pivot.multiplyScalar(new Fraction(-1/1));
                MatrixGUI.solveText += "Restoring pivot: " + pivot + "\n";
            }
            
            i++;
            if(i == rows.size()){
                break;
            }
            MatrixGUI.solveText += "\n";
        }
        
        MatrixGUI.solveText += "\n";
        MatrixGUI.solveText += "Row-Echelon Form \n";
        MatrixGUI.solveText += this.toString() + "\n";
        MatrixGUI.solveText += "\n";
        
        //reverse elimination down to up to convert matrix into reduced row-echelon form
        i = this.rows.size()-1;
        for(int keyNum = 1;keyNum < this.keys.size(); keyNum++){
            String curKey = keys.get(keyNum);
            Row pivot = this.rows.get(i);
            if(pivot.getValue(curKey).getValue() == 0){
                int checkNext = keyNum + 1;
                while(checkNext < this.keys.size() && pivot.getValue(curKey).getValue() == 0){
                    curKey = keys.get(checkNext);
                    checkNext++;
                }
                if(checkNext == keys.size()){ //All zero row
                    i--;
                    keyNum--;
                    if(i < 0){
                        break;
                    }
                    continue;
                }
                else{ //Found something that can be eliminated
                    keyNum = checkNext-1;
                }   
            }

            MatrixGUI.solveText += "Pivot is " + pivot + "\n";
            Fraction pivotCoeff = pivot.getValue(curKey);
            pivot.divideScalar(pivotCoeff);
            MatrixGUI.solveText += "Pivot changed to " + pivot + "\n";
            
            for(int j = i - 1; j >= 0; j--){
                Row curRow = rows.get(j);
                MatrixGUI.solveText += "Removing " + curKey + " from row " + (j+1) + ": " + curRow + "\n";
                Fraction curRowCoeff = curRow.getValue(curKey);
                if(curRowCoeff.getValue() == 0){
                    continue;
                }
                
                pivot.multiplyScalar(curRowCoeff);
                MatrixGUI.solveText += "Multiply pivot by: " + curRowCoeff + "\n";
                MatrixGUI.solveText += "Pivot row is now " + pivot + "\n";
                //SUBTRACT THE TWO
                this.subtract(j,i);
                MatrixGUI.solveText += "Row " + (j+1) + " is now " + curRow +"\n";
                
                pivot.divideScalar(curRowCoeff);
                pivot.multiplyScalar(new Fraction(-1/1));
                MatrixGUI.solveText += "Restoring pivot: " + pivot + "\n";
                MatrixGUI.solveText += "\n";
            }
            
            pivot.multiplyScalar(pivotCoeff);
            MatrixGUI.solveText += "Restoring pivot: " + pivot + "\n";
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
