package com.accenture.atv;

import com.accenture.atv.machine.Turing;
import com.accenture.atv.other.Util;

import java.util.*;

/**
 * @author Victor
 */
public class Main
{
    public static void main(String[] args)
    {
        System.out.println("hello");
        // prepare data
        Scanner scanner = new Scanner(System.in);
        List<List<String>> symbolDefinitions = new ArrayList<>();
        // char[] alphabet = new char[]{'a', 'b', 'c'};
        Set<Character> alphabet = new LinkedHashSet<>();

        System.out.print("How many letters we have(not unique): ");
        int count = scanner.nextInt();
        if (count > 6) count = 6;
        for (int i = 0; i < count; i++)
        {
            // Get user input for char
            System.out.print("Enter a character: ");
            char inputChar = scanner.next().charAt(0);
            // Get user input for number
            System.out.print("Enter a number: ");
            int inputNumber = scanner.nextInt();
            symbolDefinitions.add(Util.generateList(inputChar, inputNumber));
            alphabet.add(inputChar);
        }

        char[] alphabetArray = new char[alphabet.size()];
        int index = 0;
        for (Character character : alphabet)
        {
            alphabetArray[index++] = character;
        }
        Turing turing = new Turing(alphabetArray);
        turing.appendAllInstructions(Util.combineAllLists(symbolDefinitions));
        turing.analizeMachine();
        turing.printAll();

    }
}


