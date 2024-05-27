# Spring Core Space Center - Orbital Launchers (Java Config)

## A propos de l'exercice

Dans cet exercice, nous allons créer plusieurs implémentations d'un contrat commun à tous les lanceurs orbitaux.

En s’affranchissant des stéréotypes, il est possible de rendre la configuration du contexte d’application Spring
déclarative et plus robuste, au détriment d'un peu plus de code à écrire. Pour ce faire, il est nécessaire de créer des
méthodes au sein de classes détectables par Spring (le plus souvent, des `@Configuration`s). Ces méthodes décrivent
comment instancier chaque bean, et les éventuelles injections à réaliser (par paramètre) afin de satisfaire les
dépendances présentes en constructeur de la classe instanciée.

> S'il faut écrire plus de code, quel est le vrai intérêt de cette approche ?

L'utilisation de méthodes annotées avec `@Bean` permet d'expliciter les déclarations des instances gérées par Spring. En
effectuant cette déclaration en dehors de la classe concernée, on limite le couplage entre le code métier et la
configuration nécessaire à l'exécution de notre application. C'est une technique très utile dans des patrons
d'architecture avancés qui mettent en avant la séparation entre les différentes couches d'une application, comme
**l'architecture hexagonale**.

Ainsi, une méthode permettant de créer un bean de type B implémentant une interface A et
dépendant d'une dépendance de type C s'écrira ainsi :

**Oui, on ne met pas plusieurs classes/interfaces dans le même fichier, mais c'est pour l'exemple :wink:**

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// A expose une capacité accessible par une méthode nommée doSomething, sans que l'on sache l'implémentation associée
interface A {
    void doSomething();
}

// C expose une capacité accessible par une méthode nommée doAnotherThing, sans que l'on sache l'implémentation associée
interface C {
    void doAnotherThing();
}

// On déclare B qui est une classe implémentant la capacité déclarée dans l'interface A, et cette fonctionnalité utilise 
// une éventuelle instance de C par composition. On doit donc injecter une instance de C dans B pour permettre à B 
// d'accomplir ce qu'elle souhaite faire.
class B implements A {
    private final C theDependency;

    // L'injection de dépendances par constructeur, c'est le bien ;)
    public B(C theDependency) {
        this.theDependency = theDependency;
    }

    @Override
    public void doSomething() {
        this.theDependency.doAnotherThing();
    }
}

class D implements C {

    @Override
    public void doAnotherThing() {

    }
}

// @Configuration est un stéréotype au même titre que @Service ou @Component, par exemple. C'est ce qui permet à Spring
// de localiser la classe et l'inspecter pour voir si elle contient des méthodes déclarant des beans
@Configuration
class SampleConfig {

    @Bean
    // Nous crééons ici une instance D qui implémente C, nommée "theDependency". Cette instance sera réutilisée juste 
    // en dessous car elle est nécessaire à l'instanciation de B, nous devons donc la créer avant B. La définition d'un
    // bean devant permettre l'inversion de dépendances, on retourne de façon générale le type abstrait d'un bean s'il existe.
    C theDependency() {
        return new D();
    }

    @Bean
    A theBean(
            // En demandant un paramètre de type C, Spring sait faire le lien avec le bean créé précédemment 
            // (qui est une instance de D, mais qui implémente C) et injectera le bean correspondant
            C theDependency) {
        return new B(theDependency);
    }
}
```

> Pourquoi injecter par constructeur lorsque l'on déclare la classe B ?

L'injection par constructeur est à privilégier car elle permet de déclarer **explicitement** les dépendances requises
par une classe (à l'inverse de l'injection par champ), elle permet également d'en obtenir une valeur immuable à la
construction (par opposition à l'injection par setter). Et en plus, elle facilite les tests unitaires :wink:.
[Vous pourrez découvrir tout ceci plus en détail en 15 minutes ici](https://www.youtube.com/watch?v=NVzCeKVA-5k)

Nous découvrirons ensuite comment nommer les beans de façon personnalisée et comment créer des alias de noms de beans
avec l'annotation `@Bean`. L'intérêt d'`@Bean` par rapport aux stéréotypes que l'on a vu précédemment est qu'on peut
déclarer plusieurs alias pour la même instance.

Enfin, nous verrons comment rendre une implémentation “prioritaire” **lors d'une injection de dépendances ambiguë**.

> Quel est l'intérêt une implémentation "prioritaire" ?

Pour comprendre l'intérêt de cette approche, il est nécessaire de
comprendre [comment Spring gère les ambiguïtés d'injection](https://www.baeldung.com/spring-autowire#disambiguation),
lorsque l'on injecte un type abstrait qui possède plusieurs implémentations connues au sein du contexte d'application.

Quand aucune des méthodes présentes ci-dessus ne permet de résoudre une ambiguïté d'injection, c'est à ce moment
que `@Primary` est utile pour marquer un bean spécifique comme [prioritaire à l'injection **par
défaut**](https://www.baeldung.com/spring-primary).

## Resources

[Using the @Bean annotation](https://docs.spring.io/spring-framework/docs/5.3.x/reference/html/core.html#beans-java-basic-concepts)

[List of orbital launch systems](https://en.wikipedia.org/wiki/List_of_orbital_launch_systems) (si vous êtes toujours
curieux)
