import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/** 
* Option Tester. 
* 
* @author Zach Wilson
* @since Feb 1, 2016
* @version 1.0 
*/ 
public class OptionTest {

  /** Test directory to hold test file */
  private static File testDir;

  /** The current working directory */
  private static final String CWD = System.getProperty("user.dir");

  /** The test directory path */
  private static final String testDirName = CWD + "/test/test-directory/";

  /** The file1 path name */
  private static final String fileName1 = testDirName + "file1.txt";

  /** The file2 path name */
  private static final String fileName2 = testDirName + "file2.txt";

  /** The file3 path name */
  private static final String fileName3 = testDirName + "file3.txt";

  /**
   * Create test directory and test file within it.
   * @throws Exception
   */
  @Before
  public void before() throws Exception {

    // Create test directory inside commandline/test
    testDir = new File(testDirName);
    if (testDir.exists()) {
      deleteRecursively(testDir);
    }
    if (!testDir.mkdir()) {
      throw new Exception("Failed to create directory: " +
        testDir.getAbsolutePath());
    }

    // Create files
    createFile(fileName1);
    createFile(fileName2);
    createFile(fileName3);
  }

  /**
   * Remove test directory.
   * @throws Exception
   */
  @After
  public void after() throws Exception {
    deleteRecursively(testDir);
  }

  /**
  * Method: addShortName(final String name), getShortName()
  */
  @Test
  public void testShortName() throws Exception {
  Option option = new Option();
  option.addShortName("o");
  if (!option.getShortName().equals("-o")) {
    Assert.fail();
  }
  }

  /**
  * Method: addLongName(final String name), getLongName()
  */
  @Test
  public void testLongName() throws Exception {
  Option option = new Option();
  option.addLongName("option");
  if (!option.getLongName().equals("--option")) {
    Assert.fail();
  }
  }

  /**
  * Method: getName()
  */
  @Test
  public void testGetName() throws Exception {
  // Add short name; name should be short name
  Option option = new Option();
  option.addShortName("o");
  if (!option.getName().equals("o")) {
    Assert.fail();
  }
  // Now add long name; name should be long name now
  option.addLongName("option");
  if (!option.getName().equals("option")) {
    Assert.fail();
  }
  }

  /**
  * Method: addDescription(final String description), getDescription()
  */
  @Test
  public void testDescription() throws Exception {
  Option option = new Option();
  option.addDescription("This is an option.");
  if (!option.getDescription().equals("This is an option.")) {
    Assert.fail();
  }
  }

  /**
  * Method: addExpectedArgCount(final int count), getExpectedArgCount()
  */
  @Test
  public void testExpectedArgCount() throws Exception {
  Option option = new Option();
  // Default is 0
  if (option.getExpectedArgCount() != 0) {
    Assert.fail();
  }
  // Set it to 5 and make sure it's 5
  option.addExpectedArgCount(5);
  if (option.getExpectedArgCount() != 5) {
    Assert.fail();
  }
  }

  /**
  * Method: getArguments(), addArguments(final int index)
  */
  @Test
  public void testArguments() throws Exception {
    String[] args = new String[] { "Arg 1", "Arg 2", "Arg 3" };
    Option option = new Option();
    option.addArgument(args[0]);
    option.addArgument(args[1]);
    option.addArgument(args[2]);
    List<String> list = option.getArguments();
    for (int i = 0; i < list.size(); ++i) {
      if (!list.get(i).equals(args[i])) {
        Assert.fail("Failed on argument: " + list.get(i) +
          "--should have been " + args[i] + ".");
      }
    }
  }

  /**
  * Method: addArgument(final String argument), getArgument()
  */
  @Test
  public void testArgument() throws Exception {
    String arg1 = "Arg 1";
    String arg2 = "Arg 2";
    Option option = new Option();
    option.addArgument(arg1);
    option.addArgument(arg2);
    // Test argument 0
    if (!option.getArgument(0).equals(arg1)) {
      Assert.fail("Failed on argument: " + option.getArgument(0) +
      "--should have been " + arg1 + ".");
    }
    // Test argument 1
    if (!option.getArgument(1).equals(arg2)) {
      Assert.fail("Failed on argument: " + option.getArgument(1) +
        "--should have been " + arg2 + ".");
    }
  }

  /**
  * Method: hasTag(final String tag)
  */
  @Test
  public void testHasTag() throws Exception {
    String shortName = "o";
    String longName = "option";

    // Option has both long & short name
    Option bothOptions = new Option();
    bothOptions.addShortName(shortName);
    bothOptions.addLongName(longName);
    if (!bothOptions.hasTag("-" + shortName)) {
      Assert.fail();
    }
    if (!bothOptions.hasTag("--" + longName)) {
      Assert.fail();
    }

    // Option has only long name
    Option longOption = new Option();
    longOption.addLongName(longName);
    if (!longOption.hasTag("--" + longName)) {
      Assert.fail();
    }

    // Option has only short name
    Option shortOption = new Option();
    shortOption.addShortName(shortName);
    if (!shortOption.hasTag("-" + shortName)) {
      Assert.fail();
    }
  }

  /**
  * Method: isRequired(), isRequired(final boolean required)
  */
  @Test
  public void testIsRequired() throws Exception {
    Option option = new Option();
    // By default, 'isRequired' should be false
    if (option.isRequired()) {
      Assert.fail();
    }
    // Change to true
    option.isRequired(true);
    if (!option.isRequired()) {
      Assert.fail();
    }
  }

  /**
  * Method: isFound(final boolean found), isFound()
  */
  @Test
  public void testIsFoundFound() throws Exception {
    Option option = new Option();
    // By default, 'isFound' should be false
    if (option.isFound()) {
      Assert.fail();
    }
    // Change to true
    option.isFound(true);
    if (!option.isFound()) {
      Assert.fail();
    }
  }

  /**
  * Method: isFile()
  */
  @Test
  public void testIsFile() throws Exception {
    // File that does not exist
    Option nonFile = new Option();
    nonFile.addArgument("nonfile.txt");
    if (nonFile.isFile()) {
      Assert.fail("Failed on non-file.");
    }
    // File that exists
    Option file = new Option();
    file.addArgument(fileName1);
    if (!file.isFile()) {
      Assert.fail("Failed on real file.");
    }
  }

  /**
  * Method: areFiles()
  */
  @Test
  public void testAreFiles() throws Exception {
    // Files--some of which do no exist
    Option bad = new Option();
    bad.addArgument("nonfile0.txt");
    bad.addArgument(fileName1);
    bad.addArgument("nonfile2.txt");
    if (bad.areFiles()) {
      Assert.fail("Failed on files that do not exist.");
    }

    // Files that exist
    Option good = new Option();
    good.addArgument(fileName1);
    good.addArgument(fileName2);
    good.addArgument(fileName3);
    if (!good.areFiles()) {
      Assert.fail("Failed on files that exist.");
    }
  }

  /**
  * Method: isNumeric()
  */
  @Test
  public void testIsNumeric() {
    // Non numeric value
    Option nonNumeric = new Option();
    nonNumeric.addArgument("option");
    if (nonNumeric.isNumeric()) {
      Assert.fail("Failed on non numeric.");
    }
    // Integer value
    Option integer = new Option();
    integer.addArgument("1234");
    if (!integer.isNumeric()) {
      Assert.fail("Failed on integer.");
    }
    // Decimal value
    Option decimal = new Option();
    decimal.addArgument("12.34");
    if (!decimal.isNumeric()) {
      Assert.fail("Failed on decimal.");
    }
    // Invalid decimal value
    Option invalidDecimal = new Option();
    invalidDecimal.addArgument("1.");
    if (invalidDecimal.isNumeric()) {
      Assert.fail("Failed on invalid decimal.");
    }
  }


  @Test
  public void testAreNumeric() {
    // Non numeric values
    Option nonNumeric = new Option();
    nonNumeric.addArgument("These");
    nonNumeric.addArgument("are");
    nonNumeric.addArgument("command line arguments.");
    if (nonNumeric.areNumeric()) {
      Assert.fail("Failed on non-numeric values.");
    }
    // Integer values
    Option integers = new Option();
    integers.addArgument("1");
    integers.addArgument("23");
    integers.addArgument("456");
    if (!integers.areNumeric()) {
      Assert.fail("Failed on integer values.");
    }
    // Decimal values
    Option decimals = new Option();
    decimals.addArgument("1");
    decimals.addArgument("2.3");
    decimals.addArgument("4.5");
    decimals.addArgument(".129");
    if (!decimals.areNumeric()) {
      Assert.fail("Failed on decimal values.");
    }
    // Mixed strings and numbers
    Option mixed = new Option();
    mixed.addArgument("String");
    mixed.addArgument("1234");
    if (mixed.areNumeric()) {
      Assert.fail("Failed on mixed values.");
    }
  }

  /**
   * Recursively deletes all files in a directory and the directory itself.
   * @param file - directory to be deleted
   * @throws IOException
   */
  void deleteRecursively(File file) throws Exception {
    if (file == null || !file.exists()) {
      throw new Exception("File does not exist.");
    }

    if (file.isDirectory()) {
      File[] list = file.listFiles();
      if (list == null) {
        throw new Exception("List of files is null.");
      }
      for (File nextFile : list) {
        deleteRecursively(nextFile);
      }
    }
    if (!file.delete())
      throw new FileNotFoundException("Failed to deleteRecursively file: " + file);
  }

  /**
   * Create file.
   * @param path - path to file
   * @throws Exception
   */
  void createFile(final String path) throws Exception {
    File file = new File(path);
    try {
      if (!file.createNewFile()) {
        throw new Exception("Failed to create file: " + file.getAbsolutePath());
      }
    } catch (IOException ioe) {
      throw new IOException("Failed to create file: " + file.getAbsolutePath());
    }
  }

}
