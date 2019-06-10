package com.mygdx.game;

import com.badlogic.gdx.Input;

import java.util.HashMap;
import java.util.HashSet;

public class MyTextInputListener implements Input.TextInputListener {

    String word;

    @Override
    public void input(String text){
        word = text;
    }

    @Override
    public void canceled(){

    }

    public boolean isWord(Grid grid, HashSet<String> dictionary){
        HashMap<Character, Integer> compareFreq = new HashMap<Character, Integer>(grid.getLetterFreq());

        if(word==null){
            return false;
        }

        //if the word is in the dictionary
        if(dictionary.contains(word)) {
            //System.out.println("is a word");
            //go through each character in the word created
            for (char ch : word.toUpperCase().toCharArray()) {
                //System.out.println(ch+" "+compareFreq.containsKey(ch)+" "+compareFreq.get(ch));
                if (compareFreq.containsKey(ch) && compareFreq.get(ch) > 0) {
                    int n = ((Integer) compareFreq.get(ch)).intValue();
                    compareFreq.put(ch, n - 1);
                }
                else{
                    //System.out.println(ch);
                    return false;
                }
            }

        }

        return true;
    }

    public String getWord(){
        return word;
    }

}
