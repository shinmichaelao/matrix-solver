/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrixsolver;

import java.util.*;
public class ChemTerm {
    Map<String, Fraction> parts = new HashMap(); //element and its coefficient
    
    public ChemTerm(String term){
        term = term.replaceAll("\\s+","");
        String num = "";
        char[] kappa = term.toCharArray();
        String element = Character.toString(kappa[0]);
        for(int i = 1; i < kappa.length; i++){
            char c = kappa[i];
            
            if(c == '('){
                i++;
                String miniterm = "";
                while(kappa[i]!=')'){
                    miniterm = miniterm + Character.toString(kappa[i]);
                    i++;
                }
                ChemTerm bracketed = new ChemTerm(miniterm);
                i++;
                String coeff = "";
                while(i < kappa.length && Character.isDigit(kappa[i])){
                    coeff = coeff + Character.toString(kappa[i]);
                    i++;
                }
                if(coeff.equals("")){
                    coeff = "1";
                }
                int intCoeff = Integer.parseInt(coeff);
                
                List<String> keys = new ArrayList(bracketed.parts.keySet());
                for(String key: keys){
                    bracketed.parts.get(key).multiplyScalar(intCoeff);
                    putElement(key, bracketed.parts.get(key));
                }
            }
            else if(Character.isDigit(c)){
                num = num + Character.toString(c);
            }
            else if(Character.isUpperCase(c)){
                if(num.equals("")){
                    num = "1";
                }
                putElement(element, new Fraction(Integer.parseInt(num)));
                element = Character.toString(c);
                num = "";
            }
            else{
                element = element + Character.toString(c);
            }
        }
        if(num.equals("")){
                num = "1";
        }
        putElement(element, new Fraction(Integer.parseInt(num)));
        
        //System.out.println(parts);
    }
    
    public void putElement(String element, Fraction coeff){
        if(parts.containsKey(element)){
            parts.put(element, Fraction.add( coeff, parts.get(element) ) );
        }
        else{
            parts.put(element, coeff);
        }
    }
}
