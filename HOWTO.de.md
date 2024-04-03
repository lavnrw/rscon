# rscon - Rendition Server Command Line PDF/A Converter

Das Tool rscon ist ein Kommandozeilenclient für die
PDF/A-Konvertierungsfunktionen des [Doxis4 Rendition Server][rs] der Firma
[SER][ser].

Der Rendition Server ist für die Integration in größere Softwaresysteme und
nicht als Standalone-Anwendung gedacht, dementsprechend dürftig sind seine
Schnittstellen für menschliche Benutzerinnen und Benutzer ausgestattet. Für die
Validierung bzw. Konvertierung einzelner Dateien wird eine kleine GUI
mitgeliefert, die aber primär für Tests der Installation gedacht ist; die
Bearbeitung mehrerer Dateien oder Verzeichnisse im Batchverfahren ist nicht
vorgesehen. Diese Lücke füllen die Tools [rsval (Rendition Server
Validation)][rsval] zur Validierung von PDF/A und [rscon (Rendition Server
Conversion)][rscon] zur Konvertierung nach PDF/A.

[ser]: https://www.sergroup.com/
[rs]: https://web.archive.org/web/20181122130019/http://www.ser-solutions.com/media-library/overview/medien/server-side-format-conversion-doxis4-rendition-server.html
[rsval]: http://example.org/TODO
[rscon]: http://example.org/TODO

## Installation

Es wird eine Java-Laufzeitumgebung (JRE) ab Version 8, 64 Bit benötigt. Außerdem
muss im Netzwerk ein Rendition Server erreichbar sein.

Die [bereitgestellte JAR-Datei `rscon.jar`][releases] ist ohne Installation
direkt ausführbar.

Für einen bequemeren Aufruf bietet es sich an, die JAR-Datei an einem zentralen
Ort abzulegen und durch ein kleines Wrapper-Script ohne Pfadangabe und
Java-Blabla aufrufbar zu machen (also `rscon` statt z.B. `java -jar
C:\bin\rscon.jar`), aber das ist optional und abhängig von der individuellen
Systemumgebung (z.B. Linux/Bash vs. Windows/PowerShell). Ein Beispiel für Linux
findet sich in [`script/rscon`][script/rscon].

[releases]: http://example.org/TODO

## Benutzung

Die folgenden Beispiele gehen zwecks besserer Lesbarkeit davon aus, dass ein
Wrapper-Script für das Kommando `rscon` eingerichtet wurde (s.o. im Abschnitt
Installation). Ist das nicht der Fall, sind die Aufrufe entsprechend dem
Speicherort der JAR-Datei anzupassen, z.B. folgendermaßen (oben mit, unten ohne
Wrapper; die Dollarzeichen symbolisieren den Kommandozeilenprompt und werden
nicht mit eingegeben):

~~~console
$ rscon --help
$ java -jar C:\bin\rscon.jar --help
~~~

Die Beispiele gehen außerdem davon aus, dass der Rendition Server unter dem
Hostnamen `rs.example.org` erreichbar ist. Auch das muss der eigenen Umgebung
angepasst werden.

Anzeige der verfügbaren Programmoptionen:

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
      PDF/A type (1b, 2b)
      Default: 1b
    -v, --verbose
      print more conversion details
      Default: false
    --version
      show version info
      Default: false
~~~

Konvertierung der Datei `a.docx` nach PDF/A-1b (default) als `pdfa/a.pdf`:

~~~console
$ rscon --server rs.example.org a.docx
FINISHED    PDF/A-2b    a.docx -> pdfa/a.pdf
~~~

Konvertierung der Datei `a.docx` nach PDF/A-2b als `b.pdf`:

~~~console
$ rscon --server rs.example.org --type 2b a.docx --output b.pdf
FINISHED    PDF/A-2b    a.docx -> b.pdf
~~~

Statt eines einzelnen Dateinamens kann auch eine Liste von Dateien oder
Verzeichnissen (schließt Unterverzeichnisse mit ein) zusammen mit einem
Zielverzeichnis für die konvertierten Dateien angegeben werden. Dabei erhalten
die konvertierten Dateien im Zielverzeichnis automatisch die Dateiendung `.pdf`:

~~~console
$ rscon -s rs.example.org -t 2b a.docx b.docx -d PDF-A
FINISHED    PDF/A-2b    a.docx -> PDF-A/a.pdf
FINISHED    PDF/A-2b    b.docx -> PDF-A/b.pdf
~~~

Die meisten Optionen können abgekürzt werden und haben zudem Default-Werte,
wodurch sie ggf. weggelassen werden können. Details dazu zeigt `rscon --help`.
