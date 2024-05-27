# Spring Core Space Center - Legacy Orbital Launchers

## A propos de l'exercice

Le but de cet exercice est d'aborder ce que Spring offre en matière de profils d’exécution. La disponibilité de certains
beans sera conditionnée à la présence/absence de ces profils.

Au sein de notre base de lancement, nous allons accueillir l'Agence spatiale Européenne (ESA), qui va effectuer une
inspection de l'ensemble de notre base. Manque de bol, il nous manque de quoi distinguer nos lanceurs encore en activité
de ceux qui ont pris une retraite bien méritée et nous en avons besoin : hors de question que l'ESA considère que de
vieux lanceurs soient utilisés et révoque notre licence d'exploitation !

Pour ce faire, nous allons utiliser des profils d’exécution. Le jour de l'inspection, hop un profil à activer et ni vu
ni connu :grin:

> Comment savoir quel profils une application utilise ?

Lorsque l'on lance une application Spring, cette ligne apparaît en console :

```
[main] com.carbon_it.space.SpaceCenter : No active profile set, falling back to default profiles: default
```

On voit bien qu'il existe deux types de profils, **actif** et **par défaut**. Un profil par défaut sera automatiquement
actif lorsqu'aucun autre profil n'est actif. Les profils deviennent actifs s'ils sont configurés comme tels en
passant par les fichiers YAML, des arguments de ligne de commande...

> Mais quel est l'intérêt d'un profil ?

Les profils permettent d'inclure ou exclure facilement des sections entières de la configuration d'une application
Spring. Ils sont également applicables aux beans que nous allons déclarer au sein de notre contexte d'application.

C'est ce que nous allons approfondir dès maintenant : nous allons créer deux annotations se reposant sur l’annotation
`@Profile` permettant de rendre certains beans disponibles dans le contexte d'application (ou pas) selon le profil
d’exécution. Nous allons donc créer une _méta-annotation_.

> Qu’est-ce qu’une méta-annotation ?

Ce sont des annotations qui sont annotées par d’autres annotations, permettant leur réutilisation plus facilement.
Regardons une annotation bien connue de Spring Core, `@Service` :

```java
import org.springframework.stereotype.Component;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Service {
}
```

Si on annote un élément du langage avec `@Service`, on annote également l'élément avec l'annotation `@Component` :
`@Service` est donc une méta-annotation.

Nous allons créer deux annotations dans cet exercice, en combinant `@Profile` avec :

- L’annotation `@Launchable`, qui rendra les beans annotés disponibles si le profil par défaut est activé, ou si le
  profil inspection l’est.
- L’annotation `@Grounded`, qui rendra les beans annotés disponibles si le profil par défaut est activé, ou “inactif” si
  le profil inspection l’est.

> Qu'est-ce que _@Target_, *@Retention* et pourquoi dois-je m'assurer que mon annotation est visible à l'exécution ?

`@Target` définit le ou les emplacements où l'annotation sera utilisable : classe, champ, paramètre de méthode, de
constructeur... C'est vous qui choisissez !

`@Retention`
définit [la stratégie de rétention de l'annotation](https://gayerie.dev/docs/java/langage_java/les_annotations.html#retention-d-une-annotation),
pour définir si elle n'est visible que dans le code source avant la compilation, ou également dans le code interprété
à l'exécution.

Nous concernant, **n'oubliez pas de marquer vos méta-annotations comme visibles au runtime.** Même si les tests
passeront sans, le packaging et l'exécution de l'application sur un environnement de production est sensible à cette
différence : si votre annotation n'est pas visible, alors votre méta-annotation ne sera pas prise en compte au démarrage du
contexte lors de la phase de création des beans.

## Ressources

[Spring Profiles](https://www.baeldung.com/spring-profiles)

[@Profile](https://docs.spring.io/spring-framework/docs/5.3.x/javadoc-api/org/springframework/context/annotation/Profile.html)

[Comprendre les annotations Java](https://gayerie.dev/docs/java/langage_java/les_annotations.html)
