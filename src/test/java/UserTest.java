import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Field;

@RunWith(JUnit4.class)
public class UserTest {
    @Test
    public void constructorTest() throws Exception{
        String firstname = "shahryar";
        String surname = "soltanpour";
        User sh = new User(firstname, surname);
        Field firstnameField = User.class.getDeclaredField("firstname");
        firstnameField.setAccessible(true);
        Assert.assertEquals((firstnameField.get(sh)), firstname);

        Field surnameField = User.class.getDeclaredField("surname");
        surnameField.setAccessible(true);
        Assert.assertEquals(surnameField.get(sh), surname);

    }
}
