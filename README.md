# Jerba CMS

[![Build Status](https://travis-ci.org/santiagolizardo/jerba.svg?branch=master)](https://travis-ci.org/santiagolizardo/jerba)

Simple Content Management System written in Java and designed to be deployed in a Google App Engine server.

## Requirements

  - Java Development Kit 11
  - Google App Engine SDK 1.9 for Java
  - Maven 3.5+
  - Bower JS

### How to try it

```sh
$ cp src/main/webapp/WEB-INF/jerba-config-example.xml src/main/webapp/WEB-INF/jerba-config.xml
$ mvn compile
$ mvn package
$ mvn appengine:run
```

### How to deploy it to Google App Engine

```sh
$ mvn appengine:deploy -Dapp.deploy.projectId=YOUR-PROJECT-ID -Dapp.deploy.version=YOUR-PROJECT-VERSION
```

### Other useful commands

```sh
$ mvn versions:display-dependency-updates # Check for dependency updates
```

