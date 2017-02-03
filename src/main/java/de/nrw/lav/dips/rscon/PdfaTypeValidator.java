package de.nrw.lav.dips.rscon;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import java.util.Arrays;

public final class PdfaTypeValidator implements IParameterValidator {
    public void validate(String name, String value)
        throws ParameterException {
        String[] types = {"1b", "2b"};
        if (!Arrays.asList(types).contains(value)) {
            throw new ParameterException(String.format(
                        "PDF/A type for conversion must be one of %s.",
                        String.join(", ", types)));
        }
    }
}
