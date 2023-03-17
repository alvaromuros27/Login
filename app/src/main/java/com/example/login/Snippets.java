package com.example.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Snippets {
    @SerializedName("count")
    @Expose
    public int count;
    @SerializedName("previous")
    @Expose
    public String previous;
    @SerializedName("next")
    @Expose
    public String next;
    @SerializedName("results")
    @Expose
    public List<Snippet> snippetList;




    public Snippets(int count, List snippetList, String next, String previous){
        this.count=count;
        this.snippetList = snippetList;
        this.next=next;
        this.previous=previous;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Snippet> getSnippetList() {
        return snippetList;
    }

    public void setSnippetList(List<Snippet> snippetList) {
        this.snippetList = snippetList;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}

