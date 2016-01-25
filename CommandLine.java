/**
 * Created by Zach Wilson on 1/23/16
 */

import java.util.ArrayList;

/**
 * Allows for specification and parsing of command line arguments.
 */
public class CommandLine {

  /** Optional command line arguments */
  private ArrayList<Option> options;

  /**
   * Constructor.
   */
  CommandLine() {
    options = new ArrayList<>();
  }

  /**
   * Adds command line option.
   * @param option - command line option
   */
  public void addOption(final Option option) {
    options.add(option);
  }

  /**
   * Gets command line option.
   * @return command line option
   */
  public ArrayList getOptions() {
    return new ArrayList<>(options);
  }

  /**
   * Parses command line arguments and stores data in Options list.
   * @param args - command line arguments
   */
  public void parse(final String[] args) {

    // Parse command line arguments
    Parser parser = new Parser(args, options);
    parser.parse();

    // Check for missing required options
    for (Option option : this.options) {
      if (option.isRequired() && !option.hasArguments()) {
        throw new IllegalArgumentException("Required option '" + option.getName() + "'.");
      }
    }
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (Option option : this.options)
      builder.append(option.toString());
      builder.append("\n");
    return builder.toString();
  }
}