package com.mygdx.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;


//class of the Letter letters for MEGAWORD game
public class Grid {

    //the 25 letters (characters) that players must use to make a word out of
    char[] letters;

    HashMap<Character, Integer> letterFreq;//the number of times each letter appears

    public Grid(){

        letters = new char[25];//25 letters

        for(int i=0; i<letters.length; i++){
            //add a random letter in alphabet to array of letters to be displayed
            letters[i] = (char)(Math.random()*26 + 65);
        }

        //create a collection of all characters in the primitive array and their frequencies
        letterFreq = new HashMap<Character, Integer>();

        //go through each letter in the array
        for(char ch : letters){
            //if the character is not yet in the Map, add this letter and frequency 1
            if(!letterFreq.containsKey(ch)){
                letterFreq.put(ch,1);
            }

            //if the character is already in the Map, add one to its current frequency
            else{
                int n = ((Integer)letterFreq.get(ch)).intValue();
                letterFreq.put(ch,n+1);
            }
        }

    }

    //get HashMap of the letter frequencies
    public HashMap getLetterFreq(){
        return letterFreq;
    }

    //get each letter in array
    public char getLetter(int i){
        return letters[i];
    }

    //get number of letters in the array to choose from
    public int getLength(){
        return letters.length;
    }

    //display all the letters to choose from
    @Override
    public String toString(){
        return Arrays.toString(letters);
    }

}
