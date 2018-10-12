import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Field;
import java.util.Vector;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(JUnit4.class)
public class UserFriendsTest {
    private User user;

    @Before
    public void setup() {
        this.user = new User("mreza", "kiani");
    }

    @Test
    public void addFriendTest() {
        User ahmadreza = new User("ahr", "sy");
        this.user.addFriend(ahmadreza);
        Vector<User> friends = new Vector<User>();
        try {
            friends = getFriendsField();
        } catch (Exception e) {
            System.out.println(e);
            fail();
        }
        Assert.assertTrue(friends.contains(ahmadreza));
    }
    @Test
    public void removeCopiedFriendTest(){ //This test fails because users are checked by memory address not firstname and surname
        Vector<User> friends = new Vector<User>();
        try {
            friends = getFriendsField();
        } catch (Exception e) {
            System.out.println(e);
            fail();
        }
        User ahmadreza = new User("ahr", "sy");
        User removedAhmadreza = new User("ahr", "sy");
        friends.add(ahmadreza);
        user.removeFriend(removedAhmadreza);
        Assert.assertTrue(!friends.contains(ahmadreza));
    }
    @Test
    public void removeNonFriendTest(){
        Vector<User> friends = new Vector<User>();
        try {
            friends = getFriendsField();
        } catch (Exception e) {
            System.out.println(e);
            fail();
        }
        User ahmadreza = new User("ahr", "sy");
        User Tina = new User("tina", "yz");
        friends.add(ahmadreza);
        Vector<User> friendsBefore = new Vector<User>(friends);
        user.removeFriend(Tina);
        Assert.assertTrue(friendsBefore.size() == friends.size());
        for(User friend: friends){
            friendsBefore.contains(friend);
        }
    }
    @Test
    public void isFriendHappyTest(){
        Vector<User> friends = new Vector<User>();
        try {
            friends = getFriendsField();
        } catch (Exception e) {
            System.out.println(e);
            fail();
        }
        User ahmadreza = new User("ahr", "sy");
        User tina = new User("tina","yz");
        friends.add(ahmadreza);
        assertTrue(user.isFriendsWith(ahmadreza));
        assertFalse(user.isFriendsWith(tina));
    }
    @Test
    public void isFriendBadTest(){
        Vector<User> friends = new Vector<User>();
        try {
            friends = getFriendsField();
        } catch (Exception e) {
            System.out.println(e);
            fail();
        }
        User ahmadreza = new User("ahr", "sy");
        User sameAhmadreza = new User("ahr","sy");
        friends.add(ahmadreza);
        Assert.assertTrue(user.isFriendsWith(sameAhmadreza));
    }
    private Vector<User> getFriendsField() throws Exception{
        Field field = this.user.getClass().getDeclaredField("friends");
        field.setAccessible(true);
        return (Vector<User>) field.get(this.user);
    }
}
