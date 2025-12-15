# JavaFx Tracker ![Java](https://img.shields.io/badge/Java-23-orange)

## Aperçu du Projet

**JavaFx Tracker** est une application de composition musicale moderne inspirée des célèbres **Trackers** des années 90, comme Protracker sur Amiga. Il permet de jouer des *samples* audio et de placer ces séquences sur une grille temporelle appelée piste. La composition peut être enregistrée dans un fichier.



---

## Guide d'Utilisation Rapide

### Piano Virtuel (Contrôles Clavier)

Le clavier de votre ordinateur est transformé en piano. L'application est configurée pour mapper les rangées de touches aux notes de musique :

* **Notes naturelles (Blanches) :** Utilisez la rangée de lettres (ex: `A`, `Z`, `E`, `R`... en configuration AZERTY).
    * *Exemple :* `A` = Do (C-2), `Z` = Ré (D-2),  `V` = Ré (B-3)...
* **Altérations (Noires - Dièses) :** Utilisez la rangée de chiffres située juste au-dessus.
    * *Exemple :* `2` = Do# (C#2)...

### Composition (Tracker)

L'interface de composition fonctionne comme un tableur musical. Voici les commandes essentielles :

1.  **Navigation :**
    * Utilisez les touches **`,`** (virgule) et **`;`** (point-virgule) pour déplacer le curseur vers le haut ou le bas dans la grille.

2.  **Placer une note :**
    * Sélectionnez une ligne et appuyez sur une touche du clavier (voir section Piano) pour insérer la note.

3.  **Supprimer une note :**
    * Assurez-vous d'être en mode **Record**.
    * Maintenez la touche **`Shift`** (Majuscule) et appuyez sur **`,`** ou **`;`**. Cela supprimera la note présente sur la ligne courante.

4.  **Jouer la piste :**
    * Appuyez sur le bouton **Play** pour écouter votre séquence.
    
5. **Augmenter le volume de la piste :**
    * augmenter ou diminuer le volume de la piste grâce au curseur de **Volume** .

6. **Charger une piste :**
    * cliquer sur le bouton **presets pistes** pour charger une piste.

7. **Enregistrer une note :**
    * cliquer sur le bouton avec un icon rond **Record** pour enregistrer une note.

8. **Enregistrer une piste :**
    * cliquer sur le bouton avec un icon disquette qui est **Enregistrer** pour enregistrer une piste.
9. **Mettre en pause la piste :**
    * cliquer sur le bouton avec un icon pause qui est **Pause** pour mettre en pause la piste.
10. **Stopper la piste :**
    * cliquer sur le bouton avec un icon stop qui est **Stop** pour stopper la piste.
11. **Choix de l'instrument**
    * choisir son instrument grâce au menu **instrument**


---

## Fonctionnalités Détaillées

L'application répond aux besoins fonctionnels suivants:

### Piano Virtuel et Jeu de Notes

L'application fournit un **piano de 2 octaves** , permettant de jouer un *sample* à différentes fréquences correspondant à des notes.

* **Touches du Clavier :** les événements clavier sont associées à des touches spécifiques à des notes (par exemple, la touche `A` joue C-2, la touche `2` joue C#2, etc.).
* **Contrôles Audio :** Lorsqu'une note est jouée, l'objet `Note` est transmis à l'`AudioService` (implémenté par `AudioServiceImpl`). Ce service ajuste la **vitesse de lecture (`rate`)** de l'échantillon audio pour modifier sa hauteur en utilisant la formule :
  $$\text{rate} = \frac{\text{Fréquence de la Note}}{\text{Fréquence du Sample d'origine}}$$
  `**AudioServiceImpl`** utilise `SourceDataLine` pour gérer la lecture audio, initialisant une "boucle de lecture" continue (ou **buffer**), et convertissant (grossièrement) les échantillons audio en bytes pour alimenter le buffer.
* **Classes Métier Impliquées :** `Note`, `Hauteur` (énumération des fréquences), et `Instrument`.

### Piste et Séquenceur (Tracker)

La piste est la grille temporelle de composition, typiquement de **64 lignes**.

* **Affichage :** Le composant `pisteView` (`Vbox`) affiche les 64 lignes de la séquence de manière dynamique, la `Vbox` contient 9 cases (`Hbox`) dans lesquelles sont injectés dynamiquement les lignes à afficher.
* **Édition :** L'interface doit permettre d'indiquer une note sur chaque ligne de la piste.
    * **Service d'Enregistrement :** L'`EnregistrementService` (implémenté par `EnregistrementServiceImpl`) permet d'ajouter ou de supprimer une `Note` dans le tableau `Note[] sequence` de l'objet `Piste` à un *step* donné.
    * **Chargement/Sauvegarde :** Les boutons d'ouverture et d'enregistrement de piste sont gérés par le **`PisteService`**.

### Lecture, Arrêt et Pause

Le service de lecture gère la progression de la séquence musicale.

* **Contrôles :** Des boutons sont fournis pour :
    * **`Play` :** Débuter la lecture de la piste.
    * **`Record` :** Enregistrer la piste.
    * **`Stop` :** Stopper la lecture et revenir au début de la séquence.
    * **`Pause` :** Mettre la lecture en pause (fonctionnalité gérée par le `LectureService`).
* **Service de Lecture :** Le `LectureService` (implémenté par `LectureServiceImpl`) utilise un `ScheduledExecutorService` (`horloge`) pour déclencher la lecture des notes à un rythme fixe.

### Vue des Crédits

* Un bouton dans l'interface principale permet d'ouvrir une vue affichant les noms des développeurs de l'équipe.
* Cette vue est gérée par le **`CreditsController`** et la vue **`Credits.fxml`**.

---

## Technologies Utilisées

* **Langage :** Java 21+
* **Framework GUI :** JavaFX 21 (avec `javafx-controls`, `javafx-fxml`, `javafx-media`) 
* **Gestionnaire de Projet :** Apache Maven
* **Outils de Développement :** IntelliJ IDEA, Scene Builder 
* **Architecture :** Projet Maven avec packages `business`, `service`, `service.impl`, `util`, `dao`, `observer`, `controller`

---

## Installation et Lancement

### Prérequis

* JDK 21 ou supérieur.
* Apache Maven.

### Étapes d'installation

1.  **Cloner le dépôt :**

    ```bash
    git clone --branch main https://github.com/lmeline/ProjetFinalTrackerJava.git
    cd ProjetFinalTrackerJava
    ```
2. **Lancer les tests unitaires**
    Avant de lancer l'application, il est recommandé d'exécuter les tests unitaires pour s'assurer que tout fonctionne correctement.
    ```bash
    mvn test
    ```

3. **Lancer l'application avec Maven :**
    Le `pom.xml` est configuré pour l'utilisation des dépendances JavaFX spécifiques à l'environnement de l'utilisateur.

    ```bash
    # Exécute l'application via JavaFX Maven
    mvn clean javafx:run
    ```

---

## Structure du Projet

Le projet suit l'architecture standard Java/Maven, en séparant clairement les couches métier, service et présentation.

--
```

ProjetFinalTracker
├── README.md
├── doc
│   └── diagramme
│       ├── business_entities.png
│       ├── controllers.png
│       ├── dao.png
│       ├── observer_observable_interfaces.png
│       ├── services_implementations.png
│       ├── services_interfaces.png
│       └── utils.png
├── javadoc
│   ├── allclasses-index.html
│   ├── allpackages-index.html
│   ├── element-list
│   ├── fr.esgi.tracker
│   │   ├── fr
│   │   │   └── esgi
│   │   │       └── tracker
│   │   │           ├── App.html
│   │   │           ├── business
│   │   │           │   ├── Hauteur.html
│   │   │           │   ├── Instrument.html
│   │   │           │   ├── Note.html
│   │   │           │   ├── Piste.html
│   │   │           │   ├── StatutLecture.html
│   │   │           │   ├── StatutRecord.html
│   │   │           │   ├── package-summary.html
│   │   │           │   └── package-tree.html
│   │   │           ├── controller
│   │   │           │   ├── CreditsController.html
│   │   │           │   ├── EnregistrerPisteModaleController.html
│   │   │           │   ├── TrackerController.html
│   │   │           │   ├── package-summary.html
│   │   │           │   └── package-tree.html
│   │   │           ├── dao
│   │   │           │   ├── PisteDao.html
│   │   │           │   ├── package-summary.html
│   │   │           │   └── package-tree.html
│   │   │           ├── observer
│   │   │           │   ├── LectureObservable.html
│   │   │           │   ├── LectureObserver.html
│   │   │           │   ├── package-summary.html
│   │   │           │   └── package-tree.html
│   │   │           ├── package-summary.html
│   │   │           ├── package-tree.html
│   │   │           ├── services
│   │   │           │   ├── AudioService.html
│   │   │           │   ├── EnregistrementService.html
│   │   │           │   ├── InstrumentService.html
│   │   │           │   ├── LectureService.html
│   │   │           │   ├── PisteService.html
│   │   │           │   ├── impl
│   │   │           │   │   ├── AudioServiceImpl.html
│   │   │           │   │   ├── EnregistrementServiceImpl.html
│   │   │           │   │   ├── InstrumentServiceImpl.html
│   │   │           │   │   ├── LectureServiceImpl.html
│   │   │           │   │   ├── PisteServiceImpl.html
│   │   │           │   │   ├── package-summary.html
│   │   │           │   │   └── package-tree.html
│   │   │           │   ├── package-summary.html
│   │   │           │   └── package-tree.html
│   │   │           └── utils
│   │   │               ├── AudioTools.html
│   │   │               ├── PisteJsonManager.html
│   │   │               ├── package-summary.html
│   │   │               └── package-tree.html
│   │   └── module-summary.html
│   ├── help-doc.html
│   ├── index-files
│   │   ├── index-1.html
│   │   ├── index-10.html
│   │   ├── index-11.html
│   │   ├── index-12.html
│   │   ├── index-13.html
│   │   ├── index-14.html
│   │   ├── index-15.html
│   │   ├── index-16.html
│   │   ├── index-17.html
│   │   ├── index-18.html
│   │   ├── index-19.html
│   │   ├── index-2.html
│   │   ├── index-20.html
│   │   ├── index-3.html
│   │   ├── index-4.html
│   │   ├── index-5.html
│   │   ├── index-6.html
│   │   ├── index-7.html
│   │   ├── index-8.html
│   │   └── index-9.html
│   ├── index.html
│   ├── legal
│   │   ├── ADDITIONAL_LICENSE_INFO
│   │   ├── ASSEMBLY_EXCEPTION
│   │   ├── LICENSE
│   │   ├── dejavufonts.md
│   │   ├── jquery.md
│   │   └── jqueryUI.md
│   ├── member-search-index.js
│   ├── module-search-index.js
│   ├── overview-tree.html
│   ├── package-search-index.js
│   ├── resource-files
│   │   ├── copy.svg
│   │   ├── fonts
│   │   │   ├── DejaVuLGCSans-Bold.woff
│   │   │   ├── DejaVuLGCSans-Bold.woff2
│   │   │   ├── DejaVuLGCSans-BoldOblique.woff
│   │   │   ├── DejaVuLGCSans-BoldOblique.woff2
│   │   │   ├── DejaVuLGCSans-Oblique.woff
│   │   │   ├── DejaVuLGCSans-Oblique.woff2
│   │   │   ├── DejaVuLGCSans.woff
│   │   │   ├── DejaVuLGCSans.woff2
│   │   │   ├── DejaVuLGCSansMono-Bold.woff
│   │   │   ├── DejaVuLGCSansMono-Bold.woff2
│   │   │   ├── DejaVuLGCSansMono-BoldOblique.woff
│   │   │   ├── DejaVuLGCSansMono-BoldOblique.woff2
│   │   │   ├── DejaVuLGCSansMono-Oblique.woff
│   │   │   ├── DejaVuLGCSansMono-Oblique.woff2
│   │   │   ├── DejaVuLGCSansMono.woff
│   │   │   ├── DejaVuLGCSansMono.woff2
│   │   │   ├── DejaVuLGCSerif-Bold.woff
│   │   │   ├── DejaVuLGCSerif-Bold.woff2
│   │   │   ├── DejaVuLGCSerif-BoldItalic.woff
│   │   │   ├── DejaVuLGCSerif-BoldItalic.woff2
│   │   │   ├── DejaVuLGCSerif-Italic.woff
│   │   │   ├── DejaVuLGCSerif-Italic.woff2
│   │   │   ├── DejaVuLGCSerif.woff
│   │   │   ├── DejaVuLGCSerif.woff2
│   │   │   └── dejavu.css
│   │   ├── glass.png
│   │   ├── jquery-ui.min.css
│   │   ├── link.svg
│   │   ├── stylesheet.css
│   │   └── x.png
│   ├── script-files
│   │   ├── jquery-3.7.1.min.js
│   │   ├── jquery-ui.min.js
│   │   ├── script.js
│   │   ├── search-page.js
│   │   └── search.js
│   ├── search.html
│   ├── tag-search-index.js
│   └── type-search-index.js
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   ├── fr
    │   │   │   └── esgi
    │   │   │       └── tracker
    │   │   │           ├── App.java
    │   │   │           ├── business
    │   │   │           │   ├── Hauteur.java
    │   │   │           │   ├── Instrument.java
    │   │   │           │   ├── Note.java
    │   │   │           │   ├── Piste.java
    │   │   │           │   ├── StatutLecture.java
    │   │   │           │   └── StatutRecord.java
    │   │   │           ├── controller
    │   │   │           │   ├── CreditsController.java
    │   │   │           │   ├── EnregistrerPisteModaleController.java
    │   │   │           │   └── TrackerController.java
    │   │   │           ├── dao
    │   │   │           │   └── PisteDao.java
    │   │   │           ├── observer
    │   │   │           │   ├── LectureObservable.java
    │   │   │           │   └── LectureObserver.java
    │   │   │           ├── services
    │   │   │           │   ├── AudioService.java
    │   │   │           │   ├── EnregistrementService.java
    │   │   │           │   ├── InstrumentService.java
    │   │   │           │   ├── LectureService.java
    │   │   │           │   ├── PisteService.java
    │   │   │           │   └── impl
    │   │   │           │       ├── AudioServiceImpl.java
    │   │   │           │       ├── EnregistrementServiceImpl.java
    │   │   │           │       ├── InstrumentServiceImpl.java
    │   │   │           │       ├── LectureServiceImpl.java
    │   │   │           │       └── PisteServiceImpl.java
    │   │   │           └── utils
    │   │   │               ├── AudioTools.java
    │   │   │               └── PisteJsonManager.java
    │   │   └── module-info.java
    │   └── resources
    │       └── fr
    │           └── esgi
    │               └── tracker
    │                   ├── EnregistrerPisteModale.fxml
    │                   ├── assets
    │                   │   ├── fonts
    │                   │   │   └── LCD.ttf
    │                   │   └── icons
    │                   │       ├── grip_slider.png
    │                   │       ├── pause_off.png
    │                   │       ├── pause_on.png
    │                   │       ├── play_off.png
    │                   │       ├── play_on.png
    │                   │       ├── record_off.png
    │                   │       ├── record_on.png
    │                   │       ├── save_diskette.png
    │                   │       ├── stop_off.png
    │                   │       └── stop_on.png
    │                   ├── credits.fxml
    │                   ├── instruments
    │                   │   ├── guitar_amped_mid.wav
    │                   │   ├── piano_C3.wav
    │                   │   ├── sw_bass.wav
    │                   │   ├── sw_hat.wav
    │                   │   ├── sw_kick.wav
    │                   │   └── sw_snare.wav
    │                   ├── style.css
    │                   └── tracker.fxml
    └── test
        ├── java
        │   └── fr
        │       └── esgi
        │           └── tracker
        │               ├── business
        │               │   ├── InstrumentTest.java
        │               │   ├── NoteTest.java
        │               │   └── PisteTest.java
        │               └── service
        │                   ├── AudioServiceImplTest.java
        │                   ├── EnregistrementServiceImplTest.java
        │                   ├── InstrumentServiceImplTest.java
        │                   ├── LectureServiceImplTest.java
        │                   └── PisteServiceImplTest.java
        └── resources
            └── mockito-extensions
                └── org.mockito.plugins.MockMaker

```

---

## Développeurs

Ce projet a été créé et conçu par :

* Loïse MELINE
* Nicolas CARPITA
* Jose VASQUEZ
* Albin RIVIERE

Année : 2025-2026, B3 ESGI.
