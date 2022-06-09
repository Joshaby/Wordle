# Wordle

Implementação em Java usando Spring "não tão boa"(pelo menos na minha opinião) do jogo Wordle

## Execução do programa

Abra o terminal e digite `mvn spring-boot:run` para execução do programa

## Notas

A aplicação tem dois ambientes, test e dev. Para mudar de ambiente, edite a linha spring.profiles.active do arquivo application.properties. Sobre os ambientes, o test usa banco H2 e o dev usa banco MySQL. O banco H2 e banco MySQL toda vez é populado pelo `import.sql`ao iniciar o programa. 

### Como usar o banco H2?

Abra o link `http://localhost:8080/h2-console` no seu navegador, em Driver Class org.h2.Driver, em JDBC URL coloque `jdbc:h2:mem:testdb`, em User Name coloque `sa` e em password deixe vazio. Após isso, você tera acesso as tabelas usada pelo programa