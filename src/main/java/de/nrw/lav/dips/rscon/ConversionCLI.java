package de.nrw.lav.dips.rscon;

import com.ser.renditionserver.ConvertException;
import com.ser.renditionserver.Converter;
import java.io.File;

public final class ConversionCLI {
    private final Configuration config;
    private final Converter converter;

    private ConversionCLI(String[] args) {
        config = new Configuration(args);
        converter = createConverter();
    }

    private Converter createConverter() {
        Converter c = new Converter();
        c.setServerURL("http://" + config.server() + "/Dxr.Interfaces");
        c.setTimeoutCall(120);
        c.setTimeoutConnect(1);
        return c;
    }

    private Report convert(File input, File output) {
        String format = config.pdfaType().replace("1b", "PDFA").replace("2b",
                "PDFA2B");
        Report.Result result;
        String details;
        if (output.getParentFile() != null) {
            output.getParentFile().mkdirs();
        }
        try {
            converter.convert(input, output, format, "", "");
            result = Report.Result.FINISHED;
            details = "";
        }
        catch (ConvertException e) {
            result = Report.Result.ERROR;
            details = e.getMessage();
        }
        return new Report(input.toString(), output.toString(), result,
                config.pdfaType(), details);
    }

    public static void main(String[] args) {
        ConversionCLI cli = new ConversionCLI(args);
        for (File f : cli.config.inputFiles()) {
            Report r = cli.convert(f, cli.config.outputFile(f));
            System.out.println(r.toString(cli.config.verbose()));
        }
    }
}
