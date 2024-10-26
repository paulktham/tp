# Developer Guide

## Table of Contents
- [Acknowledgements](#Acknowledgements)
- [Installation](#Installation)
- [Design & Implementation](#design)
  - [Architecture](#architecture)
  - [Frontend / User Interface](#frontend--user-interface)
  - [Parser](#parser)
  - [Commands](#commands)
    - [Add Command](#add-command)


## Acknowledgements

This project utilizes the following dependencies:

- [JUnit 5.10.0](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api/5.10.0) for testing.
- [AsciiTable 0.3.2](https://mvnrepository.com/artifact/de.vandermeer/asciitable/0.3.2) for formatting tables.
- [Jackson Databind 2.18.0](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind/2.18.0) for JSON processing.
- [OpenCSV 5.9](https://mvnrepository.com/artifact/com.opencsv/opencsv/5.9) for CSV parsing.

Many thanks to the developers and maintainers of these libraries for their incredible work. Their efforts have significantly contributed to the success of this project.

## Installation

To get started with this project, follow these steps:

### Prerequisites

- Java 17
- Download the latest `.jar` from [here](https://github.com/AY2425S1-CS2113-W12-2/tp/releases/tag/v1.0).

### Steps

1. **Copy the `.jar` file:**
    - Move the downloaded `.jar` file into a designated folder on your computer.

2. **Prepare your data file:**
    - If you want to parse a file (.CSV, .JSON, .TXT) containing student data, ensure you have the absolute path ready for that file.

3. **Run the `.jar` file:**
    - Open a terminal.
    - Navigate (`cd`) to the folder containing the `.jar` file.
    - Execute the `.jar` file using the following command:
      ```shell
      java -jar FindOurSEP.jar
      ```


🎉 Congratulations! You’re all set to dive into the wonders of this project. Enjoy the ride!

## Design

### Architecture

### Frontend / User Interface

### Parser

The `Parser` class is a crucial component instantiated as soon as FindOurSEP is initialized. Its responsibility is to process the user’s input into commands and invoking the correct command object for the rest of the program.

Some of its core features include:
- Breaking down user input and extracting the relevant command and data for further processing.
- Provide robust error handling when unknown command is received.
- Validate data parsed in from external file (.CSV, .JSON, .TXT) sources. (Further details in the `Storage` class)

Here is a class diagram highlighting the fundamental structure of the `Parser` class.

![ParserClass](UML_Diagrams/ParserClass.drawio.svg)

How `Parser` works:
1. Whenever the user enters an input, the input will be directed to the `Parser` class's `parseInput()` method. 
2. Within the method, the command will be extracted and the appropriate `XYZCommand` object (XYZCommand is a placeholder for various commands such as DeleteCommand, ListCommand, etc.) will be instantiated.
3. Upon instantiation, the `XYZCommand` is prepared for execution. Each `XYZCommand` class, inheriting from the abstract `Command` class, has a `run()` method that executes its specific instructions.

The sequence diagram below demonstrates the interactions within the `Parser` component when a user inputs the command: `add id/A1234567I gpa/5.0 p/{13,61,43}`.

![ParserSequence](UML_Diagrams/ParserSequence.drawio.svg)

The boolean return value of `parseInput()` indicates whether the user has chosen to continue or terminate the program. A `true` value keeps FindOurSEP running, while a `false` value ends the program.

### Commands

#### Add Command

#### Delete Command

#### Find Command

#### List Command

#### Allocate Command

#### Exit Command

#### Help Command

#### Generate Command

#### Unknown Command

### Implementation

## Product scope
### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v2.0|user|find a to-do item by name|locate a to-do without having to go through the entire list|

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
