package me.matata.matamenu.general.utils;

import com.google.common.collect.Lists;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author 坏黑
 */
public class Strings {


    public static String copy(String text, int count) {
        return IntStream.range(0, count).mapToObj(i -> text).collect(Collectors.joining());
    }

    public static boolean nonBlack(String var) {
        return !isBlank(var);
    }

    public static boolean isBlank(String var) {
        return var == null || var.trim().isEmpty();
    }

    public static boolean nonEmpty(CharSequence var) {
        return !isEmpty(var);
    }

    public static boolean isEmpty(CharSequence var) {
        return var == null || var.length() == 0;
    }

    public static String replaceWithOrder(String template, String... args) {
        return replaceWithOrder(template, Lists.newArrayList(args).toArray());
    }

    /**
     * 优化过的 String#replace，比默认快了大概 5 倍
     *
     * @param template 模板替换文件
     * @param args     替换的参数
     * @return 替换好的字符串
     */
    public static String replaceWithOrder(String template, Object... args) {
        if (args.length == 0 || template.length() == 0) {
            return template;
        }
        char[] arr = template.toCharArray();
        StringBuilder stringBuilder = new StringBuilder(template.length());
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == '{' && Character.isDigit(arr[Math.min(i + 1, arr.length - 1)])
                    && arr[Math.min(i + 1, arr.length - 1)] - '0' < args.length
                    && arr[Math.min(i + 2, arr.length - 1)] == '}') {
                stringBuilder.append(args[arr[i + 1] - '0']);
                i += 2;
            } else {
                stringBuilder.append(arr[i]);
            }
        }
        return stringBuilder.toString();
    }

    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    public static double similarDegree(String strA, String strB){
        String newStrA = removeSign(max(strA, strB));
        String newStrB = removeSign(min(strA, strB));
        try {
            int temp = Math.max(newStrA.length(), newStrB.length());
            int temp2 = longestCommonSubstring(newStrA, newStrB).length();
            return temp2 * 1.0 / temp;
        } catch (Exception ignored) {
            return 0;
        }
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    private static String max(String strA, String strB) {
        return strA.length() >= strB.length() ? strA : strB;
    }

    private static String min(String strA, String strB) {
        return strA.length() < strB.length() ? strA : strB;
    }

    private static String removeSign(String str) {
        StringBuilder builder = new StringBuilder();
        for (char item : str.toCharArray()) {
            if (charReg(item)){
                builder.append(item);
            }
        }
        return builder.toString();
    }

    private static boolean charReg(char charValue) {
        return (charValue >= 0x4E00 && charValue <= 0X9FA5) || (charValue >= 'a' && charValue <= 'z') || (charValue >= 'A' && charValue <= 'Z')  || (charValue >= '0' && charValue <= '9');
    }

    private static String longestCommonSubstring(String strA, String strB) {
        char[] chars_strA = strA.toCharArray();
        char[] chars_strB = strB.toCharArray();
        int m = chars_strA.length;
        int n = chars_strB.length;

        int[][] matrix = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (chars_strA[i - 1] == chars_strB[j - 1]) {
                    matrix[i][j] = matrix[i - 1][j - 1] + 1;
                } else {
                    matrix[i][j] = Math.max(matrix[i][j - 1], matrix[i - 1][j]);
                }
            }
        }

        char[] result = new char[matrix[m][n]];
        int currentIndex = result.length - 1;
        while (matrix[m][n] != 0) {
            if (matrix[n] == matrix[n - 1]) {
                n--;
            } else if (matrix[m][n] == matrix[m - 1][n]) {
                m--;
            } else {
                result[currentIndex] = chars_strA[m - 1];
                currentIndex--;
                n--;
                m--;
            }
        }
        return new String(result);
    }

    public static String replaceFromMap(String string, Map<String, String> replacements) {
        StringBuilder sb = new StringBuilder(string);
        int size = string.length();
        for (Entry<String, String> entry : replacements.entrySet()) {
            if (size == 0) {
                break;
            }
            String key = entry.getKey();
            String value = entry.getValue();
            int start = sb.indexOf(key, 0);
            while (start > -1) {
                int end = start + key.length();
                int nextSearchStart = start + value.length();
                sb.replace(start, end, value);
                size -= end - start;
                start = sb.indexOf(key, nextSearchStart);
            }
        }
        return sb.toString();
    }

    public static int intersection(Set<String> options, String[] toCheck) {
        int count = 0;
        for (String check : toCheck) {
            if (options.contains(check)) {
                count++;
            }
        }
        return count;
    }

    public static String getString(Object obj) {
        if (obj == null) {
            return "null";
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj.getClass().isArray()) {
            StringBuilder result = new StringBuilder();
            String prefix = "";

            for (int i = 0; i < Array.getLength(obj); i++) {
                result.append(prefix).append(getString(Array.get(obj, i)));
                prefix = ",";
            }
            return "( " + result + " )";
        } else if (obj instanceof Collection<?>) {
            StringBuilder result = new StringBuilder();
            String prefix = "";
            for (Object element : (Collection<?>) obj) {
                result.append(prefix).append(getString(element));
                prefix = ",";
            }
            return "[ " + result + " ]";
        } else {
            return obj.toString();
        }
    }

    public static String replaceFirst(char c, String s) {
        if (s == null) {
            return "";
        }
        if (s.isEmpty()) {
            return s;
        }
        char[] chars = s.toCharArray();
        char[] newChars = new char[chars.length];
        int used = 0;
        boolean found = false;
        for (char cc : chars) {
            if (!found && (c == cc)) {
                found = true;
            } else {
                newChars[used++] = cc;
            }
        }
        if (found) {
            chars = new char[newChars.length - 1];
            System.arraycopy(newChars, 0, chars, 0, chars.length);
            return String.valueOf(chars);
        }
        return s;
    }

    public static String replaceAll(String string, Object... pairs) {
        StringBuilder sb = new StringBuilder(string);
        for (int i = 0; i < pairs.length; i += 2) {
            String key = pairs[i] + "";
            String value = pairs[i + 1] + "";
            int start = sb.indexOf(key, 0);
            while (start > -1) {
                int end = start + key.length();
                int nextSearchStart = start + value.length();
                sb.replace(start, end, value);
                start = sb.indexOf(key, nextSearchStart);
            }
        }
        return sb.toString();
    }

    public static boolean isAlphanumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if ((c < 0x30) || ((c >= 0x3a) && (c <= 0x40)) || ((c > 0x5a) && (c <= 0x60)) || (c > 0x7a)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlphanumericUnd(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c < 0x30 || (c >= 0x3a) && (c <= 0x40) || (c > 0x5a) && (c <= 0x60) || (c > 0x7a)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlpha(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if ((c <= 0x40) || ((c > 0x5a) && (c <= 0x60)) || (c > 0x7a)) {
                return false;
            }
        }
        return true;
    }

    public static String join(Collection<?> collection, String delimiter) {
        return join(collection.toArray(), delimiter);
    }

    public static String joinOrdered(Collection<?> collection, String delimiter) {
        Object[] array = collection.toArray();
        Arrays.sort(array, Comparator.comparingInt(Object::hashCode));
        return join(array, delimiter);
    }

    public static String join(Collection<?> collection, char delimiter) {
        return join(collection.toArray(), delimiter + "");
    }

    public static boolean isAsciiPrintable(char c) {
        return (c >= ' ') && (c < '');
    }

    public static boolean isAsciiPrintable(String s) {
        for (char c : s.toCharArray()) {
            if (!isAsciiPrintable(c)) {
                return false;
            }
        }
        return true;
    }

    public static int getLevenshteinDistance(String s, String t) {
        int n = s.length();
        int m = t.length();
        if (n == 0) {
            return m;
        } else if (m == 0) {
            return n;
        }
        if (n > m) {
            String tmp = s;
            s = t;
            t = tmp;
            n = m;
            m = t.length();
        }
        int[] p = new int[n + 1];
        int[] d = new int[n + 1];
        int i;
        for (i = 0; i <= n; i++) {
            p[i] = i;
        }
        for (int j = 1; j <= m; j++) {
            char t_j = t.charAt(j - 1);
            d[0] = j;

            for (i = 1; i <= n; i++) {
                int cost = s.charAt(i - 1) == t_j ? 0 : 1;
                d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
            }
            int[] _d = p;
            p = d;
            d = _d;
        }
        return p[n];
    }

    public static String join(Object[] array, String delimiter) {
        StringBuilder result = new StringBuilder();
        for (int i = 0, j = array.length; i < j; i++) {
            if (i > 0) {
                result.append(delimiter);
            }
            result.append(array[i]);
        }
        return result.toString();
    }

    public static String join(int[] array, String delimiter) {
        Integer[] wrapped = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            wrapped[i] = array[i];
        }
        return join(wrapped, delimiter);
    }

    public static boolean isEqualToAny(String a, String... args) {
        for (String arg : args) {
            if (Strings.isEqual(a, arg)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEqualIgnoreCaseToAny(String a, String... args) {
        for (String arg : args) {
            if (Strings.isEqualIgnoreCase(a, arg)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEqual(String a, String b) {
        return a.equals(b);
    }

    public static boolean isEqualIgnoreCase(String a, String b) {
        return a.equals(b) || b != null && a.length() == b.length() && a.equalsIgnoreCase(b);
    }

    public static String repeat(String s, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(s);
        }
        return sb.toString();
    }

    public <T> Collection<T> match(Collection<T> col, String startsWith) {
        if (col == null) {
            return null;
        }
        startsWith = startsWith.toLowerCase();
        Iterator iterator = col.iterator();
        while (iterator.hasNext()) {
            Object item = iterator.next();
            if (item == null || !item.toString().toLowerCase().startsWith(startsWith)) {
                iterator.remove();
            }
        }
        return col;
    }

    public static boolean contains(String name, char c) {
        for (char current : name.toCharArray()) {
            if (c == current) {
                return true;
            }
        }
        return false;
    }
}
