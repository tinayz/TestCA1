import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Field;
import java.util.Vector;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(JUnit4.class)
public class UserFriendsTest {
    private User user;
    private User ahmadreza;
    Vector<User> friends;

    @Before
    public void setup() throws Exception {
        ahmadreza = new User("ahr", "sy");
        this.user = new User("mreza", "kiani");
        friends = new Vector<User>();
        friends = getFriendsField();
    }

    @Test
    public void addFriendTest() {
        this.user.addFriend(ahmadreza);
        Assert.assertTrue(friends.contains(ahmadreza));
    }

    @Test
    public void removeCopiedFriendTest() { //This test fails because users are checked by memory address not firstname and surname
        User removedAhmadreza = new User("ahr", "sy");
        friends.add(ahmadreza);
        user.removeFriend(removedAhmadreza);
        Assert.assertTrue(!friends.contains(ahmadreza));
    }

    @Test
    public void removeNonFriendTest() {
        User Tina = new User("tina", "yz");
        friends.add(ahmadreza);
        Vector<User> friendsBefore = new Vector<User>(friends);
        user.removeFriend(Tina);
        Assert.assertTrue(friendsBefore.size() == friends.size());
        for (User friend : friends) {
            friendsBefore.contains(friend);
        }
    }

    @Test
    public void isFriendHappyTest() {
        User tina = new User("tina", "yz");
        friends.add(ahmadreza);
        assertTrue(user.isFriendsWith(ahmadreza));
        assertFalse(user.isFriendsWith(tina));
    }

    @Test
    public void isFriendBadTest() {
        User ahmadreza = new User("ahr", "sy");
        User sameAhmadreza = new User("ahr", "sy");
        friends.add(ahmadreza);
        Assert.assertTrue(user.isFriendsWith(sameAhmadreza));
    }

    private Vector<User> getFriendsField() throws Exception {
        Field field = this.user.getClass().getDeclaredField("friends");
        field.setAccessible(true);
        return (Vector<User>) field.get(this.user);
    }
}
