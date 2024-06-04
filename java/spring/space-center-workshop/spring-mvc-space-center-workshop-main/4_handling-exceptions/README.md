# Spring MVC Space Center - Handling exceptions

# A propos de l'exercice

Lorsque nous créons des controllers au sein de nos applications backend, ils ne fonctionnent jamais seuls et délèguent
une partie du travail nécessaire au traitement d'une requête à des tierces parties (en règle générale, **un service**).
Nous savons qu'en Java, c'est le mécanisme d'exceptions qui est utilisé. Tout élément de code qui sera invoqué peut
**jeter des exceptions si le fonctionnement nominal représenté est perturbé**. Mais que se passe-t-il si cette tierce
partie remonte une erreur ?

Spring MVC est autoconfiguré via Spring Boot pour gérer l'intégralité des exceptions pouvant survenir et renverra une
réponse générique avec le code de statut `500 Internal Server Error`.

Dans cet exercice, nous allons découvrir différentes façons que Spring MVC nous offre afin de facilement transformer
nos exceptions métier en message d'erreur et en code de statut HTTP **personnalisé**.

Commençons par ouvrir le fichier qui contient la
classe [UnavailableLaunchpadException](./src/main/java/io/vieira/space/exceptions/UnavailableLaunchpadException.java).
Notre classe étend de la classe `Exception`, et est utilisée dans la signature de la méthode _launch_ dans
l'interface [LaunchPad](./src/main/java/io/vieira/space/launchpad/LaunchPad.java) pour signaler que cette méthode peut
jeter une instance de l'exception quand elle sera invoquée.

Nous pouvons remarquer une erreur de compilation dans la
classe [LaunchesController](./src/main/java/io/vieira/space/launchpad/LaunchesController.java). L'appel à la méthode
_launch_ est surligné en rouge, avec le message suivant :

```
Unhandled exception: io.vieira.space.exceptions.UnavailableLaunchpadException
```

Le compilateur vérifie qu'une exception déclarée comme **étant potentiellement jetée** est gérée correctement :
c'est une [exception checked](https://www.baeldung.com/java-checked-unchecked-exceptions#checked).

> Du coup, on en fait quoi de cette exception ?

IntelliJ nous propose deux options : soit ajouter un bloc _try/catch_ autour de l'appel de la méthode, soit en refilant
la patate chaude à l'appelant en déclarant que la méthode où l'on se situe **elle aussi** jettera la même exception (_on
dit qu'elle la propage_).

Écrire un bloc try/catch dans nos controllers serait disgracieux et pas nécessaire. Spring MVC possède un mécanisme
permettant de _l'inversion de contrôle_ autour de la gestion des exceptions lors de l'invocation d'une méthode de
controller.

Pour l'utiliser, nous devons créer un gestionnaire d'exceptions (_exception handler_) sous forme de méthode annotée
par `@ExceptionHandler`. Cette méthode peut être située au sein d'un controller, ou au sein d'un bean enregistré auprès
de Spring MVC via deux stéréotypes, `@ControllerAdvice` ou `@RestControllerAdvice`. **Si un gestionnaire est déclaré
dans un controller, il ne sera utilisé que pour le controller : ce n'est qu'avec un controller advice qu'on rend la
gestion des exceptions enregistrées disponibles dans l'intégralité de l'application.**

Tout comme les méthodes présentes au sein d'un controller, ces méthodes peuvent déclarer des valeurs
que [Spring MVC est capable de fournir](https://docs.spring.io/spring-framework/docs/5.3.x/reference/html/web.html#mvc-ann-exceptionhandler-args).

> La différence entre `@ControllerAdvice` et `@RestControllerAdvice` est-elle la même qu'entre `@Controller`
> et `@RestController` ?

Tout à fait.

Nous n'avons pas besoin d'accepter de paramètres au sein de notre gestionnaire d'exception, nous renverrons simplement
un code de statut `503 Service Unavailable` en réponse avec l'annotation `@ReponseStatus` que l'on a déjà abordé :

```java
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    // Ici la gestion de cette exception n'est propre qu'à ce 
    // controller, pas besoin de @RestControllerAdvice !
    @ExceptionHandler(MyCustomCheckedException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    void handleMyCustomCheckedException() {
    }
}
```

Spring nous offre deux autres alternatives afin de lier une exception à un code de statut spécifique :

- Annoter l'exception avec `@ResponseStatus` directement, si on n'a pas besoin de modifier le corps de la réponse. C'est
  ce que nous allons faire au sein de l'exception
  [FlawedORing](./src/main/java/io/vieira/space/exceptions/FlawedORingException.java) :

  ```java
  import org.springframework.http.HttpStatus;
  import org.springframework.web.bind.annotation.ResponseStatus;
  
  @ResponseStatus(HttpStatus.PRECONDITION_REQUIRED)
  public class FlawedORingException extends RuntimeException {}
  ```

- Avoir une classe d'exception qui étend
  de [ResponseStatusException](https://docs.spring.io/spring-framework/docs/5.3.x/javadoc-api/org/springframework/web/bind/annotation/ResponseStatus.html) :

  ```java
  import org.springframework.http.HttpStatus;   
  import org.springframework.web.server.ResponseStatusException;

  public class FlawedORingException extends ResponseStatusException {
    public FlawedORingException() {
        super(HttpStatus.PRECONDITION_REQUIRED);
    }   
  }
  ```

Ces deux approches sont déconseillées même si elles existent, car elles lient des exceptions métier à la couche MVC de
Spring : même si cela peut sembler anodin, c'est **une mauvaise pratique dans certains styles d'architecture backend
comme _Clean, Onion ou Hexagonale_**.

> Tiens, ce coup-ci l'exception _FlawedORingException_ n'est pas déclarée comme jetée au sein d'une méthode. C'est
> normal ?

Tout à fait. Cette exception n'étend pas de la classe _Exception_, mais de la classe _RuntimeException_ et de ce fait
est une _exception unchecked_ considérée comme jetée de façon imprévisible : sa gestion ne sera pas vérifiée par le
compilateur.

> Mais quel type d'exception choisir lorsque l'on développe ?

Oracle donne ces recommandations : _if a client (l'utilisateur du code, ndlr) can reasonably be expected to
recover from an exception, make it a checked exception. If a client cannot do anything to recover from the exception,
make it an unchecked exception._

C'est pour ces raisons que dans l'exercice, l'exception _UnavailableLaunchpadException_ est **checked** : on peut
aisément et rapidement changer de pas de tir pour notre lanceur. Par contre l'exception _FlawedORingException_ est
**unchecked** car si un joint torique est défectueux sur un lanceur, cela entraine obligatoirement le remplacement de
celui-ci ainsi que le démontage du lanceur et donc son indisponibilité immédiate.

## Ressources

[Checked and unchecked exceptions](https://www.baeldung.com/java-checked-unchecked-exceptions)

[Error Handling for REST with Spring](https://www.baeldung.com/exception-handling-for-rest-with-spring)
