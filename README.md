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
* **Architecture :** Projet Maven avec packages `business`, `service`, `service.impl`, `util`, et `controller`

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

tracker_poc/
├── README.md               # Le présent document.
├── .gitignore              # Fichier de configuration Git.
├── pom.xml                 # Configuration Maven (Dépendances JavaFX, JUnit, Mockito).
├── .idea/                  # Configuration IntelliJ.
└── src/
    ├── main/
    │   ├── java/
    │   │   └── fr/esgi/tracker/
    │   │       ├── module-info.java      # Définitions des modules et permissions (opens/exports).
    │   │       ├── App.java              # Point de lancement JavaFX.
    │   │       ├── business/             # Objets métier (Piste, Note, Instrument, Hauteur).
    │   │       ├── controller/           # Contrôleurs FXML (TrackerController).
    │   │       ├── dao/                  # Accès aux données (PisteDao).
    │   │       ├── observer/             # Pattern Observer (LectureObserver).
    │   │       ├── services/             # Interfaces de service.
    │   │       │   └── impl/             # Implémentations (AudioServiceImpl, LectureServiceImpl).
    │   │       └── utils/                # Utilitaires statiques (AudioTools, PisteJsonManager).
    │   │
    │   └── resources/
    │       └── fr/esgi/tracker/
    │           ├── tracker.fxml          # Vues FXML.
    │           ├── style.css             # Feuilles de style.
    │           └── instruments/          # Samples audio (.wav).
    │
    └── test/
        ├── java/
        │   └── fr/esgi/tracker/
        │       ├── business/             # Tests unitaires des objets métier.
        │       └── service/              # Tests unitaires des services (Mocks & Réflexion).
        │
        └── resources/
            └── mockito-extensions/
                └── org.mockito.plugins.MockMaker  # Configuration pour supporter le Mocking sur Java 21+.
```

---

## Développeurs

Ce projet a été créé et conçu par :

* Loïse MELINE
* Nicolas CARPITA
* Jose VASQUEZ
* Albin RIVIERE

Année : 2025-2026, B3 ESGI.
