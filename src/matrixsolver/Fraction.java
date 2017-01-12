/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package matrixsolver;

/**
 *
 * @author xum3131
 */
public class Fraction {
    int numerator;
    int denominator;
    
    public Fraction(int num, int denom){
        this.numerator = num;
        this.denominator = denom;
    }
    
    public Fraction(double n){
        denominator = 1;
        
        while ( ! isWholeNumber(n) ) {
            n *= 10;
            denominator *= 10;
        } 
        
        numerator = (int)n;
    }
    
    //reduce fraction to lowest terms
    public void reduce() {
        int gcd = getGCD( numerator, denominator );
        numerator /= gcd;
        denominator /= gcd;       
    }
    
    @Override
    public String toString(){
        String printedFrac;
        if(this.denominator == 0){
            printedFrac = "" + this.numerator;
        }
        else{
            printedFrac = this.numerator + "/" + this.denominator;
        }
        return printedFrac;
    }
    //Useful stuff for operations with fractions
    private static int getGCD(int a, int b) { 
        int max, min, rem;
        
        max = Math.max(a, b);
        min = Math.min(a, b);
        
        rem = max % min;
        
        while (rem > 0) {
            max = min;
            min = rem;
            rem = max % min;
        }
        
        return min;
    }
    
    private static int getLCM(int a, int b) {
        return a*b/getGCD(a,b);
    }
    
    private static boolean isWholeNumber( double x ) {
        return x == Math.round(x);
    }
    
}
