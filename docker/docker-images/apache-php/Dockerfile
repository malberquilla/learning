FROM centos:7

ENV HTML_DIR /var/www/html

RUN yum -y install httpd \
    php php-cli php-common \
    mod_ssl openssl

RUN echo "<?php phpinfo(); ?>" > ${HTML_DIR}/hola.php

COPY ssl.conf /etc/httpd/conf.d/default.conf

COPY docker.crt /docker.crt

COPY docker.key /docker.key

EXPOSE 443

CMD apachectl -DFOREGROUND