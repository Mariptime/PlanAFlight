# Plan a Flight Project

This project involves planning flights between cities by analyzing flight data and requested flight plans. The goal is to output the most efficient flight plans based on time or cost.

## Introduction

The task is to determine all possible flight plans between origin and destination cities using information from input files containing flight data and requested flight plans. Flight data includes city pairs, flight costs, and travel times. Requested flight plans specify origin/destination pairs and whether to sort results by time or cost. The program must output the top 3 most efficient flight plans or all plans if fewer than 3 exist. If no plan is possible, an error message is output.

## Data Structures

An undirected graph data structure represents all flight connections between cities. Nodes are cities, and edges are flights between cities with associated costs and times. The graph is implemented using an adjacency list, where each city has a linked list of its connections. 

A stack is also implemented to perform iterative backtracking during the exhaustive search for flight plans. The stack "remembers" the search path and allows backtracking if a dead end is reached.

## Algorithm

An iterative backtracking algorithm uses the graph and stack to find all possible flight plans between an origin/destination pair. The stack holds the current partial path during depth-first search. If a dead end is reached, the search backtracks and tries a new path. All valid plans are output once the destination is reached.

## Output 

For each requested flight plan, the top 3 most efficient plans based on the time or cost sort parameter are output. Plans include the path taken and total time/cost. If fewer than 3 plans exist, all plans are output. An error message is shown if no plan is possible.

## Implementation

The program is run from the command line, passing the input and output file names. Classes are used to represent the graph, stack, and other data structures. Methods are divided appropriately among classes. The main method has minimal code by delegating work to other classes/methods. Proper OOP design and robust, well-structured code is required.
