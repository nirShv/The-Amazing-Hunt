package com.theamazinghunt.presentation.creategame

import androidx.annotation.StringRes
import com.theamazinghunt.R

enum class WizardStep(
    val number: Int,
    @StringRes val titleRes: Int,
    @StringRes val bodyRes: Int
) {
    BASICS(
        number = 1,
        titleRes = R.string.wizard_basics_title,
        bodyRes = R.string.wizard_basics_body
    ),
    PLAY_AREA(
        number = 2,
        titleRes = R.string.wizard_play_area_title,
        bodyRes = R.string.wizard_play_area_body
    ),
    POINTS(
        number = 3,
        titleRes = R.string.wizard_points_title,
        bodyRes = R.string.wizard_points_body
    ),
    CLUES(
        number = 4,
        titleRes = R.string.wizard_clues_title,
        bodyRes = R.string.wizard_clues_body
    ),
    REVIEW(
        number = 5,
        titleRes = R.string.wizard_review_title,
        bodyRes = R.string.wizard_review_body
    );

    companion object {
        val totalSteps: Int = entries.size
    }
}
