# Justificativa para Inclusão no Portfólio: Encurtador de URL

O projeto do Encurtador de URL desenvolvido com Spring Boot e MongoDB representa um excelente desafio para portfólio, pois vai além de um simples CRUD (Create, Read, Update, Delete), incorporando diversas lógicas de negócio e conceitos de design de sistema relevantes.

## Lógica de Negócio e Desafios Técnicos Específicos

1.  **Geração de URLs Curtas (Hashids):**
    *   **Desafio:** A simples persistência de dados não garante URLs curtas e amigáveis. A utilização da biblioteca `Hashids` para converter IDs numéricos em hashes curtos e únicos introduz uma complexidade adicional e demonstra a capacidade de integrar soluções de terceiros para problemas específicos.
    *   **Valor para Portfólio:** Mostra a habilidade de implementar algoritmos de hashing e lidar com a geração de identificadores únicos de forma eficiente e segura, além de considerar a experiência do usuário.

2.  **Expiração Automática de URLs (TTL Index):**
    *   **Desafio:** Implementar um mecanismo eficiente para remover URLs expiradas é crucial para a manutenção do banco de dados e a validade do serviço. Ao invés de um agendamento manual ou tarefa em segundo plano complexa, o uso de um índice Time-To-Live (TTL) do MongoDB mostra uma solução elegante e otimizada a nível de banco de dados.
    *   **Valor para Portfólio:** Demonstra conhecimento de recursos avançados de bancos de dados NoSQL e a preocupação com a otimização de recursos e a "saúde" do sistema ao longo do tempo.

3.  **Gerador de Sequência Customizado:**
    *   **Desafio:** Garantir a singularidade e a sequência de IDs em um ambiente distribuído ou mesmo em um banco de dados NoSQL que não impõe auto-incremento (como o MongoDB por padrão) requer uma solução robusta. O `SequenceGenerator` customizado que interage com o MongoDB aborda diretamente esse problema.
    *   **Valor para Portfólio:** Evidencia a compreensão de padrões de geração de ID em sistemas distribuídos e a implementação de serviços de infraestrutura essenciais.

4.  **Redirecionamento Inteligente:**
    *   **Desafio:** O endpoint de redirecionamento (`GET /{hash}`) não é apenas uma leitura simples. Ele precisa decodificar o hash, buscar a URL original no banco de dados, verificar se a URL existe e se não expirou antes de realizar o redirecionamento HTTP (código 302/307).
    *   **Valor para Portfólio:** Ilustra a criação de lógica de controle de fluxo de requisições, manipulação de status HTTP e tratamento de condições de erro (URL não encontrada, expirada).

5.  **Configuração e Ambiente:**
    *   **Desafio:** Integrar a aplicação com Docker e Docker Compose para gerenciar o ambiente de desenvolvimento e produção do MongoDB mostra a capacidade de orquestrar serviços e preparar a aplicação para implantação. A utilização de variáveis de ambiente para credenciais de banco de dados é uma boa prática de segurança.
    *   **Valor para Portfólio:** Reflete competências em DevOps básicas, segurança de credenciais e conteinerização de aplicações.

## Benefícios Gerais para o Portfólio

*   **Spring Boot:** Experiência prática com um dos frameworks Java mais populares para desenvolvimento de microserviços.
*   **MongoDB:** Familiaridade com bancos de dados NoSQL e suas particularidades (documentos, índices TTL).
*   **Design de API RESTful:** Criação de endpoints claros e funcionais.
*   **Tratamento de Erros:** Implementação de retornos adequados para cenários de URLs inválidas ou expiradas.
*   **Boas Práticas de Código:** O projeto pode ser usado para demonstrar princípios SOLID, padrões de projeto e código limpo.
*   **Documentação:** Aprimorar o `README.md` e, potencialmente, incluir uma postagem de blog ou uma explicação técnica mais aprofundada, demonstra habilidades de comunicação e documentação.

Em resumo, este projeto vai muito além de um CRUD básico, abordando problemas reais de um serviço de encurtamento de URL e demonstrando uma gama de habilidades técnicas e de design de sistema que são altamente valorizadas na indústria.
