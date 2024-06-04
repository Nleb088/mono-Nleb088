# Spring MVC Space Center

## Introduction & contexte

Nous allons dans cet atelier reprendre ce qui a été mis en place dans les ateliers précédents afin de créer una
application Spring MVC qui va exposer deux endpoints REST et une page web dans le but de pouvoir afficher et lancer nos
lanceurs orbitaux dans l'espace ou afficher les lancements précédemment effectués.

## Prérequis

- Développer en Java (**8 minimum**) et comprendre les API Stream et Optional (*Kotlin et Groovy sont tout à fait
  utilisables, mais les solutions ne sont pas disponibles dans ces langages*)
- Avoir suivi la formation Spring Core et Boot
- Connaître ce qu'est REST et comment est architecturée une API REST
  (notion de [ressources, verbes HTTP associés](https://restfulapi.net/resource-naming/)...)
- Avoir une idée de ce qu'est le patron [Modèle-Vue-Contrôleur](https://fr.wikipedia.org/wiki/Modèle-vue-contrôleur)

## Objectifs

- Appréhender comment déclarer un contrôleur (REST ou MVC simple)
- Savoir quelles annotations utiliser sur les méthodes chargées de gérer nos
  requêtes HTTP afin de produire des vues HTML ou du contenu exposé par une API REST
- Perfectionner l'utilisation de Spring MVC dans la mise en place d'une API REST
    - Savoir quelles sont les bonnes pratiques pour gérer les exceptions métier au sein d'une API REST
    - Apprendre comment développer des endpoints REST et connaître les bonnes
      pratiques concernant les paramètres d'entrée et de sortie liés à la
      requête/réponse HTTP
    - Intégrer la Bean Validation pour nos modèles de requête JSON
- Maîtriser comment tester nos endpoints MVC/REST avec `MockMvc` et les utilitaires fournis par Spring Boot

## Table des matières

1. [Creating REST endpoints](./1_creating-rest-endpoints/README.md)
2. [Adding a view](./2_adding-a-view/README.md)
3. [Producing binary data](./3_producing-binary-data/README.md)
4. [Handling exceptions](./4_handling-exceptions/README.md)
5. [Sharing resources](./5_sharing-resources/README.md)

## A propos des exercices

**Cette formation s'effectue dans l'idéal sur une durée d’environ _4h_ (une demi-journée). Si cela est nécessaire, vous
pouvez vous allouer une journée entière, mais il ne doit pas être nécessaire de vous allouer plus de temps : en cas de
difficultés, n'hésitez pas à faire signe à un formateur pour vous débloquer.**

_Chaque exercice possède sa solution, qui ne doit être consultée qu'à des fins de comparaison, ou lorsque vous êtes
bloqués._ :blush:

Bon courage !
