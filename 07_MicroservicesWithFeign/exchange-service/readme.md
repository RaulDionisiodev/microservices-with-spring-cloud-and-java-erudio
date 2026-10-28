Markdown# Ambiente de Desenvolvimento

Este projeto utiliza **MySQL em um container Docker** centralizado na pasta `infra` para persistência de dados, **Adminer** para gerenciamento visual do banco pelo navegador e **Flyway** para versionamento do esquema do banco de dados em cada microsserviço.

As aplicações Java (microsserviços) utilizam Spring Boot, Spring Data JPA e Hibernate, sendo executadas diretamente na máquina (ou via IntelliJ) durante o desenvolvimento.

## Tecnologias

- Java + Spring Boot
- Spring Data JPA / Hibernate
- MySQL 8.4
- Docker Compose
- Adminer
- Flyway
- Maven

## Arquitetura

O ambiente de desenvolvimento é composto por:

- **Microsserviços Java:** executados localmente (ex: via IntelliJ).
- **MySQL:** executado em um único container Docker na pasta `infra`, disponibilizado na porta `3306`. O mesmo servidor abriga os bancos/schemas separados para cada microsserviço.
- **Adminer:** interface web para consultar e administrar os bancos, disponibilizada na porta `8081`.
- **Flyway:** responsável por aplicar e controlar as migrations do banco de dados de cada serviço de forma isolada.

O banco é persistido em um volume Docker, permitindo preservar os dados mesmo após a parada ou recriação dos containers.

## Pré-requisitos

Antes de iniciar, instale e configure:

- Java compatível com a versão do Spring Boot utilizada no projeto.
- Maven ou Maven Wrapper.
- Docker Engine.
- Docker Compose.

Confira as instalações:

```bash
java -version
./mvnw -version
docker --version
docker compose version 
```

Caso o projeto não tenha o Maven Wrapper, utilize mvn -version. 

# Configuração das variáveis de ambiente

As configurações globais da infraestrutura ficam no arquivo .env, localizado na pasta infra, junto ao compose.yaml.Exemplo de variáveis utilizadas:
```bash
MYSQL_ROOT_PASSWORD=root_dev_123
```

### Configurações para o Microsserviço A
```bash
MYSQL_DATABASE_A=banco_a
MYSQL_USER_A=servico_a
MYSQL_PASSWORD_A=senha_a_dev
```

### Configurações para o Microsserviço B
```bash
MYSQL_DATABASE_B=banco_b
MYSQL_USER_B=servico_b
MYSQL_PASSWORD_B=senha_b_dev
MYSQL_PORT=3306
ADMINER_PORT=8081
```

Os valores acima são exemplos para desenvolvimento local. Ajuste-os conforme a configuração dos projetos. O Docker Compose utiliza as variáveis do .env para subir a infraestrutura. O Spring Boot, por sua vez, recebe as variáveis de conexão por meio do ambiente do processo Java. Importante: o Docker Compose carrega o .env automaticamente para interpolar variáveis no arquivo Compose. Isso não significa que o Spring Boot carregará esse arquivo automaticamente. Não versione o arquivo .env se ele contiver credenciais. Mantenha-o no .gitignore e disponibilize um .env.example sem segredos reais quando necessário. 

# Iniciando a infraestrutura de banco de dados

Navegue até a pasta infra que contém o compose.yaml e execute:
```bash
compose up -d
```

O comando cria e inicia os containers em segundo plano. Verifique o estado dos serviços:
```bash 
compose ps 
```

Para acompanhar os logs do MySQL:
```bash
 compose logs -f mysql
 ```
Aguarde o banco estar saudável antes de iniciar as aplicações Java. 

# Parando o ambiente

Para parar e remover os containers sem excluir os dados persistidos:
```bash
 compose down
 ```

Para iniciá-los novamente:
```bash
 compose up -d
 ```

Atenção: evite docker compose down -v quando quiser preservar o banco, pois esse comando também remove o volume de dados.

# Acessando o banco pelo navegador

Com os containers ativos, acesse:http://localhost:8081. Utilize as informações correspondentes ao microsserviço que deseja inspecionar no formulário do Adminer (exemplo para o Serviço A):

| Campo | Valor |
| :--- | :--- |
| Sistema | MySQL |
| Servidor | `mysql` |
| Usuário | Valor configurado para `MYSQL_USER_A` |
| Senha | Valor configurado para `MYSQL_PASSWORD_A` |
| Banco de dados | Valor configurado para `MYSQL_DATABASE_A` |

O servidor deve ser mysql porque o Adminer se conecta ao MySQL pela rede interna do Docker Compose. Pelo Adminer, é possível consultar tabelas, visualizar registros, executar comandos SQL e inspecionar o histórico de migrations do Flyway.

# Conexão da aplicação Spring Boot

O arquivo src/main/resources/application.yml de cada microsserviço contém as configurações de conexão específicas para o seu respectivo banco. Exemplo para o Microsserviço A:
```YAMLspring:

  application:
    name: servico-a

  datasource:
    url: ${DB_URL:jdbc:mysql://localhost:3306/banco_a}
    username: ${DB_USERNAME:servico_a}
    password: ${DB_PASSWORD:senha_a_dev}

  flyway:
    enabled: true
    locations: classpath:db/migration
    validate-on-migrate: true

  jpa:
    hibernate:
      ddl-auto: validate
    open-in-view: false
    show-sql: true
    properties:
      hibernate:
        format_sql: true
```

A URL utiliza localhost:3306 porque a aplicação Java é executada diretamente na máquina (fora do Docker). As variáveis DB_URL, DB_USERNAME e DB_PASSWORD podem ser preenchidas com os valores correspondentes do arquivo .env da pasta infra. 

# Carregando o .env no terminal

O exemplo a seguir pressupõe que você esteja na raiz de um dos microsserviços e que o arquivo .env esteja na pasta infra:
```Bashset -a
source ../infra/.env
set +a

export DB_URL="$DB_URL_A"
export DB_USERNAME="$DB_USERNAME_A"
export DB_PASSWORD="$DB_PASSWORD_A"
```
```bash
./mvnw spring-boot:run
```

Se o caminho para o arquivo .env for diferente, ajuste o comando source. Essas variáveis ficam disponíveis no processo iniciado pelo terminal. Se executar a aplicação pela IDE (IntelliJ), configure as mesmas variáveis no ambiente de execução da IDE (Run Configurations -> Environment Variables).

# Versionamento do banco com Flyway

O Flyway gerencia as alterações do esquema por meio de migrations SQL versionadas. Cada microsserviço gerencia exclusivamente as suas próprias tabelas no seu respectivo schema. Os arquivos de migration de cada projeto devem ficar em: 
```
src/main/resources/
```

```
└── db/
    └── migration/
        ├── V1__create_initial_tables.sql
        ├── V2__add_description_column.sql
        └── V3__create_indexes.sql
```

Os nomes seguem o padrão: V<versão>__<descrição>.sql Por exemplo:

```sql

CREATE TABLE despesas (

    id BIGINT NOT NULL AUTO_INCREMENT,
    descricao VARCHAR(255) NOT NULL,
    valor DECIMAL(12, 2) NOT NULL,
    data_despesa DATE NOT NULL,
    PRIMARY KEY (id)
);
```

Ao iniciar o Spring Boot de um microsserviço, o Flyway verifica o histórico do seu respectivo banco e aplica as migrations pendentes. O histórico fica registrado na tabela flyway_schema_history daquele banco.

# Boas práticas

- Não altere migrations que já foram aplicadas em ambientes compartilhados; crie uma nova versão. 
- Use migrations para criar tabelas, modificar colunas, adicionar índices e executar alterações controladas.
- Mantenha spring.jpa.hibernate.ddl-auto: validate para que o Hibernate valide o esquema sem alterá-lo automaticamente.
- Garanta que o usuário configurado tenha as permissões necessárias para executar as migrations.
- Mantenha as migrations versionadas junto ao código da aplicação.

# Executando a aplicação

Depois que o MySQL estiver ativo na pasta infra, execute o microsserviço desejado: 
```bash
./mvnw spring-boot:run
```

O Spring Boot iniciará a aplicação, o Flyway aplicará as migrations pendentes e o Hibernate validará o mapeamento das entidades. Para compilar e executar os testes antes de iniciar:
```bash
./mvnw clean verify
```

# Solução de problemas

- A porta 3306 já está em uso: Verifique se existe outro servidor MySQL ativo na sua máquina:
```bash
sudo ss -ltnp | grep 3306
```

Se necessário, encerre o outro serviço ou altere a porta mapeada no compose.yaml da pasta infra e atualize a URL JDBC.

- Erro de autenticação no MySQL: Confira as credenciais do .env na pasta infra e as configurações do application.yml do microsserviço. Se o volume já tiver sido inicializado anteriormente, alterar as variáveis de ambiente no .env não modifica automaticamente as credenciais já criadas no MySQL. Se precisar recriar tudo do zero com as novas credenciais, utilize:
```bash
compose down -v
docker compose up -d
```
 - O Flyway informa que uma tabela ou coluna está ausente: Confira se a migration responsável pela estrutura foi criada e aplicada. Consulte a tabela flyway_schema_history do respectivo banco pelo Adminer e verifique os logs da aplicação. 
 - O Hibernate informa divergência no esquema: Como ddl-auto está definido como validate, o Hibernate não corrige automaticamente o banco. Crie uma nova migration para ajustar o esquema e mantenha as entidades JPA alinhadas à estrutura final.
 
# Segurança

Esta configuração destina-se ao desenvolvimento local. Não utilize as senhas de exemplo em produção. Não versione credenciais reais. Mantenha o MySQL e o Adminer restritos à máquina local durante o desenvolvimento. Não utilize o Adminer como ferramenta de alteração manual do esquema em ambientes controlados por migrations.

# Referências
Docker Compose: https://docs.docker.com/compose/ 
MySQL: https://dev.mysql.com/doc/
Adminer: https://www.adminer.org/ 
Spring Boot: https://docs.spring.io/spring-boot/
Flyway: https://documentation.red-gate.com/flyway
