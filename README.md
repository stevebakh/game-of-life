# Game of Life
A simple Scala implementation of Game of Life (rule engine).

There's no main class yet, the code is simply the game engine and some tests to prove that it works correctly. The idea was to write a version of the engine that could scale; instead of using a 2D array to map the world, a set of living cells is used.

Current usage:
`sbt test`
