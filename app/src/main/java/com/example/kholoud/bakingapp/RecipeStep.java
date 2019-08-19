package com.example.kholoud.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeStep implements Parcelable {

    private int Id;
    private String smallDescription;
    private String Description;
    private String VideoUrl;
    private String ImageUrl;

    public static final Parcelable.Creator<RecipeStep> CREATOR
            = new Parcelable.Creator<RecipeStep>() {
        @Override
        public RecipeStep createFromParcel(Parcel source) {
            return new RecipeStep(source);
        }

        @Override
        public RecipeStep[] newArray(int size) {
            return new RecipeStep[size];
        }
    };


    public RecipeStep(int id, String smallDescription,
                      String Description, String VideoUrl, String ImageUrl) {
        Id= id;
        this.smallDescription = smallDescription;
        this.Description = Description;
        this.VideoUrl = VideoUrl;
        this.ImageUrl = ImageUrl;
    }

    private RecipeStep(Parcel in) {
        Id = in.readInt();
        smallDescription = in.readString();
        Description = in.readString();
        VideoUrl = in.readString();
        ImageUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(smallDescription);
        dest.writeString(Description);
        dest.writeString(VideoUrl);
        dest.writeString(ImageUrl);
    }



    public String getSmallDescription() {
        return smallDescription;
    }


    public String getDescription() {
        return Description;
    }


    public String getVideoUrl() {
        return VideoUrl;
    }


    public String getImageUrl() {
        return ImageUrl;
    }

}
