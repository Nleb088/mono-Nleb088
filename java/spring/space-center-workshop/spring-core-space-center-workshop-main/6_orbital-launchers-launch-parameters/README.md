# Spring Core Space Center - Orbital Launchers Launch Parameters

## A propos de l'exercice

Dans cet exercice, nous allons découvrir les possibilités qu'offrent le standard Java de Bean Validation ainsi que son
interopérabilité avec Spring.

Toute demande de lancement qui sera envoyée au contrôle de mission devra être vérifié et est désormais soumis à des
contraintes bien spécifiques. Pour ce faire, nous mettrons en place un ensemble de contraintes de validation sur notre
modèle représentant une demande de lancement, dans la
classe [LaunchRequest](src/main/java/com/carbon_it/space/missioncontrol/LaunchRequest.java) :

- Le champ `launcher` **ne doit pas être d'une longueur égale à zéro, les espaces et tabulations ne sont pas non plus
  acceptés**
- Le champ `payloadWeight` doit être une valeur numérique **supérieure à un**
- **Chaque lanceur orbital ne peut pas envoyer une charge utile plus lourde que le poids
  maximal accepté, et ce poids est variable selon les capacités du lanceur utilisé.**

Les deux premières contraintes n'ont pas à être développées, elles sont déjà existantes en Java : `@NotBlank` ainsi
que `@Min`. Etant donné que ces contraintes ne s'appliquent qu'à un champ de la classe, nous pouvons directement les
apposer sur le champ concerné. La dernière contrainte utilisant plusieurs champs de la classe, elle ne peut être apposée
que sur la classe cible. De plus, nous devons la créer en deux phases :

- Tout d'abord, une annotation qui décrit la contrainte : voyez l'annotation comme un :flag: que l'on appose quelque
  part pour signaler qu'une contrainte doit être exécutée. Cette annotation doit remplir un certain nombre de
  contraintes qui sont documentées dans les liens ci-dessous.
  **N'oubliez pas de restreindre la cible de l'annotation à une utilisation sur des types, et de bien vous assurer que
  l'annotation sera visible dans le code interprété à l'exécution.**

- Un validateur qui exécutera la vérification permettant de valider ou non la contrainte.
  **Avec Spring, ces validateurs supportent l'injection de dépendances, ce qui nous va nous permettre d'utiliser des
  valeurs externes à l'implémentation du validateur**.

> Qu'est-ce qui fait le lien entre l'annotation de validation et le validateur ?

Lors de la définition de l'annotation de validation, apposer l'annotation `@Constraint` est requis afin de qualifier
l'annotation comme étant celle d'une contrainte de validations. Cette annotation prend en paramètre une référence à la
classe implémentant le validateur, ici `MaxPayloadWeightValidator`.

```java
@Constraint(validatedBy = MaxPayloadWeightValidator.class)
```

Passons enfin à l'implémentation du validateur. N'importe quel validateur doit implémenter l'interface
générique `ConstraintValidator<ValidationAnnotation, Model>`, `ValidationAnnotation` est à remplacer par le type de
l'annotation définie, et `Model` par le type de l'objet cible. Nous concernant, l'objet cible sera toujours de type
**LaunchRequest**.

Cette interface possède deux méthodes implémentables :

- `void initialize(ValidationAnnotation annotation)`, qui permet d'initialiser le validateur avec des données provenant
  de l'annotation
- `boolean isValid(LaunchRequest value, ConstraintValidatorContext context)`, permettant de définir le code exécuté lors
  de l'exécution de la contrainte de validation.

Dans la méthode _isValid_, nous allons localiser selon le **nom du lanceur**, le poids maximal accepté par charge
utile : c'est une propriété externe que nous avons injecté dans nos configurations lors de l'étape deux. Ici, nous
allons utiliser [l'environnement Spring afin de localiser dynamiquement la propriété](https://docs.spring.io/spring-framework/docs/5.3.x/javadoc-api/org/springframework/core/env/PropertyResolver.html#getProperty-java.lang.String-java.lang.Class-)
en concaténant la valeur du nom du lanceur avec le suffixe `.max.leo`.

Souvenez-vous, nous l'avons fait dans l'étape 3 en ajoutant dans des fichiers YAML l'ensemble de ces propriétés !

```
Integer maxLeo = environment.getProperty(value.getLauncher() + ".max.leo", Integer.class);
```

Si cette valeur est nulle, la propriété n'existe pas ce qui signifie que le lanceur n'existe probablement pas non plus :
la validation sera donc rejetée.

Enfin, si la valeur numérique trouvée est inférieure ou égale au poids fourni dans la requête, alors le lancement est accepté.

## Ressources

[Java Bean Validation Basics](https://www.baeldung.com/javax-validation)

[Spring Validation](https://docs.spring.io/spring/docs/5.3.x/reference/html/core.html#validation-beanvalidation)

[Creating a custom validation constraint](https://www.baeldung.com/spring-mvc-custom-validator)
