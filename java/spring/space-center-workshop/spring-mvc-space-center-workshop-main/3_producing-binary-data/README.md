# Spring MVC Space Center - Producing binary data

## A propos de l'exercice

Au-delà d'exposer des ressources au format JSON, XML ou même des vues rendues en HTML, Spring MVC est également capable
de renvoyer au client des contenus binaires, chose très utile lorsqu'on souhaite rendre des fichiers téléchargeables.

Nous allons mettre à disposition la transcription du dernier lancement effectué (_transcript_, en anglais) : c'est dans
notre cas un fichier texte, mais le contenu téléchargeable peut varier selon le cas d'usage. Une image, un dossier
compressé... De multiples types de fichiers existent, et nous allons découvrir qu'il est possible de tous les servir
avec Spring.

> Serait-il possible d'avoir quelques rappels sur ces notions de types de fichier ?

Un fichier possède **toujours** un nom, et _parfois_ une extension :

```
maphotodevacances.jpg
      Nom        | Extension
```

Lorsque l'on observe l'extension du fichier, on sait d'expérience que ce fichier sera **probablement** une image.
Pourquoi probablement ? L'extension n'est qu'un indice permettant de déduire quel est le type du fichier, mais
un esprit malin aurait pu prendre un dossier compressé (qui possède l'extension _.zip_) et renommer ce fichier pour
qu'il devienne un _.jpeg_.

Aussi, plusieurs extensions correspondent au même type de fichier : les extensions _.jpg_ et _.jpeg_ font toutes les
deux référence au même type de fichier, appelé **type MIME**.

> Qu'est-ce qu'un type MIME ?

Le type _Multipurpose Internet Mail Extensions_ est un standard permettant d'indiquer la nature et le format d'un
document. La structure d'un type MIME est simple, elle est composée d'un type et d'un sous-type, séparés par un `/`.

Par exemple, un fichier avec l'extension `.txt` aura un type MIME *text/plain* : le type est *text*, le sous-type
*plain*. Un fichier avec l'extension `.html` aura un type MIME *text/html* : c'est également un type _texte_, mais le
sous-type est _html_.

Lorsqu'on expose des fichiers au téléchargement, c'est à la responsabilité du serveur de fournir au client les
informations nécessaires pour qu'il puisse reconstituer le fichier, puisque les données qui constituent le fichier vont
transiter au format binaire.

Nous avons différentes options pour cela :

- Ajouter en dur dans notre `@RequestMapping` le type MIME produit, avec l'attribut _produces_. Un mapping est donc lié
  au type de contenu qu'il produit, c'est acceptable pour un seul type de fichier, mais pas pour des fichiers dont le
  type peut varier

```java
@GetMapping(value = "/download-text", produces = MediaType.TEXT_PLAIN_VALUE)
```

- Utiliser des bibliothèques pour [deviner le type MIME d'un fichier](https://www.baeldung.com/java-file-mime-type).
  Cela fonctionne très bien pour des fichiers dont le type peut fortement varier, au détriment de bibliothèques externes
  et de code supplémentaire à ajouter.
- Renvoyer un type de contenu `application/octet-stream` qui donne l'indice au client que le contenu est binaire, sans
  plus de détails. Cela fonctionne bien avec les navigateurs qui reconstitueront le fichier au client en fonction du
  contenu binaire et est plus simple à coder coté serveur. Je ne dis pas que c'est l'option à toujours privilégier, je
  dis simplement que c'est celle que nous allons réaliser :wink:

```java
@GetMapping(value = "/download-binary", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
```

Nous allons ajouter un nouveau point de terminaison disponible à l'URI `/api/launches/last-transcript` qui exposera au
téléchargement le fichier [last.txt](src/main/resources/launch-transcripts/last.txt) : au vu de l'URI, c'est donc un
point de terminaison qui appartiendra au
controller [LaunchesController](src/main/java/io/vieira/space/launchpad/LaunchesController.java).

> Comment obtenir la Resource que nous renvoyons au client ?

Nous avons vu _à deux reprises_ dans les ateliers Spring Core et Spring Data comment injecter des ressources présentes
dans le classpath de l'application. Je vous laisse fouiller
dans [vos souvenirs](https://docs.spring.io/spring-framework/docs/5.3.x/reference/html/core.html#resources-as-dependencies) !

Une fois que notre ressource est correctement injectée, voyons ce que nous pouvons utiliser dans Spring MVC pour lire le
contenu d'un fichier et le renvoyer dans la réponse HTTP :

- Nous pouvons utiliser
  l'abstraction [`StreamingResponseBody`](https://www.baeldung.com/spring-mvc-sse-streams#streamingresponsebody)
  pour renvoyer le contenu de notre fichier de façon asynchrone (_non-bloquante_) : **c'est une très bonne approche pour
  de gros
  fichiers**, mais elle ne nous concerne pas dans cet exercice

```java
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;

@RestController
@RequestMapping("/api/files")
public class FilesController {

    @GetMapping(value = "/a-file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<StreamingResponseBody> downloadAFile() {
        // C'est un fichier d'exemple
        File theFile = new File("file.txt");
        return ResponseEntity.ok(outputStream -> StreamUtils.copy(new FileInputStream(theFile), outputStream));
    }
}
```

- Renvoyer une _Resource_ (**vu dans la formation Spring Core**) qui sera automatiquement convertie en contenu binaire :

```java
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/files")
public class FilesController {

    private final Resource aFile;

    public FilesController(Resource aFile) {
        this.aFile = aFile;
    }

    @GetMapping(value = "/a-file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Resource downloadAFile() {
        return aFile;
    }
}
```

Nous aurions également pu utiliser cette Resource dans une _ResponseEntity_, mais nous n'avons pas besoin de modifier le
code de statut de la réponse HTTP renvoyée aux clients.

## Ressources

[Returning an Image or a File with Spring MVC](https://www.baeldung.com/spring-controller-return-image-file)

[Streaming raw data](https://docs.spring.io/spring/docs/5.3.x/reference/html/web.html#mvc-ann-async-output-stream)

[Downloading a file using Spring controllers](https://stackoverflow.com/a/10837053)
