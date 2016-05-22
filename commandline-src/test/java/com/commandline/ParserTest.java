package test.java.com.commandline;

import org.junit.Assert;
import org.junit.Test;

import main.java.com.commandline.Option;
import main.java.com.commandline.Parser;

import java.util.ArrayList;

/**
 * Test {@link Parser}.
 *
 * @author Zach Wilson
 */
public class ParserTest {

    /**
     * Method: parse(), getAllArguments().
     */
    @Test
    public void testParse() {

        // No args should  be found

        String[] args = new String[] { "-a", "-b", "-c" };
        ArrayList<Option> options = new ArrayList<>();

        Option a = new Option("a");
        options.add(a);

        Option b = new Option("b");
        options.add(b);

        Option c = new Option("c");
        options.add(c);

        Parser p1 = new Parser(args, options);
        p1.parse();
        ArrayList<String> parserArgs = p1.getArguments();

        if (!parserArgs.isEmpty()) {
            Assert.fail("Failed on empty arguments.");
        }

        // Args should be found

        args = new String[] { "-x", "X", "-y", "Y", "-z", "Z" };
        String[] expectedArgs = new String[] { "X", "Y", "Z" };
        options.clear();

        Option x = new Option("x");
        x.setExpectedArgumentCount(1);
        options.add(x);

        Option y = new Option("y");
        y.setExpectedArgumentCount(1);
        options.add(y);

        Option z = new Option("z");
        z.setExpectedArgumentCount(1);
        options.add(z);

        Parser p2 = new Parser(args, options);
        p2.parse();
        parserArgs = p2.getArguments();

        if (parserArgs.isEmpty()) {
            Assert.fail("Failed on non-empty arguments.");
        }

        for (int i = 0; i < parserArgs.size(); ++i) {
            if (!parserArgs.get(i).equals(expectedArgs[i])) {
                Assert.fail("Argument '" + parserArgs.get(i) + "' does not match expected argument '" + expectedArgs[i] + "'.");
            }
        }
    }

    /**
     * Method: parse().
     */
    @Test (expected=IllegalArgumentException.class)
    public void testParseBadInput() {
        String[] args = new String[] { "-x", "X1", "X2", "-y", "-z", "Z" };
        ArrayList<Option> options = new ArrayList<>();

        Option x = new Option("x");
        x.setExpectedArgumentCount(2);
        options.add(x);

        Option y = new Option("y");
        y.setExpectedArgumentCount(1);
        options.add(y);

        Option z = new Option("z");
        z.setExpectedArgumentCount(1);
        options.add(z);

        Parser parser = new Parser(args, options);
        parser.parse();
    }

    /**
     * Method: getOptions().
     */
    @Test
    public void testGetOptions() {
        Option a = new Option("a");
        Option b = new Option("b");
        Option c = new Option("c");

        String[] args = new String[] { "a", "b", "c" };
        ArrayList<Option> options = new ArrayList<>();
        options.add(a);
        options.add(b);
        options.add(c);

        Parser parser = new Parser(args, options);

        ArrayList<Option> newOptions = parser.getOptions();
        for (int i = 0; i < newOptions.size(); ++i) {
            if (!newOptions.get(i).equals(options.get(i))) {
                Assert.fail("Failed on getting options.");
            }
        }

    }

}
