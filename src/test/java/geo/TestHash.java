package geo;

import de.alpharogroup.jgeohash.GeoHashExtensions;
import de.alpharogroup.jgeohash.GeoHashPoint;
import de.alpharogroup.jgeohash.api.Position;
import de.alpharogroup.jgeohash.distance.MeasuringUnit;
import de.alpharogroup.jgeohash.geoip.LocationExtensions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xym on 2016/7/17.
 */
public class TestHash {
    @Test
    public void testGeo() {
        List<Position> geohashes = new ArrayList<>();
        geohashes.add(new GeoHashPoint("u0vf2w1s5tsy"));
        geohashes.add(new GeoHashPoint("u0t3z7cnznrx"));
        geohashes.add(new GeoHashPoint("u0td0v9xnz28"));
        Position startPoint = new GeoHashPoint("u11fyhzqhp3c");

        LocationExtensions.sortByDistance(startPoint, geohashes, MeasuringUnit.METER);

        geohashes = new ArrayList<>();
        geohashes.add(new GeoHashPoint("u0z4")); // below Wuerzburg
        geohashes.add(new GeoHashPoint("u1p1")); // right from Bad Hersfeld
        geohashes.add(new GeoHashPoint("u28j")); // Ingolstadt
        geohashes.add(new GeoHashPoint("u0zc4wp0k")); // Nurremberg at work
        startPoint = new GeoHashPoint("u0ww"); // Ludwigsburg

        LocationExtensions.sortByDistance(startPoint, geohashes, MeasuringUnit.METER);

    }

    @Test
    public void testGeoHash() {
        String geohash = GeoHashExtensions.encode(53.5526394, 10.0067103);//u1x0esywtr81
        String adjacent = GeoHashExtensions.getAdjacent("u1x0esywtr81", "top"); //u1x0esywtr84
        System.out.println(adjacent);
        double[] decoded = GeoHashExtensions.decode(geohash);
        System.out.println("geohash:" + geohash);

        System.out.println(decoded[0] + ", " + decoded[1]);

    }

    @Test
    public void testGeoHash2() {
        String geohash = GeoHashExtensions.encode(53.55, 10.00);
        double[] decoded = GeoHashExtensions.decode(geohash);
        System.out.println("geohash:" + geohash);
        System.out.println(decoded[0] + ", " + decoded[1]);
        String adjacent = GeoHashExtensions.getAdjacent("u1x0esywtr81", "top");
        decoded = GeoHashExtensions.decode(adjacent);
        System.out.println("geohash:" + adjacent);
        System.out.println(decoded[0] + ", " + decoded[1]);


    }


    @Test
    public void testGeoHash3() {
        String geohash = GeoHashExtensions.encode(53.55, 10.00);
        geohash = geohash.substring(0, 7);
        Map adjacentAreas = GeoHashExtensions.getAllAdjacentAreasMap(geohash);
        System.out.println(adjacentAreas);


    }
}
