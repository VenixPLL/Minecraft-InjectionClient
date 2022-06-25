package me.dickmeister.client.mappings;

import me.dickmeister.client.mappings.proguard.objects.MappedClass;
import me.dickmeister.client.mappings.proguard.objects.MappedField;

import java.util.HashMap;
import java.util.Map;

public class MappingManager {

    private final Map<String, MappedClass> mappedClasses = new HashMap<>();

    public String translateClassName(String className){
        return mappedClasses.get(className).getNewClassName();
    }

    public String translateMethodName(String className,String methodName){
        return mappedClasses.get(className).getMappedMethodMap().get(methodName).getNewMethodName();
    }

    public String translateFieldName(String className,String fieldName){
        var mappedClass = mappedClasses.get(className);
        var field = mappedClass.getMappedFieldMap().get(fieldName);
        return field.getNewFieldName();
    }

    public String getFieldName(String className,String fieldName){
        var mappedClass = mappedClasses.get(className);
        var field = mappedClass.getMappedFieldMap().values().stream().filter(val -> val.getNewFieldName().equals(fieldName)).findFirst();
        return field.map(MappedField::getFieldName).orElse(null);
    }

    public Class<?> getClass(String className) throws ClassNotFoundException{
        return Class.forName(translateClassName(className));
    }

    public Map<String, MappedClass> getMappedClasses() {
        return mappedClasses;
    }
}
