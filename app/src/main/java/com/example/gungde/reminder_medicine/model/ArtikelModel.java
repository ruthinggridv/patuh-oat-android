package com.example.gungde.reminder_medicine.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ArtikelModel implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("title")
    @Expose
    private Title title;
    @SerializedName("content")
    @Expose
    private Content content;
    @SerializedName("excerpt")
    @Expose
    private Excerpt excerpt;
    @SerializedName("_embedded")
    private Embedded embedded;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Excerpt getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(Excerpt excerpt) {
        this.excerpt = excerpt;
    }

    public Embedded getEmbedded() {
        return embedded;
    }

    public void setEmbedded(Embedded embedded) {
        this.embedded = embedded;
    }

    public static class Content implements Parcelable {

        @SerializedName("rendered")
        @Expose
        private String rendered;
        @SerializedName("protected")
        @Expose
        private Boolean _protected;

        public String getRendered() {
            return rendered;
        }

        public void setRendered(String rendered) {
            this.rendered = rendered;
        }

        public Boolean getProtected() {
            return _protected;
        }

        public void setProtected(Boolean _protected) {
            this._protected = _protected;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.rendered);
            dest.writeValue(this._protected);
        }

        public Content() {
        }

        protected Content(Parcel in) {
            this.rendered = in.readString();
            this._protected = (Boolean) in.readValue(Boolean.class.getClassLoader());
        }

        public static final Creator<Content> CREATOR = new Creator<Content>() {
            @Override
            public Content createFromParcel(Parcel source) {
                return new Content(source);
            }

            @Override
            public Content[] newArray(int size) {
                return new Content[size];
            }
        };
    }

    public static class Embedded implements Parcelable {

        @SerializedName("wp:featuredmedia")
        @Expose
        private List<WpFeaturedmedium> wpFeaturedmedia;
        @SerializedName("author")
        @Expose
        private List<Author> author;

        public List<WpFeaturedmedium> getWpFeaturedmedia() {
            return wpFeaturedmedia;
        }

        public void setWpFeaturedmedia(List<WpFeaturedmedium> wpFeaturedmedia) {
            this.wpFeaturedmedia = wpFeaturedmedia;
        }

        public List<Author> getAuthor() {
            return author;
        }

        public void setAuthor(List<Author> author) {
            this.author = author;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeList(this.wpFeaturedmedia);
        }

        public Embedded() {
        }

        protected Embedded(Parcel in) {
            this.wpFeaturedmedia = new ArrayList<WpFeaturedmedium>();
            in.readList(this.wpFeaturedmedia, WpFeaturedmedium.class.getClassLoader());
        }

        public static final Creator<Embedded> CREATOR = new Creator<Embedded>() {
            @Override
            public Embedded createFromParcel(Parcel source) {
                return new Embedded(source);
            }

            @Override
            public Embedded[] newArray(int size) {
                return new Embedded[size];
            }
        };
    }

    public static class Author {

        @SerializedName("id")
        @Expose
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


    }

    public static class Excerpt implements Parcelable {

        @SerializedName("rendered")
        @Expose
        private String rendered;
        @SerializedName("protected")
        @Expose
        private Boolean _protected;

        public String getRendered() {
            return rendered;
        }

        public void setRendered(String rendered) {
            this.rendered = rendered;
        }

        public Boolean getProtected() {
            return _protected;
        }

        public void setProtected(Boolean _protected) {
            this._protected = _protected;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.rendered);
            dest.writeValue(this._protected);
        }

        public Excerpt() {
        }

        protected Excerpt(Parcel in) {
            this.rendered = in.readString();
            this._protected = (Boolean) in.readValue(Boolean.class.getClassLoader());
        }

        public static final Creator<Excerpt> CREATOR = new Creator<Excerpt>() {
            @Override
            public Excerpt createFromParcel(Parcel source) {
                return new Excerpt(source);
            }

            @Override
            public Excerpt[] newArray(int size) {
                return new Excerpt[size];
            }
        };
    }

    public static class Title implements Parcelable {

        @SerializedName("rendered")
        @Expose
        private String rendered;

        public String getRendered() {
            return rendered;
        }

        public void setRendered(String rendered) {
            this.rendered = rendered;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.rendered);
        }

        public Title() {
        }

        protected Title(Parcel in) {
            this.rendered = in.readString();
        }

        public static final Creator<Title> CREATOR = new Creator<Title>() {
            @Override
            public Title createFromParcel(Parcel source) {
                return new Title(source);
            }

            @Override
            public Title[] newArray(int size) {
                return new Title[size];
            }
        };
    }

    public static class WpFeaturedmedium implements Parcelable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("source_url")
        @Expose
        private String sourceUrl;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getSourceUrl() {
            return sourceUrl;
        }

        public void setSourceUrl(String sourceUrl) {
            this.sourceUrl = sourceUrl;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.id);
            dest.writeString(this.date);
            dest.writeString(this.sourceUrl);
        }

        public WpFeaturedmedium() {
        }

        protected WpFeaturedmedium(Parcel in) {
            this.id = (Integer) in.readValue(Integer.class.getClassLoader());
            this.date = in.readString();
            this.sourceUrl = in.readString();
        }

        public static final Creator<WpFeaturedmedium> CREATOR = new Creator<WpFeaturedmedium>() {
            @Override
            public WpFeaturedmedium createFromParcel(Parcel source) {
                return new WpFeaturedmedium(source);
            }

            @Override
            public WpFeaturedmedium[] newArray(int size) {
                return new WpFeaturedmedium[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.date);
        dest.writeString(this.link);
        dest.writeParcelable(this.title, flags);
        dest.writeParcelable(this.content, flags);
        dest.writeParcelable(this.excerpt, flags);
        dest.writeParcelable(this.embedded, flags);
    }

    public ArtikelModel() {
    }

    protected ArtikelModel(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.date = in.readString();
        this.link = in.readString();
        this.title = in.readParcelable(Title.class.getClassLoader());
        this.content = in.readParcelable(Content.class.getClassLoader());
        this.excerpt = in.readParcelable(Excerpt.class.getClassLoader());
        this.embedded = in.readParcelable(Embedded.class.getClassLoader());
    }

    public static final Parcelable.Creator<ArtikelModel> CREATOR = new Parcelable.Creator<ArtikelModel>() {
        @Override
        public ArtikelModel createFromParcel(Parcel source) {
            return new ArtikelModel(source);
        }

        @Override
        public ArtikelModel[] newArray(int size) {
            return new ArtikelModel[size];
        }
    };
}
