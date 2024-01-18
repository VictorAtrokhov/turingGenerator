package com.accenture.atv.machine;

import java.util.Objects;

/**
 * @author Victor
 */
class Definition
{
    private String initialState;
    private char receivedSymbol;

    public Definition(String initialState, char receivedSymbol)
    {
        this.initialState = initialState;
        this.receivedSymbol = receivedSymbol;
    }

    public void setInitialState(String initialState)
    {
        this.initialState = initialState;
    }

    public void setReceivedSymbol(char receivedSymbol)
    {
        this.receivedSymbol = receivedSymbol;
    }

    public String getInitialState()
    {
        return initialState;
    }

    public char getReceivedSymbol()
    {
        return receivedSymbol;
    }

    public void printDefinition()
    {
        System.out.print("\n" + this.initialState + " ");
        System.out.print(this.receivedSymbol + " ");
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Definition that = (Definition) o;
        return receivedSymbol == that.receivedSymbol && Objects.equals(initialState,
                that.initialState);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(initialState, receivedSymbol);
    }
}
