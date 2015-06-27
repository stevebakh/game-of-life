# Game of Life
A simple Scala implementation of Game of Life (rule engine).

There's no main class yet, the code is simply the game engine and some test to prove it works correctly. The idea was to write a version of the engine that could scale; instead of using a 2D array to map the world, a set of living cells is used.

For each iteration (or tick), the set of cells representing the current state of the game is passed into the `tick` method, which returns a new set of cells representing the updated state. This should be much more performant than keeping track of every possible candidate cell; the code should only look at real possibly candidate positions to determine if a cell should be exist or not.

Current usage:
`sbt test`
