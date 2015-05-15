# algebolic

Algebolic is a library for evolving mathematical expressions. You can use it to perform what's known as symbolic
regression, where a symbolic mathematical expression is found that fits a given dataset. More generally, you can use it
to evolve mathematical expressions that optimise any score function. The expressions are implemented so they can be very
quickly evaluated, as can their derivatives which is useful in more advanced applications. We use it in our research
lab, for example, to evolve laws of physics directly from data.

Algebolic is built on the [darwin](https://github.com/JonyEpsilon/darwin) genetic algorithm framework.

## Usage

Add it to your project.clj file:
````
[algebolic "1.0.0"]
````

Examples can be found in the ws/demo directory. The intro.clj worksheet is quite readable. With the others you might
have to put in some thought/experimentation! You can view them on the web:

- [Introduction, basic symbolic regression](http://viewer.gorilla-repl.org/view.html?source=github&user=JonyEpsilon&repo=algebolic&path=ws/demo/intro.clj)
- [Simple symbolic regression, full configuration](http://viewer.gorilla-repl.org/view.html?source=github&user=JonyEpsilon&repo=algebolic&path=ws/demo/single_sr.clj)
- [SPEA2 symbolic regression, full configuration](http://viewer.gorilla-repl.org/view.html?source=github&user=JonyEpsilon&repo=algebolic&path=ws/demo/spea2.clj)
- [Adaptive evolution](http://viewer.gorilla-repl.org/view.html?source=github&user=JonyEpsilon&repo=algebolic&path=ws/demo/adaptive.clj)


## License

Copyright © 2015 Imperial College London

Distributed under the MIT licence.
