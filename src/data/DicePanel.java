package data;

import java.util.ArrayList;
import java.util.Random;

public class DicePanel {
	private Coloring[] colors = new Coloring[11];
	private Dice[][] dices = new Dice[6][11]; // Spielfeld mit 66 Würfeln mit Dimension 6x11
	private ArrayList<Pair> list = new ArrayList<>();
	private ArrayList<Integer> numbers = new ArrayList<>();
	private int resultPoints = 0;
	private boolean swop = false;

	public DicePanel() {
		colors = Coloring.values();
		initDicePanel();

	}

	public void initDicePanel() {

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 11; j++) {
				Dice dice = initDice(i, j);
				dices[i][j] = dice;
			}

		}
		// Sicherstellen dass am Anfang keine Punkte moeglich
		checkForDefaultPoints();

		// Liste der Wuerfel erstellen
		createList();

	}

	// Liste der Wuerfel als Paare erstellen
	synchronized public void createList() {
		list.clear();
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 11; j++) {
				Pair p = new Pair(dices[i][j].getColor().toString(), dices[i][j].getNumber());

				list.add(p);
			}
		}
	}

	// Wuerfel mit zufaelliger Farbe und Nummer hinzufuegen
	private Dice initDice(int x, int y) {
		int number = (new Random()).nextInt(6) + 1;
		Coloring color = colors[(new Random()).nextInt(11)];
		Dice dice = new Dice(number, color, x, y);

		return dice;
	}

	// Tauscht Wuerfel bis keine Kombinationen mehr moeglich sind (zu Beginn ausgeführt)
	public void checkForDefaultPoints() {
		boolean defaultPoints = false;

		int checkKind = 0;
		int checkRow = 0;
		boolean upwards = false;
		boolean downwards = false;

		do {
			defaultPoints = false;

			// Horizontale pruefen
			for (int i = 0; i < 6; i++) {
				for (int j = 1; j < 11; j++) {

					// Paare
					if (dices[i][j].getNumber() == dices[i][j - 1].getNumber()) {
						checkKind++;
						if (checkKind == 2) { // 3 dices in a row
							do {
								dices[i][j] = initDice(i, j);
							} while (dices[i][j].getNumber() == dices[i][j - 1].getNumber());
							checkKind = 0;
							defaultPoints = true;
						}
					} else {
						checkKind = 0;
					}

					// Reihe
					if ((dices[i][j].getNumber() - dices[i][j - 1].getNumber()) == 1) { // 1. Wuerfel < 2.Wuerfel
						checkRow++;
						if ((checkRow == 2) && upwards) {
							do {
								dices[i][j] = initDice(i, j);
							} while ((dices[i][j].getNumber() - dices[i][j - 1].getNumber()) == 1);

							checkRow = 0;
							defaultPoints = true;
						} else if ((checkRow == 2) && !upwards) {
							checkRow = 0;
						}
						upwards = true;

					} else if ((dices[i][j].getNumber() - dices[i][j - 1].getNumber()) == -1) { // 1. dice > 2. dice
						checkRow++;
						if ((checkRow == 2) && downwards) {
							do {
								dices[i][j] = initDice(i, j);
							} while ((dices[i][j].getNumber() - dices[i][j - 1].getNumber()) == -1);
							checkRow = 0;
							defaultPoints = true;
						} else if ((checkRow == 2) && !downwards) {
							checkRow = 0;
						}
						downwards = true;

					} else {
						checkRow = 0;
						upwards = false;
						downwards = false;
					}
				}
			}

			// Vertikale pruefen
			for (int j = 0; j < 11; j++) {
				for (int i = 1; i < 6; i++) {
					// Paare
					if (dices[i][j].getNumber() == dices[i - 1][j].getNumber()) {
						checkKind++;
						if (checkKind == 2) { // 3 gleiche Wuerfel in einer Reihe/Spalte
							do {
								dices[i][j] = initDice(i, j);
							} while (dices[i][j].getNumber() == dices[i - 1][j].getNumber());
							checkKind = 0;
							defaultPoints = true;
						}
					}

					// Reihen
					if ((dices[i][j].getNumber() - dices[i - 1][j].getNumber()) == 1) { // 1. Wuerfel < 2.Wuerfel
						checkRow++;
						if ((checkRow == 2) && upwards) {
							do {
								dices[i][j] = initDice(i, j);
							} while ((dices[i][j].getNumber() - dices[i - 1][j].getNumber()) == 1);

							checkRow = 0;
							defaultPoints = true;
						} else if ((checkRow == 2) && !upwards) {
							checkRow = 0;
						}
						upwards = true;
					} else if ((dices[i][j].getNumber() - dices[i - 1][j].getNumber()) == -1) { // 1. Wuerfel > 2.
																								// Wuerfel
						checkRow++;
						if ((checkRow == 2) && downwards) {
							do {
								dices[i][j] = initDice(i, j);
							} while ((dices[i][j].getNumber() - dices[i - 1][j].getNumber()) == -1);

							checkRow = 0;
							defaultPoints = true;
						} else if ((checkRow == 2) && !downwards) {
							checkRow = 0;
						}
						downwards = true;
					} else {
						checkRow = 0;
						upwards = false;
						downwards = false;
					}
				}
			}
		} while (defaultPoints);

	}

	// Punkte berechnen bei Austauschen zweier Wuerfel
	public int checkPoints(int posX1, int posY1, int posX2, int posY2) {
		int points = 0;
		resultPoints = 0;
		int combinations = 0;

		// Wuerfel tauschen
		Dice d1 = dices[posX1][posY1];
		Dice d2 = dices[posX2][posY2];
		dices[posX1][posY1] = d2;
		dices[posX2][posY2] = d1;

		// Wuerfel 1
		// Vertikal
		for (int i = 0; i < 6; i++) {
			numbers.add(dices[i][posY1].getNumber());
		}

		points = checkRowForPoints(points, numbers, posX1);
		numbers.clear();

		if (points != 0) {
			combinations++;
			resultPoints += points;
			points = 0;
		}

		// Horizontal
		for (int i = 0; i < 11; i++) {
			numbers.add(dices[posX1][i].getNumber());
		}

		points = checkRowForPoints(points, numbers, posY1);
		numbers.clear();

		if (points != 0) {
			combinations++;
			resultPoints += points;
			points = 0;
		}

		// Pruefen ob ein Swap zustande kam
		if (swop) {
			dices[posX1][posY1].setHole(true);
			; // Schwarzes Loch erstellen
		}
		swop = false;

		// Wenn Wuerfel nicht nebeneinander liegen, dann checke Punkte für 2. Wuerfel
		if (!((posY1 == posY2) && (Math.abs(posX1 - posX2) <= 2))) { // Wuerfel liegen nicht vertikal nebeneinander (mit
																		// hoechstens einem Wuerfel Abstand)
			// Vertikal
			for (int i = 0; i < 6; i++) {
				numbers.add(dices[i][posY2].getNumber());
			}
			points = checkRowForPoints(points, numbers, posX2);
			numbers.clear();

			if (points != 0) {
				combinations++;
				resultPoints += points;
				points = 0;
			}
		}

		if (!((posX1 == posX2) && (Math.abs(posY1 - posY2) <= 2))) { // Wuerfel liegen nicht horizontal
																		// nebeneinander (mit hoechstens einem
																		// Wuerfel Abstand)
			// Horizontal
			for (int i = 0; i < 11; i++) {
				numbers.add(dices[posX2][i].getNumber());
			}
			points = checkRowForPoints(points, numbers, posY2);
			numbers.clear();

			if (points != 0) {
				combinations++;
				resultPoints += points;
				points = 0;
			}

		}

		// Pruefen ob ein Swap zustande kam
		if (swop) {
			dices[posX2][posY2].setHole(true); // Schwarzes Loch erstellen
		}
		swop = false;

		// Liste der Wuerfel erstellen
		createList();
		if (combinations >= 2) {
			resultPoints += combinations - 1;
		}

		return resultPoints;
	}

	// Pruefen ob Punkte in einer Reihe/Spalte
	public int checkRowForPoints(int points, ArrayList<Integer> numbers, int index) {
		int checkKind = 0;
		int checkRowU = 0;
		int checkRowD = 0;

		// wenn index nicht das erste Element -> links vom index pruefen
		if (index != 0) {
			// Paare
			for (int i = index; i > 0; i--) {
				if (numbers.get(i) == numbers.get(i - 1)) {
					checkKind++;
					if (checkKind >= 2) {
						if (checkKind == 5) {
							points += 2;
							swop = true;
							break;
						}
						points++;
					}
				} else {
					break;
				}
			}

			// aufsteigende Reihe
			for (int i = index; i > 0; i--) {

				if ((numbers.get(i) - numbers.get(i - 1)) == 1) { // linker Wuerfel < rechter Wuerfel : aufsteigend

					checkRowU++;
					if ((checkRowU >= 2)) {
						if (checkRowU == 5) {
							points += 2;
							swop = true;
							break;
						}
						points++;
					}
				} else {
					break;
				}
			}

			// abteigende Reihe
			for (int i = index; i > 0; i--) {
				if ((numbers.get(i) - numbers.get(i - 1)) == -1) { // linker Wuerfel > rechter Wuerfel: absteigend
					checkRowD++;
					if ((checkRowD >= 2)) {
						if (checkRowD == 5) {
							points += 2;
							swop = true;
							break;
						}
						points++;
					}
				} else {
					break;
				}
			}

		}

		// wenn index nicht letztes Element -> pruefe rechts vom index
		if ((index != (numbers.size() - 1)) && !swop) {

			// Paare
			for (int i = index; i < numbers.size() - 1; i++) {
				if (numbers.get(i) == numbers.get(i + 1)) {
					checkKind++;
					if (checkKind >= 2) {
						if (checkKind == 5) {
							points += 2;
							swop = true;
							break;
						}
						points += 1;
					}
				} else {
					break;
				}
			}

			// aufsteigende Reihe
			for (int i = index; i < numbers.size() - 1; i++) {

				if ((numbers.get(i + 1) - numbers.get(i)) == 1) { // linker Wuerfel < rechter Wuerfel : aufsteigend
					checkRowU++;
					if ((checkRowU >= 2)) {
						if (checkRowU == 5) {
							points += 2;
							swop = true;
							break;
						}
						points++;
					}
				} else {
					break;
				}
			}

			// absteigende Reihe
			for (int i = index; i < numbers.size() - 1; i++) {

				if ((numbers.get(i + 1) - numbers.get(i)) == -1) { // linker Wuerfel > rechter Wuerfel: absteigend
					checkRowD++;
					if ((checkRowD >= 2)) {
						if (checkRowD == 5) {
							points += 2;
							swop = true;
							break;
						}
						points++;
					}
				} else {
					break;
				}

			}
		}

		return points;
	}

	public ArrayList<Pair> getList() {
		return list;
	}

}