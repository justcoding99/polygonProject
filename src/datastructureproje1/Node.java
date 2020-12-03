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
public class Node {
   Point data;
	Node next;

	public Node() {
		next = null;
	}

	public Node(Point data) {
		this.data = data;
		next = null;

	}

	@Override
	public String toString() {
		return "" + this.data;     
        }
}
