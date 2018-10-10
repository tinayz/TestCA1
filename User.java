import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bornarz on 9/28/18.
 */
public class User {
    private String firstname;
    private String surname;
    private String email;
    private Vector<User> friends;

    public User(String firstname, String surname) {
        this.firstname = firstname;
        this.surname = surname;
        this.email = User.createEmailID(firstname, surname);
        if (!User.isEmailValid(this.email)) {
            throw new IllegalArgumentException();
        }
        this.friends = new Vector<>();
    }

    private static String createEmailID(String firstpart, String secondpart) {
        String subfirst = firstpart.substring(1);
        return subfirst + "." + secondpart + "@test.ut.ac.ir";
    }

    private static boolean isEmailValid(String email) {
        String regex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@(([a-zA-Z\\-0-9]+\\.)*[a-zA-Z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(email);
        return m.matches();
    }

    public void addFriend(User newfriend) {
        friends.add(newfriend);
    }

    public void removeFriend(User removedfriend) {
        friends.remove(removedfriend);
    }

    public boolean isFriendsWith(User friend) {
        return friends.indexOf(friend) != -1;
    }
}
