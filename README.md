# rscon

Command line client for the [SER Doxis4 Rendition Server][rs] PDF/A conversion
features, based on the Rendition Server Java API.

[rs]: https://web.archive.org/web/20181122130019/http://www.ser-solutions.com/media-library/overview/medien/server-side-format-conversion-doxis4-rendition-server.html

[Deutschsprachige Anleitung | German User Guide](HOWTO.de.md)

## Installation

Prerequisites:

* Java Runtime Environment (JRE) version 8 or greater.
* A SER Doxis4 Rendition Server on the same host or network.
* Due to licensing issues you need *two* JAR files:
  * A JAR file containing the Rendition Server Java API (usually called
    something like `dxrapi.jar`). Since this is a proprietary library owned by
    [SER][ser] it cannot be distributed together with this repository. Please
    find it in your Rendition Server installation directory or ask the good
    people at SER for a copy of the JAR file.
  * A second JAR file containing the rest of the application (usually called
    `rscon.jar`). [Get it here!][releases]

The actual installation depends on your environment. Save both JAR files in a
directory that suites your taste (e.g., `/opt/rs` on Linux or `C:\bin\rs` on
Windows). Then create a wrapper script to make the actual `rscon` command
available; examples for Linux and Windows can be found in the [script](script)
directory.

[ser]: https://www.sergroup.com/en/
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
the Installation section above). Get the JAR file and make it available in your
development environment. For example, if you use Maven something like this
should install it into your local repository (check your version number!):

~~~console
$ mvn install:install-file -Dfile=dxrapi.jar -DgroupId=com.ser.renditionserver -DartifactId=dxrapi -Dversion=4.0.1.357 -Dpackaging=jar -DgeneratePom=true
~~~

Then run the following commands to build the `rscon` JAR file:

~~~console
$ mvn clean
$ mvn package
~~~

This will create `target/rscon-<version>-jar-with-dependencies.jar` which
contains all dependencies except for the proprietary Rendition Server API (which
has to be distributed separately, see the Installation section above). To run
the tool *both* JARs are required.
