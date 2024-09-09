# Data Resilience with Spring and RabbitMQ Eventing Streaming
This is the repository for the LinkedIn Learning course `Data Resilience with Spring and RabbitMQ Eventing Streaming`. The full course is available from [LinkedIn Learning][lil-course-url].

![lil-thumbnail-url]

RabbitMQ is a widely deployed messaging solution and Spring is a popular framework for building modern Java applications. In this course, Gregory Green explains how Spring and RabbitMQ can be used to build a resilient data architecture for critical applications. Learn about RabbitMQ features such as quorum queues, streams, and multi-site replication. Gregory also reviews Spring projects like Spring Cloud and AMQP that simplify building production-ready applications. Plus, learn how Kubernetes improves resiliency for Spring applications and RabbitMQ.

_See the readme file in the main branch for updated instructions and information._
## Instructions
This repository has branches for each of the videos in the course. You can use the branch pop up menu in github to switch to a specific branch and take a look at the course at that stage, or you can add `/tree/BRANCH_NAME` to the URL to go to the branch you want to access.

## Branches
The branches are structured to correspond to the videos in the course. The naming convention is `CHAPTER#_MOVIE#`. As an example, the branch named `02_03` corresponds to the second chapter and the third video in that chapter. 
Some branches will have a beginning and an end state. These are marked with the letters `b` for "beginning" and `e` for "end". The `b` branch contains the code as it is at the beginning of the movie. The `e` branch contains the code as it is at the end of the movie. The `main` branch holds the final state of the code when in the course.

When switching from one exercise files branch to the next after making changes to the files, you may get a message like this:

    error: Your local changes to the following files would be overwritten by checkout:        [files]
    Please commit your changes or stash them before you switch branches.
    Aborting

To resolve this issue:
	
    Add changes to git using this command: git add .
	Commit changes using this command: git commit -m "some message"


[0]: # (Replace these placeholder URLs with actual course URLs)

[lil-course-url]: https://www.linkedin.com/learning/data-resilience-with-spring-and-rabbitmq-event-streaming
[lil-thumbnail-url]: https://media.licdn.com/dms/image/v2/D4E0DAQGcf2HhlwJPxg/learning-public-crop_675_1200/learning-public-crop_675_1200/0/1724448476620?e=2147483647&v=beta&t=0kijKM2zFiq9PtkHrG2Dee-6Ayx5xGwJZYvkHas5zoE

