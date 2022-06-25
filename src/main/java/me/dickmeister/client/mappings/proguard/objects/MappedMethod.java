package me.dickmeister.client.mappings.proguard.objects;

public class MappedMethod {

    private final String className; //Original
    private final int firstLineNumber; //Original
    private final int lastLineNumber; //Original

    private final String methodReturnType; //No Changes
    private final String methodName; //Original
    private final String methodArguments; //No Changes

    private final String newClassName; //Obfuscated
    private final int newFirstLineNumber; //Obfuscated
    private final int newLastLineNumber; //Obfuscated
    private final String newMethodName; //Obfuscated

    public MappedMethod(String className, int firstLineNumber, int lastLineNumber, String methodReturnType, String methodName, String methodArguments, String newClassName, int newFirstLineNumber, int newLastLineNumber, String newMethodName) {
        this.className = className;
        this.firstLineNumber = firstLineNumber;
        this.lastLineNumber = lastLineNumber;
        this.methodReturnType = methodReturnType;
        this.methodName = methodName;
        this.methodArguments = methodArguments;
        this.newClassName = newClassName;
        this.newFirstLineNumber = newFirstLineNumber;
        this.newLastLineNumber = newLastLineNumber;
        this.newMethodName = newMethodName;
    }

    public int getFirstLineNumber() {
        return firstLineNumber;
    }

    public int getLastLineNumber() {
        return lastLineNumber;
    }

    public int getNewFirstLineNumber() {
        return newFirstLineNumber;
    }

    public int getNewLastLineNumber() {
        return newLastLineNumber;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodArguments() {
        return methodArguments;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getMethodReturnType() {
        return methodReturnType;
    }

    public String getNewClassName() {
        return newClassName;
    }

    public String getNewMethodName() {
        return newMethodName;
    }
}
