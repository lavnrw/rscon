# Release 0.2.0 (2019-02-01)

Conversion timeout can now be configured via command line option `--timeout`.
This is useful for files that take a long time to convert, so that the server
runs into a timeout exception.

# Release 0.1.2 (2017-04-05)

When writing converted files to an output directory their file extensions are
now changed to .pdf, like in `file.docx -> pdfa/file.pdf`. This does not apply
to single output files specified by the --output option.

# Release 0.1.1 (2017-02-03)

Help message clarification.

# Release 0.1.0 (2017-02-03)

Initial release, based on the rsval tool.