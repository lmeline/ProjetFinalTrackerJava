package fr.esgi.tracker.service;

import fr.esgi.tracker.business.Note;
import fr.esgi.tracker.business.Piste;
import fr.esgi.tracker.dao.PisteDao;
import fr.esgi.tracker.services.impl.PisteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PisteServiceImplTest {

    @InjectMocks
    private PisteServiceImpl pisteService;

    @Mock
    private Piste pisteMock;

    @Mock
    private PisteDao pisteDaoMock;

    private Map<String, Piste> pistes;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        pistes = new HashMap<>();
        injecterChamp(pisteService, "pistes", pistes);

        injecterChamp(pisteService, "pisteDao", pisteDaoMock);
    }

    @Test
    @DisplayName("Vérification chargement piste et mise à jour piste courante")
    void testChargerPiste_Nominal() {
        // Arrange
        String nomPiste = "MaPisteTest";
        when(pisteMock.clone()).thenReturn(pisteMock);
        pistes.put(nomPiste, pisteMock);

        // Act
        Piste resultat = pisteService.chargerPiste(nomPiste);

        // Assert
        assertNotNull(resultat);
        assertEquals(pisteMock, resultat);
        assertEquals(pisteMock, pisteService.getPisteCourante());
    }

    @Test
    @DisplayName("Vérification chargement piste inexistante")
    void testChargerPiste_Inexistante() {
        // Act
        Piste resultat = pisteService.chargerPiste("PisteFantome");

        // Assert
        assertNull(resultat);
    }

    @Test
    @DisplayName("Devrait supprimer une piste de la liste")
    void testSupprimerPiste() {
        // Arrange
        String nomPiste = "PisteASupprimer";
        when(pisteMock.getNomPreset()).thenReturn(nomPiste);
        pistes.put(nomPiste, pisteMock);

        // Act
        pisteService.supprimerPiste(pisteMock);

        // Assert
        assertFalse(pistes.containsKey(nomPiste), "La piste aurait dû être supprimée de la Map");
    }

    @Test
    @DisplayName("Devrait retourner la map complète")
    void testGetToutesLesPistes() {
        // Arrange
        pistes.put("P1", pisteMock);

        // Act
        Map<String, Piste> resultat = pisteService.getToutesLesPistes();

        // Assert
        assertEquals(1, resultat.size());
        assertTrue(resultat.containsKey("P1"));
    }

    @Test
    @DisplayName("Devrait ajouter la piste à la map et demander la sauvegarde JSON")
    void testEnregistrerPiste() {
        // Arrange
        Piste piste = new Piste("AncienNom", new Note[64]);
        String nomChoisi = "NouvellePiste";

        // Act
        pisteService.enregistrerPiste(piste, nomChoisi);

        // Assert
        assertTrue(pistes.containsKey("NouvellePiste"));
        assertEquals(nomChoisi, pistes.get("NouvellePiste").getNomPreset());
        verify(pisteDaoMock).sauvegarder(piste);
    }

    @Test
    @DisplayName("Devrait initialiser et charger toutes les pistes")
    void testChargerToutesLesPistes() {
        // Arrange
        Map<String, Piste> mapRetourneeParLeJson = new HashMap<>();
        mapRetourneeParLeJson.put("PisteDuDisque", pisteMock);

        when(pisteDaoMock.chargerTout()).thenReturn(mapRetourneeParLeJson);

        // Act
        pisteService.chargerToutesLesPistes();

        // Assert
        verify(pisteDaoMock).initialiserDossier(anyList());

        Map<String, Piste> mapDuService = pisteService.getToutesLesPistes();
        assertEquals(1, mapDuService.size());
        assertTrue(mapDuService.containsKey("PisteDuDisque"));
    }

    private void injecterChamp(Object target, String nomChamp, Object valeur) throws Exception {
        Field field = target.getClass().getDeclaredField(nomChamp);
        field.setAccessible(true);
        field.set(target, valeur);
    }
}