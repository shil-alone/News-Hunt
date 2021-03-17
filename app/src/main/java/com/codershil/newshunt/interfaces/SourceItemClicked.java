package com.codershil.newshunt.interfaces;

import com.codershil.newshunt.models.Source;

public interface SourceItemClicked {
    void sourceItemClicked(Source source);
    void sourceSaveButtonClicked(Source source);
    void sourceDeleteButtonClicked(Source source,int position);
}
