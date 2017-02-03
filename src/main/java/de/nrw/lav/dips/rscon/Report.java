package de.nrw.lav.dips.rscon;

final class Report {
    private final String inputFile;
    private final String outputFile;
    private final Result result;
    private final String pdfaType;
    private final String details;

    enum Result { FINISHED, ERROR }

    Report(String inputFile, String outputFile, Result result, String
            pdfaType, String details) {
        this.inputFile  = inputFile;
        this.outputFile = outputFile;
        this.result     = result;
        this.pdfaType   = pdfaType;
        this.details    = details;
    }

    @Override
    public String toString() {
        return result + "\tPDF/A-" + pdfaType + "\t" + inputFile + " -> " +
            outputFile;
    }

    String toString(Boolean verbose) {
        String rest = (!verbose || details.isEmpty()) ? ""
            : System.getProperty("line.separator") + details;
        return toString() + rest;
    }
}
