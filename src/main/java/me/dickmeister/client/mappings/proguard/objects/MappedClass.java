package me.dickmeister.client.mappings.proguard.objects;

import java.util.HashMap;
import java.util.Map;

public class MappedClass {

    private final String className; //Original
    private final String newClassName; //Obfuscated
    private final Map<String,MappedField> mappedFieldMap = new HashMap<>();
    private final Map<String,MappedMethod> mappedMethodMap = new HashMap<>();

    public MappedClass(String className, String newClassName) {
        this.className = className;
        this.newClassName = newClassName;
    }

    public String getClassName() {
        return className;
    }

    public String getNewClassName() {
        return newClassName;
    }

    public Map<String, MappedField> getMappedFieldMap() {
        return mappedFieldMap;
    }

    public Map<String, MappedMethod> getMappedMethodMap() {
        return mappedMethodMap;
    }
}