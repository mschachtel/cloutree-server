
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.persistence.entity.util;

import java.util.Comparator;

import com.cloutree.server.persistence.entity.Modelrevision;

/**
 * DescendingRevisionComparator
 *
 * @author marc
 *
 * Since 27.08.2013
 */
public class DescendingRevisionComparator implements Comparator<Modelrevision> {

	@Override
	public int compare(Modelrevision o1, Modelrevision o2) {
	    return (o1.compareTo(o2));
	}
	
}
