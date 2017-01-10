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
        this.coeff = Double.parseDouble(coeffString);
        this.variable = termString;
    }
    
}
