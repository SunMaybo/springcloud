FROM 192.168.200.23:5002/micro-spring-cloud/java7:latest
#FROM 192.168.200.23:5002/micro-spring-cloud/nginx
VOLUME /tmp
RUN mkdir /app/
ADD *.jar /app/app.jar
ADD run.sh /app/
RUN bash -c 'touch /app/app.jar'
WORKDIR /app
RUN chmod a+x run.sh
CMD /app/run.sh
