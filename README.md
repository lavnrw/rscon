# rscon

Command line client for the [SER Doxis4 Rendition Server][rs] PDF/A conversion
features, based on the Rendition Server Java API.

[Deutschsprachige Anleitung | German User Guide](HOWTO.de.md)

## Installation

Prerequisites:

* Java Runtime Environment (JRE) version 8 or greater
* Rendition Server on the same host or reachable over the network

The [provided JAR file `rscon.jar`][releases] contains all dependencies and can
be executed directly, but the user experience may be improved by creating a
wrapper script like the example in [script/rscon][script/rscon].

[releases]: http://example.org/TODO

## Usage

Show the available options:

~~~console
$ rscon --help
Usage: rscon [options] input files
  Options:
    -h, --help
      show this help message
      Default: false
    -o, --output
      output file (this works only with a single input file, if multiple input
      files are given the --output-directory option should be used instead)
    -d, --output-directory, --destination
      output directory for the converted PDF/A files
      Default: pdfa
    -s, --server
      name of the host running the rendition server
      Default: localhost
    --timeout
      timeout in seconds, increase in case of errors
      Default: 120
    -t, --type
      PDF/A type (1b, 2b, 2u)
      Default: 1b
    -v, --verbose
      print more conversion details
      Default: false
    --version
      show version info
      Default: false
~~~

Convert `a.docx` into `pdfa/a.pdf`, using PDF/A-1b (default):

~~~console
$ rscon --server rs.example.org a.docx
FINISHED    PDF/A-1b    a.docx -> pdfa/a.pdf
~~~

Convert `a.docx` into `b.pdf`, using PDF/A-2b:

~~~console
$ rscon --server rs.example.org --type 2b a.docx --output b.pdf
FINISHED    PDF/A-2b    a.docx -> b.pdf
~~~

Convert multiple files (and/or directories, recursively):

~~~console
$ rscon -s rs.example.org -t 2b a.docx b.docx -d PDF-A
FINISHED    PDF/A-2b    a.docx -> PDF-A/a.pdf
FINISHED    PDF/A-2b    b.docx -> PDF-A/b.pdf
~~~

## Building from source

To build this tool the proprietary Rendition Server Java API is required (see
[lib/README.md](lib/README.md) for details).

Run the following commands to build a standalone executable JAR file:

    $ mvn clean
    $ mvn package

This will create `target/rscon-<version>-jar-with-dependencies.jar`. Copy,
rename, symlink, whatever this file to your heart's content!

[rs]: https://web.archive.org/web/20181122130019/http://www.ser-solutions.com/media-library/overview/medien/server-side-format-conversion-doxis4-rendition-server.html
