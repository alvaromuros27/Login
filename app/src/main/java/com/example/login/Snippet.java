package com.example.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Snippet {
    
        @SerializedName("url")
        @Expose
        public String url;
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("highlight")
        @Expose
        public String highlight;
        @SerializedName("owner")
        @Expose
        public String owner;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("code")
        @Expose
        public String code;
        @SerializedName("linenos")
        @Expose
        public Boolean linenos;
        @SerializedName("language")
        @Expose
        public String language;
        @SerializedName("style")
        @Expose
        public String style;



        public Snippet(String url, String id, String highlight, String owner, String title, String code, Boolean linenos, String language, String style){
            this.url=url;
            this.id=id;
            this.highlight=highlight;
            this.owner=owner;
            this.title=title;
            this.code=code;
            this.linenos=linenos;
            this.language=language;
            this.style=style;

        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHighlight() {
            return ""+highlight+"";
        }

        public void setHighlight(String highlight) {
            this.highlight = highlight;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Boolean getLinenos() {
            return linenos;
        }

        public void setLinenos(Boolean linenos) {
            this.linenos = linenos;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", id='" + id + '\'' +
                    ", highlight='" + highlight + '\'' +
                    ", owner='" + owner + '\'' +
                    ", title='" + title + '\'' +
                    ", code='" + code + '\'' +
                    ", linenos='" + linenos + '\'' +
                    ", language='" + language + '\'' +
                    ", style='" + style + '\'' +
                    '}';
        }
}
