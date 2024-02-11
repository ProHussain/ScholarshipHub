package com.hashmac.scholarshiphub.admin.utils;

import com.hashmac.scholarshiphub.admin.dto.Scholarship;

import java.util.List;

public interface RepoListener {

    public void onRepoSuccess();
    public void onRepoFailure(String message);

    public void onRepoFetch(List<Scholarship> scholarships);
}
