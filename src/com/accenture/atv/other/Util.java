package com.accenture.atv.other;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Victor
 */
public class Util
{
    public static List<String> generateList(char symbol, int count)
    {
        List<String> list = new ArrayList<>();
        for (int i = 0; i <= count; i++)
        {
            list.add(String.valueOf(symbol).repeat(i));
        }
        return list;
    }

    static List<String> combineLists(List<String> first, List<String> second)
    {
        List<String> combinedList = new ArrayList<>();
        for (String a : first)
        {
            for (String b : second)
            {
                String combinedElement = a + b;

                // Check for duplicates before adding
                if (!combinedList.contains(combinedElement))
                {
                    combinedList.add(combinedElement);
                }
            }
        }
        return combinedList;
    }

    public static List<String> combineAllLists(List<List<String>> listContainer)
    {
        if (listContainer.isEmpty())
        {
            return new ArrayList<>();
        }

        List<String> combinedList = listContainer.get(0);

        for (int i = 1; i < listContainer.size(); i++)
        {
            combinedList = combineLists(combinedList, listContainer.get(i));
        }

        return combinedList;
    }
}
