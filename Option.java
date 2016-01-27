/**
 * Created by Zach Wilson on 1/23/16
 */

import java.util.ArrayList;

/**
 * Represents a single command line option.
 */
public class Option {

  /** The name of the option */
  private String name;

  /** The short name of the command line option */
  private String shortName;

  /** The long name of the command line option */
  private String longName;

  /** The description of the command line option */
  private String description;

  /** The arguments associated with the option */
  private ArrayList<String> arguments;

  /** The number of preceeding arguments */
  private int argCount;

  /** Indicated whether option is required or not */
  private boolean required;

  /** Indicated whether option has been provided or not */
  private boolean found;

  /**
   * Constructor.
   */
  public Option() {
    this.name = null;
    this.shortName = null;
    this.longName = null;
    this.description = null;
    this.arguments = new ArrayList<>();
    this.argCount = 0;
    this.required = false;
    this.found = false;
  }

  /**
   * Adds short name of option. '-' will be added to beginning of name.
   * @param name - short name of option
   */
  public void addShortName(final String name) {
    this.shortName = "-" + name;
  }

  /**
   * Gets short name of option
   * @return short name
   */
  public String getShortName() {
    return this.shortName;
  }

  /**
   * Adds long name of option. '--' will be added to beginning of name.
   * @param name - long name of option
   */
  public void addLongName(final String name) {
    this.name = name;
    this.longName = "--" + name;
  }

  /**
   * Gets long name of option.
   * @return long name
   */
  String getLongName() {
    return this.longName;
  }

  /**
   * Gets name of option.
   * @return name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Adds description of option.
   * @param description - description of option
   */
  public void addDescription(final String description) {
    this.description = description;
  }

  /**
   * Gets description of option.
   * @return description
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Adds arguments count of option: how many arguments preceded.
   * The default count is 0.
   * @param count - number of preceeding arguments
   */
  public void addExpectedArgCount(final int count)  {
    this.argCount = count;
  }

  /**
   * Gets arguments count of option.
   * @return number of preceeding arguments
   */
  int getExpectedArgCount() {
    return this.argCount;
  }

  /**
   * Gets the arguments of the option.
   * @return arguments
   */
  ArrayList<String> getArguments() {
    return this.arguments;
  }

  /**
   * Gets the arguments with the specified index.
   * @param index - specified index of argument in list
   * @return argument
   */
  public String getArgument(final int index) {
    return this.arguments.get(index);
  }

  /**
   * Adds an arguments of the option.
   * @param argument - associated arguments of the option
   */
  void addArgument(final String argument) {
    this.arguments.add(argument);
  }

  /**
   * Determines whether there exists a tag for the option: short or long name.
   * @return true if the tag exists
   */
  boolean hasTag(final String tag) {
    return (this.longName != null && this.longName.equals(tag)) ||
           (this.shortName != null && this.shortName.equals(tag));
  }

  /**
   * Sets whether option is required or not. By default, it is set to false.
   * @param required - whether option is required
   */
  public void isRequired(final boolean required) {
    this.required = required;
  }

  /**
   * Returns whether option is required or not.
   * @return true if option is required
   */
  boolean isRequired() {
    return this.required;
  }

  /**
   * Set whether there exists associated arguments for the option.
   * @param found - sets whether option has been found
   */
  public void isFound(final boolean found) {
    this.found = found;
  }

  /**
   * Determines whether there exists associated arguments for the option.
   * @return true if option has been found
   */
  public boolean isFound() {
    return this.found;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    // Option
    builder.append("Option: ");
    builder.append(this.name);
    builder.append("\n");

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