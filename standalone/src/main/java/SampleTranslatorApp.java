import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;

import org.agmip.ace.AceDataset;
import org.agmip.ace.io.AceParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Parameters;

@Command(name = "sample-translator-cmd", description="Sample Translator Command Line Interface")
public class SampleTranslatorApp implements Callable<Void>{
  private static final Logger log = LoggerFactory.getLogger("Application");
  
  @Parameters(index="0", description="File to translate", paramLabel="FILE")
  private File inputFile;

  @Option(names = {"-h", "--help"}, usageHelp = true, description="Show this help message")
  private boolean help;

  public static void main(String[] args) throws Exception {
    CommandLine.call(new SampleTranslatorApp(), System.err, args);
  }

  private String getFileExtension(Path p) {
    String s = p.toString();
    int dot = p.toString().lastIndexOf(".");
    if (dot == -1 || s.length() == dot) {
      return "";
    } else {
      return s.substring(dot+1);
    }
  }

  @Override
  public Void call() throws Exception {
    Path inputFilePath = inputFile.toPath().normalize().toAbsolutePath();
    if ((! Files.isReadable(inputFilePath)) || Files.isDirectory(inputFilePath)) {
      throw new Exception("Invalid input file");
    } else {
      log.info("Path to input file: {}", inputFilePath);
      log.info("Input file is readable: {}", (Files.isReadable(inputFilePath) ? "yes" : "no"));
      log.info("Filetype Probed: {}", getFileExtension(inputFilePath));
    }
    return null;
  }
}
