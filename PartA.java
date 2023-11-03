import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.Scanner;
import java.io.*;
import java.net.*;

public class PartA {
    public static void main (String[] args) {
        //for regular hangman
        String[] words = {"apple", "banana", "cherry", "date", "strawberry", "fig", "grape", "melon", "kiwi", "lemon"};
        //for evil hangman
        Scanner URLInput = null;
        ArrayList<String> evil_words = new ArrayList<>();
        try {
            URL url = new URL("https://www.mit.edu/~ecprice/wordlist.10000");
            URLInput = new Scanner(url.openStream());
            while (URLInput.hasNext()) {
                String line = URLInput.nextLine();
                if (!line.equals("documentcreatetextnode")) {//remove this word from list
                    evil_words.add(line);
                }
            }
        }
        catch (MalformedURLException ex) {
            System.out.println("Invalid URL");
        }
        catch (IOException ex) {
            System.out.println("IO Errors");
        }
        finally {
            URLInput.close();
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("-----------");
        System.out.println("|          O");
        System.out.println("|         /|\\");
        System.out.println("|         / \\");
        System.out.println("|");
        System.out.println("----");
        boolean cont = true;
        boolean cont_again = true;
        int game = 0;
        boolean cont_game = true;
        System.out.println("Welcome to Part A of Hangman!");

        while (cont == true) {
            while (cont_game == true) {
                System.out.println("Regular Hangman (1) or Evil Hangman (2)?");
                game = scanner.nextInt();
                if (game == 1) {
                    regularHangman(words);
                    break;
                }
                else if (game == 2) {
                    evilHangman(evil_words);
                    break;
                }
                else {
                    System.out.println("Please enter 1 or 2.");
                }
            }
            while (cont_again == true) {
                System.out.println("Play again? (y/n)");
                String again = scanner.nextLine();
                if (again.equals("y")) {
                    break;
                }
                else if (again.equals("n")) {
                    cont_again = false;
                    cont = false;
                }
                else {
                    System.out.println("Please press y or n.");
                }
            }
        }
    }
    public static void regularHangman(String [] words) {
        ArrayList <String> lettersUsed = new ArrayList<>();
        char [] bodyParts = { 'O', '/', '|', '\\', '/', '\\'};
        char [] hangMan = {' ', ' ', ' ', ' ', ' ', ' '};
        boolean cont = true;
        Scanner scanner = new Scanner(System.in);
        String letter;
        int wrongth = 0;
        String correctWord = words[(int)((Math.random()*10))];
        char [] wordArray = new char[correctWord.length()];

        for (int i = 0; i < correctWord.length(); ++i) {
            wordArray[i] = '-';
        }
        System.out.println(hangMan[0]);
        System.out.println("-----------");
        System.out.println("|          ");
        System.out.println("|         ");
        System.out.println("|         ");
        System.out.println("|");
        System.out.println("----");
        while (cont) {
            System.out.print("Letters used: ");
            for (String s: lettersUsed) {
                System.out.print(s + " ");
            }
            System.out.println();
            for (char i : wordArray) {
                System.out.print(i);
            }
            System.out.println();
            System.out.print("Enter letter: ");
            letter = scanner.nextLine();
            if (letter.length() == 1 && Character.isLetter(letter.charAt(0))) {//input validation
                for (String c: lettersUsed) {
                    if (c.charAt(0) == letter.charAt(0)) {//if letter has already been used
                        System.out.println("Letter has already been guessed");
                    }
                }
                int[] indexes = new int[correctWord.length()];
                boolean isEmpty = true;
                for (int i = 0; i < correctWord.length(); ++i) {//every index of correctWord that is letter is 1 in indexes array
                    if (correctWord.charAt(i) == letter.charAt(0)) {
                        indexes[i] = 1;
                    }
                }
                // Checking if the int array is  empty
                for (int i : indexes) {
                    if (i != 0) {
                        isEmpty = false;
                        break;
                    }
                }
                if (isEmpty) {//if there are no instances of the letter in the correct word
                    System.out.println("Not in word");
                    lettersUsed.add(letter);
                    Collections.sort(lettersUsed);

                    hangMan[wrongth] = bodyParts[wrongth];
                    ++wrongth;
                    //make hangman
                }
                else {//if the letter exists in the correct word
                    for (int i = 0; i < correctWord.length(); ++i) {
                        if (indexes[i] == 1) {
                            wordArray[i] = letter.charAt(0);//
                        }
                    }
                }
                int numRight = 0;
                for (char c : wordArray) {
                    if (c != '-') {
                        ++numRight;
                    }
                }
                if (numRight == correctWord.length()) {
                    System.out.println("You found the word!");
                    return;
                }
                System.out.println("-----------");
                System.out.print("|          ");
                System.out.println(hangMan[0]);
                System.out.print("|         ");
                System.out.print(hangMan[1]);
                System.out.print(hangMan[2]);
                System.out.println(hangMan[3]);
                System.out.print("|         ");
                System.out.print(hangMan[4]);
                System.out.println(" " + hangMan[5]);
                System.out.println("|");
                System.out.println("----");
                if (wrongth == 6) {
                    System.out.println("You lose!");
                    return;
                }
            }
            else {
                System.out.println("Please enter a single letter character");
            }
        }
    }
    public static void evilHangman(ArrayList<String> evil_words) {
        ArrayList <String> lettersUsed = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;
        int length = 0;
        int guesses = 0;
        while (validInput == false) {//get length of word
            System.out.print("Length of word (1-18): ");//get length of word
            if (scanner.hasNextInt()) {
                length = scanner.nextInt();
                if ((length > 0) && (length <= 18)) {
                    char[] charArray = new char[length];//make array for word
                    break;
                }
                System.out.println("Please enter a number 1-18");
            }
            else {
                System.out.println("Please enter an integer");
            }
        }
        while (validInput == false) {
            System.out.print("Number of guesses: ");//get number of guesses
            if (scanner.hasNextInt()) {
                guesses = scanner.nextInt();
                break;
            }
            else {
                System.out.println("Please enter an integer");
            }
        }
        ArrayList<String> optimalList = new ArrayList<>();
        for (String element: evil_words) {//get all the words that are that long
            if (element.length() == length) {
                optimalList.add(element);
            }
        }
        System.out.println("There are " + optimalList.size() + " possible words.");
        String letter = "";
        ArrayList <String> noLetter = new ArrayList<>();
        String theWord = "";
        char [] wordArray = new char[length];
        for (int i = 0; i < length; ++i) {
            wordArray[i] = '-';
        }
        Scanner scanner2 = new Scanner(System.in);//had to create a new scanner so that java would wait to get an input to get letter
        boolean guess = true;
        while (guess) {
            while (validInput == false) {
                System.out.print("Letters used: ");

                for (String s: lettersUsed) {
                    System.out.print(s + " ");
                }
                System.out.println();
                System.out.print("Enter letter: ");
                letter = scanner2.nextLine();
                if (letter.length() == 1 && Character.isLetter(letter.charAt(0))) {//if it is a valid letter
                    for (String c: lettersUsed) {
                        if (c.charAt(0) == letter.charAt(0)) {//if letter has already been used
                            System.out.println("Letter has already been guessed");
                            //add something to terminate this letter
                        }
                    }
                    optimalList = makeList(optimalList, length, letter);//get word group with most amount of words
                    int numRight = 0;
                    for (char c : wordArray) {
                        if (c != '-') {
                            ++numRight;
                        }
                    }
                    if (numRight == length) {
                        System.out.println("You found the word!");
                        return;
                    }
                    if (optimalList.get(0).indexOf(letter) == -1) {
                        ++guesses;
                        System.out.println("Not in word");
                        lettersUsed.add(letter);
                        Collections.sort(lettersUsed);
                    }
                    else {
                        for (int i = 0; i < length; ++i) {
                            if (optimalList.get(0).charAt(i) == letter.charAt(0)) {
                                wordArray[i] = letter.charAt(0);
                            }
                        }
                    }

                    for (int i = 0; i < length; ++i) {
                        System.out.print(wordArray[i]);
                    }
                    System.out.println();
                    System.out.println("There are " + optimalList.size() + " possible words.");

                    break;
                }
                else {
                    System.out.println("Please enter a letter");
                }
            }
            if (guesses == 0) {
                //get random word from list
                theWord = optimalList.get((int)(Math.random()*(optimalList.size())));
                System.out.println("The word was: " + theWord);
            }
        }
    }

    public static ArrayList<String> makeList(ArrayList<String> optimalList, int length, String stringLetter) {
        char letter = stringLetter.charAt(0);
        int mostMatches = 0; //find out group with the most matches
        int tempMatches = 0;

        boolean matchFound = false;
        ArrayList<ArrayList<String>> wordGroups = new ArrayList<>();
        //add new arraylist to wordgroups for each group discovered
        boolean cont = true;
        for (String element: optimalList) {//for each word in optimallist
            int [] indexArray = new int [length];
            for (int i = 0; i < length; ++i) {//for each letter in the word
                if (element.charAt(i) == letter) {
                    indexArray[i] = 1;
//                    System.out.println(element);
//                    System.out.println(Arrays.toString(indexArray));
                }
            }
            for (int i = 0; i < wordGroups.size(); ++i) {//go through wordGroups arraylist to see if any matches the index array of the current element from optimalList
                int [] indexArrayGroup = new int [length];
                for (int j = 0; j < length; ++j) {
                    if (wordGroups.get(i).get(0).charAt(j) == letter) {
                        indexArrayGroup[j] = 1;
                    }
                }
                if (Arrays.equals(indexArray, indexArrayGroup)) {//if the index arrays are equal, add the word to the group
                    wordGroups.get(i).add(element);
//                    System.out.println(wordGroups.get(i).toString());
//                    System.out.println("EQUAL");
                    matchFound = true;
                    break;//exit from the search for a matching wordgroup
                }
                else {
//                    System.out.println("WAS NOT A MATCH");
                }
            }

            if (matchFound == false) {
                //if it did not find a match
                System.out.println("MAKING NEW ARRAYLIST");
                System.out.println("size of wordgroups1: " + wordGroups.size());
                ArrayList<String> list = new ArrayList<String>();
                list.add(element);
                wordGroups.add(list);
            }
            matchFound = false;
        }
        System.out.println("size of wordgroups2: " + wordGroups.size());
        print2DArrayList(wordGroups);
        int largestArrayListIndex = 0;
        for (int i = 0; i < wordGroups.size(); ++i) {
            if (wordGroups.get(i).size() > wordGroups.get(largestArrayListIndex).size()) {
                largestArrayListIndex = i;
            }
        }
        System.out.println("index of largest arraylist: " + largestArrayListIndex);
        return wordGroups.get(largestArrayListIndex);
    }

    public static void print2DArrayList(ArrayList<ArrayList<String>> arrayLists){
        for (ArrayList<String> list : arrayLists) {
            for (String value: list) {
                System.out.print(value + ", ");
            }
            System.out.println();
        }
    }
}