package com.example.newsapp.models;

import java.util.List;

public interface OnFetchDataListener<NewsApiResponse> {
    void OnFetchData(List<NewsHeadlines> list , String message);
    void OnError(String message);

}
