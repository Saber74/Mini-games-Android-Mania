package com.mygdx.game;

import com.badlogic.gdx.Input;

import java.util.HashMap;
import java.util.HashSet;

//takes in user inputted word for MEGA WORD game
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
        //copy the hashmap created in the grid class
    	//with all of the frequencies of the letters in the grid
    	//compare these letter frequencies to the letters used in the word
    	//inputted - check whether or not word is valid
    	//(can actually be made from the letters in the grid)
    	HashMap<Character, Integer> compareFreq = new HashMap<Character, Integer>(grid.getLetterFreq());

        if(word==null){
            return false;
        }

        //if the word is in the dictionary (an actual word in English)
        if(dictionary.contains(word)) {
            //go through each character in the word created
            for (char ch : word.toUpperCase().toCharArray()) {
            	
            	//subtract 1 from frequency of each letter in the word
            	//that is found in the grid
                if (compareFreq.containsKey(ch) && compareFreq.get(ch) > 0) {
                    int n = ((Integer) compareFreq.get(ch)).intValue();
                    compareFreq.put(ch, n - 1);
                }
                
                //if letter no longer occurs in hashmap, then
                //word is invalid
                else{
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
