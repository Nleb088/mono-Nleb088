# Spring MVC Space Center - Creating REST endpoints

## A propos de l'exercice

Dans cet exercice, nous allons mettre en place deux points de terminaison (_endpoints_) REST. Chaque point de
terminaison est défini par un URI, un ou plusieurs verbes HTTP et un ou plusieurs types de contenus gérés/produits.
Spring MVC nous permet l'agrégation de ces endpoints sous forme de méthodes au sein de classes nommées contrôleurs. Une
méthode possède dans le jargon de Spring le nom de *HandlerMethod*, elle peut posséder des paramètres [qui seront
fournis par Spring MVC](https://docs.spring.io/spring-framework/docs/5.3.x/reference/html/web.html#mvc-ann-arguments)
et [un type de retour](https://docs.spring.io/spring-framework/docs/5.3.x/reference/html/web.html#mvc-ann-return-types).

**Une classe faisant office de contrôleur est annotée par `@Controller` ou `@RestController`.**

> Quelle est la différence entre `@Controller` et `@RestController` ?

Un **@Controller** est un stéréotype permettant à Spring d'extraire les méthodes de la classe annotée afin de
transformer celles éligibles en tant qu'*HandlerMethod*.

**@RestController** est une méta-annotation composant `@Controller` et `@ResponseBody`, qui sert à signifier que toutes
les méthodes de la classe annotée retourneront des valeurs qui devront être directement converties en tant que corps de
la réponse HTTP dans le contenu négocié avec le client.

> Qu'est-ce que la négociation de contenu ?

La négociation de contenu est un mécanisme défini dans la spécification du protocole HTTP qui offre la possibilité
d'obtenir différentes représentations d'une même ressource. Elle peut être disponible en différentes langues, avec
différents types de média ou une combinaison des deux. **Dans les APIs REST, on utilise généralement ce mécanisme pour
renvoyer au client une ressource en JSON, ou en XML**.

*Le type de média est déterminé selon le header HTTP **Accept** fourni par le client au sein de la requête.*

Spring Boot auto-configure
certains [types de contenus gérés par défaut au sein de Spring MVC](https://github.com/spring-projects/spring-boot/blob/b18fffaf14f9ce3e5651f44745019890e8a899c2/spring-boot-project/spring-boot-autoconfigure/src/test/java/org/springframework/boot/autoconfigure/http/HttpMessageConvertersTests.java#L56)
pour que l'on puisse produire par exemple des fichiers binaires, du texte, du JSON ou du XML.

Avant de pouvoir configurer notre premier contrôleur, nous devons ajouter Spring MVC à notre projet ; il est également
nécessaire d'ajouter le support de la _Bean Validation_ (on l'a vu lors de l'Atelier Spring Core). Ces deux dépendances
sont disponibles dans leurs starters respectifs :

```xml

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
</dependencies>
```

Nous pouvons désormais modifier le premier
contrôleur [OrbitalLaunchersController](src/main/java/io/vieira/space/launchers/OrbitalLaunchersController.java) afin
d'ajouter deux nouvelles *HandlerMethod*s :

- **GET** */api/launchers*, permettant de récupérer l'ensemble des lanceurs présents en base de données sous forme de
  liste

Nous sommes dans le cas d'utilisation le plus simple de Spring MVC. Nous devons déclarer une méthode qui retournera une
liste d'[OrbitalLauncher](./src/main/java/io/vieira/space/launchers/OrbitalLauncher.java) récupérée depuis le
repository, et c'est tout !

- **GET** */api/launchers/{codeName}*, permettant de récupérer un lanceur spécifique selon son nom de code. Le nom de
  code doit être passé par le _path_ de l'URI, c'est la convention lorsqu'on met en place une API REST.

> Je ne sais pas ce qu'est le _path_ de mon URI ?

[Quelques explications sur la structure d'une URL](https://danielmiessler.com/study/difference-between-uri-url/#urlstructure)
pourront nous aider à trouver où se trouve notre variable.

La variable *codeName* est une **PathVariable**, un paramètre dynamique passé dans une route spécifique. Celui-ci
sera extrait par Spring MVC et utilisable en tant que paramètre de la *HandlerMethod*, grâce à l'annotation
**@PathVariable** :

```java
import io.vieira.space.launchers.OrbitalLauncher;
import io.vieira.space.launchers.OrbitalLaunchersRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
public class LaunchersController {

    private final OrbitalLaunchersRepository orbitalLaunchersRepository;

    public LaunchersController(OrbitalLaunchersRepository orbitalLaunchersRepository) {
        this.orbitalLaunchersRepository = orbitalLaunchersRepository;
    }

    @GetMapping("/api/launchers")
    public List<OrbitalLauncher> getLaunchers() {
        return orbitalLaunchersRepository.findAll();
    }

    @GetMapping("/api/launchers/{codeName}")
    public OrbitalLauncher getLauncher(@PathVariable("codeName") String codeName) {
        // ... On va implémenter ça ;)
        return null;
    }
}
```

On peut remarquer que les deux URI que l'on doit déclarer possèdent la meme base : `/api/launchers`. Il est possible de
composer l'annotation `@RequestMapping` au niveau de la classe et d'une ou plusieurs méthodes en particulier afin de
déclarer une route qui sera issue de la concaténation des URIs déclarées dans les annotations :

```java
import io.vieira.space.launchers.OrbitalLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/launchers")
public class LaunchersController {

    // ... Le reste du code est omis pour des questions de clarté ;)

    @GetMapping("/{codeName}")
    public OrbitalLauncher getLauncher(@PathVariable("codeName") String codeName) {
        // ... On va implémenter ça ;)
        return null;
    }
}
```

La méthode **getLauncher** sera donc invoquée lorsque le serveur HTTP recevra une requête GET vers l'URL
*/api/launchers/{codeName}*, avec la variable _codeName_ en fonction de ce que l'utilisateur demande dans sa requête.

> Pourquoi est-ce que le contrôleur interagit avec le repository directement ? N'y a-t-il pas normalement un service
> entre les deux, lorsque l'on met en place une architecture en couches ?

[Oui.](https://dev.to/blindkai/backend-layered-architecture-514h) Mais pour des questions de clarté et de simplicité de
l'atelier, la couche service n'existera pas **même si elle le devrait**.

> Quelles différences ont `@RequestMapping` et `@Get/Post/Put/Delete...Mapping` ?

*@RequestMapping* est une annotation qui permet de configurer l'URI, les verbes et les types de média acceptés/produits
par une méthode de contrôleur, ou toutes les méthodes d'un contrôleur si cette annotation est placée sur une classe.

Les versions *@Get/Post/Put/Delete...Mapping* sont des alias qui rendent une méthode liée spécifiquement au verbe HTTP
que l'on souhaite utiliser.

Lorsque nous ajoutons l'implémentation de la méthode du contrôleur renvoyant un lanceur orbital selon son nom, il nous
est impossible de compiler notre code, car le type de retour du repository est incompatible avec _OrbitalLauncher_. Le
repository représente correctement **l'absence ou la présence d'un lanceur orbital en fonction du nom de code demandé**,
en utilisant l'API [Optional](https://www.baeldung.com/java-optional-return#optional-as-return-type).

Nous pourrions changer le type de retour de la méthode du contrôleur pour qu'elle renvoie une Optional en partant du
principe que Spring pourrait changer lui-même le code de statut associé au corps de la réponse en fonction de la
présence ou non de la valeur souhaitée. 

La classe Optional n'est malheureusement 
[pas bien supportée par les bibliothèques de sérialisation JSON](https://www.baeldung.com/java-optional-return#2-json)
et nous devons nous-même brancher notre Optional à la réponse HTTP qui sera décrite par la classe **ResponseEntity** :

```java
import io.vieira.space.launchers.OrbitalLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/launchers")
public class LaunchersController {

    // ... Le reste du code est omis pour des questions de clarté ;)

    @GetMapping("/{codeName}")
    public ReponseEntity<OrbitalLauncher> getLauncher(@PathVariable("codeName") String codeName) {
        return ResponseEntity.of(orbitalLaunchersRepository.findByCodeName(codeName));
    }
}
```

Passons au second contrôleur, [LaunchesController](src/main/java/io/vieira/space/launchpad/LaunchesController.java).

Nous devons de nouveau marquer cette classe comme étant un contrôleur afin qu'elle puisse accepter des requêtes
HTTP, puis ajouter une méthode pour gérer les requêtes **POST** sur l'URI */api/launches*. Le corps de la requête que
cette méthode reçoit contiendra une instance du
modèle [LaunchRequest](./src/main/java/io/vieira/space/launchpad/LaunchRequest.java) qui devra être validée avant
l'invocation de la méthode du contrôleur par Spring. Nous devons donc ajouter un paramètre à la méthode et l'annoter
avec `@RequestBody` pour préciser d'où il provient, et `@Valid` (ou `@Validated`) afin de déclencher l'exécution des
règles de validations déclarées.

Cette méthode ayant pour responsabilité de mettre en attente un lancement, le code de statut à renvoyer
sera `202 Accepted`, sans aucun corps de réponse. Nous pourrions utiliser _ResponseEntity_ comme précédemment, mais il
existe [des alternatives](https://www.baeldung.com/spring-response-entity#alternatives) dont
l'annotation `@ResponseStatus` qui correspond à notre cas d'usage, il n'est pas nécessaire de modifier le corps de la
réponse, uniquement son code de statut :

```java
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LaunchesController {

    @PostMapping("/api/launches")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void launch(@RequestBody @Valid LaunchRequest launchRequest) {

    }
}
```

## Ressources

[Request Mapping](https://docs.spring.io/spring/docs/5.3.x/reference/html/web.html#mvc-ann-requestmapping)

[Handler methods -
*@RequestBody*](https://docs.spring.io/spring/docs/5.3.x/reference/html/web.html#mvc-ann-requestbody)

[Annotated controllers -
*@ResponseBody*](https://docs.spring.io/spring/docs/5.3.x/reference/html/web.html#mvc-ann-responsebody)

[Using Spring ResponseEntity to Manipulate the HTTP Response](https://www.baeldung.com/spring-response-entity)
