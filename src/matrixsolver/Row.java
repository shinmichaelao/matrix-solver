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
    
    //make a row in a matrix from a math equation in string form
    public Row(String equation){
        //add a constant of 0
        parts.put("constant", new Fraction(0,1));
        
        //add the rest of the variables and constants
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
            else if(!curPart.equals("") && (curChar == '+' || curChar == '-')){
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
    
    //make row in matrix by using the number of a specific element as the coefficient
    public Row(String element, ChemEquation ce){
        //add constant of 0
        parts.put("constant", new Fraction(0,1));
        
        for(String key: ce.leftSide.keySet()){ //for each chemical compound, find the number of each element in it
            ChemTerm curTerm = ce.leftSide.get(key);
            
            Fraction fr;
            if(curTerm.parts.containsKey(element)){
                fr = curTerm.parts.get(element);
                
            }
            else{
                fr = new Fraction(0,1);
            }
            Term t = new Term(fr, key);
            //System.out.println(t);
            this.addTerm(t);
        }

        for(String key: ce.rightSide.keySet()){ //for each chemical compound, find the number of each element in it
            ChemTerm curTerm = ce.rightSide.get(key);
            Fraction fr;
            if(curTerm.parts.containsKey(element)){
                fr = curTerm.parts.get(element);
                fr.multiplyScalar(-1);
            }
            else{
                fr = new Fraction(0,1);
            }
            Term t = new Term(fr, key);
            this.addTerm(t);
        }
    }
    
    //returns the entire term from the key
    public Term getTerm(String s){
        if(!this.parts.containsKey(s)){
            this.parts.put(s, new Fraction(0,1));
        }
        Fraction f = this.parts.get(s);
        return new Term(f,s);
    }
    
    //returns specific value from the key
    public Fraction getValue(String s){
        if(!this.parts.containsKey(s)){
            this.parts.put(s, new Fraction(0,1));
        }
        return this.parts.get(s);
    }
    
    //adds terms to the row while combining terms with the same variable
    public void addTerm(Term newTerm){      
        String key = newTerm.variable;
        Fraction value;
        if(key.equals("constant")){
            newTerm.coeff.multiplyScalar(fixing * -1);
            value = newTerm.coeff;
        }
        else{
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
    
    //multiplies the entire row by a scalar
    public void multiplyScalar(Fraction f){
        List<String> keys = new ArrayList<>(this.parts.keySet());
        for(String key: keys){
            this.parts.put(key, Fraction.multiply(this.parts.get(key), f));
        }
    }
    
    //divides entire row by a scalar
    public void divideScalar(Fraction f){
        List<String> keys = new ArrayList<>(this.parts.keySet());
        for(String key: keys){
            this.parts.put(key, Fraction.divide(this.parts.get(key), f));
        }
    }
    
    //return actual string equivalent
    public String toString(){
        List<String> keys = new ArrayList<>(this.parts.keySet());
        String stringMode = "=";
        for(String key: keys){
            if(key.equals("constant")){
                stringMode = stringMode + this.parts.get(key);
            }
            else{
                stringMode = this.parts.get(key) + key + " " + stringMode;
            }
        }
        return stringMode;
    }
}
