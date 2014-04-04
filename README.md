Polls
=====

API for managing polls for hackathon project.


API Documentation
-----------------

#### Create a poll

```
POST /polls
```

And send the Poll that you want to create as JSON in the request body:

```
{
  "productId": "prod001",
  "questionText": "Should I buy this product?"
}
```

This call will return the ID of the new Poll.

#### Vote on a poll

You can vote "yes" or "no" on a poll as follows, specifying the poll ID that was returned
when you created the poll.

To vote "yes":
```
GET /polls/{pollId}/yes
```

To vote "no":
```
GET /polls/{pollId}/no
```

#### Get a poll

```
GET /polls/{pollId}
```

#### Get all polls for a given product

```
GET /polls/product/{productId}
```


Developing
----------

#### Running locally

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


#### Database

To connect to the database:

- host: hackathon.cudqte3kvjgc.us-east-1.rds.amazonaws.com
- username: admin
- password: voC7oj2D
- port: 3306
- database: hackathon

If the database does not yet exist, here's how to create it:

    $ cd service
    $ java -jar target/service-*.jar db migrate config.local.yaml


Misc
----

- Gmail info:
    - www.gmail.com
    - pollHackathon@gmail.com
    - JukNimp3

- Facebook info:
    - www.facebook.com
    - bazaarvoicefb@facebook.com
    - JukNimp3
