package net.senmori.senlib;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * This enum class was originally found in Forge, who debofuscated it from NMS.
 * All credit goes to them for this class. I merely copied it from them and made it work for Spigot
 */
public enum Direction {
    DOWN(0, 1, - 1, "down", Direction.AxisDirection.NEGATIVE, Direction.Axis.Y, BlockFace.DOWN, new Vector(0, - 1, 0)),
    UP(1, 0, - 1, "up", Direction.AxisDirection.POSITIVE, Direction.Axis.Y, BlockFace.UP, new Vector(0, 1, 0)),
    NORTH(2, 3, 2, "north", Direction.AxisDirection.NEGATIVE, Direction.Axis.Z, BlockFace.NORTH, new Vector(0, 0, - 1)),
    SOUTH(3, 2, 0, "south", Direction.AxisDirection.POSITIVE, Direction.Axis.Z, BlockFace.SOUTH, new Vector(0, 0, 1)),
    WEST(4, 5, 1, "west", Direction.AxisDirection.NEGATIVE, Direction.Axis.X, BlockFace.WEST, new Vector(- 1, 0, 0)),
    EAST(5, 4, 3, "east", Direction.AxisDirection.POSITIVE, Direction.Axis.X, BlockFace.EAST, new Vector(1, 0, 0));

    /** Ordering index for D-U-N-S-W-E */
    private final int index;
    /** Index of the opposite Facing in the VALUES array */
    private final int opposite;
    /** Ordering index for the HORIZONTALS field (S-W-N-E) */
    private final int horizontalIndex;
    private final String name;
    private final Direction.Axis axis;
    private final Direction.AxisDirection axisDirection;
    /** Normalized Vector that points in the direction of this Facing */
    private final Vector directionVec;
    private final BlockFace bukkitFace;
    /** All facings in D-U-N-S-W-E order */
    public static final Direction[] VALUES = new Direction[6];
    /** All Facings with horizontal axis in order S-W-N-E */
    public static final Direction[] HORIZONTALS = new Direction[4];
    private static final Map<String, Direction> NAME_LOOKUP = Maps.<String, Direction>newHashMap();

    private Direction(int indexIn, int oppositeIn, int horizontalIndexIn, String nameIn, Direction.AxisDirection axisDirectionIn, Direction.Axis axisIn, BlockFace bukkitFace, Vector directionVecIn) {
        this.index = indexIn;
        this.horizontalIndex = horizontalIndexIn;
        this.opposite = oppositeIn;
        this.name = nameIn;
        this.axis = axisIn;
        this.axisDirection = axisDirectionIn;
        this.directionVec = directionVecIn;
        this.bukkitFace = bukkitFace;
    }

    /**
     * Get the Index of this Facing (0-5). The order is D-U-N-S-W-E
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Get the matching {@link BlockFace} for Bukkit.
     * @return
     */
    public BlockFace getBlockFace() {
        return bukkitFace;
    }

    /**
     * Get the index of this horizontal facing (0-3). The order is S-W-N-E
     */
    public int getHorizontalIndex() {
        return this.horizontalIndex;
    }

    /**
     * Get the AxisDirection of this Facing.
     */
    public Direction.AxisDirection getAxisDirection() {
        return this.axisDirection;
    }

    /**
     * Get the opposite Facing (e.g. DOWN => UP)
     */
    public Direction getOpposite() {
        return getFront(this.opposite);
    }

    /**
     * Rotate this Facing around the given axis clockwise. If this facing cannot be rotated around the given axis,
     * returns this facing without rotating.
     */
    public Direction rotateAround(Direction.Axis axis) {
        switch (axis) {
            case X:

                if (this != WEST && this != EAST) {
                    return this.rotateX();
                }

                return this;
            case Y:

                if (this != UP && this != DOWN) {
                    return this.rotateY();
                }

                return this;
            case Z:

                if (this != NORTH && this != SOUTH) {
                    return this.rotateZ();
                }

                return this;
            default:
                throw new IllegalStateException("Unable to get CW facing for axis " + axis);
        }
    }

    /**
     * Rotate this Facing around the Y axis clockwise (NORTH => EAST => SOUTH => WEST => NORTH)
     */
    public Direction rotateY() {
        switch (this) {
            case NORTH:
                return EAST;
            case EAST:
                return SOUTH;
            case SOUTH:
                return WEST;
            case WEST:
                return NORTH;
            default:
                throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
        }
    }

    /**
     * Rotate this Facing around the X axis (NORTH => DOWN => SOUTH => UP => NORTH)
     */
    private Direction rotateX() {
        switch (this) {
            case NORTH:
                return DOWN;
            case EAST:
            case WEST:
            default:
                throw new IllegalStateException("Unable to get X-rotated facing of " + this);
            case SOUTH:
                return UP;
            case UP:
                return NORTH;
            case DOWN:
                return SOUTH;
        }
    }

    /**
     * Rotate this Facing around the Z axis (EAST => DOWN => WEST => UP => EAST)
     */
    private Direction rotateZ() {
        switch (this) {
            case EAST:
                return DOWN;
            case SOUTH:
            default:
                throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
            case WEST:
                return UP;
            case UP:
                return EAST;
            case DOWN:
                return WEST;
        }
    }

    /**
     * Rotate this Facing around the Y axis counter-clockwise (NORTH => WEST => SOUTH => EAST => NORTH)
     */
    public Direction rotateYCCW() {
        switch (this) {
            case NORTH:
                return WEST;
            case EAST:
                return NORTH;
            case SOUTH:
                return EAST;
            case WEST:
                return SOUTH;
            default:
                throw new IllegalStateException("Unable to get CCW facing of " + this);
        }
    }

    /**
     * Returns a offset that addresses the block in front of this facing.
     */
    public int getFrontOffsetX() {
        return this.axis == Direction.Axis.X ? this.axisDirection.getOffset() : 0;
    }

    public int getFrontOffsetY() {
        return this.axis == Direction.Axis.Y ? this.axisDirection.getOffset() : 0;
    }

    /**
     * Returns a offset that addresses the block in front of this facing.
     */
    public int getFrontOffsetZ() {
        return this.axis == Direction.Axis.Z ? this.axisDirection.getOffset() : 0;
    }

    /**
     * Same as getName, but does not override the method from Enum.
     */
    public String getName2() {
        return this.name;
    }

    public Direction.Axis getAxis() {
        return this.axis;
    }

    /**
     * Get the facing specified by the given name
     */
    @Nullable
    public static Direction byName(String name) {
        return name == null ? null : (Direction) NAME_LOOKUP.get(name.toLowerCase(Locale.ROOT));
    }

    /**
     * Get a Facing by it's index (0-5). The order is D-U-N-S-W-E. Named getFront for legacy reasons.
     */
    public static Direction getFront(int index) {
        return VALUES[Math.abs(index % VALUES.length)];
    }

    /**
     * Get a Facing by it's horizontal index (0-3). The order is S-W-N-E.
     */
    public static Direction getHorizontal(int horizontalIndexIn) {
        return HORIZONTALS[Math.abs(horizontalIndexIn % HORIZONTALS.length)];
    }

    /**
     * Get the Facing corresponding to the given angle (0-360). An angle of 0 is SOUTH, an angle of 90 would be WEST.
     */
    public static Direction fromAngle(double angle) {
        return getHorizontal((int) Math.floor(angle / 90.0D + 0.5D) & 3);
    }

    public float getHorizontalAngle() {
        return (float) ( ( this.horizontalIndex & 3 ) * 90 );
    }

    /**
     * Choose a random Facing using the given Random
     */
    public static Direction random(Random rand) {
        return values()[rand.nextInt(values().length)];
    }

    public static Direction getFacingFromVector(float x, float y, float z) {
        Direction enumfacing = NORTH;
        float f = Float.MIN_VALUE;

        for (Direction enumfacing1 : values()) {
            float f1 = x * (float) enumfacing1.directionVec.getX() + y * (float) enumfacing1.directionVec.getY() + z * (float) enumfacing1.directionVec.getZ();

            if (f1 > f) {
                f = f1;
                enumfacing = enumfacing1;
            }
        }

        return enumfacing;
    }

    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public static Direction getFacingFromAxis(Direction.AxisDirection axisDirectionIn, Direction.Axis axisIn) {
        for (Direction enumfacing : values()) {
            if (enumfacing.getAxisDirection() == axisDirectionIn && enumfacing.getAxis() == axisIn) {
                return enumfacing;
            }
        }

        throw new IllegalArgumentException("No such direction: " + axisDirectionIn + " " + axisIn);
    }

    public static Direction getDirectionFromEntityLiving(Location pos, LivingEntity placer) {
        double placerX = placer.getLocation().getX();
        double placerZ = placer.getLocation().getZ();
        double placerY = placer.getLocation().getZ();
        if (Math.abs(placerX - (double) ( (float) pos.getX() + 0.5F )) < 2.0D && Math.abs(placerZ - (double) ( (float) pos.getZ() + 0.5F )) < 2.0D) {
            double d0 = placerY + (double) placer.getEyeHeight();

            if (d0 - (double) pos.getY() > 2.0D) {
                return UP;
            }

            if ((double) pos.getY() - d0 > 0.0D) {
                return DOWN;
            }
        }

        int floor = (int)Math.floor((placer.getLocation().getYaw() * 4.0F / 360.0F) + 0.5D);
        return Direction.getHorizontal(floor & 3);
    }

    /**
     * Get a normalized Vector that points in the direction of this Facing.
     */
    public Vector getDirectionVec() {
        return this.directionVec;
    }

    static {
        for (Direction enumfacing : values()) {
            VALUES[enumfacing.index] = enumfacing;

            if (enumfacing.getAxis().isHorizontal()) {
                HORIZONTALS[enumfacing.horizontalIndex] = enumfacing;
            }

            NAME_LOOKUP.put(enumfacing.getName2().toLowerCase(Locale.ROOT), enumfacing);
        }
    }

    public static enum Axis implements Predicate<Direction> {
        X("x", Direction.Plane.HORIZONTAL),
        Y("y", Direction.Plane.VERTICAL),
        Z("z", Direction.Plane.HORIZONTAL);

        private static final Map<String, Axis> NAME_LOOKUP = Maps.<String, Direction.Axis>newHashMap();
        private final String name;
        private final Direction.Plane plane;

        private Axis(String name, Direction.Plane plane) {
            this.name = name;
            this.plane = plane;
        }

        /**
         * Get the axis specified by the given name
         */
        @Nullable
        public static Direction.Axis byName(String name) {
            return name == null ? null : (Direction.Axis) NAME_LOOKUP.get(name.toLowerCase(Locale.ROOT));
        }

        /**
         * Like getName but doesn't override the method from Enum.
         */
        public String getName2() {
            return this.name;
        }

        /**
         * If this Axis is on the vertical plane (Only true for Y)
         */
        public boolean isVertical() {
            return this.plane == Direction.Plane.VERTICAL;
        }

        /**
         * If this Axis is on the horizontal plane (true for X and Z)
         */
        public boolean isHorizontal() {
            return this.plane == Direction.Plane.HORIZONTAL;
        }

        public String toString() {
            return this.name;
        }

        public boolean apply(@Nullable Direction p_apply_1_) {
            return p_apply_1_ != null && p_apply_1_.getAxis() == this;
        }

        /**
         * Get this Axis' Plane (VERTICAL for Y, HORIZONTAL for X and Z)
         */
        public Direction.Plane getPlane() {
            return this.plane;
        }

        public String getName() {
            return this.name;
        }

        static {
            for (Direction.Axis enumfacing$axis : values()) {
                NAME_LOOKUP.put(enumfacing$axis.getName2().toLowerCase(Locale.ROOT), enumfacing$axis);
            }
        }
    }

    public static enum AxisDirection {
        POSITIVE(1, "Towards positive"),
        NEGATIVE(- 1, "Towards negative");

        private final int offset;
        private final String description;

        private AxisDirection(int offset, String description) {
            this.offset = offset;
            this.description = description;
        }

        /**
         * Get the offset for this AxisDirection. 1 for POSITIVE, -1 for NEGATIVE
         */
        public int getOffset() {
            return this.offset;
        }

        public String toString() {
            return this.description;
        }
    }

    public static enum Plane implements Predicate<Direction>, Iterable<Direction> {
        HORIZONTAL,
        VERTICAL;

        /**
         * All Direction values for this Plane
         */
        public Direction[] facings() {
            switch (this) {
                case HORIZONTAL:
                    return new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
                case VERTICAL:
                    return new Direction[]{Direction.UP, Direction.DOWN};
                default:
                    throw new Error("Someone's been tampering with the universe!");
            }
        }

        /**
         * Choose a random Facing from this Plane using the given Random
         */
        public Direction random(Random rand) {
            Direction[] aenumfacing = this.facings();
            return aenumfacing[rand.nextInt(aenumfacing.length)];
        }

        public boolean apply(@Nullable Direction p_apply_1_) {
            return p_apply_1_ != null && p_apply_1_.getAxis().getPlane() == this;
        }

        public Iterator<Direction> iterator() {
            return Iterators.<Direction>forArray(this.facings());
        }
    }
}
