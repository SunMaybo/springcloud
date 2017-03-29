FROM index.tenxcloud.com/tenxcloud/ubuntu:latest


#JAVA1.8

ADD ./jdk-8u91-linux-x64.tar.gz /usr/local/

RUN ln -s /usr/local/jdk1.8.0_91 /usr/local/java1.8

ENV JAVA_HOME /usr/local/java1.8

ENV CLASSPATH $JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar

ENV PATH $PATH:$JAVA_HOME/bin

RUN echo "JAVA_HOME=/usr/local/java1.8\nCLASSPATH=JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar\nPATH=$PATH:$JAVA_HOME/bin" >> /etc/profile
