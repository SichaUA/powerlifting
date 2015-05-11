package com.powerlifting.reports;

import javafx.util.Pair;

import java.util.*;

public class ReportRegionPointsSum {
    private List<RegionWithPoints> regions = new ArrayList<>();

    public List<RegionWithPoints> getRegions() {
        return regions;
    }

    public void addPoint(String region, Integer point) {
        for(Iterator<RegionWithPoints> i = regions.iterator(); i.hasNext(); ) {
            RegionWithPoints currRegionWithPoints = i.next();

            if(currRegionWithPoints.getRegion().equals(region)) {
                currRegionWithPoints.addPoint(point);
                Collections.sort(regions, new RegionWithPointsComparator());
                return;
            }
        }

        RegionWithPoints regionWithPoints = new RegionWithPoints();
        regionWithPoints.setRegion(region);
        regionWithPoints.addPoint(point);
        regions.add(regionWithPoints);
        Collections.sort(regions, new RegionWithPointsComparator());
    }
}

class RegionWithPoints {
    private String region;
    private List<Integer> points = new ArrayList<>();

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<Integer> getPoints() {
        return points;
    }

    public void addPoint(Integer point) {
        points.add(point);
        Collections.sort(points, Collections.reverseOrder());
    }
}

class RegionWithPointsComparator implements Comparator<RegionWithPoints> {

    @Override
    public int compare(RegionWithPoints o1, RegionWithPoints o2) {
        if(o1.getPoints().stream().mapToInt(Integer::intValue).sum() > o2.getPoints().stream().mapToInt(Integer::intValue).sum())
            return -1;
        else if(o1.getPoints().stream().mapToInt(Integer::intValue).sum() < o2.getPoints().stream().mapToInt(Integer::intValue).sum())
            return 1;

        return 0;
    }
}
