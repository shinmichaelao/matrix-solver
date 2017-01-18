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
    Fraction constant = new Fraction(0,1);
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
    
    public Term getTerm(String s){
        if(!this.parts.containsKey(s)){
            this.parts.put(s, new Fraction(0,1));
        }
        Fraction f = this.parts.get(s);
        return new Term(f,s);
    }
    
    public Fraction getValue(String s){
        if(!this.parts.containsKey(s)){
            this.parts.put(s, new Fraction(0,1));
        }
        return this.parts.get(s);
    }
    
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
    
    public void multiplyScalar(Fraction f){
        List<String> keys = new ArrayList<>(this.parts.keySet());
        for(String key: keys){
            this.parts.put(key, Fraction.multiply(this.parts.get(key), f));
        }
    }
    
    public void divideScalar(Fraction f){
        List<String> keys = new ArrayList<>(this.parts.keySet());
        for(String key: keys){
            this.parts.put(key, Fraction.divide(this.parts.get(key), f));
        }
    }
    
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
        //return this.parts.toString();
    }
}
