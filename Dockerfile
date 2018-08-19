FROM java:8

# preserve Java 8  from the maven install.
RUN mv /etc/alternatives/java /etc/alternatives/java8
RUN apt-get update && apt-get install maven -y

# Restore Java 8
RUN mv -f /etc/alternatives/java8 /etc/alternatives/java
RUN ls -l /usr/bin/java && java -version

ENV INSTALL_DIR /opt
ENV GLASSFISH_ARCHIVE glassfish5
ENV GLASSFISH_HOME ${INSTALL_DIR}/${GLASSFISH_ARCHIVE}/

ENV DOMAIN_NAME domain1
ENV INSTALL_DIR /opt
RUN useradd -b /opt -m -s /bin/sh -d ${INSTALL_DIR} serveradmin && echo serveradmin:serveradmin | chpasswd
RUN curl -o ${INSTALL_DIR}/${GLASSFISH_ARCHIVE}.zip -L http://download.oracle.com/glassfish/5.0/release/glassfish-5.0.zip \
    && unzip ${INSTALL_DIR}/${GLASSFISH_ARCHIVE}.zip -d ${INSTALL_DIR} \
    && rm ${INSTALL_DIR}/${GLASSFISH_ARCHIVE}.zip \
    && chown -R serveradmin:serveradmin /opt \
    && chmod -R a+rw /opt

WORKDIR ${GLASSFISH_HOME}/bin

ENV EMAIL teste@teste.com
ENV PASSWORD 6320632ea13bd1
ENV EMAIL_USER bc82647d48b758
ENV EMAIL_PORT 2525
ENV EMAIL_HOST smtp.mailtrap.io

ENTRYPOINT sh asadmin start-domain ${DOMAIN_NAME}  && \
            sh asadmin create-javamail-resource --mailhost smtp\.mailtrap\.io \
            --mailuser sample --fromaddress sample\@test\.com --property \
            mail.smtp.host=${EMAIL_HOST}:mail.smtp.port=${EMAIL_PORT}:mail.smtp.pass=${PASSWORD}:mail.smtp.user=${EMAIL_USER}:mail.smtp.auth=true:mail.address=${EMAIL} \
            mail/MyMailSession \
            && sh asadmin stop-domain ${DOMAIN_NAME} && sh asadmin start-domain --verbose ${DOMAIN_NAME}


USER serveradmin
EXPOSE 4848 8009 8080 8181

COPY ./ /etc/event-cdi-example


ENV DEPLOYMENT_DIR ${GLASSFISH_HOME}glassfish/domains/domain1/autodeploy
ENV PROJECT_HOME /etc/event-cdi-example

USER root

RUN  chmod -R 777 ${PROJECT_HOME}

RUN  cd ${PROJECT_HOME} && mvn clean install && ls ${PROJECT_HOME}/target/

RUN  cp ${PROJECT_HOME}/target/event-cdi-example.war ${DEPLOYMENT_DIR}


#CMD ./asadmin start-domain --verbose ${DOMAIN_NAME}



