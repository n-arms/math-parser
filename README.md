# math-parser
A library with the main goal of taking in a string of mathematical text and returning a tree. This can then be evaluated to return a numeric result. Having an expression in tree state allows for you to manipulate it easily, and work directly with math to perform various operations on the expression. These include:
- Reducing and Expanding
- Derivatives
- Integrals
- Graphing


## What is a tree
A tree is composed of leaves and nodes. A leaf in the mathematial trees used in this parser is either a variable or a number literal. A node is an operator. Each kind of leaf and node has a class assoiated with it, and every class in a tree inherits from 1 abstract base class with a couple of built in methods.

### Graphically Representing Trees

**1 + 2**
```
      +
     / \
    1   2
```
**1 + 2 * 3**
```
        +
       / \
      /   \
     *     1
    / \  
   2   3
```
**(1 + 2) * 3**
```
        *
       / \
      /   \
     +     3
    / \
   1   2
```

## How are trees evaluated?
Every leaf and node in a tree has an evaluate method. Evaluating a constant returns its value, but evaluating an operator returns the operation performed on the *evaluated* versions of its arguments:
```
      *   
    /   \               *
   +     3     --->    / \   --->   9
  / \                 3   3  
 1   2
```
