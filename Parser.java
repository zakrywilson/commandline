/**
 * Created by Zach Wilson on 1/23/16
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/*
 * The parser that handles parsing command line arguments and storing them into
 * their appropriate Options.
 */
public class Parser {

  /** The command line arguments */
  private ArrayList<String> args;

  /** The available options for command line arguments */
  private ArrayList<Option> options;

  /**
   * Constructor.
   */
  @SuppressWarnings("unused")
  private Parser() {
    // not used
  }

  /**
   * Constructor.
   * Sets args and options.
   * @param args - command line arguments
   * @param options - list of command line options
   */
  public Parser(String[] args, ArrayList<Option> options) {
    this.args = new ArrayList<>(Arrays.asList(args));
    this.options = options;
  }

  /**
   * Parses command line arguments and loads them into Options objects.
   * @throws IllegalArgumentException
   */
  public void parse() throws IllegalArgumentException {

    // Iterates over the arguments list
    Iterator<String> iterator = this.args.iterator();

    // Keeps track of whether a first argument has been checked
    boolean firstArgument = true;

    // Iterate over entire arguments list
    while(iterator.hasNext()) {

      // Get the first argument
      String arg = iterator.next();

      // If the argument isn't an option, throw exception
      if (!arg.startsWith("-")) {
        if (firstArgument) {
          throw new IllegalArgumentException("Missing command line option.");
        }
        throw new IllegalArgumentException("Too many arguments for option.");
      }

      firstArgument = false;

      // Iterate over the options, looking for a match
      for (Option option : this.options) {

        // Do not re-check options with arguments
        // Check if argument matches long or short name
        if (!option.hasArgument() && option.hasTag(arg)) {

          // Number of arguments preceeding an option is known
          // Keep looping through until that number is expelled
          for(int counter = 0; counter < option.getArgumentCount(); ++counter) {

            // If there is no next-item, an item is missing: throw exception
            if (!iterator.hasNext()) {
              throw new IllegalArgumentException("Missing argument for option '" +
                option.getName() + "'. Given " + counter + ", expected " +
                option.getArgumentCount() + ".");
            }

            // Ensuring no option is found here because more args are expected
            String a = iterator.next();
            if (a.startsWith("-")) {
              throw new IllegalArgumentException();
            }

            // If all checks out, assign argument into it's associated option
            option.addArgument(a);
          }
        }
      }
    }
  }

  /**
   * Gets the list of Options.
   * @return list of options
   */
  public ArrayList<Option> getOptions() {
    ArrayList<Option> optionsList = new ArrayList<>();
    for (Option option : this.options)
      optionsList.add(option);
    return optionsList;
  }

  /**
   * Gets the list of arguments.
   * @return arguments list
   */
  public ArrayList<String> getArguments() {
    ArrayList<String> argsList = new ArrayList<>();
    for (Option option : this.options) {
      for (String argument : option.getArguments()) {
        argsList.add(argument);
      }
    }
    return argsList;
  }
}