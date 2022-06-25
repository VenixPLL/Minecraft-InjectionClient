package me.dickmeister.client.mappings.proguard.objects;

public class MappedField {

    private final String className; //Original
    private final String fieldName; //Original
    private final String fieldType; //No changes
    private final String newClassName; //Obfuscated
    private final String newFieldName; //Obfuscated

    public MappedField(String className, String fieldType, String fieldName, String newClassName, String newFieldName) {
        this.className = className;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.newClassName = newClassName;
        this.newFieldName = newFieldName;
    }

    public String getNewClassName() {
        return newClassName;
    }

    public String getClassName() {
        return className;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public String getNewFieldName() {
        return newFieldName;
    }
}