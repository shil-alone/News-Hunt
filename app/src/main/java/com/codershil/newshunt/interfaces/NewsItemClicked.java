package com.codershil.newshunt.interfaces;

import com.codershil.newshunt.models.News;

public interface NewsItemClicked {
    void newsImageClicked(News item);
    void shareButtonClicked(News item);
    void deleteButtonClicked(News item,int position);
    void saveButtonClicked(News item);
}
