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
        
        this.reduce();
    }
    
    public Fraction(double n){
        denominator = 1;
        
        while ( ! isWholeNumber(n) ) {
            n *= 10;
            denominator *= 10;
        } 
        
        numerator = (int)n;
        
        this.reduce();
    }
    
    public Fraction(String s){
        int slashIndex = s.indexOf("/");
        if(slashIndex == -1){
            Fraction kappaFrac = new Fraction(Double.parseDouble(s));
            this.numerator = kappaFrac.numerator;
            this.denominator = kappaFrac.denominator;
        }
        else{
            Fraction kappaFracTop = new Fraction(Double.parseDouble(s.substring(0,slashIndex)));
            Fraction kappaFracBot = new Fraction(Double.parseDouble(s.substring(slashIndex+1,s.length())));
            Fraction actualFrac = divide(kappaFracTop,kappaFracBot);
            this.numerator = actualFrac.numerator;
            this.denominator = actualFrac.denominator;
        }
    }
    
    public double getValue(){
        return (double)this.numerator/this.denominator;
    }
    //reduce fraction to lowest terms
    public void reduce() {
        int gcd = getGCD( numerator, denominator );
        this.numerator /= gcd;
        this.denominator /= gcd;
        
        if(denominator < 0){
            this.numerator = numerator *-1;
            this.denominator = denominator *-1;
        }
    }
    
    public void multiplyScalar( int n){
        this.numerator = this.numerator * n;
        this.reduce();
    }
    
    public static Fraction add(Fraction f1, Fraction f2){
        int n1, n2, d;
        
        n1 = f1.numerator * f2.denominator;
        n2 = f2.numerator * f1.denominator;
        d = f1.denominator * f2.denominator;
        
        return new Fraction(n1+n2,d);
    }
    
    public static Fraction subtract(Fraction f1, Fraction f2){
        f2.multiplyScalar(-1);
        return add(f1, f2);
    }
    
    public static Fraction multiply( Fraction f1, Fraction f2 ) {
        int n1, n2, d1, d2;
        
        n1 = f1.numerator;
        d1 = f1.denominator;
        
        n2 = f2.numerator;
        d2 = f2.denominator;
        
        return new Fraction( n1*n2, d1*d2 );    
    }
    
    public static Fraction divide(Fraction f1, Fraction f2){
        int n1, n2, d1, d2;
        
        n1 = f1.numerator;
        d1 = f1.denominator;
        
        n2 = f2.numerator;
        d2 = f2.denominator;
        
        return new Fraction( n1*d2, d1*n2);
    }
    
    @Override
    public String toString(){
        String printedFrac;
        if(this.numerator == 0){
            return "0";
        }
        
        if(this.denominator == 1){
            printedFrac = "" + this.numerator;
        }
        else{
            printedFrac = this.numerator + "/" + this.denominator;
        }
        return printedFrac;
    }
    
    //Useful stuff for operations with fractions
    public static int getGCD(int a, int b) {
        
        int max, min, rem;
        
        max = Math.max(a, b);
        min = Math.min(a, b);
        
        if(min == 0){
            return 1;
        }
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
