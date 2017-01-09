/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package matrixsolver;

/**
 *
 * @author xum3131
 */
public class Term {
    double coeff;
    String variable;
    
    public Term(double c, String v){
        this.coeff = c;
        this.variable = v;
    }
    
    public Term(String term){
        
        String variableStr;
        int coeffEndsAt = 0;
        char curChar = term.charAt(coeffEndsAt);
        while(Character.isDigit(curChar) || !Character.isLetterOrDigit(curChar)){ //check if its either a number or  a . or sign
            coeffEndsAt++;
        }
        this.coeff = Double.parseDouble(term.substring(0,coeffEndsAt + 1));
    }
}
