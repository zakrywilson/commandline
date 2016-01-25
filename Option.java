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
  public String getLongName() {
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
  public void addArgumentCount(final int count)  {
    this.argCount = count;
  }

  /**
   * Gets arguments count of option.
   * @return number of preceeding arguments
   */
  public int getArgumentCount() {
    return this.argCount;
  }

  /**
   * Gets the arguments of the option.
   * @return arguments
   */
  public ArrayList<String> getArguments() {
    return this.arguments;
  }

  /**
   * Adds an arguments of the option.
   * @param argument - associated arguments of the option
   */
  public void addArgument(final String argument) {
    this.arguments.add(argument);
  }

  /**
   * Determines whether there exists a short name for the option.
   * @return true if a short name exists
   */
  public boolean hasShortName() {
    return this.shortName != null;
  }

  /**
   * Determines whether there exists a long name for the option.
   * @return true if a long name exists
   */
  public boolean hasLongName() {
    return this.longName != null;
  }

  /**
   * Determines whether there exists a tag for the option: short or long name.
   * @return true if the tag exists
   */
  public boolean hasTag(final String tag) {
    return (longName != null && longName.equals(tag)) ||
           (shortName != null && shortName.equals(tag));
  }

  /**
   * Determines whether there exists a description for the option.
   * @return true if a description exists
   */
  public boolean hasDescription() {
    return this.description != null;
  }

  /**
   * Determines whether there exists an associated arguments for the option.
   * @return true if an arguments exists
   */
  public boolean hasArgument() {
    return !this.arguments.isEmpty();
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