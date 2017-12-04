# EvolutionaryAlgorithm-NeuralNetwork
An Evolutionary Algorithm that aims to solve the Travelling Salesperson Problem and an Artificial Neural Network that randomly attempts to solve the 'Unit Step' activation function.

The 'Unit Step' activation function is defined as follows:
f(x) = 0	if x < 0
f(x) = 0.5	if x = 0
f(x) = 1 	if x > 0

ANN.java and TSP.java are the two main classes.

To see the Evolutionary Algorithm running run the following commands in the terminal:

$ javac TSP.java
$ java TSP

And for the Artificial Neural Network run the following commands:

$ javac ANN.java
$ java ANN