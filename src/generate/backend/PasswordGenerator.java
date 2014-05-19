package generate.backend;

/**
 * Created by sobolev-a on 19.05.2014.
 * algorytm using from http://syntx.io/how-to-generate-a-random-string-in-java/
 *
 */
public class PasswordGenerator {

    public static String generateRandomString(int length) {
        StringBuffer buffer = new StringBuffer();
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        int charactersLength = characters.length();

        for (int i = 0; i < length; i++) {
            double index = Math.random() * charactersLength;
            buffer.append(characters.charAt((int) index));
        }
        return buffer.toString();
    }
}
