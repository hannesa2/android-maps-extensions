package com.androidmapsextensions;

public class ClusteringSettings {

    public static final double DEFAULT_CLUSTER_SIZE = 180.0;
    private static final int DEFAULT_MIN_MARKERS_COUNT = 2;

    private boolean addMarkersDynamically = false;
    private ClusterOptionsProvider clusterOptionsProvider = null;
    private double clusterSize = DEFAULT_CLUSTER_SIZE;
    private boolean enabled = true;
    private int minMarkersCount = DEFAULT_MIN_MARKERS_COUNT;

    public ClusteringSettings addMarkersDynamically(boolean addMarkersDynamically) {
        this.addMarkersDynamically = addMarkersDynamically;
        return this;
    }

    public ClusteringSettings clusterOptionsProvider(ClusterOptionsProvider clusterOptionsProvider) {
        this.clusterOptionsProvider = clusterOptionsProvider;
        return this;
    }

    /**
     * Consider using value of 180, 160, 144, 120 or 96 for 8x8, 9x9, 10x10, 12x12 and 15x15 grids respectively on zoom level 2.
     *
     * @param clusterSize cluster size in degrees of longitude on zoom level 0.
     */
    public ClusteringSettings clusterSize(double clusterSize) {
        this.clusterSize = clusterSize;
        return this;
    }

    public ClusteringSettings enabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public ClusterOptionsProvider getClusterOptionsProvider() {
        return clusterOptionsProvider;
    }

    public double getClusterSize() {
        return clusterSize;
    }

    public int getMinMarkersCount() {
        return minMarkersCount;
    }

    public boolean isAddMarkersDynamically() {
        return addMarkersDynamically;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public ClusteringSettings minMarkersCount(int minMarkersCount) {
        this.minMarkersCount = minMarkersCount;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClusteringSettings)) {
            return false;
        }
        ClusteringSettings other = (ClusteringSettings) o;
        if (enabled != other.enabled) {
            return false;
        }
        if (addMarkersDynamically != other.addMarkersDynamically) {
            return false;
        }
        if (!enabled) {
            return true;
        }
        if (clusterSize != other.clusterSize) {
            return false;
        }
        if (minMarkersCount != other.minMarkersCount) {
            return false;
        }
        return equals(clusterOptionsProvider, other.clusterOptionsProvider);
    }

    private static boolean equals(Object objLeft, Object objRight) {
        if (objLeft == null) {
            return objRight == null;
        } else {
            return objLeft.equals(objRight);
        }
    }

    @Override
    public int hashCode() {
        // TODO: implement, low priority
        return super.hashCode();
    }
}
