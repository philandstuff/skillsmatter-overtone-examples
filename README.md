# Skillsmatter Overtone Examples

## Getting started with Eclipse

* Install eclipse and counterclockwise, the clojure plugin for eclipse, by following the first two steps [here](http://dev.clojure.org/display/doc/Getting+Started+with+Eclipse+and+Counterclockwise).
* Clone this repository in git
* In eclipse, go File->Import->Existing Projects into Workspace
* Open src/skillsmatter_overtone_examples/sounds.clj and run cmd-alt-L to load in the REPL

## Getting started with Emacs

* Install emacs and ensure it has clojure-mode
* Install [leiningen](https://github.com/technomancy/leiningen), the build tool for clojure
* Install [swank-clojure](https://github.com/technomancy/swank-clojure)
* From the command line inside the project, run 'lein swank' to start the server
* From emacs, run M-x slime-connect to connect to it

-------

These examples were written up for an Overtone workshop at the
[Clojure Exchange](http://skillsmatter.com/event/scala/clojure-exchange)
to demonstrate a number of basic things you can achieve with Overtone.

## License

Copyright (C) 2011-12 Philip Potter

Distributed under the Eclipse Public License, the same as Clojure.
