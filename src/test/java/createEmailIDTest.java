import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

@RunWith(Theories.class)
public class createEmailIDTest {
    private Method method;
    private User user;

    public createEmailIDTest() throws Exception {
        method = User.class.getDeclaredMethod("createEmailID", String.class, String.class);
        method.setAccessible(true);
        this.user = new User("mreza", "kiani");
    }

    @DataPoints
    public static String[] names() {
        return new String[]{
                "",
                "a",
                "!",
                "1a",
                "a1",
                "r21s",
                "A!",
                "..long.long.long.long.long.long.long",
                null};
    }

    @Theory
    public void testCreateEmailIDHappyTest(String firstPart, String secondPart) {
        assumeNotNull(firstPart, secondPart);
        assumeFalse(firstPart.equals("") || secondPart.equals(""));
        System.out.println(String.format("Testing with %s and %s", firstPart, secondPart));
        String result = null;
        try {
            result = callTestedMethod(firstPart, secondPart);
        } catch (Exception e) {
            fail();
        }
        Assert.assertTrue(result.endsWith("@test.ut.ac.ir"));
        Assert.assertTrue(result.contains("." + secondPart));
        Assert.assertTrue(result.contains(firstPart.substring(1)));
    }

    @Theory
    public void testCreateEmailIDBadTest(String firstPart, String secondPart) {
        assumeTrue(firstPart == null || secondPart == null || isBlank(firstPart) || isBlank(secondPart));
        System.out.println(String.format("Testing with %s and %s", firstPart, secondPart));
        try {
            this.callTestedMethod(firstPart, secondPart);
        } catch (Exception e) {
            if (firstPart == null)
                assertTrue(e.getCause() instanceof NullPointerException);
            else
                assertTrue(e.getCause() instanceof StringIndexOutOfBoundsException);
        }
    }
    public String callTestedMethod(String firstPart, String secondPart) throws Exception {
        return (String) method.invoke(this.user, firstPart, secondPart);
    }
    private boolean isBlank(String string){
        return string.equals("");
    }
}
