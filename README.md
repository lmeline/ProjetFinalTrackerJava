# JavaFx Tracker

## Aperçu du Projet

**JavaFx Tracker** est une application de composition musicale moderne inspirée des célèbres **Trackers** des années 90, comme Protracker sur Amiga. Il permet de jouer des *samples* audio et de placer ces séquences sur une grille temporelle appelée piste. La composition peut être enregistrée dans un fichier.

Ce projet a été réalisé dans le cadre du cours de **B3 - Java Avancé** de l'ESGI et compte pour **50%** de la matière Java Avancé et **50%** de la matière Tests Unitaires.

---

## Fonctionnalités Détaillées

L'application répond aux besoins fonctionnels suivants:

### Piano Virtuel et Jeu de Notes

L'application fournit un **piano de 2 octaves** , permettant de jouer un *sample* à différentes fréquences correspondant à des notes.

* **Touches du Clavier :** Le **`PianoController`** gère les événements clavier et associe des touches spécifiques à des notes (par exemple, la touche `A` joue C-2, la touche `2` joue C#2, etc.).
* **Contrôles Audio :** Lorsqu'une note est jouée, l'objet `Note` est transmis à l'`AudioService` (implémenté par `AudioServiceImpl`). Ce service ajuste le **taux de lecture (`rate`)** de l'`AudioClip` pour modifier la hauteur (`Hauteur`) du *sample* en utilisant la formule :
  $$\text{rate} = \frac{\text{Fréquence de la Note}}{\text{Fréquence du Sample d'origine}}$$
* **Classes Métier Impliquées :** `Note`, `Hauteur` (énumération des fréquences), et `Instrument`.

### Piste et Séquenceur (Tracker)

La piste est la grille temporelle de composition, typiquement de **64 lignes**.

* **Affichage :** Le composant `TrackerList` (`TableView`) affiche les 64 lignes de la séquence, gérées par le `TableauController`.
* **Édition :** L'interface doit permettre d'indiquer une note sur chaque ligne de la piste.
    * **Service d'Enregistrement :** L'`EnregistrementService` (implémenté par `EnregistrementServiceImpl`) permet d'ajouter ou de supprimer une `Note` dans le tableau `Note[] sequence` de l'objet `Piste` à un *step* donné.
    * **Chargement/Sauvegarde :** Les boutons d'ouverture et d'enregistrement de piste sont gérés par le **`PisteService`**.

### Lecture, Arrêt et Pause

Le service de lecture gère la progression de la séquence musicale.

* **Contrôles :** Des boutons sont fournis pour :
    * **`Play` :** Débuter la lecture de la piste.
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
* **Architecture :** Projet Maven avec packages `business`, `service`, `service.impl`, `util`, et `controller`

---

## Installation et Lancement

### Prérequis

* JDK 21 ou supérieur.
* Apache Maven.

### Étapes d'installation

1.  **Cloner le dépôt :**

    ```bash
    git clone git clone --branch main https://github.com/lmeline/ProjetFinalTrackerJava.git
    cd ProjetFinalTrackerJava
    ```

2.  **Lancer l'application avec Maven :**
    Le `pom.xml` est configuré pour l'utilisation des dépendances JavaFX spécifiques à l'environnement de l'utilisateur.

    ```bash
    # Exécute l'application via JavaFX Maven
    mvn clean javafx:run
    ```

---

## Structure du Projet

Le projet suit l'architecture standard Java/Maven, en séparant clairement les couches métier, service et présentation.

--

tracker_poc/ 

README.md # Le présent document.

├── .gitignore # Fichier de configuration pour ignorer les dossiers de compilation.

├── pom.xml # Configuration Maven et dépendances.

├── target/ # Dossier généré par Maven contenant les classes et le JAR.

├── .idea/ # Dossier de configuration de IntelliJ.

└── src/ └── main/ ├── java/ │ 

└── fr/ │ └── esgi/ │ └── tracker/ │

├── module-info.java # Définitions des modules utilisés. │

├── App.java # Point de lancement de l'application JavaFX. │ 

├── business/ # Classes métier (Piste, Note, Instrument, Hauteur, StatutLecture). │ 

├── controller/ # Contrôleurs FXML (TrackerController, PianoController). │ 

├── services/ # Interfaces de service (AudioService, LectureService, PisteService). │ 

└── services/impl/ # Implémentations concrètes des services. 

└── resources/ └── fr/ └── esgi/ └── tracker/ | 

├── credits.fxml # Vue des crédits. 

├── tracker.fxml # Vue principale de l'interface Tracker/Piano. 

├── style.css # Styles de l'application. 

└── instruments/ # Dossier contenant les samples audio (guitar.wav, kick.wav).

---

## Développeurs

Ce projet a été créé et conçu par :

* Loise Meline
* Nicolas Carpita
* Jose Vasquez
* Albin Riviere

Année : 2025-2026, B3 ESGI.