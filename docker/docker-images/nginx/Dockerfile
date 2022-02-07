FROM nginx

RUN useradd malberquilla

COPY fruit /usr/share/nginx/html

ENV archivo docker

WORKDIR /usr/share/nginx/html

EXPOSE 90

LABEL version=1

USER malberquilla

RUN echo "I'm user $(whoami)" > /tmp/me.html

USER root

RUN cp /tmp/me.html .

VOLUME /var/log/nginx

CMD nginx -g 'daemon off;'