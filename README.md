# learning
Repository for uploading learning code

# author
malberquilla

# Commands
Remove dangling images

    docker images -f dangling=true -q | xargs docker rmi