package ru.naburnm8.bmstu.android.datamanagementnirapp.misc;

import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.RESTDBOutput;

public interface OnGenerationListener extends RESTDBOutput {
    public void onGenerationResult(String status);
}
