# Malio

[![License](https://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
![GWT3/J2CL compatible](https://img.shields.io/badge/GWT3/J2CL-compatible-brightgreen.svg)
[![Join the chat at https://gitter.im/Nalukit42/Lobby](https://badges.gitter.im/Nalukit42/Lobby.svg)](https://gitter.im/Nalukit42/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.nalukit/malio.svg?colorB=44cc11)](https://search.maven.org/artifact/com.github.nalukit/malio)
[![Build & Deploy](https://github.com/NaluKit/malio/actions/workflows/build.yaml/badge.svg?branch=dev)](https://github.com/NaluKit/malio/actions/workflows/build.yaml)

Malio is a tiny framework to validate POJOs using annotations. It is easy to use by just adding annotations to members
of a POJO. No writing of validations or validators. Based on the annotations inside the POJO, the processor
generates a validator.

Each validator offers two methods for validation:

* **check**: check the POJO and as soon as an error is detected throw an exception
* **validate**: validate the POJO, collect all error messages and return a validation result

Here is an example using Malio:

```java
@MalioValidator
public class Person {

    @NotNull
    @NotBlank
    @MaxLength(20)
    private String name;

    @NotNull
    @NotBlank
    @MaxLength(value = 20, message = "First name too long!")
    private String firstName;

    @NotNull
    private Address address;

    public Person() {
    }

    // getters and setters ... 
}
```

and

```java
@MalioValidator
public class Address {

    @NotNull
    @NotBlank
    @MaxLength(20)
    private String street;
  
    @NotNull
    @NotBlank
    @MaxLength(5)
    private String zip;
  
    @NotNull
    @NotBlank
    @MaxLength(20)
    private String city;

    public Address() {
    }

    // getters and setters ... 
}
```

Running the validation:

```java
    Address address = new Address();
    // set values 
    Person person = new Person();
    person.setAddress
    // set values 
    
    // throws an exception in case of error ...
    PersonMalioValidator.INSTANCE.check(model);
```

## Usage

Let's assume, we have a POJO like this:

```java
public class Person {

    private String name;

    private String firstName;

    public Person() {
    }

    // getters and setters ... 
}
```

These are the steps to trigger the creation of a validator.

### Dependencies

To use Malio, add the following dependencies to your pom (in case they are missing):

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

The processor is only needed at compile time!

### Creating a Malio Validator

First, we need to trigger the creation of a validator. This is done by adding `MalioValidator` to the class:

```java
@MalioValidator
public class Person {

    private String name;

    private String firstName;

    public Person() {
    }

    // getters and setters ... 
}
```

Now, Malio will create a validator. Next we have to add constraints:

```java
@MalioValidator
public class Person {

    @NotNull
    @NotBlank
    @MaxLength(20)
    private String name;

    @NotNull
    @NotBlank
    @MaxLength(20)
    private String firstName;

    @NotNull
    private Address address;

    public Person() {
    }

    // getters and setters ... 
}
```

Malio will create a validator for the `Person` class. The validator will be called [name of the class] + 'MalioValidator'.
To validate an instance of the class, call:

`PersonMalioValidator.INSTANCE.check(myInstenceOfPerson);`

## How does Malio work?

Malio will generate for each variable and annotation a constraint class. Inside the validator, all constraints are
collected and processed. In case the type of a variable has a Malio validator, the validator of the type will be called.
In case classes are extending user classes, Malio will also look for existing validators of super classes.

## Custom Messages

Malio comes with predefined and localized messages, which can be set via `LocalizedMessages.INSTANCE.setMessages(new MessagesEN());`.
At the moment, malio only provides messages for english and german. Contributions are welcome! See [IMessages.java](malio%2Fsrc%2Fmain%2Fjava%2Fcom%2Fgithub%2Fnalukit%2Fmalio%2Fshared%2Fmessages%2FIMessages.java)
and [locales](malio%2Fsrc%2Fmain%2Fjava%2Fcom%2Fgithub%2Fnalukit%2Fmalio%2Fshared%2Fmessages%2Flocales).

The user can overwrite the dynamic messages with own static messages in *every* annotation. E.g., `@MaxLength`:

```java
@MaxLength(value = 20, message = "First name too long!")
private String firstName;
```

## Supported Annotations

These are the annotations provided by Malio:

### MalioValidator

```java
@MalioValidator
```

The `MalioValidator`-annotation can be added to a **class**. The annotation is necessary to trigger the creation of a
validator. Without this annotation no validator or constraint will be created!

This annotation can also be used with abstract classes.

### Annotations for variables

The following annotation can only be used on variable types.

#### Blacklist

```java
@Blacklist({"x", "y"})
```

The annotation accepts a list of String values that are not allowed for the field. The constrain will look for the
String values inside the variable and in case one of the values are found, create an error - if the value is not null.

This annotation can only be used on fields of type **String**.

#### DecimalMax

```java
@DecimalMax("0.5")
```

The annotation accepts a String value of the maximal value that is allowed for the field - if the value is not null.

This annotation can only be used on fields of type **BigDecimal**.

#### DecimalMin

```java
@DecimalMin("0.5")
```

The annotation accepts a String value of the minimal value that is allowed for the field - if the value is not null.

This annotation can only be used on fields of type **BigDecimal**.

#### Email

```java
@Email
```

The annotation check the value of the field for a formal valid email address - if the value is not null.

This annotation can only be used on fields of type **String**.

#### MalioIgnore

```java
@MalioIgnore
```

The annotation can be added to variable. It will tell Malio - in case there is a validator for the type of the
variable - to ignore the validator and not calling it. This annotation can be used to avoid circular validations.

#### MaxLength

```java
@MaxLength(20)
```

The annotation accepts a int value of the maximal numbers of characters. This is the maximal length allowed for the
variable - if the value is not null.

This annotation can only be used on fields of type **String**.

#### Max

```java
@Max(99)
```

The annotation accepts a long value of the maximal value of Number. This is the maximal length allowed for the
variable - if the value is not null.

This annotation can only be used on fields of type **Number** and their unboxed variants.

#### MinLength

```java
@MinLength(10)
```

The annotation accepts a int value of the minimal numbers of characters. This is the minimal length allowed for the
variable - if the value is not null.

This annotation can only be used on fields of type **String**.

#### Min

```java
@Min(10)
```

The annotation accepts a long value of the maximal value of Number. This is the maximum allowed for the
variable - if the value is not null.

This annotation can only be used on fields of type **Number** and their unboxed variants.

#### NotBlank

```java
@NotBlank
```

The annotation indicates that the String should not be empty - if the value is not null.

This annotation can only be used on fields of type **String**.

#### NotEmpty

```java
@NotEmpty
```

The annotation ensures that a `Collection` is not empty.

This annotation can only be used on fields of types extending **Collection** (List, Set, ...).

#### NotNull

```java
@NotNull
```

The annotation indicates that the variable should not be null.

This annotation can be used on any complex type of field.

#### RegExp

```java
@Regexp(regexp = "\\d{5}")
```

The annotation indicates that the value of the variable will be tested against the regular expression - if the value of
the variable is not null.

This annotation can only be used on fields of type **String**.

#### Size

```java
@Size(min=1, max=99)
```

The annotation defines the minimum and maximum size of a collection. The constraint will not be checked if the collection is null.

This annotation can only be used on fields of types extending **Collection** (List, Set, ...).

#### ArraySize

```java
@ArraySize(min=1, max=99)
```

The annotation defines the minimum and maximum size of an array. The constraint will not be checked if the array is null.

This annotation can only be used on fields of types **array** (int[], String[], ...).

#### UUID

```java
@Uuid
```

The annotation check the value of the field for a formal valid UUID - if the value is not null.

This annotation can only be used on fields of type **String**.

#### Whitelist

```java
@Whitelist({"x","y"})
```

The annotation accepts a list of String values that are allowed for the field. The constrain will look for the String
values inside the variable and in case a value is not contained inside the list, an error is created - if the value is
not
null.

This annotation can only be used on fields of type **String**.

### Annotations for variables of type array

The following annotation can only be used on variable types that are arrays.

#### ArrayItemNotNull

```java
@ArrayItemNotNull
```

The annotation indicates that an item of an array should not be null.

This annotation can be used on any array that uses a complex type of field.

## To get in touch with the developer

Please use the [Nalu Gitter room](https://gitter.im/Nalukit42/Lobby).

## Examples

Examples can be found inside the test cases.

## Notes

In case you find a bug, please open an issue or post it inside
the [Nalu Gitter room](https://gitter.im/Nalukit42/Lobby).
