/**
 * @author anton.safonov
 */
public class PatternMatcher {
    public static void main(String[] args) {
        check("a", ".");
        check("ab", "..");
        check("aabbcd", "a*b*..");
        check("aabbcd", "a*b*...");
        check("aabbcd", "a*b*....");
        check("aabbcd", "a*b*.....");
        check("aabbcd", "a*b*......");
        check("aabbcd", "a*ab?bcde?f*");
        check("aabbcd", "c?aab?bcde?f*");
        check("aabbcd", "c?.ab?bcde?f*");
        check("aabbcd", ".?.ab?bcde?f*");

        System.out.println();

        //TODO: Fix the case
        check("abc", ".*");
        check("aabbcd", "a*b*.......");
        check("aabbcd", "c.ab?bcde?f*");
        check("aabbcd", ".ab?bcdde?f?");
    }

    private static void check(String text, String pattern) {
        boolean match = match(text, pattern);
        System.out.println((match ? "+ " : "- ") + text + " <> " + pattern);
    }

    public static boolean match(String text, String pattern) {
        if (text.isEmpty() && pattern.isEmpty()) {
            return true;
        }
        if (pattern.isEmpty()) {
            return text.isEmpty();
        }
        char c = pattern.charAt(0);

        if (!text.isEmpty() && (text.charAt(0) == c || c == '.')) {
            if (pattern.length() == 1) {
                return text.length() == 1;
            }
            if (pattern.charAt(1) == '*') {
                return match(text, pattern.substring(2)) || match(text.substring(1), pattern);
            } else if (pattern.charAt(1) == '?') {
                return match(text, pattern.substring(2)) || match(text.substring(1), pattern.substring(2));
            } else {
                return match(text.substring(1), pattern.substring(1));
            }

        } else {
            if (pattern.length() == 1) {
                return false;
            }
            if (pattern.charAt(1) == '*' || pattern.charAt(1) == '?') {
                return match(text, pattern.substring(2));
            }
        }

        return false;
    }
}
