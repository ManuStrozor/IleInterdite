package game;

public class Utils {

    static String getImageFromName(String s) {
        return toCamelCase(s).replace("'", "").replace("é", "e");
    }

    static String toCamelCase(String s){
        String[] parts = s.split(" ");
        StringBuilder cc = new StringBuilder();
        for (String part : parts) cc.append(part.substring(0, 1).toUpperCase()).append(part.substring(1).toLowerCase());
        return cc.toString();
    }
}
