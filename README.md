# Malio - (in progress)

---

[![License](https://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
![GWT3/J2CL compatible](https://img.shields.io/badge/GWT3/J2CL-compatible-brightgreen.svg)
[![Join the chat at https://gitter.im/Nalukit42/Lobby](https://badges.gitter.im/Nalukit42/Lobby.svg)](https://gitter.im/Nalukit42/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.nalukit/malio.svg?colorB=44cc11)](https://search.maven.org/artifact/com.github.nalukit/malio)
[![Build & Deploy](https://github.com/NaluKit/malio/actions/workflows/build.yaml/badge.svg?branch=dev)](https://github.com/NaluKit/malio/actions/workflows/build.yaml)

## Motivation

Due to the lack of a validation library that works with GWT/J2cl hera an approach, that can be used with GWT / J2CL and does basic validation. 
The idea is to add some annotation to attributes of a POJO which controls the generation of a validation class. The result is a validatato that supports two methods:
* **check**: check the POJO and in case of an error throw an exception
* **validate**: validate the POJO, collect all error messages and return a validation result

## Using

To add Malio to your project add the following dependecies to your pom: 

* **SNAPSHOT**
```XML
<dependency>
    <groupId>com.github.nalukit</groupId>
    <artifactId>malio</artifactId>
    <version>HEAD-SNAPSHOT</version>
</dependency>
<dependency>
    <groupId>com.github.nalukit</groupId>
    <artifactId>malio-processor</artifactId>
    <version>HEAD-SNAPSHOT</version>
    <scope>provided</scope>
</dependency>
```
* **GWT 2.9.0 (and newer)  - Release**
```XML
<dependency>
    <groupId>com.github.nalukit</groupId>
    <artifactId>malio</artifactId>
    <version>1.0.0</version>
</dependency>
<dependency>
    <groupId>com.github.nalukit</groupId>
    <artifactId>malio-processor</artifactId>
    <version>1.0.0</version>
    <scope>provided</scope>
</dependency>
```

## Usage

To force the creation of a validator, you need to add the `MalioValidator`-annotation to a POJO. Malio will create a validator that will check the POJO.

### Constraints

There are several annotations to trigger the creation of a constraint for a field in a POJO.

#### Not Null check


## Example
