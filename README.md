# UfoNetwork
Social network for interplanetary communication

The user has the ability to search and view the profiles of other users and groups without registering / authenticating on the network. Search can be carried out either by one parameter of a user or group, or by several at once.

When registering, the user must use a unique login, as well as indicate his race (choose from existing or create a new one), a planet (choose from existing or specify a new one), gender, age. Also, the user can upload the png-file, for use as an avatar. The system assigns to each registered user a unique ID, and also records the date and time of registration.
An authenticated user can edit information about himself, with the exception of the ID and registration time, which are not available to the user.

An authenticated user can be in an unlimited number of groups, as well as create different groups, leave messages in them and delete their own messages.

The user who created the group can exclude from it any other user and delete any message written in it.

When creating a group, the user must specify the name of the group and select the category of the group from the existing list. The group has a unique identifier (ID), stores a link to the user who created it, and can store a user-uploaded png file that can be used as an avatar of the group. P
The user who created the group can change its name, category, avatar.

[Here](https://qc63nr.axshare.com/#g=1) you can see the first version of prototype.

We choose [Apache Tomcat](http://tomcat.apache.org) as application server, because ti's free and easy to setup and use.

Also we choose [PostgreSQL](https://www.postgresql.org) as DBMS, because it's free for enterprise also and have a lot of preset functions.
