package com.accenture.atv.machine;

import java.util.Objects;

/**
 * @author Victor
 */
public class Instruction
{
    private Definition definition;
    private char outputSymbol;
    private char direction;
    private String newState;

    public Instruction(Definition definition, char outputSymbol, char direction, String newState)
    {
        this.definition = definition;
        this.outputSymbol = outputSymbol;
        this.direction = direction;
        this.newState = newState;
    }

    public void setDefinition(Definition definition)
    {
        this.definition = definition;
    }

    public void setOutputSymbol(char outputSymbol)
    {
        this.outputSymbol = outputSymbol;
    }

    public void setDirection(char direction)
    {
        this.direction = direction;
    }

    public void setNewState(String newState)
    {
        this.newState = newState;
    }

    public Definition getDefinition()
    {
        return this.definition;
    }

    public char getOutputSymbol()
    {
        return outputSymbol;
    }

    public char getDirection()
    {
        return direction;
    }

    public String getNewState()
    {
        return this.newState;
    }


    public void printInstruction()
    {
        this.definition.printDefinition();
        System.out.print(this.outputSymbol + " ");
        System.out.print(this.direction + " ");
        System.out.print(this.newState);
    }

    public String inLineInstruction(int spaces)
    {
        String space = String.valueOf(" ").repeat(spaces);
        return String.format("%s%s%c %c %c %s", this.definition.getInitialState(), space,
                this.definition.getReceivedSymbol(), this.outputSymbol, this.direction,
                this.newState);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instruction that = (Instruction) o;
        return Objects.equals(definition, that.definition);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(definition);
    }
}
