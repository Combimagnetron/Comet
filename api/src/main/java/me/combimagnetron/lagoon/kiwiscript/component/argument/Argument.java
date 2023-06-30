package me.combimagnetron.lagoon.kiwiscript.component.argument;

public class Argument {
    private final ExpectedArgument expectedArgument;
    private final ArgumentType<?>[] argumentTypes;

    Argument(ExpectedArgument expectedArgument, ArgumentType<?>... argumentTypes) {
        this.expectedArgument = expectedArgument;
        this.argumentTypes = argumentTypes;
    }

    public ArgumentType<?>[] argumentTypes() {
        return argumentTypes;
    }

    public ExpectedArgument expectedArgument() {
        return expectedArgument;
    }

}
