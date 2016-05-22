package main.java.com.commandline;

import java.util.ArrayList;

/**
 * Allows for specification and parsing of command line arguments.
 *
 * @author Zach Wilson
 */
public class CommandLine {

    /** Optional command line arguments. */
    private ArrayList<Option> options = new ArrayList<>();

    /** Help information option. */
    private Option help = new Option("h");

    /**
     * Adds command line option.
     * @param option command line option.
     */
    public void addOption(final Option option) {
        options.add(option);
    }

    /**
     * Gets the option argument based on the option's name.
     * @param optionName the option's short or long name.
     * @return argument of the option; but if no argument is found, null.
     */
    public String getOptionValue(final String optionName) {
        for (Option option : this.options) {
            if (option.hasTag(optionName)) {
                return option.getArgumentAtIndex(0);
            }
        }
        return null;
    }

    /**
     * Gets command line option.
     * @return command line option.
     */
    public ArrayList getOptions() {
        return new ArrayList<>(options);
    }

    /**
     * Parses command line arguments and stores data in Options list.
     * @param args command line arguments.
     */
    public void parse(final String[] args) {

        // Parse command line arguments
        Parser parser = new Parser(args, options);
        parser.parse();

        // Check for missing required options
        if (!this.help.isFound()) {
            for (Option option : this.options) {
                if (option.isRequired() && !option.isFound()) {
                    throw new IllegalArgumentException("Missing required option '" + option.getShortName() + "'.");
                }
            }
        }
    }

    /**
     * Prints the help information.
     * @return help information.
     */
    public String getHelp() {
        return this.help.getDescription();
    }

    /**
     * Creates help information option.
     * @param help help information.
     */
    public void createHelp(final String help) {
        this.help.setLongName("help");
        this.help.setDescription(help);
        this.options.add(this.help);
    }

    /**
     * Determines whether the "--help" option is found.
     * @return true if the help option is found.
     */
    public boolean needHelp() {
        return this.help.isFound();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Option option : this.options) {
            builder.append(option.toString());
        }
        builder.append("\n");
        return builder.toString();
    }
}