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
    Fraction coeff;
    String variable;
    
    //constructor with a double as the coefficient and a string as the variable
    public Term(double c, String v){
        this.coeff = new Fraction(c);
        this.variable = v;
    }
    
    //constructor with fraction coefficient and string variable
    public Term(Fraction f, String v){
        this.coeff = f;
        this.variable = v;
    }
    
    //constructor that parses through a string ie 4x or 16/3x, you cannot use 16x/3
    public Term(String term){
        int coeffEndsAt = 0;
        int termLength = term.length();
        while(!Character.isLetter(term.charAt(coeffEndsAt))){ //check if its either a number or  a . or sign
            coeffEndsAt++;
            if(coeffEndsAt == termLength){
                break;
            }
        }
        coeffEndsAt--;
        String coeffString = term.substring(0,coeffEndsAt + 1);
        switch (coeffString) {
            case "":
                coeffString = "1";
                break;
            case "+":
                coeffString = "1";
                break;
            case "-":
                coeffString = "-1";
                break;
        }
        String termString = term.substring(coeffEndsAt+1, termLength);
        if(termString.equals("")){
            termString = "constant";
        }
        this.coeff = new Fraction(coeffString);
        this.variable = termString;
    }
    
    //return actual string equivalent
    @Override
    public String toString(){
        return coeff.toString() + variable;
    }
}
