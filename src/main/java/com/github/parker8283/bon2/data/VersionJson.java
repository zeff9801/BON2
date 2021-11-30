package com.github.parker8283.bon2.data;

import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;

import java.util.Map;
import java.util.Set;

public class VersionJson {
    
    public static class MappingsJson {
        
        private int[] snapshot;
        private int[] stable;
        
        private transient TIntSet snapshotSet, stableSet;
        
        private TIntSet getSnapshotSet() {
            if (snapshotSet == null) {
                snapshotSet = new TIntHashSet(snapshot);
            }
            return snapshotSet;
        }
        
        private TIntSet getStableSet() {
            if (stableSet == null) {
                stableSet = new TIntHashSet(stable);
            }
            return stableSet;
        }
        
        public boolean hasSnapshot(String version) {
            return hasSnapshot(Integer.parseInt(version));
        }
        
        public boolean hasSnapshot(int version) {
            return getSnapshotSet().contains(version); 
        }
        
        public boolean hasStable(String version) {
            return hasStable(Integer.parseInt(version));
        }
        
        public boolean hasStable(int version) {
            return getStableSet().contains(version);
        }
        
        public int[] getSnapshots() {
            return snapshot;
        }
        
        public int[] getStables() {
            return stable;
        }
    }
    
    private Map<String, MappingsJson> versionToList;

    public VersionJson(Map<String, MappingsJson> data) {
        this.versionToList = data;
    }

    public MappingsJson getMappings(String mcversion) {
        return versionToList.get(mcversion);
    }
    
    public Set<String> getVersions() {
        return versionToList.keySet();
    }
}
