package com.andrefilho99.balaio.utils;

import org.apache.lucene.util.SloppyMath;
import org.springframework.stereotype.Service;

@Service
public class GeoMatchUtils {

	public double distanceFromAToB(Double latitudeA, double longitudeA, Double latitudeB, double longitudeB) {
		return SloppyMath.haversinMeters(latitudeA, longitudeA, latitudeB, latitudeB);
	}
	
	 public double distFrom(Double lat1, Double lng1, Double lat2, Double lng2) {
	    
		double earthRadius = 6371000; //meters
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    float dist = (float) (earthRadius * c);

	    return dist;
	}
}
