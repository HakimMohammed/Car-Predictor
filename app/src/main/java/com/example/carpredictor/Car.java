package com.example.carpredictor;

import android.os.Parcel;
import android.os.Parcelable;

public class Car implements Parcelable {
    private String mpg;
    private String displacement;
    private String acceleration;
    private String weight;
    private String horsePower;
    private String origin;

    public Car(String mpg, String displacement, String acceleration, String weight, String horsePower, String origin) {
        this.mpg = mpg;
        this.displacement = displacement;
        this.acceleration = acceleration;
        this.weight = weight;
        this.horsePower = horsePower;
        this.origin = (origin != null) ? origin : "";
    }

    public int getMpg() {
        return Integer.parseInt(mpg);
    }

    public int getDisplacement() {
        return Integer.parseInt(displacement);
    }

    public int getAcceleration() {
        return Integer.parseInt(acceleration);
    }

    public int getWeight() {
        return Integer.parseInt(weight);
    }

    public int getHorsePower() {
        return Integer.parseInt(horsePower);
    }

    public String getOrigin() {
        return origin;
    }

    protected Car(Parcel in) {
        mpg = in.readString();
        displacement = in.readString();
        acceleration = in.readString();
        weight = in.readString();
        horsePower = in.readString();
        origin = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mpg);
        dest.writeString(displacement);
        dest.writeString(acceleration);
        dest.writeString(weight);
        dest.writeString(horsePower);
        dest.writeString(origin);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    public String toString() {
        return "Car{" +
                "mpg='" + mpg + '\'' +
                ", displacement='" + displacement + '\'' +
                ", acceleration='" + acceleration + '\'' +
                ", weight='" + weight + '\'' +
                ", horsePower='" + horsePower + '\'' +
                ", origin='" + origin + '\'' +
                '}';
    }

    public String toCsvString() {
        return String.format("%s,%s,%s,%s,%s,%s",
                mpg, displacement, acceleration, weight, horsePower, origin);
    }

    public double distanceEuclidean(Car c) {
        double distance = 0.0;
        int mpgDiff = Math.abs(this.getMpg() - c.getMpg());
        int displacementDiff = Math.abs(this.getDisplacement() - c.getDisplacement());
        int accelerationDiff = Math.abs(this.getAcceleration() - c.getAcceleration());
        int weightDiff = Math.abs(this.getWeight() - c.getWeight());
        int horsePowerDiff = Math.abs(this.getHorsePower() - c.getHorsePower());

        distance = Math.sqrt(mpgDiff + displacementDiff + accelerationDiff + weightDiff + horsePowerDiff);

        return distance;
    }

    public double distanceManhattan(Car c) {
        double distance = 0.0;
        int mpgDiff = Math.abs(this.getMpg() - c.getMpg());
        int displacementDiff = Math.abs(this.getDisplacement() - c.getDisplacement());
        int accelerationDiff = Math.abs(this.getAcceleration() - c.getAcceleration());
        int weightDiff = Math.abs(this.getWeight() - c.getWeight());
        int horsePowerDiff = Math.abs(this.getHorsePower() - c.getHorsePower());

        distance = Math.sqrt(mpgDiff + displacementDiff + accelerationDiff + weightDiff + horsePowerDiff);

        return distance;
    }
}
