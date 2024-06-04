# Spring MVC Space Center - Sharing resources

## A propos de l'exercice

Nous terminons cet atelier en abordant les requêtes inter-origines sur une API REST et leur configuration avec
Spring MVC.

> Le CORS, c'est quoi ?

CORS (pour _Cross-Origin-Resource-Sharing_) définit des règles sur les interactions entre client et un serveur
lorsque le frontend envoie des requêtes HTTP provenant d'une adresse/nom de domaine différent de celui sur lequel le
serveur s'exécute le backend. Cela permet que les requêtes de même origine fonctionnent de façon transparente, en
autorisant au compte-gouttes les requêtes venant d'autres origines afin de réduire la surface d'attaque des
attaques [XSS](https://developer.mozilla.org/en-US/docs/Web/Security/Types_of_attacks#cross-site_scripting_xss).

Il est important de retenir que nous devons configurer notre application dans le but de la sécuriser au mieux face aux
attaques XSS, nous devons donc appliquer **le
[principe de moindre privilège](https://www.ssi.gouv.fr/uploads/2017/12/guide_cloisonnement_systeme_anssi_pg_040_v1.pdf#subsection.2.1.1)**
en n'autorisant que ce qui est nécessaire.

Au sein de la classe
[CorsConfiguration](./src/main/java/io/vieira/space/CorsConfiguration.java), nous allons autoriser l'accès à tous les
controllers accessibles depuis le chemin de base **/api** aux origines _app-a.nasa.gov_, _tracking.roscosmos.ru_ et
_internal.spacex.com_. Les origines autorisées sont ici communes à toutes les routes exposées dans l'application, nous
pouvons donc utiliser
l'interface [WebMvcConfigurer](https://docs.spring.io/spring-framework/docs/5.3.x/javadoc-api/org/springframework/web/servlet/config/annotation/WebMvcConfigurer.html)
et redéfinir la méthode _addCorsMappings_ pour définir notre configuration CORS :

```java
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/api/**")
                .allowedOrigins(
                        "app-a.nasa.gov",
                        "tracking.roscosmos.ru",
                        "internal.spacex.com"
                );
    }
}
```

Désormais, nous devons appliquer une configuration plus fine selon les routes présentes au sein de nos controllers et ce
qu'elles utilisent :

```java
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/acontroller")
// ❌ : plusieurs méthodes de verbes différents sont présentes dans le controller,
// mais en ajoutant la configuration sur la classe, nous autorisons toutes les 
// verbes possibles sur des routes qui en utilisent des différents
@CrossOrigin(methods = {
        RequestMethod.GET,
        RequestMethod.POST
})
class AController {

    // ❌ : cette route en GET est autorisée en POST
    @GetMapping
    void aGetMapping() {
    }

    // ❌ : cette route en POST est autorisée en GET
    @PostMapping("/other")
    void aPostMapping() {
    }
}
```

```java
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/acontroller")
// ✅ : chaque route possède sa propre configuration CORS, car il n'est pas 
// possible de les factoriser au sein de classe
class AController {

    // ✅ : cette route en GET n'est autorisée dans un autre verbe HTTP
    @GetMapping
    @CrossOrigin(methods = RequestMethod.GET)
    void aGetMapping() {
    }

    // ✅ : cette route en POST n'est autorisée dans un autre verbe HTTP
    @PostMapping("/other")
    @CrossOrigin(methods = RequestMethod.POST)
    void aPostMapping() {
    }
}
```

```java
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/acontroller")
// ✅ : une ou plusieurs méthodes associées au même verbe sont présentes
// dans le controller, la configuration peut être inscrite au niveau de la classe
@CrossOrigin(methods = RequestMethod.GET)
class AController {

    @GetMapping
    void aGetMapping() {
    }

    @GetMapping("/other")
    void anotherGetMapping() {
    }
}
```

Nous terminerons l'exercice en ajoutant des en-têtes autorisés à la configuration CORS que nous avons déjà mise en
place. Ils le sont tous par défaut, nous pouvons restreindre ceux qui seront échangeables entre notre frontend et notre
backend et utilisés par Spring. Etant donné qu'ils sont utilisés dans tous les
controllers REST, ces headers sont configurables dans la
classe [CorsConfiguration](./src/main/java/io/vieira/space/CorsConfiguration.java) :

```java
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/api/**")
                .allowedOrigins(
                        "app-a.nasa.gov",
                        "tracking.roscosmos.ru",
                        "internal.spacex.com"
                )
                .allowedHeaders(
                        HttpHeaders.ACCEPT,
                        HttpHeaders.CONTENT_TYPE
                );
    }
}
```

## Ressources

[Spring CORS](https://www.baeldung.com/spring-cors)
