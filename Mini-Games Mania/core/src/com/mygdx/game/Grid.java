package com.mygdx.game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Grid {

    //the 5 x 5 grid of letters that players must use to make a word out of
    char[] grid;

    HashMap<Character, Integer> letterFreq;

    public Grid(){

        grid = new char[25];

        for(int i=0; i<grid.length; i++){
            grid[i] = (char)(Math.random()*26 + 65);
        }

        //create a collection of all characters on the grid and their frequencies
        letterFreq = new HashMap<Character, Integer>();

        for(char ch : grid){
            //if the character is not yet in the Map
            if(!letterFreq.containsKey(ch)){
                letterFreq.put(ch,1);
            }

            //if the character is already in the Map, add one to its frequency
            else{
                int n = ((Integer)letterFreq.get(ch)).intValue();
                letterFreq.put(ch,n+1);
            }
        }



    }

    public boolean isWord(String word, HashSet<String> dictionary){
        HashMap<Character, Integer> compareFreq = letterFreq;
        //if the word is in the dictionary
        if(dictionary.contains(word)) {
            //go through each character in the word created
            for (char ch : word.toCharArray()) {
                if (compareFreq.containsKey(ch) && compareFreq.get(ch) > 0) {
                    int n = ((Integer) compareFreq.get(ch)).intValue();
                    compareFreq.put(ch, n - 1);
                }
                else{
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    public HashMap getLetterFreq(){
        return letterFreq;
    }

    public char getLetter(int i){
        return grid[i];
    }

    public int getLength(){
        return grid.length;
    }

    @Override
    public String toString(){
        return Arrays.toString(grid);
    }

}
