package com.github.yaroslavguschak.entityrequest;


public class SearchRequest {

    private String searchTextInput = "";

    public SearchRequest() {
    }

    public SearchRequest(String searchTextInput) {
        this.searchTextInput = searchTextInput;
    }

    public String getSearchTextInput() {
        return searchTextInput;
    }

    public void setSearchTextInput(String searchTextInput) {
        this.searchTextInput = searchTextInput;
    }
}
