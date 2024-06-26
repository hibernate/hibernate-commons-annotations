Hibernate Commons Annotations
==================================================

Description
-----------

Hibernate Commons Annotations is a utility project used by several Hibernate projects;
as a user of Hibernate libraries you probably are not going to use this directly.

Its first scope is to support Java Generics type discovery.
Its second scope is to support Java Annotations overriding through XML files
(mainly but not conceptually limited to).

Requirements
------------
Since version 6.0 this project requires Java 11.

Release Instructions
------------

Ensure JIRA is up to date and reflecting the state of the branch being released.

Edit the properties file named `gradle/version.properties` to define the version.

Then run the publishing task:

    ./gradlew publish -PhibernatePublishUsername=hibernate_org_ossrh -PhibernatePublishPassword=verySecretPassword

Create a Tag and push it.

Navigate to https://oss.sonatype.org/ to close and release the staged repository.

Reset the `gradle/version.properties` to the next snapshot version for the current branch, push it as well.


Contact
------------

Latest Documentation:

This project has no documentation per se, because of its internal use focus.
Please ask questions to the technical support forum.

Bug Reports:

   Hibernate JIRA (preferred): https://hibernate.atlassian.net

Or contact us via chat, mailing list, etc as described on:

   http://hibernate.org/community/

Free, volunteers based technical support:

   https://discourse.hibernate.org


Notes
-----------

If you want to contribute, go to http://www.hibernate.org/

This software and its documentation are distributed under the terms of the
Apache License Version 2.0.
N.B. older versions of this library used a different license: LGPL 2.1.
