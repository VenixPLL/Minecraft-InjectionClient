package me.dickmeister.client.mappings.proguard.processor.impl;

import me.dickmeister.client.mappings.MappingManager;
import me.dickmeister.client.mappings.proguard.objects.MappedClass;
import me.dickmeister.client.mappings.proguard.objects.MappedField;
import me.dickmeister.client.mappings.proguard.objects.MappedMethod;
import me.dickmeister.client.mappings.proguard.processor.MappingProcessor;
import me.dickmeister.client.mappings.proguard.utils.ProguardMap;
import me.dickmeister.common.util.LogUtil;

import java.util.Objects;

public class ClientMappingProcessor implements MappingProcessor {

    private final MappingManager mappingManager;

    public ClientMappingProcessor(MappingManager mappingManager) {
        this.mappingManager = mappingManager;
    }

    @Override
    public boolean processClassMapping(String className, String newClassName) {
        if(mappingManager.getMappedClasses().containsKey(className)) return true;

        mappingManager.getMappedClasses().put(className,new MappedClass(className,newClassName));
        return true;
    }

    @Override
    public void processFieldMapping(String className, String fieldType, String fieldName, String newClassName, String newFieldName) {
        var mappedClass = mappingManager.getMappedClasses().get(className);
        if(Objects.isNull(mappedClass)){
            processClassMapping(className,newClassName);
            processFieldMapping(className,fieldType,fieldName,newClassName,newFieldName);
            return;
        }

        var fieldMap = mappedClass.getMappedFieldMap();
        if(fieldMap.containsKey(fieldName)){
            LogUtil.err("Found duplicate field entry in class " + className + " fieldName: " + fieldName);
            return;
        }
        fieldMap.put(fieldName,new MappedField(className,fieldType,fieldName,newClassName,newFieldName));
    }

    @Override
    public void processMethodMapping(String className, int firstLineNumber, int lastLineNumber, String methodReturnType, String methodName, String methodArguments, String newClassName, int newFirstLineNumber, int newLastLineNumber, String newMethodName) {
        var mappedClass = mappingManager.getMappedClasses().get(className);
        if(Objects.isNull(mappedClass)){
            processClassMapping(className,newClassName);
            processMethodMapping(className,firstLineNumber,lastLineNumber,methodReturnType,methodName,methodArguments,newClassName,newFirstLineNumber,newLastLineNumber,newMethodName);
            return;
        }

        var methodMap = mappedClass.getMappedMethodMap();
        var originalDesc = methodName + ProguardMap.fromProguardSignature('(' + methodArguments + ')' + methodReturnType);
        if (methodMap.containsKey(originalDesc)) {
            LogUtil.err("Found duplicate method entry in class " + className + " methodName: " + methodName);
            return;
        }
        methodMap.put(originalDesc,new MappedMethod(className,firstLineNumber,lastLineNumber,methodReturnType,methodName,methodArguments,newClassName,newFirstLineNumber,newLastLineNumber,newMethodName));


    }
}
