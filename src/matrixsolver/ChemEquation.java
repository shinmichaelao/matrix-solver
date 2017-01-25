/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrixsolver;

/**
 *
 * @author Michael
 */
import java.util.*;
public class ChemEquation {
    Map<String, ChemTerm> leftSide = new HashMap();
    Map<String, ChemTerm> rightSide = new HashMap();
    Set<String> elements = new HashSet();
    
    public ChemEquation(String ls, String rs){
        ls = ls.replaceAll("\\s+","");
        rs = rs.replaceAll("\\s+","");
        
        char[] leftChar = ls.toCharArray();
        String curCompound = "";
        for(char c: leftChar){
            if(c == '+'){
                //System.out.println(curCompound); //
                leftSide.put(curCompound, new ChemTerm(curCompound));
                elements.addAll(leftSide.get(curCompound).parts.keySet());
                curCompound = "";
            }
            else{
                curCompound = curCompound + Character.toString(c);
            }
        }
        //System.out.println(curCompound); //
        leftSide.put(curCompound, new ChemTerm(curCompound));
        elements.addAll(leftSide.get(curCompound).parts.keySet());
        curCompound = "";
        
        char[] rightChar = rs.toCharArray();
        for(char c: rightChar){
            if(c == '+'){
                //System.out.println(curCompound); //
                rightSide.put(curCompound, new ChemTerm(curCompound));
                elements.addAll(rightSide.get(curCompound).parts.keySet());
                curCompound = "";
            }
            else{
                curCompound = curCompound + Character.toString(c);
            }
        }
        //System.out.println(curCompound); //
        rightSide.put(curCompound, new ChemTerm(curCompound));
        elements.addAll(rightSide.get(curCompound).parts.keySet());
        curCompound = "";
        
    }
}
