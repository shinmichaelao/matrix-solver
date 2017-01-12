/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package matrixsolver;

import java.util.*;

/**
 *
 * @author xum3131
 */
public class Row {
    Map<String, Double> parts = new HashMap<>();
    int fixing = 1;
    

    public Row(String equation){
        parts.put("constant", 0.0);
        
        equation = equation.replaceAll("\\s+","");
        char[] kappa = equation.toCharArray();
        String curPart = "";
        for(char curChar: kappa){
            if(curChar == '='){
                Term curTerm = new Term(curPart);
                this.addTerm(curTerm);
                
                curPart = "";
                fixing = -1;
            }
            else if(curChar == '+' || curChar == '-'){
                Term curTerm = new Term(curPart);
                this.addTerm(curTerm);
                
                curPart = Character.toString(curChar);
            }
            else{
                curPart += Character.toString(curChar);
            }
        }
        this.addTerm(new Term(curPart));
        fixing = 1;
    }
    
    public void addTerm(Term newTerm){
                
        String key = newTerm.variable;
        double value;
        if(key.equals("constant")){
            value = newTerm.coeff * fixing * -1;
        }
        else{
            value = newTerm.coeff * fixing;
        }

        if(!this.parts.containsKey(key)){
            this.parts.put(key, value);
        }
        else{
            this.parts.put(key, this.parts.get(key) + value);
        }
        
    }
    
    public void multiplyScalar(){
        
    }
}
