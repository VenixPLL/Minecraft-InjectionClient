package me.dickmeister.client.mappings.proguard.utils;

import me.dickmeister.common.util.LogUtil;

public class ProguardMap {

    // Converts a proguard-formatted method signature into a Java formatted
    // method signature.
    public static String fromProguardSignature(String sig) {
        if (sig.startsWith("(")) {
            int end = sig.indexOf(')');
            if (end == -1) {
                LogUtil.err("Error parsing signature: " + sig);
            }

            StringBuilder converted = new StringBuilder();
            converted.append('(');
            if (end > 1) {
                for (String arg : sig.substring(1, end).split(",")) {
                    converted.append(fromProguardSignature(arg));
                }
            }
            converted.append(')');
            converted.append(fromProguardSignature(sig.substring(end + 1)));
            return converted.toString();
        } else if (sig.endsWith("[]")) {
            return "[" + fromProguardSignature(sig.substring(0, sig.length() - 2));
        } else if (sig.equals("boolean")) {
            return "Z";
        } else if (sig.equals("byte")) {
            return "B";
        } else if (sig.equals("char")) {
            return "C";
        } else if (sig.equals("short")) {
            return "S";
        } else if (sig.equals("int")) {
            return "I";
        } else if (sig.equals("long")) {
            return "J";
        } else if (sig.equals("float")) {
            return "F";
        } else if (sig.equals("double")) {
            return "D";
        } else if (sig.equals("void")) {
            return "V";
        } else {
            return "L" + sig.replace('.', '/') + ";";
        }
    }


}

