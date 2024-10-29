# User Guide

## Introduction

FindMySEP is a Command Line Interface (CLI) tool designed for admins handling the allocation of Student Exchange Program (SEP) locations for Computer Engineering (CEG) students at NUS. The app allows administrators to efficiently manage the allocation process using automated workflows and data-driven decision-making.

## Table of Contents
[Quick Start](#quick-start)  
[Features](#features)  
[FAQ](#faq)  
[Command Summary](#command-summary)  

## Quick Start

{Give steps to get started quickly}

1. Ensure that you have Java 17 or above installed.
2. Down the latest version of `FindOurSEP` from [here](https://github.com/AY2425S1-CS2113-W12-2/tp/releases).
3. Copy the file to the folder you want to use as the home folder for your AddressBook.
4. Open a command terminal, cd into the folder you put the jar file in, and use the java -jar FindMySEP.jar command to run the application.

## Features

### *Notes about the command format:*
- Words in UPPER_CASE are the parameters to be supplied by the user.
- Extraneous parameters for commands that do not take in parameters (such as help, list and exit) will be ignored.
  - e.g. if the command specifies `help 123`, it will be interpreted as help.
- If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple 
lines as space characters surrounding line-breaks may be omitted when copied over to the application.

### Add Student Application: `add`

Adds a student to the student list. `PREFERENCE_RANKINGS`  should be enclosed in curly braces. We allow students to have a preference of up to 3 universities and they are ranked left to right, with the HIGHEST priority starting on the left.

Format: `add id/STUDENT_ID gpa/GPA p/{PREFERENCE_RANKINGS}`
e.g.
`add id/A1234567I gpa/5.0 p/{13,61,43}`


### View help: `help`
Shows a message explaining how to use the program. (Commands, etc.)  
Format: `help`

### Print current student list: `list`
Outputs a list of all current students in the student list.  
Format: `list`  
Example output:  

```shell
> list
Here is the list:
┌───────────────┬──────────┬─────────────────────────┐
│    Student    │   GPA    │   Preference Rankings   │
├───────────────┼──────────┼─────────────────────────┤
│   A1234567I   │   5.0    │        13,61,43         │
│   A2113113X   │   4.99   │           61            │
└───────────────┴──────────┴─────────────────────────┘
```

### Set minimum GPA criteria: `minimum`

This sets the minimum GPA for the cohort.

Format: `minimum GPA`
The student must achieve the same or a higher GPA to be considered for exchange. Any student below this GPA will not be allocated to any universities.
e.g.
`minimum 4.0`


### View allocation statistics: ``stats``

Displays the average or minimum GPA for the specified partner university.  
Format: `stats -avggpa UNI_INDEX` or `stats -mingpa UNI_INDEX`   
Example output:  

```bash
> stats -avggpa 36
The average GPA for university index 36 (The University of Hong Kong) is: 3.80
```

### View remaining quota: ``viewQuota``

Displays the index, name, and remaining quota for the specified partner university. 
Format: `viewQuota UNI_INDEX`   
Example output:  

```bash
> viewQuota 
 Index: 58
 Name:  ETH Zurich
 Quota: 1
```

### Run allocation algorithm: ``allocate``

Start the allocation algorithm. It will allocate universities to the current student list based on each student's preferences and GPA.  
Format: `allocate`

### Revert allocation outcome: `revert`

Reverses the allocation algorithm. To be used when changes need to be made to the student list.  
Format: `revert`

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

| Action    | Format/Example                                               |
| --------- | ------------------------------------------------------------ |
| Add       | `add id/STUDENT_ID gpa/GPA p/{PREFERENCE_RANKINGS}` <br> e.g. `add id/A1234567I gpa/5.0 p/{13,61,43}` |
| Delete    | `delete STUDENT_ID` <br> e.g. `delete A1234567I`             |
| List      | `list`                                                       |
| Statistic | ``stats -avggpa UNI_INDEX``  or ``stats -mingpa UNI_INDEX`` <br> e.g. ``stats -mingpa 42`` |
| viewQuota | ``viewQuota UNI_INDEX`` <br> e.g. ``viewQuota 42``           |
| Allocate  | `allocate`                                                   |
| Minimum   | `minimum 4.0`                                                |
| Revert    | `revert`                                                     |
| GetReport | `generate`                                                   |
| Exit      | `bye`, `exit`, `quit`                                        |
| Help      | `help`                                                       |
