package de.widemeadows.projectcore.math.tests;

import de.widemeadows.projectcore.math.Box;
import de.widemeadows.projectcore.math.Vector3;
import org.junit.Test;

import static de.widemeadows.projectcore.math.MathUtils.DEFAULT_EPSILON;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Tests für die Bounding Box
 */
public class BoxTests {

	/**
	 * Testet die Flächenberechnung der Box
	 */
	@Test
	public void areaTest() {

		// Einheitsbox testen
		Box box = Box.createNew();
		assertEquals(1.0f, box.calculateArea(), DEFAULT_EPSILON);

		// Box der Größe 2x2x2
		box = Box.createNew(
				0, 0, 0,
				1, 1, 1
		);
		assertEquals(8.0f, box.calculateArea(), DEFAULT_EPSILON);

		// Box der Größe 2x2x2
		box = Box.createNew(
				0, 0, 0,
				-1, -1, -1
		);
		assertEquals(8.0f, box.calculateArea(), DEFAULT_EPSILON);

		// Box der Größe 2x2x2
		box = Box.createNew(
				0, 1000, 0,
				1, -1, 1
		);
		assertEquals(8.0f, box.calculateArea(), DEFAULT_EPSILON);

	}

	/**
	 * Stellt sicher, dass der Extent-Vektor immer positiv ist
	 */
	@Test
	public void extentAlwaysPositiveTest() {
		// Alle Komponenten positiv
		Box box = Box.createNew(
				0, 0, 0,
				1, 1, 1
		);
		assertEquals(1.0f, box.extent.x, DEFAULT_EPSILON);
		assertEquals(1.0f, box.extent.y, DEFAULT_EPSILON);
		assertEquals(1.0f, box.extent.z, DEFAULT_EPSILON);

		// Alle Komponenten negativ
		box = Box.createNew(
				0, 0, 0,
				-1, -1, -1
		);
		assertEquals(1.0f, box.extent.x, DEFAULT_EPSILON);
		assertEquals(1.0f, box.extent.y, DEFAULT_EPSILON);
		assertEquals(1.0f, box.extent.z, DEFAULT_EPSILON);

		// Setzen der Komponenten
		box.setExtent(-1, 1, 1);
		assertEquals(1.0f, box.extent.x, DEFAULT_EPSILON);
		assertEquals(1.0f, box.extent.y, DEFAULT_EPSILON);
		assertEquals(1.0f, box.extent.z, DEFAULT_EPSILON);

		// Setzen der Komponenten
		box.setExtent(Vector3.createNew(1, -1, -1));
		assertEquals(1.0f, box.extent.x, DEFAULT_EPSILON);
		assertEquals(1.0f, box.extent.y, DEFAULT_EPSILON);
		assertEquals(1.0f, box.extent.z, DEFAULT_EPSILON);
	}

	/**
	 * Testet das Beziehen der Eckpunkte der Box
	 */
	@Test
	public void boxCornerSelection() {

		// Alle Komponenten positiv
		Box box = Box.createNew(
				0, 0, 0,
				1, 2, 3
		);

		// vorne unten links
		Vector3 point = box.getCornerPoint(Box.Point.FrontBottomLeft);
		assertEquals(-1, point.x, DEFAULT_EPSILON);
		assertEquals(-2, point.y, DEFAULT_EPSILON);
		assertEquals(+3, point.z, DEFAULT_EPSILON);

		// vorne unten rechts
		point = box.getCornerPoint(Box.Point.FrontBottomRight);
		assertEquals(+1, point.x, DEFAULT_EPSILON);
		assertEquals(-2, point.y, DEFAULT_EPSILON);
		assertEquals(+3, point.z, DEFAULT_EPSILON);

		// vorne oben links
		point = box.getCornerPoint(Box.Point.FrontTopLeft);
		assertEquals(-1, point.x, DEFAULT_EPSILON);
		assertEquals(+2, point.y, DEFAULT_EPSILON);
		assertEquals(+3, point.z, DEFAULT_EPSILON);

		// vorne oben rechts
		point = box.getCornerPoint(Box.Point.FrontTopRight);
		assertEquals(+1, point.x, DEFAULT_EPSILON);
		assertEquals(+2, point.y, DEFAULT_EPSILON);
		assertEquals(+3, point.z, DEFAULT_EPSILON);

		// hinten unten links
		point = box.getCornerPoint(Box.Point.BackBottomLeft);
		assertEquals(-1, point.x, DEFAULT_EPSILON);
		assertEquals(-2, point.y, DEFAULT_EPSILON);
		assertEquals(-3, point.z, DEFAULT_EPSILON);

		// hinten unten rechts
		point = box.getCornerPoint(Box.Point.BackBottomRight);
		assertEquals(+1, point.x, DEFAULT_EPSILON);
		assertEquals(-2, point.y, DEFAULT_EPSILON);
		assertEquals(-3, point.z, DEFAULT_EPSILON);

		// hinten oben links
		point = box.getCornerPoint(Box.Point.BackTopLeft);
		assertEquals(-1, point.x, DEFAULT_EPSILON);
		assertEquals(+2, point.y, DEFAULT_EPSILON);
		assertEquals(-3, point.z, DEFAULT_EPSILON);

		// hinten oben rechts
		point = box.getCornerPoint(Box.Point.BackTopRight);
		assertEquals(+1, point.x, DEFAULT_EPSILON);
		assertEquals(+2, point.y, DEFAULT_EPSILON);
		assertEquals(-3, point.z, DEFAULT_EPSILON);
	}

	/**
	 * Überprüft Punkte
	 */
	@Test
	public void intersectionWithPoint() {

		// Alle Komponenten positiv
		Box box = Box.createNew(
				0, 0, 0,
				1, 2, 3
		);

		// Punkt in der Box
		Vector3 point = Vector3.createNew(0, 0, 0);
		assertTrue(box.intersectsAABB(point));
		assertTrue(box.intersectsAABB(point.x, point.y, point.z));

		// Punkt in der Box
		point = Vector3.createNew(0.5f, -0.5f, 0.23f);
		assertTrue(box.intersectsAABB(point));
		assertTrue(box.intersectsAABB(point.x, point.y, point.z));

		// Punkt auf der Box
		point = Vector3.createNew(1, 2, 3);
		assertTrue(box.intersectsAABB(point));
		assertTrue(box.intersectsAABB(point.x, point.y, point.z));

		// Punkt auf der Box
		point = Vector3.createNew(-1, -2, -3);
		assertTrue(box.intersectsAABB(point));
		assertTrue(box.intersectsAABB(point.x, point.y, point.z));

		// Punkt außerhalb der Box
		point = Vector3.createNew(1, 2, 3.01f);
		assertFalse(box.intersectsAABB(point));
		assertFalse(box.intersectsAABB(point.x, point.y, point.z));

		// Punkt außerhalb der Box
		point = Vector3.createNew(1, 2.01f, 3);
		assertFalse(box.intersectsAABB(point));
		assertFalse(box.intersectsAABB(point.x, point.y, point.z));

		// Punkt außerhalb der Box
		point = Vector3.createNew(1.01f, 2, 3);
		assertFalse(box.intersectsAABB(point));
		assertFalse(box.intersectsAABB(point.x, point.y, point.z));

		// Punkt außerhalb der Box
		point = Vector3.createNew(1.01f, 10000, -3000);
		assertFalse(box.intersectsAABB(point));
		assertFalse(box.intersectsAABB(point.x, point.y, point.z));
	}
}
