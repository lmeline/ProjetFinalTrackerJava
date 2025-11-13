package fr.esgi.tracker.business;

public enum Hauteur {
    C2(65.41),
    CSharp2(69.30),
    D2(73.42),
    DSharp2(77.78),
    E2(82.41),
    F2(87.31),
    FSharp2(92.50),
    G2(98.00),
    GSharp2(103.83),
    A2(110.00),
    ASharp2(116.54),
    B2(123.47),
    C3(130.81),
    CSharp3(138.59),
    D3(146.83),
    DSharp3(155.56),
    E3(164.81),
    F3(174.61),
    FSharp3(185.00),
    G3(196.00),
    GSharp3(207.65),
    A3(220.00),
    ASharp3(233.08),
    B3(246.94),
    C4(261.62);

    private final double frequence;

    Hauteur(double frequence) {
        this.frequence = frequence;
    }

    public double getFrequence() {
        return frequence;
    }
}
