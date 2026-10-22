public class UrlNormalizer {
    public static String normalize(String url) {
        if (url == null) {
            throw new IllegalArgumentException("URL cannot be null.");
        }

        String s = url.trim().toLowerCase(java.util.Locale.ROOT);

        if (s.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be empty.");
        }

        int hash = s.indexOf('#');
        if (hash != -1) {
            s = s.substring(0, hash);
        }

        while (s.length() > 1 && s.endsWith("/")) {
            s = s.substring(0, s.length() - 1);
        }

        if (s.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be empty.");
        }

        return s;
    }
}
