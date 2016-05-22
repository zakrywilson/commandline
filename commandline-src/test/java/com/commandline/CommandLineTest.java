package test.java.com.commandline;

import org.junit.Assert;
import org.junit.Test;

import main.java.com.commandline.CommandLine;
import main.java.com.commandline.Option;

import java.util.ArrayList;

/**
 * Test {@link CommandLine}.
 *
 * @author Zach Wilson
 */
public class CommandLineTest {

    /**
     * Method: addOption(final Option option), getOptions().
     */
    @Test
    public void testOptions() {
        Option a = new Option("a");
        Option b = new Option("b");
        Option c = new Option("c");

        Option[] optionsArray = new Option[] { a, b, c };

        CommandLine commandline = new CommandLine();
        commandline.addOption(a);
        commandline.addOption(b);
        commandline.addOption(c);

        @SuppressWarnings("unchecked")
		ArrayList<Option> options = (ArrayList<Option>) commandline.getOptions();

        for (int i = 0; i < options.size(); ++i) {
            if (!options.get(i).equals(optionsArray[i])) {
                Assert.fail("Failed on testing options. Option '" + options.get(i) +
                        "' does not match expected Option '" + optionsArray[i] + "'.");
            }
        }

    }

    /**
     * Method: parse(final String[] args).
     */
    @Test
    public void testParse() {
        CommandLine commandline = new CommandLine();

        String[] args = new String[] { "-x", "X", "-y", "Y", "-z", "Z" };

        Option x = new Option("x");
        x.setExpectedArgumentCount(1);
        commandline.addOption(x);

        Option y = new Option("y");
        y.setExpectedArgumentCount(1);
        commandline.addOption(y);

        Option z = new Option("z");
        z.setExpectedArgumentCount(1);
        commandline.addOption(z);

        commandline.parse(args);

        if (!x.isFound()) {
            Assert.fail("Option 'x' not found.");
        }
        if (!y.isFound()) {
            Assert.fail("Option 'y' not found.");
        }
        if (!z.isFound()) {
            Assert.fail("Option 'z' not found.");
        }
    }

    /**
     * Method: getHelp(), createHelp(final String help), needHelp().
     */
    @Test
    public void testHelp() {
        String helpMessage = "This is the help message.";
        String[] args = new String[] { "--help" };

        CommandLine commandline = new CommandLine();
        commandline.createHelp(helpMessage);
        commandline.parse(args);

        if (!commandline.needHelp()) {
            Assert.fail("Failed on needing help option.");
        }
        if (!commandline.getHelp().equals(helpMessage)) {
            Assert.fail("Failed: help information does not match.");
        }
    }

}
