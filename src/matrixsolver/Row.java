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
    Map<String, Fraction> parts = new HashMap<>();
    int fixing = 1;
    

    public Row(String equation){
        parts.put("constant", new Fraction(0,1));
        
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
        Fraction value;
        if(key.equals("constant")){
            //value = newTerm.coeff * fixing * -1;
            newTerm.coeff.multiplyScalar(fixing * -1);
            value = newTerm.coeff;
        }
        else{
            //value = newTerm.coeff * fixing;
            newTerm.coeff.multiplyScalar(fixing);
            value = newTerm.coeff;
        }

        if(!this.parts.containsKey(key)){
            this.parts.put(key, value);
        }
        else{
            this.parts.put(key, Fraction.add(this.parts.get(key), value));
        }
        
    }
    
    public void multiplyScalar(){
        
    }
}
