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
public class MyLinkedList {
        Node first;
	Node last;
	int size = 0;

	public MyLinkedList() {
		first = null;
		last = null;
		size = 0;
	}

	public void insertFirst(Point value) {
		Node newNode = new Node(value);
		if (first == null) {
			first = newNode;
			last = newNode;
		} else {
			newNode.next = first;
			first = newNode;
		}
		size++;
	}

	public void insertLast(Point value) {
		Node newNode = new Node(value);
		if (first == null) {
			first = newNode;
			last = newNode;
		} else {
			last.next = newNode;
			last = newNode;
		}
		size++;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public String toString() {
		if (isEmpty()) {
			return "List is empty";
		}
		Node tmp = first;
		String str = "List with " + size + "elements   : ";

		while (tmp != null) {
			str += tmp.data + "->";
			tmp = tmp.next;
		}
		str += "\n First :" + first + "\t Last :" + last;
		return str;

	}
	
	//LinkedList'de bulunan elemanlardan verilen siradaki elemani dondoruyor.
	public Node getNode(int number) {
		Node tmp = first;
		if (number == 1) {
			return tmp;
		}
		for (int i = 1; i < number; i++) {
			tmp = tmp.next;
		}
		return tmp;
	}

	public Node removeFirst() {
		if (isEmpty()) {
			return null;
		}
		Node tmp = first;
		first = first.next;
		size--;
		return tmp;
	}

	public Node removeLast() {
		if (isEmpty()) {
			return null;
		}
		Node secondLast = first;
		while (secondLast.next.next != null) {
			secondLast = secondLast.next;
		}
		Node lastNode = secondLast.next;
		secondLast.next = null;
		last = secondLast;
		size--;
		return last;
	}
}
