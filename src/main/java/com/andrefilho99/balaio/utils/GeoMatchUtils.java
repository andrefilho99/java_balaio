package com.andrefilho99.balaio.utils;

import org.apache.lucene.util.SloppyMath;
import org.springframework.stereotype.Service;

@Service
public class GeoMatchUtils {

	public double distanceFromAToB(Double latitudeA, double longitudeA, Double latitudeB, double longitudeB) {
		return SloppyMath.haversinMeters(latitudeA, longitudeA, latitudeB, latitudeB);
	}
}
