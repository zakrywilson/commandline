package test.java.com.commandline;

import org.junit.*;

import main.java.com.commandline.Option;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Tests {@link Option}.
 *
 * @author Zach Wilson
 */
public class OptionTest {

    /** Test directory to hold test file. */
    private static File testDir;

    /** Resources directory to contain test data. */
    private static File resourcesDir;

    private static final String resourcesDirName = "resources" + File.separator;

    /** The test directory path. */
    private static final String testDirName = resourcesDirName + "test-directory" + File.separator;

    /** The file1 path name. */
    private static final String fileName1 = testDirName + "file1.txt";

    /** The file2 path name. */
    private static final String fileName2 = testDirName + "file2.txt";

    /** The file3 path name. */
    private static final String fileName3 = testDirName + "file3.txt";
    
    /** <code>Option#addArgument(String)</code> package protected method. */
    private static Method addArgument;

    /**
     * Create test directory and test file within it.
     * @throws Exception if resources cannot be created.
     */
    @BeforeClass
    public static void before() throws Exception {
        try {
            createResources();
        } catch (Exception e) {
            throw new Exception ("Failed to set up resources: cannot continue testing.", e);
        }

    }

    /**
     * Remove test directory.
     * @throws Exception if the test directory cannot be deleted.
     */
    @AfterClass
    public static void after() throws Exception {
        deleteRecursively(resourcesDir);
    }

    /**
     * Method: setShortName(final String name), getShortName().
     */
    @Test
    public void testGetShortName() {
        String name = "o";
        Option option = new Option(name);
        if (!option.getShortName().equals(name)) {
            Assert.fail("Option name expected to be 'o', received '" + option.getShortName() + "'.");
        }
    }

    /**
     * Method: setLongName(final String name), getLongName().
     */
    @Test
    public void testLongName() {
        String name = "option";
        Option option = new Option("o");
        option.setLongName(name);
        if (!option.getLongName().equals(name)) {
            Assert.fail("Option name expected to be 'option', received '" + option.getLongName() + "'.");
        }
    }

    /**
     * Method: setDescription(final String description), getDescription().
     */
    @Test
    public void testDescription() {
        Option option = new Option("o");
        option.setDescription("This is an option.");
        if (!option.getDescription().equals("This is an option.")) {
            Assert.fail();
        }
    }

    /**
     * Method: setExpectedArgumentCount(final int count), getExpectedArgumentCount().
     */
    @Test
    public void testExpectedArgCount() {
        Option option = new Option("o");

        // Default is 0
        if (option.getExpectedArgumentCount() != 0) {
            Assert.fail("Failed to get expected value of 0.");
        }

        // Set it to 5 and make sure it's 5
        option.setExpectedArgumentCount(5);
        if (option.getExpectedArgumentCount() != 5) {
            Assert.fail("Failed to get expected value of 5.");
        }
    }

    /**
     * Method: getAllArguments(), addArguments(final int index).
     */
    @Test
    public void testArguments() {
        String[] args = new String[] { "Arg 1", "Arg 2", "Arg 3" };
        Option option = new Option("o");
        
        try {
            addArgument = option.getClass().getDeclaredMethod("addArgument", String.class);
            addArgument.setAccessible(true);
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String)'.");
        }

        try {
            addArgument.invoke(option, args[0]);
            addArgument.invoke(option, args[1]);
            addArgument.invoke(option, args[2]);
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String) through reflections.");
        }

        List<String> list = option.getAllArguments();
        for (int i = 0; i < list.size(); ++i) {
            if (!list.get(i).equals(args[i])) {
                Assert.fail("Failed on argument: " + list.get(i) + "--should have been " + args[i] + ".");
            }
        }
    }

    /**
     * Method: addArgument(final String argument), getArgumentAtIndex().
     */
    @Test
    public void testArgument() {
        String arg1 = "Arg 1";
        String arg2 = "Arg 2";
        Option option = new Option("o");
        
        try {
            addArgument = option.getClass().getDeclaredMethod("addArgument", String.class);
            addArgument.setAccessible(true);
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String)'.");
        }

        try {
            addArgument.invoke(option, arg1);
            addArgument.invoke(option, arg2);
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String) through reflections.");
        }

        // Test argument 0
        if (!option.getArgumentAtIndex(0).equals(arg1)) {
            Assert.fail("Failed on argument: " + option.getArgumentAtIndex(0) + "--should have been " + arg1 + ".");
        }
        // Test argument 1
        if (!option.getArgumentAtIndex(1).equals(arg2)) {
            Assert.fail("Failed on argument: " + option.getArgumentAtIndex(1) + "--should have been " + arg2 + ".");
        }
    }

    /**
     * Method: hasTag(final String tag).
     */
    @Test
    public void testHasTag() {
        String shortName = "o";
        String longName = "option";

        // Option has both long & short name
        Option bothOptions = new Option(shortName);
        bothOptions.setLongName(longName);
        if (!bothOptions.hasTag("-" + shortName)) {
            Assert.fail();
        }
        if (!bothOptions.hasTag("--" + longName)) {
            Assert.fail();
        }

        // Option has only long name
        Option longOption = new Option(longName);
        longOption.setLongName(longName);
        if (!longOption.hasTag("--" + longName)) {
            Assert.fail();
        }

        // Option has only short name
        Option shortOption = new Option(shortName);
        if (!shortOption.hasTag("-" + shortName)) {
            Assert.fail();
        }
    }

    /**
     * Method: isRequired(), isRequired(final boolean required).
     */
    @Test
    public void testIsRequired() {
        Option option = new Option("o");
        // By default, 'isRequired' should be false
        if (option.isRequired()) {
            Assert.fail();
        }
        // Change to true
        option.setRequired(true);
        if (!option.isRequired()) {
            Assert.fail();
        }
    }

    /**
     * Method: setFound(final boolean found), setFound().
     */
    @Test
    public void testsetFound() {
        Option option = new Option("o");
        // By default, 'setFound' should be false
        if (option.isFound()) {
            Assert.fail();
        }
        // Change to true
        try {
            Method setFoundMethod = option.getClass().getDeclaredMethod("setFound", Boolean.class);
            setFoundMethod.setAccessible(true);
            setFoundMethod.invoke(option, true);
        } catch (Throwable t) {
            String msg = "";
            for (StackTraceElement e : t.getStackTrace()) {
                msg += "\n    " + e.toString();
            }
            Assert.fail("Failed to obtain protected method, Option#setFound(boolean), for testing: " + msg);
        }
        if (!option.isFound()) {
            Assert.fail("Failed to return 'true' as expected.");
        }
    }

    /**
     * Method: isFile().
     */
    @Test
    public void testIsFile() {
        // File that does not exist
        Option nonFile = new Option("o");
        try {
            addArgument = nonFile.getClass().getDeclaredMethod("addArgument", String.class);
            addArgument.setAccessible(true);
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String)'.");
        }
        try {
            addArgument.invoke(nonFile, "non-file.txt");
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String) through reflections.");
        }
        if (nonFile.isFile()) {
            Assert.fail("Failed on non-file.");
        }

        // File that exists
        Option file = new Option("o");
        try {
            addArgument = file.getClass().getDeclaredMethod("addArgument", String.class);
            addArgument.setAccessible(true);
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String)'.");
        }
        try {
            addArgument.invoke(file, fileName1);
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String) through reflections.");
        }
        if (!file.isFile()) {
            Assert.fail("Failed on real file.");
        }
    }

    /**
     * Method: areFiles().
     */
    @Test
    public void testAreFiles() {
        // Files--some of which do no exist
        Option bad = new Option("o");
        
        try {
            addArgument = bad.getClass().getDeclaredMethod("addArgument", String.class);
            addArgument.setAccessible(true);
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String)'.");
        }

        try {
            addArgument.invoke(bad, "non-file0.txt");
            addArgument.invoke(bad, fileName1);
            addArgument.invoke(bad, "non-file2.txt");
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String) through reflections.");
        }
        
        if (bad.areFiles()) {
            Assert.fail("Failed on files that do not exist.");
        }

        // Files that exist
        Option good = new Option("o");
        
        try {
            addArgument = good.getClass().getDeclaredMethod("addArgument", String.class);
            addArgument.setAccessible(true);
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String)'.");
        }

        try {
            addArgument.invoke(good, fileName1);
            addArgument.invoke(good, fileName2);
            addArgument.invoke(good, fileName3);
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String) through reflections.");
        }
        
        if (!good.areFiles()) {
            Assert.fail("Failed on files that exist.");
        }
    }

    /**
     * Method: isNumeric().
     */
    @Test
    public void testIsNumeric() {
        // Non numeric value
        Option nonNumeric = new Option("o");
        try {
            addArgument = nonNumeric.getClass().getDeclaredMethod("addArgument", String.class);
            addArgument.setAccessible(true);
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String)'.");
        }
        try {
            addArgument.invoke(nonNumeric, "option");
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String) through reflections.");
        }
        if (nonNumeric.isNumeric()) {
            Assert.fail("Failed on non numeric.");
        }

        // Integer value
        Option integer = new Option("o");
        try {
            addArgument = integer.getClass().getDeclaredMethod("addArgument", String.class);
            addArgument.setAccessible(true);
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String)'.");
        }
        try {
            addArgument.invoke(integer, "1234");
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String) through reflections.");
        }
        
        if (!integer.isNumeric()) {
            Assert.fail("Failed on integer.");
        }

        // Decimal value
        Option decimal = new Option("o");
        try {
            addArgument = decimal.getClass().getDeclaredMethod("addArgument", String.class);
            addArgument.setAccessible(true);
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String)'.");
        }
        try {
            addArgument.invoke(decimal, "12.34");
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String) through reflections.");
        }
        if (!decimal.isNumeric()) {
            Assert.fail("Failed on decimal.");
        }

        // Invalid decimal value
        Option invalidDecimal = new Option("o");
        try {
            addArgument = invalidDecimal.getClass().getDeclaredMethod("addArgument", String.class);
            addArgument.setAccessible(true);
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String)'.");
        }
        try {
            addArgument.invoke(invalidDecimal, "1.");
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String) through reflections.");
        }
        if (invalidDecimal.isNumeric()) {
            Assert.fail("Failed on invalid decimal.");
        }
    }


    /**
     * Method areNumeric().
     */
    @Test
    public void testAreNumeric() {
        // Non numeric values
        Option nonNumeric = new Option("o");
        try {
            addArgument = nonNumeric.getClass().getDeclaredMethod("addArgument", String.class);
            addArgument.setAccessible(true);
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String)'.");
        }
        try {
            addArgument.invoke(nonNumeric, "These");
            addArgument.invoke(nonNumeric, "are");
            addArgument.invoke(nonNumeric, "command line arguments.");
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String) through reflections.");
        }
        if (nonNumeric.areAllNumeric()) {
            Assert.fail("Failed on non-numeric values.");
        }

        // Integer values
        Option integers = new Option("o");
        try {
            addArgument = integers.getClass().getDeclaredMethod("addArgument", String.class);
            addArgument.setAccessible(true);
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String)'.");
        }
        try {
            addArgument.invoke(integers, "1");
            addArgument.invoke(integers, "12");
            addArgument.invoke(integers, "456");
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String) through reflections.");
        }
        if (!integers.areAllNumeric()) {
            Assert.fail("Failed on integer values.");
        }

        // Decimal values
        Option decimals = new Option("o");
        try {
            addArgument = decimals.getClass().getDeclaredMethod("addArgument", String.class);
            addArgument.setAccessible(true);
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String)'.");
        }
        try {
            addArgument.invoke(decimals, "1");
            addArgument.invoke(decimals, "2.3");
            addArgument.invoke(decimals, "4.5");
            addArgument.invoke(decimals, ".129");
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String) through reflections.");
        }
        if (!decimals.areAllNumeric()) {
            Assert.fail("Failed on decimal values.");
        }

        // Mixed strings and numbers
        Option mixed = new Option("o");
        try {
            addArgument = mixed.getClass().getDeclaredMethod("addArgument", String.class);
            addArgument.setAccessible(true);
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String)'.");
        }
        try {
            addArgument.invoke(mixed, "String");
            addArgument.invoke(mixed, "1234");
        } catch (Throwable t) {
            Assert.fail("Failed to obtain method 'Option#addArgument(String) through reflections.");
        }
        if (mixed.areAllNumeric()) {
            Assert.fail("Failed on mixed values.");
        }
    }

    /**
     * Creates directory structure for testing.
     * @throws Exception if any of the resources cannot be created.
     */
    private static void createResources() throws Exception {
        // Create resources directory: ./resources
        resourcesDir = new File(resourcesDirName);
        if (resourcesDir.exists()) {
            deleteRecursively(resourcesDir);
        }
        if (!resourcesDir.mkdirs()) {
            throw new Exception("Failed to create directory: " + resourcesDir.getAbsolutePath());
        }

        // Create test directory: ./resources/test-directory
        testDir = new File(testDirName);
        if (!testDir.mkdirs()) {
            throw new Exception("Failed to create directory: " + testDir.getAbsolutePath());
        }

        // Create files
        createFile(fileName1);
        createFile(fileName2);
        createFile(fileName3);
    }

    /**
     * Recursively deletes all files in a directory and the directory itself.
     * @param file - directory to be deleted.
     * @throws Exception if any file is null or not found.
     */
    private static void deleteRecursively(File file) throws Exception {
        if (file.isDirectory()) {
            File[] list = file.listFiles();
            if (list == null) {
                throw new Exception("List of files is null.");
            }
            for (File nextFile : list) {
                deleteRecursively(nextFile);
            }
        }
        if (!file.delete()) {
            throw new FileNotFoundException("Failed to deleteRecursively file: " + file);
        }
    }

    /**
     * Create file.
     * @param path - path to file
     * @throws Exception if file cannot be created.
     */
    private static void createFile(final String path) throws Exception {
        File file = new File(path);
        try {
            if (!file.createNewFile()) {
                throw new Exception("Failed to create file: " + file.getAbsolutePath());
            }
        } catch (IOException ioe) {
            throw new IOException("Failed to create file: " + file.getAbsolutePath(), ioe);
        }
    }

}
