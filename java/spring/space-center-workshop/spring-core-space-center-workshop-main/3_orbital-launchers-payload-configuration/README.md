
# Spring Core Space Center - Orbital Launchers Configuration

## A propos de l'exercice

L’objectif de cet exercice est de comprendre et d’utiliser les mécanismes que Spring met à disposition du développeur
afin de variabiliser les configurations utilisées dans une application Spring, généralement au sein des beans. Nous
allons associer les différents lanceurs que nous avons créés lors de l'étape précédente afin qu'ils contiennent le poids
maximal nécessaire que chacun autorise dans le but de suivre
une [orbite terrestre basse](https://en.wikipedia.org/wiki/Low_Earth_orbit).

Dans un premier temps, il est nécessaire de visualiser le contexte d’application Spring comme un conteneur qui contient
une **liste de beans**, ainsi qu’un **environnement**. Cet environnement se base sur des sources de propriétés
(_PropertySources_).

> Qu’est-ce qu’une source de propriétés ?

Dans Spring, une PropertySource est une abstraction permettant de représenter une source de valeurs présentés sous la
forme clé/valeur. L'idée est de pouvoir modéliser différentes origines de configurations telles que des fichiers,
variables d’environnement, dictionnaire distant… Les implémentations sont diverses et permettent à Spring de s’adapter à
tous les cas d'usages possibles pour lui passer des configurations externes : contexte de Servlet, variables
d'environnement système, arguments de ligne de commande, fichiers de configuration, JNDI…

**Néanmoins, nous ne travaillerons ici qu’avec des fichiers _.properties_ dans cet exercice, les fichiers YAML (
et `@ConfigurationProperties` associées) sont réservés à Spring Boot**

Nous allons ajouter la configuration nécessaire afin de déclarer des fichiers de configuration personnalisés contenant
des valeurs à injecter dans chacun des lanceurs orbitaux. Il suffit d'annoter au sein d'une classe de configuration (
annotée avec `@Configuration`) avec `@PropertySources` pour ajouter de multiples `@PropertySource` :

```java
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("..."),
        @PropertySource("...")
})
class AConfiguration {
}
```

L'annotation `@PropertySource` prend en paramètre l'identifiant de la ressource à considérer comme une source de
propriétés. Nous concernant, c'est un fichier situé dans le **classpath** de l'application.

> Qu'est-ce que classpath ?

Grossièrement, c'est un "dossier" virtuel contenant les sources de l'application, les ressources et les bibliothèques
externes utilisées. Cette notion est avancée, il n'est pas nécessaire que vous la mémorisiez mais sachez simplement que
pour localiser un fichier dans le classpath, son chemin relatif au dossier Maven `resources` suffit.

Une fois tous nos fichiers enregistrés, nous allons utiliser l'annotation `@Value` qui permet (entre autres) de
demander à Spring d'injecter la valeur associée à une propriété, en utilisant la syntaxe `${...}`, aussi appelée
**placeholder**.

*Note* : Avec IntelliJ **Ultimate**, vous pourrez constater si votre configuration est valide et si les propriétés
injectées sont bien celles contenues dans vos fichiers de configuration car IntelliJ remplace les expressions par leur
valeur.

Il est possible d'utiliser l'annotation de deux façons :

- En annotant le constructeur de la classe dans laquelle on va injecter la valeur
- En annotant le paramètre de la méthode produisant un `@Bean`, l'instantiation de la classe se faisant dans le corps de
  la méthode en utilisant ce paramètre.

L'utilisation d'`@Value` conjointe avec l'utilisation des méthodes `@Bean` permet encore une fois d'appuyer cette
séparation entre les classes utilisant les valeurs injectées, et la classe de configuration faisant
office de médiateur entre le conteneur d'inversion de contrôle de Spring et notre code.

## Ressources

[Properties with Spring and Spring Boot](https://www.baeldung.com/properties-with-spring)

Pour trouver le poids maximal afin de réaliser une mise en orbite terrestre basse pour chaque lanceur :

- [Ariane 5](https://en.wikipedia.org/wiki/Ariane_5)
- [Soyuz](https://en.wikipedia.org/wiki/Soyuz-2)
- [Energia](https://en.wikipedia.org/wiki/Energia)
- [Falcon Heavy](https://en.wikipedia.org/wiki/Falcon_Heavy)
