package tp2;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

public final class Interview {

    /** TODO
     * This function returns if the two texts are similar based on if they have a similar entropy of the HashMap
     * @return boolean based on if the entropy is similar
     */
    public static Double compareEntropies(String filename1, String filename2) throws IOException {
        HashMap<Character, Integer> hmap1 = getFrequencyHashTable(readFile(filename1));
        HashMap<Character, Integer> hmap2 = getFrequencyHashTable(readFile(filename2));

        double entropy1 = calculateEntropy(hmap1);
        double entropy2 = calculateEntropy(hmap2);

        return Math.abs(entropy1-entropy2);
    }

    /** TODO
     * This function returns the difference in frequencies of two HashMaps which corresponds
     * to the sum of the differences of frequencies for each letter.
     * @return the difference in frequencies of two HashMaps
     */
    public static Integer compareFrequencies(String filename1, String filename2) throws IOException{
        HashMap<Character, Integer> hmap1 = getFrequencyHashTable(readFile(filename1));
        HashMap<Character, Integer> hmap2 = getFrequencyHashTable(readFile(filename2));

        int somme = 0;
        for(var key : hmap1.keySet()){
            if(hmap2.containsKey(key)){
                somme += Math.abs(hmap1.get(key) - hmap2.get(key));
            }
            else{
                somme += hmap1.get(key);
            }
        }

        for(var key : hmap2.keySet()){
            if(!hmap1.containsKey(key)) {
                somme += hmap2.get(key);
            }
        }
        return somme;
    }

    /** TODO
     * @return This function returns the entropy of the HashMap
     */
    public static Double calculateEntropy(HashMap<Character, Integer> map){
        double entropy = 0.0;
        double p, logBase2;
        int stringLength = 0;
        for(var key : map.keySet()){
            stringLength += map.get(key);
        }
        for(var key : map.keySet()){
            p =  (double)map.get(key)/stringLength;
            logBase2 = Math.log10(1/p)/Math.log10(2);
            entropy += p*logBase2;
        }
        return entropy;
    }

    /**
     * This function reads a text file {filenamme} and returns the appended string of all lines
     * in the text file
     */
    public static String readFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        StringBuilder stringB = new StringBuilder();
        String ligne = reader.readLine();

        while(ligne != null){
            stringB.append(ligne);
            ligne = reader.readLine();
        }
        return stringB.toString();
    }

    /** TODO
     * This function takes a string as a parameter and creates and returns a HashTable
     * of character frequencies
     */
    public static HashMap<Character, Integer> getFrequencyHashTable(String text) {
        HashMap<Character, Integer> hmap = new HashMap<Character, Integer>();
        char textArray[] = text.toCharArray();
        for(int i = 0; i < textArray.length; i++){
            if(!hmap.containsKey(textArray[i]) && isAlphabetic(textArray[i])){
                int frequency = 0;
                for(int j = i; j < textArray.length; j++){
                    if(textArray[j] == textArray[i])
                        frequency++;
                }
                hmap.put(textArray[i], frequency);
            }
        }
        return hmap;
    }

    /** TODO
     * This function takes a character as a parameter and returns if it is a letter in the alphabet
     */
    public static Boolean isAlphabetic(Character c){
        return Character.isLetter(c);
    }
}
