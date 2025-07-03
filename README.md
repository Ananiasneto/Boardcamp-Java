# BoardCamp - Sistema de Locadora de Jogos

API RESTful em Java com Spring Boot e PostgreSQL para gerenciar jogos, clientes e aluguéis em uma locadora de jogos de tabuleiro.

---

## Funcionalidades

- Gestão de jogos (listar, inserir com validação)  
- Gestão de clientes (listar, buscar por ID, inserir)  
- Gestão de aluguéis (listar, inserir, finalizar, apagar)  
- Tratamento global de erros  
- Testes unitários e de integração (mínimo 6 por entidade)

---

## Endpoints Principais

**Jogos:**  
- GET /games  
- POST /games  

**Clientes:**  
- GET /customers  
- GET /customers/{id}  
- POST /customers  

**Aluguéis:**  
- GET /rentals  
- POST /rentals  
- POST /rentals/return/{id}  
- DELETE /rentals/{id}  

---

## Tecnologias

Java 17 • Spring Boot • PostgreSQL • JUnit 5 • Mockito • Maven • Git

---
