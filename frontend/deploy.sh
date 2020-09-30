docker build -f Dockerfile -t kloud-web:latest . \
 && docker rm -f kloud-web; docker run -td -p 80:5000 --name kloud-web kloud-web
