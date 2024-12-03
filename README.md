# MyStudentHub

O projeto MyStudentHub parte da iniciativa de intercalar duas matérias, FRONT-END & BACK-END respectivamente. Com o intuito de aprimorar os conhecimentos e propor um desafio para os alunos foi proposto em sala que em duplas criasse um ambiente de comunicação entre essas duas partes distintas até então.

## Projeto:
O objetivo era realizar uma operação **CRUD (Criar, Ler, Editar e Deletar)** que consistem em operações básicas de manipulação de dados. A API construida em java teria que lidar com essas requisições, o Banco de Dados é responsável por abrigar essas informações, bem como fornece-lás e, por último, o FRONT consiste em um interface amigável para o cliente que deseja realizar essas operações.


## Desenvolvedores

- [@Thiago-P-Sampaio](https://github.com/Thiago-P-Sampaio)
- [@fearaujo293](https://github.com/fearaujo293)

## Ferramentas

<div align="left">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/javascript/javascript-original.svg" height="40" alt="javascript logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" height="40" alt="java logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mysql/mysql-original.svg" height="40" alt="mysql logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/html5/html5-original.svg" height="40" alt="html5 logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/css3/css3-original.svg" height="40" alt="css3 logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/azure/azure-original.svg" height="40" alt="azure logo"  />
</div>

###


## MyStudent.com

Portanto, ao lado de [@fearaujo293](https://github.com/fearaujo293), decidimos basear nossos procedimentos CRUD contextualizando em um ambiente de gerenciamento escolar. Portanto, é isso que se trata ***MyStudentHub***.  \
Situado em um ambiente de controle escolar, nosso projeto busca auxiliar a equipe docente no controle dos alunos fornecendo um lista com os dados referentes a:

- Nome
- Email
- Telefone
- RM(matrícula)
- Foto de perfil
- Nome do responsável

### Funcionalidades

- Adicionar novo aluno (Nome, Email, Telefone, RM, Responsável, Foto);
- Remover aluno;
- Ver lista com alunos;
- Editar informações do aluno (Nome, Email, Telefone, RM, Responsável, Foto);
- Expandir informações do aluno (Janela Pop-up);
- Exibir a quantidade de alunos cadastrados;
- Buscar aluno(s) pelo nome.

## Como usar:
#### Instalação

Clone o repositório com git bash

```bash
git clone https://github.com/Thiago-P-Sampaio/MSHUB-definitive_version.git
```

#### Configure o ``` aplication.properties```
Defina as varáveis de ambiente:

| Variável  |   Descrição                           |
| :----------|  :---------------------------------- |
| `${BD_URL}`  | **Obrigatório**. URL do seu BD |
| `${BD_USERNAME}`  | **Obrigatório**. Username do seu BD |
| `${BD_PASSWORD}`  | **Obrigatório**. senha do seu BD |
| `${CLOUD_NAME}`  | **Obrigatório**. Configurações CLOUDNARY |
| `${CLOUD_NAME_KEY}`  | **Obrigatório**.  Configurações CLOUDNARY: Sua key |
| `${CLOUD_NAME_SECRET}`  | **Obrigatório**.  Configurações CLOUDNARY: Sua SECRET |

```Java
 spring.application.name=api
 spring.datasource.url=${BD_URL}
 spring.datasource.username=${BD_USERNAME}
 spring.datasource.password=${BD_PASSWORD}
 spring.jpa.hibernate.ddl-auto=update


CLOUDINARY_CLOUD_NAME=${CLOUD_NAME}
CLOUDINARY_API_KEY=${CLOUD_NAME_KEY}
CLOUDINARY_API_SECRET=${CLOUD_NAME_SECRET}
```
Para o upload de imagens, busquei utilizar um serviço de hospedagem gratuita, e encontrei a [**Cloudnary**](https://cloudinary.com/), por isso precisamos definir as configurações da API desse serviço cloud para prosseguir com nossas operações se quisermos salvar as imagens dos alunos.


### Documentação da API

#### Retorna todos os itens

```
  GET: http:/localhost:8080/mshub/get
```
##
#### Retorna um item

```
  GET http://localhost:8080/mshub/get/{id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `UUID` | **Obrigatório**. O ID do aluno que você quer |

###
#### Retorna a quantidade de alunos


```
  GET http://localhost:8080/mshub/get/count
```
 Utilizado uma **VIEW** no banco para criação desse método:
 ```SQL
 CREATE VIEW quant_alunos AS
SELECT COUNT(*) FROM aluno;


SELECT * FROM quant_alunos;
```
###
#### Retorna um ou mais alunos


```
  GET http://localhost:8080/mshub/get/buscar?nome=  
```
| Parâmetro   | Descrição                                   |
| :---------- | :------------------------------------------ |
| `nome`       | Passe o nome do aluno |

 Utilizado uma **PROCEDURE** no banco para criação desse método:
 ```SQL
DELIMITER $$

CREATE PROCEDURE buscar_aluno (
    IN aluno_nome TEXT
)
BEGIN
    SELECT * 
    FROM aluno
    WHERE nome LIKE CONCAT('%', aluno_nome, '%');
END$$

DELIMITER ;

CALL buscar_aluno('thiago');
```

###
####  Envia dados(objeto) para o banco

```
  POST: http:/localhost:8080/mshub/new
```
| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `nome`      | `formData` | **Obrigatório**. Passe o nome como JSON  |
| `email`      | `formData` | **Obrigatório**. Passe o email como JSON  |
| `telefone`      | `formData` | **Obrigatório**. Passe o telefone como JSON  |
| `responsavel`      | `formData` | **Obrigatório**. Passe o responsável como JSON  |
| `matricula`      | `formData` | **Obrigatório**. Passe a matricula como JSON  |
| `file`      | `formData` | **Obrigatório**. Passe a foto como **FILE**  |


###
####  Atualizar dados(objeto) do banco

```
  PUT: http:/localhost:8080/mshub/update/{id}
```
```
  PUT: http:/localhost:8080/image/updt/{id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `file`      | `formData` | **Obrigatório**. Passe o ID do aluno que quer mudar a foto |
| `id`      | `UUID` | **Obrigatório**. O ID do aluno que você quer |

###
####  Deletar dados(objeto) do  banco

```
  DELETE: http:/localhost:8080/mshub/delete/{id}
```
```
  DELETE: http:/localhost:8080/image/del/{id}
```


| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `UUID` | **Obrigatório**. O ID do aluno que você quer |

###

## Comandos utilizados no git

- `git init` Iniciar um repositório local ;
- `git status` Observar os status dos arquivos (untracked,unmodified, modified, staged);
- `git add .` Rastrear todos os arquivos e/ou todas as alterações e preprar para comitar;
- `git commit -m ""` Comitar com comentário as alterações;
- `git branch <nome>` Ramificar projeto em outras branchs independentes;
- `git log` Consultar os commits;
- `git checkout <nome>` Transitar entre branches;
- `git show <id_commit>` Mostrar as alterações daquele commit;
- `git merge <branch_atual> <outra>` Mesclar duas branches ;
- `git remote add origin <link_do_repositorio>` Adicionar repositório remoto;
- `git push <repositorio_remoto> <branch>` Mandar os commits de um branch para o repositório remoto;
- `git fetch <repositorio_remoto> <branch>` Puxar do repositório remoto as últimas alterações;
- `git pull <>` Puxar do repositório remoto alguma branch;
- `git diff` Ver diferenças entre o último commit e a situação atual do seu código;
- `git clone <link_do_repositorio>` Clonar repositório remoto;

  ###
## Teste localhost:8080
<pre>
src  
└── main  
    ├── java  
    │   └── com.studenthub.api  
    │       ├── Config  
    │       ├── controller  
    │       │   ├── AlunoController  
    │       │   └── ControllerImage  
    │       ├── domain  
    │       │   ├── Aluno  
    │       │   ├── dto  
    │       │   ├── repository  
    │       │   └── Service  
    │       └── ApiApplication  
    └── resources  
        ├── static  
        └── application.properties  

</pre>

Inicie a aplicação e busque no seu navegador por:
```
http://localhost:8080
ou
http://localhost:8080/index.html
```
###

## Deploy

O deploy tanto do Back-End quanto do Front-End foi feito através do serviço da Azure. Utilizando a conta acadêmica, primeiro foi necessário:
>1. Criar em Serviços de Aplicativos um serviço web
>2. Configurá-lo definindo: nome, criando grupo de serviços, SDK, localização, S.O e >gratuidade com infraeestrutura compartilhada.
>3. Configurar o serviço para que possa receber o package ".jar" via FTP
>4. Autenticar no app WinSCP com as credênciais da cloud
>5. Transferir o package ".jar" para a cloud
>6. Iniciar a aplicação

 > **Obs:** O banco de dados foi hospedado no serviço online gratuito chamado [**Aiven**](https://aiven.io/)

 #### Observações:
 Antes de fazer o build de sua aplicação certifique-se:
 >1. Alterar o `localhost:8080` pela URL do serviço para Deploy
 >2. Troque `http` por **`https`**
 >3. Certifique-se que foi passado as credenciais do banco em nuvem;
