rm -rf $PWD/deployments/local/runtime/rabbitmq/persistence

docker run --rm -v $PWD/deployments/local/runtime/rabbitmq/persistence:/bitnami/rabbitmq/mnesia  --name rabbitmq-server --rm  -p 5552:5552  -p 15672:15672 -p 5672:5672 -e RABBITMQ_USERNAME=app -e RABBITMQ_ENABLED_PLUGINS_FILE=/bitnami/rabbitmq/external_plugins/demo_plugins.txt  -e RABBITMQ_SERVER_ADDITIONAL_ERL_ARGS='-rabbitmq_stream advertised_host localhost' -e RABBITMQ_PASSWORD=app -e RABBITMQ_MANAGEMENT_ALLOW_WEB_ACCESS=true  bitnami/rabbitmq:latest


docker exec rabbitmq-server rabbitmq-plugins enable rabbitmq_stream
docker exec rabbitmq-server rabbitmq-plugins enable rabbitmq_management