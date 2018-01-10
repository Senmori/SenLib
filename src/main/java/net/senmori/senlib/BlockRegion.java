package net.senmori.senlib;

import com.google.common.collect.AbstractIterator;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Iterator;

public class BlockRegion {

    /**
     * Create an Iterable that returns all locations in the box specified by the given corners
     */
    public static Iterable<Location> getAllInBox(Location from, Location to) {
        return getAllInBox(from.getWorld(),
                           Math.min(from.getBlockX(), to.getBlockX()), Math.min(from.getBlockY(), to.getBlockY()), Math.min(from.getBlockZ(), to.getBlockZ()),
                           Math.max(from.getBlockX(), to.getBlockX()), Math.max(from.getBlockY(), to.getBlockY()), Math.max(from.getBlockZ(), to.getBlockZ()));
    }

    public static Iterable<Location> getAllInBox(final World world, final int originX, final int originY, final int originZ, final int deltaX, final int deltaY, final int deltaZ) {
        return new Iterable<Location>() {
            public Iterator<Location> iterator() {
                return new AbstractIterator<Location>() {
                    private boolean firstRun = true;
                    private int currX;
                    private int currY;
                    private int currZ;
                    protected Location computeNext() {
                        if (this.firstRun) {
                            this.firstRun = false;
                            this.currX = originX;
                            this.currY = originY;
                            this.currZ = originZ;
                            return new Location(world, originX, originY, originZ);
                        }
                        else if (this.currX == deltaX && this.currY == deltaY && this.currZ == deltaZ) {
                            return (Location)this.endOfData();
                        }
                        else {
                            if (this.currX < deltaX) {
                                ++this.currX;
                            }
                            else if (this.currY < deltaY) {
                                this.currX = originX;
                                ++this.currY;
                            }
                            else if (this.currZ < deltaZ) {
                                this.currX = originX;
                                this.currY = originY;
                                ++this.currZ;
                            }

                            return new Location(world, this.currX, this.currY, this.currZ);
                        }
                    }
                };
            }
        };
    }
}
