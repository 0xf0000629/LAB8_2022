import java.util.Locale;
import java.util.ResourceBundle;

public class Localit {
    private static ResourceBundle bundleEN = null;
    private static ResourceBundle bundleRU = null;
    private static ResourceBundle bundleES = null;
    private static ResourceBundle bundleIT = null;
    private static ResourceBundle bundleSP = null;
    private static String lang = "en";
    public static void load() {
        bundleEN = ResourceBundle.getBundle("Apptext", new Locale("en", "US"));
        bundleRU = ResourceBundle.getBundle("Apptext", new Locale("ru", "RU"));
        bundleES = ResourceBundle.getBundle("Apptext", new Locale("et", "EE"));
        bundleIT = ResourceBundle.getBundle("Apptext", new Locale("it", "IT"));
        bundleSP = ResourceBundle.getBundle("Apptext", new Locale("es", "NL"));
    }
    public static void setLang(String s){lang = s;}
    public static String l(String s) {
        s = s.replace(" ", "");
        String last = "";
        if (s.charAt(s.length()-1) == ':') {last = ":"; s=s.substring(0, s.length()-1);}
        if (s.charAt(s.length()-1) == '!') {last = "!"; s=s.substring(0, s.length()-1);}
        try {
            switch (lang) {
                case "en":
                    return bundleEN.getString(s)+last;
                case "ru":
                    return bundleRU.getString(s)+last;
                case "es":
                    return bundleES.getString(s)+last;
                case "it":
                    return bundleIT.getString(s)+last;
                case "sp":
                    return bundleSP.getString(s)+last;
                default:
                    return s+last;
            }
        }
        catch (Exception e){
            return s+last;
        }
    }
    public static String d(String s, boolean r){
        if (r){
            switch (lang){
                case "en":
                    return s.replaceAll("(\\d*)\\/(\\d*)\\/(\\d*)","$2\\/$1\\/$3");
                case "ru":
                    return s.replaceAll("(\\d*)\\/(\\d*)\\/(\\d*)","$1\\/$2\\/$3");
                case "es":
                    return s.replaceAll("(\\d*)\\/(\\d*)\\/(\\d*)","$1\\/$2\\/$3");
                case "it":
                    return s.replaceAll("(\\d*)\\/(\\d*)\\/(\\d*)","$1\\/$2\\/$3");
                case "sp":
                    return s.replaceAll("(\\d*)\\/(\\d*)\\/(\\d*)","$1\\/$2\\/$3");
            }
        }
        else {
            switch (lang) {
                case "en":
                    return s.replaceAll("(\\d*)-(\\d*)-(\\d*)", "$2\\/$3\\/$1");
                case "ru":
                    return s.replaceAll("(\\d*)-(\\d*)-(\\d*)", "$3\\/$2\\/$1");
                case "es":
                    return s.replaceAll("(\\d*)-(\\d*)-(\\d*)", "$3\\/$2\\/$1");
                case "it":
                    return s.replaceAll("(\\d*)-(\\d*)-(\\d*)", "$3\\/$2\\/$1");
                case "sp":
                    return s.replaceAll("(\\d*)-(\\d*)-(\\d*)", "$3\\/$2\\/$1");
            }
        }
        return s;
    }
    public static String n(double g) {
        if (lang == "ru") return String.valueOf(g).replace('.', ',');
        else return String.valueOf(g);
    }
}
