/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructureproje1;

import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author meltem koc
 */
public class Polygon {
    private MyLinkedList points = new MyLinkedList();
	private int numberOfPoints = 0;
	private boolean isPolygonClosed = false;

	public Polygon() {
	}

	// Poligon olusturulan Queue ile yaratiliyor.
	public Polygon(Queue<Point> queue, boolean isClosed) {
		this.isPolygonClosed = isClosed;
		this.numberOfPoints = queue.size();
		while (!(queue.isEmpty())) {
			points.insertLast(queue.poll());
		}
	}

	// Poligon olusturulan Stack ile yaratiliyor.
	public Polygon(Stack<Point> stack, boolean isClosed) {
		this.isPolygonClosed = isClosed;
		this.numberOfPoints = stack.size();
		while (!(stack.isEmpty())) {
			points.insertFirst(stack.pop());
		}
	}

	// Verilen x, y koordinatlarindan nokta yaratiliyor ve LinkedList'e atilip,
	// nokta sayisi artiliyor.
	public void addPoint(int x, int y) {
		Point point = new Point(x, y);
		points.insertLast(point);
		this.numberOfPoints++;
	}

	// Verilen siradaki nokta LinkedListten siliniyor.
	public void removePoint(int index) {
		Stack<Point> elements = new Stack<Point>();
		int elementsSize;
		// acikk poligonlar icin
		if (!isPolygonClosed) {
			if (index != 1) {
				for (int i = points.size; i >= index; i--) {
					elements.push(points.getNode(i).data);
					points.removeLast();
				}
				elementsSize = elements.size();
				if (elements.size() > 1) {
					elements.pop();
					for (int i = 0; i < (elementsSize - 1); i++) {
						points.insertLast(elements.pop());
					}
				}
				numberOfPoints--;
				// ilk noktanin silinmesi
			} else {
				points.removeFirst();
				numberOfPoints--;
			} // Kapali poligonlar i�in
		} else {
			if (index == numberOfPoints) {
				points.removeLast();
				isPolygonClosed = false;
				numberOfPoints--;
			} else {
				// ��genden nokta silme
				if (numberOfPoints == 4) {
					if (index != 1) {
						for (int i = points.size; i >= index; i--) {
							elements.push(points.getNode(i).data);
							points.removeLast();
						}
						elementsSize = elements.size();
						if (elements.size() > 1) {
							elements.pop();
							for (int i = 0; i < (elementsSize - 1); i++) {
								points.insertLast(elements.pop());
							}
						}
						numberOfPoints--;
						isPolygonClosed = false;
					} else {
						points.removeFirst();
						numberOfPoints--;
						isPolygonClosed = false;
					}
				} else {
					if (index != 1) {
						for (int i = points.size; i >= index; i--) {
							elements.push(points.getNode(i).data);
							points.removeLast();
						}
						elementsSize = elements.size();
						if (elements.size() > 1) {
							elements.pop();
							for (int i = 0; i < (elementsSize - 1); i++) {
								points.insertLast(elements.pop());
							}
						}
						numberOfPoints--;
					} else {
						points.removeFirst();
						isPolygonClosed = false;
						numberOfPoints--;
					}
				}
			}
		}
	}

	// Verilen poligonla icinde fonksiyonu kullanan poligonun birlesip girdi olarak
	// verilen poligonun silinmesi
	public boolean concatenatePolygon(Polygon polygon) {
		int samePoints = 0;
		for (int i = 0; i < numberOfPoints; i++) {
			for (int j = 0; j < polygon.getNumberOfPoints(); j++) {
				if (getPoints().getNode(i + 1).data.getX_cord() == polygon.getPoints().getNode(j + 1).data.getX_cord()
						&& getPoints().getNode(i + 1).data.getY_cord() == polygon.getPoints().getNode(j + 1).data
								.getY_cord()) {
					samePoints++;
				}
			}
		}
		if (samePoints > 2) {
			return false;
		}
		if (samePoints == 2) {
			// ilk poligonun son noktasiyla ikinci poligonun ilk noktasi veya ilk poligonun
			// ilk noktasiyla ikinci poligonun son noktasi ayni degilse
			if (points.getNode(numberOfPoints).data.getX_cord() != polygon.getPoints().getNode(1).data.getX_cord()
					&& points.getNode(numberOfPoints).data.getY_cord() != polygon.getPoints().getNode(1).data
							.getY_cord()
					|| points.getNode(1).data
							.getX_cord() != polygon.getPoints().getNode(polygon.getNumberOfPoints()).data.getX_cord()
							&& points.getNode(1).data
									.getY_cord() != polygon.getPoints().getNode(polygon.getNumberOfPoints()).data
											.getY_cord()) {
				return false;
			} else {
				polygon.getPoints().removeFirst();
				while (!(polygon.getPoints().isEmpty())) {
					points.insertLast(polygon.getPoints().getNode(1).data);
					polygon.getPoints().removeFirst();
					this.numberOfPoints++;
				}
				this.isPolygonClosed = true;
				return true;
			}
		}
		if (samePoints == 1) {
			// 1. poligonun son noktasiyla 2. poligonun ilk noktasi ayni ise
			if (points.getNode(numberOfPoints).data.getX_cord() == polygon.getPoints().getNode(1).data.getX_cord()
					&& points.getNode(numberOfPoints).data.getY_cord() == polygon.getPoints().getNode(1).data
							.getY_cord()) {
				polygon.getPoints().removeFirst();
				while (!(polygon.getPoints().isEmpty())) {
					points.insertLast(polygon.getPoints().getNode(1).data);
					this.numberOfPoints++;
					polygon.getPoints().removeFirst();
				}
				return true;
			}
			// 1. poligonun ilk noktasiyla 2. poligonun son noktasi ayniysa
			else if (points.getNode(1).data.getX_cord() == polygon.getPoints().getNode(polygon.getNumberOfPoints()).data
					.getX_cord()
					&& points.getNode(1).data
							.getY_cord() == polygon.getPoints().getNode(polygon.getNumberOfPoints()).data.getY_cord()) {
				while (!(polygon.getPoints().isEmpty())) {
					points.insertLast(polygon.getPoints().getNode(1).data);
					this.numberOfPoints++;
					polygon.getPoints().removeFirst();
				}
				this.isPolygonClosed = true;
				return true;
			} else {
				return false;
			}
			// Ortak nokta yoksa
		} else if (samePoints == 0) {
			while (!(polygon.getPoints().isEmpty())) {
				points.insertLast(polygon.getPoints().getNode(1).data);
				this.numberOfPoints++;
				polygon.getPoints().removeFirst();
			}
			return true;
		}
		return true;
	}

	// Verilen iki nokta aras�ndaki mesafeyi buluyor.
	public double findDistance(Point p1, Point p2) {
		return Math.sqrt(Math.pow(p1.getX_cord() - p2.getX_cord(), 2) + Math.pow(p1.getY_cord() - p2.getY_cord(), 2));
	}

	// Poligonun cevresini buluyor.
	public double findLength() {
		double length = 0;
		if (numberOfPoints == 1) {
			return 0;
		}
		int i = 1;
		while (points.getNode(i + 1) != null) {
			length += findDistance(points.getNode(i).data, points.getNode(i + 1).data);
			i++;
		}
		return length;
	}

	// 2 nokta arasindaki egimi buluyor.
	public double findGradient(Point p1, Point p2) {
		return ((p2.getY_cord() - p1.getY_cord()) / (p2.getX_cord() - p1.getX_cord()));
	}

	// Acik olan bir poligonu kapali bir poligon haline getiriyor.
	public void closePolygon() {
		Point firstPoint = points.getNode(1).data;
		addPoint(firstPoint.getX_cord(), firstPoint.getY_cord());
		setIsPolygonClosed(true);
	}

	// Poligonun noktalarini ters ceviriyor.
	public void reverseOrderOfPoints() {
		Point[] pointsTemp = returnPointsOfPolygon();
		for (int i = 0; i < numberOfPoints; i++) {
			points.removeFirst();
		}
		for (int i = numberOfPoints - 1; i >= 0; i--) {
			points.insertLast(pointsTemp[i]);
		}
	}

	// Kapal� poligonun alanini buluyor.
	public double findArea() {
		double area = 0;
		for (int i = 1; i < numberOfPoints; i++) {
			area += ((points.getNode(i).data.getX_cord()) * (points.getNode(i + 1).data.getY_cord()))
					- ((points.getNode(i).data.getY_cord()) * (points.getNode(i + 1).data.getX_cord()));
		}
		return Math.abs(area / 2);
	}

	// Poligonun noktalarini dizi olarak donduruyor.
	public Point[] returnPointsOfPolygon() {
		Point[] pointsArray = new Point[numberOfPoints];
		for (int i = 0; i < numberOfPoints; i++) {
			pointsArray[i] = this.points.getNode(i + 1).data;
		}
		return pointsArray;
	}

	public MyLinkedList getPoints() {
		return points;
	}

	public void setPoints(MyLinkedList points) {
		this.points = points;
	}

	public int getNumberOfPoints() {
		return numberOfPoints;
	}

	public void setNumberOfPoints(int numberOfPoints) {
		this.numberOfPoints = numberOfPoints;
	}

	public boolean getIsPolygonClosed() {
		return isPolygonClosed;
	}

	public void setIsPolygonClosed(boolean isPolygonClosed) {
		this.isPolygonClosed = isPolygonClosed;
	}

	public String toString() {
		if (numberOfPoints == 1) {
			return "It is a dot.";
		} else if (numberOfPoints == 2) {
			return "It is a line.";
		} else if (numberOfPoints > 2) {
			int i = 1;
			if (points.getNode(1).data.getX_cord() == 0 && points.getNode(numberOfPoints).data.getX_cord() == 0) {
				if (!isPolygonClosed) {
					if ((int) findLength() == points.getNode(numberOfPoints).data.getY_cord()) {
						return "It is a line.";
					}
				}
			} else if (points.getNode(1).data.getY_cord() == 0
					&& points.getNode(numberOfPoints).data.getY_cord() == 0) {
				if (!isPolygonClosed) {
					if ((int) findLength() == points.getNode(numberOfPoints).data.getX_cord()) {
						return "It is a line";
					}
				}
			}
			int undefinedGradientCount = 0;
			int definedGradientCount = 0;
			for (i = 1; i < numberOfPoints - 1; i++) {
				if ((points.getNode(i + 1).data.getX_cord() - points.getNode(i).data.getX_cord() != 0)
						&& points.getNode(i + 2).data.getX_cord() - points.getNode(i + 1).data.getX_cord() != 0) {
					if (findGradient(points.getNode(i).data, points.getNode(i + 1).data) != findGradient(
							points.getNode(i + 1).data, points.getNode(i + 2).data)) {
						break;
					} else {
						definedGradientCount++;
					}
				} else {
					undefinedGradientCount++;
				}
			}
			if (undefinedGradientCount == this.numberOfPoints - 2) {
				System.out.println(undefinedGradientCount + " " + this.numberOfPoints);
				return "It is a line.";
			} else if (definedGradientCount == this.numberOfPoints - 2) {
				return "It is a line.";
			} else if (!isPolygonClosed) {
				return "It is an open polygon with " + (numberOfPoints - 1) + " edges.";
			} else {
				if (numberOfPoints == 4) {
					return "It is a triangle.";
				} else if (numberOfPoints == 5) {
					if (findGradient(points.getNode(1).data, points
							.getNode(3).data) == -(findGradient(points.getNode(2).data, points.getNode(4).data))) {
						return "It is a square;";
					} else {
						return "It is a quadrangle.";
					}
				} else {
					return "It is a closed polygon with " + (numberOfPoints - 1) + " edges.";
				}
			}
		}
		return "";
	}
}
