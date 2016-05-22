package main.java.com.commandline;

import java.io.File;
import java.util.ArrayList;

/**
 * Represents a single command line option.
 *
 * @author Zach Wilson
 */
public final class Option {

    /** The short name of the command line option. */
    private String shortName;

    /** The long name of the command line option. */
    private String longName;

    /** The description of the command line option. */
    private String description;

    /** The arguments associated with the option. */
    private ArrayList<String> arguments;

    /** The number of preceeding arguments. */
    private int argCount;

    /** Indicated whether option is required or not. */
    private boolean required;

    /** Indicated whether option has been provided or not. */
    private boolean found;

    /**
     * Constructor.
     */
    @SuppressWarnings("unused")
    private Option() {}

    /**
     * Constructor.
     * @param shortName option name.
     */
    public Option(final String shortName) {
        this.shortName = "-" + shortName;
        this.longName = "";
        this.arguments = new ArrayList<>();
    }

    /**
     * Gets short name of option.
     * @return short name.
     */
    public String getShortName() {
        return this.shortName.substring(1, this.shortName.length());
    }

    /**
     * Adds long name of option. '--' will be added to beginning of name.
     * @param name long name of option.
     */
    public void setLongName(final String name) {
        this.longName = "--" + name;
    }

    /**
     * Gets long name of option.
     * @return long name.
     */
    public String getLongName() {
        return this.longName.substring(2, this.longName.length());
    }
    
    /**
     * Adds description of option.
     * @param description description of option.
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Gets description of option.
     * @return description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Adds arguments count of option: how many arguments preceded. The default count is 0.
     * @param count number of preceding arguments.
     */
    public void setExpectedArgumentCount(final int count)  {
        this.argCount = count;
    }

    /**
     * Gets arguments count of option.
     * @return number of preceding arguments.
     */
    public int getExpectedArgumentCount() {
        return this.argCount;
    }

    /**
     * Gets the arguments of the option.
     * @return arguments.
     */
    public ArrayList<String> getAllArguments() {
        return this.arguments;
    }

    /**
     * Gets the arguments with the specified index.
     * @param index specified index of argument in list.
     * @return argument.
     */
    public String getArgumentAtIndex(final int index) {
        return this.arguments.get(index);
    }

    /**
     * Adds an arguments of the option.
     * @param argument associated arguments of the option.
     */
    void addArgument(String argument) {
        this.arguments.add(argument);
    }

    /**
     * Determines whether there exists a tag for the option: short or long name.
     * @param tag to be searched for.
     * @return true if the tag exists.
     */
    public boolean hasTag(final String tag) {
        return (this.longName != null && this.longName.equals(tag)) ||
               (this.shortName != null && this.shortName.equals(tag));
    }

    /**
     * Sets whether option is required or not. By default, it is set to false.
     * @param required whether option is required.
     */
    public void setRequired(final boolean required) {
        this.required = required;
    }

    /**
     * Returns whether option is required or not.
     * @return true if option is required.
     */
    public boolean isRequired() {
        return this.required;
    }

    /**
     * Set whether there exists associated arguments for the option.
     * @param found sets whether option has been found.
     */
    void setFound(final Boolean found) {
        this.found = found;
    }

    /**
     * Determines whether there exists associated arguments for the option.
     * @return true if option has been found.
     */
    public boolean isFound() {
        return this.found;
    }

    /**
     * Determines whether the first argument is a valid file.
     * @return true if the first argument is a valid file.
     */
    public boolean isFile() {
        File file = new File(this.arguments.get(0));
        return file.exists();
    }

    /**
     * Determines whether the arguments are valid files.
     * @return true if all arguments are valid files.
     */
    public boolean areFiles() {
        for (String argument : this.arguments) {
            File file = new File(argument);
            if (!file.exists()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines whether the first argument is a number.
     * @return true if the argument is a number.
     */
    public boolean isNumeric() {
        return isANumberAtIndex(0);
    }

    /**
     * Determines whether all arguments are numeric.
     * @return true if all arguments are numbers.
     */
    public boolean areAllNumeric() {
        for (int index = 0; index < this.arguments.size(); ++index) {
            if (!isANumberAtIndex(index)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if arguments at index is a number.
     * @param index index to be checked.
     * @return true if argument at index is numeric.
     */
    private boolean isANumberAtIndex(int index) {
        return this.arguments.get(index).matches("(\\d+(\\.\\d+)?)|(\\.\\d+)");
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        // Option
        builder.append("Option");

        // Short name
        builder.append("Short name: ");
        builder.append(this.shortName);
        builder.append("\n");

        // Long name
        builder.append("Long name: ");
        builder.append(this.longName);
        builder.append("\n");

        // Argument count
        builder.append("Argument count: " );
        builder.append(this.argCount);
        builder.append("\n");

        // Found
        builder.append("Found: ");
        builder.append(this.found);
        builder.append("\n");

        // Description
        builder.append("Description: ");
        builder.append(this.description);
        builder.append("\n");

        // Arguments
        builder.append("Arguments: ");
        if (!this.arguments.isEmpty()) {
            for (String argument : this.arguments) {
                builder.append(argument);
                builder.append(" ");
            }
        } else {
            builder.append("No arguments provided.");
        }
        builder.append("\n\n");

        return builder.toString();
    }
}
