import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import static junit.framework.TestCase.fail;

@RunWith(Parameterized.class)
public class IsEmailValidTest {

    private String email;
    private Method method;
    private User user;
    private boolean expected;

    public IsEmailValidTest(String email, boolean expected) {
        this.email = email;
        this.expected = expected;
        Method method;
        try {
            method = User.class.getDeclaredMethod("isEmailValid", String.class);
            method.setAccessible(true);
            this.method = method;
        } catch (NoSuchMethodException e) {
        }
        this.user = new User("mreza", "kiani");
    }

    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(
                new Object[][]{
                        {"a@gmail.com", true},
                        {"c@email.com", true},
                        {null, false},
                        {"", false},
                        {"#@@@testi", false}, // multple @ s
                        {"#@-.#", false}, // no word
                        {"123@123.123", false}, // just numbers
                        {"test@", false},
                        {" t  est  @ gmail .com ", false}, //spacing
                        {"", false},
                        {"esm.famil@x.y.ir", true}, //multi dots
                        {"-esm-@gmail.com", false}, //dash characters
                        {"*+/=?^_`{|}~-@gmail.com", false}, //illegal characters
                        {"name@com", false},
                        {"name@test.a", false}
                }
        );
    }

    @Test
    public void emailValidationTest() {
        try {
            boolean result = (Boolean) method.invoke(this.user, this.email);
            Assert.assertTrue("emailValidationTest", result == expected);
        } catch (Exception e) {
            if (email == null) {
                Assert.assertTrue("emailValidationTest null pointer", e.getCause() instanceof NullPointerException);
            } else
                fail();
        }
    }
}
