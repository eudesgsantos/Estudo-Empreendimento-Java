A dupla optou por fazer uso conexão Banco de Dados (Containerizado) e Aplicação (Containerizada)

Utilizando a docker network com nome de "net"

Comando de Build: "docker build -t empreendimento ."

Comando de Run: "docker run -d -p 8080:8080 --name empreendimento --network net empreendimento"


