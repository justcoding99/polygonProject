/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructureproje1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author meltem koc
 */
public class MainMenu {
        private int choice;
	private int numberOfPolygons;
	private Polygon[] polygons = new Polygon[1000000];

	public MainMenu() {
		this.numberOfPolygons = 0;
		for (int i = 0; i < polygons.length; i++) {
			this.polygons[i] = null;
		}
	}

	public int getChoice() {
		return choice;
	}

	public void setChoice(int secim) {
		this.choice = secim;
	}

	public int getNumberOfPolygons() {
		return numberOfPolygons;
	}

	public void setNumberOfPolygons(int numberOfPolygons) {
		this.numberOfPolygons = numberOfPolygons;
	}

	public Polygon[] getPolygons() {
		return polygons;
	}

	public void setPolygons(Polygon[] polygons) {
		this.polygons = polygons;
	}

	//Poligonun noktalarini ekrana yazdiriyor.
	public void printPoints(int choice) {
		Point[] pointsArray = polygons[choice - 1].returnPointsOfPolygon();
		for (int i = 0; i < pointsArray.length; i++) {
			System.out.println("Point " + (i + 1) + ":");
			System.out.println(pointsArray[i].toString());
		}
	}

	//Butun poligonlari noktalariyla birlikte ekrana yazdiriyor.
	public void printPolygons() {
		for (int i = 0; i < numberOfPolygons; i++) {
			System.out.println("Polygon " + (i + 1) + ":");
			printPoints(i + 1);
			System.out.println();
		}
	}

	//Kullanicinin sadece tamsayi girmesini sagliyor.
	public void controlInput(Scanner sc) {
		while (!sc.hasNextInt()) {
			System.out.println("You didn't enter a number. Please enter a number.");
			sc.nextLine();
		}
	}

	//Verilen siradaki poligonu kaldiriyor.
	public void removePolygon(int choice) {
		if (choice == 999999) {
			this.polygons[999999] = null;
		} else {
			this.polygons[choice] = null;
			for (int i = choice; i < this.numberOfPolygons; i++) {
				this.polygons[i] = this.polygons[i + 1];
			}
		}
	}

	public static void main(String[] args) {
		MainMenu mainMenu = new MainMenu();
		Scanner sc = new Scanner(System.in);

		System.out.println("There can be created maximum 1 million polygons at the moment.");
		do {
			System.out.print(
					"\n1-) Create new polygon\n2-) Create new polygon with Queue\n3-) Create new polygon with Stack"
							+ "\n4-) Show coordinates of points of a polygon"
							+ "\n5-) Find length of a polygon\n6-) Describe a polygon\n7-) Reverse order of "
							+ "points of a polygon\n8-) Close an open polygon\n9-) Find area of a closed polygon"
							+ "\n10-) Add a new point or remove a point from a polygon\n"
							+ "11-) Concatenate an open polygon with another open polygon\n"
							+ "12-) Exit the program\n");

			mainMenu.controlInput(sc);
			mainMenu.setChoice(sc.nextInt());

			if (mainMenu.getChoice() == 1) {
				int choice = 1;
				boolean isExitLoop = false;
				Polygon newPolygon = new Polygon();
				do {
					if (choice == 0 && newPolygon.getNumberOfPoints() >= 1 && !(newPolygon.getIsPolygonClosed())) {
						System.out.println("1-) Add a new point");
						System.out.println("2-) Exit adding points");
						mainMenu.controlInput(sc);
						choice = sc.nextInt();
						if (choice <= 0 || choice > 2) {
							System.out.println("You entered a wrong number. Please enter 1 or 2.");
							continue;
						}
					}
					if ((choice == 1 || newPolygon.getNumberOfPoints() == 0) && !(newPolygon.getIsPolygonClosed())) {
						System.out.print("Enter x coordinate: ");
						mainMenu.controlInput(sc);
						int x = sc.nextInt();
						System.out.print("Enter y coordinate: ");
						mainMenu.controlInput(sc);
						int y = sc.nextInt();
						if (newPolygon.getNumberOfPoints() == 0) {
							newPolygon.addPoint(x, y);
							System.out.println("The first point is added.");
							choice = 0;
						} else {
							Point[] pointsOfPolygon = newPolygon.returnPointsOfPolygon();
							for (int i = 0; i < pointsOfPolygon.length; i++) {
								if (new Point(x, y).getX_cord() == pointsOfPolygon[0].getX_cord()
										&& new Point(x, y).getY_cord() == pointsOfPolygon[0].getY_cord()
										&& newPolygon.getNumberOfPoints() > 2) {
									newPolygon.setIsPolygonClosed(true);
									newPolygon.addPoint(x, y);
									isExitLoop = true;
									System.out.println("The last point is added.");
									System.out.println("Polygon is closed.");
									break;
								}
								if (new Point(x, y).getX_cord() == pointsOfPolygon[i].getX_cord()
										&& new Point(x, y).getY_cord() == pointsOfPolygon[i].getY_cord()) {
									System.out.println("You entered coordinates of an existing point.");
									choice = 1;
									break;
								}
								if (i == pointsOfPolygon.length - 1) {
									newPolygon.addPoint(x, y);
									System.out.println("The new point is added.");
									choice = 0;
								}
							}
						}
					} else if (choice == 2) {
						isExitLoop = true;
					}
					else {
						choice = 0;
						continue;
					}
				} while (!isExitLoop);
				mainMenu.getPolygons()[mainMenu.getNumberOfPolygons()] = newPolygon;
				mainMenu.setNumberOfPolygons(mainMenu.getNumberOfPolygons() + 1);
				System.out.println("The polygon is created.");

			} else if (mainMenu.getChoice() == 2) {
				int choice = 1;
				Queue<Point> queue = new LinkedList<Point>();
				boolean isNewPoint = true;
				boolean isClosed = false;
				boolean isExitLoop = false;
				do {
					if (choice == 0 && queue.size() >= 1) {
						System.out.println("1-) Add a new point");
						System.out.println("2-) Exit adding points");
						mainMenu.controlInput(sc);
						choice = sc.nextInt();
						if (choice < 0 || choice > 2) {
							System.out.println("You entered a wrong number. Please enter 1 or 2.");
							continue;
						}
					}
					if ((choice == 1) && ((queue.size() == 0) || (queue.size() >= 1))) {
						System.out.print("Enter x coordinate: ");
						mainMenu.controlInput(sc);
						int x = sc.nextInt();
						System.out.print("Enter y coordinate: ");
						mainMenu.controlInput(sc);
						int y = sc.nextInt();
						if (queue.size() == 0) {
							queue.add(new Point(x, y));
							System.out.println("The first point is added.");
							choice = 0;
						} else {

							for (Point point : queue) {
								if (new Point(x, y).getX_cord() == queue.element().getX_cord()
										&& new Point(x, y).getY_cord() == queue.element().getY_cord()
										&& queue.size() > 2) {
									isClosed = true;
									queue.add(new Point(x, y));
									System.out.println("Polygon is closed.");
									isNewPoint = true;
									isExitLoop = true;
									break;
								}
								if (new Point(x, y).getX_cord() == point.getX_cord()
										&& new Point(x, y).getY_cord() == point.getY_cord()) {
									System.out.println("You entered coordinates of an existing point.");
									isNewPoint = false;
									choice = 1;
									break;
								}
							}
							if (isNewPoint) {
								queue.add(new Point(x, y));
								System.out.println("The new point is added.");
								choice = 0;
							}
						}
					}
					else if (choice == 2) {
						isExitLoop = true;
					}
					else {
						choice = 0;
						continue;
					}
				} while (!isExitLoop);
				Polygon newPolygon = new Polygon(queue, isClosed);
				mainMenu.getPolygons()[mainMenu.getNumberOfPolygons()] = newPolygon;
				mainMenu.setNumberOfPolygons(mainMenu.getNumberOfPolygons() + 1);
				System.out.println("The polygon is created.");

			} else if (mainMenu.getChoice() == 3) {
				int choice = 1;
				Stack<Point> stack = new Stack<Point>();
				boolean isNewPoint = true;
				boolean isClosed = false;
				boolean isExitLoop = false;
				do {
					if (choice == 0 && stack.size() >= 1) {
						System.out.println("1-) Add a new point");
						System.out.println("2-) Exit adding points");
						mainMenu.controlInput(sc);
						choice = sc.nextInt();
						if (choice < 0 || choice > 2) {
							System.out.println("You entered a wrong number. Please enter 1 or 2.");
							continue;
						}
					}
					if (choice == 1 && (stack.size() == 0 || stack.size() >= 1)) {
						System.out.print("Enter x coordinate: ");
						mainMenu.controlInput(sc);
						int x = sc.nextInt();
						System.out.print("Enter y coordinate: ");
						mainMenu.controlInput(sc);
						int y = sc.nextInt();
						if (stack.size() == 0) {
							stack.add(new Point(x, y));
							System.out.println("The first point is added.");
							choice = 0;
						} else {

							for (Point point : stack) {
								if (new Point(x, y).getX_cord() == stack.lastElement().getX_cord()
										&& new Point(x, y).getY_cord() == stack.lastElement().getY_cord()
										&& stack.size() > 2) {
									isClosed = true;
									stack.push(new Point(x, y));
									System.out.println("The last point is added.");
									isExitLoop = true;
									System.out.println("Polygon is closed.");
									isNewPoint = true;
									break;
								}
								if (new Point(x, y).getX_cord() == point.getX_cord()
										&& new Point(x, y).getY_cord() == point.getY_cord()) {
									System.out.println("You entered coordinates of an existing point.");
									isNewPoint = false;
									choice = 1;
									break;
								}
							}
							if (isNewPoint) {
								stack.push(new Point(x, y));
								System.out.println("The new point is added.");
								choice = 0;
							}
						}
					}
					else if (choice == 2) {
						isExitLoop = true;
					}
					else {
						choice = 0;
						continue;
					}
				} while (!isExitLoop);
				Polygon newPolygon = new Polygon(stack, isClosed);
				mainMenu.getPolygons()[mainMenu.getNumberOfPolygons()] = newPolygon;
				mainMenu.setNumberOfPolygons(mainMenu.getNumberOfPolygons() + 1);
				System.out.println("The polygon is created.");

			} else if (mainMenu.getChoice() == 4) {
				if (mainMenu.getNumberOfPolygons() > 0) {
					int choice = 0;
					do {
						if (mainMenu.getNumberOfPolygons() == 1) {
							System.out.print(
									"There is only one polygon. Please enter 1 to show the polygon's coordinates: ");
						} else {
							System.out.print(
									"There are " + mainMenu.getNumberOfPolygons() + " polygons. Enter the sequence "
											+ "number of the polygon whose coordinates you want to view: ");
						}
						mainMenu.controlInput(sc);
						choice = sc.nextInt();
						if (choice <= mainMenu.getNumberOfPolygons() && choice > 0) {
							mainMenu.printPoints(choice);
						} else {
							if (mainMenu.getNumberOfPolygons() == 1) {
								System.out.println("You entered a wrong number. Please enter 1.");
							} else {
								System.out.println("You entered a wrong number. Please enter a number between 1 - "
										+ mainMenu.getNumberOfPolygons() + ".");
							}
						}
					} while (choice <= 0 || choice > mainMenu.getNumberOfPolygons());

				} else if (mainMenu.getNumberOfPolygons() == 0) {
					System.out.println("There are no polygons at the moment.");
				}

			} else if (mainMenu.getChoice() == 5) {
				if (mainMenu.getNumberOfPolygons() > 0) {
					int choice = 0;
					do {
						mainMenu.printPolygons();
						if (mainMenu.getNumberOfPolygons() == 1) {
							System.out.print("There is only one polygon. Enter 1 to calculate the polygon's length: ");
						} else {
							System.out.print(
									"There are " + mainMenu.getNumberOfPolygons() + " polygons. Enter the sequence "
											+ "number of the polygon whose you want to learn of its length: ");
						}
						mainMenu.controlInput(sc);
						choice = sc.nextInt();
						if (choice <= mainMenu.getNumberOfPolygons() && choice > 0) {
							Polygon polygonTemp = mainMenu.getPolygons()[choice - 1];
							double length = polygonTemp.findLength();
							System.out.println("This polygon's length is: " + length);
						} else {
							if (mainMenu.getNumberOfPolygons() == 1) {
								System.out.println("You entered a wrong number. Please enter 1.");
							} else {
								System.out.println("You entered a wrong number. Please enter a number between 1 - "
										+ mainMenu.getNumberOfPolygons() + ".");
							}
						}
					} while (choice <= 0 || choice > mainMenu.getNumberOfPolygons());
				} else if (mainMenu.getNumberOfPolygons() == 0) {
					System.out.println("There are no polygons at the moment.");
				}

			} else if (mainMenu.getChoice() == 6) {
				if (mainMenu.getNumberOfPolygons() > 0) {
					int choice = 0;
					do {
						mainMenu.printPolygons();
						if (mainMenu.getNumberOfPolygons() == 1) {
							System.out.print("There is only one polygon. Enter 1 to describe the polygon: ");
						} else {
							System.out.print(
									"There are " + mainMenu.getNumberOfPolygons() + " polygons. Enter the sequence "
											+ "number of the polygon whose you want to describe: ");
						}
						mainMenu.controlInput(sc);
						choice = sc.nextInt();
						if (choice <= mainMenu.getNumberOfPolygons() && choice > 0) {
							Polygon polygonTemp = mainMenu.getPolygons()[choice - 1];
							System.out.println(polygonTemp.toString());
						} else {
							if (mainMenu.getNumberOfPolygons() == 1) {
								System.out.println("You entered a wrong number. Please enter 1.");
							} else {
								System.out.println("You entered a wrong number. Please enter a number between 1 - "
										+ mainMenu.getNumberOfPolygons() + ".");
							}
						}
					} while (choice <= 0 || choice > mainMenu.getNumberOfPolygons());
				} else if (mainMenu.getNumberOfPolygons() == 0) {
					System.out.println("There are no polygons at the moment.");
				}

			} else if (mainMenu.getChoice() == 7) {
				if (mainMenu.getNumberOfPolygons() > 0) {
					int choice = 0;
					do {
						mainMenu.printPolygons();
						if (mainMenu.getNumberOfPolygons() == 1) {
							System.out.print(
									"There is only one polygon. Enter 1 to reverse the order of points of the polygon: ");
						} else {
							System.out.print(
									"There are " + mainMenu.getNumberOfPolygons() + " polygons. Enter the sequence "
											+ "number of the polygon whose you want to describe: ");
						}
						System.out
								.print("There are " + mainMenu.getNumberOfPolygons() + " polygons. Enter the sequence "
										+ "number of the polygon whose you want to reverse the order of points: ");
						choice = sc.nextInt();
						if (choice <= mainMenu.getNumberOfPolygons() && choice > 0) {
							mainMenu.getPolygons()[choice - 1].reverseOrderOfPoints();
							System.out.println("Coordinates of the polygon is reversed.");
						} else {
							if (mainMenu.getNumberOfPolygons() == 1) {
								System.out.println("You entered a wrong number. Please enter 1.");
							} else {
								System.out.println("You entered a wrong number. Please enter a number between 1 - "
										+ mainMenu.getNumberOfPolygons() + ".");
							}
						}
					} while (choice <= 0 || choice > mainMenu.getNumberOfPolygons());
				} else if (mainMenu.getNumberOfPolygons() == 0) {
					System.out.println("There are no polygons at the moment.");
				}

			} else if (mainMenu.getChoice() == 8) {
				if (mainMenu.getNumberOfPolygons() > 0) {
					int choice = 0;
					int openIndexesCount = 0;
					boolean isExitLoop = false;
					int[] openIndexes = new int[mainMenu.getNumberOfPolygons()];
					do {
						mainMenu.printPolygons();
						if(mainMenu.getNumberOfPolygons() == 1) {
							System.out.print("There is only one polygon.");
						}
						else {
							System.out.print("There are " + mainMenu.getNumberOfPolygons() + " polygons. ");
						}
						for (int i = 0; i < mainMenu.numberOfPolygons; i++) {
							if (!(mainMenu.getPolygons()[i].getIsPolygonClosed())) {
								openIndexes[i] = 1;
								openIndexesCount++;
							} else {
								openIndexes[i] = 0;
							}
						}
						if (openIndexesCount > 0) {
							if (openIndexesCount == 1) {
								System.out.print("There is only one open polygon. Polygon ");
								for (int i = 0; i < openIndexes.length; i++) {
									if (openIndexes[i] == 1) {
										System.out.print((i + 1) + " is open.");
									}
								}
							} else {
								System.out.print("There are " + openIndexesCount + " open polygons. ");
								int count = 0;
								for (int i = 0; i < openIndexes.length; i++) {
									if (openIndexes[i] == 1) {
										if (count < (openIndexesCount - 1)) {
											System.out.print("Polygon " + (i + 1) + " and ");
											count++;
										} else {
											System.out.print("Polygon " + (i + 1));
										}
									}
								}
								System.out.print(" are open.");
							}
							System.out.println();
							System.out
									.print("Enter the sequence number of the polygon whose you want it to be closed: ");
							mainMenu.controlInput(sc);
							choice = sc.nextInt();
							boolean isChoiceTrue = false;
							if (choice <= 0 || choice > openIndexes.length) {
								System.out.println("You entered a wrong number.");
								continue;
							}
							for (int i = 0; i < openIndexes.length; i++) {
								if (openIndexes[choice - 1] == 1) {
									isChoiceTrue = true;
								}
							}
							if (isChoiceTrue) {
								if (mainMenu.getPolygons()[choice - 1].getNumberOfPoints() > 2) {
									for (int i = 0; i < openIndexes.length; i++) {
										if (choice == (i + 1)) {
											mainMenu.getPolygons()[choice - 1].closePolygon();
											System.out.println("The polygon is closed.");
											isExitLoop = true;
											break;
										}
									}
								} else {
									System.out.println("The polygon can't be closed.");
									isExitLoop = true;
									break;
								}
							} else {
								System.out.println("You entered a wrong number.");
								continue;
							}
						} else {
							System.out.println("There are no open polygon to close.");
							break;
						}

					} while (!isExitLoop);
				} else if (mainMenu.getNumberOfPolygons() == 0) {
					System.out.println("There are no polygons at the moment.");
				}

			} else if (mainMenu.getChoice() == 9) {
				if (mainMenu.getNumberOfPolygons() > 0) {
					int choice = 0;
					int closeIndexesCount = 0;
					boolean isExitLoop = false;
					int[] closeIndexes = new int[mainMenu.getNumberOfPolygons()];
					do {
						mainMenu.printPolygons();
						if(mainMenu.getNumberOfPolygons() == 1) {
							System.out.print("There is only one polygon.");
						}
						else {
							System.out.print("There are " + mainMenu.getNumberOfPolygons() + " polygons. ");
						}
						for (int i = 0; i < mainMenu.numberOfPolygons; i++) {
							if (mainMenu.getPolygons()[i].getIsPolygonClosed()) {
								closeIndexes[i] = 1;
								closeIndexesCount++;
							} else {
								closeIndexes[i] = 0;
							}
						}
						if (closeIndexesCount > 0) {
							if (closeIndexesCount == 1) {
								System.out.print("There is only one closed polygon. Polygon ");
								for (int i = 0; i < closeIndexes.length; i++) {
									if (closeIndexes[i] == 1) {
										System.out.print((i + 1) + " is closed.");
									}
								}
							} else {
								System.out.print("There are " + closeIndexesCount + " open polygons. ");
								int count = 0;
								for (int i = 0; i < closeIndexes.length; i++) {
									if (closeIndexes[i] == 1) {
										if (count < (closeIndexesCount - 1)) {
											System.out.print("Polygon " + (i + 1) + " and ");
											count++;
										} else {
											System.out.print("Polygon " + (i + 1));
										}
									}
								}
								System.out.print(" are closed.");
							}
							System.out.println();
							System.out.print(
									"Enter the sequence number of the polygon whose you want to calculate its area: ");
							mainMenu.controlInput(sc);
							choice = sc.nextInt();
							boolean isChoiceTrue = false;
							if (choice <= 0 || choice > closeIndexes.length) {
								System.out.println("You entered a wrong number.");
								continue;
							}
							for (int i = 0; i < closeIndexes.length; i++) {
								if (closeIndexes[choice - 1] == 1) {
									isChoiceTrue = true;
								}
							}
							if (isChoiceTrue) {
								double area = mainMenu.getPolygons()[choice - 1].findArea();
								System.out.println("The area of the polygon: " + area);
								isExitLoop = true;
								break;
							} else {
								System.out.println("You entered a wrong number.");
								continue;
							}
						} else {
							System.out.println("There are no closed polygon.");
							break;
						}

					} while (!isExitLoop);
				} else if (mainMenu.getNumberOfPolygons() == 0) {
					System.out.println("There are no polygons at the moment.");
				}

			} else if (mainMenu.getChoice() == 10) {
				if (mainMenu.getNumberOfPolygons() > 0) {
					int choice = 0;
					boolean isExitLoop = false;
					boolean isExitLoop_1 = false;
					boolean isPolygonClosed = false;
					do {
						mainMenu.printPolygons();
						if (mainMenu.getNumberOfPolygons() == 1) {
							System.out.print(
									"There is only one polygon at the moment. Please enter 1 to add or remove point from the polygon: ");
						} else {
							System.out.print("There are " + mainMenu.getNumberOfPolygons() + " polygons. ");
							System.out.print(" Enter the sequence number of the polygon whose you want to "
									+ "add or remove point: ");
						}
						mainMenu.controlInput(sc);
						choice = sc.nextInt();
						if (choice > 0 && choice <= mainMenu.getNumberOfPolygons()) {
							int choice_1;
							do {
								mainMenu.printPoints(choice);
								if (!(mainMenu.getPolygons()[choice - 1]).getIsPolygonClosed()) {
									System.out.println("1-) Add a point\n2-) Remove a point\n3-) Cancel The Operation");
									isPolygonClosed = false;
									mainMenu.controlInput(sc);
									choice_1 = sc.nextInt();
								} else {
									System.out.println("1-) Remove a point\n2-) Cancel The Operation");
									isPolygonClosed = true;
									mainMenu.controlInput(sc);
									choice_1 = sc.nextInt();
								}
								if ((!isPolygonClosed && choice_1 == 3) || (isPolygonClosed && choice_1 == 2)) {
									isExitLoop_1 = true;
									isExitLoop = true;
									break;
								}
								if (!isPolygonClosed && (choice_1 <= 0 || choice_1 > 3)) {
									System.out.println("You entered a wrong number.");
									continue;
								}
								if (isPolygonClosed && (choice_1 <= 0 || choice_1 > 2)) {
									System.out.println("You entered a wrong number.");
									continue;
								}
								if (!isPolygonClosed && choice_1 == 1) {
									mainMenu.printPoints(choice);
									System.out.println("Enter the coordinates of point you want to add");
									System.out.print("Enter x coordinate: ");
									mainMenu.controlInput(sc);
									int x = sc.nextInt();
									System.out.print("Enter y coordinate: ");
									mainMenu.controlInput(sc);
									int y = sc.nextInt();
                                                                        if(!(isPolygonClosed)){
                                                                            System.out.println("the polygon is open");
                                                                        }else{
                                                                            System.out.println("the polygon is closed.");
                                                                        }
									Point[] pointsOfPolygon = mainMenu.getPolygons()[choice - 1]
											.returnPointsOfPolygon();
									for (int i = 0; i < pointsOfPolygon.length; i++) {
										if (new Point(x, y).getX_cord() == pointsOfPolygon[0].getX_cord()
												&& new Point(x, y).getY_cord() == pointsOfPolygon[0].getY_cord()
												&& mainMenu.getPolygons()[choice - 1].getNumberOfPoints() > 2) {
											mainMenu.getPolygons()[choice - 1].setIsPolygonClosed(true);
											mainMenu.getPolygons()[choice - 1].addPoint(x, y);
											System.out.println("Polygon is closed.");
											break;
										}
										if (new Point(x, y).getX_cord() == pointsOfPolygon[i].getX_cord()
												&& new Point(x, y).getY_cord() == pointsOfPolygon[i].getY_cord()) {
											System.out.println("You entered coordinates of an existing point.");
											break;
										}
										if (i == pointsOfPolygon.length - 1) {
											mainMenu.getPolygons()[choice_1 - 1].addPoint(x, y);
											System.out.println("The new point is added.");
										}
									}
								} else if ((!isPolygonClosed && choice_1 == 2) || (isPolygonClosed && choice_1 == 1)) {
									mainMenu.printPoints(choice);
									boolean isExitLoop_2 = false;
									do {
										System.out.print("Enter the sequence number of point you want to remove: ");
										mainMenu.controlInput(sc);
										int index = sc.nextInt();
										if (index <= 0
												|| index > mainMenu.getPolygons()[choice - 1].getNumberOfPoints()) {
											System.out.println("You entered a wrong number.");
											continue;
										} else {
											if (mainMenu.getPolygons()[choice - 1].getNumberOfPoints() == 1) {
												mainMenu.removePolygon(choice - 1);
												System.out.println("The polygon you choose is removed completely.");
												mainMenu.setNumberOfPolygons(mainMenu.getNumberOfPolygons() - 1);
												isExitLoop_2 = true;
												isExitLoop_1 = true;
												isExitLoop = true;
												break;
											} else {
												mainMenu.getPolygons()[choice - 1].removePoint(index);
												System.out.println("The point is removed.");
												isExitLoop_2 = true;
												break;
											}
										}
									} while (!isExitLoop_2);
								}
							} while (!isExitLoop_1);
						} else {
							System.out.println("You entered a wrong number.");
						}
					} while (!isExitLoop);
				} else if (mainMenu.getNumberOfPolygons() == 0) {
					System.out.println("There are no polygons at the moment.");
				}
			}

			else if (mainMenu.getChoice() == 11) {
				if (mainMenu.getNumberOfPolygons() > 0) {
					int openIndexesCount = 0;
					boolean canConcatenate = false;
					boolean isExitLoop = false;
					int[] openIndexes = new int[mainMenu.getNumberOfPolygons()];
					do {
						mainMenu.printPolygons();
						if(mainMenu.getNumberOfPolygons() == 1) {
							System.out.print("There is only one polygon.");
						}
						else {
							System.out.print("There are " + mainMenu.getNumberOfPolygons() + " polygons. ");
						}
						for (int i = 0; i < mainMenu.numberOfPolygons; i++) {
							if (!(mainMenu.getPolygons()[i].getIsPolygonClosed())) {
								openIndexes[i] = 1;
								openIndexesCount++;
							} else {
								openIndexes[i] = 0;
							}
						}
						if (openIndexesCount > 1) {
							int choice_1 = 0;
							int choice_2 = 0;
							boolean isChoiceTrue_1 = false;
							boolean isChoiceTrue_2 = false;
							if (openIndexesCount == 1) {
								System.out.print("There is only one open polygon. Polygon ");
								for (int i = 0; i < openIndexes.length; i++) {
									if (openIndexes[i] == 1) {
										System.out.print((i + 1) + " is open.");
									}
								}
							} else {
								System.out.print("There are " + openIndexesCount + " open polygons. ");
								int count = 0;
								for (int i = 0; i < openIndexes.length; i++) {
									if (openIndexes[i] == 1) {
										if (count < (openIndexesCount - 1)) {
											System.out.print("Polygon " + (i + 1) + " and ");
											count++;
										} else {
											System.out.print("Polygon " + (i + 1));
										}
									}
								}
								System.out.print(" are open.");
							}
							System.out.println();
							boolean isExitLoop_1 = false;
							do {
								System.out.print(
										"Enter the sequence number of the first polygon whose you want to concatenate: ");
								mainMenu.controlInput(sc);
								choice_1 = sc.nextInt();
								if (choice_1 <= 0 || choice_1 > openIndexes.length) {
									System.out.println("You entered a wrong number.");
									continue;
								}
								for (int i = 0; i < openIndexes.length; i++) {
									if (openIndexes[choice_1 - 1] == 1) {
										isChoiceTrue_1 = true;
									}
								}
								if (!isChoiceTrue_1) {
									System.out.println("You entered a wrong number.");
									continue;
								} else {
									do {
										System.out.print(
												"Enter the sequence number of the second polygon whose you want to "
														+ "concatenate with first one: ");
										mainMenu.controlInput(sc);
										choice_2 = sc.nextInt();
										if (choice_2 <= 0 || choice_2 > openIndexes.length) {
											System.out.println("You entered a wrong number.");
											continue;
										}
										for (int i = 0; i < openIndexes.length; i++) {
											if (openIndexes[choice_2 - 1] == 1 && choice_1 != choice_2) {
												isChoiceTrue_2 = true;
											}
										}
										if (!isChoiceTrue_2) {
											System.out.println("You entered a wrong number.");
											continue;
										} else {
											canConcatenate = mainMenu.getPolygons()[choice_1 - 1]
													.concatenatePolygon(mainMenu.getPolygons()[choice_2 - 1]);
											if (canConcatenate) {
												System.out.println("Polygon " + choice_1 + " and Polygon " + choice_2
														+ " are concatenated.");
												mainMenu.removePolygon(choice_2 - 1);
												mainMenu.setNumberOfPolygons(mainMenu.getNumberOfPolygons() - 1);
												;
												isExitLoop_1 = true;
												isExitLoop = true;
												break;
											} else {
												isExitLoop_1 = true;
												isExitLoop = true;
												break;
											}
										}
									} while (!(isChoiceTrue_2));
								}
							} while (!isExitLoop_1);
							if (!canConcatenate) {
								System.out.println("These polygons can't be concatenated.");
								break;
							}
						} else {
							System.out.println("There are no two open polygons to concatenate.");
							break;
						}

					} while (!isExitLoop);
				} else if (mainMenu.getNumberOfPolygons() == 0) {
					System.out.println("There are no polygons at the moment.");
				}
			} else if (mainMenu.getChoice() == 12) {
				System.out.println("Program is closed.");
				break;
			} else {
				System.out.println("You entered a wrong number. Please try again.");
				continue;
			}
		} while (mainMenu.getChoice() != 12);
		sc.close();
	}
}
