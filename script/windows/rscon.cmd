:: This is an example wrapper script for the rscon tool running on Windows.
:: Adapt it to your environment and needs and put it somewhere in your PATH.

:: This line runs the rscon JAR file, threading through any command line
:: parameters that were given to this wrapper script (in the %* variable). If
:: you didn't save your JAR file at the specified location `C:\bin\rscon.jar`
:: you have to adapt the path.
@java -jar C:\_\rscon.jar %*