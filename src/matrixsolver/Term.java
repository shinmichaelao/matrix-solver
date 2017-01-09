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
        int coeffEndsAt = 0;
        char curChar = term.charAt(coeffEndsAt);
        while(!Character.isLetter(term.charAt(coeffEndsAt))){ //check if its either a number or  a . or sign
            coeffEndsAt++;
        }
        coeffEndsAt--;
        String coeffString = term.substring(0,coeffEndsAt + 1);
        this.coeff = Double.parseDouble(term.substring(0,coeffEndsAt + 1));
        this.variable = term.substring(coeffEndsAt, term.length());
    }
    
}
