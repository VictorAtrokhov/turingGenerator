package com.accenture.atv.machine;

import java.util.*;

/**
 * @author Victor
 */
public class Turing
{
    private final char[] alphabet;

    private Map<String, Set<Instruction>> stateToInstructions = new HashMap<>();
    // private Set<String> States = new LinkedHashSet<>();
    private List<String> inlineStates = new ArrayList<>();
    private List<String> inlineInstructions = new ArrayList<>();
    private List<String> inlineDefinitions = new ArrayList<>();
    private List<String> inlineRange = new ArrayList<>();

    private Set<String> inlineRangeSet = new HashSet<>();

    private List<String> inlineTransitionFunction = new ArrayList<>();

    // private Set<Definition> definitions = new LinkedHashSet<>();
    private Set<Instruction> instructionSet = new LinkedHashSet<>();
    // private Set<Instruction> acceptInstructions = new LinkedHashSet<>();
    // private Set<Instruction> rejectInstructions = new LinkedHashSet<>();

    public Turing(char... alphabet)
    {
        this.alphabet = alphabet;
        Set<Instruction> initialInstructions = new LinkedHashSet<>();
        Definition definition = new Definition("0", '*');
        Instruction newInstruction = new Instruction(definition, '*', '*', "S0");
        initialInstructions.add(newInstruction);
        stateToInstructions.put("0", initialInstructions);
    }

    public void appendAllInstructions(List<String> sequences)
    {
        for (String sequence : sequences)
        {
            appendInstructions(sequence);
        }
        generateAccepts();
        generateRejects();
    }

    private void appendInstructions(String sequence)
    {
        for (int i = 0; i < sequence.length(); i++)
        {
            String charactersBeforeI = sequence.substring(0, i);
            char charAtI = sequence.charAt(i);
            generateInstruction(charactersBeforeI, charAtI);
        }
    }

    private void generateAccepts()
    {
        for (Instruction instruction : instructionSet)
        {
            String initialState = instruction.getNewState();
            Definition definition = new Definition(initialState, '_');
            Instruction newInstruction = new Instruction(definition, '_', 'R', "halt-accept");
            stateToInstructions.computeIfAbsent(initialState, k -> new LinkedHashSet<>()).add(newInstruction);
        }
    }

    private void generateRejects()
    {
        Map<String, Set<Instruction>> temp = stateToInstructions;
        for (String key : temp.keySet())
        {
            if (key.equals("0"))
            {
                continue;
            }
            Set<Instruction> insts = temp.get(key);
            for (char letter : alphabet)
            {
                boolean found = false;
                for (Instruction inst : insts)
                {
                    Definition def = inst.getDefinition();
                    if (letter == def.getReceivedSymbol())
                    {
                        found = true;
                        break;
                    }
                }
                if (!found)
                {
                    Definition definition = new Definition(key, letter);
                    Instruction newInstruction = new Instruction(definition, letter, 'R',
                            "halt" + "-reject"); // not R?
                    stateToInstructions.computeIfAbsent(key, k -> new LinkedHashSet<>()).add(newInstruction);
                }
            }
        }

    }

    private void generateInstruction(String before, char symbol)
    {
        String initialState;
        if (before.isEmpty())
        {
            initialState = "S0";
        } else
        {
            initialState = "S" + before;
        }
        String nextState = 'S' + before + symbol;
        Definition definition = new Definition(initialState, symbol);
        Instruction instruction = new Instruction(definition, symbol, 'R', nextState);
        instructionSet.add(instruction);
        stateToInstructions.computeIfAbsent(initialState, k -> new LinkedHashSet<>()).add(instruction);
    }

    private void sortTuringList(List<String> list)
    {
        list.sort(new CustomStringComparator());
    }

    public void printAll()
    {
        //THIS can be refactored
        for (String line : inlineInstructions)
        {
            System.out.println(line);
        }
        int i = 1;
        System.out.println();
        System.out.println("//////////////////////States of machine/////////////////////////");
        for (String line : inlineStates)
        {
            System.out.printf("%s,", line);
            if (i % 10 == 0)
            {
                System.out.println();
            }
            i++;
        }
        i = 1;
        System.out.println();
        System.out.println("//////////////////////Domain of definitions/////////////////////////");
        for (String line : inlineDefinitions)
        {
            System.out.printf("%s,", line);
            if (i % 5 == 0)
            {
                System.out.println();
            }
            i++;
        }
        i = 1;
        System.out.println();
        System.out.println("//////////////////////Range of Turing " +
                "Machine/////////////////////////");
        System.out.println("//////////////////////Without duplicates/////////////////////////");
        for (String line : inlineRange)
        {
            System.out.printf("%s,", line);
            if (i % 5 == 0)
            {
                System.out.println();
            }
            i++;
        }
        i = 1;
        System.out.println();
        System.out.println("//////////////////////Transition Function/////////////////////////");
        for (String line : inlineTransitionFunction)
        {
            System.out.printf("%s,", line);
            if (i % 3 == 0)
            {
                System.out.println();
            }
            i++;
        }
    }

    public void analizeMachine()
    {
        for (Map.Entry<String, Set<Instruction>> entry : stateToInstructions.entrySet())
        {
            inlineStates.add(entry.getKey());
            for (Instruction instruction : entry.getValue())
            {
                inlineInstructions.add(instruction.inLineInstruction(1));
                Definition def = instruction.getDefinition();
                String inlineDef = String.format("(%s,%c)", def.getInitialState(),
                        def.getReceivedSymbol());
                String inlineValue = String.format("(%c,%c,%s)", instruction.getOutputSymbol(),
                        instruction.getDirection(), instruction.getNewState());
                String inlineTransFunction = String.format("(%s,%s)", inlineDef, inlineValue);
                inlineDefinitions.add(inlineDef);
                inlineRangeSet.add(inlineValue);
                // inlineRange.add(inlineValue);
                inlineTransitionFunction.add(inlineTransFunction);

            }
        }
        sortTuringList(inlineStates);
        sortTuringList(inlineInstructions);
        sortTuringList(inlineDefinitions);
        inlineRange = new ArrayList<>(inlineRangeSet);
        // sortTuringList(inlineRange);
        sortTuringList(inlineTransitionFunction);
    }

}

class CustomStringComparator implements Comparator<String>
{
    @Override
    public int compare(String str1, String str2)
    {
        int spaceIndex1 = str1.indexOf(' ');
        int spaceIndex2 = str2.indexOf(' ');

        if (spaceIndex1 != spaceIndex2)
        {
            return Integer.compare(spaceIndex1, spaceIndex2);
        }

        int minLength = Math.min(str1.length(), str2.length());
        for (int i = 0; i < minLength; i++)
        {
            int order1 = getCustomOrder(str1.charAt(i));
            int order2 = getCustomOrder(str2.charAt(i));

            int result = Integer.compare(order1, order2);

            if (result != 0)
            {
                return result;
            }
        }
        return Integer.compare(str1.length(), str2.length());
    }

    private int getCustomOrder(char c)
    {
        return switch (c)
                {
                    case ' ' -> 0;
                    case '0' -> 1;
                    case 'S' -> 2;
                    case '_' -> 3;
                    case 'a', '1' -> 4;
                    case 'b', '2' -> 5;
                    case 'c' ,'3' -> 6;
                    case 'd' -> 7;
                    default -> 10;
                };
    }
}
