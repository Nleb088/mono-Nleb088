# Spring Core Space Center - Orbital Launchers Mission Control

## A propos de l'exercice

L’objectif de cet exercice est de présenter les hooks que Spring expose aux beans afin d’effectuer des actions liées au
cycle de vie de l’application (par exemple établir une connection au démarrage de l'application, la détruire à
l'extinction…).

Nous allons voir 3 méthodes différentes afin de lier nos beans au cycle de vie de l'application.

- Héritées de JavaEE (désormais nommé **JakartaEE**), les annotations `@PostConstruct` et `@PreDestroy` permettent de
  marquer une méthode qui sera invoquée après la création du bean ou avant sa destruction. Spring est rétrocompatible
  avec ces
  annotations, mais elles ont différents désavantages qui peuvent freiner son usage (elles créent une dépendance forte
  entre la classe où elles sont utilisées et le framework, sont invoquées par réflexion...)

- Présentes dans Spring, les
  interfaces [InitializingBean](https://docs.spring.io/spring-framework/docs/5.3.x/javadoc-api/org/springframework/beans/factory/InitializingBean.html)
  et
  [DisposableBean](https://docs.spring.io/spring-framework/docs/5.3.x/javadoc-api/org/springframework/beans/factory/DisposableBean.html)
  permettent de définir des méthodes appelées à la création/destruction du bean. Cette approche est plus claire que les
  annotations, car les méthodes implémentées sont appelables programmatiquement, visibles de l'extérieur de la classe ;
  néanmoins, la dépendance forte reste présente entre la classe annotée et Spring.

- Enfin, il est possible d'utiliser les attributs `initMethod` et `destroyMethod` de l'annotation
  `@Bean` afin préciser lors de la déclaration du bean les méthodes à appeler à l'initiation ou la destruction. En
  utilisant cette approche, plus aucun couplage n'est présent. Mais elle est sensible au refactoring (*car les méthodes
  sont spécifiées sous forme de chaîne de caractères*).

> Dans quel ordre sont pris en compte les configurations présentées ci-dessus ?

En exécutant l'application et en observant les journaux en console, la réponse apparait : **les annotations JakartaEE
sont considérées en premier, puis les interfaces Spring et enfin, les méthodes définies dans les attributs de
l'annotation `@Bean`.**

```
[           main] c.c.s.missioncontrol.CustomMissionControl  : @PostConstruct
[           main] c.c.s.missioncontrol.CustomMissionControl  : Init interface
[           main] c.c.s.missioncontrol.CustomMissionControl  : Init Javaconfig
[           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
[           main] com.carbon_it.space.SpaceCenter              : Started SpaceCenter in 0.755 seconds (JVM running for 1.007)
[ionShutdownHook] c.c.s.missioncontrol.CustomMissionControl  : @PreDestroy
[ionShutdownHook] c.c.s.missioncontrol.CustomMissionControl  : Destroy interface
[ionShutdownHook] c.c.s.missioncontrol.CustomMissionControl  : Destroy Javaconfig
```

## Ressources

[Running Setup Data on Startup in Spring](https://www.baeldung.com/running-setup-logic-on-startup-in-spring)

[Spring Shutdown Callbacks](https://www.baeldung.com/spring-shutdown-callbacks)
