package de.nrw.lav.dips.rscon;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class Configuration {
    private final static String PROG_NAME = "rscon";
    private final static String PROG_VERSION = "0.2.0";
    @Parameter(names = {"-h", "--help"},
            description = "show this help message", help = true)
    private Boolean showHelp = false;
    @Parameter(names = "--version", description = "show version info")
    private Boolean showVersion = false;
    @Parameter(names = {"-s", "--server"},
            description = "name of the host running the rendition server",
            validateWith = HostNameValidator.class)
    private String server = "localhost";
    @Parameter(names = "--timeout",
            description = "timeout in seconds, increase in case of errors")
    private int timeout = 120;
    @Parameter(names = {"-t", "--type"},
            description = "PDF/A type (1b, 2b, 2u)",
            validateWith = PdfaTypeValidator.class)
    private String pdfaType = "1b";
    @Parameter(names = {"-v", "--verbose"},
            description = "print more conversion details")
    private Boolean verbose = false;
    @Parameter(description = "input files")
    private List<String> inputFilePaths = new ArrayList<String>();
    private List<File> inputFiles;
    @Parameter(names = {"-o", "--output"},
            description = "output file (this works only with a single input"
            + " file, if multiple input files are given the"
            + " --output-directory option should be used instead)")
    private String outputFilePath = null;
    private File outputFile;
    @Parameter(names = {"-d", "--output-directory", "--destination"},
            description = "output directory for the converted PDF/A files")
    private String outputDirectoryPath = "pdfa";
    private File outputDirectory;
    @Parameter(names = "--carter", hidden = true,
            description = "use Carter mode (no parameter validation)")
    private Boolean carterMode = false;

    Configuration(String[] args) {
        JCommander jc = new JCommander(this);
        jc.setProgramName(PROG_NAME);
        try {
            if (Arrays.asList(args).contains("--carter")) {
                carterMode = true;
                jc.parseWithoutValidation(args);
            }
            else {
                jc.parse(args);
            }
        }
        catch (ParameterException e) {
            System.err.println(e.getMessage());
            System.err.println("Use --help for usage info.");
            System.exit(1);
        }
        if (showHelp || args.length == 0) {
            jc.usage();
            System.exit(0);
        }
        if (showVersion) {
            System.out.println(PROG_VERSION);
            System.exit(0);
        }
        inputFiles = createInputFiles(inputFilePaths);
        if (outputFilePath != null) {
            outputFile = new File(outputFilePath);
        }
        if (outputDirectoryPath != null) {
            outputDirectory = new File(outputDirectoryPath);
        }
    }

    /**
     * Create File objects for reading from file paths.
     *
     * This method ensures that all given strings are valid file or directory
     * paths and that the files are readable. Directories are traversed
     * recursively, creating File objects for all files found in the directory
     * tree.
     */
    private List<File> createInputFiles(List<String> paths) {
        List<File> fs = new ArrayList<File>();
        for (String p : paths) {
            File f = new File(p);
            if (!(f.exists() && f.canRead())) {
                if (carterMode) {
                    System.err.println("Cannot read file, skip " + p);
                    continue;
                }
                else {
                    System.err.println("Cannot read file " + p);
                    System.exit(1);
                }
            }
            if (f.isDirectory()) {
                fs.addAll(FileUtils.listFiles(f, null, true));
            }
            else {
                fs.add(f);
            }
        }
        return fs;
    }

    String server() {
        return server;
    }

    int timeout() {
        return timeout;
    }

    String pdfaType() {
        return pdfaType;
    }

    Boolean verbose() {
        return verbose;
    }

    File[] inputFiles() {
        return inputFiles.toArray(new File[0]);
    }

    /**
     * For some input file, get the corresponding output file.
     *
     * The location of the output file is based on the initial command line
     * arguments. If an output file was specified and only a single input file
     * is given then the specified output file is returned. Otherwise (no
     * output file but possibly output directory specified or multiple input
     * files given) the output file is "output directory + input file".
     *
     * There is no guarantee that all components (particularly directories) of
     * the path represented by the returned File object exist! They should be
     * created by the caller if needed.
     */
    File outputFile(File input) {
        if (inputFiles.size() == 1 && outputFile != null) {
            return outputFile;
        }
        return new File(outputDirectory,
                FilenameUtils.removeExtension(input.toString()) + ".pdf");
    }
}
