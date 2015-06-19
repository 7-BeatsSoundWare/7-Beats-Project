package br.com.sevenbeats.utils.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by diogojayme on 6/16/15.
 */
public class FormValidateUtils {

    public static boolean containsSpecial(String sequence){
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(sequence);
        return  m.find();
    }

    public static boolean matchStrings(String string1, String string2){
        return string1.equals(string2);
    }

    public static boolean startsWithNumber(String sequence){
        return Character.isDigit(sequence.charAt(0));
    }

}
