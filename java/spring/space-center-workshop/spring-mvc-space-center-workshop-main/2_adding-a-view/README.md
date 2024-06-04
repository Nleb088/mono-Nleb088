# Spring MVC Space Center - Adding a view

## A propos de l'exercice

Dans cet exercice, nous allons nous éloigner des API REST pour produire des pages HTML générées comme au bon vieux
temps :heart:.

Nous allons créer des controllers qui vont manipuler un modèle afin que celui-ci soit réutilisé dans la vue
qui sera générée lors de l'écriture de la réponse HTTP. Du MVC comme on l'aime, quoi : il n'est pas question d'API REST
dans cet exercice.

Le dossier **templates** dans le classpath contient des fichiers `.pebble`, du moteur de templating éponyme. Pebble est
inspiré dans sa syntaxe de Twig ([matrice de compatibilité](https://pebbletemplates.io/twig-compatibility/)), qui est un
moteur de rendu de vue apprécié et très prisé dans le milieu PHP (c'est le moteur utilisé par *Symfony*) pour sa
flexibilité et sa rapidité.

> Quel moteur de templating choisir, en production ?

Lors de projets, vous aurez plutôt l'occasion d'utiliser des moteurs de templating
comme [Thymeleaf](https://www.thymeleaf.org/) (*le plus célèbre et officiellement conseillé par les développeurs de
Spring*) ou [Freemarker](https://freemarker.apache.org/). Il existe également
JTE ([Java Template Engine](https://jte.gg/)) qui est très prometteur, même s'il manque d'une intégration avec Spring
Boot à l'écriture de ces lignes.

Nous utiliserons Pebble ici est plutôt une occasion de profiter de l'exercice pour voir d'autres choses : il est très
agréable à utiliser grâce à sa syntaxe.

Commençons par marquer la
classe [OrbitalLaunchersViewController](src/main/java/io/vieira/space/launchers/OrbitalLaunchersViewController.java)
comme un controller (pas un **controller REST !**) :

```java
import org.springframework.stereotype.Controller;

@Controller
public class OrbitalLaunchersViewController {
}
```

Ensuite, ajoutons les tests qui valideront l'implémentation de cette classe, affichant l'ensemble des lanceurs
orbitaux présents en base. Regardons le test de la
classe [OrbitalLaunchersControllerTest](src/test/java/io/vieira/space/launchers/OrbitalLaunchersControllerTest.java),
afin de s'en inspirer dans l'écriture de notre test :

```java
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrbitalLaunchersController.class)
class OrbitalLaunchersControllerTest {

    @Test
    @DisplayName("should get all launchers")
    void shouldGetAllLaunchers() throws Exception {
        // Arrange
        final List<OrbitalLauncher> orbitalLaunchers = Collections.singletonList(new OrbitalLauncher(1, "Ariane 5", "ariane-5", 16000));
        when(launchersRepository.findAll()).thenReturn(orbitalLaunchers);

        // Act
        mockMvc.perform(get("/api/launchers").accept(MediaType.APPLICATION_JSON))
                // Assert
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(orbitalLaunchers), true));

        verify(launchersRepository).findAll();
        verifyNoMoreInteractions(launchersRepository);
    }
}
```

> Notre classe de test n'utilise pas l'annotation `@SpringBootTest`, mais `@WebMvcTest`. Qu'est-ce que c'est ?

Nous l'avons entrevu lors de l'**exercice 2 de l'atelier Spring Data** : les annotations `@xxxxTest` permettent de
bénéficier des _[test slices](https://www.baeldung.com/spring-tests#5-using-test-slices)_ de Spring pour configurer un
contexte d'application ne contenant que le **strict minimum** nécessaire au test unitaire.

En observant la structure du test unitaire, nous pouvons remarquer qu'on peut en dégager 3 étapes principales :

- Une phase de configuration (_arrange_) qui nous sert ici à configurer le faux repository : il est sous forme
  de [mock](https://blog.pragmatists.com/test-doubles-fakes-mocks-and-stubs-1a7491dfa3da), nous pouvons donc lui dicter
  son comportement quand il sera appelé par le controller
- Une phase d'exécution (_act_), pour appeler ce qui va déclencher l'exécution du code que nous testons : nous envoyons
  une requête HTTP sur le point de terminaison _/api/launchers_, en GET et en spécifiant au serveur que nous souhaitons
  du JSON en réponse
- Une phase d'assertions (_assert_) pour vérifier que nous recevons bien la bonne réponse HTTP en termes de code de
  statut, format de réponse et contenu, mais également vérifier que le controller a correctement interagi avec le
  repository

> Notre test est-il réellement unitaire ? Nous faisons une requête HTTP sur un controller, ce me semble plus être un
> test d'intégration.

On pourrait arguer que non, étant donné qu'un controller ne fonctionne jamais seul : il est utilisé par Spring qui
configure un ensemble d'étapes intermédiaires permettant à une requête HTTP d'être traitée avant d'entrainer
l'invocation d'une _HandlerMethod_. Cependant, le code du controller que nous écrivons est testé en isolation (**étant
donné que le repository est substitué par un double de test**) et est indissociable de Spring : on peut donc considérer
qu'on le teste unitairement.

> Comment faire une requête sur notre controller ?

On le voit ci-dessus, on utilise la classe utilitaire **MockMvc** pour tester facilement ce que peut produire n'importe
quel controller Spring MVC : elle expose une [API fluent](https://www.martinfowler.com/bliki/FluentInterface.html) qui
tire avantage du chaînage de méthodes sur un objet afin de spécifier une requête à exécuter et vérifier la réponse HTTP.
Il suffit d'appeler la méthode `perform` et de lui passer le type de requête
souhaitée, [une fois qu'elle est configurée](https://docs.spring.io/spring-framework/docs/5.3.x/javadoc-api/org/springframework/test/web/servlet/request/MockMvcRequestBuilders.html).

> Je ne sais pas vraiment utiliser des mocks, est-ce grave ?

Non, les seules connaissances nécessaires pour cet atelier concernant les mocks reposent sur :

- Comment les récupérer dans un test avec Spring

```java
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(OrbitalLaunchersController.class)
class OrbitalLaunchersControllerTest {

    // Cette annotation sert à dire à Spring de remplacer, dans le cadre du test, la vraie version du bean par un mock
    @MockBean
    private OrbitalLaunchersRepository launchersRepository;
}
```

- Comment les configurer pour que le comportement défini influence notre controller lorsqu'il appellera sa dépendance

```java
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class OrbitalLaunchersControllerTest {

    @Test
    void shouldSomething() {
        final List<OrbitalLauncher> orbitalLaunchers = Collections.singletonList(new OrbitalLauncher(1, "Ariane 5", "ariane-5", 16000));
        // Quand j'appelle la méthode findAll du repository, je retournerai toujours la même collection, définie juste au-dessus
        when(launchersRepository.findAll()).thenReturn(orbitalLaunchers);
    }
}
```

Nous devons donc vérifier dans notre test le statut de réponse HTTP (dans ce cas, *200 OK*) ainsi que le code HTML
retourné dans la réponse grâce au *matcher* `content()`. Nous vérifierons que le contenu est de type **text/html**,
encodé en *UTF-8*.

Nous utiliserons d'autres matchers afin de vérifier d'autres exigences :

- Que le nom de la vue est bien *launchers* : on utilise le matcher `view()` pour cela
- Que le contenu HTML est généré correctement selon le
  template [launchers.pebble](src/main/resources/templates/launchers.pebble) : on utilise le matcher `xpath()` pour
  cela, qui nous permet de cibler des éléments spécifiques à l'intérieur de notre code HTML
- Que le modèle qui lui est lié contient bien les mêmes lanceurs que ceux que l'on aura configuré sur notre mock : on
  utilise `model()` pour cela

```java
class OrbitalLaunchersViewControllerTest {

    @Test
    void shouldExposeAllLaunchersAsHTML() {
        // Le reste du code est omis pour des questions de clarté ;) pensez bien à configurer le mock pour qu'il renvoie autant
        // de lanceurs que ce qui est souhaité en résultat !
        mockMvc
                .perform(get("/launchers").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("launchers"))
                .andExpect(model().attributeExists("launchers"))
                .andExpect(model().attribute("launchers", equalTo(launchers)))
                .andExpect(xpath("/html/body/h1").string("There is actually 4 launchers registered."))
                .andExpect(xpath("/html/body/h2").nodeCount(4))
                .andExpect(xpath("/html/body/h2").string(in(launcherNames)))
                .andExpect(xpath("/html/body/p").string(in(launcherLeos)));
    }
}
```

> Qu'est-ce qu'un modèle et comment l'utilise-t-on dans Spring MVC ?

Le modèle est un objet partagé entre la vue et le controller, permettant de faire transiter de la donnée de l'un à
l'autre. Dans Spring MVC, le modèle est lié à la vue et représenté par la classe **ModelAndView**, qui contient le nom
de la vue à rendre ainsi que les données associées.

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

class OrbitalLaunchersViewController {

    @GetMapping(value = "/launchers")
    ModelAndView getLaunchers() {
        // Notre vue sera nommée "launchers" et contiendra une variable "launchers" qui est la liste des lanceurs disponibles
        return new ModelAndView("launchers")
                .addObject("launchers", launchersRepository.findAll());
    }
}
```

Il est également possible de demander une instance de cet objet en paramètre de la *HandlerMethod* pour pouvoir y
ajouter les données, puis retourner simplement le nom de la vue : **je déconseille personnelle cette approche qui rend
le code moins lisible**.

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

class OrbitalLaunchersViewController {

    @GetMapping(value = "/launchers")
    String getLaunchers(ModelAndView modelAndView) {
        modelAndView.addObject("launchers", launchersRepository.findAll());
        return "launchers";
    }
}
```

Enfin, nous terminerons l'exercice en configurant une vue statique en HTML, qui ne nécessite aucun modèle parce
qu'aucune donnée ne provient du controller. Il n'y a pas besoin dans ce cas de figure d'ajouter un controller, ce genre
de vues est directement enregistrable auprès de Spring en implémentant l'interface
[WebMvcConfigurer](https://docs.spring.io/spring-framework/docs/5.3.x/javadoc-api/org/springframework/web/servlet/config/annotation/WebMvcConfigurer.html)
dans une classe de configuration. En l'occurrence, nous l'implémenterons dans la classe de configuration
[MissionControlConfiguration](src/main/java/io/vieira/space/launchpad/MissionControlConfiguration.java) : en
redéfinissant la méthode `addViewControllers(ViewControllerRegistry registry)`, nous pouvons ajouter à travers le
_ViewControllerRegistry_ des vues statiques associées à un URI particulier. Dans notre cas, nous souhaitons que
l'URI `/mission-control` entraine le rendu de la vue nommée _mission-control_.

```java
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class MissionControlConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry
                .addViewController("/mission-control")
                .setViewName("mission-control");
    }
}
```

## Ressources

[Référence de la syntaxe Pebble](https://pebbletemplates.io/wiki/guide/basic-usage/#syntax-reference)

[Liste des tags Pebble](https://pebbletemplates.io/wiki/#tags)

[Model, ModelMap and ModelView in Spring MVC](https://www.baeldung.com/spring-mvc-model-model-map-model-view)

[MVC Config - View Controllers](https://docs.spring.io/spring/docs/5.3.x/reference/html/web.html#mvc-config-view-controller)
