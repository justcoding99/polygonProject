/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructureproje1;

/**
 *
 * @author meltem koc
 */
public class Point {
        private int x_cord;
	private int y_cord;

	public Point(int x, int y) {
		this.x_cord = x;
		this.y_cord = y;
	}

	public int getX_cord() {
		return x_cord;
	}

	public void setX_cord(int x_cord) {
		this.x_cord = x_cord;
	}

	public int getY_cord() {
		return y_cord;
	}

	public void setY_cord(int y_cord) {
		this.y_cord = y_cord;
	}

	public String toString() {
		return "X: " + this.x_cord + "\nY: " + this.y_cord;
	}
}
