Polls
=====

API for managing polls for hackathon project.

Running locally
---------------

1.  Get and build the code

        $ git clone git@github.com:bentonporter/polls.git
        $ cd polls
        $ mvn clean install

2.  Run the service

    ... In Intelij:

        * Install the Lombok plugin in IntelliJ.
        * Create a run configuration with:
          * Main Class:  com.bazaarvoice.polls.PollsApplication
          * Program Arguments:  server config.local.yaml
          * Working Directory: $MODULE_DIR$
          * Use classpath of module:  service

    ... or via command line:

        $ cd service
        $ java -jar target/service-*.jar server config.local.yaml

One time thing
--------------

Create the DB table:

        $ cd service
        $ java -jar target/service-*.jar db migrate config.local.yaml