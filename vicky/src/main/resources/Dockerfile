FROM gradle:4.10.2-jdk8
MAINTAINER Pierre Vincent

# Set Appropriate Environmental Variables
ENV GRADLE_HOME /usr/bin/gradle
ENV PATH $PATH:$GRADLE_HOME/bin

WORKDIR /opt
USER root

# Add github key to known hosts
RUN mkdir -p ~/.ssh
RUN ssh-keyscan -t rsa github.com >> ~/.ssh/known_hosts

# Checkout code and build it
RUN git clone -b master https://github.com/devadmin-com/vicky.git
COPY src/application.yml /opt/vicky/vicky/src/main/resources/application.yml
WORKDIR /opt/vicky/vicky
RUN gradle clean
RUN gradle build

# Set ports to expose
EXPOSE 5005
EXPOSE 8080

# Set application to run
ENTRYPOINT ["java", "-jar", "/opt/vicky/vicky/build/libs/vicky-0.0.1.SNAPSHOT.jar"]

