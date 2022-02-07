FROM centos:7

LABEL version=0.0.1-SNAPSHOT
LABEL description="This is an apache image"

RUN yum -y update && yum -y install httpd && yum clean all

WORKDIR /var/www/html

COPY startbootstrap-freelancer-master .

ENV contenido prueba

RUN echo "$contenido" > prueba.html

RUN echo "$(whoami)" > user1.html

RUN useradd malberquilla

USER malberquilla

RUN echo "$(whoami)" > /tmp/user2.html

USER root

RUN cp /tmp/user2.html .

EXPOSE 80

COPY run.sh /run.sh

CMD sh /run.sh