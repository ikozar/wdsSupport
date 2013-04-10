package ru.ki;

import org.apache.commons.lang3.StringUtils;

public class MyStringUtils {
    private MyStringUtils() {
    }

    public static String[] splitOnLastSeparator(String name, int searchChar) {
        return splitOnLastSeparator(name, searchChar, null);
    }

    public static String[] splitOnLastSeparator(String name, int searchChar, String prefixDefault) {
        int index = StringUtils.lastIndexOf(name, searchChar);
        if (index >= 0) {
            String[] names = new String[2];
            names[0] = name.substring(0, index);
            names[1] = name.substring(index+1);
            return names;
        }
        if (prefixDefault != null) {
            String[] names = new String[2];
            names[0] = prefixDefault;
            names[1] = name;
        }
        return null;
    }

    public static enum RootAnalizeType {EQUALS, ROOT, START, DIFFERENT};

    public static RootAnalizeType rootAnalize(CharSequence root, CharSequence str, CharSequence splitters) {
        if (root == str) {
            return RootAnalizeType.EQUALS;
        }
        if (root == null || str == null || root.length() > str.length()) {
            return RootAnalizeType.DIFFERENT;
        }
        int i;
        for (i = 0; i < root.length(); ++i) {
            if (root.charAt(i) != str.charAt(i)) {
                return RootAnalizeType.DIFFERENT;
            }
        }
        if (i == str.length()) {
            return RootAnalizeType.EQUALS;
        }
        if (StringUtils.indexOf(splitters, str.charAt(i)) >= 0) {
            return RootAnalizeType.ROOT;
        }
        return RootAnalizeType.START;
    }


}
