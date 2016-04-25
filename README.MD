# XML handler for JSON libraries

[![Build Status](https://travis-ci.org/markenwerk/java-utils-json-handler-xml.svg?branch=master)](https://travis-ci.org/markenwerk/java-utils-json-handler-xml)
[![Coverage Status](https://coveralls.io/repos/github/markenwerk/java-utils-json-handler-xml/badge.svg?branch=master)](https://coveralls.io/github/markenwerk/java-utils-json-handler-xml?branch=master)
[![Dependency Status](https://www.versioneye.com/user/projects/571e48cdfcd19a0039f1805b/badge.svg)](https://www.versioneye.com/user/projects/571e48cdfcd19a0039f1805b)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.markenwerk/utils-json-handler-xml/badge.svg)](https://maven-badges.herokuapp.com/maven-central/net.markenwerk/utils-json-handler-xml)
[![Issues](https://img.shields.io/github/issues/markenwerk/java-utils-json-handler-xml.svg)](https://github.com/markenwerk/java-utils-json-handler-xml/issues)
[![MIT License](https://img.shields.io/badge/license-MIT-brightgreen.svg)](https://github.com/markenwerk/java-utils-json-handler-xml/blob/master/LICENSE)

## Overview

This library provides XML related JSON handlers for other JSON processing libraries.

Consult the [documentation](http://markenwerk.github.io/java-utils-json-handler-xml/javadoc/index.html) and the [usage description](#usage) for further information:

- [XML document handler](#xml-document-handler)

## Maven

This library is hosted in the [Maven Central Repository](https://maven-badges.herokuapp.com/maven-central/net.markenwerk/utils-json-handler-xml). You can use it with the following coordinates:

```xml
<dependency>
	<groupId>net.markenwerk</groupId>
	<artifactId>utils-json-handler-xml</artifactId>
	<version>1.0.0</version>
</dependency>
```
 
## Usage

### XML document handler

A [`XmlDocumentJsonHandler`][XmlDocumentJsonHandler] is a [`JsonHandler`][JsonHandler] that creates a XML [`Document`][Document] that represents the described JSON document using the following rules:

- A JSON array is represented as a tag with name `array`.
- A JSON object is represented as a tag with name `object`.
- A JSON object entry is represented as a tag with name `entry`. The name of the JSON object entry is represented as an attribute with name `name`.
- A JSON null is represented as a tag with name `null`.
- A JSON boolean is represented as a tag with name `boolean`. The value of the JSON boolean represented as an attribute with name `value`.
- A JSON number is represented as a tag with name `number`. The value of the JSON boolean represented as an attribute with name `value`.
- A JSON string is represented as a tag with name `string`. The value of the JSON boolean represented as an attribute with name `value`.

```java
// a JsonDocument
JsonDocument document = ...

// returns a xml document  
Document xmlDocument = document.handle(new XmlDocumentJsonHandler());
```

The returned XML [`Document`][Document] for the [`example.json`](https://gist.github.com/toKrause/208992e7625f0b8a9d04e7283f09fc26)has the following content:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<object>
   <entry name="null">
      <null />
   </entry>
   <entry name="boolean">
      <boolean value="true" />
   </entry>
   <entry name="longValue">
      <number value="-42" />
   </entry>
   <entry name="double">
      <number value="-23.42" />
   </entry>
   <entry name="array">
      <array>
         <string value="foo" />
         <string value="bar" />
      </array>
   </entry>
</object>
```

[XmlDocumentJsonHandler]: http://markenwerk.github.io/java-utils-json-common/index.html?net/markenwerk/utils/json/common/handler/XmlDocumentJsonHandler.html

[JsonHandler]: http://markenwerk.github.io/java-utils-json-handler/index.html?net/markenwerk/utils/json/handler/JsonHandler.html

[Document]: https://docs.oracle.com/javase/8/docs/api/index.html?org/w3c/dom/Document.html

